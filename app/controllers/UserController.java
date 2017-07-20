package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Password;
import models.User;
import play.Logger;
import play.db.jpa.Transactional;
import play.db.jpa.JPAApi;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import validators.NewUser;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class UserController extends Controller
{
    private final JPAApi jpaApi;

    @Inject
    public UserController(JPAApi jpaApi)
    {
        this.jpaApi = jpaApi;
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result signUp()
    {
        boolean valid = true;
        List<String> errorList = new ArrayList<>();

        JsonNode request = request().body().asJson();

        String email = request.findPath("email").textValue();
        String password = request.findPath("password").textValue();
        String passwordConfirm = request.findPath("passwordConfirm").textValue();

        if(NewUser.emailInvalid(email))
        {
            errorList.add("-Email Invalid");
            valid = false;
        }
        if(!NewUser.emailInvalid(email) && alreadyExists(email))
        {
            errorList.add("-Email address in use");
            valid = false;
        }
        if(NewUser.passwordInvalid(password))
        {
            errorList.add("-Password Invalid");
            valid = false;
        }
        if(!NewUser.passwordInvalid(password) && !NewUser.passwordsMatch(password, passwordConfirm))
        {
            errorList.add("-Passwords do not match");
            valid = false;
        }

        if(valid)
        {
            session().put("email", email);
            session().put("password", password);

            return ok(Json.toJson("success"));
        }
        else
        {
            return ok(Json.toJson(errorList));
        }
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result addUser() throws Exception
    {
        boolean namesValid = false;
        boolean milesValid = false;
        List<String> errorList = new ArrayList<>();
        JsonNode request = request().body().asJson();

        String email = session().get("email");
        String password = session().get("password");
        String firstName = request.findPath("firstName").textValue();
        String lastName = request.findPath("lastName").textValue();
        int notificationsOptIn = request.findPath("notificationsOptIn").asInt();
        String notificationsMilesAhead = request.findPath("notificationsMilesAhead").asText();

        if (firstName != null && lastName != null)
        {
            if (NewUser.nameValid(firstName) && NewUser.nameValid(lastName))
            {
                namesValid = true;
            } else
            {
                errorList.add("Names cannot contain special characters or exceed 20 characters");
            }

            if (notificationsOptIn == 0 || NewUser.milesValid(notificationsMilesAhead))
            {
                milesValid = true;
            } else
            {
                errorList.add("Please enter a number between 1 and 65,000 miles. Default 100");
            }
        }
        else
        {
            errorList.add("Fields marked with * are required");
        }

        if(namesValid && milesValid)
        {
            User user = new User();
            LocalDate date = LocalDate.now();

            byte[] salt = Password.getNewSalt();
            byte[] hashedPassword = Password.hashPassword(password.toCharArray(), salt);

            user.setEmail(email);
            user.setPassword(hashedPassword);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setNotificationsOptIn(notificationsOptIn);
            user.setLastNotified(date);
            user.setSalt(salt);

            if(user.getNotificationsOptIn() == 1)
            {
                user.setNotificationsMilesAhead(Integer.parseInt(notificationsMilesAhead));
            }

            jpaApi.em().persist(user);

            int userId = user.getUserID();
            session().put("userId", userId + ""); 

            return ok(Json.toJson("success"));
        }
        else
        {
            return ok(Json.toJson(errorList));
        }
    }

    @Transactional
    public Result getUser()
    {
        int id = Integer.parseInt(session().get("userId"));

        User user = jpaApi.em().createQuery("SELECT u FROM User u WHERE userID = :id", User.class)
                .setParameter("id", id).getSingleResult();

        return ok(Json.toJson(user));
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result updateProfile()
    {
        boolean namesValid = false;
        boolean milesValid = false;
        List<String> errorList = new ArrayList<>();

        JsonNode request = request().body().asJson();

        int id = Integer.parseInt(session().get("userId"));
        String firstName = request.findPath("firstName").textValue();
        String lastName = request.findPath("lastName").textValue();
        int notificationsOptIn = request.findPath("notificationsOptIn").asInt();
        String notificationsMilesAhead = request.findPath("notificationsMilesAhead").asText();

        if (NewUser.nameValid(firstName) && NewUser.nameValid(lastName))
        {
            namesValid = true;
        }
        else
        {
            errorList.add("Names cannot contain special characters or exceed 20 characters");
        }

        if(NewUser.milesValid(notificationsMilesAhead))
        {
            milesValid = true;
        }
        else
        {
            errorList.add("Please enter a number between 1 and 65,000 miles. Default 100");
        }

        if(namesValid && milesValid)
        {
            jpaApi.em().createQuery("UPDATE User u SET " +
                    "u.firstName = :firstName, " +
                    "u.lastName = :lastName, " +
                    "u.notificationsOptIn = :notificationsOptIn, " +
                    "u.notificationsMilesAhead = :notificationsMilesAhead " +
                    "WHERE u.userID = :id")
                    .setParameter("firstName", firstName)
                    .setParameter("id", id)
                    .setParameter("lastName", lastName)
                    .setParameter("notificationsOptIn", notificationsOptIn)
                    .setParameter("notificationsMilesAhead", Integer.parseInt(notificationsMilesAhead))
                    .executeUpdate();
            return ok(Json.toJson("success"));
        }
        else
        {
            return ok(Json.toJson(errorList));
        }
    }

    @Transactional
    private boolean alreadyExists(String email)
    {
        boolean exists = false;

        List<User> users = jpaApi.em().createQuery("SELECT u FROM User u WHERE email = :email", User.class)
                .setParameter("email", email).getResultList();

        if(users.size() > 0)
        {
            exists = true;
        }

        return exists;
    }
}

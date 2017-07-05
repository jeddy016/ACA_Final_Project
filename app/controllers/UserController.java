package controllers;

import com.fasterxml.jackson.databind.JsonNode;
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
        if(!NewUser.emailInvalid(email) && NewUser.alreadyExists(email))
        {
            errorList.add("-Email address taken");
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
    public Result addUser()
    {
        //TODO: form validation

        boolean valid = true;
        List<String> errorList = new ArrayList<>();
        JsonNode request = request().body().asJson();

        String email = session().get("email");
        String password = session().get("password");
        String firstName = request.findPath("firstName").textValue();
        String lastName = request.findPath("lastName").textValue();
        long phoneNumber = request.findPath("phoneNumber").asLong();
        int zipCode = request.findPath("zipCode").asInt();
        int notificationsOptIn = request.findPath("notificationsOptIn").asInt();
        int notificationsHour = request.findPath("notificationsHour").asInt();
        int notificationsDaysAhead = request.findPath("notificationsDaysAhead").asInt();

        if(valid)
        {
            User user = new User();

            user.setEmail(email);
            user.setPassword(password);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setZipCode(zipCode);
            user.setPhoneNumber(phoneNumber);
            user.setNotificationsHour(notificationsHour);
            user.setNotificationsDaysAhead(notificationsDaysAhead);
            user.setNotificationsOptIn(notificationsOptIn);

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
        //TODO: form validation
        boolean valid = true;
        List<String> errorList = new ArrayList<>();

        JsonNode request = request().body().asJson();

        int id = Integer.parseInt(session().get("userId"));
        String firstName = request.findPath("firstName").textValue();
        String lastName = request.findPath("lastName").textValue();
        long phoneNumber = request.findPath("phoneNumber").asLong();
        int zipCode = request.findPath("zipCode").asInt();
        int notificationsOptIn = request.findPath("notificationsOptIn").asInt();
        int notificationsHour = request.findPath("notificationsHour").asInt();
        int notificationsDaysAhead = request.findPath("notificationsDaysAhead").asInt();

        Logger.debug("" + notificationsOptIn);

        if(valid)
        {
            jpaApi.em().createQuery("UPDATE User u SET " +
                    "u.firstName = :firstName, " +
                    "u.lastName = :lastName, " +
                    "u.phoneNumber = :phoneNumber, " +
                    "u.zipCode = :zipCode, " +
                    "u.notificationsOptIn = :notificationsOptIn, " +
                    "u.notificationsHour = :notificationsHour, " +
                    "u.notificationsDaysAhead = :notificationsDaysAhead " +
                    "WHERE u.userID = :id")
                    .setParameter("firstName", firstName)
                    .setParameter("id", id)
                    .setParameter("lastName", lastName)
                    .setParameter("phoneNumber", phoneNumber)
                    .setParameter("zipCode", zipCode)
                    .setParameter("notificationsOptIn", notificationsOptIn)
                    .setParameter("notificationsHour", notificationsHour)
                    .setParameter("notificationsDaysAhead", notificationsDaysAhead)
                    .executeUpdate();

            return ok(Json.toJson("success"));
        }
        else
        {
            return ok(Json.toJson(errorList));
        }
    }
}

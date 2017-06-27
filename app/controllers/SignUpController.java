package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.User;
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


public class SignUpController extends Controller
{
    private final JPAApi jpaApi;

    @Inject
    public SignUpController(JPAApi jpaApi)
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
            User user = new User();

            user.setUserID(1);
            user.setEmail(email);
            user.setPassword(password);
            user.setFirstName("Jordan");
            user.setLastName("Eddy");
            user.setNotificationsHour(1);
            user.setNotificationsDaysAhead(1);
            user.setNotificationsOptIn(true);

            jpaApi.em().persist(user);

            return ok(Json.toJson("success"));
        }
        else
        {
            return ok(Json.toJson(errorList));
        }
    }

    public Result addUser()
    {
        User user = new User();

        user.setUserID(1);
       // user.setEmail(email);
        //user.setPassword(password);
        user.setFirstName("Jordan");
        user.setLastName("Eddy");
        user.setNotificationsHour(1);
        user.setNotificationsDaysAhead(1);
        user.setNotificationsOptIn(true);

        jpaApi.em().persist(user);

        return ok(Json.toJson("success"));
    }
}

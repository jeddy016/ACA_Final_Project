package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Password;
import models.User;
import models.UserID;
import play.Logger;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import validators.Login;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginController extends Controller
{
    private final JPAApi jpaApi;

    @Inject
    public LoginController(JPAApi jpaApi)
    {
        this.jpaApi = jpaApi;
    }

    @Transactional(readOnly = true)
    @BodyParser.Of(BodyParser.Json.class)
    public Result authenticate()
    {
        List<String> errorList = new ArrayList<>();
        JsonNode request = request().body().asJson();

        String email = request.findPath("email").textValue();
        String password = request.findPath("password").textValue();

        @SuppressWarnings("unchecked")
        List<UserID> users = jpaApi.em().createNativeQuery("SELECT user_id as ID, password, salt FROM User WHERE user_email = :email", UserID.class).setParameter("email", email).getResultList();

        Logger.debug(users.size() + "");

        if (users.size() == 1)
        {
            UserID user = users.get(0);
            byte[] hashedPassword = Password.hashPassword(password.toCharArray(), user.getSalt());
            byte[] dbPassword = user.getPassword();

            if (Arrays.equals(hashedPassword, dbPassword))
            {
                session().put("userId", "" + user.getID());

                return ok(Json.toJson("success"));
            }
        }

        errorList.add("- Email or Password incorrect");

        return ok(Json.toJson(errorList));
    }

    public Result logout()
    {
        session().remove("userId");

        return ok(Json.toJson("logged out"));
    }
}

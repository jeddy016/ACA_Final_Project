package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.User;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import validators.Login;

import javax.inject.Inject;
import java.util.ArrayList;
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
        boolean valid = true;

        List<String> errorList = new ArrayList<>();

        JsonNode request = request().body().asJson();

        String email = request.findPath("email").textValue();
        String password = request.findPath("password").textValue();

        if(Login.passwordInvalid(password) || Login.emailInvalid(email))
        {
            errorList.add("- Email or Password incorrect");
            valid = false;
        }

        if(valid)
        {
            User user = jpaApi.em().createQuery("SELECT u FROM User u WHERE u.email = :email AND u.password = :password", User.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult();

            session().put("userId", "" + user.getUserID());

            return ok(Json.toJson("success"));
        }
        else
        {
            return ok(Json.toJson(errorList));
        }
    }

    public Result logout()
    {
        session().remove("userId");

        return ok(Json.toJson("logged out"));
    }
}

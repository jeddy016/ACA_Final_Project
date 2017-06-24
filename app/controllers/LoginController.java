package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.Logger;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import validators.LoginValidator;
import java.util.ArrayList;
import java.util.List;


public class LoginController extends Controller
{

    @BodyParser.Of(BodyParser.Json.class)
    public Result authenticate()
    {
        boolean valid = true;

        List<String> errorList = new ArrayList<>();

        JsonNode request = request().body().asJson();

        String email = request.findPath("email").textValue();
        String password = request.findPath("password").textValue();

        if(LoginValidator.passwordInvalid(password))
        {
            errorList.add("Password Invalid");
            valid = false;
        }
        if(LoginValidator.emailInvalid(email))
        {
            errorList.add("Email Invalid");
            valid = false;
        }

        //TODO find user matching u/n & p/w, put their id in session

        if(valid)
        {
            //TODO send user id back to browser to validate further requests
            return ok("");
        }
        else
        {
            return ok(Json.toJson(errorList));
        }
    }
}

package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import validators.Login;
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

        if(Login.passwordInvalid(password) || Login.emailInvalid(email))
        {
            errorList.add("- Email or Password incorrect");
            valid = false;
        }

        //TODO find user matching u/n & p/w, put their id in session

        if(valid)
        {

            //TODO(maybe) send user id back to browser to validate further requests

            return ok(Json.toJson("success"));
        }
        else
        {
            return ok(Json.toJson(errorList));
        }
    }
}

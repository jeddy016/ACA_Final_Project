package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import validators.NewUser;
import java.util.ArrayList;
import java.util.List;


public class SignUpController extends Controller
{
    @BodyParser.Of(BodyParser.Json.class)
    public Result addUser()
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
            //TODO: Add user to DB with email and password

            return ok(Json.toJson("success"));
        }
        else
        {
            return ok(Json.toJson(errorList));
        }
    }
}

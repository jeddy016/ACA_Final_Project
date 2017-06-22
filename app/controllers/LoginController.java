package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import play.Logger;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.IOException;


public class LoginController extends Controller
{

    @BodyParser.Of(BodyParser.Json.class)
    public Result authenticate()
    {
        //Get data sent from JS Http post
        JsonNode json = request().body().asJson();

        String email = json.findPath("email").textValue();
        String password = json.findPath("password").textValue();

        Logger.debug(email);
        Logger.debug(password);
        return ok();
    }
}

package controllers;


import play.mvc.Controller;
import play.mvc.Result;

import static play.mvc.Results.ok;

public class AppController extends Controller
{
    public Result index(String any)
    {
        return ok(views.html.index.render());
    }

    public Result checkForUser()
    {
        String id = session().get("userId");

        if(id == null)
        {
            return internalServerError();
        }

        return ok("user logged in");
    }
}

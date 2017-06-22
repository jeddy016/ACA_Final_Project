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
}

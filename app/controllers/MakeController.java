package controllers;

import models.VehicleMake;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;


public class MakeController extends Controller
{
    private final JPAApi jpaApi;

    @Inject
    public MakeController(JPAApi jpaApi)
    {
        this.jpaApi = jpaApi;
    }

    @Transactional(readOnly = true)
    public Result getMakes()
    {
        List<VehicleMake> makeList = jpaApi.em().createQuery("SELECT m FROM VehicleMake m ORDER BY name", VehicleMake.class).getResultList();

        return ok(Json.toJson(makeList));
    }
}

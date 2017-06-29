package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.VehicleMake;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;

import javax.inject.Inject;

public class MakeController extends Controller
{
    private final JPAApi jpaApi;

    @Inject
    public MakeController(JPAApi jpaApi)
    {
        this.jpaApi = jpaApi;
    }

    @Transactional
    public VehicleMake set(JsonNode request)
    {
        VehicleMake make = new VehicleMake();



        return make;
    }
}

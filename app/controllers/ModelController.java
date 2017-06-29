package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import play.Logger;
import models.VehicleModel;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;


public class ModelController extends Controller
{
    private final JPAApi jpaApi;

    @Inject
    public ModelController(JPAApi jpaApi)
    {
        this.jpaApi = jpaApi;
    }

    @Transactional(readOnly = true)
    public Result getModels()
    {
        String request = request().getQueryString("makeID");

        List<VehicleModel> modelList = jpaApi.em().createQuery("SELECT m FROM VehicleModel m WHERE vehicleMakeID = :id ORDER BY name", VehicleModel.class)
                .setParameter("id", Integer.parseInt(request))
                .getResultList();

        return ok(Json.toJson(modelList));
    }
}

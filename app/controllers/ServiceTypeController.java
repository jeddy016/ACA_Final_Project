package controllers;

import models.ServiceType;
import models.VehicleList;
import play.Logger;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

public class ServiceTypeController extends Controller
{
    private final JPAApi jpaApi;

    @Inject
    public ServiceTypeController(JPAApi jpaApi)
    {
        this.jpaApi = jpaApi;
    }

    @Transactional
    public Result getServiceTypes()
    {
        int userId = Integer.parseInt(session().get("userId"));

        List<ServiceType> serviceTypeList = jpaApi.em().createQuery("SELECT t FROM ServiceType t ORDER BY t.typeName", ServiceType.class)
                .getResultList();

        return ok(Json.toJson(serviceTypeList));
    }



}

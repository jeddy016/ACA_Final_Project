package controllers;


import models.CompletedService;
import models.CompletedServiceDetail;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

public class CompletedServiceController extends Controller
{
    private final JPAApi jpaApi;

    @Inject
    public CompletedServiceController(JPAApi jpaApi)
    {
        this.jpaApi = jpaApi;
    }

    @Transactional
    public Result getCompletedServices()
    {
        boolean valid;
        int vehicleID = Integer.parseInt(request().getQueryString("vehicleID"));

        List<CompletedServiceDetail> completedServices = jpaApi.em().createNativeQuery("SELECT cs.completed_service_id as id, st.type_name as name, cs.service_date as date, cs.total_cost as totalCost, cs.labor_cost as laborCost, cs.parts_cost as partsCost, cs.shop as shop FROM completed_service cs JOIN service s ON s.service_id = cs.service_id  JOIN service_type st ON s.service_type_id = st.service_type_id WHERE cs.vehicle_id = :id ORDER BY cs.service_date", CompletedServiceDetail.class)
                .setParameter("id",vehicleID)
                .getResultList();

        return ok(Json.toJson(completedServices));
    }
}


package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.ServiceDetail;
import models.ServiceType;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

public class ServiceController extends Controller
{
    private final JPAApi jpaApi;

    @Inject
    public ServiceController(JPAApi jpaApi)
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

    @Transactional
    public Result getServices()
    {
        int vehicleID = Integer.parseInt(request().getQueryString("vehicleID"));

        List<ServiceDetail> serviceList = jpaApi.em().createNativeQuery("SELECT s.service_id as id, st.type_name as name, s.mileage_interval as milesInterval, " +
                "st.rec_miles_interval as recMilesInterval, " +
                "s.miles_til_due as milesTilDue FROM service s " +
                "JOIN service_type st ON st.service_type_id = s.service_type_id " +
                "WHERE vehicle_id = :vehicleID " +
                "ORDER BY s.miles_til_due", ServiceDetail.class)
                .setParameter("vehicleID", vehicleID)
                .getResultList();

        return ok(Json.toJson(serviceList));
    }


}

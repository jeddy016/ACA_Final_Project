package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Service;
import models.ServiceDetail;
import models.ServiceType;
import play.Logger;
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

    /*@Transactional
    public Result getServiceTypes()
    {
        int userId = Integer.parseInt(session().get("userId"));

        List<ServiceType> serviceTypeList = jpaApi.em().createQuery("SELECT t FROM ServiceType t ORDER BY t.typeName", ServiceType.class)
                .getResultList();

        return ok(Json.toJson(serviceTypeList));
    }*/

    @Transactional
    public Result getServices()
    {
        int vehicleID = Integer.parseInt(request().getQueryString("vehicleID"));

        @SuppressWarnings("unchecked")
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

    @Transactional
    public Result getTrackedServices()
    {
        int vehicleID = Integer.parseInt(request().getQueryString("vehicleID"));

        List<Integer> trackedServiceTypes = jpaApi.em().createNativeQuery("SELECT s.service_type_id as id " +
                "FROM service s " +
                "JOIN vehicle v ON s.vehicle_id = v.vehicle_id " +
                "WHERE v.vehicle_id = :id").setParameter("id", vehicleID)
                .getResultList();

        return ok(Json.toJson(trackedServiceTypes));
    }

    @Transactional
    public Result updateTrackedServices()
    {
        JsonNode request = request().body().asJson();
        int vehicleID = request.get("id").asInt();
        JsonNode serviceTypeIDs = request.get("services");

        List<Integer> trackedServiceTypes = jpaApi.em().createNativeQuery("SELECT s.service_type_id as id " +
                "FROM service s " +
                "JOIN vehicle v ON s.vehicle_id = v.vehicle_id " +
                "WHERE v.vehicle_id = :id").setParameter("id", vehicleID)
                .getResultList();

        jpaApi.em().createNativeQuery("DELETE FROM service WHERE vehicle_id = :id").setParameter("id", vehicleID).executeUpdate();

        for(JsonNode id : serviceTypeIDs)
        {
            ServiceType serviceType = jpaApi.em().createQuery("SELECT s FROM ServiceType s WHERE serviceTypeID = :id", ServiceType.class)
                    .setParameter("id", id.asInt())
                    .getSingleResult();

            Service service = new Service();
            service.setMilesInterval(serviceType.getRecommendedMilesinterval());
            service.setMilesTilDue(serviceType.getRecommendedMilesinterval());
            service.setVehicleID(vehicleID);
            service.setServiceTypeID(id.asInt());

            jpaApi.em().persist(service);
        }
        return ok(Json.toJson("success"));
    }

}

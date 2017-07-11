package controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.*;
import play.Logger;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
    public Result getServices()
    {
        int vehicleID = Integer.parseInt(request().getQueryString("vehicleID"));

        @SuppressWarnings("unchecked")
        List<ServiceDetail> serviceList = jpaApi.em().createNativeQuery("SELECT s.service_id as id, st.type_name as name, s.mileage_interval as milesInterval, " +
                "st.rec_miles_interval as recMilesInterval, " +
                "s.miles_til_due as milesTilDue FROM service s " +
                "JOIN service_type st ON st.service_type_id = s.service_type_id " +
                "WHERE vehicle_id = :vehicleID AND tracked = 1 " +
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
                "WHERE v.vehicle_id = :id AND s.tracked = 1").setParameter("id", vehicleID)
                .getResultList();

        return ok(Json.toJson(trackedServiceTypes));
    }

    @Transactional
    public Result updateTrackedServices(Integer id)
    {
        JsonNode request = request().body().asJson();

        ObjectMapper mapper = new ObjectMapper();

        List<Integer> serviceTypes = Arrays.asList(mapper.convertValue(request, Integer[].class));

        List<Integer> newServiceTypes = new ArrayList<>();

        List<Integer> existingServices = jpaApi.em().createQuery("SELECT s.serviceTypeID " +
                "FROM Service s " +
                "WHERE vehicle_id = :id", Integer.class).setParameter("id", id)
                .getResultList();

        jpaApi.em().createNativeQuery("UPDATE Service s SET s.tracked = 2 WHERE vehicle_id = :id").setParameter("id", id).executeUpdate();

        for(int type : serviceTypes)
        {
            if(existingServices.contains(type))
            {
                jpaApi.em().createQuery("UPDATE Service s SET s.tracked = 1 WHERE service_type_id = :id")
                        .setParameter("id", type)
                        .executeUpdate();
            }
            else
            {
                ServiceType serviceType = jpaApi.em().createQuery("SELECT s FROM ServiceType s WHERE serviceTypeID = :id", ServiceType.class)
                        .setParameter("id", type)
                        .getSingleResult();

                Service service = new Service();

                service.setMilesInterval(serviceType.getRecommendedMilesinterval());
                service.setMilesTilDue(serviceType.getRecommendedMilesinterval());
                service.setVehicleID(id);
                service.setServiceTypeID(type);
                service.setTracked(1);

                jpaApi.em().persist(service);
            }
        }

        return ok(Json.toJson("success"));
    }

    @Transactional
    public Result logService() throws Exception
    {
        JsonNode request = request().body().asJson();
        boolean valid = true;
        JsonNode response = null;

        int vehicleID = request.findPath("vehicleID").asInt();
        int serviceID = request.findPath("serviceID").asInt();
        String shop = request.findPath("shop").textValue();
        BigDecimal partsCost = new BigDecimal(request.findPath("partsCost").textValue());
        BigDecimal laborCost = new BigDecimal(request.findPath("laborCost").textValue());
        BigDecimal totalCost = new BigDecimal(request.findPath("totalCost").textValue());
        String date = request.findPath("date").textValue();
        int odometerDifference = request.findPath("odometerDifference").asInt();

        if(valid)
        {
            CompletedService service = new CompletedService();

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date serviceDate = formatter.parse(date);

            service.setCompletedServiceDate(serviceDate);
            service.setLaborCost(laborCost);
            service.setPartsCost(partsCost);
            service.setTotalCost(totalCost);
            service.setShop(shop);
            service.setVehicleID(vehicleID);
            service.setServiceID(serviceID);

            int interval = (Integer)jpaApi.em().createNativeQuery("SELECT mileage_interval FROM service WHERE service_id = :id")
                    .setParameter("id", serviceID).getSingleResult();

            //Add the odometer difference to the interval so that when updateOdometer is called it zeroes out the difference
            int paddedInterval = interval + odometerDifference;

            jpaApi.em().createQuery("UPDATE Service s SET s.milesTilDue = :newMilesTilDue WHERE service_id = :id")
                    .setParameter("newMilesTilDue", paddedInterval)
                    .setParameter("id", serviceID)
                    .executeUpdate();

            jpaApi.em().persist(service);

            response = Json.toJson("service logged");
        }
        else
        {
            response= Json.toJson("Error logging service");
        }
        return ok(response);
    }

    @Transactional
    public Result getNextDue()
    {
        int userID = Integer.parseInt(session().get("userId"));

        try
        {
            NextDueResponse data = (NextDueResponse) jpaApi.em().createNativeQuery("SELECT v.vehicle_nickname as vehicleName, s.miles_til_due as milesTilDue, s.service_id as id, st.type_name serviceName FROM vehicle v JOIN service s ON v.vehicle_id = s.vehicle_id JOIN service_type st ON st.service_type_id = s.service_type_id WHERE v.user_id = :id AND s.tracked = 1 ORDER BY s.miles_til_due LIMIT 1", NextDueResponse.class).setParameter("id", userID).getSingleResult();

            return ok(Json.toJson(data));
        }catch(Exception e)
        {}

        return ok();
    }

    @Transactional
    public Result updateIntervals() throws Exception
    {
        JsonNode request = request().body().asJson();
        ObjectMapper mapper = new ObjectMapper();

        Interval[] intervals = mapper.convertValue(request, Interval[].class);

        for(Interval interval : intervals)
        {
            int id = interval.getServiceID();
            int serviceInterval = interval.getInterval();

            jpaApi.em().createQuery("UPDATE Service s SET s.milesInterval = :interval WHERE service_id = :id")
                    .setParameter("id", id)
                    .setParameter("interval", serviceInterval)
                    .executeUpdate();
        }

        return ok(Json.toJson("Intervals updated"));
    }
}

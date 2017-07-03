package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Service;
import models.ServiceDetail;
import models.Vehicle;
import play.Logger;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class OdometerController extends Controller
{
    private final JPAApi jpaApi;

    @Inject
    public OdometerController(JPAApi jpaApi)
    {
        this.jpaApi = jpaApi;
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result updateOdometer()
    {
        JsonNode request = request().body().asJson();
        boolean valid = true;
        List<String> errorList = new ArrayList<>();

        int vehicleID = request.findPath("vehicleID").asInt();

        Vehicle vehicle = jpaApi.em().createQuery("SELECT v FROM Vehicle v WHERE vehicleID = :id", Vehicle.class)
                .setParameter("id", vehicleID)
                .getSingleResult();

        List<Service> serviceList = jpaApi.em().createQuery("SELECT s FROM Service s WHERE vehicleID = :id")
                .setParameter("id", vehicleID)
                .getResultList();

        int currentReading = vehicle.getCurrentOdometer();
        int newReading = request.findPath("reading").asInt();
        int difference = newReading - currentReading;

        for(Service service : serviceList)
        {
            int serviceID = service.getServiceID();
            int newMilesTilDue = service.getMilesTilDue() - difference;

            jpaApi.em().createQuery("UPDATE Service s SET s.milesTilDue = :newMilesTilDue WHERE service_id = :id")
                    .setParameter("newMilesTilDue", newMilesTilDue)
                    .setParameter("id", serviceID)
                    .executeUpdate();
        }

        jpaApi.em().createQuery("UPDATE Vehicle v SET v.currentOdometer = :reading WHERE vehicle_id = :id")
                .setParameter("reading", newReading)
                .setParameter("id", vehicleID)
                .executeUpdate();

//TODO:send back an error if the new odometer reading is less than the old one

        if(valid)
        {
            return ok(Json.toJson(difference));
        }
        else
        {
            return ok(Json.toJson(errorList));
        }
    }

}

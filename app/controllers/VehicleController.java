package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeCreator;
import models.*;
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

public class VehicleController extends Controller
{
    private final JPAApi jpaApi;

    @Inject
    public VehicleController(JPAApi jpaApi)
    {
        this.jpaApi = jpaApi;
    }

    @Transactional
    public Result getVehicle()
    {
        int vehicleID = Integer.parseInt(request().getQueryString("vehicleID"));

        VehicleDetail vehicle = (VehicleDetail)jpaApi.em().createNativeQuery("SELECT v.vehicle_id as id, v.vehicle_nickname as nickname, v.current_odometer_reading as currentOdometer, v.engine as engine, v.model_year as modelYear, " +
                "mo.vehicle_model_id as modelID, mo.vehicle_model_name as model, ma.vehicle_make_id as makeID, ma.vehicle_make_name as make " +
                "FROM vehicle v " +
                "JOIN vehicle_model mo ON v.vehicle_model_id = mo.vehicle_model_id " +
                "JOIN vehicle_make ma ON mo.vehicle_make_id = ma.vehicle_make_id "  +
                "WHERE vehicle_id = :id ", VehicleDetail.class)
                .setParameter("id", vehicleID)
                .getSingleResult();

        return ok(Json.toJson(vehicle));
    }

    @Transactional
    public Result getVehicles()
    {
        int userId = Integer.parseInt(session().get("userId"));

        List<VehicleDetail> vehicles = jpaApi.em().createNativeQuery("SELECT v.vehicle_id as id, v.vehicle_nickname as nickname, v.current_odometer_reading as currentOdometer, v.engine as engine, v.model_year as modelYear, " +
                "mo.vehicle_model_id as modelID, mo.vehicle_model_name as model, ma.vehicle_make_id as makeID, ma.vehicle_make_name as make " +
                "FROM vehicle v " +
                "JOIN vehicle_model mo ON v.vehicle_model_id = mo.vehicle_model_id " +
                "JOIN vehicle_make ma ON mo.vehicle_make_id = ma.vehicle_make_id "  +
                "WHERE user_id = :id " +
                "ORDER BY v.vehicle_nickname", VehicleDetail.class)
                .setParameter("id", userId)
                .getResultList();

        return ok(Json.toJson(vehicles));
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result addVehicle()
    {
        //TODO: form validation
        boolean valid = true;

        List<String> errorList = new ArrayList<>();
        JsonNode request = request().body().asJson();

        Vehicle vehicle = new Vehicle();

        int userId = Integer.parseInt(session().get("userId"));
        int modelId = request.findPath("model").asInt();
        int year = request.findPath("year").asInt();
        int currentOdometer = request.findPath("odometerReading").asInt();
        String nickname = request.findPath("nickname").textValue();
        String engine = request.findPath("engine").textValue();

        vehicle.setUserID(userId);
        vehicle.setModelID(modelId);
        vehicle.setCurrentOdometer(currentOdometer);
        vehicle.setModelYear(year);
        vehicle.setNickname(nickname);
        vehicle.setEngine(engine);

        if(valid)
        {
            jpaApi.em().persist(vehicle);

            JsonNode serviceTypeIDs = request.get("services");

            for(JsonNode id : serviceTypeIDs)
            {
                ServiceType serviceType = jpaApi.em().createQuery("SELECT s FROM ServiceType s WHERE serviceTypeID = :id", ServiceType.class)
                        .setParameter("id", id.asInt())
                        .getSingleResult();

                Service service = new Service();
                service.setMilesInterval(serviceType.getRecommendedMilesinterval());
                service.setMilesTilDue(serviceType.getRecommendedMilesinterval());
                service.setVehicleID(vehicle.getVehicleID());
                service.setServiceTypeID(id.asInt());

                jpaApi.em().persist(service);
            }
            return ok(Json.toJson("success"));
        }
        else
        {
            return ok(Json.toJson(errorList));
        }
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result updateVehicle()
    {
        //TODO: form validation
        boolean valid = true;

        List<String> errorList = new ArrayList<>();
        JsonNode request = request().body().asJson();

        int vehicleId = request.findPath("id").asInt();
        int modelId = request.findPath("modelID").asInt();
        int year = request.findPath("modelYear").asInt();
        int currentOdometer = request.findPath("currentOdometer").asInt();
        String nickname = request.findPath("nickname").textValue();
        String engine = request.findPath("engine").textValue();

        if(valid)
        {
            jpaApi.em().createQuery("UPDATE Vehicle v SET " +
                    "v.currentOdometer = :reading, " +
                    "v.modelID = :modelID, " +
                    "v.modelYear = :modelYear, " +
                    "v.nickname = :nickname, " +
                    "v.engine = :engine " +
                    "WHERE v.vehicleID = :id")
                    .setParameter("reading", currentOdometer)
                    .setParameter("id", vehicleId)
                    .setParameter("modelID", modelId)
                    .setParameter("modelYear", year)
                    .setParameter("nickname", nickname)
                    .setParameter("engine", engine)
                    .executeUpdate();

            /*JsonNode serviceTypeIDs = request.get("services");

            for(JsonNode id : serviceTypeIDs)
            {
                ServiceType serviceType = jpaApi.em().createQuery("SELECT s FROM ServiceType s WHERE serviceTypeID = :id", ServiceType.class)
                        .setParameter("id", id.asInt())
                        .getSingleResult();

                Service service = new Service();
                service.setMilesInterval(serviceType.getRecommendedMilesinterval());
                service.setMilesTilDue(serviceType.getRecommendedMilesinterval());
                service.setVehicleID(vehicle.getVehicleID());
                service.setServiceTypeID(id.asInt());

                jpaApi.em().persist(service);
            }*/
            return ok(Json.toJson("success"));
        }
        else
        {
            return ok(Json.toJson(errorList));
        }
    }

}

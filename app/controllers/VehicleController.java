package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeCreator;
import models.*;
import models.Vehicle;
import play.Logger;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import validators.*;


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

        @SuppressWarnings("unchecked")
        List<VehicleDetail> vehicles = jpaApi.em().createNativeQuery("SELECT v.vehicle_id as id, v.vehicle_nickname as nickname, v.current_odometer_reading as currentOdometer, v.engine as engine, v.model_year as modelYear, " +
                "mo.vehicle_model_id as modelID, mo.vehicle_model_name as model, ma.vehicle_make_id as makeID, ma.vehicle_make_name as make " +
                "FROM vehicle v " +
                "JOIN vehicle_model mo ON v.vehicle_model_id = mo.vehicle_model_id " +
                "JOIN vehicle_make ma ON mo.vehicle_make_id = ma.vehicle_make_id "  +
                "WHERE user_id = :id AND v.tracked = 1 " +
                "ORDER BY v.vehicle_nickname", VehicleDetail.class)
                .setParameter("id", userId)
                .getResultList();

        return ok(Json.toJson(vehicles));
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result addVehicle()
    {
        boolean odometerValid = false;
        boolean nicknameValid = false;
        boolean engineValid = false;

        List<String> errorList = new ArrayList<>();
        JsonNode request = request().body().asJson();

        Vehicle vehicle = new Vehicle();

        int userId = Integer.parseInt(session().get("userId"));
        String modelId = request.findPath("model").asText();
        String year = request.findPath("year").asText();
        String currentOdometer = request.findPath("odometerReading").asText();
        String nickname = request.findPath("nickname").textValue();
        String engine = request.findPath("engine").textValue();

        if(modelId != null && year != null && currentOdometer != null && nickname != null && engine != null)
        {
            if(VehicleValidator.odometerValid(currentOdometer))
            {
                odometerValid = true;
            }
            else
            {
                errorList.add("Odometer must be a number between 0 and 5 million");
            }
            if(VehicleValidator.nicknameValid(nickname))
            {
                nicknameValid = true;
            }
            else
            {
                errorList.add("Vehicle nickname cannot exceed 20 characters");
            }
            if(VehicleValidator.engineValid(engine))
            {
                engineValid = true;
            }
            else
            {
                errorList.add("Engine description cannot exceed 20 characters");
            }
        }
        else
        {
            errorList.add("All fields marked with * are required");
        }

        if(odometerValid && nicknameValid && engineValid)
        {
            vehicle.setUserID(userId);
            vehicle.setModelID(Integer.parseInt(modelId));
            vehicle.setCurrentOdometer(Integer.parseInt(currentOdometer));
            vehicle.setModelYear(Integer.parseInt(year));
            vehicle.setNickname(nickname);
            vehicle.setEngine(engine);
            vehicle.setTracked(1);

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
                service.setTracked(1);

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
        boolean nicknameValid = false;
        boolean engineValid = false;

        List<String> errorList = new ArrayList<>();
        JsonNode request = request().body().asJson();

        int vehicleId = request.findPath("id").asInt();
        String modelId = request.findPath("modelID").asText();
        String year = request.findPath("modelYear").asText();
        String nickname = request.findPath("nickname").textValue();
        String engine = request.findPath("engine").textValue();

        if(modelId != null && year != null && nickname != null && engine != null)
        {
            if(VehicleValidator.nicknameValid(nickname))
            {
                nicknameValid = true;
            }
            else
            {
                errorList.add("Vehicle nickname cannot exceed 20 characters");
            }
            if(VehicleValidator.engineValid(engine))
            {
                engineValid = true;
            }
            else
            {
                errorList.add("Engine description cannot exceed 20 characters");
            }
        }
        else
        {
            errorList.add("All fields marked with * are required");
        }

        if(nicknameValid && engineValid)
        {
            jpaApi.em().createQuery("UPDATE Vehicle v SET " +
                    "v.modelID = :modelID, " +
                    "v.modelYear = :modelYear, " +
                    "v.nickname = :nickname, " +
                    "v.engine = :engine " +
                    "WHERE v.vehicleID = :id")
                    .setParameter("id", vehicleId)
                    .setParameter("modelID", Integer.parseInt(modelId))
                    .setParameter("modelYear", Integer.parseInt(year))
                    .setParameter("nickname", nickname)
                    .setParameter("engine", engine)
                    .executeUpdate();

            return ok(Json.toJson("success"));
        }
        else
        {
            return ok(Json.toJson(errorList));
        }
    }

    @Transactional
    public Result deleteVehicle(Integer id)
    {
        boolean valid = false;
        String response = "";

        int userID = Integer.parseInt(session().get("userId"));
        JsonNode request = request().body().asJson();

        User user = jpaApi.em().createQuery("SELECT u FROM User u WHERE userID = :id", User.class)
                .setParameter("id", userID)
                .getSingleResult();

        String password = user.getPassword();
        String inputPassword = request.findPath("password").textValue();

        if(password.equals(inputPassword))
        {
            valid = true;
        }

        if(valid)
        {
            /*jpaApi.em().createNativeQuery("DELETE FROM completed_service WHERE vehicle_id = :id").setParameter("id", id).executeUpdate();*/
            jpaApi.em().createQuery("UPDATE Service s SET s.tracked = 2 WHERE vehicle_id = :id").setParameter("id", id).executeUpdate();
            jpaApi.em().createNativeQuery("UPDATE Vehicle v SET v.tracked = 2 WHERE vehicle_id = :id").setParameter("id", id).executeUpdate();

            response = "success";
        }
        else
        {
            response = "fail";
        }

        return ok(Json.toJson(response));
    }

}

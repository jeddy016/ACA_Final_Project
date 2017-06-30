package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Vehicle;
import models.VehicleModel;
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
    @BodyParser.Of(BodyParser.Json.class)
    public Result addVehicle()
    {
        //TODO: form validation
        //TODO: figure out how to find the next service due

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
        vehicle.setNextServiceDue("Oil Change");
        vehicle.setNickname(nickname);
        vehicle.setEngine(engine);

        if(valid)
        {
            Logger.debug(vehicle.toString());

            jpaApi.em().persist(vehicle);
            return ok(Json.toJson("success"));
        }
        else
        {
            return ok(Json.toJson(errorList));
        }
    }

}

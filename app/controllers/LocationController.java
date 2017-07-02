package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.UserLocation;
import play.db.jpa.JPAApi;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

public class LocationController extends Controller
{
    private final JPAApi jpaApi;

    @Inject
    public LocationController(JPAApi jpaApi)
    {
        this.jpaApi = jpaApi;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result setUserLocation()
    {
        JsonNode request = request().body().asJson();
        Double lat = request.findPath("lat").asDouble();
        Double lng = request.findPath("lng").asDouble();

        session().put("lat", lat + "");
        session().put("lng", lng + "");

        return ok("user location set");
    }

    public Result getUserLocation()
    {
        Double lat = Double.parseDouble(session().get("lat"));
        Double lng = Double.parseDouble(session().get("lng"));

        UserLocation location = new UserLocation();
        location.setLat(lat);
        location.setLng(lng);

        return ok(Json.toJson(location));
    }

}

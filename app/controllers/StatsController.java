package controllers;

import models.TotalAndAVG;
import play.Logger;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class StatsController extends Controller
{
    private final JPAApi jpaApi;

    @Inject
    public StatsController(JPAApi jpaApi)
    {
        this.jpaApi = jpaApi;
    }

    @Transactional
    public Result getTotalAndAVG()
    {
        int userID = Integer.parseInt(session().get("userId"));
        int year = Calendar.getInstance().get(Calendar.YEAR);

        TotalAndAVG data = (TotalAndAVG)jpaApi.em().createNativeQuery("SELECT v.user_id as id, SUM(c.total_cost) as totalCost, AVG(c.total_cost) as avgCost FROM completed_service c JOIN vehicle v ON v.vehicle_id = c.vehicle_id WHERE v.user_id = :id AND YEAR(c.service_date) = :year ", TotalAndAVG.class)
                .setParameter("id", userID)
                .setParameter("year", year)
                .getSingleResult();

        return ok(Json.toJson(data));
    }

    @Transactional
    public Result getTotalCostByMonth()
    {
        int vehicleID = Integer.parseInt(request().getQueryString("vehicleID"));
        int year = Calendar.getInstance().get(Calendar.YEAR);

        List<Integer> values = jpaApi.em().createNativeQuery("SELECT MONTH(service_date) as month, SUM(total_cost) as total FROM completed_service c JOIN vehicle v on v.vehicle_id = c.vehicle_id WHERE v.vehicle_id = :id AND YEAR(c.service_date) = :year GROUP BY MONTH(c.service_date)")
                .setParameter("id", vehicleID)
                .setParameter("year", year)
                .getResultList();


        return ok(Json.toJson(values));
    }
}

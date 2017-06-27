package models;

//TODO: Delete comments when DB is wired up

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Service_Event")
public class ServiceEvent
{
    public static final int MAX_LENGTH_SERVICENAME = 80;
    public static final int MAX_LENGTH_MILES_INT = 7;
    public static final int MAX_LENGTH_DAYS_INT = 7;

    @Id
    @Column(name = "service_id")
    private int serviceID;

    @Column(name = "vehicle_id")
    private int vehicleID;

    @Column(name = "day_interval_id")
    private int dayIntervalID;

    @Column(name = "miles_interval_id")
    private int milesIntervalID;

    @Column(name = "service_name")
    private String name;

    @Column(name = "rec_days_interval")
    private int recommendedDaysInterval;

    @Column(name = "rec_miles_interval")
    private int recommendedMilesInterval;

    public int getServiceID()
    {
        return serviceID;
    }

    public void setServiceID(int serviceID)
    {
        this.serviceID = serviceID;
    }

    public int getVehicleID()
    {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID)
    {
        this.vehicleID = vehicleID;
    }

    public int getDayIntervalID()
    {
        return dayIntervalID;
    }

    public void setDayIntervalID(int dayIntervalID)
    {
        this.dayIntervalID = dayIntervalID;
    }

    public int getMilesIntervalID()
    {
        return milesIntervalID;
    }

    public void setMilesIntervalID(int milesIntervalID)
    {
        this.milesIntervalID = milesIntervalID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getRecommendedDaysInterval()
    {
        return recommendedDaysInterval;
    }

    public void setRecommendedDaysInterval(int recommendedDaysInterval)
    {
        this.recommendedDaysInterval = recommendedDaysInterval;
    }

    public int getRecommendedMilesInterval()
    {
        return recommendedMilesInterval;
    }

    public void setRecommendedMilesInterval(int recommendedMilesInterval)
    {
        this.recommendedMilesInterval = recommendedMilesInterval;
    }
}
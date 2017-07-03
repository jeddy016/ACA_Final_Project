package models;

//TODO: Delete comments when DB is wired up

import javax.persistence.*;

@Entity
@Table(name = "Service")
public class Service
{
    public static final int MAX_LENGTH_SERVICENAME = 80;
    public static final int MAX_LENGTH_MILES_INT = 7;
    public static final int MAX_LENGTH_DAYS_INT = 7;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "service_id")
    private int serviceID;

    @Column(name = "vehicle_id")
    private int vehicleID;

    @Column(name = "mileage_interval_id")
    private int milesIntervalID;

    @Column(name = "miles_til_due")
    private int milesTilDue;

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

    public int getMilesIntervalID()
    {
        return milesIntervalID;
    }

    public void setMilesIntervalID(int milesIntervalID)
    {
        this.milesIntervalID = milesIntervalID;
    }

    public int getMilesTilDue()
    {
        return milesTilDue;
    }

    public void setMilesTilDue(int milesTilDue)
    {
        this.milesTilDue = milesTilDue;
    }
}
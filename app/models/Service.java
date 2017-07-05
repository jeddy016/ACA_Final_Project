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

    @Column(name = "mileage_interval")
    private int milesInterval;

    @Column(name = "miles_til_due")
    private int milesTilDue;

    @Column(name = "service_type_id")
    private int serviceTypeID;

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

    public int getMilesInterval()
    {
        return milesInterval;
    }

    public void setMilesInterval(int milesInterval)
    {
        this.milesInterval = milesInterval;
    }

    public int getMilesTilDue()
    {
        return milesTilDue;
    }

    public void setMilesTilDue(int milesTilDue)
    {
        this.milesTilDue = milesTilDue;
    }

    public int getServiceTypeID()
    {
        return serviceTypeID;
    }

    public void setServiceTypeID(int serviceTypeID)
    {
        this.serviceTypeID = serviceTypeID;
    }
}
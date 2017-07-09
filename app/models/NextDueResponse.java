package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class NextDueResponse
{
    @Id
    @Column
    private int id;

    @Column
    private String vehicleName;

    @Column
    private String serviceName;

    @Column
    private int milesTilDue;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getVehicleName()
    {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName)
    {
        this.vehicleName = vehicleName;
    }

    public String getServiceName()
    {
        return serviceName;
    }

    public void setServiceName(String serviceName)
    {
        this.serviceName = serviceName;
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

package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EmailDetail
{
    @Id
    @Column
    private int id;

    @Column
    private String vehicleName;

    @Column
    private String serviceName;

    @Column
    private String milesTilDue;

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

    public String getMilesTilDue()
    {
        return milesTilDue;
    }

    public void setMilesTilDue(String milesTilDue)
    {
        this.milesTilDue = milesTilDue;
    }
}

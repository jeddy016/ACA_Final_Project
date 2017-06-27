package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Vehicle_Make")
public class VehicleMake
{
    private static int MAX_LENGTH_MAKE_NAME = 30;

    @Id
    @Column(name = "vehicle_make_id")
    private int VehicleMakeID;

    @Column(name = "vehicle_make_name")
    private String name;

    public int getVehicleMakeID()
    {
        return VehicleMakeID;
    }

    public void setVehicleMakeID(int vehicleMakeID)
    {
        VehicleMakeID = vehicleMakeID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}

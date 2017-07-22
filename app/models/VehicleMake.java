package models;

import javax.persistence.*;

@Entity
@Table(name = "Vehicle_Make")
public class VehicleMake
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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

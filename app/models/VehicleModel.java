package models;

import com.fasterxml.jackson.databind.JsonNode;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.*;

@Entity
@Table(name = "Vehicle_Model")
public class VehicleModel
{
    public static final int MAX_LENGTH_MODEL_NAME = 20;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "vehicle_model_id")
    private int vehicleModelID;

    @Column(name = "vehicle_make_id")
    private int vehicleMakeID;

    @Column(name = "vehicle_model_name")
    private String name;


    public int getVehicleModelID()
    {
        return vehicleModelID;
    }

    public void setVehicleModelID(int vehicleModelID)
    {
        this.vehicleModelID = vehicleModelID;
    }

    public int getVehicleMakeID()
    {
        return vehicleMakeID;
    }

    public void setVehicleMakeID(int vehicleMakeID)
    {
        this.vehicleMakeID = vehicleMakeID;
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

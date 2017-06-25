package models;

//TODO: Delete comments when DB is wired up

/*import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;*/

//@Entity
//@Table(name = "Vehicle_Model")
public class VehicleModel
{
    public static final int MAX_LENGTH_MODEL_NAME = 20;

    //@Id
    //@Column(name = "vehicle_model_id")
    private int vehicleModelID;

    //@Column(name = "vehicle_make_id")
    private int vehicleMakeID;

    //@Column(name = "engine_id")
    private int engineID;

    //@Column(name = "vehicle_model_name")
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

    public int getEngineID()
    {
        return engineID;
    }

    public void setEngineID(int engineID)
    {
        this.engineID = engineID;
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

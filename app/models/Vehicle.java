package models;

import javax.persistence.*;

@Entity
@Table(name = "Vehicle")
public class Vehicle
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    private int vehicleID;

    @Column(name = "vehicle_model_id")
    private int modelID;

    @Column(name = "model_year")
    private int modelYear;

    @Column(name = "user_id")
    private int userID;

    @Column(name = "vehicle_nickname")
    private String nickname;

    @Column(name = "current_odometer_reading")
    private int currentOdometer;

    @Column(name = "engine")
    private String engine;


    public int getVehicleID()
    {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID)
    {
        this.vehicleID = vehicleID;
    }

    public int getModelID()
    {
        return modelID;
    }

    public void setModelID(int modelID)
    {
        this.modelID = modelID;
    }

    public int getModelYear()
    {
        return modelYear;
    }

    public void setModelYear(int modelYear)
    {
        this.modelYear = modelYear;
    }

    public int getUserID()
    {
        return userID;
    }

    public void setUserID(int userID)
    {
        this.userID = userID;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public int getCurrentOdometer()
    {
        return currentOdometer;
    }

    public void setCurrentOdometer(int currentOdometer)
    {
        this.currentOdometer = currentOdometer;
    }

    public String getEngine()
    {
        return engine;
    }

    public void setEngine(String engine)
    {
        this.engine = engine;
    }
}
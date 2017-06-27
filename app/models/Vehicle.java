package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Vehicle")
public class Vehicle
{
    public static final int MAX_LENGTH_NICKNAME = 20;
    public static final int MAX_LENGTH_ODOMETER = 7;

    @Id
    @Column(name = "vehicle_id")
    private int vehicleID;

    @Column(name = "vehicle_model_id")
    private int modelID;

    @Column(name = "model_year_id")
    private int modelYear;

    @Column(name = "user_id")
    private int userID;

    @Column(name = "vehicle_nickname")
    private String nickname;

    @Column(name = "next_service_due")
    private String nextServiceDue;

    @Column(name = "current_odometer_reading")
    private int currentOdometer;


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

    public String getNextServiceDue()
    {
        return nextServiceDue;
    }

    public void setNextServiceDue(String nextServiceDue)
    {
        this.nextServiceDue = nextServiceDue;
    }

    public int getCurrentOdometer()
    {
        return currentOdometer;
    }

    public void setCurrentOdometer(int currentOdometer)
    {
        this.currentOdometer = currentOdometer;
    }
}
package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class VehicleDetail
{
    @Id
    @Column
    private int id;

    @Column
    private String nickname;

    @Column
    private int currentOdometer;

    @Column
    private String engine;

    @Column
    private int modelYear;

    @Column
    private String make;

    @Column
    private int makeID;

    @Column
    private String model;

    @Column
    private int modelID;


    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
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

    public int getModelYear()
    {
        return modelYear;
    }

    public void setModelYear(int modelYear)
    {
        this.modelYear = modelYear;
    }

    public String getMake()
    {
        return make;
    }

    public void setMake(String make)
    {
        this.make = make;
    }

    public int getMakeID()
    {
        return makeID;
    }

    public void setMakeID(int makeID)
    {
        this.makeID = makeID;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public int getModelID()
    {
        return modelID;
    }

    public void setModelID(int modelID)
    {
        this.modelID = modelID;
    }

}

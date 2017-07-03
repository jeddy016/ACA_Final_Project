package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class ServiceDetail
{
    @Id
    @Column
    private int id;

    @Column
    private String name;

    @Column
    private int milesInterval;

    @Column
    private int recMilesInterval;

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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getMilesInterval()
    {
        return milesInterval;
    }

    public void setMilesInterval(int mileageInterval)
    {
        this.milesInterval = mileageInterval;
    }

    public int getRecMilesInterval()
    {
        return recMilesInterval;
    }

    public void setRecMilesInterval(int recMilesInterval)
    {
        this.recMilesInterval = recMilesInterval;
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

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
    private int dayInterval;

    @Column
    private int mileageInterval;

    @Column
    private int recDaysInterval;

    @Column
    private int recMilesInterval;

    @Column
    private int daysTilDue;

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

    public int getDayInterval()
    {
        return dayInterval;
    }

    public void setDayInterval(int dayInterval)
    {
        this.dayInterval = dayInterval;
    }

    public int getMileageInterval()
    {
        return mileageInterval;
    }

    public void setMileageInterval(int mileageInterval)
    {
        this.mileageInterval = mileageInterval;
    }

    public int getRecDaysInterval()
    {
        return recDaysInterval;
    }

    public void setRecDaysInterval(int recDaysInterval)
    {
        this.recDaysInterval = recDaysInterval;
    }

    public int getRecMilesInterval()
    {
        return recMilesInterval;
    }

    public void setRecMilesInterval(int recMilesInterval)
    {
        this.recMilesInterval = recMilesInterval;
    }

    public int getDaysTilDue()
    {
        return daysTilDue;
    }

    public void setDaysTilDue(int daysTilDue)
    {
        this.daysTilDue = daysTilDue;
    }

    public int getMilesTilDue()
    {
        return milesTilDue;
    }

    public void setMilesTilDue(int milesTilDue)
    {
        this.milesTilDue = milesTilDue;
    }

    public int updateMilesTilDue (ServiceDetail service, int difference)
    {
        int currentValue = service.getMilesTilDue();
        return currentValue - difference;
    }
}

package models;

import javax.persistence.*;

@Entity
@Table(name = "Service_Mileage_Interval")
public class MileageInterval
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "mileage_interval_id")
    private int mileageIntervalID;

    @Column(name = "mileage_interval")
    private int interval;

    public int getMileageIntervalID()
    {
        return mileageIntervalID;
    }

    public void setMileageIntervalID(int mileageIntervalID)
    {
        this.mileageIntervalID = mileageIntervalID;
    }

    public int getInterval()
    {
        return interval;
    }

    public void setInterval(int interval)
    {
        this.interval = interval;
    }
}

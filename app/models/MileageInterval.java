package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Service_Mileage_Interval")
public class MileageInterval
{
    private static int MAX_LENGTH_INTERVAL = 6;

    @Id
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

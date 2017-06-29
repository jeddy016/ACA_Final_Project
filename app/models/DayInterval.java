package models;

import javax.persistence.*;

@Entity
@Table(name = "Service_Day_Interval")
public class DayInterval
{
    private static int MAX_LENGTH_INTERVAL = 5;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "day_interval_id")
    private int dayIntervalID;

    @Column(name = "day_interval")
    private int interval;

    public int getDayIntervalID()
    {
        return dayIntervalID;
    }

    public void setDayIntervalID(int dayIntervalID)
    {
        this.dayIntervalID = dayIntervalID;
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

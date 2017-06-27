package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Times_To_Text")
public class TimeToText
{
    private static int MAX_LENGTH_TIME = 4;

    @Id
    @Column(name = "time_id")
    private int timeID;

    @Column(name = "user_id")
    private int userID;

    @Column(name = "time")
    private int time;

    public int getTimeID()
    {
        return timeID;
    }

    public void setTimeID(int timeID)
    {
        this.timeID = timeID;
    }

    public int getUserID()
    {
        return userID;
    }

    public void setUserID(int userID)
    {
        this.userID = userID;
    }

    public int getTime()
    {
        return time;
    }

    public void setTime(int time)
    {
        this.time = time;
    }
}
package models;

//TODO: Delete comments when DB is wired up

/*import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;*/

//@Entity
//@Table(name = "Days_To_Text")
public class DaysToText
{
    public static final int MAX_LENGTH_DAY_NAME = 10;

    //@Id
    //@Column(name = "day_id")
    private int dayID;

    //@Column(name = "user_id")
    private int userID;

    //@Column(name = "day_name")
    private String dayName;

    public int getDayID()
    {
        return dayID;
    }

    public void setDayID(int dayID)
    {
        this.dayID = dayID;
    }

    public int getUserID()
    {
        return userID;
    }

    public void setUserID(int userID)
    {
        this.userID = userID;
    }

    public String getDayName()
    {
        return dayName;
    }

    public void setDayName(String dayName)
    {
        this.dayName = dayName;
    }
}

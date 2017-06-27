package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "User")
public class User
{
    public static final int MAX_LENGTH_FIRSTNAME = 20;
    public static final int MAX_LENGTH_LASTNAME = 30;
    public static final int MAX_LENGTH_EMAIL = 40;
    public static final int MAX_LENGTH_PASSWORD = 20;

    @Id
    @Column(name = "user_id")
    private int userID;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_email")
    private String email;

    @Column(name = "notifications_opt_in")
    private Boolean notificationsOptIn;

    @Column(name = "notifications_hour")
    private int notificationsHour;

    @Column(name = "notifications_days_ahead")
    private int notificationsDaysAhead;

    public int getUserID()
    {
        return userID;
    }

    public void setUserID(int userID)
    {
        this.userID = userID;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Boolean getNotificationsOptIn()
    {
        return notificationsOptIn;
    }

    public void setNotificationsOptIn(Boolean notificationsOptIn)
    {
        this.notificationsOptIn = notificationsOptIn;
    }

    public int getNotificationsHour()
    {
        return notificationsHour;
    }

    public void setNotificationsHour(int notificationsHour)
    {
        this.notificationsHour = notificationsHour;
    }

    public int getNotificationsDaysAhead()
    {
        return notificationsDaysAhead;
    }

    public void setNotificationsDaysAhead(int notificationsDaysAhead)
    {
        this.notificationsDaysAhead = notificationsDaysAhead;
    }
}

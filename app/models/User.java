package models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "User")
public class User
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userID;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "notifications_opt_in")
    private int notificationsOptIn;

    @Column(name = "notifications_miles_ahead")
    private int notificationsMilesAhead;

    @Column(name = "last_notified")
    private LocalDate lastNotified;

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

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public int getNotificationsOptIn()
    {
        return notificationsOptIn;
    }

    public void setNotificationsOptIn(int notificationsOptIn)
    {
        this.notificationsOptIn = notificationsOptIn;
    }

    public int getNotificationsMilesAhead()
    {
        return notificationsMilesAhead;
    }

    public void setNotificationsMilesAhead(int notificationsDaysAhead)
    {
        this.notificationsMilesAhead = notificationsDaysAhead;
    }

    public LocalDate getLastNotified()
    {
        return lastNotified;
    }

    public void setLastNotified(LocalDate lastNotified)
    {
        this.lastNotified = lastNotified;
    }
}

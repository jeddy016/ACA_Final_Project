package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Odometer_History")
public class OdometerHistory
{
    private static int MAX_LENGTH_READING = 7;

    @Id
    @Column(name = "odometer_id")
    private int odometerID;

    @Column(name = "vehicle_id")
    private int vehicleID;

    @Column(name = "odometer_reading")
    private int reading;

    public int getOdometerID()
    {
        return odometerID;
    }

    public void setOdometerID(int odometerID)
    {
        this.odometerID = odometerID;
    }

    public int getVehicleID()
    {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID)
    {
        this.vehicleID = vehicleID;
    }

    public int getReading()
    {
        return reading;
    }

    public void setReading(int reading)
    {
        this.reading = reading;
    }
}

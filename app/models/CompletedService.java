package models;

//TODO: Delete comments when DB is wired up

/*import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;*/

import java.math.BigDecimal;

//@Entity
//@Table(name = "Completed_Service")
public class CompletedService
{
    public static final int MAX_LENGTH_SERVICEDATE = 8;
    public static final int MAX_LENGTH_TOTAL_COST = 10;
    public static final int MAX_LENGTH_PARTS_COST = 10;
    public static final int MAX_LENGTH_LABOR_COST = 10;

    //@Id
    //@Column(name = "completed_service_id")
    private int completedServiceID;

    //@Column(name = "vehicle_id")
    private int vehicleID;

    //@Column(name = "service_event_id")
    private int serviceEventID;

    //@Column(name = "shop_id")
    private int shopID;

    //TODO: Make this a date type somehow
    //@Column(name = "service_date")
    private int date;

    //@Column(name = "total_cost")
    private BigDecimal totalCost;

    //@Column(name = "labor_cost")
    private BigDecimal laborCost;

    //@Column(name = "parts_cost")
    private BigDecimal partsCost;


    public int getCompletedServiceID()
    {
        return completedServiceID;
    }

    public void setCompletedServiceID(int completedServiceID)
    {
        this.completedServiceID = completedServiceID;
    }

    public int getVehicleID()
    {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID)
    {
        this.vehicleID = vehicleID;
    }

    public int getServiceEventID()
    {
        return serviceEventID;
    }

    public void setServiceEventID(int serviceEventID)
    {
        this.serviceEventID = serviceEventID;
    }

    public int getShopID()
    {
        return shopID;
    }

    public void setShopID(int shopID)
    {
        this.shopID = shopID;
    }

    public int getDate()
    {
        return date;
    }

    public void setDate(int date)
    {
        this.date = date;
    }

    public BigDecimal getTotalCost()
    {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost)
    {
        this.totalCost = totalCost;
    }

    public BigDecimal getLaborCost()
    {
        return laborCost;
    }

    public void setLaborCost(BigDecimal laborCost)
    {
        this.laborCost = laborCost;
    }

    public BigDecimal getPartsCost()
    {
        return partsCost;
    }

    public void setPartsCost(BigDecimal partsCost)
    {
        this.partsCost = partsCost;
    }
}

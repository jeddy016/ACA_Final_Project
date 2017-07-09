package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TotalAndAVG
{
    @Id
    @Column
    private int id;

    @Column
    private double totalCost;

    @Column
    private double avgCost;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public double getTotalCost()
    {
        return totalCost;
    }

    public void setTotalCost(double totalCost)
    {
        this.totalCost = totalCost;
    }

    public double getAvgCost()
    {
        return avgCost;
    }

    public void setAvgCost(double avgCost)
    {
        this.avgCost = avgCost;
    }
}

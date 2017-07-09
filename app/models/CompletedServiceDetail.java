package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CompletedServiceDetail
{
    @Id
    @Column
    private int id;

    @Column
    private String name;

    @Column
    private String date;

    @Column
    private String totalCost;

    @Column
    private String partsCost;

    @Column
    private String laborCost;

    @Column
    private String shop;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getTotalCost()
    {
        return totalCost;
    }

    public void setTotalCost(String totalCost)
    {
        this.totalCost = totalCost;
    }

    public String getPartsCost()
    {
        return partsCost;
    }

    public void setPartsCost(String partsCost)
    {
        this.partsCost = partsCost;
    }

    public String getLaborCost()
    {
        return laborCost;
    }

    public void setLaborCost(String laborCost)
    {
        this.laborCost = laborCost;
    }

    public String getShop()
    {
        return shop;
    }

    public void setShop(String shop)
    {
        this.shop = shop;
    }
}

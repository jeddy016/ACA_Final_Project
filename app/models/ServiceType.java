package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="service_type")
public class ServiceType
{
    @Id
    @Column(name="service_type_id")
    private int serviceTypeID;

    @Column(name="type_name")
    private String typeName;

    @Column(name="rec_miles_interval")
    private int recMilesInterval;

    public int getServiceTypeID()
    {
        return serviceTypeID;
    }

    public void setServiceTypeID(int serviceTypeID)
    {
        this.serviceTypeID = serviceTypeID;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public int getRecommendedMilesinterval()
    {
        return recMilesInterval;
    }

    public void setRecommendedMilesinterval(int recommendedMilesinterval)
    {
        this.recMilesInterval = recommendedMilesinterval;
    }
}

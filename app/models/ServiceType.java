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
}

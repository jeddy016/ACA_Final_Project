package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "serviceID",
        "interval"
})
public class Interval
{

    @JsonProperty("serviceID")
    private int serviceID;

    @JsonProperty("interval")
    private String miles;


    public int getServiceID()
    {
        return serviceID;
    }

    public void setServiceID(int serviceID)
    {
        this.serviceID = serviceID;
    }

    public String getMiles()
    {
        return miles;
    }

    public void setMiles(String miles)
    {
        this.miles = miles;
    }
}

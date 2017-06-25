package models;

//TODO: Delete comments when DB is wired up

/*import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;*/

//@Entity
//@Table(name = "Engine")
public class Engine
{
    private static int MAX_LENGTH_ENGINE_NAME = 10;

    //@Id
    //@Column(name = "engine_id")
    private int engineID;

    //@Column(name = "engine_name")
    private String name;

    public int getEngineID()
    {
        return engineID;
    }

    public void setEngineID(int engineID)
    {
        this.engineID = engineID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}

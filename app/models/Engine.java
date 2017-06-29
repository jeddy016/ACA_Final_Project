package models;

import javax.persistence.*;

@Entity
@Table(name = "Engine")
public class Engine
{
    private static int MAX_LENGTH_ENGINE_NAME = 10;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "engine_id")
    private int engineID;

    @Column(name = "engine_name")
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

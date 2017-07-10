package models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CostByService
{
    @Id
    @Column
    private String name;

    @Column
    private double cost;
}

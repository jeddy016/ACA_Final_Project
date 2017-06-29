package models;

import javax.persistence.*;

@Entity
@Table(name = "Model_Year")
public class ModelYear
{
    private static int MAX_LENGTH_YEAR = 4;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "model_year_id")
    private int modelYearID;

    @Column(name = "model_year")
    private int year;

    public int getModelYearID()
    {
        return modelYearID;
    }

    public void setModelYearID(int modelYearID)
    {
        this.modelYearID = modelYearID;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }
}

package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Model_Year")
public class ModelYear
{
    private static int MAX_LENGTH_YEAR = 4;

    @Id
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

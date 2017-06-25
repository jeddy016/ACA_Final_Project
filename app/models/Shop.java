package models;

//TODO: Delete comments when DB is wired up

/*import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;*/

//@Entity
//@Table(name = "Shop")
public class Shop
{
    private static int MAX_LENGTH_SHOP_NAME = 50;

    //@Id
    //@Column(name = "shop_id")
    private int shopID;

    //@Column(name = "shop_name")
    private String name;

    public int getShopID()
    {
        return shopID;
    }

    public void setShopID(int shopID)
    {
        this.shopID = shopID;
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

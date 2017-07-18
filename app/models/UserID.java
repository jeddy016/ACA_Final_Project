package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserID
{
    @Id
    private int ID;

    private byte[] password;
    private byte[] salt;

    public int getID()
    {
        return ID;
    }

    public byte[] getPassword()
    {
        return password;
    }

    public byte[] getSalt()
    {
        return salt;
    }
}
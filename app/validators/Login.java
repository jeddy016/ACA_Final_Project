package validators;


public class Login
{
    public static boolean passwordInvalid(String password)
    {
        boolean invalid = true;

        if (password.equals("admin"))
        {
            invalid = false;
        }
        return invalid;
    }

    public static boolean emailInvalid(String email)
    {
        boolean invalid = true;

        if (email.equals("admin"))
        {
            invalid = false;
        }
        return invalid;
    }
}

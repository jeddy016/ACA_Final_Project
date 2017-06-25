package validators;


public class Login
{
    //TODO: Write validation checks for email/pwd currently hard-coded

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

        if (email.equals("demo@example.com"))
        {
            invalid = false;
        }
        return invalid;
    }
}

package validators;

import play.Logger;

public class NewUser
{
    public static boolean emailInvalid(String email)
    {
        boolean invalid = true;

        //TODO: find out how to validate email addresses
        if (email.matches("demo@example.com"))
        {
            invalid = false;
        }
        return invalid;
    }

    public static boolean passwordInvalid(String password)
    {
        boolean invalid = true;

        if (password.length() >= 8 && password.matches(".*\\d+.*"))
        {
            invalid = false;
        }
        return invalid;
    }

    public static boolean passwordsMatch(String password, String confirm)
    {
        boolean valid = true;

        if (!password.equals(confirm))
        {
            valid = false;
        }

        return valid;
    }

    public static boolean alreadyExists(String email)
    {
        boolean valid = false;

        //TODO: check DB to see if email already exists for another user

        return valid;
    }
}

package validators;

import play.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewUser
{
    private static final int MAX_LENGTH_NAME = 20;
    private static final int MAX_LENGTH_EMAIL = 40;
    private static final int MAX_LENGTH_PASSWORD = 20;
    private static final int MAX_MILES = 65000;

    public static boolean emailInvalid(String email)
    {
        boolean invalid = false;
        final Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);

        if(!matcher.find())
        {
            invalid = true;
        }
        if(email.length() > MAX_LENGTH_EMAIL)
        {
            invalid = true;
        }

        return invalid;
    }

    public static boolean passwordInvalid(String password)
    {
        boolean invalid = true;

        if (password.length() >= 8 && password.matches(".*\\d+.*") && password.length() <= MAX_LENGTH_PASSWORD)
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

    public static boolean nameValid(String name)
    {
        boolean valid = false;

        String regx = "^[\\p{L} .'-]+$";
        Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);

        if(name.length() > 0 && name.length() <= MAX_LENGTH_NAME && matcher.find())
        {
            valid = true;
        }

        return valid;
    }

    public static boolean milesValid(String miles)
    {
        boolean valid = false;

        try
        {
            int mileTest = Integer.parseInt(miles);

            if(mileTest > 0 && mileTest <= MAX_MILES)
            {
                valid = true;
            }

        }catch (NumberFormatException e)
        {
            Logger.error("Miles entered not a number");
        }

        return valid;
    }
}

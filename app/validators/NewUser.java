package validators;

import play.db.jpa.JPAApi;

import javax.inject.Inject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewUser
{
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
}

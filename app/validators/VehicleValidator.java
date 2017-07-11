package validators;


import play.Logger;

public class VehicleValidator
{
    private static final int MAX_LENGTH_NICKNAME = 20;
    private static final int MAX_ODOMETER = 5000000;
    private static final int MAX_LENGTH_ENGINE = 20;

    public static boolean odometerValid(String odometer)
    {
        boolean valid = false;

        try
        {
            int odometerTest = Integer.parseInt(odometer);

            if(odometerTest <= MAX_ODOMETER)
            {
                valid = true;
            }

        }catch (NumberFormatException e)
        {
            Logger.error("Odometer format error");
        }

        return valid;
    }

    public static boolean nicknameValid(String nickname)
    {
        boolean valid = false;

        if(nickname != null && nickname.length() <= MAX_LENGTH_NICKNAME)
        {
            valid = true;
        }

        return valid;
    }

    public static boolean engineValid(String engine)
    {
        boolean valid = false;

        if(engine != null && engine.length() <= MAX_LENGTH_ENGINE)
        {
            valid = true;
        }

        return valid;
    }
}

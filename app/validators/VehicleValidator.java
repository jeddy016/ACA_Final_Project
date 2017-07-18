package validators;


import play.Logger;

public class VehicleValidator
{
    private static final int MAX_LENGTH_NICKNAME = 20;
    private static final int MAX_ODOMETER = 2000000;
    private static final int MAX_LENGTH_ENGINE = 20;

    public static boolean odometerValid(String odometer)
    {
        boolean valid = false;

        try
        {
            int odometerTest = Integer.parseInt(odometer);

            if(odometerTest > 0 && odometerTest <= MAX_ODOMETER)
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
        boolean valid = true;

        if(engine != null)
        {
            if (engine.length() > MAX_LENGTH_ENGINE)
            {
                valid = false;
            }
        }

        return valid;
    }
}

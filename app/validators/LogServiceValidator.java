package validators;


import play.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class LogServiceValidator
{

    private static final int SHOP_MAX_LENGTH = 80;
    private static BigDecimal COST_MAX_VALUE = new BigDecimal(99999.99);

    public static boolean dateValid(String date)
    {
        boolean valid = false;

        try
        {
            LocalDate serviceDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            if (!serviceDate.atStartOfDay().isAfter(LocalDateTime.now()))
            {
                valid = true;
            }
        }catch(Exception e){}

        return valid;
    }

    public static boolean shopValid(String shop)
    {
        boolean valid = false;

        if(shop != null && shop.length() < SHOP_MAX_LENGTH)
        {
            valid = true;
        }

        return valid;
    }

    public static boolean costsValid(String total, String labor, String parts)
    {
        boolean valid = false;

        try
        {
            BigDecimal totalCost = new BigDecimal(total).setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal partsCost = new BigDecimal(parts).setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal laborCost = new BigDecimal(labor).setScale(2, BigDecimal.ROUND_HALF_UP);

            if(!(laborCost.add(partsCost).compareTo(totalCost) == 1))
            {
                if(totalCost.compareTo(COST_MAX_VALUE) == -1 && partsCost.compareTo(COST_MAX_VALUE) == -1 && laborCost.compareTo(COST_MAX_VALUE) == -1)
                {
                    valid = true;
                }
            }
        }catch(Exception e)
        {

        }
        return valid;
    }
}

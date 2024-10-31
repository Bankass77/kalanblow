package ml.kalanblow.gestiondesinscriptions.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static final SimpleDateFormat sdf = new SimpleDateFormat();

    public static Date today() {
        return new Date();

    }

    /**
     * @return today's date as yyyy-MM-dd format
     */
    public static String todayStr() {

        return sdf.format(today());

    }

    /**
     * @param date
     * @return
     */
    public static String formattedDate(Date date) {

        return date != null ? sdf.format(date) : todayStr();

    }
}

package scorpio.scorpioblog.utils;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String formatDate(Date date){

        return sf1.format(date);
    }

    public static String formatDateALL(Date date){

        return sf2.format(date);
    }
}
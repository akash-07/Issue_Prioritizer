package features;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by @kash on 2/18/2018.
 */
public class Test {
    public static void main(String[] args)  {
        Calendar d1 = Calendar.getInstance();
        Calendar d2 = Calendar.getInstance();
        d1.set(2015, 10, 10);
        d2.set(2015, 11, 10);
        long end = d2.getTimeInMillis();
        long start = d1.getTimeInMillis();
        long days = TimeUnit.MILLISECONDS.toDays(Math.abs(end-start));
        System.out.println(days);
        System.out.println(d1.toString());
        String s = "aas'as'asd";
        s = s.replaceAll("[']", "!");
        System.out.println(s);
        Calendar date = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        date.setTimeInMillis(date.getTimeInMillis() - 86400000);
        int hrs = date.get(Calendar.HOUR_OF_DAY);
        int min = date.get(Calendar.MINUTE);
        int sec = date.get(Calendar.SECOND);
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);   month += 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        System.out.println(hrs + ":" + min + ":" + sec);
        System.out.println(year + "-" + month + "-" + day);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        System.out.println(format.format(date.getTime()));
    }
}

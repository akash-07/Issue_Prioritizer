package features;

import java.util.Calendar;
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
        s = s.replaceAll("[']", "\'");
        System.out.println(s);
    }
}

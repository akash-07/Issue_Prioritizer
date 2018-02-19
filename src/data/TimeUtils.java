package data;

/**
 * Created by @kash on 2/20/2018.
 */
public class TimeUtils {
    static long getMillis(String since) {
        long sec = 1000;
        if(since.equals("pastmin"))
            return 60*sec;
        else if(since.equals("past10min"))
            return 10*60*sec;
        else if(since.equals("pasthour"))
            return 60*60*sec;
        else if((since.equals("pastday")))
            return 24*60*60*sec;
        else
            throw new Error("incorrect string for getMillis: " + since);
    }
}

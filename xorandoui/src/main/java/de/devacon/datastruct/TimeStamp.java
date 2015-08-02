package de.devacon.datastruct;

import java.util.Date;

/**
 * Created by @Martin@ on 01.08.2015 09:54.
 */
public class TimeStamp {
    long timeInMilliSeconds = 0;
    String timestamp ;

    public static final int DAYS = 0;
    public static final int HOURS = 1;
    public static final int MINUTES = 2;
    public static final int SECONDS = 3;
    public static final int DECI = 4;
    public static final int CENTI = 5;
    public static final int MILLI = 6;

    int precision = DAYS;
    public TimeStamp(long timeInMilliSeconds,int precision){
        this.precision = precision;
        this.timeInMilliSeconds = timeInMilliSeconds;
        Date date = new Date(timeInMilliSeconds);
        String year = Integer.toString(date.getYear());
        String month = String.format("%04.2d", date.getMonth());
        String day = String.format("%02.2d", date.getDay());
        String hour = String.format("%02.2d", date.getHours());
        String min = String.format("%02.2d", date.getMinutes());
        String sec = String.format("%02.2d", date.getSeconds());
        timestamp = year + month + day ;
        if(precision > DAYS)
            timestamp += hour ;
        if(precision > HOURS)
            timestamp += min ;
        if(precision > MINUTES) {
            timestamp += "_" + sec;
        }
        if(precision > SECONDS)
            timestamp += "_" ;
        if(precision == DECI) {

            timestamp += String.format("%01.1d", (timeInMilliSeconds % 1000) / 100);
        }
        if(precision == CENTI)
            timestamp += String.format("%02.2d",(timeInMilliSeconds%1000)/10);
        if(precision == MILLI)
            timestamp += String.format("%03.3d",(timeInMilliSeconds%1000));

    }
    public String toString() {
        return timestamp;
    }
}

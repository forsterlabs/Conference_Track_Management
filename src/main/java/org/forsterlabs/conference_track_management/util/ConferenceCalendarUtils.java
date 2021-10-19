package org.forsterlabs.conference_track_management.util;

import org.forsterlabs.conference_track_management.entity.ConferenceSession;

import java.util.Calendar;

public class ConferenceCalendarUtils {

    public static Calendar getCalendar(int hour, int minute){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        return cal;
    }

    public static Calendar getNextStartTime(Calendar currentStartTime, int sessionLength) {
        Calendar newTime = Calendar.getInstance();
        newTime.set(Calendar.HOUR_OF_DAY, currentStartTime.get(Calendar.HOUR_OF_DAY));
        newTime.set(Calendar.MINUTE, currentStartTime.get(Calendar.MINUTE));
        newTime.add(Calendar.MINUTE, sessionLength);
        return newTime;
    }
}

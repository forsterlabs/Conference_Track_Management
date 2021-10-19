package org.forsterlabs.conference_track_management.config;

import org.forsterlabs.conference_track_management.entity.TrackSession;
import org.forsterlabs.conference_track_management.util.ConferenceCalendarUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ConferenceTrackConfig {
    public static final SimpleDateFormat HOUR_AMPM_FORMAT = new SimpleDateFormat("hh:mm a");

    public static final String LIGHTNING_KEYWORD = "lightning";
    public static final int LIGHTNING_TALK_LENGTH = 5;

    public static final Calendar LUNCH_START = ConferenceCalendarUtils.getCalendar(12,0);
    public static final Calendar NETWORK_START = ConferenceCalendarUtils.getCalendar(17, 0);
    public static final Calendar MORNING_SESSION_START = ConferenceCalendarUtils.getCalendar(9,0);
    public static final Calendar AFTERNOON_SESSION_START = ConferenceCalendarUtils.getCalendar(13,0);


    public static final TrackSession LUNCH_SESSION = new TrackSession("Lunch", LUNCH_START, AFTERNOON_SESSION_START, 60);
    public static final TrackSession NETWORK_SESSION = new TrackSession("Networking Event", NETWORK_START, null, 60);
}

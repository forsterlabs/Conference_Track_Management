package org.forsterlabs.conference_track_management.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrackSession {

    private String sessiongTitle = "";
    private Calendar startTime;
    private Calendar endTime;
    private int sessionLength;

}

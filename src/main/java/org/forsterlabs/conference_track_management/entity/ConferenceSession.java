package org.forsterlabs.conference_track_management.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConferenceSession {
    private String sessionName;
    private int sessionLength;

    private boolean isNotInTrack = true;
}

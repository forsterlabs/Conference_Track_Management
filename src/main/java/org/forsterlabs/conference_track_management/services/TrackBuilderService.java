package org.forsterlabs.conference_track_management.services;

import org.forsterlabs.conference_track_management.config.ConferenceTrackConfig;
import org.forsterlabs.conference_track_management.entity.ConferenceSession;
import org.forsterlabs.conference_track_management.entity.TrackSession;
import org.forsterlabs.conference_track_management.util.ConferenceCalendarUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TrackBuilderService {

    private Calendar previousEndTime = null;

    public Map<Integer, List<TrackSession>> buildTracks(List<ConferenceSession> conferenceSessions) {

        int trackCounter = 1;
        HashMap<Integer, List<TrackSession>> trackMap = new HashMap<>();
        while (conferenceSessions.stream().anyMatch(ConferenceSession::isNotInTrack)) {
            trackMap.put(trackCounter, new ArrayList<>());
            // create and add morning sessions
            addMorningSessions(conferenceSessions, trackMap.get(trackCounter));

            // add lunch
            trackMap.get(trackCounter).add(ConferenceTrackConfig.LUNCH_SESSION);

            // create and add afternoon sessions
            addAfternoonSessions(conferenceSessions, trackMap.get(trackCounter));

            // add networking session
            trackMap.get(trackCounter).add(ConferenceTrackConfig.NETWORK_SESSION);
            trackCounter += 1;
        }
        return trackMap;
    }

    private boolean canAddSession(TrackSession trackSession) {
        if (trackSession.getEndTime().before(ConferenceTrackConfig.LUNCH_START)
                || trackSession.getStartTime().equals(ConferenceTrackConfig.AFTERNOON_SESSION_START)
                || (trackSession.getStartTime().after(ConferenceTrackConfig.AFTERNOON_SESSION_START)
                && trackSession.getEndTime().before(ConferenceTrackConfig.NETWORK_START))) {
            return true;
        }
        return false;
    }

    private void addMorningSessions(List<ConferenceSession> conferenceSessions, List<TrackSession> sessions) {
        previousEndTime = ConferenceTrackConfig.MORNING_SESSION_START;
        addSessions(conferenceSessions, sessions);
    }

    private void addAfternoonSessions(List<ConferenceSession> conferenceSessions, List<TrackSession> sessions) {
        previousEndTime = ConferenceTrackConfig.AFTERNOON_SESSION_START;
        addSessions(conferenceSessions, sessions);
    }

    private void addSessions(List<ConferenceSession> conferenceSessions, List<TrackSession> sessions) {
        conferenceSessions.stream().filter(ConferenceSession::isNotInTrack).forEach(conferenceSession -> {
            TrackSession trackSession = new TrackSession();
            trackSession.setSessiongTitle(conferenceSession.getSessionName());
            trackSession.setSessionLength(conferenceSession.getSessionLength());
            trackSession.setStartTime(previousEndTime);
            trackSession.setEndTime(ConferenceCalendarUtils.getNextStartTime(previousEndTime, conferenceSession.getSessionLength()));
            if (canAddSession(trackSession)) {
                conferenceSession.setNotInTrack(false);
                sessions.add(trackSession);
                previousEndTime = trackSession.getEndTime();
            }
        });
    }

}

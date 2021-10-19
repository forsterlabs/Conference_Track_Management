package org.forsterlabs.conference_track_management.services;

import org.forsterlabs.conference_track_management.config.ConferenceTrackConfig;
import org.forsterlabs.conference_track_management.entity.ConferenceSession;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class SessionFileReaderService {

    public List<ConferenceSession> readConferenceSessionFile(File sessionFile) throws IOException {
        List<ConferenceSession> sessions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(sessionFile))) {
            String sessionInformation;
            while ((sessionInformation = br.readLine()) != null) {
                sessions.add(readInputLineToConferenceSession(sessionInformation));
            }
        }

        return sessions;
    }

    public ConferenceSession readInputLineToConferenceSession(String inputLine) {
        ConferenceSession conferenceSession = new ConferenceSession();
        if (inputLine.endsWith(ConferenceTrackConfig.LIGHTNING_KEYWORD)) {
            conferenceSession.setSessionLength(ConferenceTrackConfig.LIGHTNING_TALK_LENGTH);
            conferenceSession.setSessionName(inputLine.replace(" lightning", ""));
        } else {
            String[] sessionInfo = inputLine.split("\\d+min");
            conferenceSession.setSessionName(sessionInfo[0].trim());
            conferenceSession.setSessionLength(Integer.valueOf(inputLine.substring(conferenceSession.getSessionName().length() + 1).replace("min", "").trim()));
        }
        return conferenceSession;
    }
}

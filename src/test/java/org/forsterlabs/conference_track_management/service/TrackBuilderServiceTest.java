package org.forsterlabs.conference_track_management.service;

import org.forsterlabs.conference_track_management.entity.ConferenceSession;
import org.forsterlabs.conference_track_management.entity.TrackSession;
import org.forsterlabs.conference_track_management.services.TrackBuilderService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@ActiveProfiles("test")
@SpringBootTest
public class TrackBuilderServiceTest {

    @Autowired
    private TrackBuilderService trackBuilderService;

    @Test
    public void testTrackBuilder(){
        List<ConferenceSession> testSessions = getTestSessions();
        Map<Integer, List<TrackSession>> tracks = trackBuilderService.buildTracks(testSessions);

        Assert.assertEquals(2, tracks.size());
        Assert.assertEquals("Proper Unit Tests for Anyone", tracks.get(1).get(0).getSessiongTitle());
        Assert.assertEquals(9, tracks.get(1).get(0).getStartTime().get(Calendar.HOUR_OF_DAY));


        tracks.keySet().forEach(integer -> {
            Assert.assertEquals(1, tracks.get(integer).stream().filter(session ->
                session.getStartTime().get(Calendar.HOUR_OF_DAY) == 12
            ).count());

            Assert.assertEquals(1, tracks.get(integer).stream().filter(session ->
                    session.getStartTime().get(Calendar.HOUR_OF_DAY) == 17
            ).count());

            Assert.assertTrue(tracks.get(integer).stream().filter(session ->
                    session.getStartTime().get(Calendar.HOUR_OF_DAY) < 12
            ).count() >= 2);

            Assert.assertTrue(tracks.get(integer).stream().filter(session ->
                    session.getStartTime().get(Calendar.HOUR_OF_DAY) >= 13
                            && session.getStartTime().get(Calendar.HOUR_OF_DAY) < 17
            ).count() >= 2);
        });

    }

    public List<ConferenceSession> getTestSessions(){
        List<ConferenceSession> sessions = new ArrayList<>();

        sessions.add(new ConferenceSession("Proper Unit Tests for Anyone", 60, true));
        sessions.add(new ConferenceSession("Why Python?", 45, true));
        sessions.add(new ConferenceSession("TDD for Embedded Systems", 30, true));
        sessions.add(new ConferenceSession("Dependency Management with Interpreted languages", 45, true));
        sessions.add(new ConferenceSession("Java 8, really end of life", 45, true));
        sessions.add(new ConferenceSession("From Java 8 to Java 12", 5, true));
        sessions.add(new ConferenceSession("Managing Network Latency", 60, true));
        sessions.add(new ConferenceSession("BDD Gone Mad", 45, true));
        sessions.add(new ConferenceSession("Do you smell that?  (Code Smells)", 30, true));
        sessions.add(new ConferenceSession("Open Office Space or Closets????", 30, true));
        sessions.add(new ConferenceSession("Proper Pairing", 45, true));
        sessions.add(new ConferenceSession("Spring JuJu", 60, true));
        sessions.add(new ConferenceSession("Effective DSL (Domain Specific Languages)", 60, true));
        sessions.add(new ConferenceSession("Clojure... What Happened (on my project)", 45, true));
        sessions.add(new ConferenceSession("Effective Legacy Code Techniques...", 30, true));
        sessions.add(new ConferenceSession("Backends and FrontEnds in JavaScript?", 30, true));
        sessions.add(new ConferenceSession("CD on the Mainframe", 60, true));
        sessions.add(new ConferenceSession("Making Windows Development Enjoyable...", 30, true));
        sessions.add(new ConferenceSession("Modern Build Systems", 30, true));
        return sessions;
    }
}

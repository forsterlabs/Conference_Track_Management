package org.forsterlabs.conference_track_management.service;

import org.forsterlabs.conference_track_management.entity.ConferenceSession;
import org.forsterlabs.conference_track_management.services.SessionFileReaderService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class SessionFileReaderServiceTest {

    @Autowired
    private SessionFileReaderService sessionFileReaderService;

    @Test
    public void testFileLineSplitterNormal() {
        String inputLine = "Proper Unit Tests for Anyone 60min";
        ConferenceSession conferenceSession1 = sessionFileReaderService.readInputLineToConferenceSession(inputLine);
        Assert.assertEquals("Proper Unit Tests for Anyone", conferenceSession1.getSessionName());
        Assert.assertEquals(60, conferenceSession1.getSessionLength());
    }

    @Test
    public void testFileLineSplitterLightning() {
        String inputLine = "From Java 8 to Java 12 lightning";
        ConferenceSession conferenceSession1 = sessionFileReaderService.readInputLineToConferenceSession(inputLine);
        Assert.assertEquals("From Java 8 to Java 12", conferenceSession1.getSessionName());
        Assert.assertEquals(5, conferenceSession1.getSessionLength());
    }

    @Test
    public void testFileLineSplitterNormalWithNumbers() {
        String inputLine = "Java 8, really end of life 45min";
        ConferenceSession conferenceSession1 = sessionFileReaderService.readInputLineToConferenceSession(inputLine);
        Assert.assertEquals("Java 8, really end of life", conferenceSession1.getSessionName());
        Assert.assertEquals(45, conferenceSession1.getSessionLength());
    }
}

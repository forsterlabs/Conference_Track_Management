package org.forsterlabs.conference_track_management.services;

import org.forsterlabs.conference_track_management.config.ConferenceTrackConfig;
import org.forsterlabs.conference_track_management.entity.ConferenceSession;
import org.forsterlabs.conference_track_management.entity.TrackSession;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Profile("!test")
@Component
public class CommandLineService implements CommandLineRunner {

    private SessionFileReaderService sessionFileReaderService;

    private TrackBuilderService trackBuilderService;

    private boolean validTracks;

    public CommandLineService(SessionFileReaderService sessionFileReaderService, TrackBuilderService trackBuilderService) {
        this.sessionFileReaderService = sessionFileReaderService;
        this.trackBuilderService = trackBuilderService;
    }

    @Override
    public void run(String... args) throws Exception {
        boolean userContinue = true;
        String userPrompts = "";
        while (userContinue) {
            this.validTracks = true;
            Scanner scannerObj = new Scanner(System.in);
            System.out.println("Enter file location of conference sessions:");
            userPrompts = scannerObj.nextLine();
            File sessionFile = new File(userPrompts);
            if (sessionFile.exists()) {
                Map<Integer, List<TrackSession>> tracks = trackBuilderService.buildTracks(sessionFileReaderService
                        .readConferenceSessionFile(sessionFile));
                validateTracks(tracks);
                if (this.validTracks) {
                    outputConferenceTracks(tracks);
                }

            } else {
                System.out.println("File not found " + userPrompts);
            }

            System.out.println("Do you wish to submit a new session file? (Y/N)");
            userPrompts = scannerObj.nextLine();
            if (!userPrompts.equalsIgnoreCase("Y")) {
                userContinue = false;
            }
        }
        System.exit(0);
    }

    private void outputConferenceTracks(Map<Integer, List<TrackSession>> tracks) {
        tracks.keySet().stream().forEach(count -> {
            System.out.println("Track " + count + ":");
            tracks.get(count).forEach(session -> System.out.println(getSessionText(session)));
            System.out.println(" ");
        });
    }

    private String getSessionText(TrackSession session) {
        return ConferenceTrackConfig.HOUR_AMPM_FORMAT.format(
                session.getStartTime().getTime()) + " " + session.getSessiongTitle() + " "
                + (session.getSessionLength() == ConferenceTrackConfig.LIGHTNING_TALK_LENGTH ?
                ConferenceTrackConfig.LIGHTNING_KEYWORD : session.getSessionLength() + "min");
    }

    private void validateTracks(Map<Integer, List<TrackSession>> tracks) {
        tracks.keySet().forEach(integer -> {
            if (tracks.get(integer).stream().filter(session -> session.getStartTime().get(Calendar.HOUR_OF_DAY) < 12).count() < 2) {
                System.out.println("Track " + integer + " is missing talks for the morning session");
                this.validTracks = false;
            }

            if (tracks.get(integer).stream().filter(session -> session.getStartTime().get(Calendar.HOUR_OF_DAY) >= 13 && session.getStartTime().get(Calendar.HOUR_OF_DAY) < 17).count() < 2) {
                System.out.println("Track " + integer + " is missing talks for the afternoon session");
                this.validTracks = false;
            }
        });
    }
}

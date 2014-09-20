package com.alternativeheroes.hackgt.whereat;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by mde on 9/20/14.
 */
public class ServerAPI {

    private static String currentOrdering;

    private static String[] blurbs = {
            "Parties",
            "Andrew's Hoes",
            "Linux Users Group",
            "Local Clubs",
            "Local 'Clubs'",
            "Career Fairs",
            "Spelling Tutors"
    };

    private static String[] locations = {
            "Georgia Tech",
            "University of Michigan"
    };

    private static LinkedList<String> activatedBlurbs = new LinkedList<String>();
    private static String currentLocation;

    private static LinkedList<Event> Events;

    public static void updateBlurbs() {

    }

    public static void updateLocations() {

    }

    public static boolean hasBlurbs() {
        return true;
    }

    public static boolean hasLocations() {
        return true;
    }

    public static String[] getBlurbs() {
        return blurbs;
    }

    public static String[] getLocations() {
        return locations;
    }

    public static void activateBlurb(int index) {
        activatedBlurbs.add(blurbs[index]);
    }

    public static void deactivateBlurb(int index) {
        activatedBlurbs.remove(blurbs[index]);
    }

    public static void setCurrentLocation(int index) {
        currentLocation = locations[index];
    }

    public static void setCurrentOrdering(String ordering) {
        currentOrdering = ordering;
    }

    public static void fetchEventsList() {

    }

    public static LinkedList<Event> getEvents() {
        return Events;
    }

    public static int getNumberOfEvents() {
        if (Events == null) {
            return 0;
        }
        return Events.size();
    }
}

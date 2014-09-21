package com.alternativeheroes.hackgt.whereat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by mde on 9/20/14.
 */
public class ServerAPI {
    public static final String ORDER_LIKES = "popular";
    public static final String ORDER_TIME = "time";
    public static final String ORDER_DISTANCE = "distance";

    private static String currentOrdering = ORDER_LIKES;

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

    private static ArrayList<Event> Events;

    public static void updateBlurbs() {

    }

    public static void updateLocations() {

    }

    public static void fetchEventsList() {
        Events = new ArrayList<Event>(5);

        Events.add(new Event("1", 34, System.currentTimeMillis() / 1000L, 1.0, "Party at TEKE", true));
        Events.add(new Event("2", 56, System.currentTimeMillis() / 1000L, 2.0, "SGA Elections TOnight", false));
        Events.add(new Event("3", 0, System.currentTimeMillis() / 1000L, 1.2, "Party at TEKE (AGAIN)", true));
        Events.add(new Event("4", 100, System.currentTimeMillis() / 1000L, 0.1, "LUG", false));
        Events.add(new Event("5", 1000, System.currentTimeMillis() / 1000L, 0.0, "Swinging", true));

        order();
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

    public static String getCurrentLocation() {
        return currentLocation;
    }

    public static void setCurrentOrdering(String ordering) {
        currentOrdering = ordering;
    }

    public static ArrayList<Event> getEvents() {
        return Events;
    }

    public static int getNumberOfEvents() {
        if (Events == null) {
            return 0;
        }
        return Events.size();
    }

    public static String getCurrentOrdering() {
        return currentOrdering;
    }

    public static void switchOut() {

    }

    private static void order() {

    }
}

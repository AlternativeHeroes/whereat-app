package com.alternativeheroes.hackgt.whereat.Server;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.TimeZone;

/**
 * Created by mde on 9/20/14.
 */
public class EventServer {
    public static final String ORDER_LIKES = "popular";
    public static final String ORDER_TIME = "time";
    public static final String ORDER_DISTANCE = "distance";
    public static final SimpleDateFormat sdf = new SimpleDateFormat("h:mm a  -  MMM d");

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

    private static ArrayList<Event> EventsRaw;
    private static ArrayList<Event> EventsByLikes;
    private static ArrayList<Event> EventsByDistance;
    private static ArrayList<Event> EventsByTime;

    private static ArrayList<Event> Events;

    public static void updateBlurbs() {

    }

    public static void updateLocations() {

    }

    public static void fetchEventsList() {
        EventsRaw = new ArrayList<Event>(5);

        EventsRaw.add(new Event("1", 34, System.currentTimeMillis() / 1000L + 100, 1.0, "Party at TEKE", true, "Hello wors"));
        EventsRaw.add(new Event("2", 56, System.currentTimeMillis() / 1000L + 50, 2.0, "SGA Elections TOnight", false, "asjdacjllkhjhj"));
        EventsRaw.add(new Event("3", 0, System.currentTimeMillis() / 1000L + 10, 1.2, "Party at TEKE (AGAIN)", true, "KKKKKKKKKKKKK - Yelim's laugh"));
        EventsRaw.add(new Event("4", 100, System.currentTimeMillis() / 1000L - 10, 0.1, "LUG", false, "ON WEDSDAYS??"));
        EventsRaw.add(new Event("5", 1000, System.currentTimeMillis() / 1000L - 50, 0.0, "Swinging", true, "jhhjhhhjhjhjhj"));

        EventsByLikes    = new ArrayList<Event>(EventsRaw);
        EventsByDistance = new ArrayList<Event>(EventsRaw);
        EventsByTime     = new ArrayList<Event>(EventsRaw);

        Collections.sort(EventsByLikes, new Event.LikesComparator());
        Collections.sort(EventsByDistance, new Event.DistanceComparator());
        Collections.sort(EventsByTime, new Event.TimeComparator());

        switchOut();
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
        if (currentOrdering.equals(EventServer.ORDER_DISTANCE)) {
            Events = EventsByDistance;
        }
        else if (currentOrdering.equals(EventServer.ORDER_TIME)) {
            Events = EventsByTime;
        }
        else {
            Events = EventsByLikes;
        }
    }
}

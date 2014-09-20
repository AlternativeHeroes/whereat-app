package com.alternativeheroes.hackgt.whereat;

/**
 * Created by mde on 9/20/14.
 */
public class ServerAPI {

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
}

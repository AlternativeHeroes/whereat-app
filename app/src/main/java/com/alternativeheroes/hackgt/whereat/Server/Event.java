package com.alternativeheroes.hackgt.whereat.Server;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by mde on 9/20/14.
 */
public class Event {

    private int     likes;
    private long    startTime;
    private long    timePosted;
    private String  title;
    private String  id;
    private String  description;
    private String  location;
    private boolean isLiked;
    private int     attendees;

    private ArrayList<String> comments;

    public Event(String id, int likes, String location,
          long timePosted, String title, boolean isLiked,
          String description, int attendees, ArrayList<String> comments) {
        this.likes = likes;
        this.location = location;
        this.timePosted = timePosted;
        this.title = title;
        this.isLiked = isLiked;
        this.id = id;
        this.description = description;
        this.attendees = attendees;
        this.comments = comments;
    }


    public boolean isLiked() {
        return isLiked;
    }

    public void toggleLike() {
        if (isLiked) {
            isLiked = false;
            updateLikes(false);
        }
        else {
            isLiked = true;
            updateLikes(true);
        }
    }

    public void updateLikes(boolean increment) {
        /*
        Server shit.
         */
        if (increment) {
            ++likes;
        }
        else {
            --likes;
        }
    }

    public String getTitle() {
        return title;
    }

    public int getLikes() {
        return likes;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getTimePosted() {
        return timePosted;
    }

    public String getDescription() {
        return description;
    }

    public static class TimeComparator implements Comparator<Event> {
        @Override
        public int compare(Event lhs, Event rhs) {
            if (lhs.getStartTime() < rhs.getStartTime()) {
                return -1;
            }
            else {
                return 1;
            }
        }

        @Override
        public boolean equals(Object object) { return false; }
    }

    /*public static class DistanceComparator implements Comparator<Event> {
        @Override
        public int compare(Event lhs, Event rhs) {
            if (lhs.getDistance() < rhs.getDistance()) {
                return -1;
            }
            else {
                return 1;
            }
        }

        @Override
        public boolean equals(Object object) { return false; }
    }*/

    public static class LikesComparator implements Comparator<Event> {
        @Override
        public int compare(Event lhs, Event rhs) {
            if (lhs.getLikes() > rhs.getLikes()) {
                return -1;
            }
            else {
                return 1;
            }
        }

        @Override
        public boolean equals(Object object) { return false; }
    }

    public String getLocation() {
        return location;
    }

    public int getAttendees() {
        return attendees;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public String getId() {
        return id;
    }
}

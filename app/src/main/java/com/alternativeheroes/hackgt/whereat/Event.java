package com.alternativeheroes.hackgt.whereat;

/**
 * Created by mde on 9/20/14.
 */
public class Event {

    private int     likes;
    private long    startTime;
    private double  distance;
    private String  title;
    private String  id;
    private boolean isLiked;

    Event(String id, int likes, long startTime, double distance, String title, boolean isLiked) {
        this.likes = likes;
        this.startTime = startTime;
        this.distance = distance;
        this.title = title;
        this.isLiked = isLiked;
        this.id = id;
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

    public double getDistance() {
        return distance;
    }

}

package com.alternativeheroes.hackgt.whereat.Server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by mde on 9/20/14.
 */
public class CommentServer {

    public static ArrayList<String> images;
    public static ArrayList<String> comments;

    public static void updateImages() {
        images = new ArrayList<String>();

        images.add("http://static.guim.co.uk/sys-images/Guardian/Pix/pictures/2009/7/26/1248641740302/The-Goons-001.jpg");
        images.add("http://rubberneckzine.com/wp-content/uploads/2014/02/GINO_front_small.png");
        images.add("http://img3.wikia.nocookie.net/__cb20131003054946/popeye/images/e/e2/Goons.jpg");
        images.add("http://mcgarnagle.files.wordpress.com/2011/07/vlcsnap-00064.jpg");
    }

    public static void updateComments(Event event) {
        ArrayList<String> commentIds = event.getComments();
        comments = new ArrayList<String>(commentIds.size());

        for (int i = 0; i < commentIds.size(); ++i) {
            JSONObject data =  EventServer.getDataObject(
                    "http://128.61.77.195:3000/comment/" + commentIds.get(i), false);
            try {
                comments.add(URLDecoder.decode(data.getString("text"), "UTF-8"));
            }
            catch (Exception err) { err.printStackTrace(); }
        }

        /*
        comments.add("Hello");
        comments.add("FUCK YOU RICHARD STALLMAN!");
        comments.add("I love that part at 3:10");
        comments.add("Do you even be?");
        comments.add("FIRST LAWLS"); */
    }

    public static ArrayList<String> getImages() {
        return images;
    }

    public static ArrayList<String> getComments() {
        return comments;
    }

    public static void postComment(String comment, Event event) {
        try {
            EventServer.getDataObject("http://128.61.77.195:3000/event/" + event.getId()
                    + "/541e365e84ae90fe6fd48a61/text/" + URLEncoder.encode(comment, "UTF-8"), true);
        }
        catch (Exception err) { err.printStackTrace(); }
    }
}

package com.alternativeheroes.hackgt.whereat.Server;

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

    public static void updateComments() {

    }

    public static ArrayList<String> getImages() {
        return images;
    }
}

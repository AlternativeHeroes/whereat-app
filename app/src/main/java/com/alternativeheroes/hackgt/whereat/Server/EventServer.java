package com.alternativeheroes.hackgt.whereat.Server;

import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
    public static final String ORDER_NEWEST = "newest";
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

    private static String USER_ID = "";

    public static void updateBlurbs() {

    }

    public static void updateLocations() {

    }

    public static void fetchEventsList() {
        /*
        EventsRaw = new ArrayList<Event>(5);

        EventsRaw.add(new Event("1", 34, System.currentTimeMillis() / 1000L + 100, 1L, "Party at TEKE", true, "The baddes party in town."));
        EventsRaw.add(new Event("2", 56, System.currentTimeMillis() / 1000L + 50, 2L, "SGA Elections Tonight", false, "Come out and vote for your classmates!"));
        EventsRaw.add(new Event("3", 3, System.currentTimeMillis() / 1000L + 10, 1L, "Party at TEKE (AGAIN)", true, "It'll be a blast! (Yelim's laugh)"));
        EventsRaw.add(new Event("4", 104, System.currentTimeMillis() / 1000L - 10, 1L, "GT vs. VT TODAY", false, "Support Georgia Tech!!"));
        */

        JSONArray data;

        if ((data = getDataArray("http://128.61.77.195:3000/event/list/all")) == null) {
            return;
        }
        try {
            parseJsonIntoRaw(data);
        } catch (Exception err) {
            err.printStackTrace();
        }

        sortAndCopy();
        switchOut();
    }

    public static void sortAndCopy() {
        EventsByLikes    = new ArrayList<Event>(EventsRaw);
        //EventsByDistance = new ArrayList<Event>(EventsRaw);
        EventsByTime     = new ArrayList<Event>(EventsRaw);

        Collections.sort(EventsByLikes, new Event.LikesComparator());
        //Collections.sort(EventsByDistance, new Event.DistanceComparator());
        Collections.sort(EventsByTime, new Event.TimeComparator());
    }

    public static void parseJsonIntoRaw(JSONArray data) throws JSONException {
        Log.i("JSON: ", data.toString());
        EventsRaw = new ArrayList<Event>(data.length());

        for (int i = 0; i < data.length(); ++i) {
            EventsRaw.add(parseEventFromJsonObject(data.getJSONObject(i)));
        }
    }

    public static Event parseEventFromJsonObject(JSONObject object) throws JSONException{
        String  ID    = object.getString("_id");
        int     likes = object.getInt("votes");
        long    time  = object.getLong("unixTimestamp");
        String  title = object.getString("name");
        String  loc   = object.getString("where");
        int     atten = object.getJSONArray("attendees").length();
        String  desc  = "";
        boolean liked = false;

        ArrayList<String> commentList = new ArrayList<String>();
        JSONArray comments = object.getJSONArray("comments");
        for (int j = 0; j < comments.length(); ++j) {
            commentList.add(comments.getString(j));
        }

        return new Event(ID, likes, loc, time, title, liked, desc, atten, commentList);
    }

    public static JSONArray getDataArray(String url) {
        JSONArray data = null;

        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();

            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                StringBuilder builder = new StringBuilder();
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(content));

                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                data = new JSONArray(builder.toString());
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return data;
    }

    public static JSONObject getDataObject(String url, boolean isPost) {
        JSONObject data = null;
        HttpClient client = new DefaultHttpClient();
        Object request;

        if (isPost) {
            request = new HttpPost(url);
        }
        else {
            request = new HttpGet(url);
        }

        try {
            HttpResponse response;
            if (isPost) {
                response = client.execute((HttpPost) request);
            }
            else {
                response = client.execute((HttpGet) request);
            }
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();

            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                StringBuilder builder = new StringBuilder();
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(content));

                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                data = new JSONObject(builder.toString());
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return data;
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
        if (currentOrdering.equals(EventServer.ORDER_NEWEST)) {
            Events = EventsRaw;
        }
        else if (currentOrdering.equals(EventServer.ORDER_TIME)) {
            Events = EventsByTime;
        }
        else {
            Events = EventsByLikes;
        }
    }

    public static void pushNewEvent(Event event) {

        fetchEventsList();
    }

    public static void setUserId(String id) {
        USER_ID = id;
    }

    public static String getUserId() {
        return USER_ID;
    }

    public static String registerNewUser() {





        return "";
    }

    public static void updateEvent(int index) {
        String id = Events.get(index).getId();
        try {
            JSONObject data = getDataObject("http://128.61.77.195:3000/event/" + id, false);
            Event updatedEvent = parseEventFromJsonObject(data);

            for (int i = 0; i < EventsRaw.size(); ++i) {
                if (EventsRaw.get(i).getId().equals(id)) {
                    EventsRaw.set(i, updatedEvent);
                }
            }
        }
        catch (JSONException err) { err.printStackTrace(); }

        sortAndCopy();
    }
}

package com.alternativeheroes.hackgt.whereat;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by mde on 9/20/14.
 */
public class EventsAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private MainActivity activity;

    public EventsAdapter(MainActivity a) {
        activity = a;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ServerAPI.getNumberOfEvents();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return View.inflate(activity, R.layout.event_panel, parent);
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "nothing important");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}

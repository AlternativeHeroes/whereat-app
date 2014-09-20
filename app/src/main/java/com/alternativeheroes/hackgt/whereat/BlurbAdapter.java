package com.alternativeheroes.hackgt.whereat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by mde on 9/20/14.
 */
public class BlurbAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private Activity activity;

    public BlurbAdapter(Activity a) {
        activity = a;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ServerAPI.getBlurbs().length + 1;
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
        View v = inflater.inflate(R.layout.single_panel_text, null);

        if (position > 0) {
            ((TextView) v.findViewById(R.id.text)).setText(ServerAPI.getBlurbs()[position - 1]);
        }
        else {
            ((TextView) v.findViewById(R.id.text)).setText(ServerAPI.getLocations()[0]);
        }
        return v;
    }
}

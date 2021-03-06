package com.alternativeheroes.hackgt.whereat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.alternativeheroes.hackgt.whereat.MainActivity;
import com.alternativeheroes.hackgt.whereat.R;
import com.alternativeheroes.hackgt.whereat.Server.EventServer;

/**
 * Created by mde on 9/20/14.
 */
public class BlurbAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private MainActivity activity;

    public BlurbAdapter(MainActivity a) {
        EventServer.updateLocations();
        EventServer.updateBlurbs();
        activity = a;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return EventServer.getBlurbs().length + 1;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v;

        if (position > 0) {
            final int newPos = position - 1;
            v = inflater.inflate(R.layout.single_panel_text, null);
            CheckBox checkBox = ((CheckBox) v.findViewById(R.id.checkBox));
            checkBox.setText(EventServer.getBlurbs()[newPos]);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    activity.onBlurbChange(newPos, isChecked, buttonView);
                }
            });
        }
        else {
            v = inflater.inflate(R.layout.drawer_header, null);
            Spinner spinner = ((Spinner) v.findViewById(R.id.spinner));
            spinner.setAdapter(
                    new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1,
                            EventServer.getLocations()));
            spinner.setOnItemSelectedListener(activity);
        }
        return v;
    }
}

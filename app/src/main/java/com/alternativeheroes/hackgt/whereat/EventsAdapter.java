package com.alternativeheroes.hackgt.whereat;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.zip.Inflater;

/**
 * Created by mde on 9/20/14.
 */
public class EventsAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private MainActivity activity;

    private SimpleDateFormat sdf = new SimpleDateFormat("h:mm a  -  MMM d");

    public EventsAdapter(MainActivity a) {
        ServerAPI.fetchEventsList();
        sdf.setTimeZone(TimeZone.getDefault());
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View  v =  inflater.inflate(R.layout.event_panel, null);
        Event e = ServerAPI.getEvents().get(position);

        ((LinearLayout) v.findViewById(R.id.voting)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onUpVote(v, position);
            }
        });

        ((RelativeLayout) v.findViewById(R.id.touching)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onMoreInfoSelect(v, position);
            }
        });

        ((TextView) v.findViewById(R.id.eventTitle)).setText(e.getTitle());
        ((TextView) v.findViewById(R.id.up_count)).setText(
                String.valueOf(e.getLikes()));

        if (e.isLiked()) {
            ((ImageView) v.findViewById(R.id.upVote)).setImageResource(R.drawable.smyle_selected);
        }

        String order = ServerAPI.getCurrentOrdering();
        TextView data = ((TextView) v.findViewById(R.id.eventInfo));

        if (order.equals(ServerAPI.ORDER_DISTANCE)) {
            data.setText(e.getDistance() + " miles");
        } else if (order.equals(ServerAPI.ORDER_LIKES)) {
            data.setText("");
        } else if (order.equals(ServerAPI.ORDER_TIME)) {
            Date date = new Date(e.getStartTime() * 1000L);
            data.setText(sdf.format(date));
        }

        return v;
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

package com.alternativeheroes.hackgt.whereat;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.alternativeheroes.hackgt.whereat.Server.Event;
import com.alternativeheroes.hackgt.whereat.Server.EventServer;

import java.util.ArrayList;
import java.util.Date;


public class NewEventActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        ((Button) findViewById(R.id.submitNewEvent)).setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        TimePicker time = ((TimePicker) findViewById(R.id.timeInput));
        long unix =
                ((DatePicker) findViewById(R.id.dateInput)).getCalendarView().getDate() +
                (time.getCurrentHour() * 60 + time.getCurrentMinute()) * 60000;

        String location = ((TextView) findViewById(R.id.locInput)).getText().toString();

        Event e = new Event("",
                            0,
                            location,
                            unix,
                            ((TextView) findViewById(R.id.titleInput)).getText().toString(),
                            false,
                            ((TextView) findViewById(R.id.descInput)).getText().toString(),
                            0,
                            new ArrayList<String>(0));

        EventServer.pushNewEvent(e);
        finish();
    }
}

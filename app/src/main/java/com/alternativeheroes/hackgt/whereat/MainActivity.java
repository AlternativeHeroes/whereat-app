package com.alternativeheroes.hackgt.whereat;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alternativeheroes.hackgt.whereat.Fragments.LoginFragment;
import com.alternativeheroes.hackgt.whereat.Server.Event;
import com.alternativeheroes.hackgt.whereat.Server.EventServer;
import com.alternativeheroes.hackgt.whereat.adapters.BlurbAdapter;
import com.alternativeheroes.hackgt.whereat.adapters.EventsAdapter;
import android.content.Intent;


public class MainActivity extends FragmentActivity
        implements AdapterView.OnItemSelectedListener {

    private LoginFragment loginFrag;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView     mDrawerList;
    private ListView     mEventList;

    public  static final String MESSAGE = "SelectedActivity";
    private static final String ID_TAG  = "userIdWhereAt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.
                ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        doUserTransaction();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.empty,  /* "open drawer" description */
                R.string.empty  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle(mDrawerTitle);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);



        mDrawerList   = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new BlurbAdapter(this));

        mEventList    = (ListView) findViewById(R.id.events_list);
        mEventList.setAdapter(new EventsAdapter(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (item.getItemId() == R.id.action_compose) {
            createNewEventActivity();
        }
        else if (item.getItemId() == R.id.action_refresh) {
            EventServer.fetchEventsList();
            updateEventsListAdapter();
        }
        else {

            //Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();

            EventServer.setCurrentOrdering(item.getTitleCondensed().toString());
            // Server will not order
            // ServerAPI.fetchEventsList();
            EventServer.switchOut();
            updateEventsListAdapter();
        }

        return true;
    }

    // On blurb change
    public void onBlurbChange(int blurbIndex, boolean checked, CompoundButton btnView) {
        //Toast.makeText(this, ServerAPI.getBlurbs()[blurbIndex], Toast.LENGTH_SHORT).show();

        if (checked) {
            ((LinearLayout) btnView.getParent()).setBackgroundColor(getResources().getColor(R.color.blurb_selected));
            EventServer.activateBlurb(blurbIndex);
        }
        else {
            ((LinearLayout) btnView.getParent()).setBackgroundColor(getResources().getColor(R.color.blurb_unselected));
            EventServer.deactivateBlurb(blurbIndex);
        }
        EventServer.fetchEventsList();
        updateEventsListAdapter();
    }

    // On Location Change
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (EventServer.getCurrentLocation() == null
                || !EventServer.getCurrentLocation().equals(EventServer.getLocations()[position])) {
            //Toast.makeText(this, ServerAPI.getLocations()[position], Toast.LENGTH_SHORT).show();
            EventServer.setCurrentLocation(position);
            EventServer.fetchEventsList();
            updateEventsListAdapter();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {  }

    private void updateEventsListAdapter() {
        ((BaseAdapter) mEventList.getAdapter()).notifyDataSetChanged();
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void onUpVote(View v, int index) {
        ImageView image = ((ImageView) v.findViewById(R.id.upVote));
        Event currEvent = EventServer.getEvents().get(index);

        if (currEvent.isLiked()) {
            image.setImageResource(R.drawable.smyle_unselected);
        }
        else {
            image.setImageResource(R.drawable.smyle_selected);
        }
        currEvent.toggleLike();
        updateEventsListAdapter();
    }

    public void onMoreInfoSelect(View v, int index) {
        Intent intent = new Intent(this, DescriptionActivity.class);
        intent.putExtra(MESSAGE, index);
        startActivity(intent);
    }

    public void createNewEventActivity() {
        Intent intent = new Intent(this, NewEventActivity.class);
        startActivity(intent);
    }

    public void doUserTransaction() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String id = prefs.getString(ID_TAG, "none");

        if (id.equals("none")) {
            id = EventServer.registerNewUser();

            SharedPreferences.Editor prefsEdit =
                    PreferenceManager.getDefaultSharedPreferences(this).edit();
            prefsEdit.putString(ID_TAG, id);
            prefsEdit.commit();
        }

        EventServer.setUserId(id);
    }
}


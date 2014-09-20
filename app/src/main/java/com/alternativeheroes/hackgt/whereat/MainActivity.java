package com.alternativeheroes.hackgt.whereat;

import android.graphics.Color;
import android.graphics.drawable.DrawableContainer;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends FragmentActivity
        implements AdapterView.OnItemSelectedListener {

    private LoginFragment loginFrag;

    private DrawerLayout mDrawerLayout;
    private ListView     mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        if (savedInstanceState == null) {
            loginFrag = new LoginFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content, loginFrag)
                    .commit();
        } else {
            loginFrag = (LoginFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.content);

        }
        */

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList   = (ListView) findViewById(R.id.left_drawer);

        mDrawerList.setAdapter(new BlurbAdapter(this));
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

        Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();

        ServerAPI.setCurrentOrdering(item.getTitleCondensed().toString());
        return super.onOptionsItemSelected(item);
    }

    // On blurb change
    public void onBlurbChange(int blurbIndex, boolean checked, CompoundButton btnView) {
        Toast.makeText(this, ServerAPI.getBlurbs()[blurbIndex], Toast.LENGTH_SHORT).show();
        if (checked) {
            ServerAPI.activateBlurb(blurbIndex);
        }
        else {
            ServerAPI.deactivateBlurb(blurbIndex);
        }
        updateEventsListAdapter();
    }

    // On Location Change
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, ServerAPI.getLocations()[position], Toast.LENGTH_SHORT).show();
        ServerAPI.setCurrentLocation(position);
        updateEventsListAdapter();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {  }

    private void updateEventsListAdapter() {

    }
}

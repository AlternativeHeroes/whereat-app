package com.alternativeheroes.hackgt.whereat;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.alternativeheroes.hackgt.whereat.Server.CommentServer;
import com.alternativeheroes.hackgt.whereat.Server.Event;
import com.alternativeheroes.hackgt.whereat.Server.EventServer;

import java.io.InputStream;
import java.net.URL;
import java.util.TimeZone;


public class DescriptionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_desccription);
        EventServer.sdf.setTimeZone(TimeZone.getDefault());

        Event e = EventServer.getEvents().get(getIntent().getIntExtra(MainActivity.MESSAGE, 0));

        ((TextView) findViewById(R.id.summaryAndTIme)).setText(
                EventServer.sdf.format(e.getStartTime())
                + ":\n" + e.getDescription());
        ((TextView) findViewById(R.id.likesCount)).setText(e.getLikes() + " Votes");
        ((TextView) findViewById(R.id.distanceCount)).setText(e.getDistance() + " miles away");
        ((TextView) findViewById(R.id.titleContainer)).setText(e.getTitle());

        LinearLayout imageContainer = (LinearLayout) findViewById(R.id.imageContainer);
        float scale = getBaseContext().getResources().getDisplayMetrics().density;

        CommentServer.updateImages();
        for (String image : CommentServer.getImages()) {
            try {
                ImageView img = new ImageView(getBaseContext());
                DownloadImageTask task = new DownloadImageTask(img);
                task.execute(image);

                img.setImageResource(R.drawable.skyline);
                img.setVisibility(View.VISIBLE);
                img.setPadding(15, 15, 15, 15);
                img.setMaxHeight((int) (200 * scale));
                img.setAdjustViewBounds(true);
                ((ViewGroup) imageContainer).addView(img);
            }
            catch (Exception err) {
                err.printStackTrace();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.description, menu);
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

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();

            Drawable d = Drawable.createFromStream(is, "nothing important");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}

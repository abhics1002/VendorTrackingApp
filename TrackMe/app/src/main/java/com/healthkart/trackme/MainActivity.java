package com.healthkart.trackme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String id = "123";
        final Intent i = new Intent(getApplicationContext(), TrackingService.class);
        i.putExtra("id", id);

        Button trackMeButton = (Button) findViewById(R.id.trackMeButton);
        trackMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getApplicationContext().startService(i);
                final Intent mapIntent = new Intent(getApplicationContext(), MapActivity.class);
                mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mapIntent.putExtra("locations", "");
                getApplicationContext().startActivity(mapIntent);



            }
        });

        Button stopTrackingButton = (Button) findViewById(R.id.stopTracking);
        stopTrackingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(getApplicationContext(), TrackingService.class));
                //getOrderLocations();

            }
        });

        /*Button showMapButton = (Button) findViewById(R.id.showMap);
        showMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locations = getOrderLocations();
                mapIntent.putExtra("locations", locations);
                getApplicationContext().startActivity(mapIntent);
            }
        });*/
    }

    private String getOrderLocations() {

        String url1 = "https://peaceful-eyrie-3591.herokuapp.com/getshipmentdetails/1";
        GetHttpClient client = new GetHttpClient(getApplicationContext());
        client.execute(new String[] { url1});
        try {
            Thread.sleep(1000, 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*String response = client.getRes();
        Log.i("Response " ,response);*/
        return null;
    }

}

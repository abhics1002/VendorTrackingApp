package com.healthkart.trackme;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.widget.Toast;

public class TrackingService extends Service {
    public TrackingService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        track();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void track() {
        String id = "123";
        String url1 = "https://peaceful-eyrie-3591.herokuapp.com/userslocation/";
        String url2 = "https://peaceful-eyrie-3591.herokuapp.com/sendlocationUpdate/";
        String name = "asd";
        Location location = trackLocation(id, name, url1, url2);
        //TextView currentLocation = (TextView) findViewById(R.id.currentLocation);
        if(location != null) {
            String latitude = String.valueOf(location.getLatitude());
            String longitude = String.valueOf(location.getLongitude());
            HttpClient client = new HttpClient(getApplicationContext(), id, name, latitude, longitude);
            client.execute(new String[] { url1, url2});
            String text = "Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude();
            Toast toast = Toast.makeText(getApplicationContext(), "Location posted - " + text, Toast.LENGTH_LONG);
            toast.show();
          //  currentLocation.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());

        }/* else  {
            currentLocation.setText("Sorry..We lost you!!");
            System.out.println("Unable to find");
        }*/
    }

    private Location trackLocation(String id, String name, String url1, String url2) {
        GPSTracker tracker = new GPSTracker(getApplicationContext(), id, name, url1, url2);

        Location location = null;
        if(tracker.canGetLocation ){
            location  = tracker.getLocation();
        }else{
            Toast toast = Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG);
            toast.show();
            tracker.showSettingsAlert();
            System.out.println("Please turn on location");
        }
        return location;
    }

}

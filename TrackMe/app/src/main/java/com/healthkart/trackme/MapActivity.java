package com.healthkart.trackme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Activity {

    GoogleMap nMap;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        if (nMap == null) {
            nMap = ((MapFragment) getFragmentManager()
                    .findFragmentById(R.id.map)).getMap();
        }

        nMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        /*GPSTracker tracker = new GPSTracker(getApplicationContext(), null, null, null, null);

        Location location = null;
        if(tracker.canGetLocation ) {
            location = tracker.getLocation();
            if(location != null) {
                MarkerOptions optns = new MarkerOptions().position(new LatLng(
                        new Double(location.getLatitude()), new Double(location.getLongitude()))).title("Its Me!").snippet("My Location");
                optns.icon(BitmapDescriptorFactory.fromResource(R.drawable.courier_boy));
                nMap.addMarker(optns);
            }

        }*/
        Intent intent = getIntent();
        //String locations = intent.getStringExtra("locations");
        String locations = "28.478,77.046,Prashanth;28.449,77.056,Abhishek;28.455,77,040,Komal;28.511,77.063,Mohit";
        String[] locs = locations.split(";");
        for(String loc : locs) {
            String[] users = loc.split(",");
            String lat = users[0];
            String lon = users[1];
            String user = users[2];
            Marker m1 = nMap.addMarker(new MarkerOptions().position(new LatLng(new Double(lat),new Double(lon) )).title(user)
                    .snippet(user));
            nMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(new Double(lat),new Double(lon)), Float.parseFloat("15")));
            nMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        }
    }
}

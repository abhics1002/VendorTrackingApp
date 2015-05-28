package com.healthkart.trackme;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by komal.kochar on 5/13/2015.
 */
public class GetHttpClient extends AsyncTask<String, Void, String> {

    String res = "";
    private Context context;

    public GetHttpClient(Context context){
        this.context = context;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    @Override
    protected String doInBackground(String... urls) {
        String response = "";
        for (String url : urls) {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(url);
            try {

                HttpResponse execute = client.execute(httpget);
                String status = String.valueOf(execute.getStatusLine().getStatusCode());
                if ( status.equalsIgnoreCase("200")) {
                    Log.i("LOCATION", "received");
                }
                else {
                    Toast.makeText(context, "Error while posting location", Toast.LENGTH_LONG).show();
                }
                HttpEntity resEntity = execute.getEntity();
                InputStream content = resEntity.getContent();

                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    response += s;
                }
                setRes(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        final Intent mapIntent = new Intent(context, MapActivity.class);
        mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mapIntent.putExtra("locations", s);
        context.startActivity(mapIntent);

    }
}

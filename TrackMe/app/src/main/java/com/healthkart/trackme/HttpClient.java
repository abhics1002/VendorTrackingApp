package com.healthkart.trackme;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by komal.kochar on 5/13/2015.
 */
public class HttpClient extends AsyncTask<String, Void, String> {

    String res = "";
    String id = "";
    String name = "";
    String latitude = "";
    String longitude = "";
    private Context context;

    public HttpClient(Context context, String id, String name, String latitude, String longitude){
        this.context = context;
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
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
        if(urls == null || urls.length == 0)
            return null;
        for (String url : urls) {
            DefaultHttpClient client = new DefaultHttpClient();
            if(url == null)
                return null;
            HttpPost httppost = new HttpPost(url);
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
                nameValuePairs.add(new BasicNameValuePair("user_id", id));
                nameValuePairs.add(new BasicNameValuePair("name", name));
                nameValuePairs.add(new BasicNameValuePair("latitude", latitude));
                nameValuePairs.add(new BasicNameValuePair("longitude", longitude));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse execute = client.execute(httppost);
                String status = String.valueOf(execute.getStatusLine().getStatusCode());
                if ( status.equalsIgnoreCase("200") || status.equalsIgnoreCase("201")) {
                    Log.i("LOCATION", "Posted : lat - " + latitude + " long - " + longitude);
                }
                else {
                    Toast.makeText(context, "Error while posting location", Toast.LENGTH_LONG).show();
                }
                /*HttpEntity resEntity = execute.getEntity();
                InputStream content = resEntity.getContent();

                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    response += s;
                }*/


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }
}

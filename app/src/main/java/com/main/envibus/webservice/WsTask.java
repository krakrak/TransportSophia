package com.main.envibus.webservice;

import android.os.AsyncTask;
import android.util.Log;

import com.main.envibus.data.Stop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by KraKk on 14/11/2015.
 */
public class WsTask extends AsyncTask<URL, Void, String> {

    private String TAG = WsTask.class.getSimpleName();

    protected String getASCIIContentFromEntity(HttpURLConnection conn) throws IllegalStateException, IOException
    {
        InputStream in = new BufferedInputStream(conn.getInputStream());

        return WsUtils.toString(in);

    }
    @Override
    protected String doInBackground(URL... urls) {
            int count = urls.length;
        Log.d(WsTask.class.getName(), "Number of URLS "+ count);
        String response = "";
        try
        {
            for (int i =0; i<count; i++)
            {
                URL url = urls[i];
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                response = getASCIIContentFromEntity(con);
            }
        } catch (IOException e)
        {
            Log.e(TAG, "Decoding error" + e.toString());
        }
        return response;
    }

    @Override
    protected void onPostExecute(String s) {

        ArrayList<Stop> stops = new ArrayList<Stop>();
        try {
            JSONObject stopsJson = new JSONObject(s);
            JSONObject status = stopsJson.getJSONObject("Status");
            JSONArray stopsData = stopsJson.getJSONArray("Data");

            if(status.get("Code") == 200)
            {
                for (int i = 0; i< stopsData.length(); i++ )
                {
                    JSONObject singleStop = stopsData.getJSONObject(i);
                    Stop stop = new Stop(singleStop.getInt("Type"), singleStop.getDouble("RoadLength"),
                            singleStop.getString("NumRoad"), singleStop.getDouble("LocalityId"),
                            singleStop.getString("LocalityName"), Float.valueOf(singleStop.getString("Longitude")), Float.valueOf(singleStop.getString("Latitude")),
                            singleStop.getDouble("Category"), singleStop.getString("Name"), singleStop.getDouble("Id"), singleStop.getString("Code"));

                    stops.add(stop);
                }
            } else {
                Log.w(TAG, "ERROR :" + status.get("Code") + ", "+status.get("Description"));
            }

        } catch (JSONException e)
        {
            Log.e(TAG, "Error in parsing JSON");
        }


        Log.d(TAG, "onPostExecute"+s);
    }
}

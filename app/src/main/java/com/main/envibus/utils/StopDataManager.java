package com.main.envibus.utils;

import android.util.Log;

import com.main.envibus.data.Stop;
import com.main.envibus.data.StopObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by KraKk on 13/12/2015.
 */
public class StopDataManager {

    private static final String TAG = StopDataManager.class.getSimpleName();
    public static StopObject toStopObject (int purpose, String data)
    {
        try {
            ArrayList<Stop> stops = new ArrayList<>();
            JSONObject stopsJson = new JSONObject(data);
            JSONObject status = stopsJson.getJSONObject("Status");
            JSONArray stopsData = stopsJson.getJSONArray("Data");
            if (status.getInt("Code") == 200) {
                for (int i = 0; i < stopsData.length(); i++) {
                    JSONObject singleStop = stopsData.getJSONObject(i);
                    Stop stop = new Stop(singleStop.getInt("Type"), singleStop.getDouble("RoadLength"),
                            singleStop.getString("NumRoad"), singleStop.getDouble("LocalityId"),
                            singleStop.getString("LocalityName"), Float.valueOf(singleStop.getString("Longitude")), Float.valueOf(singleStop.getString("Latitude")),
                            singleStop.getDouble("Category"), singleStop.getString("Name"), singleStop.getDouble("Id"), singleStop.getString("Code"));

                    stops.add(stop);
                }

                return new StopObject(stops, purpose);
            } else {
                Log.e(TAG, "ERROR : " + status.get("Code") + ", " + status.get("Description"));
            }

        } catch (JSONException e) {
            Log.e(TAG, "Error in parsing JSON");
        }

        return new StopObject(null, purpose);
    }
}

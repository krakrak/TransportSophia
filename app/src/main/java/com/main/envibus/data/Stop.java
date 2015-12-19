package com.main.envibus.data;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by KraKk on 15/11/2015.
 */
public class Stop
{
    private static final String TAG = Stop.class.getSimpleName();
    private String code;
    private Double id;
    private String name;
    private Double category;
    private Float latitude;
    private Float longitude;
    private Double localityId;
    private String localityName;
    private String numRoad;
    private Double roadLength;
    private int type;

    public Stop() {
    }

    public Stop(int type, Double roadLength, String numRoad, Double localityId, String localityName, Float longitude, Float latitude, Double category, String name, Double id, String code) {
        this.type = type;
        this.roadLength = roadLength;
        this.numRoad = numRoad;
        this.localityId = localityId;
        this.localityName = localityName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.category = category;
        this.name = name;
        this.id = id;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCategory() {
        return category;
    }

    public void setCategory(Double category) {
        this.category = category;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Double getLocalityId() {
        return localityId;
    }

    public void setLocalityId(Double localityId) {
        this.localityId = localityId;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getLocalityName() {
        return localityName;
    }

    public void setLocalityName(String localityName) {
        this.localityName = localityName;
    }

    public String getNumRoad() {
        return numRoad;
    }

    public void setNumRoad(String numRoad) {
        this.numRoad = numRoad;
    }

    public Double getRoadLength() {
        return roadLength;
    }

    public void setRoadLength(Double roadLength) {
        this.roadLength = roadLength;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String toJsonString()
    {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("Id", getId());
            jsonObject.put("Code", getCode());
            jsonObject.put("Type", getType());
            jsonObject.put("Name", getName());
            jsonObject.put("RoadLength", getRoadLength());
            jsonObject.put("NumRoad", getNumRoad());
            jsonObject.put("LocalityId", getLocalityId());
            jsonObject.put("LocalityName", getLocalityName());
            jsonObject.put("Longitude", getLongitude());
            jsonObject.put("Latitude", getLatitude());
            jsonObject.put("Category", getCategory());

            return jsonObject.toString();
        } catch(JSONException e)
        {
            Log.e(TAG, "Error processing object to JSON");
            e.printStackTrace();
            return "";
        }
    }

    public void jsonStringToObject(String jsonString)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(jsonString);

            this.setCategory(jsonObject.getDouble("Category"));
            this.setCode(jsonObject.getString("Code"));
            this.setId(jsonObject.getDouble("Id"));
            this.setLatitude(Float.valueOf(jsonObject.getString("Latitude")));
            this.setLocalityId(jsonObject.getDouble("LocalityId"));
            this.setLocalityName(jsonObject.getString("LocalityName"));
            this.setLongitude(Float.valueOf(jsonObject.getString("Longitude")));
            this.setName(jsonObject.getString("Name"));
            this.setNumRoad(jsonObject.getString("NumRoad"));
            this.setRoadLength(jsonObject.getDouble("RoadLength"));
            this.setType(jsonObject.getInt("Type"));

        } catch (JSONException e)
        {
            Log.e(TAG, "Error processing JSON to Object");
            e.printStackTrace();
        }
    }


}

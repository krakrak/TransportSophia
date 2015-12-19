package com.main.envibus.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KraKk on 15/11/2015.
 */
public class StopObject implements Parcelable
{
    private static final String TAG = StopObject.class.getSimpleName();

    //Object used during search, here we define if it's the destination or the origin.
    //Origin = 0;
    //Destination = 1;
    private int purpose;
    private ArrayList<Stop> stops;
    private List<String> stopsString;

	public StopObject ()
	{
	}

	public StopObject(ArrayList<Stop> stops, int purpose) {
        this.stops = stops;
        this.purpose = purpose;
    }

    public int getPurpose() {
        return purpose;
    }

    public void setPurpose(int purpose) {
        this.purpose = purpose;
    }

    public ArrayList<Stop> getStops() {
        return stops;
    }

    public void setStops(ArrayList<Stop> stops) {
        this.stops = stops;
    }

    @Override
    public String toString() {

        String purposeString;

        if (purpose == 0)
        {
            purposeString = "Origin";
        } else if(purpose == 1)
        {
            purposeString = "Destination";
        } else
        {
            purposeString = "Unidentified";
        }

        String names = "";

        if (!stops.isEmpty())
        {
            for (int i = 0; i<stops.size(); i++)
            {
                if(stops.size() > 1)
                {
                    names += stops.get(i).getName() + ", ";
                } else
                {
                    names = stops.get(i).getName();
                }
            }
        }

        return "StopObject{" +
                "stop=" + names +
                ", purpose=" + purposeString +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(purpose);

	    stopsString = new ArrayList<>();

        for (int i = 0; i<stops.size(); i++)
        {
            stopsString.add(i, stops.get(i).toJsonString());
        }
        dest.writeStringList(stopsString);

    }

    public static final Parcelable.Creator<StopObject> CREATOR = new Parcelable.Creator<StopObject>()
    {
        public StopObject createFromParcel(Parcel in)
        {
            return new StopObject(in);
        }

        @Override
        public StopObject[] newArray(int size) {
            return new StopObject[size];
        }
    };


    private StopObject(Parcel in)
    {
        purpose = in.readInt();

	    stopsString = new ArrayList<>();
	    in.readStringList(stopsString);
	    stops = new ArrayList<>();

        for (int i =0; i < stopsString.size(); i++)
        {
            Stop stop = new Stop();
            stop.jsonStringToObject(stopsString.get(i));
            stops.add(stop);
        }
    }



}

package com.main.envibus.data;

import java.util.ArrayList;

/**
 * Created by KraKk on 15/11/2015.
 */
public class StopObject
{
    private static final String TAG = StopObject.class.getSimpleName();

    //Object used during search, here we define if it's the destination or the origin.
    //Origin = 0;
    //Destination = 1;
    private int purpose;
    private ArrayList<Stop> stops;

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
}

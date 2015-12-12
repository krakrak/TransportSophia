package com.main.envibus.webservice;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by KraKk on 14/11/2015.
 */
public class WsTask extends AsyncTask<URL, Void, String> {

    protected String getASCIIContentFromEntity(HttpURLConnection conn) throws IllegalStateException, IOException
    {
        InputStream in = new BufferedInputStream(conn.getInputStream());

        return WsUtils.toString(in);

    }
    @Override
    protected String doInBackground(URL... urls) {
            int count = urls.length;
        Log.d(WsTask.class.getName(), "Number of URLS "+ count);
        String response = new String();
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
            Log.e(WsTask.class.getSimpleName(), "Decoding error" + e.toString());
        }
        return response;
    }

    @Override
    protected void onPostExecute(String s) {

        Log.d(WsTask.class.getSimpleName(), s);
    }
}

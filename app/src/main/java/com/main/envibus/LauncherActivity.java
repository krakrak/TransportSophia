package com.main.envibus;


import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.main.envibus.data.StopObject;
import com.main.envibus.fragment.DatePickerFragment;
import com.main.envibus.fragment.TimePickerFragment;
import com.main.envibus.utils.DateTimeManager;
import com.main.envibus.utils.StopDataManager;
import com.main.envibus.webservice.WsUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;


public class LauncherActivity extends AppCompatActivity {

    private static final String TAG = LauncherActivity.class.getSimpleName();
    private TextView dateDisplay;
    private TextView timeDisplay;
    private AutoCompleteTextView fromText;
    private AutoCompleteTextView toText;

    private int hour;
    private int minute;
    private int day;
    private int month;
    private int year;
    private TextView errorView;
    protected ArrayList<StopObject> stopObjects;
    protected Intent searchResults;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setCurrentDateAndTimeOnView();

        //Setting auto-complete
        fromText = (AutoCompleteTextView) findViewById(R.id.editDepart);
        toText = (AutoCompleteTextView) findViewById(R.id.editArriver);

	}

    public boolean onCreateOptionsMenu (Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setCurrentDateAndTimeOnView() {

        dateDisplay = (TextView) findViewById(R.id.date);
        timeDisplay = (TextView) findViewById(R.id.time);

        final Calendar c = Calendar.getInstance();

        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        timeDisplay.setText(new StringBuilder()
                .append(DateTimeManager.pad(hour))
                .append(":")
                .append(DateTimeManager.pad(minute)));

        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);

        dateDisplay.setText(
                new StringBuilder()
                        .append(DateTimeManager.pad(day))
                        .append("/").append(DateTimeManager.pad(month))
                        .append("/")
                        .append(year)
        );
    }

    public void showTimePickerDialog(View view) {
        TimePickerFragment timePicker = new TimePickerFragment();

        Bundle arguments = new Bundle();
        arguments.putInt("hour", hour);
        arguments.putInt("minutes", minute);

        timePicker.setCallback(onTime);

        timePicker.setArguments(arguments);
        timePicker.show(getFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View view) {
        DatePickerFragment datePicker = new DatePickerFragment();

        Bundle arguments = new Bundle();

        arguments.putInt("year", year);
        arguments.putInt("month", month);
        arguments.putInt("day", day);
        datePicker.setArguments(arguments);

        datePicker.setCallBack(onDate);
        datePicker.show(getFragmentManager(), "datePicker");
    }

    OnTimeSetListener onTime = new OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            updateTimeDisplay (hourOfDay, minute);
        }
    };

    private void updateTimeDisplay(int hour, int minute) {

        timeDisplay.setText(new StringBuilder()
                .append(DateTimeManager.pad(hour))
                .append(":")
                .append(DateTimeManager.pad(minute)));
    }

    OnDateSetListener onDate = new OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            updateDateDisplay(year, monthOfYear, dayOfMonth);
        }
    };

    private void updateDateDisplay(int year, int month, int day){

        dateDisplay.setText(
                new StringBuilder()
                        .append(DateTimeManager.pad(day))
                        .append("/").append(DateTimeManager.pad(month))
                        .append("/")
                        .append(year)
        );
    }

    public void doSearch(View view) {

        Log.v(TAG, "Search button Clicked !");
        errorView = (TextView) findViewById(R.id.error);

        searchResults = new Intent(this, ResultsActivity.class);

        searchResults.putExtra("year", year);
        searchResults.putExtra("month", month);
        searchResults.putExtra("day", day);
        searchResults.putExtra("hour", hour);
        searchResults.putExtra("minute", minute);

        fromText = (AutoCompleteTextView) findViewById(R.id.editDepart);
        toText = (AutoCompleteTextView) findViewById(R.id.editArriver);

        String from = fromText.getText().toString();
        String to = toText.getText().toString();

        try {

            URL busStopsTo = new URL("http://www.ceparou06.fr/WebServices/RestService/api/transport/v1/SearchPointsByCostWithOptions/json?key=TSI006&keywords="+ URLEncoder.encode(from, "utf-8")
            +"&maxItems=10&pointTypes=0" + "&categories=0");
            URL busStopsFrom = new URL("http://www.ceparou06.fr/WebServices/RestService/api/transport/v1/SearchPointsByCostWithOptions/json?key=TSI006&keywords="+URLEncoder.encode(to, "utf-8")
            +"&maxItems=10&pointTypes=0" +
                                               "&categories=0");
            new GetStopsTask().execute(busStopsFrom, busStopsTo);
        }catch (MalformedURLException mue) {
            Log.e(TAG, "Malformed url ");
            mue.printStackTrace();
        } catch ( UnsupportedEncodingException uee)
        {
	        Log.e(TAG, "Impossible to encode search parameters");
            uee.printStackTrace();
        }

        if(!Objects.equals(from, "") && !Objects.equals(to, "")) {
            searchResults.putExtra("from", from);
            searchResults.putExtra("destination", to);

            //errorView.setText("");
            //startActivity(searchResults);
        } else
        {
            errorView.setText("Arrêt de départ ou d'arriver manquant");
        }


    }

    private class GetStopsTask extends AsyncTask<URL, Void, Void>
    {
        private ArrayList<String> responses = new ArrayList<>();

        protected String getASCIIContentFromEntity(HttpURLConnection conn) throws IllegalStateException, IOException
        {
            InputStream in = new BufferedInputStream(conn.getInputStream());

            return WsUtils.toString(in);

        }
        @Override
        protected Void doInBackground(URL... urls) {
            int count = urls.length;
            Log.d(TAG, "Number of URLS "+ count);
            try
            {
                for (int i =0; i<2; i++)
                {
                    URL url = urls[i];
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    String response = getASCIIContentFromEntity(con);
                    responses.add(response);
                }
            } catch (IOException e) {
                Log.e(TAG, "Decoding error" + e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {

            stopObjects = new ArrayList<>();
            if(!responses.isEmpty()) {

                for(int i = 0; i< responses.size(); i++)
                {
                    stopObjects.add(StopDataManager.toStopObject(i, responses.get(i)));
                }
            }

            errorView.append("From : \n" + responses.get(1) + "To \n" + responses.get(0));
            if (searchResults != null)
            {
                searchResults.putParcelableArrayListExtra("stopObjects", stopObjects);
                startActivity(searchResults);
            }
            Log.d(TAG, "onPostExecute : From " + responses.get(1) + "To" + responses.get(0));
        }
    }
}
package com.main.envibus;


import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.main.envibus.fragment.DatePickerFragment;
import com.main.envibus.fragment.TimePickerFragment;
import com.main.envibus.utils.DateTimeManager;
import com.main.envibus.webservice.WsTask;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Objects;


public class LauncherActivity extends AppCompatActivity {

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

        String [] stops = getResources().getStringArray(R.array.stops_array);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stops);

        try {
            URL busStops = new URL("http://www.ceparou06.fr/WebServices/RestService/api/transport/v1/SearchPointsByCostWithOptions/json?key=TSI006&keywords=Inria&maxItems=3&pointTypes=0&categories=0");
            new WsTask().execute(busStops);
        }catch (MalformedURLException mue) {
            Log.e(LauncherActivity.class.getSimpleName(), "Malformed url ");
            mue.printStackTrace();
        }
        fromText.setAdapter(adapter);
        toText.setAdapter(adapter);
	}

    public boolean onCreateOptionsMenu (Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item) {

        int id = item.getItemId();

        if( id == R.id.action_settings) {
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

        Log.v("LAUNCHER", "Search button Clicked !");
        errorView = (TextView) findViewById(R.id.error);

        Intent searchResults = new Intent(this, ResultsActivity.class);

        searchResults.putExtra("year", year);
        searchResults.putExtra("month", month);
        searchResults.putExtra("day", day);
        searchResults.putExtra("hour", hour);
        searchResults.putExtra("minute", minute);

        fromText = (AutoCompleteTextView) findViewById(R.id.editDepart);
        toText = (AutoCompleteTextView) findViewById(R.id.editArriver);

        String from = fromText.getText().toString();
        String to = toText.getText().toString();

        if(!Objects.equals(from, "") && !Objects.equals(to, "")) {
            searchResults.putExtra("from", from);
            searchResults.putExtra("destination", to);

            errorView.setText("");
            startActivity(searchResults);
        } else
        {
            errorView.setText("Arrêt de départ ou d'arriver manquant");
        }
    }
}
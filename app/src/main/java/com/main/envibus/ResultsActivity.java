package com.main.envibus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.main.envibus.utils.DateTimeManager;

import java.util.Calendar;

public class ResultsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView dateDisplay;
    private TextView timeDisplay;
    private AutoCompleteTextView fromText;
    private AutoCompleteTextView toText;

    private int hour;
    private int minute;
    private int day;
    private int month;
    private int year;

    private String textFrom;
    private String textDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent fromLauncher = getIntent();

        final Calendar c = Calendar.getInstance();

        //Default values are the current time and date
        hour = fromLauncher.getIntExtra("hour", c.get(Calendar.HOUR_OF_DAY));
        minute = fromLauncher.getIntExtra("minute", c.get(Calendar.MINUTE));
        day = fromLauncher.getIntExtra("day", c.get(Calendar.DAY_OF_MONTH));
        month = fromLauncher.getIntExtra("month", c.get(Calendar.MONTH));
        year = fromLauncher.getIntExtra("year", c.get(Calendar.YEAR));
        textFrom = fromLauncher.getStringExtra("from");
        textDestination = fromLauncher.getStringExtra("destination");

        Log.v("RESULTS", "From "+textFrom+" to "+textDestination);

        TextView summaryText = (TextView) findViewById(R.id.summary);

        if(textFrom != null && textDestination !=null) {

            summaryText.setText("Recherche de "+textFrom.toUpperCase() +" à " +textDestination.toUpperCase() +" pour le");

        }


        displayTimeAndDate();

    }

    private void displayTimeAndDate() {

        dateDisplay = (TextView) findViewById(R.id.date);
        timeDisplay = (TextView) findViewById(R.id.time);

        dateDisplay.setText(DateTimeManager.pad(day)+"/"+
                            DateTimeManager.pad(month)+"/"+year);

        timeDisplay.setText(" à " +DateTimeManager.pad(hour) + ":" + DateTimeManager.pad(minute));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

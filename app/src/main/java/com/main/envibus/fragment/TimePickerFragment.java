package com.main.envibus.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.text.format.DateFormat;

import java.util.Calendar;

/**
 * Created by KraKk on 20/09/2015.
 */
public class TimePickerFragment extends DialogFragment {

    OnTimeSetListener onTimeSet;

    private int hour, minutes;

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();

        hour = c.get(Calendar.HOUR_OF_DAY);
        minutes = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), onTimeSet, hour, minutes, DateFormat.is24HourFormat(getActivity()));
    }
    public void setArguments(Bundle arguments) {
        super.setArguments(arguments);
        hour = arguments.getInt("hour");
        minutes = arguments.getInt("minutes");
    }

    public void setCallback(OnTimeSetListener onTime) {

        onTimeSet = onTime;
    }


}

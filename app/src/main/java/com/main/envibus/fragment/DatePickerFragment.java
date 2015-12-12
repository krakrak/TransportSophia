package com.main.envibus.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;

import java.util.Calendar;


/**
 * Created by KraKk on 20/09/2015.
 */
public class DatePickerFragment extends DialogFragment {

    OnDateSetListener onDateSet;

    private int year, month, day;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();

        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), onDateSet, year, month, day);
    }

    public void setArguments(Bundle arguments){
        super.setArguments(arguments);
        year = arguments.getInt("year");
        month = arguments.getInt("month");
        day = arguments.getInt("day");
    }

    public void setCallBack (OnDateSetListener onDate) {

        onDateSet = onDate;
    }


}

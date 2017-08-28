package com.example.nazar.mafia;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

public class MyDialogDatePicker extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private static int requestCodeDialog;

    public static MyDialogDatePicker newInstance(String myDate, int requestCode) {
        requestCodeDialog = requestCode;
        MyDialogDatePicker frag = new MyDialogDatePicker();
        Bundle args = new Bundle();
        args.putLong("myDate", DBHelper.convertStringDataToLong(myDate));
        args.putInt("requestCode", requestCode);
        frag.setArguments(args);
        return frag;
    }

    //слухач для повернення даних
    public interface MyDialogDatePickerListener {
        void dateInMyDialogDatePicker(long myDate, int requestCode);
    }
    MyDialogDatePickerListener myDialogDatePickerListener;

    //для слухача
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        Activity activity = context instanceof Activity ? (Activity) context : null;
        try {
            myDialogDatePickerListener = (MyDialogDatePickerListener)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            try {
                myDialogDatePickerListener = (MyDialogDatePickerListener)activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
            }
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        long myDate = getArguments().getLong("myDate");
        Calendar c = Calendar.getInstance();
        if (myDate != 0)
            c.setTimeInMillis(myDate);

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String myDate;
        String myDay = (dayOfMonth < 10) ? "0" + String.valueOf(dayOfMonth) + "." : String.valueOf(dayOfMonth) + ".";
        String myMonth = (monthOfYear + 1 < 10) ? "0" + String.valueOf(monthOfYear + 1) + "." : String.valueOf(monthOfYear + 1) + ".";
        String myYear = String.valueOf(year);
        myDate = myDay + myMonth + myYear;

        long result = DBHelper.convertStringDataToLong(myDate);
        myDialogDatePickerListener.dateInMyDialogDatePicker(result, requestCodeDialog);
    }
}



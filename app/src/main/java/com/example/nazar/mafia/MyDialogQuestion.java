package com.example.nazar.mafia;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

public class MyDialogQuestion extends DialogFragment {
    private String title = null;
    private String message = null;
    private String btnTextPositive = null;
    private String btnTextNegative = null;
    private String btnTextNeutral = null;
    private int icon = 0;

    //якщо ми маємо 2 питання на сторінці, то треба додати при побудові діалогу,
    // щось типу reqestCode і в слухачі повертати цей reqestCode

    //слухач для повернення даних
    public interface MyDialogListener {
        void clickPositiveButton(Boolean res);
        void clickNegativeButton(Boolean res);
        void clickNeutralButton(Boolean res);
    }
    MyDialogListener myDialogListener;

    //для слухача
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        Activity activity = context instanceof Activity ? (Activity) context : null;
        try {
            myDialogListener = (MyDialogListener)activity;
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
                myDialogListener = (MyDialogListener)activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
            }
        }
    }
    public void setTitileDialog(String _title){
        this.title = _title;
    }

    public void setIconDialog(int _icon){
        this.icon = _icon;
    }

    public void setMessageDialog(String _message){
        this.message = _message;
    }

    public void setTextBtnPositive(String _btnTextPositive){
        this.btnTextPositive = _btnTextPositive;
    }

    public void setTextBtnNegative(String _btnTextNegative){
        this.btnTextNegative = _btnTextNegative;
    }

    public void setTextBtnNeutral(String _btnTextNeutral){
        this.btnTextNeutral = _btnTextNeutral;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //створюємо діалог
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
        if(icon != 0)
            adb.setIcon(icon);
        if (title != null)
            adb.setTitle(title);
        if (message != null)
            adb.setMessage(message);
        if (btnTextPositive != null)
            adb.setPositiveButton(btnTextPositive, myDialogQuestionClickListener);
        if (btnTextNegative != null)
            adb.setNegativeButton(btnTextNegative, myDialogQuestionClickListener);
        if (btnTextNeutral != null)
            adb.setNeutralButton(btnTextNeutral, myDialogQuestionClickListener);

        return adb.create();
    }

    DialogInterface.OnClickListener myDialogQuestionClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            switch (i) {
                case Dialog.BUTTON_POSITIVE:
                    myDialogListener.clickPositiveButton(true);
                    break;
                case Dialog.BUTTON_NEGATIVE:
                    myDialogListener.clickNegativeButton(true);
                    break;
                case Dialog.BUTTON_NEUTRAL:
                    myDialogListener.clickNeutralButton(true);
                    break;
            }
            getDialog().dismiss();
        }
    };
}


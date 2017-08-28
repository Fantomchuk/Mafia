package com.example.nazar.mafia;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MyDialogPassword extends DialogFragment {

    public static MyDialogPassword newInstance(String nickName) {
        MyDialogPassword frag = new MyDialogPassword();
        Bundle args = new Bundle();
        args.putString("nickName", nickName);
        frag.setArguments(args);
        return frag;
    }
    //слухач для повернення даних
    public interface MyDialogPasswordListener {
        void dataInMyDialogPassword(String nickNameDialog, String passwordDialog);
    }
    MyDialogPasswordListener myDialogListener;

    //для слухача
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        Activity activity = context instanceof Activity ? (Activity) context : null;
        try {
            myDialogListener = (MyDialogPasswordListener)activity;
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
                myDialogListener = (MyDialogPasswordListener)activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
            }
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String nickName = getArguments().getString("nickName");

        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
        adb.setTitle(R.string.dialog_pass_title_5);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_password, null);
        adb.setView(v);

        final EditText eT_pas_1 = (EditText) v.findViewById(R.id.eT_pas_1);
        if(nickName != null) {
            eT_pas_1.setText(nickName);
        }
        final EditText eT_pas_2 = (EditText) v.findViewById(R.id.eT_pas_2);
        Button bT_pas_1 = (Button)   v.findViewById(R.id.bT_pas_1);
        bT_pas_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nickName, password;
                nickName = eT_pas_1.getText().toString();
                password = eT_pas_2.getText().toString();
                if (TextUtils.isEmpty(nickName))
                    nickName = "";
                else if (TextUtils.isEmpty(password))
                    password = "";
                myDialogListener.dataInMyDialogPassword(nickName, password);
                getDialog().dismiss();
            }
        });
        return adb.create();
    }
}

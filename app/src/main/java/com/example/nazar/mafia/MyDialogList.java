package com.example.nazar.mafia;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MyDialogList extends DialogFragment {


    private String title = null;
    private int icon = 0;
    private int layoutResourse = 0;
    private String[] dataAdapter;

    //слухач для повернення даних
    public interface MyDialogListListener {
        void positionInList(int position);
    }
    MyDialogListListener myDialogListListener;

    //для слухача
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        Activity activity = context instanceof Activity ? (Activity) context : null;
        try {
            myDialogListListener = (MyDialogListListener)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement MyDialogListListener");
        }
    }
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            try {
                myDialogListListener = (MyDialogListListener)activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() + " must implement MyDialogListListener");
            }
        }
    }

    public void setTitileDialog(String _title){
        this.title = _title;
    }

    public void setIconDialog(int _icon){
        this.icon = _icon;
    }

    public void setLayoutDialog(int _layoutResourse){
        this.layoutResourse = _layoutResourse;
    }

    public void setDataAdapterDialog(String[] _dataAdapter){
        if (_dataAdapter != null)
            this.dataAdapter = _dataAdapter;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //створюємо діалог
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
        if (title != null)
            adb.setTitle(title);
        if(icon != 0)
            adb.setIcon(icon);

        if (dataAdapter != null) {
            if(layoutResourse == 0)
                layoutResourse = android.R.layout.simple_list_item_1;

            LinearLayout view = new LinearLayout(getActivity());
            view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams lparams;
            lparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lparams.setMargins(20, 20, 20, 20);
            ListView lv = new ListView(getActivity());

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), layoutResourse, dataAdapter);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    myDialogListListener.positionInList(i);
                    getDialog().dismiss();
                }
            });

            view.addView(lv, lparams);
            adb.setView(view);
        }else{
            adb.setMessage("Список пустий");
        }
        return adb.create();
    }
}

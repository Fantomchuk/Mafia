package com.example.nazar.mafia;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class LeaderNewPeriod extends AppCompatActivity {

    String dateStart, dateEnd;
    TextView tv_LeaderNewPeriod_info;
    String periodName;
    int minCountGames;
    int idWinner;
    private static final int DIALOG_ADD_PERIOD = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_new_period);

        Intent getIntent = getIntent();
        dateStart = getIntent.getStringExtra(NewTimePeriod.KEY_FOR_DATA_START);
        dateEnd = getIntent.getStringExtra(NewTimePeriod.KEY_FOR_DATA_END);
        minCountGames = getIntent.getIntExtra(NewTimePeriod.KEY_FOR_MIN_COUNT_GAMES, 1);

        TextView tv_dateStart= (TextView) findViewById(R.id.tv_LeaderNewPeriod_1_2);
        tv_dateStart.setText(dateStart);
        TextView tv_dateEnd= (TextView) findViewById(R.id.tv_LeaderNewPeriod_2_2);
        tv_dateEnd.setText(dateEnd);
        TextView tv_minCountGames= (TextView) findViewById(R.id.tv_LeaderNewPeriod_3_2);
        tv_minCountGames.setText(String.valueOf(minCountGames));

        tv_LeaderNewPeriod_info = (TextView) findViewById(R.id.tv_LeaderNewPeriod_info);

    }

    public void onTextClickLeaderNewPeriod(View view) {
        tv_LeaderNewPeriod_info.setText("");
        switch (view.getId()){
            case R.id.img_LeaderNewPeriod_1:
                onBackPressed();
                break;
            case R.id.bt_LeaderNewPeriod_addPeriod:
                EditText et_periodName = (EditText) findViewById(R.id.et_LeaderNewPeriod_periodName);
                periodName = et_periodName.getText().toString();
                if(TextUtils.isEmpty(periodName)){
                    tv_LeaderNewPeriod_info.setText(R.string.LeaderNewPeriod_title_6);
                    return;
                }
                RatingHelper ratingHelper = new RatingHelper(LeaderNewPeriod.this, dateStart, dateEnd);
                idWinner = ratingHelper.getIdWhoIsWinner(minCountGames);
                if (idWinner == 0){
                    tv_LeaderNewPeriod_info.setText(R.string.ShowRating_title_11);
                    return;
                }
                showDialog(DIALOG_ADD_PERIOD);
                break;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ADD_PERIOD){
            //створюємо конструктор діалогу
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getText(R.string.NewTimePeriod_title_5) + periodName);
            builder.setMessage(getResources().getText(R.string.NewTimePeriod_title_6) + dateStart +
                    getResources().getText(R.string.NewTimePeriod_title_7) + dateEnd +
                    getResources().getText(R.string.NewTimePeriod_title_8) + minCountGames);
            builder.setIcon(android.R.drawable.ic_dialog_info);
            //додаємо кнопку позитивної відповіді
            builder.setPositiveButton(R.string.NewTimePeriod_title_4, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DB db = new DB(LeaderNewPeriod.this);
                    db.open();
                    db.addPeriodToDB(periodName, dateStart, dateEnd, idWinner, minCountGames);
                    db.close();
                }
            });
            //додаємо кнопку негативну відповідь
            builder.setNegativeButton(R.string.NewPlayer_title_13, null);
            builder.setCancelable(true);
            return builder.create();
        }
        return super.onCreateDialog(id);
    }
}

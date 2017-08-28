package com.example.nazar.mafia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LeaderNewPeriod extends AppCompatActivity implements MyDialogQuestion.MyDialogListener{

    String dateStart, dateEnd;
    TextView tv_LeaderNewPeriod_info;
    String periodName;
    int minCountGames;
    int idWinner;


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
                MyDialogQuestion dlg_add_period = new MyDialogQuestion();
                dlg_add_period.setTitileDialog(getResources().getText(R.string.NewTimePeriod_title_5) + periodName);
                dlg_add_period.setMessageDialog(getResources().getText(R.string.NewTimePeriod_title_6) + dateStart +
                        getResources().getText(R.string.NewTimePeriod_title_7) + dateEnd +
                        getResources().getText(R.string.NewTimePeriod_title_8) + minCountGames);
                dlg_add_period.setIconDialog(android.R.drawable.ic_dialog_info);
                dlg_add_period.setTextBtnPositive(getResources().getString(R.string.NewTimePeriod_title_4));
                dlg_add_period.setTextBtnNegative(getResources().getString(R.string.NewPlayer_title_13));
                dlg_add_period.show(getFragmentManager(), "dlg_add_period");
                break;
        }
    }

    @Override
    public void clickPositiveButton(Boolean res) {
        DB db = new DB(LeaderNewPeriod.this);
        db.open();
        db.addPeriodToDB(periodName, dateStart, dateEnd, idWinner, minCountGames);
        db.close();
        tv_LeaderNewPeriod_info.setText(getResources().getString(R.string.LeaderNewPeriod_title_7) + periodName);
    }

    @Override
    public void clickNegativeButton(Boolean res) {}

    @Override
    public void clickNeutralButton(Boolean res) {}
}

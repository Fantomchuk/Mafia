package com.example.nazar.mafia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NewTimePeriod extends AppCompatActivity implements MyDialogDatePicker.MyDialogDatePickerListener{

    public static final String KEY_FOR_DATA_START = "data_start_key";
    public static final String KEY_FOR_DATA_END = "data_end_key";
    public static final String KEY_FOR_MIN_COUNT_GAMES = "min_count_game_key";
    String dateStart = null,dateEnd = null;
    MyDialogDatePicker dlg_add_date_start,dlg_add_date_end;
    int minCountGames;


    TextView tv_NewTimePeriod_info;
    EditText eT_NewTimePeriod_numberGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_time_period);
        tv_NewTimePeriod_info = (TextView) findViewById(R.id.tv_NewTimePeriod_info);
        eT_NewTimePeriod_numberGame = (EditText) findViewById(R.id.eT_NewTimePeriod_numberGame);

        if(getIntent().getBooleanExtra(LeaderMain.KEY_FOR_LEADER, false)){
            findViewById(R.id.bt_NewTimePeriod_add_period).setVisibility(View.VISIBLE);
        }
    }

    public void onTextClickNewTimePeriod(View view) {
        tv_NewTimePeriod_info.setText("");
        switch (view.getId()){
            case R.id.img_NewTimePeriod_1:
                onBackPressed();
                break;
            case R.id.tv_NewTimePeriod_date_strart:
                dlg_add_date_start = MyDialogDatePicker.newInstance(dateStart, 1);
                dlg_add_date_start.show(getFragmentManager(), "dlg_add_date_start");
                break;
            case R.id.tv_NewTimePeriod_date_end:
                dlg_add_date_end = MyDialogDatePicker.newInstance(dateEnd, 2);
                dlg_add_date_end.show(getFragmentManager(), "dlg_add_date_end");
                break;
            case R.id.bt_NewTimePeriod_search:
                if (!checkingData()) return;
                Intent intent = new Intent(this, RatingPeriod.class);
                intent.putExtra(KEY_FOR_DATA_START, dateStart);
                intent.putExtra(KEY_FOR_DATA_END, dateEnd);
                intent.putExtra(KEY_FOR_MIN_COUNT_GAMES, minCountGames);
                startActivity(intent);
                break;
            case R.id.bt_NewTimePeriod_add_period:
                if (!checkingData()) return;
                Intent intentN = new Intent(this, LeaderNewPeriod.class);
                intentN.putExtra(KEY_FOR_DATA_START, dateStart);
                intentN.putExtra(KEY_FOR_DATA_END, dateEnd);
                intentN.putExtra(KEY_FOR_MIN_COUNT_GAMES, minCountGames);
                startActivity(intentN);
                break;
        }
    }

    private boolean checkingData(){
        if(dateStart==null || dateEnd==null){
            tv_NewTimePeriod_info.setText(getResources().getString(R.string.ShowGame_title_4));
            return false;
        }
        String countGames = eT_NewTimePeriod_numberGame.getText().toString();
        if (TextUtils.isEmpty(countGames) || Integer.valueOf(countGames) == 0) {tv_NewTimePeriod_info.setText(R.string.NewGame_title_8);return false;}
        minCountGames = Integer.valueOf(countGames);
        return true;
    }

    @Override
    public void dateInMyDialogDatePicker(long myDate, int requestCode) {
        if (requestCode == 1) {
            dateStart = PlayerPage.convertLongToData(myDate);
            ((TextView) findViewById(R.id.tv_NewTimePeriod_date_strart)).setText(dateStart);
        }
        else if (requestCode == 2) {
            dateEnd = PlayerPage.convertLongToData(myDate);
            ((TextView) findViewById(R.id.tv_NewTimePeriod_date_end)).setText(dateEnd);
        }
    }
}

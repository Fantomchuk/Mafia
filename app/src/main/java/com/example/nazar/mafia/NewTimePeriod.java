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

public class NewTimePeriod extends AppCompatActivity {

    private static final int DIALOG_ADD_DATE_START = 1;
    private static final int DIALOG_ADD_DATE_END = 2;

    public static final String KEY_FOR_DATA_START = "data_start_key";
    public static final String KEY_FOR_DATA_END = "data_end_key";
    public static final String KEY_FOR_MIN_COUNT_GAMES = "min_count_game_key";
    String dateStart="",dateEnd="";
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
                showDialog(DIALOG_ADD_DATE_START);
                break;
            case R.id.tv_NewTimePeriod_date_end:
                showDialog(DIALOG_ADD_DATE_END);
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
                Intent intentN = new Intent(NewTimePeriod.this, LeaderNewPeriod.class);
                intentN.putExtra(KEY_FOR_DATA_START, dateStart);
                intentN.putExtra(KEY_FOR_DATA_END, dateEnd);
                intentN.putExtra(KEY_FOR_MIN_COUNT_GAMES, minCountGames);
                startActivity(intentN);
                break;
        }
    }

    private boolean checkingData(){
        if(dateStart.equals("") || dateEnd.equals("")){
            tv_NewTimePeriod_info.setText(getResources().getString(R.string.ShowGame_title_4));
            return false;
        }
        String countGames = eT_NewTimePeriod_numberGame.getText().toString();
        if (TextUtils.isEmpty(countGames) || Integer.valueOf(countGames) == 0) {tv_NewTimePeriod_info.setText(R.string.NewGame_title_8);return false;}
        minCountGames = Integer.valueOf(countGames);
        return true;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ADD_DATE_START) {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int yearOfDialog, int monthOfDialog, int dayOfDialog) {
                    String day = (dayOfDialog < 10) ? "0" + String.valueOf(dayOfDialog) + "." : String.valueOf(dayOfDialog) + ".";
                    String month = (monthOfDialog + 1 < 10) ? "0" + String.valueOf(monthOfDialog + 1) + "." : String.valueOf(monthOfDialog + 1) + ".";
                    String year = String.valueOf(yearOfDialog);
                    dateStart = day + month + year;
                    TextView tv = (TextView) findViewById(R.id.tv_NewTimePeriod_date_strart);
                    tv.setText(dateStart);
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dpd.setCancelable(true);
            return dpd;
        }
        if (id == DIALOG_ADD_DATE_END) {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int yearOfDialog, int monthOfDialog, int dayOfDialog) {
                    String day = (dayOfDialog < 10) ? "0" + String.valueOf(dayOfDialog) + "." : String.valueOf(dayOfDialog) + ".";
                    String month = (monthOfDialog + 1 < 10) ? "0" + String.valueOf(monthOfDialog + 1) + "." : String.valueOf(monthOfDialog + 1) + ".";
                    String year = String.valueOf(yearOfDialog);
                    dateEnd = day + month + year;
                    TextView tv = (TextView) findViewById(R.id.tv_NewTimePeriod_date_end);
                    tv.setText(dateEnd);
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dpd.setCancelable(true);
            return dpd;
        }
        return super.onCreateDialog(id);
    }
}

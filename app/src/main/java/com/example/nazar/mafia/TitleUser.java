package com.example.nazar.mafia;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TitleUser extends AppCompatActivity {

    final public static String KEY_FOR_INTENT = "title";
    final public static String KEY_FOR_INTENT_1 = "string";
    Boolean isLeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_user);

        Intent intent_get = getIntent();
        isLeader = intent_get.getBooleanExtra(LeaderMain.KEY_FOR_LEADER, false);
    }

    public void onTextClickTitleUser(View view) {

        Intent intent = new Intent(this, PlayersList.class);
        if (isLeader) intent.putExtra(LeaderMain.KEY_FOR_LEADER, true);
        TextView tV = null;
        switch (view.getId()){
            case R.id.tV_TitleUser_1:
                tV = (TextView)findViewById(R.id.tV_TitleUser_1);
                intent.putExtra(KEY_FOR_INTENT, DBHelper.KEY_TITLE[0]);
                break;
            case R.id.tV_TitleUser_2:
                tV = (TextView)findViewById(R.id.tV_TitleUser_2);
                intent.putExtra(KEY_FOR_INTENT, DBHelper.KEY_TITLE[1]);
                break;
            case R.id.tV_TitleUser_3:
                tV = (TextView)findViewById(R.id.tV_TitleUser_3);
                intent.putExtra(KEY_FOR_INTENT, DBHelper.KEY_TITLE[2]);
                break;
            case R.id.tV_TitleUser_4:
                tV = (TextView)findViewById(R.id.tV_TitleUser_4);
                intent.putExtra(KEY_FOR_INTENT, DBHelper.KEY_TITLE[3]);
                break;
            case R.id.tV_TitleUser_5:
                tV = (TextView)findViewById(R.id.tV_TitleUser_5);
                intent.putExtra(KEY_FOR_INTENT, DBHelper.KEY_TITLE[4]);
                break;
            case R.id.tV_TitleUser_6:
                tV = (TextView)findViewById(R.id.tV_TitleUser_6);
                intent.putExtra(KEY_FOR_INTENT, DBHelper.KEY_TITLE[5]);
                break;
            case R.id.tV_TitleUser_7:
                tV = (TextView)findViewById(R.id.tV_TitleUser_7);
                intent.putExtra(KEY_FOR_INTENT, DBHelper.KEY_TITLE[6]);
                break;
            case R.id.img_TitleUser_1:
                onBackPressed();
                break;
        }
        if (tV != null) {
            intent.putExtra(KEY_FOR_INTENT_1, tV.getText().toString());
            startActivity(intent);
        }

    }
}

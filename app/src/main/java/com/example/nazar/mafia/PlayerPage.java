package com.example.nazar.mafia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class PlayerPage extends AppCompatActivity {

    private static final int REQUEST_CODE_EDIT = 1;
    TextView tv_playerPage_nickName, tv_playerPage_name, tv_playerPage_surname,
            tv_playerPage_userStartPlay, tv_playerPage_userTitle;
    Boolean isLeader;
    Intent intent_get;
    Button bt_playerPage_edit;
    String user_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_page);

        tv_playerPage_nickName = (TextView) findViewById(R.id.tv_playerPage_nickName);
        tv_playerPage_name = (TextView) findViewById(R.id.tv_playerPage_name);
        tv_playerPage_surname = (TextView) findViewById(R.id.tv_playerPage_surname);
        tv_playerPage_userStartPlay = (TextView) findViewById(R.id.tv_playerPage_userStartPlay);
        tv_playerPage_userTitle = (TextView) findViewById(R.id.tv_playerPage_userTitle);

        intent_get = getIntent();
        isLeader = intent_get.getBooleanExtra(LeaderMain.KEY_FOR_LEADER, false);
        String nickName = intent_get.getStringExtra(PlayersList.KEY_FOR_NICK_NAME);
        String name = intent_get.getStringExtra(PlayersList.KEY_FOR_NAME);
        String surname = intent_get.getStringExtra(PlayersList.KEY_FOR_SURNAME);
        long userStartPlay = intent_get.getLongExtra(PlayersList.KEY_FOR_USER_START_PLAY, 0);
        user_title = intent_get.getStringExtra(TitleUser.KEY_FOR_INTENT);

        tv_playerPage_nickName.setText(nickName);
        tv_playerPage_name.setText(name);
        tv_playerPage_surname.setText(surname);
        tv_playerPage_userStartPlay.setText(convertLongToData(userStartPlay));
        tv_playerPage_userTitle.setText(user_title);

        bt_playerPage_edit = (Button) findViewById(R.id.bt_playerPage_edit);
        if (user_title.equals(DBHelper.KEY_TITLE[6])) {
            bt_playerPage_edit.setText(R.string.EditLeader_title_2);
        }
        if (isLeader) {
            bt_playerPage_edit.setVisibility(View.VISIBLE);
        }
    }

    public void onTextClickPlayerPage(View view) {
        switch (view.getId()) {
            case R.id.img_playerPage_1:
                onBackPressed();
                break;
            case R.id.bt_playerPage_edit:
                if (user_title.equals(DBHelper.KEY_TITLE[6])) {
                    Intent intent = new Intent(intent_get);
                    intent.setClass(this, EditLeader.class);
                    startActivityForResult(intent, REQUEST_CODE_EDIT);
                } else {
                    Intent intent = new Intent(intent_get);
                    intent.setClass(this, EditPlayer.class);
                    startActivityForResult(intent, REQUEST_CODE_EDIT);
                }
                break;
        }
    }

    public static String convertLongToData(long userStartPlay) {
        SimpleDateFormat formating = new SimpleDateFormat("dd.MM.yyyy");
        return String.valueOf(formating.format(userStartPlay));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDIT)
            if (resultCode == RESULT_OK)
                finish();
    }
}

package com.example.nazar.mafia;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChoiceKilled extends AppCompatActivity {

    private static final int REQUEST_KILLED_1 = 1;
    private static final int REQUEST_KILLED_2 = 2;
    private static final int REQUEST_KILLED_3 = 3;

    TextView tv_ChoiceKilled_info;
    TextView tv_ChoiceKilled_1night_3, tv_ChoiceKilled_2night_3, tv_ChoiceKilled_3night_3;
    ArrayList<String> nickName = new ArrayList<>(10);
    ArrayList<Integer> userId = new ArrayList<>(10);
    int idKilled_1_night, idKilled_2_night, idKilled_3_night;

    public static final String INTENT_ACTION_KILLED_1 = "intent_action_killed_1";
    public static final String INTENT_ACTION_KILLED_2 = "intent_action_killed_2";
    public static final String INTENT_ACTION_KILLED_3 = "intent_action_killed_3";

    public static final String KEY_IS_CHOICE = "is_choice_key";
    public static final String KEY_CHOICE_PLAYER_ID = "choice_player_id_key";

    Intent intent_choice, intent_get;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_killed);

        tv_ChoiceKilled_info = (TextView) findViewById(R.id.tv_ChoiceKilled_info);

        intent_get = getIntent();
        nickName = intent_get.getStringArrayListExtra(PlayersInGame.KEY_NICK_NAME_ARRAY_LIST);
        userId = intent_get.getIntegerArrayListExtra(PlayersInGame.KEY_USER_ID);

        idKilled_1_night = intent_get.getIntExtra(StartGame.KEY_KILLED_1_NIGHT, -1);
        idKilled_2_night = intent_get.getIntExtra(StartGame.KEY_KILLED_2_NIGHT, -1);
        idKilled_3_night = intent_get.getIntExtra(StartGame.KEY_KILLED_3_NIGHT, -1);

        tv_ChoiceKilled_1night_3 = (TextView) findViewById(R.id.tv_ChoiceKilled_1night_3);
        tv_ChoiceKilled_2night_3 = (TextView) findViewById(R.id.tv_ChoiceKilled_2night_3);
        tv_ChoiceKilled_3night_3 = (TextView) findViewById(R.id.tv_ChoiceKilled_3night_3);

        changeTextView(tv_ChoiceKilled_1night_3, idKilled_1_night);
        changeTextView(tv_ChoiceKilled_2night_3, idKilled_2_night);
        changeTextView(tv_ChoiceKilled_3night_3, idKilled_3_night);
    }

    private void changeTextView(TextView view, int idKilled) {
        switch (idKilled) {
            case -1:
                view.setText(getResources().getString(R.string.PlayersInGameRole_title_2));
                view.setTextColor(Color.argb(255, 255, 68, 68));
                view.setClickable(true);
                break;
            case 0:
                view.setText(getResources().getString(R.string.ChoiceKilled_title_6));
                view.setTextColor(Color.argb(255, 0, 0, 0));
                view.setClickable(false);
                break;
            default:
                view.setText( nickName.get( userId.indexOf(idKilled) ));
                view.setTextColor(Color.argb(255, 0, 0, 0));
                view.setClickable(false);
                break;
        }
    }

    public void onTextClickChoiceKilled(View view) {

        intent_choice = new Intent(intent_get);
        intent_choice.setClass(this, ChoicePlayer.class);
        intent_choice.putExtra(StartGame.KEY_KILLED_1_NIGHT, idKilled_1_night);
        intent_choice.putExtra(StartGame.KEY_KILLED_2_NIGHT, idKilled_2_night);
        intent_choice.putExtra(StartGame.KEY_KILLED_3_NIGHT, idKilled_3_night);

        switch (view.getId()) {
            case R.id.img_ChoiceKilled_1:
                onBackPressed();
                break;
            case R.id.tv_ChoiceKilled_1night_3:
                intent_choice.setAction(INTENT_ACTION_KILLED_1);
                startActivityForResult(intent_choice, REQUEST_KILLED_1);
                break;
            case R.id.tv_ChoiceKilled_2night_3:
                intent_choice.setAction(INTENT_ACTION_KILLED_2);
                startActivityForResult(intent_choice, REQUEST_KILLED_2);
                break;
            case R.id.tv_ChoiceKilled_3night_3:
                intent_choice.setAction(INTENT_ACTION_KILLED_3);
                startActivityForResult(intent_choice, REQUEST_KILLED_3);
                break;
            case R.id.bt_ChoiceKilled_cancel_killed:
                clearKilledPlayers();
                break;
            case R.id.bt_ChoiceKilled_next:
                onBackPressed();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data.getBooleanExtra(KEY_IS_CHOICE, false)) {
                tv_ChoiceKilled_info.setText(getResources().getString(R.string.ChoiceKilled_title_5));
                return;
            } else
                tv_ChoiceKilled_info.setText("");

            switch (requestCode) {
                case REQUEST_KILLED_1:
                    idKilled_1_night = data.getIntExtra(KEY_CHOICE_PLAYER_ID, -1);
                    changeTextView(tv_ChoiceKilled_1night_3, idKilled_1_night);
                    break;
                case REQUEST_KILLED_2:
                    idKilled_2_night = data.getIntExtra(KEY_CHOICE_PLAYER_ID, -1);
                    changeTextView(tv_ChoiceKilled_2night_3, idKilled_2_night);
                    break;
                case REQUEST_KILLED_3:
                    idKilled_3_night = data.getIntExtra(KEY_CHOICE_PLAYER_ID, -1);
                    changeTextView(tv_ChoiceKilled_3night_3, idKilled_3_night);
                    break;
            }
        }
        if (resultCode == RESULT_CANCELED) {
            tv_ChoiceKilled_info.setText(getResources().getString(R.string.PlayersInGameRole_title_11));
        }
    }

    private void clearKilledPlayers() {
        idKilled_1_night = -1; idKilled_2_night = -1; idKilled_3_night = -1;
        changeTextView(tv_ChoiceKilled_1night_3, idKilled_1_night);
        changeTextView(tv_ChoiceKilled_2night_3, idKilled_2_night);
        changeTextView(tv_ChoiceKilled_3night_3, idKilled_3_night);
    }

    @Override
    public void onBackPressed() {
        Intent intent_return = new Intent();
        intent_return.putExtra(StartGame.KEY_KILLED_1_NIGHT, idKilled_1_night);
        intent_return.putExtra(StartGame.KEY_KILLED_2_NIGHT, idKilled_2_night);
        intent_return.putExtra(StartGame.KEY_KILLED_3_NIGHT, idKilled_3_night);
        setResult(RESULT_OK, intent_return);
        finish();
    }
}

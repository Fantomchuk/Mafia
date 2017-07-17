package com.example.nazar.mafia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChoicePlayer extends AppCompatActivity {

    ArrayList<String> nickName = new ArrayList<>(10);
    ArrayList<Integer> userId = new ArrayList<>(10);
    ArrayList<String> data = new ArrayList<>(10);
    int idKilled_1_night, idKilled_2_night, idKilled_3_night;
    String action;
    TextView textView_ChoicePlayer;
    String[] number_players;
    ListView listView_ChoicePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_player);

        Intent intent_get = getIntent();
        action = intent_get.getAction();
        nickName = intent_get.getStringArrayListExtra(PlayersInGame.KEY_NICK_NAME_ARRAY_LIST);
        userId = intent_get.getIntegerArrayListExtra(PlayersInGame.KEY_USER_ID);

        idKilled_1_night = intent_get.getIntExtra(StartGame.KEY_KILLED_1_NIGHT, -1);
        idKilled_2_night = intent_get.getIntExtra(StartGame.KEY_KILLED_2_NIGHT, -1);
        idKilled_3_night = intent_get.getIntExtra(StartGame.KEY_KILLED_3_NIGHT, -1);

        textView_ChoicePlayer = (TextView) findViewById(R.id.textView_ChoicePlayer);
        switch (action) {
            case ChoiceKilled.INTENT_ACTION_KILLED_1:
                textView_ChoicePlayer.setText(getResources().getString(R.string.ChoiceKilled_title_1) + getResources().getString(R.string.PlayerPage_title_1));
                break;
            case ChoiceKilled.INTENT_ACTION_KILLED_2:
                textView_ChoicePlayer.setText(getResources().getString(R.string.ChoiceKilled_title_2) + getResources().getString(R.string.PlayerPage_title_1));
                break;
            case ChoiceKilled.INTENT_ACTION_KILLED_3:
                textView_ChoicePlayer.setText(getResources().getString(R.string.ChoiceKilled_title_3) + getResources().getString(R.string.PlayerPage_title_1));
                break;
            case StartGame.INTENT_ACTION_PREFERABLE:
                textView_ChoicePlayer.setText(getResources().getString(R.string.PlayersInGameList_title_3) + getResources().getString(R.string.PlayerPage_title_1));
                break;
        }

        number_players = getResources().getStringArray(R.array.PlayersInGame_title_number);
        for (int i = 0; i < 10; i++) {
            data.add(getResources().getText(R.string.PlayersInGame_title_00) + number_players[i] + " "
                    + getResources().getText(R.string.PlayerPage_title_1) + " " + nickName.get(i));
        }

        listView_ChoicePlayer = (ListView) findViewById(R.id.listView_ChoicePlayer);
        listView_ChoicePlayer.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_choise, data);
        listView_ChoicePlayer.setAdapter(adapter);

    }

    public void onTextClickChoicePlayer(View view) {
        switch (view.getId()) {
            case R.id.img_ChoicePlayer_1:
                onBackPressed();
                break;
            case R.id.bt_ChoicePlayer_add:
                returnResultForIntent(false);
                break;
            case R.id.bt_ChoicePlayer_none:
                returnResultForIntent(true);
                break;
        }
    }

    private void returnResultForIntent(Boolean isNone) {
        Intent intent_return = new Intent();
        if (isNone){
            intent_return.putExtra(ChoiceKilled.KEY_CHOICE_PLAYER_ID, 0);
            setResult(RESULT_OK, intent_return);
            finish();
        }
        if (listView_ChoicePlayer.getCheckedItemPosition() != -1) {
            int userIdAdapter = userId.get(listView_ChoicePlayer.getCheckedItemPosition());
            if(userIdAdapter == idKilled_1_night || userIdAdapter == idKilled_2_night || userIdAdapter == idKilled_3_night){
                intent_return.putExtra(ChoiceKilled.KEY_IS_CHOICE, true);
                setResult(RESULT_OK, intent_return);
            } else {
                intent_return.putExtra(ChoiceKilled.KEY_CHOICE_PLAYER_ID, userIdAdapter);
                setResult(RESULT_OK, intent_return);
            }
        } else
            setResult(RESULT_CANCELED, intent_return);
        finish();
    }
}

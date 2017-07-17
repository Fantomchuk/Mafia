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

public class ChoiceRole extends AppCompatActivity {

    ArrayList<String> nickName = new ArrayList<>(10);
    ArrayList<String> role = new ArrayList<>(10);
    String action;
    ListView listView_ChoiceRole;
    String[] number_players;
    ArrayList<String> data = new ArrayList<>(10);
    TextView textView_ChoiceRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_role);

        Intent intent_get = getIntent();
        action = intent_get.getAction();
        nickName = intent_get.getStringArrayListExtra(PlayersInGame.KEY_NICK_NAME_ARRAY_LIST);
        role = intent_get.getStringArrayListExtra(PlayersInGameRole.KEY_ROLE_ARRAY_LIST);

        textView_ChoiceRole = (TextView) findViewById(R.id.textView_ChoiceRole);
        switch (action) {
            case PlayersInGameRole.INTENT_ACTION_ROLE_DON:
                textView_ChoiceRole.setText(getResources().getString(R.string.PlayersInGameRole_title_1) + getResources().getString(R.string.PlayersInGameRole_title_12));
                break;
            case PlayersInGameRole.INTENT_ACTION_ROLE_SHERIF:
                textView_ChoiceRole.setText(getResources().getString(R.string.PlayersInGameRole_title_3) + getResources().getString(R.string.PlayersInGameRole_title_12));
                break;
            case PlayersInGameRole.INTENT_ACTION_ROLE_MAF:
                textView_ChoiceRole.setText(getResources().getString(R.string.PlayersInGameRole_title_13) + getResources().getString(R.string.PlayersInGameRole_title_12));
                break;
        }

        number_players = getResources().getStringArray(R.array.PlayersInGame_title_number);
        for (int i = 0; i < 10; i++) {
            data.add(getResources().getText(R.string.PlayersInGame_title_00) + number_players[i] + " "
                    + getResources().getText(R.string.PlayerPage_title_1) + " " + nickName.get(i));
        }

        listView_ChoiceRole = (ListView) findViewById(R.id.listView_ChoiceRole);
        listView_ChoiceRole.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_choise, data);
        listView_ChoiceRole.setAdapter(adapter);
    }

    public void onTextClickChoiceRole(View view) {
        switch (view.getId()) {
            case R.id.img_ChoiceRole_1:
                onBackPressed();
                break;
            case R.id.bt_ChoiceRole_add:
                returnResultForIntent();
                break;
        }
    }

    private void returnResultForIntent() {
        Intent intent_return = new Intent();
        if (listView_ChoiceRole.getCheckedItemPosition() != -1) {
            if ((role.get(listView_ChoiceRole.getCheckedItemPosition())).equals("0")) {
                switch (action) {
                    case PlayersInGameRole.INTENT_ACTION_ROLE_DON:
                        role.set(listView_ChoiceRole.getCheckedItemPosition(), PlayersInGameRole.KEY_ROLE_DON);
                        break;
                    case PlayersInGameRole.INTENT_ACTION_ROLE_SHERIF:
                        role.set(listView_ChoiceRole.getCheckedItemPosition(), PlayersInGameRole.KEY_ROLE_SHERIF);
                        break;
                    case PlayersInGameRole.INTENT_ACTION_ROLE_MAF:
                        role.set(listView_ChoiceRole.getCheckedItemPosition(), PlayersInGameRole.KEY_ROLE_MAF);
                        break;
                }
                intent_return.putStringArrayListExtra(PlayersInGameRole.KEY_ROLE_ARRAY_LIST, role);
                setResult(RESULT_OK, intent_return);
            } else {
                intent_return.putExtra(PlayersInGameRole.KEY_IS_CHOICE, true);
                setResult(RESULT_OK, intent_return);
            }
        } else
            setResult(RESULT_CANCELED, intent_return);
        finish();
    }
}

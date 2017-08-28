package com.example.nazar.mafia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerInGame extends AppCompatActivity {

    private static final String ATTRIBUTE_NICK_NAME_TEXT = "nickName_at";
    private static final String ATTRIBUTE_NAME_TEXT = "name_at";
    private static final String ATTRIBUTE_SURNAME_TEXT = "surname_at";

    AutoCompleteTextView aCtV_PlayerInGame;
    TextView tv_PlayerInGame_info;
    ArrayList<String> nickName = new ArrayList<>(10);
    ArrayList<Integer> userId = new ArrayList<>(10);
    int number_player;
    int userId_db = -1;
    String[] nickName_db, name_db, surname_db;
    ArrayList<Map<String, String>> data;
    int idLeaderFromUsersTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_in_game);

        Intent intent_get = getIntent();
        nickName = intent_get.getStringArrayListExtra(PlayersInGame.KEY_NICK_NAME_ARRAY_LIST);
        userId = intent_get.getIntegerArrayListExtra(PlayersInGame.KEY_USER_ID);
        number_player = intent_get.getIntExtra(PlayersInGame.KEY_NUMBER_PLAYER, 0);
        idLeaderFromUsersTable = intent_get.getIntExtra(NewGame.KEY_FOR_LEADER_ID, -1);

        nickName_db = intent_get.getStringArrayExtra(PlayersInGame.KEY_NICKNAME_DB);
        name_db = intent_get.getStringArrayExtra(PlayersInGame.KEY_NAME_DB);
        surname_db = intent_get.getStringArrayExtra(PlayersInGame.KEY_SURNAME_DB);

        tv_PlayerInGame_info = (TextView) findViewById(R.id.tv_PlayerInGame_info);
        aCtV_PlayerInGame = (AutoCompleteTextView) findViewById(R.id.aCtV_PlayerInGame);
        aCtV_PlayerInGame.setHint(getResources().getString(R.string.PlayersInGame_title_11) + (number_player + 1) +
                getResources().getString(R.string.PlayerInGame_title_1));

        data = new ArrayList<>(nickName_db.length);
        data = arrayForAdapter(data);

        String[] from = new String[]{ATTRIBUTE_NICK_NAME_TEXT, ATTRIBUTE_NAME_TEXT, ATTRIBUTE_SURNAME_TEXT};
        int[] to = new int[]{R.id.item_user_1, R.id.item_user_3, R.id.item_user_4};
        SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.item_user, from, to);
        aCtV_PlayerInGame.setAdapter(sAdapter);

        aCtV_PlayerInGame.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView nickName_item = (TextView) view.findViewById(R.id.item_user_1);
                TextView name_item = (TextView) view.findViewById(R.id.item_user_3);
                TextView surname_item = (TextView) view.findViewById(R.id.item_user_4);
                aCtV_PlayerInGame.setText(nickName_item.getText().toString());
                DB db = new DB(PlayerInGame.this);
                db.open();
                userId_db = db.getPlayerId(name_item.getText().toString(), surname_item.getText().toString());
                db.close();
                if (userId.contains(userId_db) || userId_db == idLeaderFromUsersTable){
                    aCtV_PlayerInGame.setText("");
                    if(userId_db == idLeaderFromUsersTable)
                        aCtV_PlayerInGame.setHint(nickName_item.getText().toString() + getResources().getString(R.string.PlayerInGame_title_4));
                    else
                        aCtV_PlayerInGame.setHint(nickName_item.getText().toString() + getResources().getString(R.string.PlayerInGame_title_3) + (1 + userId.indexOf(userId_db)));
                    userId_db = -1;
                }
            }
        });
    }

    private ArrayList<Map<String, String>> arrayForAdapter(ArrayList<Map<String, String>> data){
        Map<String, String> m;
        for (int i = 0; i < nickName_db.length; i++) {
            m = new HashMap<>();
            m.put(ATTRIBUTE_NICK_NAME_TEXT, nickName_db[i]);
            m.put(ATTRIBUTE_NAME_TEXT, name_db[i]);
            m.put(ATTRIBUTE_SURNAME_TEXT, surname_db[i]);
            data.add(m);
        }
        return data;
    }

    public void onTextClickPlayerInGame(View view) {
        switch (view.getId()) {
            case R.id.img_PlayerInGame_1:
                onBackPressed();
                break;
            case R.id.bt_PlayerInGame:
                if (TextUtils.isEmpty(aCtV_PlayerInGame.getText().toString()) || userId_db == -1) {
                    Intent intent_return = new Intent();
                    setResult(RESULT_CANCELED, intent_return);
                    finish();
                    return;
                } else {
                    nickName.set(number_player, aCtV_PlayerInGame.getText().toString());
                    userId.set(number_player, userId_db);
                    Intent intent_return = new Intent();
                    intent_return.putIntegerArrayListExtra(PlayersInGame.KEY_USER_ID, userId);
                    intent_return.putStringArrayListExtra(PlayersInGame.KEY_NICK_NAME_ARRAY_LIST, nickName);
                    setResult(RESULT_OK, intent_return);
                    finish();
                }
                break;
        }
    }
}

package com.example.nazar.mafia;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.R.attr.data;

public class PlayersInGame extends AppCompatActivity {

    public static final String ATTRIBUTE_NUMBER_TEXT = "number_text_at";
    private static final String ATTRIBUTE_NICKNAME_TEXT = "nickName_text_at";

    public static final String KEY_NICK_NAME_ARRAY_LIST = "nick_name_array_list_key";
    public static final String KEY_USER_ID = "user_id_key";
    public static final String KEY_NUMBER_PLAYER = "number_player_key";
    public static final String KEY_NICKNAME_DB = "nick_name_db_key";
    public static final String KEY_NAME_DB = "name_db_key";
    public static final String KEY_SURNAME_DB = "surname_db_key";

    private static final int REQUEST_CODE_NICK_NAME = 1;
    private static final int REQUEST_CODE_FINISH_NEW_GAME = 555;


    ArrayList<String> nickName = new ArrayList<String>(10);
    ArrayList<Integer> userId = new ArrayList<Integer>(10);
    Intent intent_inputs;
    ListView listView_PlayersInGame;
    String[] number_players;
    ArrayList<Map<String, String>> data_list;
    SimpleAdapter sAdapter;
    String[] nickName_db, name_db, surname_db;
    int idLeaderFromUsersTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_in_game);

        intent_inputs = getIntent();
        idLeaderFromUsersTable = intent_inputs.getIntExtra(NewGame.KEY_FOR_LEADER_ID, -1);

        for (int i=0; i < 10; i++) {
            nickName.add(getResources().getText(R.string.PlayersInGame_title_11) + "");
            userId.add(-1);
        }

        number_players = getResources().getStringArray(R.array.PlayersInGame_title_number);
        data_list = new ArrayList<Map<String, String>>(number_players.length);
        data_list = arrayForAdapter_list(data_list, nickName, number_players);

        String[] from = new String[] {ATTRIBUTE_NUMBER_TEXT, ATTRIBUTE_NICKNAME_TEXT};
        int[] to = new int[] { R.id.tv_item_PlayersInGame_1, R.id.tv_item_PlayersInGame_2 };
        sAdapter = new SimpleAdapter(this, data_list, R.layout.item_players_in_game, from, to);
        listView_PlayersInGame = (ListView) findViewById(R.id.listView_PlayersInGame);

        sAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object o, String s) {
                switch (view.getId()){
                    case R.id.tv_item_PlayersInGame_2:
                        String str = (String)o;
                        if (str.equals(getResources().getString(R.string.PlayersInGame_title_11)))
                            ((TextView)view).setTextColor(Color.argb(80,255,0,0));
                        else
                            ((TextView)view).setTextColor(Color.argb(255,0,0,0));
                        ((TextView)view).setText(str);
                        return true;
                }
                return false;
            }
        });
        listView_PlayersInGame.setAdapter(sAdapter);


        listView_PlayersInGame.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent_put = new Intent(PlayersInGame.this, PlayerInGame.class);
                intent_put.putStringArrayListExtra(KEY_NICK_NAME_ARRAY_LIST, nickName);
                intent_put.putIntegerArrayListExtra(KEY_USER_ID, userId);
                intent_put.putExtra(KEY_NUMBER_PLAYER, i);
                intent_put.putExtra(KEY_NICKNAME_DB, nickName_db);
                intent_put.putExtra(KEY_NAME_DB, name_db);
                intent_put.putExtra(KEY_SURNAME_DB, surname_db);
                intent_put.putExtra(NewGame.KEY_FOR_LEADER_ID, idLeaderFromUsersTable);
                startActivityForResult(intent_put, REQUEST_CODE_NICK_NAME);
            }
        });
        createDataForAdapter();
    }

    private void createDataForAdapter() {
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String str_sql = "";
        str_sql = "select users.nickName, users.userName, users.userSurname, users.userStartPlay from users ";
        Cursor cursor = database.rawQuery(str_sql, null);

        nickName_db = new String[cursor.getCount()];
        name_db = new String[cursor.getCount()];
        surname_db = new String[cursor.getCount()];

        if (cursor.moveToFirst()) {
            //отримуємо індекси колонок в таблиці
            int nickNameIndex = cursor.getColumnIndex(DBHelper.KEY_T2_NICK_NAME);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_T2_NAME);
            int surnameIndex = cursor.getColumnIndex(DBHelper.KEY_T2_SURNAME);
            int i = 0;
            do {
                nickName_db[i] = cursor.getString(nickNameIndex);
                name_db[i] = cursor.getString(nameIndex);
                surname_db[i] = cursor.getString(surnameIndex);
                i++;
            } while (cursor.moveToNext());
        }
        cursor.close();
        dbHelper.close();
    }

    private ArrayList<Map<String, String>> arrayForAdapter_list(ArrayList<Map<String, String>> data_list, ArrayList<String> nickName, String[] number_players){
        Map<String, String> m;
        for (int i = 0; i < number_players.length; i++) {
            m = new HashMap<String, String>();
            m.put(ATTRIBUTE_NUMBER_TEXT, number_players[i]);
            m.put(ATTRIBUTE_NICKNAME_TEXT, nickName.get(i));
            data_list.add(m);
        }
        return data_list;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FINISH_NEW_GAME) {
            if (resultCode == RESULT_OK) {
                Boolean request = data.getBooleanExtra(NewGame.KEY_FOR_FINISH_NEW_GAME, false);
                if (request) {
                    Intent intent = new Intent();
                    intent.putExtra(NewGame.KEY_FOR_FINISH_NEW_GAME, true);
                    setResult(RESULT_OK, intent);
                    finish();
               }
            }
        }
        if (requestCode == REQUEST_CODE_NICK_NAME) {
            if (resultCode == RESULT_OK) {
                nickName = data.getStringArrayListExtra(KEY_NICK_NAME_ARRAY_LIST);
                userId = data.getIntegerArrayListExtra(KEY_USER_ID);
                data_list.clear();
                data_list = arrayForAdapter_list(data_list, nickName, number_players);
                sAdapter.notifyDataSetChanged();
            }
        }
    }

    public void onTextClickPlayersInGame(View view) {
        switch (view.getId()){
            case R.id.img_PlayersInGame_1:
                onBackPressed();
                break;
            case R.id.bt_PlayersInGame:
                if (userId.contains(-1)){
                    Toast.makeText(this, getResources().getString(R.string.PlayersInGame_title_11) +
                            String.valueOf((1 + userId.indexOf(-1))) + getResources().getString(R.string.PlayerInGame_title_1),
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent_out = new Intent(intent_inputs);
                intent_out.setClass(this, PlayersInGameRole.class);
                intent_out.putStringArrayListExtra(KEY_NICK_NAME_ARRAY_LIST, nickName);
                intent_out.putIntegerArrayListExtra(KEY_USER_ID, userId);
                startActivityForResult(intent_out, REQUEST_CODE_FINISH_NEW_GAME);
                break;
            case R.id.bt_PlayersInGame_newPlayer:
                startActivity(new Intent(this, NewPlayer.class));
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        nickName_db = null;
        name_db = null;
        surname_db = null;
        createDataForAdapter();
    }
}

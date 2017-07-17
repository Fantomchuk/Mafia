package com.example.nazar.mafia;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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


public class PlayersList extends AppCompatActivity {

    private static final String ATTRIBUTE_NICK_NAME_TEXT = "nickName_at";
    private static final String ATTRIBUTE_NAME_TEXT = "name_at";
    private static final String ATTRIBUTE_SURNAME_TEXT = "surname_at";

    public static final String KEY_FOR_NICK_NAME = "nickName_key";
    public static final String KEY_FOR_NAME = "name_key";
    public static final String KEY_FOR_SURNAME = "surname_key";
    public static final String KEY_FOR_USER_START_PLAY = "userStartPlay";

    ListView listView_playersList;
    TextView textView_playersList;
    String[] nickName, name, surname;
    long[] userStartPlay;
    String user_title, title_for_listViw;
    Boolean isLeader;
    ArrayList<Map<String, String>> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_list);
        textView_playersList=(TextView) findViewById(R.id.textView_playersList);

        Intent intent_get = getIntent();
        user_title = intent_get.getStringExtra(TitleUser.KEY_FOR_INTENT);
        isLeader = intent_get.getBooleanExtra(LeaderMain.KEY_FOR_LEADER, false);
        title_for_listViw = intent_get.getStringExtra(TitleUser.KEY_FOR_INTENT_1);

    }

    private ArrayList<Map<String, String>> arrayForAdapter(ArrayList<Map<String, String>> data){
        Map<String, String> m;
        for (int i = 0; i < nickName.length; i++) {
            m = new HashMap<String, String>();
            m.put(ATTRIBUTE_NICK_NAME_TEXT, nickName[i]);
            m.put(ATTRIBUTE_NAME_TEXT, name[i]);
            m.put(ATTRIBUTE_SURNAME_TEXT, surname[i]);
            data.add(m);
        }
        return data;
    }

    private void arraysForCursor(Cursor cursor){
        nickName = new String[cursor.getCount()];
        name = new String[cursor.getCount()];
        surname = new String[cursor.getCount()];
        userStartPlay = new long[cursor.getCount()];

        if (cursor.moveToFirst()) {
            //отримуємо індекси колонок в таблиці
            int nickNameIndex = cursor.getColumnIndex(DBHelper.KEY_T2_NICK_NAME);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_T2_NAME);
            int surnameIndex = cursor.getColumnIndex(DBHelper.KEY_T2_SURNAME);
            int userStartPlayIndex = cursor.getColumnIndex(DBHelper.KEY_T2_TIME);
            int i=0;
            do {
                nickName[i] = cursor.getString(nickNameIndex);
                name[i] = cursor.getString(nameIndex);
                surname[i] = cursor.getString(surnameIndex);
                userStartPlay[i] = cursor.getLong(userStartPlayIndex);
                i++;
            } while (cursor.moveToNext());
        }
    }

    public void onTextClickPlayersList(View view) {
        switch (view.getId()){
            case R.id.img_PlayersList_1:
            onBackPressed();
            break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String str_sql="";
        String[] selectionArgs = null;
        if(user_title.equals(DBHelper.KEY_TITLE[6]) ) {
            str_sql = "select users.nickName, users.userName, users.userSurname, users.userStartPlay " +
                    "from users " +
                    "inner join passwords on users._id = passwords.usersId ";
        }else{
            str_sql = "select users.nickName, users.userName, users.userSurname, users.userStartPlay " +
                    "from users " +
                    "inner join titles on users.titlesId = titles._id " +
                    "where titles.title = ? ";
            selectionArgs = new String[]{user_title};
        }
        Cursor cursor = database.rawQuery(str_sql, selectionArgs);
        if (cursor.getCount() == 0) {
            textView_playersList.setText(getString(R.string.PlayersList_title_0) + title_for_listViw + getString(R.string.PlayersList_title_1));
            cursor.close();
            dbHelper.close();
            return;
        }
        arraysForCursor(cursor);
        cursor.close();
        dbHelper.close();

        textView_playersList.setText(title_for_listViw);
        data = new ArrayList<Map<String, String>>(nickName.length);
        data = arrayForAdapter(data);
        String[] from = new String[] {ATTRIBUTE_NICK_NAME_TEXT, ATTRIBUTE_NAME_TEXT, ATTRIBUTE_SURNAME_TEXT};
        int[] to = new int[] { R.id.item_user_1, R.id.item_user_3, R.id.item_user_4 };
        SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.item_user, from, to);
        listView_playersList = (ListView) findViewById(R.id.listView_playersList);
        listView_playersList.setAdapter(sAdapter);

        //натискання пункту меню
        listView_playersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent_put = new Intent(PlayersList.this, PlayerPage.class);
                intent_put.putExtra(KEY_FOR_NICK_NAME, nickName[i]);
                intent_put.putExtra(KEY_FOR_NAME, name[i]);
                intent_put.putExtra(KEY_FOR_SURNAME, surname[i]);
                intent_put.putExtra(KEY_FOR_USER_START_PLAY, userStartPlay[i]);
                intent_put.putExtra(TitleUser.KEY_FOR_INTENT, user_title);
                if (isLeader) intent_put.putExtra(LeaderMain.KEY_FOR_LEADER, true);
                startActivity(intent_put);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        nickName = null;
        name = null;
        surname = null;
        userStartPlay = null;
        data.clear();
    }
}

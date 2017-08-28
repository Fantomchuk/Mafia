package com.example.nazar.mafia;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class NewGame extends AppCompatActivity implements MyDialogQuestion.MyDialogListener, MyDialogDatePicker.MyDialogDatePickerListener{

    private static final String ATTRIBUTE_NICK_NAME_TEXT = "nickName_at";
    private static final String ATTRIBUTE_NAME_TEXT = "name_at";
    private static final String ATTRIBUTE_SURNAME_TEXT = "surname_at";

    public static final String KEY_FOR_NICK_NAME_LEADER = "nick_name_leader_key";
    public static final String KEY_FOR_LEADER_ID = "leader_id_key";
    public static final String KEY_FOR_NUMBER_GAME = "number_game_key";
    public static final String KEY_FOR_DATE_GAME = "date_game_key";
    public static final String KEY_FOR_FINISH_NEW_GAME = "finish_game_key";

    private static final int REQUEST_CODE_FINISH_NEW_GAME = 555;

    AutoCompleteTextView aCtV_NewGame_leader;
    ArrayList<Map<String, String>> data;
    TextView tv_NewGame_info;
    EditText eT_NewGame_numberGame;
    String[] nickName, name, surname;

    int idLeaderFromUsersTable = 0;
    String dateGame = null;
    int numberGame;
    String nickNameLeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        aCtV_NewGame_leader = (AutoCompleteTextView) findViewById(R.id.aCtV_NewGame_leader);
        tv_NewGame_info = (TextView) findViewById(R.id.tv_NewGame_info);
        eT_NewGame_numberGame = (EditText) findViewById(R.id.eT_NewGame_numberGame);

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String str_sql;
        str_sql = "select users.nickName, users.userName, users.userSurname " +
                "from users " +
                "inner join passwords on users._id = passwords.usersId ";
        Cursor cursor = database.rawQuery(str_sql, null);
        arraysForCursor(cursor);
        cursor.close();
        dbHelper.close();

        data = new ArrayList<>(nickName.length);
        data = arrayFosAdapter(data);
        String[] from = new String[]{ATTRIBUTE_NICK_NAME_TEXT, ATTRIBUTE_NAME_TEXT, ATTRIBUTE_SURNAME_TEXT};
        int[] to = new int[]{R.id.item_user_1, R.id.item_user_3, R.id.item_user_4};
        SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.item_user, from, to);

        aCtV_NewGame_leader.setAdapter(sAdapter);
        aCtV_NewGame_leader.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView nickName_db = (TextView) view.findViewById(R.id.item_user_1);
                TextView name_db = (TextView) view.findViewById(R.id.item_user_3);
                TextView surName_db = (TextView) view.findViewById(R.id.item_user_4);
                nickNameLeader = nickName_db.getText().toString();
                aCtV_NewGame_leader.setText(nickNameLeader);
                DB db = new DB(NewGame.this);
                db.open();
                idLeaderFromUsersTable = db.getPlayerId(name_db.getText().toString(), surName_db.getText().toString());
                db.close();
            }
        });
    }

    private void arraysForCursor(Cursor cursor) {
        nickName = new String[cursor.getCount()];
        name = new String[cursor.getCount()];
        surname = new String[cursor.getCount()];

        if (cursor.moveToFirst()) {
            int nickNameIndex = cursor.getColumnIndex(DBHelper.KEY_T2_NICK_NAME);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_T2_NAME);
            int surnameIndex = cursor.getColumnIndex(DBHelper.KEY_T2_SURNAME);
            int i = 0;
            do {
                nickName[i] = cursor.getString(nickNameIndex);
                name[i] = cursor.getString(nameIndex);
                surname[i] = cursor.getString(surnameIndex);
                i++;
            } while (cursor.moveToNext());
        }
    }

    public void onTextClickNewGame(View view) {
        switch (view.getId()) {
            case R.id.img_NewGame_1:
                onBackPressed();
                break;
            case R.id.bt_NewGame_date:
                MyDialogDatePicker dlg_add_data_game = MyDialogDatePicker.newInstance(null, 1);
                dlg_add_data_game.show(getFragmentManager(), "dlg_add_data_game");
                break;
            case R.id.bt_NewGame_addPlayers:
                if (idLeaderFromUsersTable == 0) {tv_NewGame_info.setText(R.string.NewGame_title_5);return;}
                if (dateGame == null) {tv_NewGame_info.setText(R.string.NewGame_title_6);return;}
                String number = eT_NewGame_numberGame.getText().toString();
                if (TextUtils.isEmpty(number) || Integer.valueOf(number) == 0) {tv_NewGame_info.setText(R.string.NewGame_title_7);return;}
                numberGame = Integer.valueOf(number);
                MyDialogQuestion dlg_add_players = new MyDialogQuestion();
                dlg_add_players.setTitileDialog(getResources().getString(R.string.NewGame_title_9));
                dlg_add_players.setMessageDialog(getResources().getText(R.string.NewGame_title_10) + nickNameLeader +
                        getResources().getText(R.string.NewGame_title_11) + numberGame +
                        getResources().getText(R.string.NewGame_title_12) + dateGame);
                dlg_add_players.setIconDialog(android.R.drawable.ic_dialog_info);
                dlg_add_players.setTextBtnPositive(getResources().getString(R.string.NewGame_title_13));
                dlg_add_players.setTextBtnNegative(getResources().getString(R.string.NewPlayer_title_13));
                dlg_add_players.show(getFragmentManager(), "dlg_add_players");
                break;
        }
    }

    private ArrayList<Map<String, String>> arrayFosAdapter(ArrayList<Map<String, String>> data) {
        Map<String, String> m;
        for (int i = 0; i < nickName.length; i++) {
            m = new HashMap<>();
            m.put(ATTRIBUTE_NICK_NAME_TEXT, nickName[i]);
            m.put(ATTRIBUTE_NAME_TEXT, name[i]);
            m.put(ATTRIBUTE_SURNAME_TEXT, surname[i]);
            data.add(m);
        }
        return data;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FINISH_NEW_GAME) {
            if (resultCode == RESULT_OK) {
                Boolean request = data.getBooleanExtra(NewGame.KEY_FOR_FINISH_NEW_GAME, false);
                if (request) {
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        }
    }

    @Override
    public void clickPositiveButton(Boolean res) {
        Intent intent_put = new Intent(NewGame.this, PlayersInGame.class);
        intent_put.putExtra(KEY_FOR_NICK_NAME_LEADER, nickNameLeader);
        intent_put.putExtra(KEY_FOR_LEADER_ID, idLeaderFromUsersTable);
        intent_put.putExtra(KEY_FOR_NUMBER_GAME, numberGame);
        intent_put.putExtra(KEY_FOR_DATE_GAME, dateGame);
        startActivityForResult(intent_put, REQUEST_CODE_FINISH_NEW_GAME);
    }

    @Override
    public void clickNegativeButton(Boolean res) {}

    @Override
    public void clickNeutralButton(Boolean res) {}

    @Override
    public void dateInMyDialogDatePicker(long myDate, int requestCode) {
        dateGame = PlayerPage.convertLongToData(myDate);
        Button bt_NewGame_date = (Button) findViewById(R.id.bt_NewGame_date);
        bt_NewGame_date.setText(R.string.NewPlayer_title_12);
    }
}

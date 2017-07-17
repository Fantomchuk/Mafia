package com.example.nazar.mafia;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ShowGame extends AppCompatActivity {


    public static final String INTENT_ACTION_SHOW_GAME = "action_show_game_intent";
    int[] don, don_seat, sherif, sherif_seat, maf_1, maf_1_seat, maf_2, maf_2_seat,
            sitizen_1, sitizen_1_seat, sitizen_2, sitizen_2_seat, sitizen_3, sitizen_3_seat, sitizen_4,
            sitizen_4_seat, sitizen_5, sitizen_5_seat, sitizen_6, sitizen_6_seat, win_red, kiled_first,
            kiled_second, kiled_third, preferable, leader, number_of_game, course_game;
    long[]  time_of_game;

    int idLeaderFromUsersTable;
    ArrayList<String> nickName = new ArrayList<>(10);
    ArrayList<Integer> userId = new ArrayList<>(10);
    ArrayList<String> role = new ArrayList<>(10);
    String nickNameLeader, dateGame;
    int numberGame;
    int idKilled_1_night, idKilled_2_night, idKilled_3_night;
    int courseGame;
    int idPreferablePlayer;
    int isRedWin;

    private static final int DIALOG_ADD_DATE_START = 1;
    private static final int DIALOG_ADD_DATE_END = 2;
    String dateStart="",dateEnd="";
    ListView lv_ShowGame;
    TextView tv_ShowGame_info;
    ArrayList<Map<String, String>> data;

    private static final String ATTRIBUTE_NUMBER_GAME = "number_game_at";
    private static final String ATTRIBUTE_DATE_GEME = "date_game_at";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_game);
        lv_ShowGame = (ListView) findViewById(R.id.lv_ShowGame);
        tv_ShowGame_info = (TextView) findViewById(R.id.tv_ShowGame_info);
    }

    private void buildListView(){
        DBHelper db = new DBHelper(this);
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        Cursor cursor;
        String str_sql = "select g.don, g.don_seat, g.sherif, g.sherif_seat, g.maf_1, g.maf_1_seat, g.maf_2, g.maf_2_seat, " +
                "g.sitizen_1, g.sitizen_1_seat, g.sitizen_2, g.sitizen_2_seat, g.sitizen_3, g.sitizen_3_seat, " +
                "g.sitizen_4, g.sitizen_4_seat, g.sitizen_5, g.sitizen_5_seat, g.sitizen_6, g.sitizen_6_seat, " +
                "g.win_red, g.kiled_first, g.kiled_second, g.kiled_third, g.preferable, g.leader, g.number_of_game, " +
                "g.time_of_game, g.course_game " +
                "from games as g "+
                "where g.time_of_game >= ? and g.time_of_game <= ?";

        long dateStart_int = DBHelper.convertStringDataToLong(dateStart);
        long dateEnd_int = DBHelper.convertStringDataToLong(dateEnd);
        String[] selectionArgs = new String[]{String.valueOf(dateStart_int), String.valueOf(dateEnd_int)};
        cursor = sqLiteDatabase.rawQuery(str_sql, selectionArgs);
        if (cursor.getCount() == 0) {
            lv_ShowGame.setVisibility(View.GONE);
            tv_ShowGame_info.setVisibility(View.VISIBLE);
            tv_ShowGame_info.setText(getResources().getString(R.string.ShowGame_title_5));
            cursor.close();
            db.close();
            return;
        }
        arraysForCursor(cursor);
        cursor.close();
        db.close();

        data = new ArrayList<>(win_red.length);
        data = arrayForAdapter(data);
        String[] from = new String[] {ATTRIBUTE_NUMBER_GAME, ATTRIBUTE_DATE_GEME};
        int[] to = new int[] { R.id.tv_item_game_2, R.id.tv_item_game_4 };
        SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.item_game, from, to);
        lv_ShowGame.setAdapter(sAdapter);

        lv_ShowGame.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dataForShowGame(i);
            }
        });
    }

    private ArrayList<Map<String, String>> arrayForAdapter(ArrayList<Map<String, String>> data){
        Map<String, String> m;
        for (int i = 0; i < win_red.length; i++) {
            m = new HashMap<>();
            m.put(ATTRIBUTE_NUMBER_GAME, String.valueOf(number_of_game[i]));
            m.put(ATTRIBUTE_DATE_GEME, PlayerPage.convertLongToData(time_of_game[i]));
            data.add(m);
        }
        return data;
    }

    private void arraysForCursor(Cursor cursor){

        int sizeArray = cursor.getCount();
        don = new int[sizeArray];
        don_seat = new int[sizeArray];
        sherif = new int[sizeArray];
        sherif_seat = new int[sizeArray];
        maf_1 = new int[sizeArray];
        maf_1_seat = new int[sizeArray];
        maf_2 = new int[sizeArray];
        maf_2_seat = new int[sizeArray];
        sitizen_1 = new int[sizeArray];
        sitizen_1_seat = new int[sizeArray];
        sitizen_2 = new int[sizeArray];
        sitizen_2_seat = new int[sizeArray];
        sitizen_3 = new int[sizeArray];
        sitizen_3_seat = new int[sizeArray];
        sitizen_4 = new int[sizeArray];
        sitizen_4_seat = new int[sizeArray];
        sitizen_5 = new int[sizeArray];
        sitizen_5_seat = new int[sizeArray];
        sitizen_6 = new int[sizeArray];
        sitizen_6_seat = new int[sizeArray];
        win_red = new int[sizeArray];
        kiled_first = new int[sizeArray];
        kiled_second = new int[sizeArray];
        kiled_third = new int[sizeArray];
        preferable = new int[sizeArray];
        leader = new int[sizeArray];
        number_of_game = new int[sizeArray];
        course_game = new int[sizeArray];
        time_of_game = new long[sizeArray];
        if (cursor.moveToFirst()) {
            //отримуємо індекси колонок в таблиці
            int don_Index = cursor.getColumnIndex(DBHelper.KEY_T4_DON);
            int don_seat_Index = cursor.getColumnIndex(DBHelper.KEY_T4_DON_SEAT);
            int sherif_Index = cursor.getColumnIndex(DBHelper.KEY_T4_SHERIF);
            int sherif_seat_Index = cursor.getColumnIndex(DBHelper.KEY_T4_SHERIF_SEAT);
            int maf_1_Index = cursor.getColumnIndex(DBHelper.KEY_T4_MAF_1);
            int maf_1_seat_Index = cursor.getColumnIndex(DBHelper.KEY_T4_MAF_1_SEAT);
            int maf_2_Index = cursor.getColumnIndex(DBHelper.KEY_T4_MAF_2);
            int maf_2_seat_Index = cursor.getColumnIndex(DBHelper.KEY_T4_MAF_2_SEAT);
            int sitizen_1_Index = cursor.getColumnIndex(DBHelper.KEY_T4_SITIZEN_1);
            int sitizen_1_seat_Index = cursor.getColumnIndex(DBHelper.KEY_T4_SITIZEN_1_SEAT);
            int sitizen_2_Index = cursor.getColumnIndex(DBHelper.KEY_T4_SITIZEN_2);
            int sitizen_2_seat_Index = cursor.getColumnIndex(DBHelper.KEY_T4_SITIZEN_2_SEAT);
            int sitizen_3_Index = cursor.getColumnIndex(DBHelper.KEY_T4_SITIZEN_3);
            int sitizen_3_seat_Index = cursor.getColumnIndex(DBHelper.KEY_T4_SITIZEN_3_SEAT);
            int sitizen_4_Index = cursor.getColumnIndex(DBHelper.KEY_T4_SITIZEN_4);
            int sitizen_4_seat_Index = cursor.getColumnIndex(DBHelper.KEY_T4_SITIZEN_4_SEAT);
            int sitizen_5_Index = cursor.getColumnIndex(DBHelper.KEY_T4_SITIZEN_5);
            int sitizen_5_seat_Index = cursor.getColumnIndex(DBHelper.KEY_T4_SITIZEN_5_SEAT);
            int sitizen_6_Index = cursor.getColumnIndex(DBHelper.KEY_T4_SITIZEN_6);
            int sitizen_6_seat_Index = cursor.getColumnIndex(DBHelper.KEY_T4_SITIZEN_6_SEAT);

            int win_red_Index = cursor.getColumnIndex(DBHelper.KEY_T4_WIN_RED);
            int kiled_first_Index = cursor.getColumnIndex(DBHelper.KEY_T4_KILED_FIRST);
            int kiled_second_Index = cursor.getColumnIndex(DBHelper.KEY_T4_KILED_SECOND);
            int kiled_third_Index = cursor.getColumnIndex(DBHelper.KEY_T4_KILED_THIRD);
            int preferable_Index = cursor.getColumnIndex(DBHelper.KEY_T4_PREFERABLE);
            int leader_Index = cursor.getColumnIndex(DBHelper.KEY_T4_LEADER);
            int number_of_game_Index = cursor.getColumnIndex(DBHelper.KEY_T4_NUMBER_OF_GAME);
            int time_of_game_Index = cursor.getColumnIndex(DBHelper.KEY_T4_TIME);
            int course_game_Index = cursor.getColumnIndex(DBHelper.KEY_T4_COURSE_GAME);
            int i = 0;
            do {
                don[i] = cursor.getInt(don_Index);
                don_seat[i] = cursor.getInt(don_seat_Index);
                sherif[i] = cursor.getInt(sherif_Index);
                sherif_seat[i] = cursor.getInt(sherif_seat_Index);
                maf_1[i] = cursor.getInt(maf_1_Index);
                maf_1_seat[i] = cursor.getInt(maf_1_seat_Index);
                maf_2[i] = cursor.getInt(maf_2_Index);
                maf_2_seat[i] = cursor.getInt(maf_2_seat_Index);
                sitizen_1[i] = cursor.getInt(sitizen_1_Index);
                sitizen_1_seat[i] = cursor.getInt(sitizen_1_seat_Index);
                sitizen_2[i] = cursor.getInt(sitizen_2_Index);
                sitizen_2_seat[i] = cursor.getInt(sitizen_2_seat_Index);
                sitizen_3[i] = cursor.getInt(sitizen_3_Index);
                sitizen_3_seat[i] = cursor.getInt(sitizen_3_seat_Index);
                sitizen_4[i] = cursor.getInt(sitizen_4_Index);
                sitizen_4_seat[i] = cursor.getInt(sitizen_4_seat_Index);
                sitizen_5[i] = cursor.getInt(sitizen_5_Index);
                sitizen_5_seat[i] = cursor.getInt(sitizen_5_seat_Index);
                sitizen_6[i] = cursor.getInt(sitizen_6_Index);
                sitizen_6_seat[i] = cursor.getInt(sitizen_6_seat_Index);

                win_red[i] = cursor.getInt(win_red_Index);
                kiled_first[i] = cursor.getInt(kiled_first_Index);
                kiled_second[i] = cursor.getInt(kiled_second_Index);
                kiled_third[i] = cursor.getInt(kiled_third_Index);
                preferable[i] = cursor.getInt(preferable_Index);
                leader[i] = cursor.getInt(leader_Index);
                number_of_game[i] = cursor.getInt(number_of_game_Index);
                course_game[i] = cursor.getInt(course_game_Index);
                time_of_game[i] = cursor.getLong(time_of_game_Index);
                i++;
            } while (cursor.moveToNext());
        }
    }

    public void onTextClickShowGame(View view) {
        switch (view.getId()){
            case R.id.img_ShowGame_1:
                onBackPressed();
                break;
            case R.id.tv_ShowGame_date_strart:
                showDialog(DIALOG_ADD_DATE_START);
                lv_ShowGame.setVisibility(View.GONE);
                break;
            case R.id.tv_ShowGame_date_end:
                showDialog(DIALOG_ADD_DATE_END);
                lv_ShowGame.setVisibility(View.GONE);
                break;
            case R.id.bt_ShowGame_search:
                if(dateStart.equals("") || dateEnd.equals("")){
                    lv_ShowGame.setVisibility(View.GONE);
                    tv_ShowGame_info.setVisibility(View.VISIBLE);
                    tv_ShowGame_info.setText(getResources().getString(R.string.ShowGame_title_4));
                    return;
                }else{
                    lv_ShowGame.setVisibility(View.VISIBLE);
                    tv_ShowGame_info.setVisibility(View.GONE);
                }
                buildListView();
                break;
        }
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
                    TextView tv = (TextView) findViewById(R.id.tv_ShowGame_date_strart);
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
                    TextView tv = (TextView) findViewById(R.id.tv_ShowGame_date_end);
                    tv.setText(dateEnd);
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dpd.setCancelable(true);
            return dpd;
        }
        return super.onCreateDialog(id);
    }


    private void dataForShowGame(int i){
        for (int k=0; k<10; k++)
            role.add("0");
        role.set(don_seat[i]-1, PlayersInGameRole.KEY_ROLE_DON);
        role.set(sherif_seat[i]-1, PlayersInGameRole.KEY_ROLE_SHERIF);
        role.set(maf_1_seat[i]-1, PlayersInGameRole.KEY_ROLE_MAF);
        role.set(maf_2_seat[i]-1, PlayersInGameRole.KEY_ROLE_MAF);
        role.set(sitizen_1_seat[i]-1, PlayersInGameRole.KEY_ROLE_SITIZEN);
        role.set(sitizen_2_seat[i]-1, PlayersInGameRole.KEY_ROLE_SITIZEN);
        role.set(sitizen_3_seat[i]-1, PlayersInGameRole.KEY_ROLE_SITIZEN);
        role.set(sitizen_4_seat[i]-1, PlayersInGameRole.KEY_ROLE_SITIZEN);
        role.set(sitizen_5_seat[i]-1, PlayersInGameRole.KEY_ROLE_SITIZEN);
        role.set(sitizen_6_seat[i]-1, PlayersInGameRole.KEY_ROLE_SITIZEN);

        for (int k=0; k<10; k++)
            userId.add(-1);
        userId.set(don_seat[i]-1, don[i]);
        userId.set(sherif_seat[i]-1, sherif[i]);
        userId.set(maf_1_seat[i]-1, maf_1[i]);
        userId.set(maf_2_seat[i]-1, maf_2[i]);
        userId.set(sitizen_1_seat[i]-1, sitizen_1[i]);
        userId.set(sitizen_2_seat[i]-1, sitizen_2[i]);
        userId.set(sitizen_3_seat[i]-1, sitizen_3[i]);
        userId.set(sitizen_4_seat[i]-1, sitizen_4[i]);
        userId.set(sitizen_5_seat[i]-1, sitizen_5[i]);
        userId.set(sitizen_6_seat[i]-1, sitizen_6[i]);

        idLeaderFromUsersTable = leader[i];
        for (int k = 0; k < 10; k++)
            nickName.add("0");

        DB dbb = new DB(this);
        dbb.open();
        for (int k = 0; k < 10; k++)
            nickName.set(k, dbb.getPlayerNickName(userId.get(k)));
        nickNameLeader = dbb.getPlayerNickName(idLeaderFromUsersTable);
        dbb.close();

        isRedWin = win_red[i];
        idKilled_1_night = kiled_first[i];
        idKilled_2_night = kiled_second[i];
        idKilled_3_night = kiled_third[i];
        idPreferablePlayer = preferable[i];
        numberGame = number_of_game[i];
        courseGame = course_game[i];
        dateGame = PlayerPage.convertLongToData(time_of_game[i]);

        Intent intent = new Intent(this, PlayersInGameList.class);
        intent.setAction(INTENT_ACTION_SHOW_GAME);
        intent.putExtra(NewGame.KEY_FOR_LEADER_ID, idLeaderFromUsersTable);
        intent.putExtra(NewGame.KEY_FOR_NICK_NAME_LEADER, nickNameLeader);
        intent.putExtra(NewGame.KEY_FOR_NUMBER_GAME, numberGame);
        intent.putExtra(NewGame.KEY_FOR_DATE_GAME, dateGame);
        intent.putExtra(PlayersInGame.KEY_NICK_NAME_ARRAY_LIST, nickName);
        intent.putExtra(PlayersInGame.KEY_USER_ID, userId);
        intent.putExtra(PlayersInGameRole.KEY_ROLE_ARRAY_LIST, role);
        intent.putExtra(StartGame.KEY_KILLED_1_NIGHT, idKilled_1_night);
        intent.putExtra(StartGame.KEY_KILLED_2_NIGHT, idKilled_2_night);
        intent.putExtra(StartGame.KEY_KILLED_3_NIGHT, idKilled_3_night);
        intent.putExtra(StartGame.KEY_COURSE_GAME, courseGame);
        intent.putExtra(StartGame.KEY_PREFERABLE_PLAYER, idPreferablePlayer);
        intent.putExtra(StartGame.KEY_WINNER_TEAM, isRedWin);
        startActivity(intent);
    }
}



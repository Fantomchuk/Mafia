package com.example.nazar.mafia;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


public class OneGame {


    private Context context;

    private DBHelper mDBHelper;
    private SQLiteDatabase sqLiteDatabase;

    public OneGame(Context mCtx, int idGame) {
        this.context = mCtx;
        getGame(idGame);
    }

    public void open() {
        mDBHelper = new DBHelper(context);
        sqLiteDatabase = mDBHelper.getWritableDatabase();
    }

    public void close() {
        if (mDBHelper != null)
            mDBHelper.close();
    }

    private ArrayList<String> nickName = new ArrayList<>(10);
    private ArrayList<Integer> userId = new ArrayList<>(10);
    private ArrayList<String> role = new ArrayList<>(10);

    public int don, don_seat, sherif, sherif_seat, maf_1, maf_1_seat, maf_2, maf_2_seat,
            sitizen_1, sitizen_1_seat, sitizen_2, sitizen_2_seat, sitizen_3, sitizen_3_seat, sitizen_4,
            sitizen_4_seat, sitizen_5, sitizen_5_seat, sitizen_6, sitizen_6_seat, win_red, kiled_first,
            kiled_second, kiled_third, preferable, leader, number_of_game, course_game;
    public long  time_of_game;

    private void getGame(int idGame){
        open();

        Cursor cursor;
        String str_sql = "select g.don, g.don_seat, g.sherif, g.sherif_seat, g.maf_1, g.maf_1_seat, g.maf_2, g.maf_2_seat, " +
                "g.sitizen_1, g.sitizen_1_seat, g.sitizen_2, g.sitizen_2_seat, g.sitizen_3, g.sitizen_3_seat, " +
                "g.sitizen_4, g.sitizen_4_seat, g.sitizen_5, g.sitizen_5_seat, g.sitizen_6, g.sitizen_6_seat, " +
                "g.win_red, g.kiled_first, g.kiled_second, g.kiled_third, g.preferable, g.leader, g.number_of_game, " +
                "g.time_of_game, g.course_game " +
                "from games as g "+
                "where g._id = ? ";

        String[] selectionArgs = new String[]{ String.valueOf(idGame) };
        cursor = sqLiteDatabase.rawQuery(str_sql, selectionArgs);
        arraysForCursor(cursor);
        cursor.close();

        close();
    }

    private void arraysForCursor(Cursor cursor){
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

            this.don = cursor.getInt(don_Index);
            this.don_seat = cursor.getInt(don_seat_Index);
            this.sherif = cursor.getInt(sherif_Index);
            this.sherif_seat = cursor.getInt(sherif_seat_Index);
            this.maf_1 = cursor.getInt(maf_1_Index);
            this.maf_1_seat = cursor.getInt(maf_1_seat_Index);
            this.maf_2 = cursor.getInt(maf_2_Index);
            this.maf_2_seat = cursor.getInt(maf_2_seat_Index);
            this.sitizen_1 = cursor.getInt(sitizen_1_Index);
            this.sitizen_1_seat = cursor.getInt(sitizen_1_seat_Index);
            this.sitizen_2 = cursor.getInt(sitizen_2_Index);
            this.sitizen_2_seat = cursor.getInt(sitizen_2_seat_Index);
            this.sitizen_3 = cursor.getInt(sitizen_3_Index);
            this.sitizen_3_seat = cursor.getInt(sitizen_3_seat_Index);
            this.sitizen_4 = cursor.getInt(sitizen_4_Index);
            this.sitizen_4_seat = cursor.getInt(sitizen_4_seat_Index);
            this.sitizen_5 = cursor.getInt(sitizen_5_Index);
            this.sitizen_5_seat = cursor.getInt(sitizen_5_seat_Index);
            this.sitizen_6 = cursor.getInt(sitizen_6_Index);
            this.sitizen_6_seat = cursor.getInt(sitizen_6_seat_Index);

            this.win_red = cursor.getInt(win_red_Index);
            this.kiled_first = cursor.getInt(kiled_first_Index);
            this.kiled_second = cursor.getInt(kiled_second_Index);
            this.kiled_third = cursor.getInt(kiled_third_Index);
            this.preferable = cursor.getInt(preferable_Index);
            this.leader = cursor.getInt(leader_Index);
            this.number_of_game = cursor.getInt(number_of_game_Index);
            this.course_game = cursor.getInt(course_game_Index);
            this.time_of_game = cursor.getLong(time_of_game_Index);
        }
    }

    public ArrayList<String> getRoles() {
        for (int k = 0; k < 10; k++)
            role.add("0");
        role.set(don_seat - 1, PlayersInGameRole.KEY_ROLE_DON);
        role.set(sherif_seat - 1, PlayersInGameRole.KEY_ROLE_SHERIF);
        role.set(maf_1_seat - 1, PlayersInGameRole.KEY_ROLE_MAF);
        role.set(maf_2_seat - 1, PlayersInGameRole.KEY_ROLE_MAF);
        role.set(sitizen_1_seat - 1, PlayersInGameRole.KEY_ROLE_SITIZEN);
        role.set(sitizen_2_seat - 1, PlayersInGameRole.KEY_ROLE_SITIZEN);
        role.set(sitizen_3_seat - 1, PlayersInGameRole.KEY_ROLE_SITIZEN);
        role.set(sitizen_4_seat - 1, PlayersInGameRole.KEY_ROLE_SITIZEN);
        role.set(sitizen_5_seat - 1, PlayersInGameRole.KEY_ROLE_SITIZEN);
        role.set(sitizen_6_seat - 1, PlayersInGameRole.KEY_ROLE_SITIZEN);

        return role;
    }

    public ArrayList<Integer> getUsersId() {
        for (int k = 0; k < 10; k++)
            userId.add(-1);
        userId.set(don_seat - 1, don);
        userId.set(sherif_seat - 1, sherif);
        userId.set(maf_1_seat - 1, maf_1);
        userId.set(maf_2_seat - 1, maf_2);
        userId.set(sitizen_1_seat - 1, sitizen_1);
        userId.set(sitizen_2_seat - 1, sitizen_2);
        userId.set(sitizen_3_seat - 1, sitizen_3);
        userId.set(sitizen_4_seat - 1, sitizen_4);
        userId.set(sitizen_5_seat - 1, sitizen_5);
        userId.set(sitizen_6_seat - 1, sitizen_6);

        return userId;
    }

    public ArrayList<String> getUsersNickName() {
        for (int k = 0; k < 10; k++)
            nickName.add("0");

        DB dbb = new DB(context);
        dbb.open();
        for (int k = 0; k < 10; k++)
            nickName.set(k, dbb.getPlayerNickName(userId.get(k)));
        dbb.close();
        return nickName;
    }

}

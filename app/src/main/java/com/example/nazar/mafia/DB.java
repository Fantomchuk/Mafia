package com.example.nazar.mafia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.ArrayList;


public class DB {
    private final Context context;

    private DBHelper mDBHelper;
    private SQLiteDatabase sqLiteDatabase;

    public DB(Context mCtx) {
        this.context = mCtx;
    }

    public void open() {
        mDBHelper = new DBHelper(context);
        sqLiteDatabase = mDBHelper.getWritableDatabase();
    }

    public void close() {
        if (mDBHelper != null)
            mDBHelper.close();
    }

    public void addPlayerToDB(String nickName, String name, String surName, int title, String userStartPlay) {
        ContentValues contentValues = new ContentValues();
        contentValues.clear();
        contentValues.put(DBHelper.KEY_T2_TITLES_ID, title);
        contentValues.put(DBHelper.KEY_T2_NICK_NAME, nickName);
        contentValues.put(DBHelper.KEY_T2_NAME, name);
        contentValues.put(DBHelper.KEY_T2_SURNAME, surName);
        contentValues.put(DBHelper.KEY_T2_TIME, mDBHelper.convertStringDataToLong(userStartPlay));
        //переносимо дані з контейнера в БД
        sqLiteDatabase.insert(DBHelper.TABLE_NAME_2, null, contentValues);
    }

    public int getPlayerId(String name_, String surName_) {
        Cursor cursor;
        String str_sql;
        String[] selectionArgs;
        str_sql = "select users._id " +
                "from users " +
                "where users.userName = ? and users.userSurname = ?";
        selectionArgs = new String[]{name_, surName_};
        cursor = sqLiteDatabase.rawQuery(str_sql, selectionArgs);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                int id = cursor.getInt(idIndex);
                cursor.close();
                return id;
            }
        }
        cursor.close();
        return -1;
    }

    public String getPlayerNickName(String name_, String surName_) {
        Cursor cursor;
        String str_sql;
        String[] selectionArgs;
        str_sql = "select users.nickName " +
                "from users " +
                "where users.userName = ? and users.userSurname = ?";
        selectionArgs = new String[]{name_, surName_};
        cursor = sqLiteDatabase.rawQuery(str_sql, selectionArgs);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                int nickNameIndex = cursor.getColumnIndex(DBHelper.KEY_T2_NICK_NAME);
                String nickName = cursor.getString(nickNameIndex);
                cursor.close();
                return nickName;
            }
        }
        cursor.close();
        return "";
    }

    public String getPlayerNickName(int id) {
        Cursor cursor;
        String str_sql;
        String[] selectionArgs;
        str_sql = "select users.nickName " +
                "from users " +
                "where users._id = ? ";
        selectionArgs = new String[]{String.valueOf(id)};
        cursor = sqLiteDatabase.rawQuery(str_sql, selectionArgs);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                int nickNameIndex = cursor.getColumnIndex(DBHelper.KEY_T2_NICK_NAME);
                String nickName = cursor.getString(nickNameIndex);
                cursor.close();
                return nickName;
            }
        }
        cursor.close();
        return "";
    }

    public void updateUserInTableUsers(int id_user, String nickName, String name, String surName, int title, String userStartPlay) {
        ContentValues contentValues = new ContentValues();
        contentValues.clear();
        contentValues.put(DBHelper.KEY_T2_TITLES_ID, title);
        contentValues.put(DBHelper.KEY_T2_NICK_NAME, nickName);
        contentValues.put(DBHelper.KEY_T2_NAME, name);
        contentValues.put(DBHelper.KEY_T2_SURNAME, surName);
        contentValues.put(DBHelper.KEY_T2_TIME, mDBHelper.convertStringDataToLong(userStartPlay));
        //переносимо дані з контейнера в БД
        sqLiteDatabase.update(DBHelper.TABLE_NAME_2, contentValues, DBHelper.KEY_ID + "= ?", new String[]{String.valueOf(id_user)});
    }

    public void addLeaderToDB(int userId, String pass) {
        ContentValues contentValues = new ContentValues();
        contentValues.clear();
        contentValues.put(DBHelper.KEY_T1_USER_ID, userId);
        contentValues.put(DBHelper.KEY_T1_PASWORD, UserChoice.md5Custom(pass));
        //переносимо дані з контейнера в БД
        sqLiteDatabase.insert(DBHelper.TABLE_NAME_1, null, contentValues);
    }

    public boolean isPresentLeaderInDB(int userId_) {
        String userId = String.valueOf(userId_);
        Cursor cursor;
        String str_sql;
        String[] selectionArgs;
        str_sql = "select passwords._id " +
                "from passwords " +
                "where passwords.usersId = ? ";
        selectionArgs = new String[]{userId};
        cursor = sqLiteDatabase.rawQuery(str_sql, selectionArgs);
        if (cursor.getCount() != 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public String getPasswordForLeader(String nickName){
        Cursor cursor;
        String str_sql = "select passwords.password " +
                "from passwords " +
                "inner join users on passwords.usersId=users._id " +
                "where users.nickName = ? ";
        String[] selectionArgs = new String[]{nickName};
        cursor = sqLiteDatabase.rawQuery(str_sql, selectionArgs);
        if (cursor.moveToFirst()) {
            int passwordIndex = cursor.getColumnIndex(DBHelper.KEY_T1_PASWORD);
            return cursor.getString(passwordIndex);
        }
        cursor.close();
        return "";
    }

    public void updateLeaderInTablePasswords(int userId, String pass) {
        ContentValues contentValues = new ContentValues();
        contentValues.clear();
        contentValues.put(DBHelper.KEY_T1_PASWORD, UserChoice.md5Custom(pass));
        //переносимо дані з контейнера в БД
        sqLiteDatabase.update(DBHelper.TABLE_NAME_1, contentValues, DBHelper.KEY_T1_USER_ID + "= ?", new String[]{String.valueOf(userId)});
    }

    public void addGameToDB(ArrayList<Integer> userId, ArrayList<String> role, int isRedWin, int idKilled_1_night,
                            int idKilled_2_night, int idKilled_3_night, int idPreferablePlayer, int idLeader,
                            int numberGame, String dateGame, int courseGame) {
        ContentValues contentValues = new ContentValues();
        contentValues.clear();

        contentValues.put(DBHelper.KEY_T4_DON, userId.get(role.indexOf(PlayersInGameRole.KEY_ROLE_DON)));
        contentValues.put(DBHelper.KEY_T4_DON_SEAT, role.indexOf(PlayersInGameRole.KEY_ROLE_DON)+1);
        userId.set(role.indexOf(PlayersInGameRole.KEY_ROLE_DON), -1);
        role.set(role.indexOf(PlayersInGameRole.KEY_ROLE_DON), "0");

        contentValues.put(DBHelper.KEY_T4_SHERIF, userId.get(role.indexOf(PlayersInGameRole.KEY_ROLE_SHERIF)));
        contentValues.put(DBHelper.KEY_T4_SHERIF_SEAT, role.indexOf(PlayersInGameRole.KEY_ROLE_SHERIF)+1);
        userId.set(role.indexOf(PlayersInGameRole.KEY_ROLE_SHERIF), -1);
        role.set(role.indexOf(PlayersInGameRole.KEY_ROLE_SHERIF), "0");

        contentValues.put(DBHelper.KEY_T4_MAF_1, userId.get(role.indexOf(PlayersInGameRole.KEY_ROLE_MAF)));
        contentValues.put(DBHelper.KEY_T4_MAF_1_SEAT, role.indexOf(PlayersInGameRole.KEY_ROLE_MAF)+1);
        userId.set(role.indexOf(PlayersInGameRole.KEY_ROLE_MAF), -1);
        role.set(role.indexOf(PlayersInGameRole.KEY_ROLE_MAF), "0");

        contentValues.put(DBHelper.KEY_T4_MAF_2, userId.get(role.indexOf(PlayersInGameRole.KEY_ROLE_MAF)));
        contentValues.put(DBHelper.KEY_T4_MAF_2_SEAT, role.indexOf(PlayersInGameRole.KEY_ROLE_MAF)+1);
        userId.set(role.indexOf(PlayersInGameRole.KEY_ROLE_MAF), -1);
        role.set(role.indexOf(PlayersInGameRole.KEY_ROLE_MAF), "0");

        contentValues.put(DBHelper.KEY_T4_SITIZEN_1, userId.get(role.indexOf(PlayersInGameRole.KEY_ROLE_SITIZEN)));
        contentValues.put(DBHelper.KEY_T4_SITIZEN_1_SEAT, role.indexOf(PlayersInGameRole.KEY_ROLE_SITIZEN)+1);
        userId.set(role.indexOf(PlayersInGameRole.KEY_ROLE_SITIZEN), -1);
        role.set(role.indexOf(PlayersInGameRole.KEY_ROLE_SITIZEN), "0");

        contentValues.put(DBHelper.KEY_T4_SITIZEN_2, userId.get(role.indexOf(PlayersInGameRole.KEY_ROLE_SITIZEN)));
        contentValues.put(DBHelper.KEY_T4_SITIZEN_2_SEAT, role.indexOf(PlayersInGameRole.KEY_ROLE_SITIZEN)+1);
        userId.set(role.indexOf(PlayersInGameRole.KEY_ROLE_SITIZEN), -1);
        role.set(role.indexOf(PlayersInGameRole.KEY_ROLE_SITIZEN), "0");

        contentValues.put(DBHelper.KEY_T4_SITIZEN_3, userId.get(role.indexOf(PlayersInGameRole.KEY_ROLE_SITIZEN)));
        contentValues.put(DBHelper.KEY_T4_SITIZEN_3_SEAT, role.indexOf(PlayersInGameRole.KEY_ROLE_SITIZEN)+1);
        userId.set(role.indexOf(PlayersInGameRole.KEY_ROLE_SITIZEN), -1);
        role.set(role.indexOf(PlayersInGameRole.KEY_ROLE_SITIZEN), "0");

        contentValues.put(DBHelper.KEY_T4_SITIZEN_4, userId.get(role.indexOf(PlayersInGameRole.KEY_ROLE_SITIZEN)));
        contentValues.put(DBHelper.KEY_T4_SITIZEN_4_SEAT, role.indexOf(PlayersInGameRole.KEY_ROLE_SITIZEN)+1);
        userId.set(role.indexOf(PlayersInGameRole.KEY_ROLE_SITIZEN), -1);
        role.set(role.indexOf(PlayersInGameRole.KEY_ROLE_SITIZEN), "0");

        contentValues.put(DBHelper.KEY_T4_SITIZEN_5, userId.get(role.indexOf(PlayersInGameRole.KEY_ROLE_SITIZEN)));
        contentValues.put(DBHelper.KEY_T4_SITIZEN_5_SEAT, role.indexOf(PlayersInGameRole.KEY_ROLE_SITIZEN)+1);
        userId.set(role.indexOf(PlayersInGameRole.KEY_ROLE_SITIZEN), -1);
        role.set(role.indexOf(PlayersInGameRole.KEY_ROLE_SITIZEN), "0");

        contentValues.put(DBHelper.KEY_T4_SITIZEN_6, userId.get(role.indexOf(PlayersInGameRole.KEY_ROLE_SITIZEN)));
        contentValues.put(DBHelper.KEY_T4_SITIZEN_6_SEAT, role.indexOf(PlayersInGameRole.KEY_ROLE_SITIZEN)+1);
        userId.set(role.indexOf(PlayersInGameRole.KEY_ROLE_SITIZEN), -1);
        role.set(role.indexOf(PlayersInGameRole.KEY_ROLE_SITIZEN), "0");

        contentValues.put(DBHelper.KEY_T4_WIN_RED, isRedWin);
        contentValues.put(DBHelper.KEY_T4_KILED_FIRST, idKilled_1_night);
        contentValues.put(DBHelper.KEY_T4_KILED_SECOND, idKilled_2_night);
        contentValues.put(DBHelper.KEY_T4_KILED_THIRD, idKilled_3_night);
        contentValues.put(DBHelper.KEY_T4_PREFERABLE, idPreferablePlayer);
        contentValues.put(DBHelper.KEY_T4_LEADER, idLeader);
        contentValues.put(DBHelper.KEY_T4_NUMBER_OF_GAME, numberGame);
        contentValues.put(DBHelper.KEY_T4_TIME, mDBHelper.convertStringDataToLong(dateGame));
        contentValues.put(DBHelper.KEY_T4_COURSE_GAME, courseGame);

        //переносимо дані з контейнера в БД
        sqLiteDatabase.insert(DBHelper.TABLE_NAME_4, null, contentValues);
    }

    public void addNotesToDB(String title, String note){
        if(TextUtils.isEmpty(title) || TextUtils.isEmpty(note)) return;
        ContentValues contentValues = new ContentValues();
        contentValues.clear();
        contentValues.put(DBHelper.KEY_T5_TITLE, title);
        contentValues.put(DBHelper.KEY_T5_NOTE, note);
        sqLiteDatabase.insert(DBHelper.TABLE_NAME_5, null, contentValues);
    }

    public void deleteNoteFromDB(String title){
        if(TextUtils.isEmpty(title)) return;
        sqLiteDatabase.delete(DBHelper.TABLE_NAME_5, DBHelper.KEY_T5_TITLE + "= ?", new String[] {title});
    }

    public void updateNoteFromDB(String title, String note){
        if(TextUtils.isEmpty(title) || TextUtils.isEmpty(note)) return;
        ContentValues contentValues = new ContentValues();
        contentValues.clear();
        contentValues.put(DBHelper.KEY_T5_TITLE, title);
        contentValues.put(DBHelper.KEY_T5_NOTE, note);
        sqLiteDatabase.update(DBHelper.TABLE_NAME_5, contentValues, DBHelper.KEY_T5_TITLE + "= ?", new String[]{title});
    }

    public void addPeriodToDB(String namePeriod, String dateStart, String dateEnd, int userId, int minCountGames){
        ContentValues contentValues = new ContentValues();
        contentValues.clear();
        contentValues.put(DBHelper.KEY_T6_TITLE, namePeriod);
        contentValues.put(DBHelper.KEY_T6_DATE_START, dateStart);
        contentValues.put(DBHelper.KEY_T6_DATE_END, dateEnd);
        contentValues.put(DBHelper.KEY_T6_WINNER_ID, userId);
        contentValues.put(DBHelper.KEY_T6_MIN_GAMES, minCountGames);
        sqLiteDatabase.insert(DBHelper.TABLE_NAME_6, null, contentValues);
    }

}

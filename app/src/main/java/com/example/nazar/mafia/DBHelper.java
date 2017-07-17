package com.example.nazar.mafia;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Nazar on 6/29/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    //Constants for the database
    public final static int DATABASE_WERSION = 1;
    //Name DB
    public final static String DATABASE_NAME = "IF_MAFIA";
    //Name Table
    public static final String TABLE_NAME_1 = "passwords";
    public static final String TABLE_NAME_2 = "users";
    public static final String TABLE_NAME_3 = "titles";
    public static final String TABLE_NAME_4 = "games";
    public static final String TABLE_NAME_5 = "notes";
    public static final String TABLE_NAME_6 = "periods";

    //Column names
    public final static String KEY_ID = "_id";
    public final static String KEY_T1_USER_ID = "usersId";
    public final static String KEY_T1_PASWORD = "password";

    public final static String KEY_T2_TITLES_ID = "titlesId";
    public final static String KEY_T2_NICK_NAME = "nickName";
    public final static String KEY_T2_NAME = "userName";
    public final static String KEY_T2_SURNAME = "userSurname";
    public final static String KEY_T2_TIME = "userStartPlay";

    public final static String KEY_T3_TITLE = "title";
    public final static String[] KEY_TITLE = {"президент", "віце-президент", "сенатор", "член", "кандидат", "гість", "ведучий/ча"};

    public final static String KEY_T4_DON = "don"; //номер гравця з таблиці 2
    public final static String KEY_T4_DON_SEAT = "don_seat"; //номер за яким грає
    public final static String KEY_T4_SHERIF = "sherif";
    public final static String KEY_T4_SHERIF_SEAT = "sherif_seat";
    public final static String KEY_T4_MAF_1 = "maf_1";
    public final static String KEY_T4_MAF_1_SEAT = "maf_1_seat";
    public final static String KEY_T4_MAF_2 = "maf_2";
    public final static String KEY_T4_MAF_2_SEAT = "maf_2_seat";
    public final static String KEY_T4_SITIZEN_1 = "sitizen_1";
    public final static String KEY_T4_SITIZEN_1_SEAT = "sitizen_1_seat";
    public final static String KEY_T4_SITIZEN_2 = "sitizen_2";
    public final static String KEY_T4_SITIZEN_2_SEAT = "sitizen_2_seat";
    public final static String KEY_T4_SITIZEN_3 = "sitizen_3";
    public final static String KEY_T4_SITIZEN_3_SEAT = "sitizen_3_seat";
    public final static String KEY_T4_SITIZEN_4 = "sitizen_4";
    public final static String KEY_T4_SITIZEN_4_SEAT = "sitizen_4_seat";
    public final static String KEY_T4_SITIZEN_5 = "sitizen_5";
    public final static String KEY_T4_SITIZEN_5_SEAT = "sitizen_5_seat";
    public final static String KEY_T4_SITIZEN_6 = "sitizen_6";
    public final static String KEY_T4_SITIZEN_6_SEAT = "sitizen_6_seat";
    public final static String KEY_T4_WIN_RED = "win_red";
    public final static String KEY_T4_KILED_FIRST = "kiled_first";
    public final static String KEY_T4_KILED_SECOND = "kiled_second";
    public final static String KEY_T4_KILED_THIRD = "kiled_third";
    public final static String KEY_T4_PREFERABLE = "preferable";
    public final static String KEY_T4_LEADER = "leader";
    public final static String KEY_T4_NUMBER_OF_GAME = "number_of_game";
    public final static String KEY_T4_TIME = "time_of_game";
    public final static String KEY_T4_COURSE_GAME = "course_game";

    public final static String KEY_T5_TITLE = "title";
    public final static String KEY_T5_NOTE = "note";

    public final static String KEY_T6_TITLE = "title";
    public final static String KEY_T6_DATE_START = "data_start";
    public final static String KEY_T6_DATE_END = "data_end";
    public final static String KEY_T6_WINNER_ID = "winner_id";
    public final static String KEY_T6_MIN_GAMES = "min_games";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_WERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME_1 + "(" + KEY_ID
                + " integer primary key," + KEY_T1_USER_ID + " integer," + KEY_T1_PASWORD + " text)");

        sqLiteDatabase.execSQL("create table " + TABLE_NAME_2 + "(" + KEY_ID
                + " integer primary key," + KEY_T2_TITLES_ID + " integer," + KEY_T2_NICK_NAME + " text,"
                + KEY_T2_NAME + " text," + KEY_T2_SURNAME + " text," + KEY_T2_TIME + " integer)");

        sqLiteDatabase.execSQL("create table " + TABLE_NAME_3 + "(" + KEY_ID
                + " integer primary key," + KEY_T3_TITLE + " text)");

        sqLiteDatabase.execSQL("create table " + TABLE_NAME_4 + "(" + KEY_ID
                + " integer primary key," + KEY_T4_DON + " integer," + KEY_T4_DON_SEAT + " integer,"
                + KEY_T4_SHERIF + " integer," + KEY_T4_SHERIF_SEAT + " integer,"
                + KEY_T4_MAF_1 + " integer," + KEY_T4_MAF_1_SEAT + " integer,"
                + KEY_T4_MAF_2 + " integer," + KEY_T4_MAF_2_SEAT + " integer,"
                + KEY_T4_SITIZEN_1 + " integer," + KEY_T4_SITIZEN_1_SEAT + " integer,"
                + KEY_T4_SITIZEN_2 + " integer," + KEY_T4_SITIZEN_2_SEAT + " integer,"
                + KEY_T4_SITIZEN_3 + " integer," + KEY_T4_SITIZEN_3_SEAT + " integer,"
                + KEY_T4_SITIZEN_4 + " integer," + KEY_T4_SITIZEN_4_SEAT + " integer,"
                + KEY_T4_SITIZEN_5 + " integer," + KEY_T4_SITIZEN_5_SEAT + " integer,"
                + KEY_T4_SITIZEN_6 + " integer," + KEY_T4_SITIZEN_6_SEAT + " integer,"
                + KEY_T4_WIN_RED + " integer," + KEY_T4_KILED_FIRST + " integer,"
                + KEY_T4_KILED_SECOND + " integer," + KEY_T4_KILED_THIRD + " integer,"
                + KEY_T4_PREFERABLE + " integer," + KEY_T4_LEADER + " integer,"
                + KEY_T4_NUMBER_OF_GAME + " integer," + KEY_T4_TIME + " integer,"
                + KEY_T4_COURSE_GAME + " integer" + ")");

        sqLiteDatabase.execSQL("create table " + TABLE_NAME_5 + "(" + KEY_ID
                + " integer primary key, " + KEY_T5_TITLE + " text, " + KEY_T5_NOTE + " text)");

        sqLiteDatabase.execSQL("create table " + TABLE_NAME_6 + "(" + KEY_ID
                + " integer primary key," + KEY_T6_TITLE + " text," + KEY_T6_DATE_START + " text,"
                + KEY_T6_DATE_END + " text," + KEY_T6_WINNER_ID + " integer," + KEY_T6_MIN_GAMES + " integer)");

        addForDataBase(sqLiteDatabase);
    }

    private void addForDataBase(SQLiteDatabase sqLiteDatabase) {
        //створюємо контейнер який буде зберігати 1 повний запис
        ContentValues contentValues = new ContentValues();

        //заповнюємо таблицю 1
        int usersId[] = {2, 1, 6};
        String password[] = {"1111", "2222", "3333"};
        for (int i = 0; i < password.length; i++) {
            contentValues.clear();
            contentValues.put(KEY_T1_USER_ID, usersId[i]);
            contentValues.put(KEY_T1_PASWORD, UserChoice.md5Custom(password[i]));
            //переносимо дані з контейнера в БД
            sqLiteDatabase.insert(TABLE_NAME_1, null, contentValues);
        }

        //заповнюємо таблицю 2
        int title_id[] = {1, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 6};
        String usersNickName[] = {"Bazilio", "Fantom", "Raskat", "Felix", "Doom", "Jack", "Den", "House", "Betford", "Grisha", "Nukolka", "Nusya", "Tomat", "Byron"};
        String usersName[] = {"Василь", "Назар", "Олег", "Андрій", "Володимир", "Таня", "Денис", "Володимир", "Михайло", "Ігор", "Микола", "Ніна", "Ігор", "Василь"};
        String usersSurname[] = {"Маленкович", "Дзюбак", "Турчин", "Шанковський", "Тріщ", "Лазарук", "Заячківський", "Храбатин", "Павлюк", "Кизим", "Засідко", "Мартинюк", "Мартинюк", "Матковський"};
        String usersStartPlay[] = {"01.01.2011", "02.04.2011", "03.01.2011", "04.06.2011", "05.09.2011", "05.09.2011", "06.04.2011", "07.06.2011", "08.07.2012", "09.09.2011", "10.02.2011", "11.01.2011", "12.01.2011", "13.06.2012"};
        for (int i = 0; i < title_id.length; i++) {
            contentValues.clear();
            contentValues.put(KEY_T2_TITLES_ID, title_id[i]);
            contentValues.put(KEY_T2_NICK_NAME, usersNickName[i]);
            contentValues.put(KEY_T2_NAME, usersName[i]);
            contentValues.put(KEY_T2_SURNAME, usersSurname[i]);
            contentValues.put(KEY_T2_TIME, convertStringDataToLong(usersStartPlay[i]));
            //переносимо дані з контейнера в БД
            sqLiteDatabase.insert(TABLE_NAME_2, null, contentValues);
        }

        //заповнюємо таблицю 3
        for (String str_title : KEY_TITLE) {
            contentValues.clear();
            contentValues.put(KEY_T3_TITLE, str_title);
            //переносимо дані з контейнера в БД
            sqLiteDatabase.insert(TABLE_NAME_3, null, contentValues);
        }

        //заповнюємо таблицю 4
        contentValues.clear();
        contentValues.put(KEY_T4_DON, 2);
        contentValues.put(KEY_T4_DON_SEAT, 1);
        contentValues.put(KEY_T4_SHERIF, 3);
        contentValues.put(KEY_T4_SHERIF_SEAT, 2);
        contentValues.put(KEY_T4_MAF_1, 4);
        contentValues.put(KEY_T4_MAF_1_SEAT, 3);
        contentValues.put(KEY_T4_MAF_2, 5);
        contentValues.put(KEY_T4_MAF_2_SEAT, 4);
        contentValues.put(KEY_T4_SITIZEN_1, 6);
        contentValues.put(KEY_T4_SITIZEN_1_SEAT, 5);
        contentValues.put(KEY_T4_SITIZEN_2, 7);
        contentValues.put(KEY_T4_SITIZEN_2_SEAT, 6);
        contentValues.put(KEY_T4_SITIZEN_3, 8);
        contentValues.put(KEY_T4_SITIZEN_3_SEAT, 7);
        contentValues.put(KEY_T4_SITIZEN_4, 9);
        contentValues.put(KEY_T4_SITIZEN_4_SEAT, 8);
        contentValues.put(KEY_T4_SITIZEN_5, 10);
        contentValues.put(KEY_T4_SITIZEN_5_SEAT, 9);
        contentValues.put(KEY_T4_SITIZEN_6, 11);
        contentValues.put(KEY_T4_SITIZEN_6_SEAT, 10);
        contentValues.put(KEY_T4_WIN_RED, 1);
        contentValues.put(KEY_T4_KILED_FIRST, 11);
        contentValues.put(KEY_T4_KILED_SECOND, 10);
        contentValues.put(KEY_T4_KILED_THIRD, 9);
        contentValues.put(KEY_T4_PREFERABLE, 3);
        contentValues.put(KEY_T4_LEADER, 1);
        contentValues.put(KEY_T4_NUMBER_OF_GAME, 1);
        contentValues.put(KEY_T4_TIME, convertStringDataToLong("10.10.2017"));
        contentValues.put(KEY_T4_COURSE_GAME, 2);
        //переносимо дані з контейнера в БД
        sqLiteDatabase.insert(TABLE_NAME_4, null, contentValues);

        //заповнюємо таблицю 5
        contentValues.clear();
        contentValues.put(DBHelper.KEY_T5_TITLE, "Примітка");
        contentValues.put(DBHelper.KEY_T5_NOTE, "Тут може бути Ваш текст");
        sqLiteDatabase.insert(DBHelper.TABLE_NAME_5, null, contentValues);

        //заповнюємо таблицю 6
        contentValues.clear();
        contentValues.put(DBHelper.KEY_T6_TITLE, "Пробний");
        contentValues.put(DBHelper.KEY_T6_DATE_START, "10.10.2017");
        contentValues.put(DBHelper.KEY_T6_DATE_END, "11.10.2017");
        contentValues.put(DBHelper.KEY_T6_WINNER_ID, 3);
        contentValues.put(DBHelper.KEY_T6_MIN_GAMES, 1);
        sqLiteDatabase.insert(DBHelper.TABLE_NAME_6, null, contentValues);
    }

    public static long convertStringDataToLong(String date) {
        long result = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date1 = simpleDateFormat.parse(date);
            result = date1.getTime();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME_1);
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME_2);

        onCreate(sqLiteDatabase);
    }

}

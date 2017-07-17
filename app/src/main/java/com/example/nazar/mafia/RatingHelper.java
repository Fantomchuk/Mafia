package com.example.nazar.mafia;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by Nazar on 7/12/2017.
 */

public class RatingHelper {


    private Context context;
    private int[] idGames;
    private int numberGamesPeriod;

    private ArrayList<Integer> allUsersId = new ArrayList<>();
    private ArrayList<Double> sumPoints = new ArrayList<>();
    private ArrayList<Integer> allGames = new ArrayList<>();
    private ArrayList<Double> allRating = new ArrayList<>();
    private ArrayList<String> allNickName = new ArrayList<>();
    private ArrayList<Integer> allDonWin = new ArrayList<>();
    private ArrayList<Integer> allSherifWin = new ArrayList<>();
    private ArrayList<Integer> allMafWin = new ArrayList<>();
    private ArrayList<Integer> allSitizenWin = new ArrayList<>();
    private ArrayList<Integer> allPreferableWin = new ArrayList<>();
    private ArrayList<Integer> allKilledFirstWin = new ArrayList<>();
    private ArrayList<Integer> allCourseWin = new ArrayList<>();

    RatingHelper(Context mCtx, String dateStart, String dateEnd) {
        this.context = mCtx;
        getResultGames(dateStart, dateEnd);
    }

    ArrayList<Integer> getAllUsersId() {
        return allUsersId;
    }

    ArrayList<Double> getSumPoints() {
        return sumPoints;
    }

    ArrayList<Integer> getAllGames() {
        return allGames;
    }

    ArrayList<String> getAllNickName() {
        return allNickName;
    }

    ArrayList<Double> getAllRating() {
        for (int i = 0; i < allUsersId.size(); i++)
            allRating.add(getRatingForUser(allGames.get(i), sumPoints.get(i)));
        return allRating;
    }

    ArrayList<Integer> getAllDonWin() {
        return allDonWin;
    }

    ArrayList<Integer> getAllSherifWin() {
        return allSherifWin;
    }

    ArrayList<Integer> getAllMafWin() {
        return allMafWin;
    }

    ArrayList<Integer> getAllSitizenWin() {
        return allSitizenWin;
    }

    ArrayList<Integer> getAllPreferableWin() {
        return allPreferableWin;
    }

    ArrayList<Integer> getAllKilledFirstWin() {
        return allKilledFirstWin;
    }

    ArrayList<Integer> getAllCourseWin() {
        return allCourseWin;
    }

    int getNumberGamesPeriod() {
        return idGames.length;
    }

    int getIdWhoIsWinner(int countGames) {
        if(allUsersId.size() == 0)
            return 0;
        return allUsersId.get(getPositionInArray(countGames));
    }

    private double getRatingForUser(int numberOfGames, double points) {
        return 100 * points / numberOfGames + 0.25 * numberOfGames;
    }

    private void getResultGames(String dateStart, String dateEnd) {
        getIdGames(dateStart, dateEnd);
        OneGame[] allGame = new OneGame[idGames.length];
        for (int i = 0; i < idGames.length; i++) {
            allGame[i] = new OneGame(context, idGames[i]);

            ArrayList<String> role = allGame[i].getRoles();
            ArrayList<Integer> userId = allGame[i].getUsersId();
            ArrayList<String> nickName = allGame[i].getUsersNickName();
            int isRedWin = allGame[i].win_red;
            int idPreferablePlayer = allGame[i].preferable;
            int idKilled_1_night = allGame[i].kiled_first;
            int courseGame = allGame[i].course_game;

            for (int j = 0; j < 10; j++) {
                double points = 0;
                int donWin = 0;
                int sherifWin = 0;
                int mafWin = 0;
                int sitezenWin = 0;
                int preferableWin = 0;
                int killedFirstWin = 0;
                int courseWin = 0;
                if (role.get(j).equals(PlayersInGameRole.KEY_ROLE_SITIZEN)) {
                    if (userId.get(j) == idKilled_1_night) {
                        killedFirstWin = 1;
                        if (courseGame == 3) {
                            courseWin = 3;
                            points = points + 1;
                        }
                        if (courseGame == 2) {
                            courseWin = 2;
                            points = points + 0.5;
                        }
                    }
                    if (isRedWin == 1) {
                        sitezenWin = 1;
                        points = points + 3;
                    }
                    if (userId.get(j) == idPreferablePlayer) {
                        preferableWin = 1;
                        points = points + 1;
                    }
                }
                if (role.get(j).equals(PlayersInGameRole.KEY_ROLE_SHERIF)) {
                    if (userId.get(j) == idKilled_1_night) {
                        killedFirstWin = 1;
                        if (courseGame == 3) {
                            courseWin = 3;
                            points = points + 1;
                        }
                        if (courseGame == 2) {
                            courseWin = 2;
                            points = points + 0.5;
                        }
                    }
                    if (isRedWin == 1) {
                        points = points + 4;
                        sherifWin = 1;
                    } else if (userId.get(j) != idKilled_1_night) points = points - 1;
                    if (userId.get(j) == idPreferablePlayer) {
                        preferableWin = 1;
                        points = points + 1;
                    }
                }
                if (role.get(j).equals(PlayersInGameRole.KEY_ROLE_MAF)) {
                    if (userId.get(j) == idKilled_1_night) killedFirstWin = 1;
                    if (isRedWin == 0) {
                        mafWin = 1;
                        points = points + 4;
                    }
                    if (userId.get(j) == idPreferablePlayer) {
                        preferableWin = 1;
                        points = points + 1;
                    }
                }
                if (role.get(j).equals(PlayersInGameRole.KEY_ROLE_DON)) {
                    if (userId.get(j) == idKilled_1_night) killedFirstWin = 1;
                    if (isRedWin == 0) {
                        points = points + 5;
                        donWin = 1;
                    } else
                        points = points - 1;
                    if (userId.get(j) == idPreferablePlayer) {
                        preferableWin = 1;
                        points = points + 1;
                    }
                }

                if (!allUsersId.contains(userId.get(j))) {
                    allNickName.add(nickName.get(j));
                    allUsersId.add(userId.get(j));
                    sumPoints.add(points);
                    allGames.add(1);
                    allDonWin.add(donWin);
                    allSherifWin.add(sherifWin);
                    allMafWin.add(mafWin);
                    allSitizenWin.add(sitezenWin);
                    allPreferableWin.add(preferableWin);
                    allKilledFirstWin.add(killedFirstWin);
                    allCourseWin.add(courseWin);
                } else {
                    sumPoints.set(allUsersId.indexOf(userId.get(j)), sumPoints.get(allUsersId.indexOf(userId.get(j))) + points);
                    allGames.set(allUsersId.indexOf(userId.get(j)), allGames.get(allUsersId.indexOf(userId.get(j))) + 1);
                    allDonWin.set(allUsersId.indexOf(userId.get(j)), allDonWin.get(allUsersId.indexOf(userId.get(j))) + donWin);
                    allSherifWin.set(allUsersId.indexOf(userId.get(j)), allSherifWin.get(allUsersId.indexOf(userId.get(j))) + sherifWin);
                    allMafWin.set(allUsersId.indexOf(userId.get(j)), allMafWin.get(allUsersId.indexOf(userId.get(j))) + mafWin);
                    allSitizenWin.set(allUsersId.indexOf(userId.get(j)), allSitizenWin.get(allUsersId.indexOf(userId.get(j))) + sitezenWin);
                    allPreferableWin.set(allUsersId.indexOf(userId.get(j)), allPreferableWin.get(allUsersId.indexOf(userId.get(j))) + preferableWin);
                    allKilledFirstWin.set(allUsersId.indexOf(userId.get(j)), allKilledFirstWin.get(allUsersId.indexOf(userId.get(j))) + killedFirstWin);
                    allCourseWin.set(allUsersId.indexOf(userId.get(j)), allCourseWin.get(allUsersId.indexOf(userId.get(j))) + courseWin);
                }
            }
        }
    }


    private void getIdGames(String dateStart, String dateEnd) {
        DBHelper db = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        Cursor cursor;
        String str_sql = "select g._id " +
                "from games as g " +
                "where g.time_of_game >= ? and g.time_of_game <= ?";

        long dateStart_int = DBHelper.convertStringDataToLong(dateStart);
        long dateEnd_int = DBHelper.convertStringDataToLong(dateEnd);
        String[] selectionArgs = new String[]{String.valueOf(dateStart_int), String.valueOf(dateEnd_int)};
        cursor = sqLiteDatabase.rawQuery(str_sql, selectionArgs);

        idGames = new int[cursor.getCount()];
        if (cursor.moveToFirst()) {
            int idGames_Index = cursor.getColumnIndex(DBHelper.KEY_ID);
            int i = 0;
            do {
                idGames[i] = cursor.getInt(idGames_Index);
                i++;
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }

    private int getPositionInArray(int countGames) {
        double result = 0;
        int i = 0;
        int positionInArray = i;
        do {
            if (allGames.get(i) >= countGames) {
                if (result < getRatingForUser(allGames.get(i), sumPoints.get(i))) {
                    result = getRatingForUser(allGames.get(i), sumPoints.get(i));
                    positionInArray = i;
                }
            }
            i++;
        } while (i < allGames.size());
        return positionInArray;
    }

}

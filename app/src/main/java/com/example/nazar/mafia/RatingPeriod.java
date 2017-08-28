package com.example.nazar.mafia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class RatingPeriod extends AppCompatActivity {

    public static final String KEY_ARRAY_PLAYERS_NICK_NAME = "array_players_nick_name_key";
    public static final String KEY_ARRAY_PLAYERS_GAMES = "array_players_games_key";
    public static final String KEY_ARRAY_PLAYERS_RESULT = "array_players_result_key";
    public static final String KEY_COUNT_GAME = "count_game_key";

    public static final String INTENT_ACTION_RATING = "intent_action_rating";
    public static final String INTENT_ACTION_GAME = "intent_action_game";
    public static final String INTENT_ACTION_DON = "intent_action_don";
    public static final String INTENT_ACTION_SHERIF = "intent_action_sherif";
    public static final String INTENT_ACTION_MAF = "intent_action_maf";
    public static final String INTENT_ACTION_SITIZEN = "intent_action_sitizen";
    public static final String INTENT_ACTION_PREFERABLE = "intent_action_preferable";
    public static final String INTENT_ACTION_COURSE = "intent_action_course";
    public static final String INTENT_ACTION_KILLED = "intent_action_killed";

    String dateStart, dateEnd;
    int minCountGames;
    RatingHelper rating;
    TextView tv_RatingPeriod_date;

    Intent intent_show;
    ArrayList<Integer> resultReting;
    ArrayList<Double> rating_double;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_period);

        Intent getIntent = getIntent();
        dateStart = getIntent.getStringExtra(NewTimePeriod.KEY_FOR_DATA_START);
        dateEnd = getIntent.getStringExtra(NewTimePeriod.KEY_FOR_DATA_END);
        minCountGames = getIntent.getIntExtra(NewTimePeriod.KEY_FOR_MIN_COUNT_GAMES, 1);

        tv_RatingPeriod_date = (TextView) findViewById(R.id.tv_RatingPeriod_date);
        tv_RatingPeriod_date.setText(getResources().getString(R.string.ShowGame_title_1) + dateStart + "\n" +
                getResources().getString(R.string.ShowGame_title_2) + dateEnd);

        rating = new RatingHelper(this, dateStart, dateEnd);

        resultReting = new ArrayList<>();
        rating_double = rating.getAllRating();
        for (int i = 0; i < rating_double.size(); i++)
            resultReting.add((int) (rating_double.get(i) * 10));

        int numberGamesPeriod = rating.getNumberGamesPeriod();
        if (numberGamesPeriod == 0){
            findViewById(R.id.rl_RatingPeriod_list).setVisibility(View.GONE);
        }
        TextView tv_numberGamesPeriod = (TextView) findViewById(R.id.tv_RatingPeriod_numberGamesPeriod);
        tv_numberGamesPeriod.setText(getResources().getString(R.string.ShowRating_title_12)+numberGamesPeriod);
    }

    public void onTextClickRatingPeriod(View view) {
        intent_show= new Intent();
        intent_show.setClass(this, ShowRating.class);
        intent_show.putExtra(KEY_ARRAY_PLAYERS_NICK_NAME, rating.getAllNickName());
        intent_show.putExtra(KEY_ARRAY_PLAYERS_GAMES, rating.getAllGames());
        intent_show.putExtra(KEY_COUNT_GAME, minCountGames);


        switch (view.getId()){
            case R.id.tv_RatingPeriod_title_1:
                intent_show.setAction(INTENT_ACTION_RATING);
                intent_show.putExtra(KEY_ARRAY_PLAYERS_RESULT, resultReting);
                startActivity(intent_show);
                break;
            case R.id.tv_RatingPeriod_title_2:
                intent_show.setAction(INTENT_ACTION_GAME);
                intent_show.putExtra(KEY_ARRAY_PLAYERS_RESULT, rating.getAllGames());
                startActivity(intent_show);
                break;
            case R.id.tv_RatingPeriod_title_3:
                intent_show.setAction(INTENT_ACTION_DON);
                intent_show.putExtra(KEY_ARRAY_PLAYERS_RESULT, rating.getAllDonWin());
                startActivity(intent_show);
                break;
            case R.id.tv_RatingPeriod_title_4:
                intent_show.setAction(INTENT_ACTION_SHERIF);
                intent_show.putExtra(KEY_ARRAY_PLAYERS_RESULT, rating.getAllSherifWin());
                startActivity(intent_show);
                break;
            case R.id.tv_RatingPeriod_title_5:
                intent_show.setAction(INTENT_ACTION_MAF);
                intent_show.putExtra(KEY_ARRAY_PLAYERS_RESULT, rating.getAllMafWin());
                startActivity(intent_show);
                break;
            case R.id.tv_RatingPeriod_title_6:
                intent_show.setAction(INTENT_ACTION_SITIZEN);
                intent_show.putExtra(KEY_ARRAY_PLAYERS_RESULT, rating.getAllSitizenWin());
                startActivity(intent_show);
                break;
            case R.id.tv_RatingPeriod_title_7:
                intent_show.setAction(INTENT_ACTION_PREFERABLE);
                intent_show.putExtra(KEY_ARRAY_PLAYERS_RESULT, rating.getAllPreferableWin());
                startActivity(intent_show);
                break;
            case R.id.tv_RatingPeriod_title_8:
                intent_show.setAction(INTENT_ACTION_KILLED);
                intent_show.putExtra(KEY_ARRAY_PLAYERS_RESULT, rating.getAllKilledFirstWin());
                startActivity(intent_show);
                break;
            case R.id.tv_RatingPeriod_title_9:
                intent_show.setAction(INTENT_ACTION_COURSE);
                intent_show.putExtra(KEY_ARRAY_PLAYERS_RESULT, rating.getAllCourseWin());
                startActivity(intent_show);
                break;
        }
    }

    public void onTextClickRatingPeriod_1(View view) {
        if(view.getId() == R.id.img_RatingPeriod_1)
            onBackPressed();
    }
}

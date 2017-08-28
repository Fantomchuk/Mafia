package com.example.nazar.mafia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowRating extends AppCompatActivity {

    private static final String ATTRIBUTE_NICK_NAME = "nick_name_atr";
    private static final String ATTRIBUTE_RESULT = "result_atr";
    private static final String ATTRIBUTE_MIDDLE = "middle_atr";
    private static final String ATTRIBUTE_PLAYER_NUMBER = "player_number_atr";

    ArrayList<Map<String, Object>> data;
    String action;
    ListView lv_ShowRating;
    TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_rating);

        Intent intent_get = getIntent();
        action = intent_get.getAction();
        ArrayList<String> nickName = intent_get.getStringArrayListExtra(RatingPeriod.KEY_ARRAY_PLAYERS_NICK_NAME);
        ArrayList<Integer> games = intent_get.getIntegerArrayListExtra(RatingPeriod.KEY_ARRAY_PLAYERS_GAMES);
        int minCountGames = intent_get.getIntExtra(RatingPeriod.KEY_COUNT_GAME, 1);
        ArrayList<Integer> results = intent_get.getIntegerArrayListExtra(RatingPeriod.KEY_ARRAY_PLAYERS_RESULT);

        tv_title = (TextView) findViewById(R.id.tv_ShowRating_title);
        switch (action) {
            case RatingPeriod.INTENT_ACTION_RATING:
                tv_title.setText(getResources().getString(R.string.ShowRating_title_0) + getResources().getString(R.string.ShowRating_title_1));
                break;
            case RatingPeriod.INTENT_ACTION_GAME:
                tv_title.setText(getResources().getString(R.string.ShowRating_title_0) + getResources().getString(R.string.ShowRating_title_2));
                break;
            case RatingPeriod.INTENT_ACTION_DON:
                tv_title.setText(getResources().getString(R.string.ShowRating_title_0) + getResources().getString(R.string.ShowRating_title_3) + getResources().getString(R.string.ShowRating_title_4));
                break;
            case RatingPeriod.INTENT_ACTION_SHERIF:
                tv_title.setText(getResources().getString(R.string.ShowRating_title_0) + getResources().getString(R.string.ShowRating_title_3) + getResources().getString(R.string.ShowRating_title_5));
                break;
            case RatingPeriod.INTENT_ACTION_MAF:
                tv_title.setText(getResources().getString(R.string.ShowRating_title_0) + getResources().getString(R.string.ShowRating_title_3) + getResources().getString(R.string.ShowRating_title_6));
                break;
            case RatingPeriod.INTENT_ACTION_SITIZEN:
                tv_title.setText(getResources().getString(R.string.ShowRating_title_0) + getResources().getString(R.string.ShowRating_title_3) + getResources().getString(R.string.ShowRating_title_7));
                break;
            case RatingPeriod.INTENT_ACTION_PREFERABLE:
                tv_title.setText(getResources().getString(R.string.ShowRating_title_0) + getResources().getString(R.string.ShowRating_title_8));
                break;
            case RatingPeriod.INTENT_ACTION_KILLED:
                tv_title.setText(getResources().getString(R.string.ShowRating_title_0) + getResources().getString(R.string.ShowRating_title_9));
                break;
            case RatingPeriod.INTENT_ACTION_COURSE:
                tv_title.setText(getResources().getString(R.string.ShowRating_title_0) + getResources().getString(R.string.ShowRating_title_10));
                break;
        }

        for(int i = 0; i < nickName.size(); i++) {
            if (games.get(i) < minCountGames || results.get(i) == 0) {
                nickName.remove(i);
                results.remove(i);
                games.remove(i);
                i=-1;
            }
        }

        for (int i = results.size() - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (results.get(j) < results.get(j+1)) {
                    int res = results.get(j);
                    String nick = nickName.get(j);
                    results.set(j, results.get(j+1));
                    nickName.set(j, nickName.get(j+1));
                    results.set(j+1, res);
                    nickName.set(j+1, nick);
                }
            }
        }
        if(nickName.size() == 0){
            tv_title.setText(getResources().getString(R.string.ShowRating_title_11));
        }

        data = new ArrayList<>(nickName.size());
        Map<String, Object> m;
        for (int i = 0; i < nickName.size(); i++) {
            m = new HashMap<>();
            m.put(ATTRIBUTE_PLAYER_NUMBER, String.valueOf(i+1) + ". ");
            m.put(ATTRIBUTE_NICK_NAME, nickName.get(i));
            m.put(ATTRIBUTE_MIDDLE, "-");
            if (action.equals(RatingPeriod.INTENT_ACTION_RATING))
                m.put(ATTRIBUTE_RESULT, (double) results.get(i)/10);
            else
                m.put(ATTRIBUTE_RESULT, results.get(i));
            data.add(m);
        }

        String[] from = new String[] {ATTRIBUTE_PLAYER_NUMBER,ATTRIBUTE_NICK_NAME,ATTRIBUTE_MIDDLE,ATTRIBUTE_RESULT};
        int[] to = new int[] {R.id.tv_item_game_1, R.id.tv_item_game_2, R.id.tv_item_game_3, R.id.tv_item_game_4};
        SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.item_game, from, to);
        lv_ShowRating = (ListView) findViewById(R.id.lv_ShowRating);
        lv_ShowRating.setAdapter(sAdapter);

    }

    public void onTextClickShowRating(View view) {
        switch (view.getId()){
            case R.id.img_ShowRating_1:
                onBackPressed();
                break;
        }
    }
}

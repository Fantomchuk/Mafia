package com.example.nazar.mafia;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayersInGameList extends AppCompatActivity {

    Intent intent_inputs;
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

    ArrayList<Map<String, Object>> data;
    Map<String, Object> m;
    SimpleAdapter sAdapter;
    ListView lv_PlayersInGameList;
    TextView textView_PlayersInGameList_numberGame_3, textView_PlayersInGameList_leader_3, textView_PlayersInGameList_date_3;

    private static final String ATRIBUT_RELATIVE_LAYOUT = "relative_layout_atr";
    private static final String ATRIBUT_PLAYER_NICK_NAME = "player_nick_name_atr";
    private static final String ATRIBUT_PLAYER_NUMBER_PLACES = "player_number_places_atr";
    private static final String ATRIBUT_PLAYER_ROLE = "player_role_atr";
    private static final String ATRIBUT_PLAYER_KILLED = "player_killed_atr";
    private static final String ATRIBUT_COURSE_GAME = "course_game_atr";
    private static final String ATRIBUT_PLAYER_WINNER = "player_winner_atr";
    private static final String ATRIBUT_PLAYER_PREFERABLE = "player_preferable_atr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_in_game_list);

        intent_inputs = getIntent();

        String action = intent_inputs.getAction();
        if (action.equals(ShowGame.INTENT_ACTION_SHOW_GAME)){
            Button bt_PlayersInGameList_endGame = (Button) findViewById(R.id.bt_PlayersInGameList_endGame);
            bt_PlayersInGameList_endGame.setVisibility(View.GONE);
        }

        idLeaderFromUsersTable = intent_inputs.getIntExtra(NewGame.KEY_FOR_LEADER_ID, -1);
        nickNameLeader = intent_inputs.getStringExtra(NewGame.KEY_FOR_NICK_NAME_LEADER);
        numberGame = intent_inputs.getIntExtra(NewGame.KEY_FOR_NUMBER_GAME, 0);
        dateGame = intent_inputs.getStringExtra(NewGame.KEY_FOR_DATE_GAME);
        nickName = intent_inputs.getStringArrayListExtra(PlayersInGame.KEY_NICK_NAME_ARRAY_LIST);
        userId = intent_inputs.getIntegerArrayListExtra(PlayersInGame.KEY_USER_ID);
        role = intent_inputs.getStringArrayListExtra(PlayersInGameRole.KEY_ROLE_ARRAY_LIST);

        idKilled_1_night = intent_inputs.getIntExtra(StartGame.KEY_KILLED_1_NIGHT, -1);
        idKilled_2_night = intent_inputs.getIntExtra(StartGame.KEY_KILLED_2_NIGHT, -1);
        idKilled_3_night = intent_inputs.getIntExtra(StartGame.KEY_KILLED_3_NIGHT, -1);
        courseGame = intent_inputs.getIntExtra(StartGame.KEY_COURSE_GAME, -1);
        idPreferablePlayer = intent_inputs.getIntExtra(StartGame.KEY_PREFERABLE_PLAYER, -1);
        isRedWin = intent_inputs.getIntExtra(StartGame.KEY_WINNER_TEAM, -1);
        String[] number_places = getResources().getStringArray(R.array.PlayersInGame_title_number);

        textView_PlayersInGameList_numberGame_3 = (TextView) findViewById(R.id.textView_PlayersInGameList_numberGame_3);
        textView_PlayersInGameList_numberGame_3.setText(String.valueOf(numberGame));
        textView_PlayersInGameList_leader_3 = (TextView) findViewById(R.id.textView_PlayersInGameList_leader_3);
        textView_PlayersInGameList_leader_3.setText(nickNameLeader);
        textView_PlayersInGameList_date_3 = (TextView) findViewById(R.id.textView_PlayersInGameList_date_3);
        textView_PlayersInGameList_date_3.setText(dateGame);

        data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            m = new HashMap<>();
            m.put(ATRIBUT_RELATIVE_LAYOUT, role.get(i));
            m.put(ATRIBUT_PLAYER_NICK_NAME, nickName.get(i));
            m.put(ATRIBUT_PLAYER_NUMBER_PLACES, number_places[i]);
            m.put(ATRIBUT_PLAYER_ROLE, role.get(i));
            if (idKilled_1_night == userId.get(i) || idKilled_2_night ==userId.get(i) || idKilled_3_night == userId.get(i)) {
                int temp = 0;
                if (idKilled_1_night == userId.get(i)) temp = 1;
                if (idKilled_2_night == userId.get(i)) temp = 2;
                if (idKilled_3_night == userId.get(i)) temp = 3;
                m.put(ATRIBUT_PLAYER_KILLED, temp);
            } else
                m.put(ATRIBUT_PLAYER_KILLED, 0);

            if (idKilled_1_night == userId.get(i))
                if (role.get(i).equals(PlayersInGameRole.KEY_ROLE_SITIZEN) || role.get(i).equals(PlayersInGameRole.KEY_ROLE_SHERIF))
                    m.put(ATRIBUT_COURSE_GAME, courseGame);
                else {
                    m.put(ATRIBUT_COURSE_GAME, 4);
                    courseGame = -1;
                }
            else
                m.put(ATRIBUT_COURSE_GAME, -1);

            if (role.get(i).equals(PlayersInGameRole.KEY_ROLE_SITIZEN) || role.get(i).equals(PlayersInGameRole.KEY_ROLE_SHERIF)) {
                if (isRedWin == 1)
                    m.put(ATRIBUT_PLAYER_WINNER, 1);
                else
                    m.put(ATRIBUT_PLAYER_WINNER, 0);
            } else {
                if (isRedWin != 1)
                    m.put(ATRIBUT_PLAYER_WINNER, 1);
                else
                    m.put(ATRIBUT_PLAYER_WINNER, 0);
            }

            if (idPreferablePlayer != 0 && i == userId.indexOf(idPreferablePlayer))
                m.put(ATRIBUT_PLAYER_PREFERABLE, 1);
            else
                m.put(ATRIBUT_PLAYER_PREFERABLE, 0);

            data.add(m);
        }
        String from[] = new String[]{ATRIBUT_RELATIVE_LAYOUT, ATRIBUT_PLAYER_NICK_NAME, ATRIBUT_PLAYER_NUMBER_PLACES, ATRIBUT_PLAYER_ROLE, ATRIBUT_PLAYER_KILLED, ATRIBUT_COURSE_GAME, ATRIBUT_PLAYER_WINNER, ATRIBUT_PLAYER_PREFERABLE};
        int to[] = new int[]{R.id.rL_item_result_player, R.id.item_result_nickName_4, R.id.item_result_nickName_2, R.id.item_result_role_4, R.id.ll_item_result_killed, R.id.ll_item_result_courseGame, R.id.item_result_winner_4, R.id.ll_item_result_preferable};

        sAdapter = new SimpleAdapter(this, data, R.layout.item_result, from, to);
        sAdapter.setViewBinder(new MyViewBinder());

        lv_PlayersInGameList = (ListView) findViewById(R.id.lv_PlayersInGameList);
        lv_PlayersInGameList.setAdapter(sAdapter);
    }

    public void onTextClickPlayersInGameList(View view) {
        switch (view.getId()) {
            case R.id.img_PlayersInGameList_1:
                onBackPressed();
                break;
            case R.id.bt_PlayersInGameList_endGame:
                DB db = new DB(this);
                db.open();
                db.addGameToDB(userId, role, isRedWin, idKilled_1_night, idKilled_2_night, idKilled_3_night,
                        idPreferablePlayer, idLeaderFromUsersTable, numberGame, dateGame, courseGame);
                db.close();
                Intent intent= new Intent();
                intent.putExtra(NewGame.KEY_FOR_FINISH_NEW_GAME, true);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    class MyViewBinder implements SimpleAdapter.ViewBinder {
        @Override
        public boolean setViewValue(View view, Object data, String textRepresentation) {
            switch (view.getId()) {
                case R.id.ll_item_result_preferable:
                    if ((int) data == 1)
                        view.setVisibility(View.VISIBLE);
                    else
                        view.setVisibility(View.GONE);
                    return true;
                case R.id.item_result_role_4:
                    if (data.equals(PlayersInGameRole.KEY_ROLE_SITIZEN))
                        ((TextView) view).setText(getResources().getString(R.string.PlayersInGameList_title_14));
                    if (data.equals(PlayersInGameRole.KEY_ROLE_MAF))
                        ((TextView) view).setText(getResources().getString(R.string.PlayersInGameList_title_15));
                    if (data.equals(PlayersInGameRole.KEY_ROLE_SHERIF))
                        ((TextView) view).setText(getResources().getString(R.string.PlayersInGameList_title_16));
                    if (data.equals(PlayersInGameRole.KEY_ROLE_DON))
                        ((TextView) view).setText(getResources().getString(R.string.PlayersInGameList_title_17));
                    return true;
                case R.id.item_result_winner_4:
                    if ((int) data == 1)
                        ((TextView) view).setText(getResources().getString(R.string.PlayersInGameList_title_11));
                    else
                        ((TextView) view).setText(getResources().getString(R.string.PlayersInGameList_title_12));
                    return true;
                case R.id.ll_item_result_courseGame:
                    view.setVisibility(View.VISIBLE);
                    switch ((int) data) {
                        case 0:
                            ((TextView) view.findViewById(R.id.item_result_courseGame_4)).setText(getResources().getString(R.string.PlayersInGameList_title_7));
                            break;
                        case 1:
                            ((TextView) view.findViewById(R.id.item_result_courseGame_4)).setText(getResources().getString(R.string.PlayersInGameList_title_8));
                            break;
                        case 2:
                            ((TextView) view.findViewById(R.id.item_result_courseGame_4)).setText(getResources().getString(R.string.PlayersInGameList_title_9));
                            break;
                        case 3:
                            ((TextView) view.findViewById(R.id.item_result_courseGame_4)).setText(getResources().getString(R.string.PlayersInGameList_title_10));
                            break;
                        case 4:
                            ((TextView) view.findViewById(R.id.item_result_courseGame_4)).setText(getResources().getString(R.string.PlayersInGameList_title_13));
                            break;
                        default:
                            view.setVisibility(View.GONE);
                    }
                    return true;
                case R.id.ll_item_result_killed:
                    view.setVisibility(View.VISIBLE);
                    switch ((int) data) {
                        case 1:
                            ((TextView) view.findViewById(R.id.item_result_killed_4)).setText(getResources().getString(R.string.ChoiceKilled_title_1));
                            break;
                        case 2:
                            ((TextView) view.findViewById(R.id.item_result_killed_4)).setText(getResources().getString(R.string.ChoiceKilled_title_2));
                            break;
                        case 3:
                            ((TextView) view.findViewById(R.id.item_result_killed_4)).setText(getResources().getString(R.string.ChoiceKilled_title_3));
                            break;
                        default:
                            view.setVisibility(View.GONE);
                    }
                    return true;
                case R.id.rL_item_result_player:
                    switch ((String) data) {
                        case PlayersInGameRole.KEY_ROLE_DON:
                            view.setBackgroundColor(Color.argb(200, 0, 0, 0));
                            break;
                        case PlayersInGameRole.KEY_ROLE_SHERIF:
                            view.setBackgroundColor(Color.argb(126, 0, 150, 0));
                            break;
                        case PlayersInGameRole.KEY_ROLE_MAF:
                            view.setBackgroundColor(Color.argb(126, 66, 66, 66));
                            break;
                        case PlayersInGameRole.KEY_ROLE_SITIZEN:
                            view.setBackgroundColor(Color.argb(126, 255, 0, 0));
                            break;
                    }
                    return true;
            }
            return false;
        }
    }
}

package com.example.nazar.mafia;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StartGame extends AppCompatActivity implements MyDialogQuestion.MyDialogListener, MyDialogList.MyDialogListListener{

    public static final String INTENT_ACTION_PREFERABLE = "intent_action_preferable";

    Intent intent_inputs;
    int idLeaderFromUsersTable;
    ArrayList<String> nickName = new ArrayList<>(10);
    ArrayList<Integer> userId = new ArrayList<>(10);
    ArrayList<Integer> usersIdWhoFinishedGame = new ArrayList<>(10);
    ArrayList<Integer> usersNotificationsInGame = new ArrayList<>(10);
    ArrayList<String> role = new ArrayList<>(10);
    String nickNameLeader, dateGame;
    int numberGame;
    String[] number_places;
    Boolean flagIsRole = false;
    int idKilled_1_night = -1, idKilled_2_night = -1, idKilled_3_night = -1;
    int courseGame = -1;
    int idPreferablePlayer = -1;
    int isRedWin = -1;
    int positionKilled_1 = -1,positionKilled_2 = -1,positionKilled_3 = -1;

    MyDialogQuestion dlg_team_winner, dlg_close_activity;
    MyDialogList dlg_course_game;

    public static final String KEY_KILLED_1_NIGHT = "killed_1_night_key";
    public static final String KEY_KILLED_2_NIGHT = "killed_2_night_key";
    public static final String KEY_KILLED_3_NIGHT = "killed_3_night_key";
    public static final String KEY_COURSE_GAME = "course_game_key";
    public static final String KEY_PREFERABLE_PLAYER = "preferable_player_key";
    public static final String KEY_WINNER_TEAM = "winner_team_key";
    public static final String KEY_USERS_ID_WHO_FINISHED = "users_id_who_finished_key";
    public static final String KEY_USERS_NOTIFICATIONS = "users_notifications_key";

    private static final int REQUEST_KILLED = 1;
    private static final int REQUEST_PREFERABLE = 2;
    private static final int REQUEST_START_DAY = 3;
    private static final int REQUEST_CODE_FINISH_NEW_GAME = 555;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("idKilled_1_night", idKilled_1_night);
        outState.putInt("idKilled_2_night", idKilled_2_night);
        outState.putInt("idKilled_3_night", idKilled_3_night);
        outState.putInt("courseGame", courseGame);
        outState.putInt("idPreferablePlayer", idPreferablePlayer);
        outState.putInt("isRedWin", isRedWin);
        outState.putInt("positionKilled_1", positionKilled_1);
        outState.putInt("positionKilled_2", positionKilled_2);
        outState.putInt("positionKilled_3", positionKilled_3);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        if (savedInstanceState != null) {
            idKilled_1_night = savedInstanceState.getInt("idKilled_1_night");
            idKilled_2_night = savedInstanceState.getInt("idKilled_2_night");
            idKilled_3_night = savedInstanceState.getInt("idKilled_3_night");
            courseGame = savedInstanceState.getInt("courseGame");
            idPreferablePlayer = savedInstanceState.getInt("idPreferablePlayer");
            isRedWin = savedInstanceState.getInt("isRedWin");
            positionKilled_1 = savedInstanceState.getInt("positionKilled_1");
            positionKilled_2 = savedInstanceState.getInt("positionKilled_2");
            positionKilled_3 = savedInstanceState.getInt("positionKilled_3");
        }

        intent_inputs = getIntent();
        idLeaderFromUsersTable = intent_inputs.getIntExtra(NewGame.KEY_FOR_LEADER_ID, -1);
        nickNameLeader = intent_inputs.getStringExtra(NewGame.KEY_FOR_NICK_NAME_LEADER);
        numberGame = intent_inputs.getIntExtra(NewGame.KEY_FOR_NUMBER_GAME, 0);
        dateGame = intent_inputs.getStringExtra(NewGame.KEY_FOR_DATE_GAME);
        nickName =  intent_inputs.getStringArrayListExtra(PlayersInGame.KEY_NICK_NAME_ARRAY_LIST);
        userId = intent_inputs.getIntegerArrayListExtra(PlayersInGame.KEY_USER_ID);
        role =  intent_inputs.getStringArrayListExtra(PlayersInGameRole.KEY_ROLE_ARRAY_LIST);

//        idLeaderFromUsersTable = 1;
//        nickNameLeader = "Bazailio";
//        numberGame = 1;
//        dateGame = "22.02.2017";
//        nickName.add("Fantom"); userId.add(2); role.add(PlayersInGameRole.KEY_ROLE_DON);
//        nickName.add("Raskat"); userId.add(3); role.add(PlayersInGameRole.KEY_ROLE_SHERIF);
//        nickName.add("Felix"); userId.add(4); role.add(PlayersInGameRole.KEY_ROLE_MAF);
//        nickName.add("Doom"); userId.add(5); role.add(PlayersInGameRole.KEY_ROLE_MAF);
//        nickName.add("Jack"); userId.add(6); role.add(PlayersInGameRole.KEY_ROLE_SITIZEN);
//        nickName.add("Grisha"); userId.add(10); role.add(PlayersInGameRole.KEY_ROLE_SITIZEN);
//        nickName.add("Nukolka"); userId.add(11); role.add(PlayersInGameRole.KEY_ROLE_SITIZEN);
//        nickName.add("Tomat"); userId.add(13); role.add(PlayersInGameRole.KEY_ROLE_SITIZEN);
//        nickName.add("Byron"); userId.add(14); role.add(PlayersInGameRole.KEY_ROLE_SITIZEN);
//        nickName.add("Nusya"); userId.add(12); role.add(PlayersInGameRole.KEY_ROLE_SITIZEN);

        TextView tv_StartGame_number_game, tv_StartGame_date, tv_StartGame_leader;
        number_places = getResources().getStringArray(R.array.PlayersInGame_title_number);

        for(int i=0; i < 10; i++) {
            usersIdWhoFinishedGame.add(i, -1);
            usersNotificationsInGame.add(i, 0);
        }

        tv_StartGame_number_game = (TextView) findViewById(R.id.tv_StartGame_number_game);
        tv_StartGame_number_game.setText(String.valueOf(numberGame));
        tv_StartGame_date = (TextView) findViewById(R.id.tv_StartGame_date);
        tv_StartGame_date.setText(dateGame);
        tv_StartGame_leader = (TextView) findViewById(R.id.tv_StartGame_leader);
        tv_StartGame_leader.setText(nickNameLeader);

        changeViewGame();
    }

    private void changeViewGame() {
        Button btton = (Button) findViewById(R.id.bt_StartGame_show_role);
        btton.setText((flagIsRole) ? getResources().getString(R.string.StartGame_title_1) : getResources().getString(R.string.PlayersInGameList_title_0));
        flagIsRole = !flagIsRole;

        combineVariables(R.id.ll_StartGame_player_1, 0);
        combineVariables(R.id.ll_StartGame_player_2, 1);
        combineVariables(R.id.ll_StartGame_player_3, 2);
        combineVariables(R.id.ll_StartGame_player_4, 3);
        combineVariables(R.id.ll_StartGame_player_5, 4);
        combineVariables(R.id.ll_StartGame_player_6, 5);
        combineVariables(R.id.ll_StartGame_player_7, 6);
        combineVariables(R.id.ll_StartGame_player_8, 7);
        combineVariables(R.id.ll_StartGame_player_9, 8);
        combineVariables(R.id.ll_StartGame_player_10,9);
    }

    private void combineVariables(int id_LinearLayout, int num) {
        LinearLayout ll = (LinearLayout) findViewById(id_LinearLayout);
        TextView textView1, textView2;
        textView1 = (TextView)ll.getChildAt(1);
        textView1.setText(number_places[num]);
        textView2 = (TextView)ll.getChildAt(2);
        if (flagIsRole)
            textView2.setText(nickName.get(num));
        else
            textView2.setText(role.get(num));

        switch (textView2.getText().toString()) {
            case PlayersInGameRole.KEY_ROLE_DON:
                textView2.setBackgroundColor(Color.argb(200, 0, 0, 0));
                textView2.setTextColor(Color.argb(255, 255, 255, 255));
                break;
            case PlayersInGameRole.KEY_ROLE_SHERIF:
                textView2.setBackgroundColor(Color.argb(126, 0, 150, 0));
                textView2.setTextColor(Color.argb(255, 255, 255, 255));
                break;
            case PlayersInGameRole.KEY_ROLE_MAF:
                textView2.setBackgroundColor(Color.argb(126, 66, 66, 66));
                textView2.setTextColor(Color.argb(255, 255, 255, 255));
                break;
            case PlayersInGameRole.KEY_ROLE_SITIZEN:
                textView2.setBackgroundColor(Color.argb(126, 255, 0, 0));
                textView2.setTextColor(Color.argb(255, 255, 255, 255));
                break;
            default:
                textView2.setBackgroundColor(Color.argb(0, 0, 0, 0));
                textView2.setTextColor(Color.argb(255, 0, 0, 0));
                break;
        }
        if (usersIdWhoFinishedGame.get(num) != -1){
            ImageView tv_StartGame_player_10_img = (ImageView)ll.getChildAt(0);
            tv_StartGame_player_10_img.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.death));
            tv_StartGame_player_10_img.setVisibility(View.VISIBLE);
            if (flagIsRole)
                textView2.setVisibility(View.GONE);
            else {
                textView2.setVisibility(View.VISIBLE);
                textView2.setBackgroundColor(Color.argb(50, 100, 255, 255));
                textView2.setTextColor(Color.argb(255, 0, 0, 0));
            }
        }
    }

    public void onTextClickStartGame(View view) {
        switch (view.getId()) {
            case R.id.img_StartGame_1:
                onBackPressed();
                break;
            case R.id.bt_StartGame_show_role:
                changeViewGame();
                break;
            case R.id.bt_StartGame_add_killed_at_night:
                Intent intent_killed = new Intent(this, ChoiceKilled.class);
                intent_killed.putStringArrayListExtra(PlayersInGame.KEY_NICK_NAME_ARRAY_LIST, nickName);
                intent_killed.putIntegerArrayListExtra(PlayersInGame.KEY_USER_ID, userId);
                intent_killed.putExtra(KEY_KILLED_1_NIGHT, idKilled_1_night);
                intent_killed.putExtra(KEY_KILLED_2_NIGHT, idKilled_2_night);
                intent_killed.putExtra(KEY_KILLED_3_NIGHT, idKilled_3_night);
                startActivityForResult(intent_killed, REQUEST_KILLED);
                break;
            case R.id.bt_StartGame_add_course_game:
                dlg_course_game = new MyDialogList();
                dlg_course_game.setTitileDialog(getResources().getString(R.string.item_result_title_1));
                dlg_course_game.setIconDialog(android.R.drawable.ic_dialog_info);
                dlg_course_game.setDataAdapterDialog(getResources().getStringArray(R.array.StartGame_course_game));
                dlg_course_game.show(getFragmentManager(), "dlg_course_game");
                break;
            case R.id.bt_StartGame_add_preferable:
                Intent intent_preferable = new Intent(this, ChoicePlayer.class);
                intent_preferable.putStringArrayListExtra(PlayersInGame.KEY_NICK_NAME_ARRAY_LIST, nickName);
                intent_preferable.putIntegerArrayListExtra(PlayersInGame.KEY_USER_ID, userId);
                intent_preferable.setAction(INTENT_ACTION_PREFERABLE);
                startActivityForResult(intent_preferable, REQUEST_PREFERABLE);
                break;
            case R.id.bt_StartGame_add_win:
                dlg_team_winner = new MyDialogQuestion();
                dlg_team_winner.setTitileDialog(getResources().getString(R.string.PlayersInGameList_title_4));
                dlg_team_winner.setIconDialog(android.R.drawable.ic_dialog_info);
                dlg_team_winner.setMessageDialog(getResources().getString(R.string.dialog_StartGame_title_1));
                dlg_team_winner.setTextBtnPositive(getResources().getString(R.string.dialog_StartGame_title_2));
                dlg_team_winner.setTextBtnNegative(getResources().getString(R.string.dialog_StartGame_title_3));
                dlg_team_winner.show(getFragmentManager(), "dlg_team_winner");
                break;
            case R.id.bt_StartGame_check_all:
                if (checkAllinGame())
                    goToLastActivity();
                break;
            case R.id.bt_StartGame_start_day:
                Intent intent_start_day = new Intent(this, StartDay.class);
                intent_start_day.putStringArrayListExtra(PlayersInGame.KEY_NICK_NAME_ARRAY_LIST, nickName);
                intent_start_day.putIntegerArrayListExtra(PlayersInGame.KEY_USER_ID, userId);
                intent_start_day.putIntegerArrayListExtra(KEY_USERS_ID_WHO_FINISHED, usersIdWhoFinishedGame);
                intent_start_day.putIntegerArrayListExtra(KEY_USERS_NOTIFICATIONS, usersNotificationsInGame);
                startActivityForResult(intent_start_day, REQUEST_START_DAY);
                break;
        }
    }

    private void goToLastActivity(){
        Intent intent_out = new Intent(intent_inputs);
        intent_out.setClass(this, PlayersInGameList.class);
        intent_out.putExtra(KEY_KILLED_1_NIGHT, idKilled_1_night);
        intent_out.putExtra(KEY_KILLED_2_NIGHT, idKilled_2_night);
        intent_out.putExtra(KEY_KILLED_3_NIGHT, idKilled_3_night);
        intent_out.putExtra(KEY_COURSE_GAME, courseGame);
        intent_out.putExtra(KEY_PREFERABLE_PLAYER, idPreferablePlayer);
        intent_out.putExtra(KEY_WINNER_TEAM, isRedWin);
        intent_out.setAction("");
        startActivityForResult(intent_out, REQUEST_CODE_FINISH_NEW_GAME);
    }

    private boolean checkAllinGame(){
        if(idKilled_1_night == -1 || idKilled_2_night == -1 || idKilled_3_night == -1) {
            Toast.makeText(this, getResources().getString(R.string.StartGame_title_2),Toast.LENGTH_SHORT).show();
            return false;
        }
        if(courseGame == -1){
            Toast.makeText(this, getResources().getString(R.string.StartGame_title_3),Toast.LENGTH_SHORT).show();
            return false;
        }
        if(idPreferablePlayer == -1){
            Toast.makeText(this, getResources().getString(R.string.StartGame_title_4),Toast.LENGTH_SHORT).show();
            return false;
        }
        if(isRedWin == -1){
            Toast.makeText(this, getResources().getString(R.string.StartGame_title_5),Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_KILLED:
                if (resultCode == RESULT_OK) {
                    idKilled_1_night = data.getIntExtra(KEY_KILLED_1_NIGHT, -1);
                    if(idKilled_1_night != -1 && idKilled_1_night != 0) {
                        if(positionKilled_1 != -1){
                            usersIdWhoFinishedGame.set(positionKilled_1, -1);
                        }
                        positionKilled_1 = userId.indexOf(idKilled_1_night);
                        usersIdWhoFinishedGame.set(positionKilled_1, idKilled_1_night);
                    }else{
                        if(positionKilled_1 != -1){
                            usersIdWhoFinishedGame.set(positionKilled_1, -1);
                        }
                    }
                    idKilled_2_night = data.getIntExtra(KEY_KILLED_2_NIGHT, -1);
                    if(idKilled_2_night != -1 && idKilled_2_night != 0) {
                        if(positionKilled_2 != -1){
                            usersIdWhoFinishedGame.set(positionKilled_2, -1);
                        }
                        positionKilled_2 = userId.indexOf(idKilled_2_night);
                        usersIdWhoFinishedGame.set(positionKilled_2, idKilled_2_night);
                    }else{
                        if(positionKilled_2 != -1){
                            usersIdWhoFinishedGame.set(positionKilled_2, -1);
                        }
                    }
                    idKilled_3_night = data.getIntExtra(KEY_KILLED_3_NIGHT, -1);
                    if(idKilled_3_night != -1 && idKilled_3_night != 0) {
                        if(positionKilled_3 != -1){
                            usersIdWhoFinishedGame.set(positionKilled_3, -1);
                        }
                        positionKilled_3 = userId.indexOf(idKilled_3_night);
                        usersIdWhoFinishedGame.set(positionKilled_3, idKilled_3_night);
                    }else{
                        if(positionKilled_3 != -1){
                            usersIdWhoFinishedGame.set(positionKilled_3, -1);
                        }
                    }
                    changeViewGame(); changeViewGame();
                }
                break;
            case REQUEST_PREFERABLE:
                if (resultCode == RESULT_OK) {
                    idPreferablePlayer = data.getIntExtra(ChoiceKilled.KEY_CHOICE_PLAYER_ID, -1);
                }
                break;
            case REQUEST_CODE_FINISH_NEW_GAME:
                if (resultCode == RESULT_OK) {
                    Boolean request = data.getBooleanExtra(NewGame.KEY_FOR_FINISH_NEW_GAME, false);
                    if (request){
                        Intent intent= new Intent();
                        intent.putExtra(NewGame.KEY_FOR_FINISH_NEW_GAME, true);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
                break;
            case REQUEST_START_DAY:
                if (resultCode == RESULT_OK) {
                    usersIdWhoFinishedGame = data.getIntegerArrayListExtra(StartGame.KEY_USERS_ID_WHO_FINISHED);
                    usersNotificationsInGame = data.getIntegerArrayListExtra(StartGame.KEY_USERS_NOTIFICATIONS);
                    changeViewGame(); changeViewGame();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        dlg_close_activity = new MyDialogQuestion();
        dlg_close_activity.setTitileDialog(getResources().getString(R.string.dialog_StartGame_title_4));
        dlg_close_activity.setIconDialog(android.R.drawable.ic_dialog_info);
        dlg_close_activity.setMessageDialog(getResources().getString(R.string.dialog_StartGame_title_5));
        dlg_close_activity.setTextBtnNeutral(getResources().getString(R.string.dialog_StartGame_title_6));
        dlg_close_activity.show(getFragmentManager(), "dlg_close_activity");
    }

    @Override
    public void clickPositiveButton(Boolean res) {
        if (res)
            isRedWin = 1;
    }

    @Override
    public void clickNegativeButton(Boolean res) {
        if (res)
            isRedWin = 0;
    }

    @Override
    public void clickNeutralButton(Boolean res) {
        finish();
    }

    @Override
    public void positionInList(int position) {
        if (position == 4)
            courseGame = 0;
        else
            courseGame = position;
    }
}

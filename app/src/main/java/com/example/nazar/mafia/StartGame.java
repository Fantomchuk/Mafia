package com.example.nazar.mafia;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.nazar.mafia.R.id.eT_pas_2;

public class StartGame extends AppCompatActivity {

    private static final int DIALOG_FOR_COURSE_GAME = 1;
    private static final int DIALOG_WINNERS = 2;
    private static final int DIALOG_CLOSE = 3;
    public static final String INTENT_ACTION_PREFERABLE = "intent_action_preferable";

    Intent intent_inputs;
    int idLeaderFromUsersTable;
    ArrayList<String> nickName = new ArrayList<>(10);
    ArrayList<Integer> userId = new ArrayList<>(10);
    ArrayList<String> role = new ArrayList<>(10);
    String nickNameLeader, dateGame;
    int numberGame;
    String[] number_places;
    Boolean flagIsRole = false;
    int idKilled_1_night = -1, idKilled_2_night = -1, idKilled_3_night = -1;
    int courseGame = -1;
    EditText eT_CourseGame_1;
    int idPreferablePlayer = -1;
    int isRedWin = -1;

    public static final String KEY_KILLED_1_NIGHT = "killed_1_night_key";
    public static final String KEY_KILLED_2_NIGHT = "killed_2_night_key";
    public static final String KEY_KILLED_3_NIGHT = "killed_3_night_key";
    public static final String KEY_COURSE_GAME = "course_game_key";
    public static final String KEY_PREFERABLE_PLAYER = "preferable_player_key";
    public static final String KEY_WINNER_TEAM = "winner_team_key";

    private static final int REQUEST_KILLED = 1;
    private static final int REQUEST_PREFERABLE = 2;
    private static final int REQUEST_CODE_FINISH_NEW_GAME = 555;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        intent_inputs = getIntent();
        idLeaderFromUsersTable = intent_inputs.getIntExtra(NewGame.KEY_FOR_LEADER_ID, -1);
        nickNameLeader = intent_inputs.getStringExtra(NewGame.KEY_FOR_NICK_NAME_LEADER);
        numberGame = intent_inputs.getIntExtra(NewGame.KEY_FOR_NUMBER_GAME, 0);
        dateGame = intent_inputs.getStringExtra(NewGame.KEY_FOR_DATE_GAME);
        nickName =  intent_inputs.getStringArrayListExtra(PlayersInGame.KEY_NICK_NAME_ARRAY_LIST);
        userId = intent_inputs.getIntegerArrayListExtra(PlayersInGame.KEY_USER_ID);
        role =  intent_inputs.getStringArrayListExtra(PlayersInGameRole.KEY_ROLE_ARRAY_LIST);

        TextView tv_StartGame_number_game, tv_StartGame_date, tv_StartGame_leader;
        number_places = getResources().getStringArray(R.array.PlayersInGame_title_number);

        tv_StartGame_number_game = (TextView) findViewById(R.id.tv_StartGame_number_game);
        tv_StartGame_number_game.setText(String.valueOf(numberGame));
        tv_StartGame_date = (TextView) findViewById(R.id.tv_StartGame_date);
        tv_StartGame_date.setText(dateGame);
        tv_StartGame_leader = (TextView) findViewById(R.id.tv_StartGame_leader);
        tv_StartGame_leader.setText(nickNameLeader);

        changeViewGame();
    }

    private void changeViewGame() {
        ArrayList<String> temp = new ArrayList<>(10);
        Button btton = (Button) findViewById(R.id.bt_StartGame_show_role);
        temp = (flagIsRole) ? role : nickName;
        btton.setText((flagIsRole) ? getResources().getString(R.string.StartGame_title_1) : getResources().getString(R.string.PlayersInGameList_title_0));
        flagIsRole = !flagIsRole;

        combineVariables(R.id.tv_StartGame_num_player_1, number_places[0], R.id.tv_StartGame_player_1, temp.get(0));
        combineVariables(R.id.tv_StartGame_num_player_2, number_places[1], R.id.tv_StartGame_player_2, temp.get(1));
        combineVariables(R.id.tv_StartGame_num_player_3, number_places[2], R.id.tv_StartGame_player_3, temp.get(2));
        combineVariables(R.id.tv_StartGame_num_player_4, number_places[3], R.id.tv_StartGame_player_4, temp.get(3));
        combineVariables(R.id.tv_StartGame_num_player_5, number_places[4], R.id.tv_StartGame_player_5, temp.get(4));
        combineVariables(R.id.tv_StartGame_num_player_6, number_places[5], R.id.tv_StartGame_player_6, temp.get(5));
        combineVariables(R.id.tv_StartGame_num_player_7, number_places[6], R.id.tv_StartGame_player_7, temp.get(6));
        combineVariables(R.id.tv_StartGame_num_player_8, number_places[7], R.id.tv_StartGame_player_8, temp.get(7));
        combineVariables(R.id.tv_StartGame_num_player_9, number_places[8], R.id.tv_StartGame_player_9, temp.get(8));
        combineVariables(R.id.tv_StartGame_num_player_10, number_places[9], R.id.tv_StartGame_player_10, temp.get(9));
    }

    private void combineVariables(int id1, String number, int id2, String temp) {
        TextView textView1, textView2;
        textView1 = (TextView) findViewById(id1);
        textView1.setText(number);
        textView2 = (TextView) findViewById(id2);
        textView2.setText(temp);

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
                showDialog(DIALOG_FOR_COURSE_GAME);
                break;
            case R.id.bt_StartGame_add_preferable:
                Intent intent_preferable = new Intent(this, ChoicePlayer.class);
                intent_preferable.putStringArrayListExtra(PlayersInGame.KEY_NICK_NAME_ARRAY_LIST, nickName);
                intent_preferable.putIntegerArrayListExtra(PlayersInGame.KEY_USER_ID, userId);
                intent_preferable.setAction(INTENT_ACTION_PREFERABLE);
                startActivityForResult(intent_preferable, REQUEST_PREFERABLE);
                break;
            case R.id.bt_StartGame_add_win:
                showDialog(DIALOG_WINNERS);
                break;
            case R.id.bt_StartGame_check_all:
                if (checkAllinGame())
                    goToLastActivity();
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
                    idKilled_2_night = data.getIntExtra(KEY_KILLED_2_NIGHT, -1);
                    idKilled_3_night = data.getIntExtra(KEY_KILLED_3_NIGHT, -1);
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
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_FOR_COURSE_GAME) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle(R.string.PlayersInGameList_title_2);
            adb.setIcon(android.R.drawable.ic_dialog_info);
            LinearLayout ll_pas = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_course_game, null);
            adb.setView(ll_pas);
            return adb.create();
        }
        if (id == DIALOG_WINNERS){
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle(R.string.PlayersInGameList_title_4);
            adb.setIcon(android.R.drawable.ic_dialog_info);
            adb.setMessage(R.string.dialog_StartGame_title_1);
            adb.setPositiveButton(R.string.dialog_StartGame_title_2, myClickListener_winner);
            adb.setNegativeButton(R.string.dialog_StartGame_title_3, myClickListener_winner);
            adb.setNeutralButton(R.string.NewPlayer_title_13, myClickListener_winner);
            return adb.create();
        }
        if (id == DIALOG_CLOSE){
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle(R.string.dialog_StartGame_title_4);
            adb.setIcon(android.R.drawable.ic_dialog_info);
            adb.setMessage(R.string.dialog_StartGame_title_5);
            adb.setPositiveButton(R.string.dialog_StartGame_title_6, myClickListener_back);
            adb.setNeutralButton(R.string.NewPlayer_title_13, myClickListener_back);
            return adb.create();
        }
        return super.onCreateDialog(id);
    }
    @Override
    protected void onPrepareDialog(int id, final Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        if (id == DIALOG_FOR_COURSE_GAME) {
            // find EditText end Button in DIALOG_FOR_PASSWORD
            eT_CourseGame_1 = (EditText) dialog.findViewById(R.id.eT_CourseGame_1);
            Button bT_CourseGame_1 = (Button) dialog.findViewById(R.id.bT_CourseGame_1);

            bT_CourseGame_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!TextUtils.isEmpty(eT_CourseGame_1.getText().toString())) {
                        int numeric = Integer.valueOf(eT_CourseGame_1.getText().toString());
                        if (numeric >= 0 && numeric < 4)
                            courseGame = numeric;
                    }
                    dialog.cancel();
                }
            });
        }
    }

    DialogInterface.OnClickListener myClickListener_winner = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            switch (i) {
                case Dialog.BUTTON_POSITIVE:
                    isRedWin = 1;
                    break;
                case Dialog.BUTTON_NEGATIVE:
                    isRedWin = 0;
                    break;
                case Dialog.BUTTON_NEUTRAL:
                    break;
            }
        }
    };

    DialogInterface.OnClickListener myClickListener_back = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            switch (i) {
                case Dialog.BUTTON_POSITIVE:
                    finish();
                    break;
                case Dialog.BUTTON_NEUTRAL:
                    break;
            }
        }
    };
    @Override
    public void onBackPressed() {
        showDialog(DIALOG_CLOSE);
    }
}

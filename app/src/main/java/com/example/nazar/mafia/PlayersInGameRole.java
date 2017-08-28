package com.example.nazar.mafia;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class PlayersInGameRole extends AppCompatActivity {


    Intent intent_inputs;
    int idLeaderFromUsersTable;
    ArrayList<String> nickName = new ArrayList<>(10);
    ArrayList<Integer> userId = new ArrayList<>(10);
    String nickNameLeader, dateGame;
    int numberGame;
    TextView tv_role, tv_PlayersInGameRole_info;
    ArrayList<String> role = new ArrayList<>(10);
    Intent intent_choice;

    public static final String INTENT_ACTION_ROLE_DON = "intent_action_role_don";
    public static final String INTENT_ACTION_ROLE_SHERIF = "intent_action_role_sherif";
    public static final String INTENT_ACTION_ROLE_MAF = "intent_action_role_maf";

    public static final String KEY_ROLE_ARRAY_LIST = "role_array_list_key";
    public static final String KEY_IS_CHOICE = "is_choice_key";

    private static final int REQUEST_CODE_DON = 1;
    private static final int REQUEST_CODE_SHERIF = 2;
    private static final int REQUEST_CODE_MAF_1 = 3;
    private static final int REQUEST_CODE_MAF_2 = 4;
    private static final int REQUEST_CODE_FINISH_NEW_GAME = 555;


    public final static String KEY_ROLE_DON = "DON";
    public final static String KEY_ROLE_SHERIF = "SHERIF";
    public final static String KEY_ROLE_MAF = "MAFIA";
    public final static String KEY_ROLE_SITIZEN = "SITIZEN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_in_game_role);

        intent_inputs = getIntent();
        idLeaderFromUsersTable = intent_inputs.getIntExtra(NewGame.KEY_FOR_LEADER_ID, -1);
        nickNameLeader = intent_inputs.getStringExtra(NewGame.KEY_FOR_NICK_NAME_LEADER);
        numberGame = intent_inputs.getIntExtra(NewGame.KEY_FOR_NUMBER_GAME, 0);
        dateGame = intent_inputs.getStringExtra(NewGame.KEY_FOR_DATE_GAME);
        nickName =  intent_inputs.getStringArrayListExtra(PlayersInGame.KEY_NICK_NAME_ARRAY_LIST);
        userId = intent_inputs.getIntegerArrayListExtra(PlayersInGame.KEY_USER_ID);

        tv_PlayersInGameRole_info = (TextView) findViewById(R.id.tv_PlayersInGameRole_info);
        clearRoles();
    }

    public void onTextClickPlayersInGameRole(View view) {
        intent_choice = new Intent(this, ChoiceRole.class);
        intent_choice.putStringArrayListExtra(PlayersInGame.KEY_NICK_NAME_ARRAY_LIST, nickName);
        intent_choice.putStringArrayListExtra(KEY_ROLE_ARRAY_LIST, role);

        switch (view.getId()) {
            case R.id.img_PlayersInGameRole_1:
                onBackPressed();
                break;
            case R.id.tv_PlayersInGameRole_don_3:
                intent_choice.setAction(INTENT_ACTION_ROLE_DON);
                startActivityForResult(intent_choice, REQUEST_CODE_DON);
                break;
            case R.id.tv_PlayersInGameRole_sherif_3:
                intent_choice.setAction(INTENT_ACTION_ROLE_SHERIF);
                startActivityForResult(intent_choice, REQUEST_CODE_SHERIF);
                break;
            case R.id.tv_PlayersInGameRole_1maf_3:
                intent_choice.setAction(INTENT_ACTION_ROLE_MAF);
                startActivityForResult(intent_choice, REQUEST_CODE_MAF_1);
                break;
            case R.id.tv_PlayersInGameRole_2maf_3:
                intent_choice.setAction(INTENT_ACTION_ROLE_MAF);
                startActivityForResult(intent_choice, REQUEST_CODE_MAF_2);
                break;
            case R.id.bt_PlayersInGameRole_cancel_roles:
                clearRoles();
                break;
            case R.id.bt_PlayersInGameRole_next:
                if (checkAllRoles()) {
                    Intent intent_out = new Intent(intent_inputs);
                    intent_out.setClass(this, StartGame.class);
                    intent_out.putStringArrayListExtra(KEY_ROLE_ARRAY_LIST, role);
                    startActivityForResult(intent_out, REQUEST_CODE_FINISH_NEW_GAME);
                }
                break;
        }
    }

    private boolean checkAllRoles() {
        if (!role.contains(KEY_ROLE_DON) || !role.contains(KEY_ROLE_SHERIF) || Collections.frequency(role, KEY_ROLE_MAF) != 2) {
            tv_PlayersInGameRole_info.setText(R.string.PlayersInGameRole_title_9);
            return false;
        }
        for (int i = 0; i < 10; i++)
            if (role.get(i).equals("0"))
                role.set(i, KEY_ROLE_SITIZEN);
        return true;
    }

    private void clearRoles() {
        role.clear();
        for (int i = 0; i < 10; i++)
            role.add("0");
        tv_role = (TextView) findViewById(R.id.tv_PlayersInGameRole_don_3);
        changeTextView(tv_role, false);
        tv_role = (TextView) findViewById(R.id.tv_PlayersInGameRole_sherif_3);
        changeTextView(tv_role, false);
        tv_role = (TextView) findViewById(R.id.tv_PlayersInGameRole_1maf_3);
        changeTextView(tv_role, false);
        tv_role = (TextView) findViewById(R.id.tv_PlayersInGameRole_2maf_3);
        changeTextView(tv_role, false);
        tv_PlayersInGameRole_info.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data.getBooleanExtra(KEY_IS_CHOICE, false)) {
                tv_PlayersInGameRole_info.setText(getResources().getString(R.string.PlayersInGameRole_title_10));
                return;
            } else
                tv_PlayersInGameRole_info.setText("");

            switch (requestCode) {
                case REQUEST_CODE_DON:
                    tv_role = (TextView) findViewById(R.id.tv_PlayersInGameRole_don_3);
                    changeTextView(tv_role, true);
                    break;
                case REQUEST_CODE_SHERIF:
                    tv_role = (TextView) findViewById(R.id.tv_PlayersInGameRole_sherif_3);
                    changeTextView(tv_role, true);
                    break;
                case REQUEST_CODE_MAF_1:
                    tv_role = (TextView) findViewById(R.id.tv_PlayersInGameRole_1maf_3);
                    changeTextView(tv_role, true);
                    break;
                case REQUEST_CODE_MAF_2:
                    tv_role = (TextView) findViewById(R.id.tv_PlayersInGameRole_2maf_3);
                    changeTextView(tv_role, true);
                    break;
            }
            role = data.getStringArrayListExtra(KEY_ROLE_ARRAY_LIST);
            if (requestCode == REQUEST_CODE_FINISH_NEW_GAME) {
                Boolean request = data.getBooleanExtra(NewGame.KEY_FOR_FINISH_NEW_GAME, false);
                if (request){
                    Intent intent= new Intent();
                    intent.putExtra(NewGame.KEY_FOR_FINISH_NEW_GAME, true);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        }
        if (resultCode == RESULT_CANCELED) {
            tv_PlayersInGameRole_info.setText(getResources().getString(R.string.PlayersInGameRole_title_11));
        }
    }

    private void changeTextView(TextView view, Boolean isChoice) {
        if (isChoice) {
            view.setText(getResources().getString(R.string.PlayersInGameRole_title_8));
            view.setTextColor(Color.argb(255, 0, 0, 0));
            view.setClickable(false);
        } else {
            view.setText(getResources().getString(R.string.PlayersInGameRole_title_2));
            view.setTextColor(Color.argb(255, 255, 68, 68));
            view.setClickable(true);
        }
    }
}

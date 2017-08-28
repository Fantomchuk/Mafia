package com.example.nazar.mafia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Voiting extends AppCompatActivity {

    ArrayList<String> nickName = new ArrayList<>(10);
    ArrayList<Integer> userId = new ArrayList<>(10);
    ArrayList<Integer> usersIdWhoFinishedGame = new ArrayList<>(10);
    ArrayList<Integer> usersIdWhoNominated = new ArrayList<>(10);
    ArrayList<Integer> turnUsers = new ArrayList<>(10);
    int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voiting);

        Intent intent_get = getIntent();
        nickName = intent_get.getStringArrayListExtra(PlayersInGame.KEY_NICK_NAME_ARRAY_LIST);
        userId = intent_get.getIntegerArrayListExtra(PlayersInGame.KEY_USER_ID);
        usersIdWhoFinishedGame = intent_get.getIntegerArrayListExtra(StartGame.KEY_USERS_ID_WHO_FINISHED);
        usersIdWhoNominated = intent_get.getIntegerArrayListExtra(StartDay.KEY_USER_ID_WHO_NOMINATED);
        turnUsers = intent_get.getIntegerArrayListExtra(StartDay.KEY_TURN_USER);

        ArrayList<String> temp_nominated = new ArrayList<>();
        for(int k=1; k < 11; k++) {
            for (int i = 0; i < 10; i++) {
                if (usersIdWhoNominated.get(i) != 0 && usersIdWhoNominated.get(i) != -1) {
                    if(k == turnUsers.get(i))
                        temp_nominated.add(turnUsers.get(i) + getResources().getString(R.string.Voiting_title_4) + nickName.get(i));
                }
            }
        }
        ListView lv = (ListView) findViewById(R.id.lv_Voiting);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, temp_nominated);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = turnUsers.indexOf(i+1);
                final String str = nickName.get(index) + getResources().getString(R.string.Voiting_title_5);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((Button)findViewById(R.id.btn_Voiting_user)).setText(str);
                    }
                });
            }
        });
    }


    public void onTextClickVoiting(View view) {
        switch (view.getId()) {
            case R.id.img_Voiting_1:
            case R.id.btn_Voiting_none_a:
                onBackPressed();
                break;
            case R.id.btn_Voiting_user:
                if(index == -1){
                    Toast.makeText(this, getResources().getString(R.string.Voiting_title_6), Toast.LENGTH_SHORT).show();
                    return;
                }
                usersIdWhoFinishedGame.set(index, userId.get(index));
            case R.id.btn_Voiting_none:
                Intent intent = new Intent();
                intent.putExtra(StartGame.KEY_USERS_ID_WHO_FINISHED, usersIdWhoFinishedGame);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}

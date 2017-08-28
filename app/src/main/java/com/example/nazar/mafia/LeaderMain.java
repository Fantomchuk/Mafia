package com.example.nazar.mafia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LeaderMain extends AppCompatActivity {

    public static final String KEY_FOR_LEADER = "leader";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_main);
    }

    public void onTextClickLeaderMain(View view) {
        switch (view.getId()){
            case R.id.tV_LeaderMain_1:
                startActivity(new Intent(this, NewPlayer.class));
                break;
            case R.id.tV_LeaderMain_2:
                startActivity(new Intent(this, NewLeader.class));
                break;
            case R.id.tV_LeaderMain_3:
                Intent intent = new Intent(this, TitleUser.class);
                intent.putExtra(KEY_FOR_LEADER, true);
                startActivity(intent);
                break;
            case R.id.tV_LeaderMain_4:
                startActivity(new Intent(this, NewGame.class));
                break;
            case R.id.tV_LeaderMain_5:
                startActivity(new Intent(this, ShowGame.class));
                break;
            case R.id.tV_LeaderMain_6:
                Intent intentR = new Intent(this, RatingChoise.class);
                intentR.putExtra(KEY_FOR_LEADER, true);
                startActivity(intentR);
                break;
            case R.id.tV_LeaderMain_7:
                startActivity(new Intent(this, NotesLeader.class));
                break;
            case R.id.img_LeaderMain_1:
                onBackPressed();
                break;
        }
    }
}

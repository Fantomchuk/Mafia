package com.example.nazar.mafia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class UserMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
    }

    public void onTextClickUserMain(View view) {
        switch (view.getId()) {
            case R.id.tV_UserMain_1:
                startActivity(new Intent(this, Coordinates.class));
                break;
            case R.id.tV_UserMain_2:
                startActivity(new Intent(this, TitleUser.class));
                break;
            case R.id.tV_UserMain_3:
                startActivity(new Intent(this, ShowGame.class));
                break;
            case R.id.tV_UserMain_4:Intent intentR = new Intent(this, RatingChoise.class);
                startActivity(intentR);
                break;
            case R.id.img_UserMain_1:
                onBackPressed();
                break;
        }
    }
}

package com.example.nazar.mafia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class RatingChoise extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_choise);
    }

    public void onTextClickRatingChoise(View view) {
        switch (view.getId()) {
            case R.id.img_RatingChoise_1:
                onBackPressed();
                break;
            case R.id.tV_RatingChoise_1:
                Intent intent = new Intent(getIntent());
                intent.setClass(this, NewTimePeriod.class);
                startActivity(intent);
                break;
            case R.id.tV_RatingChoise_2:
                startActivity(new Intent(this, PeriodsList.class));
                break;
        }
    }

}

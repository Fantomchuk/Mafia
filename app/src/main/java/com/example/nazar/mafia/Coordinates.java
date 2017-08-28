package com.example.nazar.mafia;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Coordinates extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinates);
    }
    public void onTextClickCoordinates(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.tV_Coordinates_1:
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+48123456789"));
                startActivity(intent);
                break;
            case R.id.tV_Coordinates_2:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:48.925130, 24.707976"));
                startActivity(intent);
                break;
            case R.id.tV_Coordinates_3:
                intent= new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://vk.com/ifmafia"));
                startActivity(intent);
                break;
            case R.id.img_Coordinates_1:
                onBackPressed();
                break;
        }
    }
}

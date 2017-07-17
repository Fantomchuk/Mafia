package com.example.nazar.mafia;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PeriodsList extends AppCompatActivity {

    private static final String ATTRIBUTE_NICK_NAME = "nick_name_atr";
    private static final String ATTRIBUTE_TITLE = "title_atr";
    private static final String ATTRIBUTE_DATE_START = "date_start_atr";
    private static final String ATTRIBUTE_DATE_END = "date_end_atr";

    ListView lv_PeriodsList;
    String[] title, date_start, date_end, nickName;
    int[] min_games;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periods_list);

        DBHelper db = new DBHelper(this);
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        Cursor cursor;
        final String str_sql = "select p.title, p.data_start, p.data_end, p.min_games, u.nickName " +
                "from periods as p " +
                "inner join users as u on u._id = p.winner_id ";

        cursor = sqLiteDatabase.rawQuery(str_sql, null);
        if (cursor.getCount() == 0) {
            TextView tv_PeriodsList_1 = (TextView) findViewById(R.id.tv_PeriodsList_1);
            tv_PeriodsList_1.setText(getString(R.string.PeriodsList_title_1));
            cursor.close();
            db.close();
            return;
        }
        arraysForCursor(cursor);
        cursor.close();
        db.close();

        ArrayList<Map<String, String>> data = new ArrayList<>(title.length);
        Map<String, String> m;
        for (int i = 0; i < title.length; i++) {
            m = new HashMap<>();
            m.put(ATTRIBUTE_NICK_NAME, nickName[i]);
            m.put(ATTRIBUTE_TITLE, title[i]);
            m.put(ATTRIBUTE_DATE_START, date_start[i]);
            m.put(ATTRIBUTE_DATE_END, date_end[i]);
            data.add(m);
        }

        String[] from = new String[] {ATTRIBUTE_NICK_NAME, ATTRIBUTE_TITLE, ATTRIBUTE_DATE_START, ATTRIBUTE_DATE_END};
        int[] to = new int[] {R.id.tv_item_period_5, R.id.tv_item_period_1, R.id.tv_item_period_2, R.id.tv_item_period_4};
        SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.item_period, from, to);
        lv_PeriodsList = (ListView) findViewById(R.id.lv_PeriodsList);
        lv_PeriodsList.setAdapter(sAdapter);
        lv_PeriodsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(PeriodsList.this, RatingPeriod.class);
                intent.putExtra(NewTimePeriod.KEY_FOR_DATA_START, date_start[i]);
                intent.putExtra(NewTimePeriod.KEY_FOR_DATA_END, date_end[i]);
                intent.putExtra(NewTimePeriod.KEY_FOR_MIN_COUNT_GAMES, min_games[i]);
                startActivity(intent);
            }
        });
    }

    private void arraysForCursor(Cursor cursor){

        nickName = new String[cursor.getCount()];
        title = new String[cursor.getCount()];
        date_start = new String[cursor.getCount()];
        date_end = new String[cursor.getCount()];
        min_games = new int[cursor.getCount()];

        if (cursor.moveToFirst()) {
            int nickName_Index = cursor.getColumnIndex(DBHelper.KEY_T2_NICK_NAME);
            int title_Index = cursor.getColumnIndex(DBHelper.KEY_T6_TITLE);
            int date_start_Index = cursor.getColumnIndex(DBHelper.KEY_T6_DATE_START);
            int date_end_Index = cursor.getColumnIndex(DBHelper.KEY_T6_DATE_END);
            int min_games_Index = cursor.getColumnIndex(DBHelper.KEY_T6_MIN_GAMES);
            int i=0;
            do {
                nickName[i] = cursor.getString(nickName_Index);
                title[i] = cursor.getString(title_Index);
                date_start[i] = cursor.getString(date_start_Index);
                date_end[i] = cursor.getString(date_end_Index);
                min_games[i] = cursor.getInt(min_games_Index);
                i++;
            } while (cursor.moveToNext());
        }
    }


    public void onTextClickPeriodsList(View view) {
        switch (view.getId()){
            case R.id.img_PeriodsList_1:
                onBackPressed();
                break;
        }
    }
}

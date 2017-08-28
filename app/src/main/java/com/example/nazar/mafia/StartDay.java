package com.example.nazar.mafia;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StartDay extends AppCompatActivity implements MyDialogList.MyDialogListListener {

    ArrayList<String> nickName = new ArrayList<>(10);
    ArrayList<Integer> userId = new ArrayList<>(10);
    ArrayList<Integer> usersIdWhoFinishedGame = new ArrayList<>(10);
    ArrayList<Integer> usersNotificationsInGame = new ArrayList<>(10);
    ArrayList<Integer> usersIdWhoNominated = new ArrayList<>(10);
    ArrayList<Integer> turnUsers = new ArrayList<>(10);
    String[] number_players;
    ArrayList<Map<String, Object>> data_all_players;
    ArrayList<Map<String, Object>> data_nominated;
    Map<String, Object> m;
    SimpleAdapter sAdapter_all_players, sAdapter_nominated;
    ListView lv_StartDay, lv_StartDay_players;

    final String ATRIBUT_USER_WHO_FINISHED = "user_who_finished";
    final String ATRIBUT_SEAT_NUMBER = "seat_number";
    final String ATRIBUT_NICK_NAME = "nick_name";
    final String ATRIBUT_NOTIFICATIONS_PLAYER = "notifications_player";
    public static final String KEY_USER_ID_WHO_NOMINATED = "user_id_who_nominated_key";
    public static final String KEY_TURN_USER = "turn_user_key";

    private static final int REQUEST_START_VOITING = 1;

    private Handler mHandler = new Handler();
    TextView text_timer, textView_secunds;
    long mStartTime, millis;
    long millis_toEnd = 0L;
    int seconds;
    MediaPlayer mpSoundStart, mpSoundEnd;
    Boolean flagTime = true;

    String[] listNominated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_day);

        Intent intent_get = getIntent();
        nickName = intent_get.getStringArrayListExtra(PlayersInGame.KEY_NICK_NAME_ARRAY_LIST);
        userId = intent_get.getIntegerArrayListExtra(PlayersInGame.KEY_USER_ID);
        usersIdWhoFinishedGame = intent_get.getIntegerArrayListExtra(StartGame.KEY_USERS_ID_WHO_FINISHED);
        usersNotificationsInGame = intent_get.getIntegerArrayListExtra(StartGame.KEY_USERS_NOTIFICATIONS);
        number_players = getResources().getStringArray(R.array.PlayersInGame_title_number);

        for (int i = 0; i < 10; i++) {
            turnUsers.add(0);
            if (usersIdWhoFinishedGame.get(i) != -1)
                usersIdWhoNominated.add(-1);
            else
                usersIdWhoNominated.add(0);
        }
        data_all_players = new ArrayList<>(nickName.size());
        for (int i = 0; i < nickName.size(); i++) {
            m = new HashMap<>();
            m.put(ATRIBUT_USER_WHO_FINISHED, usersIdWhoFinishedGame.get(i));
            m.put(ATRIBUT_SEAT_NUMBER, number_players[i]);
            m.put(ATRIBUT_NICK_NAME, nickName.get(i));
            m.put(ATRIBUT_NOTIFICATIONS_PLAYER, usersNotificationsInGame.get(i));
            data_all_players.add(m);
        }
        String from_all_players[] = new String[]{ATRIBUT_USER_WHO_FINISHED, ATRIBUT_SEAT_NUMBER, ATRIBUT_NICK_NAME, ATRIBUT_NOTIFICATIONS_PLAYER};
        int to_all_players[] = new int[]{R.id.rl_itemUserInDay, R.id.itemUserInDay_1, R.id.itemUserInDay_2, R.id.itemUserInDay_4};
        sAdapter_all_players = new SimpleAdapter(this, data_all_players, R.layout.item_user_in_day, from_all_players, to_all_players);
        sAdapter_all_players.setViewBinder(new MyViewBinderStartDay());
        lv_StartDay = (ListView) findViewById(R.id.lv_StartDay);
        lv_StartDay.setAdapter(sAdapter_all_players);


        data_nominated = new ArrayList<>(nickName.size());
        String from_nominated[] = new String[]{ATRIBUT_SEAT_NUMBER, ATRIBUT_NICK_NAME};
        int to_nominated[] = new int[]{R.id.tv_PlayersInGame, R.id.tv_PlayersInGame_1_1};
        sAdapter_nominated = new SimpleAdapter(this, data_nominated, R.layout.item_players_in_game, from_nominated, to_nominated);
        sAdapter_nominated.setViewBinder(new MyViewBinderStartDay());
        lv_StartDay_players = (ListView) findViewById(R.id.lv_StartDay_players);
        lv_StartDay_players.setAdapter(sAdapter_nominated);

        //--------for timer ------------
        text_timer = (TextView) findViewById(R.id.tv_StartDay_timer);
        mpSoundStart = MediaPlayer.create(this, R.raw.start);
        mpSoundEnd = MediaPlayer.create(this, R.raw.end);

        RelativeLayout mainL = (RelativeLayout) findViewById(R.id.activity_start_day);
        LinearLayout.LayoutParams lparams_newText;
        lparams_newText = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lparams_newText.gravity = Gravity.CENTER;

        textView_secunds = new TextView(this);
        textView_secunds.setTextSize(100);
        textView_secunds.setGravity(lparams_newText.gravity);
        textView_secunds.setTextColor(Color.RED);
        mainL.addView(textView_secunds, lparams_newText);
        //--------for timer end------------

    }


    public void clickBtnVoting(View v) {
        if (data_nominated.size() == 0) {
            Toast.makeText(this, getResources().getString(R.string.StartDay_title_8), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent_voiting = new Intent(this, Voiting.class);
        intent_voiting.putStringArrayListExtra(PlayersInGame.KEY_NICK_NAME_ARRAY_LIST, nickName);
        intent_voiting.putIntegerArrayListExtra(PlayersInGame.KEY_USER_ID, userId);
        intent_voiting.putIntegerArrayListExtra(StartGame.KEY_USERS_ID_WHO_FINISHED, usersIdWhoFinishedGame);
        intent_voiting.putIntegerArrayListExtra(KEY_USER_ID_WHO_NOMINATED, usersIdWhoNominated);
        intent_voiting.putIntegerArrayListExtra(KEY_TURN_USER, turnUsers);
        startActivityForResult(intent_voiting, REQUEST_START_VOITING);
    }

    public void clickBtnNominated(View v) {
        MyDialogList myDialogList = new MyDialogList();
        int pos = 0;
        for (int i = 0; i < 10; i++) {
            if (usersIdWhoNominated.get(i) == 0) {
                pos++;
            }
        }
        listNominated = new String[pos];
        pos = 0;
        for (int i = 0; i < 10; i++) {
            if (usersIdWhoNominated.get(i) == 0) {
                if (i == 9)
                    listNominated[pos] = number_players[i] + " " + nickName.get(i);
                else
                    listNominated[pos] = number_players[i] + "  " + nickName.get(i);
                pos++;
            }
        }
        myDialogList.setDataAdapterDialog(listNominated);
        myDialogList.setTitileDialog(getResources().getString(R.string.StartDay_title_1));
        myDialogList.show(getFragmentManager(), "myDialogList");
    }

    public void clickItemUserInDay_Foul(View v) {
        RelativeLayout parent_row = (RelativeLayout) v.getParent();
        ListView lv = (ListView) parent_row.getParent();
        final int position = lv.getPositionForView(parent_row);
        m = new HashMap<>();
        m = data_all_players.get(position);
        usersNotificationsInGame.set(position, usersNotificationsInGame.get(position) + 1);
        if (usersNotificationsInGame.get(position) == 4) {
            usersIdWhoFinishedGame.set(position, userId.get(position));
            m.put(ATRIBUT_USER_WHO_FINISHED, usersIdWhoFinishedGame.get(position));
        }
        m.put(ATRIBUT_NOTIFICATIONS_PLAYER, usersNotificationsInGame.get(position));
        sAdapter_all_players.notifyDataSetChanged();
    }

    public void onTextClickStartDay(View v) {
        Intent intent = new Intent();
        intent.putIntegerArrayListExtra(StartGame.KEY_USERS_NOTIFICATIONS, usersNotificationsInGame);
        intent.putExtra(StartGame.KEY_USERS_ID_WHO_FINISHED, usersIdWhoFinishedGame);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void positionInList(int position) {
        String str_nickNameNominated = listNominated[position];
        String nickNameNominated = str_nickNameNominated.substring(4, str_nickNameNominated.length());

        usersIdWhoNominated.set(nickName.indexOf(nickNameNominated), userId.get(nickName.indexOf(nickNameNominated)));
        turnUsers.set(nickName.indexOf(nickNameNominated), data_nominated.size()+1);

        m = new HashMap<>();
        m.put(ATRIBUT_SEAT_NUMBER, str_nickNameNominated);
        m.put(ATRIBUT_NICK_NAME, turnUsers.get(nickName.indexOf(nickNameNominated)));
        data_nominated.add(m);

        sAdapter_nominated.notifyDataSetChanged();
    }

    class MyViewBinderStartDay implements SimpleAdapter.ViewBinder {
        @Override
        public boolean setViewValue(View view, Object o, String s) {
            switch (view.getId()) {
                case R.id.rl_itemUserInDay:
                    if ((int) o != -1) {
                        (view.findViewById(R.id.itemUserInDay_0)).setVisibility(View.VISIBLE);
                        (view.findViewById(R.id.itemUserInDay_2)).setVisibility(View.GONE);
                        (view.findViewById(R.id.itemUserInDay_3)).setVisibility(View.GONE);
                        (view.findViewById(R.id.itemUserInDay_4)).setVisibility(View.GONE);
                    } else {
                        (view.findViewById(R.id.itemUserInDay_0)).setVisibility(View.GONE);
                        (view.findViewById(R.id.itemUserInDay_2)).setVisibility(View.VISIBLE);
                        (view.findViewById(R.id.itemUserInDay_3)).setVisibility(View.VISIBLE);
                        (view.findViewById(R.id.itemUserInDay_4)).setVisibility(View.VISIBLE);
                    }
                    return true;
                case R.id.tv_PlayersInGame:
                    ((TextView) view).setText((String) o);
                    ((TextView) view).setTextSize(12);
                    ((TextView) view).setTextColor(Color.RED);
                    return true;
                case R.id.tv_PlayersInGame_1_1:
                    String str = o.toString() + getResources().getString(R.string.Voiting_title_4);
                    ((TextView) view).setText(str);
                    ((TextView) view).setTextSize(12);
                    ((TextView) view).setTextColor(Color.RED);
                    return true;
            }
            return false;
        }
    }

    //--------for timer ------------
    public void clickStartTime(View view) {
        if (!flagTime) {
            mHandler.removeCallbacks(mUpdateTimeTask);
            millis_toEnd = millis + seconds * 1000;
            if (mpSoundStart.isPlaying())
                mpSoundStart.pause();
            textView_secunds.setVisibility(View.GONE);
            flagTime = true;
            ((Button) view).setText(getResources().getString(R.string.StartDay_title_9));
            return;
        }
        if (mStartTime == 0L || millis_toEnd != 0) {
            flagTime = false;
            ((Button) view).setText(getResources().getString(R.string.StartDay_title_10));

            if (millis_toEnd != 0) {
                mStartTime = SystemClock.uptimeMillis() - millis_toEnd;
                millis_toEnd = 0L;
            } else
                mStartTime = SystemClock.uptimeMillis();

            mHandler.removeCallbacks(mUpdateTimeTask);
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    mHandler.post(mUpdateTimeTask);
                }
            });
            t.start();
        }

    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            final long start = mStartTime;
            millis = SystemClock.uptimeMillis() - start;
            seconds = (int) (millis / 1000);
            millis = (millis % 1000);
            if (seconds == 0) {
                int colorFrom = Color.GREEN;
                int colorTo = Color.BLUE;
                ObjectAnimator.ofObject(text_timer, "textColor", new ArgbEvaluator(), colorFrom, colorTo).setDuration(30000).start();
            }
            if (seconds == 30) {
                int colorFrom = Color.BLUE;
                int colorTo = Color.RED;
                ObjectAnimator.ofObject(text_timer, "textColor", new ArgbEvaluator(), colorFrom, colorTo).setDuration(28000).start();
                ObjectAnimator.ofObject(text_timer, "textSize", new ArgbEvaluator(), 20, 45).setDuration(28000).start();
            }
            if (seconds == 50) {
                mpSoundStart.start();
            }
            if (!mpSoundStart.isPlaying() && seconds > 50) {
                mpSoundStart.start();
            }

            if (seconds >= 50) {
                textView_secunds.setText(String.valueOf(60 - seconds));
                if (millis > 500) {
                    textView_secunds.setVisibility(View.GONE);
                } else
                    textView_secunds.setVisibility(View.VISIBLE);
            }
            if (seconds == 60) {
                resetMyTimer(getResources().getString(R.string.StartDay_title_11));
                mpSoundEnd.start();
            } else {
                text_timer.setText(seconds + ":" + millis / 10);
                mHandler.postAtTime(mUpdateTimeTask,
                        start + (seconds * 1000) + millis + 10);
            }
        }
    };

    public void clickEndTime(View view) {
        resetMyTimer(getResources().getString(R.string.StartDay_title_2));
        text_timer.setTextSize(20);
        text_timer.setTextColor(Color.GREEN);
    }

    private void resetMyTimer(String str_reset) {
        textView_secunds.setVisibility(View.GONE);
        ((Button) findViewById(R.id.btn_StartDay_startTime)).setText(getResources().getString(R.string.StartDay_title_3));
        text_timer.setText(str_reset);
        flagTime = true;
        mHandler.removeCallbacks(mUpdateTimeTask);
        mStartTime = 0L;
        millis_toEnd = 0L;
        millis = 0L;
        seconds = 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mpSoundStart != null)
            mpSoundStart.release();
        if (mpSoundEnd != null)
            mpSoundEnd.release();
    }
    //--------for timer end------------

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_START_VOITING:
                if (resultCode == RESULT_OK) {
                    data.putIntegerArrayListExtra(StartGame.KEY_USERS_NOTIFICATIONS, usersNotificationsInGame);
                    setResult(RESULT_OK, data);
                    finish();
                }
                break;
        }
    }
}

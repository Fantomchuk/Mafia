package com.example.nazar.mafia;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;


public class EditPlayer extends AppCompatActivity implements MyDialogQuestion.MyDialogListener, MyDialogDatePicker.MyDialogDatePickerListener  {

    EditText eT_EditPlayer_nickName, eT_EditPlayer_name, eT_EditPlayer_surname;
    Spinner spiner_EditPlayer;
    TextView tv_EditPlayer_info;
    Intent intent_get;
    MyDialogDatePicker dlg_edit_date;


    ArrayAdapter<String> adapter;
    String old_name, old_surname;
    String nickName, name, surName, user_title;
    long userStartPlay;
    int title_pos;
    Boolean isLeader;
    String date;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_player);

        intent_get = getIntent();
        nickName = intent_get.getStringExtra(PlayersList.KEY_FOR_NICK_NAME);
        name = intent_get.getStringExtra(PlayersList.KEY_FOR_NAME);
        surName = intent_get.getStringExtra(PlayersList.KEY_FOR_SURNAME);
        userStartPlay = intent_get.getLongExtra(PlayersList.KEY_FOR_USER_START_PLAY, 0);
        date = PlayerPage.convertLongToData(userStartPlay);
        user_title = intent_get.getStringExtra(TitleUser.KEY_FOR_INTENT);
        isLeader = intent_get.getBooleanExtra(LeaderMain.KEY_FOR_LEADER, false);

        old_name = name;
        old_surname = surName;

        eT_EditPlayer_nickName = (EditText) findViewById(R.id.eT_EditPlayer_nickName);
        eT_EditPlayer_nickName.setText(nickName);
        eT_EditPlayer_name = (EditText) findViewById(R.id.eT_EditPlayer_name);
        eT_EditPlayer_name.setText(name);
        eT_EditPlayer_surname = (EditText) findViewById(R.id.eT_EditPlayer_surname);
        eT_EditPlayer_surname.setText(surName);
        tv_EditPlayer_info = (TextView) findViewById(R.id.tv_EditPlayer_info);


        String[] adapter_date = new String[DBHelper.KEY_TITLE.length - 1];
        for (int i = 0; i < DBHelper.KEY_TITLE.length - 1; i++) {
            adapter_date[i] = DBHelper.KEY_TITLE[i];
            if (user_title.equals(DBHelper.KEY_TITLE[i]))
                title_pos = i;
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, adapter_date);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner_EditPlayer = (Spinner) findViewById(R.id.spiner_EditPlayer);
        spiner_EditPlayer.setAdapter(adapter);

        spiner_EditPlayer.setSelection(title_pos);
        spiner_EditPlayer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                title_pos = i + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void onTextClickEditPlayer(View view) {
        switch (view.getId()) {
            case R.id.bt_EditPlayer_date:
                dlg_edit_date = MyDialogDatePicker.newInstance(date, 1);
                dlg_edit_date.show(getFragmentManager(), "dlg_edit_date");
                break;
            case R.id.bt_EditPlayer_edit_player:
                nickName = eT_EditPlayer_nickName.getText().toString();
                name = eT_EditPlayer_name.getText().toString();
                surName = eT_EditPlayer_surname.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    tv_EditPlayer_info.setText(R.string.NewPlayer_title_8);
                    return;
                }
                if (TextUtils.isEmpty(surName)) {
                    tv_EditPlayer_info.setText(R.string.NewPlayer_title_9);
                    return;
                }
                if (TextUtils.isEmpty(nickName)) {
                    tv_EditPlayer_info.setText(R.string.NewPlayer_title_10);
                    return;
                }
                MyDialogQuestion dlg_edit_player = new MyDialogQuestion();
                dlg_edit_player.setTitileDialog(getResources().getString(R.string.EditPlayer_title_1));
                dlg_edit_player.setMessageDialog(nickName);
                dlg_edit_player.setIconDialog(android.R.drawable.ic_dialog_info);
                dlg_edit_player.setTextBtnPositive(getResources().getString(R.string.NewPlayer_title_12));
                dlg_edit_player.setTextBtnNegative(getResources().getString(R.string.NewPlayer_title_13));
                dlg_edit_player.show(getFragmentManager(), "dlg_edit_player");
                break;
            case R.id.img_EditPlayer_1:
                onBackPressed();
                break;
        }
    }

    @Override
    public void clickPositiveButton(Boolean res) {
        DB db = new DB(EditPlayer.this);
        db.open();
        int id_user = db.getPlayerId(old_name, old_surname);
        db.updateUserInTableUsers(id_user, nickName, name, surName, title_pos, date);
        db.close();
        setResult(RESULT_OK, new Intent());
        finish();
    }

    @Override
    public void clickNegativeButton(Boolean res) {
        tv_EditPlayer_info.setText(R.string.EditPlayer_title_3);
    }

    @Override
    public void clickNeutralButton(Boolean res) {}

    @Override
    public void dateInMyDialogDatePicker(long myDate, int requestCode) {
        date = PlayerPage.convertLongToData(myDate);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("EditPlayer Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


}

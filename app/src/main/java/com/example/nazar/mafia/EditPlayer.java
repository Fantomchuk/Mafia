package com.example.nazar.mafia;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class EditPlayer extends AppCompatActivity {

    private static final int DIALOG_EDIT_DATE = 1;
    private static final int DIALOG_EDIT_PLAYER = 2;

    EditText eT_EditPlayer_nickName, eT_EditPlayer_name, eT_EditPlayer_surname;
    Spinner spiner_EditPlayer;
    TextView tv_EditPlayer_info;
    Intent intent_get;
    Calendar calendar;

    ArrayAdapter<String> adapter;
    String old_name, old_surname;
    String nickName, name, surName, user_title;
    long userStartPlay;
    int title_pos;
    Boolean isLeader;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_player);

        intent_get = getIntent();
        nickName = intent_get.getStringExtra(PlayersList.KEY_FOR_NICK_NAME);
        name = intent_get.getStringExtra(PlayersList.KEY_FOR_NAME);
        surName = intent_get.getStringExtra(PlayersList.KEY_FOR_SURNAME);
        userStartPlay = intent_get.getLongExtra(PlayersList.KEY_FOR_USER_START_PLAY, 0);
        user_title = intent_get.getStringExtra(TitleUser.KEY_FOR_INTENT);
        isLeader = intent_get.getBooleanExtra(LeaderMain.KEY_FOR_LEADER, false);

        old_name = name;
        old_surname = surName;

        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(userStartPlay);

        eT_EditPlayer_nickName = (EditText) findViewById(R.id.eT_EditPlayer_nickName);
        eT_EditPlayer_nickName.setText(nickName);
        eT_EditPlayer_name = (EditText) findViewById(R.id.eT_EditPlayer_name);
        eT_EditPlayer_name.setText(name);
        eT_EditPlayer_surname = (EditText) findViewById(R.id.eT_EditPlayer_surname);
        eT_EditPlayer_surname.setText(surName);
        tv_EditPlayer_info = (TextView) findViewById(R.id.tv_EditPlayer_info);


        String[] adapter_date = new String[DBHelper.KEY_TITLE.length - 1];
        for (int i=0;i < DBHelper.KEY_TITLE.length - 1; i++) {
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
    }

    public void onTextClickEditPlayer(View view) {
        switch (view.getId()) {
            case R.id.bt_EditPlayer_date:
                showDialog(DIALOG_EDIT_DATE);
                break;
            case R.id.bt_EditPlayer_edit_player:
                nickName = eT_EditPlayer_nickName.getText().toString();
                name = eT_EditPlayer_name.getText().toString();
                surName = eT_EditPlayer_surname.getText().toString();

                if (TextUtils.isEmpty(name)) {tv_EditPlayer_info.setText(R.string.NewPlayer_title_8); return;}
                if (TextUtils.isEmpty(surName)) {tv_EditPlayer_info.setText(R.string.NewPlayer_title_9); return;}
                if (TextUtils.isEmpty(nickName)){tv_EditPlayer_info.setText(R.string.NewPlayer_title_10);return;}
                if (date == null){
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int month = calendar.get(Calendar.MONTH) + 1;
                    int year = calendar.get(Calendar.YEAR);
                    date = day + "." + month + "." + year;
                }
                showDialog(DIALOG_EDIT_PLAYER);
                break;
            case R.id.img_EditPlayer_1:
                onBackPressed();
                break;
        }
    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_EDIT_DATE) {
            DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int yearOfDialog, int monthOfDialog, int dayOfDialog) {
                    String day = (dayOfDialog < 10) ? "0" + String.valueOf(dayOfDialog) + "." : String.valueOf(dayOfDialog) + ".";
                    String month = (monthOfDialog + 1 < 10) ? "0" + String.valueOf(monthOfDialog + 1) + "." : String.valueOf(monthOfDialog + 1) + ".";
                    String year = String.valueOf(yearOfDialog);
                    date = day + month + year;
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dpd.setCancelable(true);
            return dpd;
        }
        if (id == DIALOG_EDIT_PLAYER){
            //створюємо конструктор діалогу
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.EditPlayer_title_1);
            builder.setMessage(nickName);
            builder.setIcon(android.R.drawable.ic_dialog_info);
            //додаємо кнопку позитивної відповіді
            builder.setPositiveButton(R.string.NewPlayer_title_12, myClickListener);
            //додаємо кнопку негативну відповідь
            builder.setNegativeButton(R.string.NewPlayer_title_13, myClickListener);
            builder.setCancelable(true);
            return builder.create();
        }
        return super.onCreateDialog(id);
    }
    DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            switch (i) {
                case Dialog.BUTTON_POSITIVE:
                    DB db = new DB(EditPlayer.this);
                    db.open();
                    int id_user = db.getPlayerId(old_name, old_surname);
                    db.updateUserInTableUsers(id_user, nickName, name, surName, title_pos, date);
                    db.close();
                    setResult(RESULT_OK, new Intent());
                    finish();
                    break;
                case Dialog.BUTTON_NEGATIVE:
                    tv_EditPlayer_info.setText(R.string.EditPlayer_title_3);
                    break;
            }
        }
    };
}

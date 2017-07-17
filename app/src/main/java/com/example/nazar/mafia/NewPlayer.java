package com.example.nazar.mafia;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class NewPlayer extends AppCompatActivity {

    private static final int DIALOG_ADD_DATE = 1;
    private static final int DIALOG_ADD_PLAYER = 2;

    EditText eT_NewPlayer_nickName, eT_NewPlayer_name, eT_NewPlayer_surname;
    Button bt_NewPlayer_date;
    Spinner spiner_NewPlayer;
    TextView tv_NewPlayer_info;

    ArrayAdapter<String> adapter;

    String nickName, name, surName, date = null;
    int title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_player);

        eT_NewPlayer_nickName = (EditText) findViewById(R.id.eT_NewPlayer_nickName);
        eT_NewPlayer_name = (EditText) findViewById(R.id.eT_NewPlayer_name);
        eT_NewPlayer_surname = (EditText) findViewById(R.id.eT_NewPlayer_surname);
        tv_NewPlayer_info = (TextView) findViewById(R.id.tv_NewPlayer_info);

        bt_NewPlayer_date = (Button) findViewById(R.id.bt_NewPlayer_date);
        spiner_NewPlayer = (Spinner) findViewById(R.id.spiner_NewPlayer);

        String[] adapter_date = new String[DBHelper.KEY_TITLE.length - 1];
        for (int i=0;i < DBHelper.KEY_TITLE.length - 1; i++)
            adapter_date[i] = DBHelper.KEY_TITLE[i];

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, adapter_date);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner_NewPlayer.setAdapter(adapter);

        spiner_NewPlayer.setSelection(5);
        spiner_NewPlayer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                title = i + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void onTextClickNewPlayer(View view) {
        switch (view.getId()) {
            case R.id.bt_NewPlayer_date:
                showDialog(DIALOG_ADD_DATE);
                break;
            case R.id.bt_NewPlayer_add_player:
                nickName = eT_NewPlayer_nickName.getText().toString();
                name = eT_NewPlayer_name.getText().toString();
                surName = eT_NewPlayer_surname.getText().toString();

                if (TextUtils.isEmpty(name)) {tv_NewPlayer_info.setText(R.string.NewPlayer_title_8); return;}
                if (TextUtils.isEmpty(surName)) {tv_NewPlayer_info.setText(R.string.NewPlayer_title_9); return;}
                if (TextUtils.isEmpty(nickName)){tv_NewPlayer_info.setText(R.string.NewPlayer_title_10);return;}

                DB db = new DB(NewPlayer.this); db.open();
                if (db.getPlayerId(name, surName) > 0){
                    String nick_player = db.getPlayerNickName(name, surName);
                    tv_NewPlayer_info.setText(getResources().getString(R.string.NewPlayer_title_6) + surName + " " + name + getResources().getString(R.string.NewPlayer_title_7) + nick_player);
                    db.close();
                    return;
                }db.close();

                if (date == null){tv_NewPlayer_info.setText(R.string.NewPlayer_title_11);return;}
                showDialog(DIALOG_ADD_PLAYER);
                break;
            case R.id.img_NewPlayer_1:
                onBackPressed();
                break;
        }

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ADD_DATE) {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int yearOfDialog, int monthOfDialog, int dayOfDialog) {
                    String day = (dayOfDialog < 10) ? "0" + String.valueOf(dayOfDialog) + "." : String.valueOf(dayOfDialog) + ".";
                    String month = (monthOfDialog + 1 < 10) ? "0" + String.valueOf(monthOfDialog + 1) + "." : String.valueOf(monthOfDialog + 1) + ".";
                    String year = String.valueOf(yearOfDialog);
                    date = day + month + year;
                    bt_NewPlayer_date.setText(R.string.NewPlayer_title_12);
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dpd.setCancelable(true);
            return dpd;
        }
        if (id == DIALOG_ADD_PLAYER){
            //створюємо конструктор діалогу
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.NewPlayer_title_1);
            builder.setMessage(nickName);
            builder.setIcon(android.R.drawable.ic_dialog_info);
            //додаємо кнопку позитивної відповіді
            builder.setPositiveButton(R.string.NewPlayer_title_5, myClickListener);
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
                    DB db = new DB(NewPlayer.this);
                    db.open();
                    db.addPlayerToDB(nickName,name,surName,title,date);
                    db.close();
                    finish();
                    break;
                case Dialog.BUTTON_NEGATIVE:
                    tv_NewPlayer_info.setText(R.string.NewPlayer_title_14);
                    break;
            }
        }
    };

}

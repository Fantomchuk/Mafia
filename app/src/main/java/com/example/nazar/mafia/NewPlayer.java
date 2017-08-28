package com.example.nazar.mafia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class NewPlayer extends AppCompatActivity implements MyDialogQuestion.MyDialogListener, MyDialogDatePicker.MyDialogDatePickerListener {

    EditText eT_NewPlayer_nickName, eT_NewPlayer_name, eT_NewPlayer_surname;
    Button bt_NewPlayer_date;
    Spinner spiner_NewPlayer;
    TextView tv_NewPlayer_info;
    MyDialogDatePicker dlg_add_date;

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
        System.arraycopy(DBHelper.KEY_TITLE, 0, adapter_date, 0, DBHelper.KEY_TITLE.length - 1);

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
                dlg_add_date = MyDialogDatePicker.newInstance(date, 1);
                dlg_add_date.show(getFragmentManager(), "dlg_add_dae");
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
                MyDialogQuestion dlg_add_player = new MyDialogQuestion();
                dlg_add_player.setTitileDialog(getResources().getString(R.string.NewPlayer_title_1));
                dlg_add_player.setMessageDialog(nickName);
                dlg_add_player.setIconDialog(android.R.drawable.ic_dialog_info);
                dlg_add_player.setTextBtnPositive(getResources().getString(R.string.NewPlayer_title_5));
                dlg_add_player.setTextBtnNegative(getResources().getString(R.string.NewPlayer_title_13));
                dlg_add_player.show(getFragmentManager(), "dlg_add_player");
                break;
            case R.id.img_NewPlayer_1:
                onBackPressed();
                break;
        }

    }

    @Override
    public void clickPositiveButton(Boolean res) {
        DB db = new DB(NewPlayer.this);
        db.open();
        db.addPlayerToDB(nickName,name,surName,title,date);
        db.close();
        finish();
    }

    @Override
    public void clickNegativeButton(Boolean res) {
        tv_NewPlayer_info.setText(R.string.NewPlayer_title_14);
    }

    @Override
    public void clickNeutralButton(Boolean res) {}

    @Override
    public void dateInMyDialogDatePicker(long myDate, int requestCode) {
        date = PlayerPage.convertLongToData(myDate);
        bt_NewPlayer_date.setText(R.string.NewPlayer_title_12);
    }
}

package com.example.nazar.mafia;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NewLeader extends AppCompatActivity {

    private static final int DIALOG_ADD_LEADER = 1;
    EditText eT_NewLeader_name,eT_NewLeader_surname,eT_NewLeader_password_1,eT_NewLeader_password_2;
    TextView tv_NewLeader_info;

    String name,surName,pass1,pass2,nickName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_leader);
        eT_NewLeader_name = (EditText) findViewById(R.id.eT_NewLeader_name);
        eT_NewLeader_surname = (EditText) findViewById(R.id.eT_NewLeader_surname);
        eT_NewLeader_password_1 = (EditText) findViewById(R.id.eT_NewLeader_password_1);
        eT_NewLeader_password_2 = (EditText) findViewById(R.id.eT_NewLeader_password_2);

        tv_NewLeader_info = (TextView) findViewById(R.id.tv_NewLeader_info);
    }

    public void onTextClickNewLeader(View view) {
        switch (view.getId()){
            case R.id.img_NewLeader_1:
                onBackPressed();
                break;
            case R.id.bt_NewLeader_add_leader:
                name = eT_NewLeader_name.getText().toString();
                surName = eT_NewLeader_surname.getText().toString();
                pass1 = eT_NewLeader_password_1.getText().toString();
                pass2 = eT_NewLeader_password_2.getText().toString();
                if (TextUtils.isEmpty(name)) {tv_NewLeader_info.setText(R.string.NewPlayer_title_8); return;}
                if (TextUtils.isEmpty(surName)) {tv_NewLeader_info.setText(R.string.NewPlayer_title_9); return;}
                if (TextUtils.isEmpty(pass1)) {tv_NewLeader_info.setText(R.string.NewLeader_title_4); return;}
                if (TextUtils.isEmpty(pass2)) {tv_NewLeader_info.setText(R.string.NewLeader_title_5); return;}
                if (!pass1.equals(pass2)){
                    tv_NewLeader_info.setText(R.string.NewLeader_title_6);
                    eT_NewLeader_password_1.setText("");
                    eT_NewLeader_password_2.setText("");
                    return;}
                DB db = new DB(this); db.open();
                if (db.getPlayerId(name, surName) == -1){
                    db.close();
                    tv_NewLeader_info.setText(R.string.NewLeader_title_7);
                    return;
                }else{
                    nickName = db.getPlayerNickName(name, surName);
                    if (db.isPresentLeaderInDB(db.getPlayerId(name, surName))){
                        tv_NewLeader_info.setText(getResources().getString(R.string.NewLeader_title_9) + nickName + getResources().getString(R.string.NewLeader_title_10));
                        return;
                    }
                }db.close();
                showDialog(DIALOG_ADD_LEADER);
                break;
        }
    }
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ADD_LEADER){
            //створюємо конструктор діалогу
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.NewLeader_title_1);
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
                    DB db = new DB(NewLeader.this);
                    db.open();
                    db.addLeaderToDB(db.getPlayerId(name, surName), pass1);
                    db.close();
                    finish();
                    break;
                case Dialog.BUTTON_NEGATIVE:
                    tv_NewLeader_info.setText(R.string.NewLeader_title_8);
                    break;
            }
        }
    };
}

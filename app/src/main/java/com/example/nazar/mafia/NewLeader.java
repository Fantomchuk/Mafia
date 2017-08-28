package com.example.nazar.mafia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NewLeader extends AppCompatActivity implements MyDialogQuestion.MyDialogListener {

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
                MyDialogQuestion dlg_add_leader = new MyDialogQuestion();
                dlg_add_leader.setTitileDialog(getResources().getString(R.string.NewLeader_title_1));
                dlg_add_leader.setMessageDialog(nickName);
                dlg_add_leader.setIconDialog(android.R.drawable.ic_dialog_info);
                dlg_add_leader.setTextBtnPositive(getResources().getString(R.string.NewPlayer_title_5));
                dlg_add_leader.setTextBtnNegative(getResources().getString(R.string.NewPlayer_title_13));
                dlg_add_leader.show(getFragmentManager(), "dlg_add_leader");
                break;
        }
    }

    @Override
    public void clickPositiveButton(Boolean res) {
        DB db = new DB(NewLeader.this);
        db.open();
        db.addLeaderToDB(db.getPlayerId(name, surName), pass1);
        db.close();
        finish();
    }

    @Override
    public void clickNegativeButton(Boolean res) {
        tv_NewLeader_info.setText(R.string.NewLeader_title_8);
    }

    @Override
    public void clickNeutralButton(Boolean res) {}
}

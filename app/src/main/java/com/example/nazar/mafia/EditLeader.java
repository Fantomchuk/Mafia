package com.example.nazar.mafia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditLeader extends AppCompatActivity implements MyDialogQuestion.MyDialogListener{

    EditText eT_EditLeader_password_1, eT_EditLeader_password_2, eT_EditLeader_password_Old;
    TextView tv_EditLeader_info, tv_EditLeader_nickName;
    String pass1, pass2, oldPass;
    String nickName, name, surName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_leader);
        eT_EditLeader_password_1 = (EditText) findViewById(R.id.eT_EditLeader_password_1);
        eT_EditLeader_password_2 = (EditText) findViewById(R.id.eT_EditLeader_password_2);
        eT_EditLeader_password_Old = (EditText) findViewById(R.id.eT_EditLeader_password_Old);
        tv_EditLeader_info = (TextView) findViewById(R.id.tv_EditLeader_info);
        tv_EditLeader_nickName = (TextView) findViewById(R.id.tv_EditLeader_nickName);

        Intent intent_get = getIntent();
        nickName = intent_get.getStringExtra(PlayersList.KEY_FOR_NICK_NAME);
        name = intent_get.getStringExtra(PlayersList.KEY_FOR_NAME);
        surName = intent_get.getStringExtra(PlayersList.KEY_FOR_SURNAME);

        tv_EditLeader_nickName.setText(nickName);
    }

    public void onTextClickEditLeader(View view) {
        switch (view.getId()) {
            case R.id.img_EditLeader_1:
                onBackPressed();
                break;
            case R.id.bt_EditLeader_leader:
                pass1 = eT_EditLeader_password_1.getText().toString();
                pass2 = eT_EditLeader_password_2.getText().toString();
                oldPass = eT_EditLeader_password_Old.getText().toString();

                if (TextUtils.isEmpty(pass1)) {
                    tv_EditLeader_info.setText(R.string.NewLeader_title_4);
                    return;
                }
                if (TextUtils.isEmpty(pass2)) {
                    tv_EditLeader_info.setText(R.string.NewLeader_title_5);
                    return;
                }
                if (!pass1.equals(pass2)) {
                    tv_EditLeader_info.setText(R.string.NewLeader_title_6);
                    eT_EditLeader_password_1.setText("");
                    eT_EditLeader_password_2.setText("");
                    return;
                }
                DB db = new DB(this);
                db.open();
                String encrypted_password = db.getPasswordForLeader(nickName);
                db.close();
                String encrypted_oldPass = UserChoice.md5Custom(oldPass);
                if (!encrypted_password.equals(encrypted_oldPass)) {
                    tv_EditLeader_info.setText(R.string.EditLeader_title_4);
                    eT_EditLeader_password_Old.setText("");
                    eT_EditLeader_password_1.setText("");
                    eT_EditLeader_password_2.setText("");
                    return;
                }
                MyDialogQuestion myDialogQuestion = new MyDialogQuestion();
                myDialogQuestion.setTitileDialog(getResources().getString(R.string.EditPlayer_title_1));
                myDialogQuestion.setMessageDialog(nickName);
                myDialogQuestion.setIconDialog(android.R.drawable.ic_dialog_info);
                myDialogQuestion.setTextBtnPositive(getResources().getString(R.string.NewPlayer_title_12));
                myDialogQuestion.setTextBtnNegative(getResources().getString(R.string.NewPlayer_title_13));
                myDialogQuestion.show(getFragmentManager(), "myDialogQuestion");
                break;
        }
    }

    @Override
    public void clickPositiveButton(Boolean res) {
        DB db = new DB(EditLeader.this);
        db.open();
        db.updateLeaderInTablePasswords(db.getPlayerId(name, surName), pass1);
        db.close();
        setResult(RESULT_OK, new Intent());
        finish();
    }

    @Override
    public void clickNegativeButton(Boolean res) {
        tv_EditLeader_info.setText(R.string.EditPlayer_title_3);
    }

    @Override
    public void clickNeutralButton(Boolean res) {}
}

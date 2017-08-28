package com.example.nazar.mafia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserChoice extends AppCompatActivity implements MyDialogPassword.MyDialogPasswordListener {

    String nickName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_choice);
    }

    public void onTextClickUserChoice(View view) {
        switch (view.getId()) {
            case R.id.tV_UserChoice_2:
                startActivity(new Intent(this, UserMain.class));
                break;
            case R.id.tV_UserChoice_3:
                MyDialogPassword dlg_pass = MyDialogPassword.newInstance(nickName);
                dlg_pass.show(getFragmentManager(), "dlg_pass");
                break;
        }
    }


    //функція провірки введених даних для логування
    private boolean checkedCorrectPassword(String nickName, String password) {
        //deleteDatabase(DBHelper.DATABASE_NAME);         //////////////////////////видалити в фінальній версії, для того щоб кожного разу при тестуванн
        DB db = new DB(this);
        db.open();
        String encrypted_password = db.getPasswordForLeader(nickName);
        db.close();
        if(nickName.equals("")) {
            Toast.makeText(this, R.string.UserChoice_title_6, Toast.LENGTH_SHORT).show();
            return false;
        }else if (encrypted_password.equals("")) {
            Toast.makeText(this, R.string.UserChoice_title_4, Toast.LENGTH_SHORT).show();
            return false;
        } else if (encrypted_password.equals(md5Custom(password)))
            return true;
        else
            Toast.makeText(UserChoice.this, R.string.UserChoice_title_5, Toast.LENGTH_SHORT).show();
        return false;
    }

    //функція шифрування пароля
    public static String md5Custom(String st) {
        MessageDigest messageDigest;
        byte[] digest = new byte[0];
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);
        while (md5Hex.length() < 32) {
            md5Hex = "0" + md5Hex;
        }
        return md5Hex;
    }

    @Override
    public void dataInMyDialogPassword(String nickNameDialog, String passwordDialod) {
        nickName = nickNameDialog;
        if (checkedCorrectPassword(nickNameDialog, passwordDialod)) {
            startActivity(new Intent(UserChoice.this, LeaderMain.class));
        }
    }
}

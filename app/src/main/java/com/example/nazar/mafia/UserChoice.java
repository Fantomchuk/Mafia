package com.example.nazar.mafia;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserChoice extends AppCompatActivity {


    private static final int DIALOG_FOR_PASSWORD = 1;

    private EditText eT_pas_1, eT_pas_2;
    private Button bT_pas_1;

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
                showDialog(DIALOG_FOR_PASSWORD);
                break;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_FOR_PASSWORD) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle(R.string.dialog_pass_title_5);
            LinearLayout ll_pas = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_password, null);
            adb.setView(ll_pas);
            return adb.create();
        }
        return super.onCreateDialog(id);
    }

    @Override
    protected void onPrepareDialog(int id, final Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        if (id == DIALOG_FOR_PASSWORD) {
            // find EditText end Button in DIALOG_FOR_PASSWORD
            eT_pas_1 = (EditText) dialog.findViewById(R.id.eT_pas_1);
            eT_pas_2 = (EditText) dialog.findViewById(R.id.eT_pas_2);
            bT_pas_1 = (Button) dialog.findViewById(R.id.bT_pas_1);

            bT_pas_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nickName, password;
                    nickName = eT_pas_1.getText().toString();
                    password = eT_pas_2.getText().toString();
                    //провіряємо чи існує
                    //розкоментувати нижче, щоб працювало
                    //if (checkedCorrectPassword(nickName, password)) {
                        startActivity(new Intent(UserChoice.this, LeaderMain.class));
                    //}
                    eT_pas_2.setText("");
                    dialog.cancel();
                }
            });
        }
    }

    //функція провірки введених даних для логування
    private boolean checkedCorrectPassword(String nickName, String password) {

        //deleteDatabase(DBHelper.DATABASE_NAME);         //////////////////////////видалити в фінальній версії, для того щоб кожного разу при тестуванн
        DB db = new DB(this);
        db.open();
        String encrypted_password = db.getPasswordForLeader(nickName);
        db.close();
        if (encrypted_password.equals("")) {
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
        MessageDigest messageDigest = null;
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

}

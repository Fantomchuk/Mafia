package com.example.nazar.mafia;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NotesLeader extends AppCompatActivity implements MyDialogQuestion.MyDialogListener{

    LinearLayout ll_NotesLeader_view_1,ll_NotesLeader_view_2,ll_NotesLeader_view_3,ll_NotesLeader_view_4,ll_NotesLeader_view_5;
    ListView lv_NotesLeader;
    ArrayList<String> titles,notes;
    String title,note, updateNote;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_leader);
        ll_NotesLeader_view_1 = (LinearLayout) findViewById(R.id.ll_NotesLeader_view_1);
        ll_NotesLeader_view_2 = (LinearLayout) findViewById(R.id.ll_NotesLeader_view_2);
        ll_NotesLeader_view_3 = (LinearLayout) findViewById(R.id.ll_NotesLeader_view_3);
        ll_NotesLeader_view_4 = (LinearLayout) findViewById(R.id.ll_NotesLeader_view_4);
        ll_NotesLeader_view_5 = (LinearLayout) findViewById(R.id.ll_NotesLeader_view_5);
        lv_NotesLeader =(ListView) findViewById(R.id.lv_NotesLeader);
    }

    public void onTextClickNotesLeader_view_1(View view) {
        switch (view.getId()){
            case R.id.img_NotesLeader_1:
                onBackPressed();
                break;
            case R.id.bt_NotesLeader_add_notes:
                ll_NotesLeader_view_1.setVisibility(View.GONE);
                ll_NotesLeader_view_2.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_NotesLeader_show_notes:
                ll_NotesLeader_view_1.setVisibility(View.GONE);
                addDataToListView();
                ll_NotesLeader_view_3.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void onTextClickNotesLeader_view_2(View view) {
        switch (view.getId()){
            case R.id.bt_NotesLeader_cancel_notes:
                ll_NotesLeader_view_1.setVisibility(View.VISIBLE);
                ll_NotesLeader_view_2.setVisibility(View.GONE);
                break;
            case R.id.bt_NotesLeader_save_notes:
                if (addNotes()) {
                    ll_NotesLeader_view_1.setVisibility(View.VISIBLE);
                    ll_NotesLeader_view_2.setVisibility(View.GONE);
                }
                break;
        }
    }

    public void onTextClickNotesLeader_view_3(View view) {
        switch (view.getId()){
            case R.id.bt_NotesLeader_cancel_list:
                ll_NotesLeader_view_1.setVisibility(View.VISIBLE);
                ll_NotesLeader_view_3.setVisibility(View.GONE);
                break;
        }
    }

    public void onTextClickNotesLeader_view_4(View view) {
        switch (view.getId()){
            case R.id.bt_NotesLeader_delete_note:
                MyDialogQuestion dlg_delete_note = new MyDialogQuestion();
                dlg_delete_note.setTitileDialog(getResources().getString(R.string.NotesLeader_title_12));
                dlg_delete_note.setIconDialog(android.R.drawable.ic_dialog_info);
                dlg_delete_note.setTextBtnNegative(getResources().getString(R.string.NotesLeader_title_8));
                dlg_delete_note.setTextBtnNeutral(getResources().getString(R.string.NotesLeader_title_7));
                dlg_delete_note.show(getFragmentManager(), "dlg_delete_note");
                ll_NotesLeader_view_4.setVisibility(View.GONE);
                ll_NotesLeader_view_3.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_NotesLeader_edit_note:
                ll_NotesLeader_view_4.setVisibility(View.GONE);
                TextView tv_NotesLeader_edit = (TextView) findViewById(R.id.tv_NotesLeader_edit);
                tv_NotesLeader_edit.setText(title);
                EditText eT_NotesLeader_notes_edit = (EditText) findViewById(R.id.eT_NotesLeader_notes_edit);
                eT_NotesLeader_notes_edit.setText(note);
                ll_NotesLeader_view_5.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_NotesLeader_cancel_note:
                ll_NotesLeader_view_4.setVisibility(View.GONE);
                ll_NotesLeader_view_3.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void onTextClickNotesLeader_view_5(View view) {
        switch (view.getId()){
            case R.id.bt_NotesLeader_cancel_notes_edit:
                ll_NotesLeader_view_5.setVisibility(View.GONE);
                ll_NotesLeader_view_3.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_NotesLeader_save_notes_edit:
                EditText eT_NotesLeader_notes_edit = (EditText) findViewById(R.id.eT_NotesLeader_notes_edit);
                updateNote = eT_NotesLeader_notes_edit.getText().toString();
                if (TextUtils.isEmpty(updateNote)){
                    Toast.makeText(this, R.string.NotesLeader_title_10,Toast.LENGTH_SHORT).show();
                    return;
                }
                MyDialogQuestion dlg_update_note = new MyDialogQuestion();
                dlg_update_note.setTitileDialog(getResources().getString(R.string.NotesLeader_title_13));
                dlg_update_note.setIconDialog(android.R.drawable.ic_dialog_info);
                dlg_update_note.setTextBtnPositive(getResources().getString(R.string.NotesLeader_title_6));
                dlg_update_note.setTextBtnNeutral(getResources().getString(R.string.NotesLeader_title_7));
                dlg_update_note.show(getFragmentManager(), "dlg_update_note");
                ll_NotesLeader_view_5.setVisibility(View.GONE);
                ll_NotesLeader_view_3.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void addDataToListView(){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String str_sql;
        str_sql = "select notes.title, notes.note from notes ";
        Cursor cursor = database.rawQuery(str_sql, null);

        titles = new ArrayList<>(cursor.getCount());
        notes = new ArrayList<>(cursor.getCount());

        if (cursor.moveToFirst()) {
            int titlesIndex = cursor.getColumnIndex(DBHelper.KEY_T5_TITLE);
            int notesIndex = cursor.getColumnIndex(DBHelper.KEY_T5_NOTE);
            do {
                titles.add(cursor.getString(titlesIndex));
                notes.add(cursor.getString(notesIndex));
            } while (cursor.moveToNext());
        }
        cursor.close();
        dbHelper.close();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles);
        lv_NotesLeader.setAdapter(adapter);
        lv_NotesLeader.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                title = titles.get(i);
                note = notes.get(i);
                TextView tv_NotesLeader__view_3 = (TextView) findViewById(R.id.tv_NotesLeader__view_3);
                TextView tv_NotesLeader__view_4 = (TextView) findViewById(R.id.tv_NotesLeader__view_4);
                tv_NotesLeader__view_3.setText(title);
                tv_NotesLeader__view_4.setText(note);
                ll_NotesLeader_view_3.setVisibility(View.GONE);
                ll_NotesLeader_view_4.setVisibility(View.VISIBLE);
            }
        });
    }

    private boolean addNotes(){
        EditText eT_NotesLeader_title,eT_NotesLeader_notes;
        eT_NotesLeader_title = (EditText) findViewById(R.id.eT_NotesLeader_title);
        eT_NotesLeader_notes = (EditText) findViewById(R.id.eT_NotesLeader_notes);
        String title_et = eT_NotesLeader_title.getText().toString();
        String note_et = eT_NotesLeader_notes.getText().toString();

        if(TextUtils.isEmpty(title_et)){
            Toast.makeText(this, R.string.NotesLeader_title_9,Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(note_et)){
            Toast.makeText(this, R.string.NotesLeader_title_10,Toast.LENGTH_SHORT).show();
            return false;
        }
        DB db = new DB(this);
        db.open();
        db.addNotesToDB(title_et, note_et);
        db.close();
        eT_NotesLeader_title.setText("");
        eT_NotesLeader_notes.setText("");
        return true;
    }

    @Override
    public void clickPositiveButton(Boolean res) {
        DB db = new DB(NotesLeader.this);
        db.open();
        db.updateNoteFromDB(title, updateNote);
        db.close();
        notes.set(titles.indexOf(title), updateNote);
    }

    @Override
    public void clickNegativeButton(Boolean res) {
        DB db = new DB(NotesLeader.this);
        db.open();
        db.deleteNoteFromDB(title);
        db.close();
        notes.remove(titles.indexOf(title));
        titles.remove(title);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void clickNeutralButton(Boolean res) {

    }
}

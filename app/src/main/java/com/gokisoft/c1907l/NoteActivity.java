package com.gokisoft.c1907l;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.gokisoft.c1907l.adapter.NoteAdapter;
import com.gokisoft.c1907l.db.DBHelper2;
import com.gokisoft.c1907l.models.Note;
import com.gokisoft.c1907l.modify.NoteModify;

import java.util.Date;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class NoteActivity extends AppCompatActivity {
    ListView listView;
    Cursor currentCursor;
    NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        DBHelper2.getInstance(this);

        listView = findViewById(R.id.an_listview);

        currentCursor = NoteModify.getNoteCursor();

        noteAdapter = new NoteAdapter(this, currentCursor);

        listView.setAdapter(noteAdapter);

        registerForContextMenu(listView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.note_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.note_menu_context, menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();

        int position = info.position;
        currentCursor.moveToPosition(position);

        Note note = NoteModify.find(currentCursor);

        switch (item.getItemId()) {
            case R.id.menu_edit_note:
                showNoteDialog(note);
                break;
            case R.id.menu_remove_note:
                NoteModify.delete(note.get_id());

                updateAdapter();
                break;
        }

        return super.onContextItemSelected(item);
    }

    void updateAdapter() {
        currentCursor = NoteModify.getNoteCursor();

        noteAdapter.changeCursor(currentCursor);
        noteAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_note:
                showNoteDialog(null);
                break;
            case R.id.menu_exit:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showNoteDialog(final Note note) {
        //B1: Convert layout xml -> view
        View v = getLayoutInflater().inflate(R.layout.dialog_note, null);

        final TextView headerView = v.findViewById(R.id.dn_header);
        final EditText noidungTxt = v.findViewById(R.id.dn_noidung);
        final CheckBox quantrongCb = v.findViewById(R.id.dn_quantrong);
        final Button cancelBtn = v.findViewById(R.id.dn_cancel_btn);
        final Button commitBtn = v.findViewById(R.id.dn_commit_btn);

        if(note != null) {
            headerView.setBackgroundResource(R.color.colorEditNote);
            noidungTxt.setText(note.getNoidung());
            quantrongCb.setChecked(note.isQuantrong());
        } else {
            headerView.setBackgroundResource(R.color.colorNewNote);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(v);
        final Dialog dialog = builder.show();

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noidung = noidungTxt.getText().toString();
                boolean quantrong = quantrongCb.isChecked();

                if(note == null) {
                    Note newNote = new Note(noidung, quantrong, new Date());
                    NoteModify.insert(newNote);
                } else {
                    note.setNoidung(noidung);
                    note.setQuantrong(quantrong);

                    NoteModify.update(note);
                }

                updateAdapter();
                dialog.dismiss();
            }
        });
    }
}

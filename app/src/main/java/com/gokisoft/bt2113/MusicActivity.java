package com.gokisoft.bt2113;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.gokisoft.bt2113.adapter.MusicAdapter;
import com.gokisoft.bt2113.db.DBHelper;
import com.gokisoft.bt2113.model.Music;
import com.gokisoft.bt2113.model.MusicModify;
import com.gokisoft.c1907l.R;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MusicActivity extends AppCompatActivity {
    ListView listView;
    Cursor cursor;
    MusicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        DBHelper.getInstance(this);

        //Fake data vao database - only one.
        /**Music music;
        music = new Music("Bai hat 1", "Mo ta bai hat 1");
        MusicModify.insert(music);
        music = new Music("Bai hat 2", "Mo ta bai hat 2");
        MusicModify.insert(music);
        music = new Music("Bai hat 3", "Mo ta bai hat 3");
        MusicModify.insert(music);*/

        listView = findViewById(R.id.am_listview);
        cursor = MusicModify.getMusicCusor();
        adapter = new MusicAdapter(this, cursor);

        listView.setAdapter(adapter);

        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
                Music music = MusicModify.getMusic(cursor);

                Intent intent = new Intent(MusicActivity.this, MusicDetailActivity.class);
                intent.putExtra("resId", music.getResId());
                intent.putExtra("title", music.getTitle());
                intent.putExtra("description", music.getDescription());

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.music_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.music_menu_context, menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();

        int position = info.position;
        cursor.moveToPosition(position);
        Music music = MusicModify.getMusic(cursor);

        switch (item.getItemId()) {
            case R.id.menu_edit_music:
                showMusicDialog(music);
                break;
            case R.id.menu_remove_music:
                MusicModify.delete(music.getId());
                updateListView();
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_music:
                showMusicDialog(null);
                break;
            case R.id.menu_exit:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    void showMusicDialog(final Music currentMusic) {
        View view = getLayoutInflater().inflate(R.layout.dialog_music, null);
        final EditText titleTxt = view.findViewById(R.id.dm_title);
        final EditText desTxt = view.findViewById(R.id.dm_description);
        final Button cancelBtn = view.findViewById(R.id.dm_cancel_btn);
        final Button commitBtn = view.findViewById(R.id.dm_commit_btn);

        //Edit Music
        if(currentMusic != null) {
            titleTxt.setText(currentMusic.getTitle());
            desTxt.setText(currentMusic.getDescription());
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);

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
                //Save data into database
                if(currentMusic == null) {
                    //insert du lieu
                    Music music = new Music(titleTxt.getText().toString(), desTxt.getText().toString());
                    MusicModify.insert(music);
                } else {
                    currentMusic.setTitle(titleTxt.getText().toString());
                    currentMusic.setDescription(desTxt.getText().toString());
                    MusicModify.update(currentMusic);
                }

                updateListView();

                dialog.dismiss();
            }
        });
    }

    void updateListView() {
        cursor = MusicModify.getMusicCusor();
        adapter.changeCursor(cursor);

        adapter.notifyDataSetChanged();
    }
}

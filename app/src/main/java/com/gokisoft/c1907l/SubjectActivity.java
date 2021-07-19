package com.gokisoft.c1907l;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SubjectActivity extends AppCompatActivity {
    ListView listView;
    List<String> dataList = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        listView = findViewById(R.id.as_listview);

        //fake data
        dataList.add("Lap Trinh C");
        dataList.add("HTML/CSS/JS");
        dataList.add("Bootstrap/jQuery");

        adapter = new ArrayAdapter<String>(this,
                R.layout.subject_item, R.id.si_textview, dataList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String v = dataList.get(position);
                Toast.makeText(SubjectActivity.this, v, Toast.LENGTH_SHORT).show();
            }
        });

        registerForContextMenu(listView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.subject_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.subject_menu_context, menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();

        int position = info.position;

        switch (item.getItemId()) {
            case R.id.menu_smc_edit_subject:
                showSubjectDialog(position);
                break;
            case R.id.menu_smc_remove_subject:
                dataList.remove(position);

                adapter.notifyDataSetChanged();
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sm_add_subject:
                showSubjectDialog(-1);
                break;
            case R.id.menu_sm_exit:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showSubjectDialog(final int position) {
        //B1: Convert layout xml -> view
        View v = getLayoutInflater().inflate(R.layout.dialog_subject_editor, null);

        final EditText subjectNameTxt = v.findViewById(R.id.dse_subject_name);
        if(position >= 0) {
            subjectNameTxt.setText(dataList.get(position));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(v);
        builder.setTitle("Subject Editor")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String subjectName = subjectNameTxt.getText().toString();
                        if(position >= 0) {
                            dataList.set(position, subjectName);
                        } else {
                            dataList.add(subjectName);
                        }

                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }
}

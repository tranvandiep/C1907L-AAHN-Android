package com.gokisoft.c1907l;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.gokisoft.c1907l.adapter.FoodAdapter2;
import com.gokisoft.c1907l.models.Food;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FoodActivity2 extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Food> dataList = new ArrayList<>();
    FoodAdapter2 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food2);

        recyclerView = findViewById(R.id.af2_listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Fake data
        for(int i=0;i<3;i++) {
            dataList.add(new Food("Thumbnail 1", "Title " + i, "Noi Dung " + i));
        }

        adapter = new FoodAdapter2(this, dataList);

        recyclerView.setAdapter(adapter);

        registerForContextMenu(recyclerView);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = item.getOrder();

        switch (item.getItemId()) {
            case 0:
                showSubjectDialog(position);
                break;
            case 1:
                dataList.remove(position);

                adapter.notifyDataSetChanged();
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.subject_menu, menu);

        return super.onCreateOptionsMenu(menu);
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
        View v = getLayoutInflater().inflate(R.layout.dialog_food_editor, null);

        final EditText titleTxt = v.findViewById(R.id.dfe_title);
        final EditText desTxt = v.findViewById(R.id.dfe_description);
        if(position >= 0) {
            titleTxt.setText(dataList.get(position).getTitle());
            desTxt.setText(dataList.get(position).getDescription());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(v);
        builder.setTitle("Subject Editor")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String title = titleTxt.getText().toString();
                        String des = desTxt.getText().toString();
                        Food food = new Food(title, des);
                        if(position >= 0) {
                            dataList.set(position, food);
                        } else {
                            dataList.add(food);
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

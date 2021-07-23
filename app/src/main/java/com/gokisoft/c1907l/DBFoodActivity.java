package com.gokisoft.c1907l;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.gokisoft.c1907l.adapter.FoodAdapter;
import com.gokisoft.c1907l.db.DBHelper;
import com.gokisoft.c1907l.models.Food;
import com.gokisoft.c1907l.modify.FoodModify;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DBFoodActivity extends AppCompatActivity {
    ListView listView;
    List<Food> dataList = new ArrayList<>();
    FoodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        DBHelper.getInstance(this);
        //Fake du lieu vao database
//        FoodModify.insert(new Food("https://img.dominos.vn/Veggie-mania.jpg", "Title 1", "Noi Dung 1"));
//        FoodModify.insert(new Food("https://img.dominos.vn/Veggie-mania.jpg", "Title 2", "Noi Dung 1"));
//        FoodModify.insert(new Food("https://img.dominos.vn/Veggie-mania.jpg", "Title 3", "Noi Dung 1"));

        listView = findViewById(R.id.as_listview);

        dataList = FoodModify.getFoodList();

        adapter = new FoodAdapter(this, dataList);

        listView.setAdapter(adapter);

        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Food food = dataList.get(position);

                Intent i = new Intent(DBFoodActivity.this, FoodDetailActivity.class);
                i.putExtra("thisFood", food);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.food_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.food_menu_context, menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();

        int position = info.position;

        switch (item.getItemId()) {
            case R.id.menu_fmc_edit_food:
                showSubjectDialog(position);
                break;
            case R.id.menu_fmc_remove_food:
                FoodModify.delete(dataList.get(position).get_id());

                dataList = FoodModify.getFoodList();
                adapter.setDataList(dataList);
                adapter.notifyDataSetChanged();
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_fm_add_food:
                showSubjectDialog(-1);
                break;
            case R.id.menu_fm_exit:
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
        final EditText thumbnailTxt = v.findViewById(R.id.dfe_thumbnail);
        if(position >= 0) {
            titleTxt.setText(dataList.get(position).getTitle());
            desTxt.setText(dataList.get(position).getDescription());
            thumbnailTxt.setText(dataList.get(position).getThumbnail());
        } else {
            //fake data
            thumbnailTxt.setText("https://produits.bienmanger.com/34491-0w600h600_Corona_Extra_Mexican_Blonde_Beer.jpg");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(v);
        builder.setTitle("Subject Editor")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String title = titleTxt.getText().toString();
                        String des = desTxt.getText().toString();
                        String thumbnail = thumbnailTxt.getText().toString();
                        if(position >= 0) {
                            dataList.get(position).setTitle(title);
                            dataList.get(position).setThumbnail(thumbnail);
                            dataList.get(position).setDescription(des);

                            //sua thong tin du lieu
                            FoodModify.update(dataList.get(position));
                        } else {
                            Food f = new Food(thumbnail, title, des);
                            dataList.add(f);
                            FoodModify.insert(f);

                            dataList = FoodModify.getFoodList();
                            adapter.setDataList(dataList);
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

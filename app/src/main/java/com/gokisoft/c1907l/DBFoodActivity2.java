package com.gokisoft.c1907l;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.gokisoft.c1907l.adapter.FoodCursorAdapter;
import com.gokisoft.c1907l.db.DBHelper;
import com.gokisoft.c1907l.models.Food;
import com.gokisoft.c1907l.modify.FoodModify;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DBFoodActivity2 extends AppCompatActivity {
    ListView listView;
    FoodCursorAdapter adapter;
    Cursor currentCusor;

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

        currentCusor = FoodModify.getFoodCursor();
        adapter = new FoodCursorAdapter(this, currentCusor);

        listView.setAdapter(adapter);

        registerForContextMenu(listView);
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
                currentCusor.moveToPosition(position);
                int id = currentCusor.getInt(currentCusor.getColumnIndex("_id"));
                FoodModify.delete(id);

                updateFoods();
                break;
        }

        return super.onContextItemSelected(item);
    }

    void updateFoods() {
        currentCusor = FoodModify.getFoodCursor();
        adapter.changeCursor(currentCusor);
        adapter.notifyDataSetChanged();
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
            currentCusor.moveToPosition(position);

            String title = currentCusor.getString(currentCusor.getColumnIndex("title"));
            String des = currentCusor.getString(currentCusor.getColumnIndex("description"));
            String thumbnail = currentCusor.getString(currentCusor.getColumnIndex("thumbnail"));

            titleTxt.setText(title);
            desTxt.setText(des);
            thumbnailTxt.setText(thumbnail);
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
                            currentCusor.moveToPosition(position);
                            int id = currentCusor.getInt(currentCusor.getColumnIndex("_id"));
                            Food food = new Food(id, thumbnail, title, des);

                            //sua thong tin du lieu
                            FoodModify.update(food);
                        } else {
                            Food f = new Food(thumbnail, title, des);
                            FoodModify.insert(f);
                        }

                        updateFoods();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }
}

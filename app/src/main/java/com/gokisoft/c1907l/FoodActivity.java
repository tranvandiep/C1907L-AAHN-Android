package com.gokisoft.c1907l;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.gokisoft.c1907l.adapter.FoodAdapter;
import com.gokisoft.c1907l.models.Food;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity {
    ListView listView;
    List<Food> dataList = new ArrayList<>();
    FoodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        listView = findViewById(R.id.as_listview);

        //Fake data
        dataList.add(new Food("Thumbnail 1", "Title 1", "Noi Dung 1"));
        dataList.add(new Food("Thumbnail 1", "Title 2", "Noi Dung 1"));
        dataList.add(new Food("Thumbnail 1", "Title 3", "Noi Dung 1"));
        dataList.add(new Food("Thumbnail 1", "Title 4", "Noi Dung 1"));
        dataList.add(new Food("Thumbnail 1", "Title 5", "Noi Dung 1"));
        dataList.add(new Food("Thumbnail 1", "Title 6", "Noi Dung 1"));
        dataList.add(new Food("Thumbnail 1", "Title 7", "Noi Dung 1"));
        dataList.add(new Food("Thumbnail 1", "Title 8", "Noi Dung 1"));

        adapter = new FoodAdapter(this, dataList);

        listView.setAdapter(adapter);
    }
}

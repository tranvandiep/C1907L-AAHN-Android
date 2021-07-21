package com.gokisoft.c1907l;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.gokisoft.c1907l.models.Food;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;

public class FoodDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        //lay du lieu truyen sang
        Food food = getIntent().getParcelableExtra("thisFood");
        String thumbnail = food.getThumbnail();
        String title = food.getTitle();
        String des = food.getDescription();
//        String thumbnail = getIntent().getStringExtra("thumbnail");
//        String title = getIntent().getStringExtra("title");
//        String des = getIntent().getStringExtra("description");

        ImageView thumnailImg = findViewById(R.id.afd_thumbnail);
        TextView titleView = findViewById(R.id.afd_title);
        TextView desView = findViewById(R.id.afd_description);

        Picasso.with(this).load(thumbnail).into(thumnailImg);
        titleView.setText(title);
        desView.setText(des);
    }
}

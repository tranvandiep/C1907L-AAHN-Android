package com.gokisoft.bt2113;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.gokisoft.c1907l.R;

import androidx.appcompat.app.AppCompatActivity;

public class MusicDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_detail);

        ImageView thumbImg = findViewById(R.id.amd_thumbnail);
        TextView titleView = findViewById(R.id.amd_title);
        TextView desView = findViewById(R.id.amd_description);

        int resId = getIntent().getIntExtra("resId", R.drawable.thumb_1);
        String title = getIntent().getStringExtra("title");
        String des = getIntent().getStringExtra("description");

        thumbImg.setImageResource(resId);
        titleView.setText(title);
        desView.setText(des);
    }
}

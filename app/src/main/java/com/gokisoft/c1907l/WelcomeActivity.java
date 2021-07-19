package com.gokisoft.c1907l;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {
    TextView msgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        msgView = findViewById(R.id.aw_msg);

        String fullname = getIntent().getExtras().getString("fullname");
        String username = getIntent().getExtras().getString("username");
        String email = getIntent().getExtras().getString("email");

        msgView.setText("Fullname: " + fullname + "\nUsername: " + username + "\nEmail: " + email);
    }
}

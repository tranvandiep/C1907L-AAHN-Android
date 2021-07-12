package com.gokisoft.c1907l;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText usernameTxt, passwordTxt;
    Button loginBtn, registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameTxt = findViewById(R.id.am_username);
        passwordTxt = findViewById(R.id.am_password);
        loginBtn = findViewById(R.id.am_login_button);
        registerBtn = findViewById(R.id.am_register_button);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(MainActivity.class.getName(), "click login btn");
                String username = usernameTxt.getText().toString();
                String pwd = passwordTxt.getText().toString();

                Toast.makeText(MainActivity.this, username, Toast.LENGTH_SHORT).show();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }
}

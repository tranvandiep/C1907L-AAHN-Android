package com.gokisoft.c1907l;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText usernameTxt, passwordTxt;
    Button loginBtn, registerBtn;

    String fullnameCache, usernameCache, emailCache, pwdCache;

    class RegisterReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //Du lieu khi nhan -> implement tai day
            String action = intent.getAction();

            switch (action) {
                case Config.ACTION_REGISTER_SUCCESS:
                    fullnameCache = intent.getStringExtra("fullname");
                    usernameCache = intent.getStringExtra("username");
                    emailCache = intent.getStringExtra("email");
                    pwdCache = intent.getStringExtra("password");

                    Toast.makeText(MainActivity.this, "Register successfully (2)!!! ", Toast.LENGTH_SHORT).show();

                    break;
            }
        }
    }

    RegisterReceiver myRegisterReceiver;

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

                if(username.equalsIgnoreCase(usernameCache) && pwd.equals(pwdCache)) {
                    Toast.makeText(MainActivity.this, "Login success", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(MainActivity.this, WelcomeActivity.class);
                    i.putExtra("fullname", fullnameCache);
                    i.putExtra("username", usernameCache);
                    i.putExtra("email", emailCache);
                    startActivity(i);

                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    showDialogMsg("Login Failed!", "Plz check username and password again!!!");
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
//                startActivity(i);
                startActivityForResult(i, Config.CODE_REGISTER_SUCCESS);
            }
        });

        myRegisterReceiver = new RegisterReceiver();

        IntentFilter filter = new IntentFilter();
        filter.addAction(Config.ACTION_REGISTER_SUCCESS);

        try {
            registerReceiver(myRegisterReceiver, filter);
        } catch(Exception e) {}
    }

    void showDialogMsg(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (resultCode) {
            case Config.CODE_REGISTER_SUCCESS:
                fullnameCache = data.getStringExtra("fullname");
                usernameCache = data.getStringExtra("username");
                emailCache = data.getStringExtra("email");
                pwdCache = data.getStringExtra("password");

                Toast.makeText(this, "Register successfully!!! ", Toast.LENGTH_SHORT).show();

                break;
        }
    }

    @Override
    public void finish() {
        try {
            unregisterReceiver(myRegisterReceiver);
        } catch(Exception e) {}
        super.finish();
    }
}

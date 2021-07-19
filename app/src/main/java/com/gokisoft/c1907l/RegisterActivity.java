package com.gokisoft.c1907l;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    EditText fullnameTxt, usernameTxt, emailTxt, pwdTxt, confirmPwdTxt;
    Button registerBtn, backBtn, registerBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullnameTxt = findViewById(R.id.ar_fullname);
        usernameTxt = findViewById(R.id.ar_username);
        emailTxt = findViewById(R.id.ar_email);
        pwdTxt = findViewById(R.id.ar_password);
        confirmPwdTxt = findViewById(R.id.ar_confirm_pwd);

        registerBtn = findViewById(R.id.ar_register_btn);
        backBtn = findViewById(R.id.ar_back_btn);
        registerBtn2 = findViewById(R.id.ar_register_btn2);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = fullnameTxt.getText().toString();
                String username = usernameTxt.getText().toString();
                String email = emailTxt.getText().toString();
                String pwd = pwdTxt.getText().toString();
                String confirmPwd = confirmPwdTxt.getText().toString();

                if(!pwd.equals(confirmPwd)) {
                    Toast.makeText(RegisterActivity.this, "Mat khau khong khop", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Gui du lieu nguoc lai cho LoginActivity
//                Toast.makeText(RegisterActivity.this, "Dky thanh cong", Toast.LENGTH_SHORT).show();
                Intent data = new Intent();
                data.putExtra("fullname", fullname);
                data.putExtra("username", username);
                data.putExtra("email", email);
                data.putExtra("password", pwd);
                setResult(Config.CODE_REGISTER_SUCCESS, data);

                finish();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        registerBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = fullnameTxt.getText().toString();
                String username = usernameTxt.getText().toString();
                String email = emailTxt.getText().toString();
                String pwd = pwdTxt.getText().toString();
                String confirmPwd = confirmPwdTxt.getText().toString();

                //Gui du lieu sang MainActivity bang Broadcast/Receiver nhu the nao.
                Intent data = new Intent();
                data.setAction(Config.ACTION_REGISTER_SUCCESS);
                data.putExtra("fullname", fullname);
                data.putExtra("username", username);
                data.putExtra("email", email);
                data.putExtra("password", pwd);

                sendBroadcast(data);

                finish();
            }
        });
    }
}

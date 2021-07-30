package com.gokisoft.c1907l;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TestServiceActivity extends AppCompatActivity implements View.OnClickListener{
    TextView counterView;
    Button startServiceBtn, stopServiceBtn;
    Intent intentService;

    int count = 0;

    BroadcastReceiver counterReceiver =  new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            switch (action) {
                case Config.ACTION_COUNTER:

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            counterView.setText(String.valueOf(++count));
                        }
                    });

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_service);

        counterView = findViewById(R.id.ats_counter);
        startServiceBtn = findViewById(R.id.ats_start_service);
        stopServiceBtn = findViewById(R.id.ats_stop_service);

//        startServiceBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Lang nghe su kien
//            }
//        });
        startServiceBtn.setOnClickListener(this);
        stopServiceBtn.setOnClickListener(this);

        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction(Config.ACTION_COUNTER);

            registerReceiver(counterReceiver, filter);
        } catch(Exception e) {
        }
    }

    @Override
    public void onClick(View v) {
        if(v.equals(startServiceBtn)) {
            //Xu ly start service
            Toast.makeText(this, "Start Service", Toast.LENGTH_SHORT).show();
            intentService = new Intent(this, CounterService.class);
            startService(intentService);
        } else if(v.equals(stopServiceBtn)) {
            //Xu ly stop service
            Toast.makeText(this, "Stop Service", Toast.LENGTH_SHORT).show();
            stopService(intentService);
        }
    }

    @Override
    public void finish() {
        try {
            unregisterReceiver(counterReceiver);
        } catch(Exception e) {
        }

        super.finish();
    }
}

package com.gokisoft.c1907l;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class CounterService extends Service {
    Handler handler = new Handler();
    int count = 0;
    boolean isLive = true;

    public CounterService() {
        Log.d(CounterService.class.getName(), "CounterService ...");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(CounterService.class.getName(), "onCreate ...");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(CounterService.class.getName(), "onStartCommand ...");

        startCounter();

        return super.onStartCommand(intent, flags, startId);
    }

    void startCounter() {
        if(!isLive) return;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), String.valueOf(++count), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                intent.setAction(Config.ACTION_COUNTER);

                sendBroadcast(intent);
                startCounter();
            }
        }, 1000);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(CounterService.class.getName(), "onBind ...");

        return null;
    }

    @Override
    public void onDestroy() {
        isLive = false;
        Log.d(CounterService.class.getName(), "onDestroy ...");
        super.onDestroy();
    }
}

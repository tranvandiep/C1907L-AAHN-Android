package com.gokisoft.c1907l;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import androidx.appcompat.app.AppCompatActivity;

public class AsyncTaskActivity extends AppCompatActivity implements View.OnClickListener{
    TextView titleView, percentView;
    ProgressBar percentProgressBar;
    Button downloadBtn;

    class DownloadAsyncTask extends AsyncTask {
        @Override
        protected void onPreExecute() {
            Log.d(AsyncTaskActivity.class.getName(), "onPreExecute ..");
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            Log.d(AsyncTaskActivity.class.getName(), "doInBackground ..");
            startDownloadFile();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            Log.d(AsyncTaskActivity.class.getName(), "onPostExecute ..");
            super.onPostExecute(o);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        titleView = findViewById(R.id.at_title);
        percentView = findViewById(R.id.at_percent);
        percentProgressBar = findViewById(R.id.at_progress_bar);
        downloadBtn = findViewById(R.id.at_download_btn);

        downloadBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.equals(downloadBtn)) {
            downloadBtn.setVisibility(View.GONE);
            titleView.setVisibility(View.VISIBLE);
            percentView.setVisibility(View.VISIBLE);
            percentProgressBar.setVisibility(View.VISIBLE);

            new DownloadAsyncTask().execute();
        }
    }

    void startDownloadFile() {
        int count;
        try {
            URL url = new URL(Config.URL_YOUTUBE_TEST);
            URLConnection conection = url.openConnection();
            conection.connect();

            // this will be useful so that you can show a tipical 0-100%
            // progress bar
            int lenghtOfFile = conection.getContentLength();

            // download the file
            InputStream input = new BufferedInputStream(url.openStream(),
                    8192);

            // Output stream
            File folder = new File("/sdcard/" + Environment.DIRECTORY_DOWNLOADS);
            if(!folder.exists()) {
                folder = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
            }
            File file = new File(folder, "C1907L_Youtube.mp4");

            OutputStream output = new FileOutputStream(file);

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                // After this onProgressUpdate will be called
                publishProgress((int) ((total * 100) / lenghtOfFile));

                // writing data to file
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(AsyncTaskActivity.class.getName(), "finish thread........");
    }

    int currentPercent = 0;
    void publishProgress(final int percent) {
        if(currentPercent == percent) return;
        currentPercent = percent;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                percentView.setText(percent + "%");
                percentProgressBar.setProgress(percent);

                Log.d(AsyncTaskActivity.class.getName(), "" + percent);
            }
        });
    }

    @Override
    public void finish() {
        Log.d(AsyncTaskActivity.class.getName(), "finish ........");
        super.finish();
    }
}

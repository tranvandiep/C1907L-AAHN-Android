package com.gokisoft.c1907l;

import android.os.Bundle;
import android.widget.ListView;

import com.gokisoft.c1907l.adapter.NewsAdapter;
import com.gokisoft.c1907l.rss.RssFeed;
import com.gokisoft.c1907l.rss.RssItem;
import com.gokisoft.c1907l.rss.RssService;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class NewsActivity extends AppCompatActivity {
    ListView listView;
    NewsAdapter adapter;
    List<RssItem> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        loadRss();

        listView = findViewById(R.id.ans_listview);

        adapter = new NewsAdapter(this, dataList);

        listView.setAdapter(adapter);
    }

    private void loadRss() {
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(Config.RSS_VIETNAMNET_BASE)
                .addConverterFactory(SimpleXmlConverterFactory.create());

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        builder.client(httpClient.build());

        Retrofit retrofit = builder.build();

        RssService rssService = retrofit.create(RssService.class);

        Call<RssFeed> callAsync = rssService.getBussinessFeed();

        callAsync.enqueue(new Callback<RssFeed>() {
            @Override
            public void onResponse(Call<RssFeed> call, Response<RssFeed> response) {
                if (response.isSuccessful()) {
                    final RssFeed apiResponse = response.body();
                    // API response
//                    System.out.println(apiResponse);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dataList = apiResponse.channel.itemList;
                            adapter.setDataList(dataList);
                            adapter.notifyDataSetChanged();
                        }
                    });
                } else {
                    System.out.println("Request Error :: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<RssFeed> call, Throwable t) {
                if (call.isCanceled()) {
                    System.out.println("Call was cancelled forcefully");
                } else {
                    System.out.println("Network Error :: " + t.getLocalizedMessage());
                }
            }
        });
    }
}

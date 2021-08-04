package com.gokisoft.c1907l.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gokisoft.c1907l.R;
import com.gokisoft.c1907l.rss.RssItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Diep.Tran on 8/4/21.
 */

public class NewsAdapter extends BaseAdapter{
    Activity activity;
    List<RssItem> dataList;

    public NewsAdapter(Activity activity, List<RssItem> dataList) {
        this.activity = activity;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    static int count = 0;
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        NewsHolder holder = null;

        if(view == null) {
            Log.d(FoodAdapter.class.getName(), "Count >> " + ++count);
            view = activity.getLayoutInflater().inflate(R.layout.news_item, null);

            TextView titleView = view.findViewById(R.id.ni_title);
            TextView desView = view.findViewById(R.id.ni_description);
            ImageView thumbnailImg = view.findViewById(R.id.ni_thumbnail);

            holder = new NewsHolder(titleView, desView, thumbnailImg);
            view.setTag(holder);
        } else {
            holder = (NewsHolder) view.getTag();
        }

        RssItem news = dataList.get(position);

        holder.titleView.setText(news.title);
        holder.desView.setText(news.description);

        Picasso.with(activity).load(news.media.url).into(holder.thumbnailImg);

        return view;
    }

    public List<RssItem> getDataList() {
        return dataList;
    }

    public void setDataList(List<RssItem> dataList) {
        this.dataList = dataList;
    }

    class NewsHolder {
        TextView titleView;
        TextView desView;
        ImageView thumbnailImg;

        public NewsHolder(TextView titleView, TextView desView, ImageView thumbnailImg) {
            this.titleView = titleView;
            this.desView = desView;
            this.thumbnailImg = thumbnailImg;
        }
    }
}

package com.gokisoft.c1907l.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gokisoft.c1907l.R;
import com.gokisoft.c1907l.models.Food;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Diep.Tran on 7/16/21.
 */

public class FoodAdapter extends BaseAdapter{
    Activity activity;
    List<Food> dataList;

    public FoodAdapter(Activity activity, List<Food> dataList) {
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
        FoodHolder holder = null;

        if(view == null) {
            Log.d(FoodAdapter.class.getName(), "Count >> " + ++count);
            view = activity.getLayoutInflater().inflate(R.layout.food_item, null);

            TextView titleView = view.findViewById(R.id.fi_title);
            TextView desView = view.findViewById(R.id.fi_description);
            ImageView thumbnailImg = view.findViewById(R.id.fi_thumbnail);

            holder = new FoodHolder(titleView, desView, thumbnailImg);
            view.setTag(holder);
        } else {
            holder = (FoodHolder) view.getTag();
        }

        Food food = dataList.get(position);

        holder.titleView.setText(food.getTitle());
        holder.desView.setText(food.getDescription());

        Picasso.with(activity).load(food.getThumbnail()).into(holder.thumbnailImg);

        return view;
    }

    public List<Food> getDataList() {
        return dataList;
    }

    public void setDataList(List<Food> dataList) {
        this.dataList = dataList;
    }

    class FoodHolder {
        TextView titleView;
        TextView desView;
        ImageView thumbnailImg;

        public FoodHolder(TextView titleView, TextView desView, ImageView thumbnailImg) {
            this.titleView = titleView;
            this.desView = desView;
            this.thumbnailImg = thumbnailImg;
        }
    }
}

package com.gokisoft.c1907l.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gokisoft.c1907l.R;
import com.gokisoft.c1907l.models.Food;

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

            holder = new FoodHolder(titleView, desView);
            view.setTag(holder);
        } else {
            holder = (FoodHolder) view.getTag();
        }

        Food food = dataList.get(position);

        holder.titleView.setText(food.getTitle());
        holder.desView.setText(food.getDescription());

        return view;
    }

    class FoodHolder {
        TextView titleView;
        TextView desView;

        public FoodHolder(TextView titleView, TextView desView) {
            this.titleView = titleView;
            this.desView = desView;
        }
    }
}

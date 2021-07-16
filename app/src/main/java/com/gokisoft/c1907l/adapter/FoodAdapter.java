package com.gokisoft.c1907l.adapter;

import android.app.Activity;
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

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = activity.getLayoutInflater().inflate(R.layout.food_item, null);

        TextView titleView = view.findViewById(R.id.fi_title);
        TextView desView = view.findViewById(R.id.fi_description);

        Food food = dataList.get(position);

        titleView.setText(food.getTitle());
        desView.setText(food.getDescription());

        return view;
    }
}

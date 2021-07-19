package com.gokisoft.c1907l.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gokisoft.c1907l.R;
import com.gokisoft.c1907l.models.Food;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Diep.Tran on 7/19/21.
 */

public class FoodAdapter2 extends RecyclerView.Adapter<FoodAdapter2.FoodHolder>{
    Activity activity;
    List<Food> dataList;

    public FoodAdapter2(Activity activity, List<Food> dataList) {
        this.activity = activity;
        this.dataList = dataList;
    }

    @Override
    public FoodHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = activity.getLayoutInflater().inflate(R.layout.food_item, null);
        TextView titleView = view.findViewById(R.id.fi_title);
        TextView desView = view.findViewById(R.id.fi_description);

        FoodHolder holder = new FoodHolder(view, titleView, desView);

        Log.d(FoodAdapter2.class.getName() + "(1)", "Count (1): " + ++count1);

        return holder;
    }

    static int count1 = 0;
    static int count2 = 0;
    @Override
    public void onBindViewHolder(FoodHolder holder, int position) {
        Food food = dataList.get(position);

        holder.titleView.setText(food.getTitle());
        holder.desView.setText(food.getDescription());
        holder.position = position;

        Log.d(FoodAdapter2.class.getName() + "(2)", "Count (2): " + ++count2);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class FoodHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView titleView;
        TextView desView;
        int position = -1;

        public FoodHolder(View itemView) {
            super(itemView);
        }

        public FoodHolder(View itemView, TextView titleView, TextView desView) {
            super(itemView);
            this.titleView = titleView;
            this.desView = desView;

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(Menu.NONE, 0,
                    position, "Edit Food");
            menu.add(Menu.NONE, 1,
                    position, "Remove Food");
        }
    }
}

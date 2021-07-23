package com.gokisoft.c1907l.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gokisoft.c1907l.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Diep.Tran on 7/23/21.
 */

public class FoodCursorAdapter extends CursorAdapter {
    Activity activity;

    public FoodCursorAdapter(Activity activity, Cursor c) {
        super(activity, c);
        this.activity = activity;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.food_item, null);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView titleView = view.findViewById(R.id.fi_title);
        TextView desView = view.findViewById(R.id.fi_description);
        ImageView thumbnailImg = view.findViewById(R.id.fi_thumbnail);

        String title = cursor.getString(cursor.getColumnIndex("title"));
        String des = cursor.getString(cursor.getColumnIndex("description"));
        String thumbnail = cursor.getString(cursor.getColumnIndex("thumbnail"));

        titleView.setText(title);
        desView.setText(des);

        Picasso.with(activity).load(thumbnail).into(thumbnailImg);
    }
}

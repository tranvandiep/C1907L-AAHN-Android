package com.gokisoft.bt2113.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gokisoft.bt2113.model.Music;
import com.gokisoft.bt2113.model.MusicModify;
import com.gokisoft.c1907l.R;

/**
 * Created by Diep.Tran on 8/9/21.
 */

public class MusicAdapter extends CursorAdapter{
    Activity activity;

    public MusicAdapter(Activity activity, Cursor c) {
        super(activity, c);
        this.activity = activity;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View convertView = activity.getLayoutInflater().inflate(R.layout.music_item, null);
        return convertView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView thumbImg = view.findViewById(R.id.mi_thumbnail);
        TextView titleView = view.findViewById(R.id.mi_title);
        TextView desView = view.findViewById(R.id.mi_description);

        Music music = MusicModify.getMusic(cursor);

        thumbImg.setImageResource(music.getResId());
        titleView.setText(music.getTitle());
        desView.setText(music.getDescription());
    }
}

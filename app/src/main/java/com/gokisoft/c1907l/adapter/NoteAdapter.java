package com.gokisoft.c1907l.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gokisoft.c1907l.R;
import com.gokisoft.c1907l.models.Note;
import com.gokisoft.c1907l.modify.NoteModify;

/**
 * Created by Diep.Tran on 7/26/21.
 */

public class NoteAdapter extends CursorAdapter{
    Activity activity;

    public NoteAdapter(Activity context, Cursor c) {
        super(context, c);
        this.activity = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.list_item, null);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        LinearLayout quantrongView = view.findViewById(R.id.li_quantrong);
        TextView noidungView = view.findViewById(R.id.li_noidung);
        TextView ngaytaoView = view.findViewById(R.id.li_ngaytao);

        Note note = NoteModify.find(cursor);

        if(note.isQuantrong()) {
            quantrongView.setBackgroundResource(R.color.colorImportant);
        } else {
            quantrongView.setBackgroundResource(R.color.colorNormal);
        }

        noidungView.setText(note.getNoidung());
        ngaytaoView.setText(note.getStrDate());
    }
}

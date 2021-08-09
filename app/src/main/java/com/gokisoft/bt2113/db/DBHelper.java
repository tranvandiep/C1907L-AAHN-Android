package com.gokisoft.bt2113.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gokisoft.bt2113.model.MusicModify;

/**
 * Created by Diep.Tran on 8/9/21.
 */

public class DBHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "MusicDB";
    private static final int VERSION = 1;

    private static DBHelper instance = null;

    private DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public synchronized static DBHelper getInstance(Context context) {
        if(instance == null) {
            instance = new DBHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MusicModify.SQL_MUSIC_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

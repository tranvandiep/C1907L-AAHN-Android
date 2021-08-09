package com.gokisoft.bt2113.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gokisoft.bt2113.db.DBHelper;

/**
 * Created by Diep.Tran on 8/9/21.
 */

public class MusicModify {
    public static final String TABLE_NAME = "music";

    public static final String SQL_MUSIC_TABLE = "create table music (\n" +
            "\t_id integer primary key autoincrement,\n" +
            "\ttitle varchar(150),\n" +
            "\tdescription text\n" +
            ")";

    public static void insert(Music music) {
        ContentValues values = new ContentValues();
        values.put("title", music.getTitle());
        values.put("description", music.getDescription());

        SQLiteDatabase sqLiteDatabase = DBHelper.getInstance(null).getWritableDatabase();

        sqLiteDatabase.insert(TABLE_NAME, null, values);
    }

    public static void update(Music music) {
        ContentValues values = new ContentValues();
        values.put("title", music.getTitle());
        values.put("description", music.getDescription());

        SQLiteDatabase sqLiteDatabase = DBHelper.getInstance(null).getWritableDatabase();

        sqLiteDatabase.update(TABLE_NAME, values, "_id = " + music.getId(), null);
    }

    public static void delete(int id) {
        SQLiteDatabase sqLiteDatabase = DBHelper.getInstance(null).getWritableDatabase();

        sqLiteDatabase.delete(TABLE_NAME, "_id = " + id, null);
    }

    public static Music getMusic(Cursor cursor) {
        Music music = new Music(
                cursor.getInt(cursor.getColumnIndex("_id")),
                cursor.getString(cursor.getColumnIndex("title")),
                cursor.getString(cursor.getColumnIndex("description"))
        );

        return music;
    }

    public static Cursor getMusicCusor() {
        Cursor cursor = null;

        String sql = "select * from " + TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = DBHelper.getInstance(null).getReadableDatabase();

        cursor = sqLiteDatabase.rawQuery(sql, null);
        return cursor;
    }
}

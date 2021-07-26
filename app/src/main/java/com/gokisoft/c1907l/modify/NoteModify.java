package com.gokisoft.c1907l.modify;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gokisoft.c1907l.db.DBHelper2;
import com.gokisoft.c1907l.models.Note;

/**
 * Created by Diep.Tran on 7/26/21.
 */

public class NoteModify {
    public static final String TABLE_NAME = "note";

    public static final String TABLE_NOTE = "create table note (\n" +
            "\t_id integer primary key autoincrement,\n" +
            "\tnoidung text,\n" +
            "\tquantrong integer,\n" +
            "\tngaytao Date\n" +
            ")";

    public static void insert(Note note) {
        ContentValues values = new ContentValues();

        values.put("noidung", note.getNoidung());
        values.put("quantrong", note.isQuantrong()?1:0);
        values.put("ngaytao", note.getStrDate());

        SQLiteDatabase sqLiteDatabase = DBHelper2.getInstance(null).getWritableDatabase();
        sqLiteDatabase.insert(TABLE_NAME, null, values);
    }

    public static void update(Note note) {
        ContentValues values = new ContentValues();

        values.put("noidung", note.getNoidung());
        values.put("quantrong", note.isQuantrong()?1:0);
        values.put("ngaytao", note.getStrDate());

        SQLiteDatabase sqLiteDatabase = DBHelper2.getInstance(null).getWritableDatabase();
        sqLiteDatabase.update(TABLE_NAME, values, "_id = " + note.get_id(), null);
    }

    public static void delete(int id) {
        SQLiteDatabase sqLiteDatabase = DBHelper2.getInstance(null).getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, "_id = " + id, null);
    }

    public static Note find(int id) {
        String sql = "select * from " + TABLE_NAME + " where _id = " + id;

        SQLiteDatabase sqLiteDatabase = DBHelper2.getInstance(null).getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        Note note = null;
        cursor.moveToNext();

        note = new Note(
                cursor.getInt(cursor.getColumnIndex("_id")),
                cursor.getString(cursor.getColumnIndex("noidung")),
                cursor.getInt(cursor.getColumnIndex("quantrong")),
                cursor.getString(cursor.getColumnIndex("ngaytao"))
        );

        return note;
    }

    public static Note find(Cursor cursor) {
        Note note = new Note(
                cursor.getInt(cursor.getColumnIndex("_id")),
                cursor.getString(cursor.getColumnIndex("noidung")),
                cursor.getInt(cursor.getColumnIndex("quantrong")),
                cursor.getString(cursor.getColumnIndex("ngaytao"))
        );

        return note;
    }

    public static Cursor getNoteCursor() {
        String sql = "select * from " + TABLE_NAME;

        SQLiteDatabase sqLiteDatabase = DBHelper2.getInstance(null).getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        return cursor;
    }
}

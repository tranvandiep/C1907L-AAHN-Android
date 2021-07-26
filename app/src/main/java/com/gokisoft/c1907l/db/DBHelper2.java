package com.gokisoft.c1907l.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.gokisoft.c1907l.modify.NoteModify;

/**
 * Created by Diep.Tran on 7/26/21.
 */

public class DBHelper2 extends SQLiteOpenHelper{
    static final String DB_NAME = "NoteApp";
    static final int VERSION = 1;

    static DBHelper2 instance = null;

    private DBHelper2(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public synchronized static DBHelper2 getInstance(Context context) {
        if(instance == null) {
            instance = new DBHelper2(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Ham nay chi goi 1 lan duy nhat -> khi database chua khoi tao.
        //Tao database -> table -> Foods -> Version 1
        db.execSQL(NoteModify.TABLE_NOTE);
        Log.d(DBHelper2.class.getName(), "Create food table is success!!!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Chi goi 1 lan khi co su thay doi phien ban cua Database
    }
}

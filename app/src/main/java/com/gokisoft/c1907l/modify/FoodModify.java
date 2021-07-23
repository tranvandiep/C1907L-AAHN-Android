package com.gokisoft.c1907l.modify;

/**
 * Created by Diep.Tran on 7/23/21.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gokisoft.c1907l.db.DBHelper;
import com.gokisoft.c1907l.models.Food;

import java.util.ArrayList;
import java.util.List;

/**
 * Insert/Update/Delete * Select -> database
 */
public class FoodModify {
    public static final String TABLE_FOOD = "foods";

    public static final String TABLE_SQL = "create table foods (\n" +
            "\t_id integer primary key autoincrement,\n" +
            "\ttitle varchar(150) not null,\n" +
            "\tdescription text,\n" +
            "\tthumbnail varchar(500)\n" +
            ")";

    public static void insert(Food food) {
        //insert du lieu vao database
        ContentValues values = new ContentValues();
        values.put("title", food.getTitle());
        values.put("description", food.getDescription());
        values.put("thumbnail", food.getThumbnail());

        //Insert database
        SQLiteDatabase sqLiteDatabase = DBHelper.getInstance(null).getWritableDatabase();
        sqLiteDatabase.insert(TABLE_FOOD, null, values);
    }

    public static void update(Food food) {
        //insert du lieu vao database
        ContentValues values = new ContentValues();
        values.put("title", food.getTitle());
        values.put("description", food.getDescription());
        values.put("thumbnail", food.getThumbnail());

        //Insert database
        SQLiteDatabase sqLiteDatabase = DBHelper.getInstance(null).getWritableDatabase();
        sqLiteDatabase.update(TABLE_FOOD, values, " _id = " + food.get_id(), null);
    }

    public static void delete(int id) {
        //Insert database
        SQLiteDatabase sqLiteDatabase = DBHelper.getInstance(null).getWritableDatabase();
        sqLiteDatabase.delete(TABLE_FOOD, " _id = " + id, null);
    }

    public static Food find(int id) {
        String sql = "select * from " + TABLE_FOOD + " where _id = " + id;

        SQLiteDatabase sqLiteDatabase = DBHelper.getInstance(null).getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        Food food = null;
        while(cursor.moveToNext()) {
            food = new Food(
                    cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getString(cursor.getColumnIndex("thumbnail")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("description"))
            );
            break;
        }

        return food;
    }

    public static List<Food> getFoodList() {
        List<Food> foodList = new ArrayList<>();

        String sql = "select * from " + TABLE_FOOD;

        SQLiteDatabase sqLiteDatabase = DBHelper.getInstance(null).getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        while(cursor.moveToNext()) {
            Food food = new Food(
                   cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getString(cursor.getColumnIndex("thumbnail")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("description"))
                    );
            foodList.add(food);
        }

        return foodList;
    }

    public static Cursor getFoodCursor() {
        String sql = "select * from " + TABLE_FOOD;

        SQLiteDatabase sqLiteDatabase = DBHelper.getInstance(null).getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        return cursor;
    }
}

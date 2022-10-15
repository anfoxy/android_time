package com.example.timelab.ui.bd;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.timelab.ui.model.AlarmModel;

import java.util.ArrayList;


public class MyDBManager {
    private Context context;
    private MyDBHelper myDBHelper;
    private SQLiteDatabase db;

    public MyDBManager(Context context) {
        this.context = context;
        myDBHelper = new MyDBHelper(context);
    }

    public void openDB() {
        db = myDBHelper.getWritableDatabase();
    }
    public Cursor getYourTableContents() {
        openDB();
        Cursor data = db.rawQuery("SELECT * FROM " + "alarm", null);
        return data;
    }
    @SuppressLint("Range")
    public ArrayList<AlarmModel> set() {

        ArrayList<AlarmModel> pl = new ArrayList<>();
        Cursor yourCursor = getYourTableContents();
        String queryID_que = "SELECT " + MyConstants._ID + ", " + MyConstants.TIME + ", " + MyConstants.DATE + " FROM " + MyConstants.TABLE_ALARM;
        Cursor cursor2 = db.rawQuery(queryID_que, null);

        int ff = 0;

        while (yourCursor.moveToNext()) {
            ff += 1;
        }

        if(ff > 0) {
            cursor2.moveToFirst();
            do {
                pl.add(new AlarmModel(cursor2.getString(cursor2.getColumnIndex(MyConstants.DATE)),
                        cursor2.getLong(cursor2.getColumnIndex(MyConstants.TIME))));
            }
            while (cursor2.moveToNext());


            cursor2.close();
        }
        return pl;
    }
    public void insert_TABLE_ALARM(String date, Long time) {
        ContentValues cv = new ContentValues();
        cv.put(MyConstants.DATE,  date);
        cv.put(MyConstants.TIME,  time);
        db.insert(MyConstants.TABLE_ALARM, null, cv);
    }

    @SuppressLint("Range")
    public void delete_alarm(String name) {
        String queryID_que = "SELECT " + MyConstants._ID + ", " + MyConstants.TIME + ", " + MyConstants.DATE + " FROM " + MyConstants.TABLE_ALARM;
        Cursor cursor = db.rawQuery(queryID_que, null);
        cursor.moveToFirst();
        do {
             String n = cursor.getString(cursor.getColumnIndex(MyConstants.DATE));
            if (name.equals(n)) {
                 int ind = cursor.getInt(cursor.getColumnIndex(MyConstants._ID));
                db.delete(MyConstants.TABLE_ALARM, MyConstants._ID + " = ?", new String[]{String.valueOf(ind)});
            }
        } while (cursor.moveToNext());
        cursor.close();
    }

    public void closeDB() {
        myDBHelper.close();
    }
}


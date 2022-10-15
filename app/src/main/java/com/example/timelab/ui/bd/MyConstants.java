package com.example.timelab.ui.bd;

public class MyConstants {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "DBaseV.db";


    public static final String TABLE_ALARM = "alarm";

    public static final String _ID = "_id";

    public static final String DATE = "date";
    public static final String TIME = "time";


       public static final String TABLE_STRUCTURE_ALARM = "CREATE TABLE IF NOT EXISTS " +
               TABLE_ALARM + " (" + _ID + " INTEGER PRIMARY KEY," +  TIME + " LONG," + DATE + " TEXT)";


    public static final String DROP_TABLE_QUE = "DROP TABLE IF EXISTS " + TABLE_ALARM;

}
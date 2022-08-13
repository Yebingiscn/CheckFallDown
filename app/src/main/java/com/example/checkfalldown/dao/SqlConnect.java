package com.example.checkfalldown.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlConnect extends SQLiteOpenHelper {

    public SqlConnect(Context context) {
        super(context, "userInfo.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {//创建数据库
        sqLiteDatabase.execSQL("CREATE TABLE userinfo(name VARCHAR(20) PRIMARY KEY," +
                "passWord VARCHAR(20))");
        sqLiteDatabase.execSQL("CREATE TABLE smsinfo(" +
                "sms_id Integer PRIMARY KEY AUTOINCREMENT," +
                " sms_sender VARCHAR(20) , sms_body VARCHAR(50)" +
                ",sms_time VARCHAR(50))");
        sqLiteDatabase.execSQL("CREATE TABLE oldmaninfo(" +
                "oldman_id Integer PRIMARY KEY AUTOINCREMENT," +
                "oldman_name VARCHAR(20),  oldman_contact VARCHAR(20))");
        System.out.println("db is created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

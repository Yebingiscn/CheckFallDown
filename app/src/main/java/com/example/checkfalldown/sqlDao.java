package com.example.checkfalldown;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class sqlDao {
    public static final int FAIL_OPERATE = -1;
    sqlConnect sqlConnect;
    SQLiteDatabase db;
    ContentValues contentValues;
    Cursor cursor;

    public sqlDao(Context context) {
        sqlConnect = new sqlConnect(context);
    }

    public boolean insertUserInfo(UserInfo userInfo) {
        db = sqlConnect.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put("name", userInfo.getUserName());
        contentValues.put("password", userInfo.getUserPwd());
        long id = db.insert("userinfo", null, contentValues);
        System.out.println(id);
        return id != FAIL_OPERATE;
    }

    public boolean insertSmsInfo(SmsInfo smsInfo) {
        db = sqlConnect.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put("sms_sender", smsInfo.getSms_sender());
        contentValues.put("sms_body", smsInfo.getSms_body());
        contentValues.put("sms_time", smsInfo.getSms_time());
        long id = db.insert("smsinfo", null, contentValues);
        System.out.println(id);
        return id != FAIL_OPERATE;

    }

    public List<SmsInfo> querySmsInfo() {
        db = sqlConnect.getReadableDatabase();
        cursor = db.query("smsinfo",
                null, null, null, null, null, null);
        ArrayList<SmsInfo> list = new ArrayList<>();
        if (cursor.moveToLast()) {
            @SuppressLint("Range") String sms_sender =
                    cursor.getString(cursor.getColumnIndex("sms_sender"));
            @SuppressLint("Range") String sms_body =
                    cursor.getString(cursor.getColumnIndex("sms_body"));
            @SuppressLint("Range") String sms_time =
                    cursor.getString(cursor.getColumnIndex("sms_time"));
            list.add(new SmsInfo(sms_sender, sms_body, sms_time));
        }
        cursor.close();
        db.close();
        return list;
    }

    public boolean updateUserInfo(UserInfo userInfo) {
        db = sqlConnect.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put("name", userInfo.getUserName());
        contentValues.put("password", userInfo.getUserPwd());
        int id = db.update
                ("userinfo", contentValues, "'" + userInfo.getUserName() + "'=?",
                        new String[]{userInfo.getUserName()});
        return id != FAIL_OPERATE;
    }

    public boolean query(ArrayList<String> userInfos) {//其实这里传个userInfo可以更好，不过没用过集合试下
        String userName = userInfos.get(0);
        String userPwd = userInfos.get(1);
        db = sqlConnect.getReadableDatabase();
        cursor = db.query("userinfo", null, "'" + userName + "'=?",
                new String[]{userName}, null, null, null);
        //字符串搜索要用''，不然会报错
        if (cursor.moveToNext()) {
            //@SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String pwd =
                    cursor.getString(cursor.getColumnIndex("passWord"));
            return userPwd.equals(pwd);
        } else {
            userInfos.add("没找到");
            return false;
        }

    }
    //public boolean deleteUserInfo(UserInfo userInfo){
    //return true;
    // }
}

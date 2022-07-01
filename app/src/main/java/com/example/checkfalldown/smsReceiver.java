package com.example.checkfalldown;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class smsReceiver extends BroadcastReceiver {

    @SuppressLint("SetTextI18n")
    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] objects = (Object[]) intent.getExtras().get("pdus");
        String format = intent.getStringExtra("format");
        for (Object sms_obj : objects) {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms_obj, format);//新方法
            String sms_sender = smsMessage.getOriginatingAddress();//短信联系人
            String sms_body = smsMessage.getMessageBody();//短信内容
            Date dNow = new Date();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat ft
                    = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            System.out.println("当前时间为: " + ft.format(dNow));
            String sms_time = ft.format(dNow);
            SmsInfo smsinfo = new SmsInfo(sms_sender, sms_body, sms_time);
            sqlDao sqlDao = new sqlDao(context);
            boolean isInsert = sqlDao.insertSmsInfo(smsinfo);
            if (isInsert) {
                System.out.println("sms is stored");
            }
            intent = new Intent(context, MainActivity_view.class);
            intent.putExtra("sms_sender", sms_sender);
            intent.putExtra("sms_body", sms_body);
            intent.putExtra("sms_time", sms_time);
            Toast.makeText(context, "有信息了,信息已储存", Toast.LENGTH_SHORT).show();
            abortBroadcast();
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //这里的intent被重写了，不加会出现异常，这行代码正常使用context.startActivity而不会异常
        //Toast.makeText(context,"tt",Toast.LENGTH_SHORT).show();

        context.startActivity(intent);
    }
}
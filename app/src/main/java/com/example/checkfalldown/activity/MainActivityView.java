package com.example.checkfalldown.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.checkfalldown.R;
import com.example.checkfalldown.dao.SqlDao;
import com.example.checkfalldown.entity.OldManInfo;
import com.example.checkfalldown.entity.SmsInfo;
import com.example.checkfalldown.entity.UserInfo;
import com.example.checkfalldown.map.MapActivity;

import java.util.List;

public class MainActivityView extends AppCompatActivity {
    //SmsReserve smsReserve;
    private final String[] starArray = {"菜单", "修改密码 & 绑定老人信息"};
    TextView textView;
    Button buttonEmergencyContact;
    Button buttonContactOld;
    UserInfo userInfo;
    ImageView imageView;

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        textView = findViewById(R.id.textView8);
        buttonEmergencyContact = findViewById(R.id.button4);
        imageView = findViewById(R.id.imageView2);
        buttonContactOld = findViewById(R.id.button_contact);
        Button showMap = findViewById(R.id.button_showMap);

        showMap.setOnClickListener(this::showMap);
        buttonEmergencyContact.setOnClickListener(this::EmergencyContact);
        buttonContactOld.setOnClickListener(this::ContactOld);

        Intent intent = getIntent();//获取intent
        String sms_sender = intent.getStringExtra("sms_sender");
        String sms_body = intent.getStringExtra("sms_body");
        String sms_time = intent.getStringExtra("sms_time");
        userInfo = intent.getParcelableExtra("userInfo");
        System.out.println(sms_sender + sms_body);
        initSpinner();
        System.out.println("len" + textView.length());
        if (sms_sender == null && sms_body == null) {
            SqlDao sqlDao = new SqlDao(MainActivityView.this);
            List<SmsInfo> smsInfos = sqlDao.querySmsInfo();
            System.out.println("sms is empty" + smsInfos.isEmpty());
            System.out.println(smsInfos);
            System.out.println(smsInfos.size());
            if (smsInfos.isEmpty()) {
                textView.setText(userInfo.getUserName() + "，还没有收到消息哦");
                imageView.setImageDrawable(getDrawable(R.drawable.resource__safe));
                imageView.setBackgroundColor(R.color.green);
            } else {
                SmsInfo smsInfo = smsInfos.get(0);
                textView.setText(smsInfo.getSms_sender() +
                        ":" + smsInfo.getSms_body() + "\n" + smsInfo.getSms_time());
                imageView.setImageDrawable(getDrawable(R.drawable.resource__sos));
                imageView.setBackgroundColor(R.color.red);
            }
        } else {
            textView.setText(sms_sender + ":" + sms_body + "\n" + sms_time);
        }
    }

    public void EmergencyContact(View view) {
        Uri uri = Uri.parse("tel:120");
        Intent intentToContactEmergency = new Intent(Intent.ACTION_DIAL, uri);
        startActivity(intentToContactEmergency);
    }

    public void showMap(View view) {
        Intent showMap = new Intent(MainActivityView.this, MapActivity.class);
        startActivity(showMap);
    }

    public void ContactOld(View view) {
        SqlDao sqlDao = new SqlDao(MainActivityView.this);
        List<OldManInfo> oldManInfos = sqlDao.queryOldManInfo();
        System.out.println(oldManInfos.toString());
        int i = 0;
        if (i < oldManInfos.size()) {
            System.out.println(oldManInfos.get(oldManInfos.size() - 1));
            OldManInfo oldManInfo = oldManInfos.get(oldManInfos.size() - 1);
            System.out.println(oldManInfo.getOldManContact());
            Uri uri = Uri.parse("tel:" + oldManInfo.getOldManContact());
            Intent intentToContactEmergency = new Intent(Intent.ACTION_DIAL, uri);
            startActivity(intentToContactEmergency);
        } else {
            Toast.makeText
                    (MainActivityView.this, "请先添加老人信息", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables", "ResourceAsColor"})
    protected void onNewIntent(Intent intent) {//singleTop被再次调用时会执行这个方法
        super.onNewIntent(intent);
        setIntent(intent);//必须要写
        textView = findViewById(R.id.textView8);
        //而我们在其他生命周期来获取intent值时null，所以要在onNewIntent方法获取传递回来的intent值在做其他操作；
        intent = getIntent();//获取多个intent
        String sms_sender = intent.getStringExtra("sms_sender");
        String sms_body = intent.getStringExtra("sms_body");
        String sms_time = intent.getStringExtra("sms_time");
        System.out.println(sms_sender + sms_body);
        imageView.setImageDrawable(getDrawable(R.drawable.resource__sos));
        imageView.setBackgroundColor(R.color.red);
        textView.setText(sms_sender + ":" + sms_body + "\n" + sms_time);
    }

    private void initSpinner() {
        //声明一个下拉列表的数组适配器
        ArrayAdapter<String> starAdapter =
                new ArrayAdapter<>(this, R.layout.item_select, starArray);
        //设置数组适配器的布局样式
        starAdapter.setDropDownViewResource(R.layout.item_dropdown);
        //从布局文件中获取名叫sp_dialog的下拉框
        Spinner sp = findViewById(R.id.spinner);
        //设置下拉框的标题，不设置就没有难看的标题了
        sp.setPrompt("菜单");
        //设置下拉框的数组适配器
        sp.setAdapter(starAdapter);
        //设置下拉框默认的显示第一项
        sp.setSelection(0);
        //给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
        sp.setOnItemSelectedListener(new MySelectedListener());
    }

    class MySelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (i == 1) {
                Intent intent =
                        new Intent(MainActivityView.this, MainActivityChangeInfo.class);
                intent.putExtra("userInfo", userInfo);
                startActivity(intent);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

}
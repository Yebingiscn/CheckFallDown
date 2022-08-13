package com.example.checkfalldown;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class mapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
    }
}
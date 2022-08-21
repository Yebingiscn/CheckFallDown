package com.example.checkfalldown.map;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.example.checkfalldown.R;
import com.example.checkfalldown.entity.Location;

public class MapActivity extends AppCompatActivity {
    double latitude = 23.0;
    double longitude = 112.0;
    MapInitialize mapInitialize;
    private MapView mapView = null;

    public MapActivity() {
        mapInitialize = new MapInitialize();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapView = findViewById(R.id.bmapView);
        Location location = Location.getLocation();
        // 将GPS设备采集的原始GPS坐标转换成百度坐标

        //定位初始化
        try {
            LocationClient locationClient = new LocationClient(this);
            //通过LocationClientOption设置LocationClient相关参数
            LocationClientOption option = new LocationClientOption();
            option.setOpenGps(true); // 打开gps
            option.setCoorType("bd09ll"); // 设置坐标类型
            option.setScanSpan(1000);
            //设置locationClientOption
            locationClient.setLocOption(option);

            //注册LocationListener监听器
            LocationListener myLocationListener = new LocationListener();
            locationClient.registerLocationListener(myLocationListener);
            //开启地图定位图层
            locationClient.start();
            if (location != null) {
                latitude = Double.parseDouble(location.getLocationE());
                longitude = Double.parseDouble(location.getLocationN());
            }
            // sourceLatLng待转换坐标
            LatLng ll = new LatLng(latitude, longitude);
            MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(ll);
            // mBaiduMap.setMapStatus(status);//直接到中间
            BaiduMap baiduMap = myLocationListener.baiduMap;
            baiduMap.animateMapStatus(status);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
    }
}
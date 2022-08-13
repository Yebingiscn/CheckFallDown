package com.example.checkfalldown.internet;

import com.example.checkfalldown.entity.OldManInfo;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class upLoadData {
    public void sendDataToServer() {
        new Thread(() -> {
            OldManInfo oldManInfo = new OldManInfo();
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("oldManName", oldManInfo.getOldManName())
                    .add("oldManStatus", oldManInfo.getOldManStatus())
                    .add("oldManLocation", oldManInfo.getOldManLocation())
                    .add("oldManContact", oldManInfo.getOldManContact())
                    .build();
            Request request = new Request.Builder()
                    .url("http://localhost:8080/api/page/Client-add-alerts")
                    .post(requestBody)
                    .build();
            try {
                client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

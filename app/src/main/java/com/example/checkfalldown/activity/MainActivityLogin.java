package com.example.checkfalldown.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.checkfalldown.R;
import com.example.checkfalldown.agreement.AboutUs;
import com.example.checkfalldown.agreement.PrivateAgreement;
import com.example.checkfalldown.dao.SqlDao;
import com.example.checkfalldown.entity.UserInfo;

import java.util.ArrayList;

public class MainActivityLogin extends AppCompatActivity {

    //private static final String TAG = "权限没拿到";
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    EditText editTextTextPersonName;
    EditText editTextTextPassword;
    Button buttonAcknowledge;
    UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        buttonAcknowledge = findViewById(R.id.button2);
        requestPermission();//申请权限
        buttonAcknowledge.setOnClickListener(view -> {
            String userName = editTextTextPersonName.getText().toString().trim();
            String userPwd = editTextTextPassword.getText().toString().trim();
            System.out.println(userName + userPwd);
            boolean noNullCheck = noNullCheck(userName, userPwd);
            System.out.println(noNullCheck);
            if (noNullCheck) {
                loginProgress(userName, userPwd);
            }
        });
    }

    private void requestPermission() {//申请权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            //Log.i(TAG,"用户没用此权限");
            ActivityCompat.requestPermissions
                    (this, new String[]{Manifest.permission.RECEIVE_SMS}, 1);
        }
    }

    //  根据用户的选项做出相应
    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length <= 0
                    || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                //Log.i(TAG, "用户没有给予权限");
                showWaringDialog();
            }
        }
    }

    private void showWaringDialog() {//不确认就退出
        new AlertDialog.Builder(this)
                .setTitle("警告！")
                .setMessage("请前往设置->应用->PermissionDemo->权限中打开相关权限，否则功能无法正常运行！")
                .setPositiveButton("确定", (dialog1, which) -> {
                    // 一般情况下如果用户不授权的话，功能是无法运行的，做退出处理
                    finish();
                }).show();
    }

    public boolean noNullCheck(String userName, String userPwd) {//判断用户输入的是不是空的
        if ("".equals(userName) && "".equals(userPwd)) {
            Toast.makeText(MainActivityLogin.this,
                    "不能为空", Toast.LENGTH_SHORT).show();
            editTextTextPersonName.setText("");
            editTextTextPassword.setText("");
            return false;
        } else return userName.length() <= 20 && userPwd.length() <= 20;
    }

    public void loginProgress(String userName, String userPwd) {
        userInfo = new UserInfo();//封装
        userInfo.setUserName(userName);
        userInfo.setUserPwd(userPwd);
        ArrayList<String> userinfo = new ArrayList<>();//转到集合里准备插入
        userinfo.add(userName);
        userinfo.add(userPwd);
        SqlDao sqlDao = new SqlDao(MainActivityLogin.this);
        System.out.println(sqlDao.hashCode());
        boolean isRegister = sqlDao.insertUserInfo(userInfo);
        if (isRegister) {
            //数据库插入成功，是第一次注册
            successLogin();
        } else {
            boolean isLogin = sqlDao.query(userinfo);
            if (!isLogin) {
                //数据库里有数据但是对不上
                Toast.makeText
                        (MainActivityLogin.this,
                                "用户名或密码错误", Toast.LENGTH_SHORT).show();
            } else successLogin();//密码和用户名对的上
        }

    }

    public void successLogin() {
        Toast.makeText(MainActivityLogin.this,
                "欢迎" + userInfo.getUserName(), Toast.LENGTH_SHORT).show();
        Intent intent =
                new Intent(MainActivityLogin.this, MainActivityView.class);
        intent.putExtra("userInfo", userInfo);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //add的参数（菜单项的组号，ID，排序号，标题）
        menu.add(1, 1, 1, "关于我们");
        menu.add(1, 2, 2, "隐私政策");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent openAgreementIntent;
        switch (item.getItemId()) {
            case 1:
                openAgreementIntent =
                        new Intent(MainActivityLogin.this, AboutUs.class);
                startActivity(openAgreementIntent);
                break;
            case 2:
                openAgreementIntent =
                        new Intent(MainActivityLogin.this, PrivateAgreement.class);
                startActivity(openAgreementIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
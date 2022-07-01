package com.example.checkfalldown;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity_changePwd extends AppCompatActivity {
    TextView userName;
    EditText EditTextnewPwd;
    EditText EditeTextnewPwdRepeat;
    Button isSubmit;

    //private final String[] starArray = {"设置","主页","修改密码"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_change_pwd);
        userName = findViewById(R.id.textView4);
        EditTextnewPwd = findViewById(R.id.editTextTextPassword3);
        EditeTextnewPwdRepeat = findViewById(R.id.editTextTextPassword4);
        isSubmit = findViewById(R.id.button3);
        Intent intent = getIntent();
        UserInfo userInfo = intent.getParcelableExtra("userInfo");
        if (userInfo == null) {
            Intent intent1 = new Intent(this, MainActivity_login.class);
            Toast.makeText(MainActivity_changePwd.this,
                    "尚未登录", Toast.LENGTH_SHORT).show();
            startActivity(intent1);
        } else {
            userName.setText(userInfo.getUserName());
            //initSpinner();
            isSubmit.setOnClickListener(view -> {
                String newPwd = EditTextnewPwd.getText().toString().trim();
                String newPwdRepeat = EditeTextnewPwdRepeat.getText().toString().trim();
                if (isEqual(newPwd, newPwdRepeat)) {
                    userInfo.setUserPwd(newPwd);
                    sqlDao sqlDao = new sqlDao(MainActivity_changePwd.this);
                    boolean isupDate = sqlDao.updateUserInfo(userInfo);
                    if (isupDate) {
                        Toast.makeText(MainActivity_changePwd.this,
                                "修改成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity_changePwd.this,
                                "修改失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    public boolean isEqual(String newPwd, String newPwdRepeat) {//这里其实可以在UserInfo里完成
        if ("".equals(newPwd) && "".equals(newPwdRepeat)) {
            Toast.makeText(MainActivity_changePwd.this,
                    "不能为空", Toast.LENGTH_SHORT).show();
            EditTextnewPwd.setText("");
            EditeTextnewPwdRepeat.setText("");
            return false;
        } else if (!newPwd.equals(newPwdRepeat)) {
            return false;
        } else return newPwd.length() <= 20 && newPwdRepeat.length() <= 20;
    }
//    private void initSpinner(){
//        //声明一个下拉列表的数组适配器
//        ArrayAdapter<String> starAdapter = new ArrayAdapter<String>(this,R.layout.item_select,starArray);
//        //设置数组适配器的布局样式
//        starAdapter.setDropDownViewResource(R.layout.item_dropdown);
//        //从布局文件中获取名叫sp_dialog的下拉框
//        Spinner sp = findViewById(R.id.spinner);
//        //设置下拉框的标题，不设置就没有难看的标题了
//        sp.setPrompt("设置");
//        //设置下拉框的数组适配器
//        sp.setAdapter(starAdapter);
//        //设置下拉框默认的显示第一项
//        sp.setSelection(0);
//        //给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
//        sp.setOnItemSelectedListener(new MySelectedListener());
//    }
//
//    class MySelectedListener implements AdapterView.OnItemSelectedListener{
//
//        @Override
//        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//            //Toast.makeText(MainActivity_changePwd.this,"您选择的是："+starArray[i],Toast.LENGTH_SHORT).show();
//            if (i == 1){
//                Intent intent = new Intent(MainActivity_changePwd.this,MainActivity_view.class);
//                startActivity(intent);
//            }
//        }
//
//        @Override
//        public void onNothingSelected(AdapterView<?> adapterView) {
//
//        }
//    }
}
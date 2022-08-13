package com.example.checkfalldown.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.checkfalldown.R;
import com.example.checkfalldown.dao.SqlDao;
import com.example.checkfalldown.entity.UserInfo;

public class MainActivityChangeInfo extends AppCompatActivity {
    TextView userName;
    EditText EditTextNewPwd;
    EditText EditTextNewPwdRepeat;
    EditText EditTextOldName;
    EditText EditTextOldContact;
    Button isSubmitNewPwd;
    Button isSubmitOldManInfo;
    UserInfo userInfo;


    //private final String[] starArray = {"设置","主页","修改密码"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_change_info);
        userName = findViewById(R.id.textView4);
        EditTextNewPwd = findViewById(R.id.editTextTextPassword3);
        EditTextNewPwdRepeat = findViewById(R.id.editTextTextPassword4);
        isSubmitNewPwd = findViewById(R.id.button3);
        EditTextOldName = findViewById(R.id.oldManNameEv);
        EditTextOldContact = findViewById(R.id.oldManContactEv);
        isSubmitOldManInfo = findViewById(R.id.submitOldManInfo);
        Intent intent = getIntent();
        UserInfo userInfo = intent.getParcelableExtra("userInfo");
        if (userInfo == null) {
            Intent intent1 = new Intent(this, MainActivityLogin.class);
            Toast.makeText(MainActivityChangeInfo.this,
                    "尚未登录", Toast.LENGTH_SHORT).show();
            startActivity(intent1);
        } else {
            userName.setText(userInfo.getUserName());
            //initSpinner();
            isSubmitNewPwd.setOnClickListener(this::changePwdOnClick);
        }
    }

    public void changePwdOnClick(View view) {
        String newPwd = EditTextNewPwd.getText().toString().trim();
        String newPwdRepeat = EditTextNewPwdRepeat.getText().toString().trim();
        if (isEqual(newPwd, newPwdRepeat)) {
            userInfo.setUserPwd(newPwd);
            SqlDao sqlDao = new SqlDao(MainActivityChangeInfo.this);
            boolean isupDate = sqlDao.updateUserInfo(userInfo);
            if (isupDate) {
                Toast.makeText(MainActivityChangeInfo.this,
                        "修改成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivityChangeInfo.this,
                        "修改失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean isEqual(String newPwd, String newPwdRepeat) {//这里其实可以在UserInfo里完成
        if ("".equals(newPwd) && "".equals(newPwdRepeat)) {
            Toast.makeText(MainActivityChangeInfo.this,
                    "不能为空", Toast.LENGTH_SHORT).show();
            EditTextNewPwd.setText("");
            EditTextNewPwdRepeat.setText("");
            return false;
        } else if (!newPwd.equals(newPwdRepeat)) {
            return false;
        } else return (newPwd.length() <= 20 && newPwdRepeat.length() <= 20);
    }
}
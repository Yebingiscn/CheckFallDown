package com.example.checkfalldown.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfo implements Parcelable {//这个接口可以实现intent传递构造方法
    String userName;
    String userPwd;

    protected UserInfo(Parcel in) {
        userName = in.readString();
        userPwd = in.readString();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    public UserInfo() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {//使用的时候需要顺序设置
        parcel.writeString(userName);
        parcel.writeString(userPwd);
    }
}

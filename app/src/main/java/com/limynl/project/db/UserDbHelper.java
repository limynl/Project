package com.limynl.project.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.limynl.project.entity.UserInfo;


public class UserDbHelper {
    private static UserDbHelper instance;
    private SharedPreferences sharedPreferences;

    private UserDbHelper(Context context){
        sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
    }

    public static void setInstance(Context context){
        instance = new UserDbHelper(context);
    }

    public static UserDbHelper getInstance(){
        return instance;
    }

    /**
     * 保存用户的信息到当前的数据库
     */
    public void saveUserLoginInfo(UserInfo userInfo){
        saveStringConfig("username", userInfo.getUsername());
        saveStringConfig("phoneNum", userInfo.getPhone());
        saveIntegerConfig("userId", userInfo.getId()==null ?0:Integer.parseInt(userInfo.getId()));
        saveIntegerConfig("sex", userInfo.getSex());//0女 1男 -1未知
    }

    /**
     * 得到用户信息
     */
    public UserInfo getUserInfo(){
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(getStringConfig("phoneNum"));
        userInfo.setId(getInegerConfig("userId")+"");
        userInfo.setUsername(getStringConfig("username"));
        userInfo.setSex(getInegerConfig("sex"));
        return userInfo;
    }

    public void clearUserInfo(){

    }

    /**
     * 保存用户的登录状态
     */
    public void saveLoginState(boolean loginState){
        saveBooleanConfig("loginState", loginState);
    }

    /**
     * 得到用户的登录状态
     */
    public boolean getLoginState(){
        return getBooleanConfig("loginState");
    }

    /**
     * 保存String类型数据
     */
    public void saveStringConfig(String key, String value){
        sharedPreferences.edit().putString(key, value).commit();
    }

    /**
     * 保存boolean类型数据
     */
    public void saveBooleanConfig(String key, boolean value){
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    /**
     * 保存Integer类型数据
     */
    public void saveIntegerConfig(String key, int value){
        sharedPreferences.edit().putInt(key, value).commit();
    }

    /**
     * 得到String类型的数据
     */
    public String getStringConfig(String key){
        return sharedPreferences.getString(key, "");
    }

    /**
     * 得到Boolean类型的数据
     */
    public boolean getBooleanConfig(String key){
        return sharedPreferences.getBoolean(key, true);
    }

    /**
     * 得到integer类型的数据
     */
    public int getInegerConfig(String key){
        return sharedPreferences.getInt(key, -1);
    }
}

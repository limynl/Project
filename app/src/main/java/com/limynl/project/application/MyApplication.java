package com.limynl.project.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MyApplication extends Application{
    private static MyApplication myApplication;
    public static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
         myApplication = this;
        //test
        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getAppContext() {
        return myApplication == null ? myApplication.getApplicationContext() : myApplication;
    }

    public static RequestQueue getRequestQueue(){
        return requestQueue;
    }
}
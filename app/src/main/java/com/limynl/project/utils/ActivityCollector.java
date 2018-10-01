package com.limynl.project.utils;

import android.app.Activity;

import java.util.HashMap;
import java.util.Set;


public class ActivityCollector {

    private static HashMap<String, Activity> mActivityList = new HashMap<String, Activity>();

    public static void addActivity(String activityName, Activity activity){
        mActivityList.put(activityName, activity);
    }

    public static void removeActivity(String activityName){
        mActivityList.remove(activityName);
    }

    public static Activity getActivity(String activityName){
        return mActivityList.get(activityName);
    }

    public static void finishActivity(String activityName){
        if(!mActivityList.containsKey(activityName)){
            return;
        }
        Activity activity = mActivityList.get(activityName);
        if(!activity.isFinishing()){
            activity.finish();
        }
        mActivityList.remove(activityName);
    }

    public static void finishAll(){
        Set<String> keys = mActivityList.keySet();
        for(String key : keys){
            Activity activity = mActivityList.get(key);
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}

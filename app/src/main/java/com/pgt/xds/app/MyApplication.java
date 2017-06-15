package com.pgt.xds.app;

import android.app.Activity;
import android.app.Application;
import android.content.res.TypedArray;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.pgt.xds.R;
import com.pgt.xds.utils.AppContextUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ys on 2017/6/8.
 */

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    private static MyApplication instance;
    public static List<Activity> activitiesList = new ArrayList<Activity>(); // 活动管理集合
    public static Map<String, Integer> mMap = new HashMap<>();

    /**
     * 获取单例
     *
     * @return MyApplication
     */
    public static MyApplication newInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        setWeather();
        AppContextUtil.init(this);
    }

    /**
     * 把活动添加到活动管理集合
     *
     * @param activity
     */
    public void addActyToList(Activity activity) {
        if (!activitiesList.contains(activity))
            activitiesList.add(activity);
    }

    /**
     * 把活动从活动管理集合移除
     *
     * @param activity
     */
    public void removeActyFromList(Activity activity) {
        if (activitiesList.contains(activity))
            activitiesList.remove(activity);
    }

    /**
     * 程序退出
     */
    public void clearAllActies() {
        for (Activity acty : activitiesList) {
            if (acty != null)
                acty.finish();
        }

    }

    public RequestManager getGlide() {
        return Glide.with(this);

    }

    private void setWeather() {
        mMap.clear();
        String[] weather = getResources().getStringArray(R.array.weather);
        TypedArray ar = getResources().obtainTypedArray(R.array.actions_images);
        for (int i = 0; i < weather.length; i++) {
            mMap.put(weather[i], ar.getResourceId(i, 0));

        }
    }

    public int getWeather(String key) {
        return mMap.get(key);
    }

}

package com.pgt.xds.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.pgt.xds.R;
import com.pgt.xds.utils.AppContextUtil;
import com.pgt.xds.utils.Toastor;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.utils.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
    public Toastor toastor;

    {
        Config.DEBUG = true;
        PlatformConfig.setWeixin("wx065c4495e3596491", "d765ae0ca3be6317385a954dece21e08");
        PlatformConfig.setQQZone("1106248858", "AIL87ZxcZ48yKBVh");
        PlatformConfig.setSinaWeibo("2765558393", "2260c8e15bc8c64b8c25042692b64679", "https://api.weibo.com/oauth2/default.html");
    }

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
        toastor = new Toastor(instance);
        setWeather();
        UMShareAPI.get(this);
        AppContextUtil.init(this);
        initEM();
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

    private void initEM() {
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回
        if (processAppName == null || !processAppName.equalsIgnoreCase(instance.getPackageName())) {
            Log.e(TAG, "enter the service process!");
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);

        //初始化
        EMClient.getInstance().init(instance, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);

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

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
}

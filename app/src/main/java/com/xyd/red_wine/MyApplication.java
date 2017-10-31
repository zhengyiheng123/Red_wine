package com.xyd.red_wine;

import android.app.Application;
import android.content.Context;
import android.support.v4.util.LogWriter;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.common.QueuedWork;
import com.xyd.red_wine.base.PublicStaticData;
import com.xyd.red_wine.utils.FileUtils;
import com.xyd.red_wine.utils.LogUtil;
import com.xyd.red_wine.utils.MyCrashHandler;
import com.xyd.red_wine.utils.Utils;

import static android.R.attr.data;
import static android.R.attr.track;

/**
 * @author: zhaoxiaolei
 * @date: 2017/6/12
 * @time: 11:48
 * @description:
 */

public class MyApplication extends Application {
    public static  Context context;
    private static  MyApplication application;
    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        refWatcher=  LeakCanary.install(this);


        application=this;
        context=this;
        Utils.init(this);
        UMShareAPI.get(this);
        QueuedWork.isUseThreadPool = false;
        Config.DEBUG = true;
        PublicStaticData.sharedPreferences=getSharedPreferences("wine",MODE_PRIVATE);
        initShare();
        EMHelper.getInstance().init(this);
        initLog();
    }

    private void initLog() {
        MyCrashHandler crashHandler=new MyCrashHandler();
        crashHandler.init(getApplicationContext());
    }

    private void initShare() {
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        config.isSinaAuthWithWebView();
        config.setSinaAuthType(UMShareConfig.AUTH_TYPE_SSO);
        UMShareAPI.get(this).setShareConfig(config);

        PlatformConfig.setWeixin("wxdb713d24bde112f2","5312576688a2a85b0d104586a0b6deb8");
        PlatformConfig.setQQZone("1106221519","Omc31VQxqHWnFaNl");
        PlatformConfig.setSinaWeibo("1866127300","a2222661f38e596049e64e660ae569f4","http://open.weibo.com/apps/1866127300/privilege/oauth");
    }
    public static MyApplication getInstance(){
       return application;
    }
    public  static Context getContext(){
        return context;
    }

    public static RefWatcher getRefWatcher(Context context) {
        MyApplication application = (MyApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;

}

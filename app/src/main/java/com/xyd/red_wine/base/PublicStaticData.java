package com.xyd.red_wine.base;
import android.app.Activity;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;


/**
 * @author: zhaoxiaolei
 * @date: 2017/5/2
 * @time: 16:25
 * @description:
 */

public  class PublicStaticData {
    public static SharedPreferences sharedPreferences;
    public static Boolean isSDCard;
    public static String outDir;

//    public static  String baseUrl="http://hj.jiangliping.com";
    public static String baseUrl="http://app.george-wine.com";
    public static List<Activity>  activityList=new ArrayList<>();
    public static List<String>  logString=new ArrayList<>();
    public static void closeAllActivity(){
        for (Activity activity: activityList){
            if (!activity.isFinishing())
                activity.finish();
        }
    }
}

package com.xyd.red_wine.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.xyd.red_wine.suggest.image.ImageModel;
import com.xyd.red_wine.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * @author: zhaoxiaolei
 * @date: 2017/4/26
 * @time: 15:05
 * @description: 基础类
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean isTest = true;
    protected final String TAG = this.getClass().getSimpleName();
    public static List<Activity> openActivity = new ArrayList<>();

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void showTestToast(String msg) {
        if (isTest) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 退出时关闭所有界面
     */
    public void closeApp() {
        for (Activity activity:openActivity){
            activity.finish();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        openActivity.add(this);
        setStatusBar();
        ButterKnife.bind(this);
        ImageView imageView;

        initView();
        initEvent();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        openActivity.remove(this);
    }

    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initEvent();


    /**
     * View点击
     **/
    public abstract void widgetClick(View v);

    @Override
    public void onClick(View v) {
        if (fastClick())
            widgetClick(v);
    }

    /**
     * [防止快速点击]
     *
     * @return
     */
    private boolean fastClick() {
        long lastClick = 0;
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


}

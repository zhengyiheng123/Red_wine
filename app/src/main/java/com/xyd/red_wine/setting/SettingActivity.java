package com.xyd.red_wine.setting;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.xyd.red_wine.R;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.cookie.PersistentCookieStore;
import com.xyd.red_wine.login.LoginActivity;
import com.xyd.red_wine.modification.ModificationActivity;
import com.xyd.red_wine.permissions.PermissionUtils;
import com.xyd.red_wine.permissions.PermissionsManager;
import com.xyd.red_wine.utils.AppUtils;
import com.xyd.red_wine.version.VersionUpdateHelper;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/7
 * @time: 16:26
 * @description: 设置界面
 */

public class SettingActivity extends BaseActivity {
    @Bind(R.id.base_title_back)
    TextView baseTitleBack;
    @Bind(R.id.base_title_title)
    TextView baseTitleTitle;
    @Bind(R.id.base_title_menu)
    ImageView menu;
    @Bind(R.id.setting_tv_push)
    TextView settingTvPush;
    @Bind(R.id.setting_switch_push)
    Switch settingSwitchPush;
    @Bind(R.id.setting_tv_cache)
    TextView settingTvCache;
    @Bind(R.id.setting_switch_cache)
    Switch settingSwitchCache;
    @Bind(R.id.setting_tv_updata)
    TextView settingTvUpdata;
    @Bind(R.id.setting_tv_quit)
    TextView settingTvQuit;
    @Bind(R.id.setting_tv_code)
    TextView settingTvCode;
    @Bind(R.id.setting_tv_modification)
    TextView settingTvModificaation;
    @Bind(R.id.base_title_headline)
    ImageView mHeadLine;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        mHeadLine.setVisibility(View.GONE);
        menu.setVisibility(View.INVISIBLE);
        baseTitleTitle.setText("设置");
        AppUtils.getAppVersionCode(this);
        settingTvCode.setText("v" + AppUtils.getAppVersionName(this));
    }

    @Override
    protected void initEvent() {

        baseTitleBack.setOnClickListener(this);
        settingTvUpdata.setOnClickListener(this);
        settingTvQuit.setOnClickListener(this);
        settingTvModificaation.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.base_title_back:
                finish();
                break;
            case R.id.setting_tv_updata:
//                PermissionUtils.storage(this, new PermissionUtils.OnPermissionResult() {
//                    @Override
//                    public void onGranted() {
//                        VersionUpdateHelper helper = new VersionUpdateHelper(SettingActivity.this);
//                        helper.setShowDialogOnStart(true);
//                        helper.setToastInfo(true);
//                        VersionUpdateHelper.resetCancelFlag();
//                        helper.startUpdateVersion();
//                    }
//                });

                break;
            case R.id.setting_tv_quit:
                // showTestToast("退出登陆");
                PersistentCookieStore store = new PersistentCookieStore(this);
                store.removeAll();
                closeApp();
                startActivity(LoginActivity.class);
                break;
            case R.id.setting_tv_modification:
                startActivity(ModificationActivity.class);
                break;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsManager.getInstance().notifyPermissionsChange(permissions,grantResults);
    }
}

package com.xyd.red_wine.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.Error;
import com.hyphenate.helpdesk.callback.Callback;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xyd.red_wine.R;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.base.PublicStaticData;
import com.xyd.red_wine.forgetpassword.ForgetPasswordActivity;
import com.xyd.red_wine.main.MainActivity;
import com.xyd.red_wine.promptdialog.PromptDialog;
import com.xyd.red_wine.register.RegisterActivity;
import com.xyd.red_wine.register.RegisterMessage;
import com.xyd.red_wine.utils.FileUtils;
import com.xyd.red_wine.utils.LogUtil;
import com.xyd.red_wine.utils.StatusBarUtil;
import com.xyd.red_wine.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/5
 * @time: 11:00
 * @description: 登陆
 */

public class LoginActivity extends BaseActivity implements LoginContract.View {

    @Bind(R.id.login_edt_user)
    EditText loginEdtUser;
    @Bind(R.id.login_edt_password)
    EditText loginEdtPassword;
    @Bind(R.id.login_cb)
    CheckBox loginCb;
    @Bind(R.id.login_tv_forget)
    TextView loginTvForget;
    @Bind(R.id.login_tv_register)
    TextView loginTvRegister;
    @Bind(R.id.login_jiubei)
    ImageView loginJiubei;
    @Bind(R.id.login_btn_login)
    Button loginBtnLogin;
    @Bind(R.id.login_tv_qq)
    TextView loginTvQq;
    @Bind(R.id.login_tv_wx)
    TextView loginTvWx;
    @Bind(R.id.login_tv_wb)
    TextView loginTvWb;
    @Bind(R.id.login_tv_qx)
    TextView loginTvQx;
    @Bind(R.id.login_tv_other)
    TextView loginTvOther;
    @Bind(R.id.login_ll_register)
    LinearLayout loginLlRegister;
    @Bind(R.id.login_rl_other)
    RelativeLayout loginRlOther;
    private LoginContract.Presenter presenter;
    private PromptDialog promptDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        String phoneNum= PublicStaticData.sharedPreferences.getString(LoginPresenter.PHONE_NUM,"");
        String password=PublicStaticData.sharedPreferences.getString(LoginPresenter.PASSWORD,"");
        loginEdtUser.setText(phoneNum);
        loginEdtPassword.setText(password);
        EventBus.getDefault().register(this);
//        if (Build.VERSION.SDK_INT >= 23) {
//            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE,
//                    Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE,
//                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP,
//                    Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS,
//                    Manifest.permission.WRITE_APN_SETTINGS};
//            ActivityCompat.requestPermissions(this, mPermissionList, 123);
//        }
        presenter = new LoginPresenter(this);
        promptDialog = new PromptDialog(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
    }

    @Override
    protected void initEvent() {
        loginTvRegister.setOnClickListener(this);
        loginTvForget.setOnClickListener(this);
        loginTvOther.setOnClickListener(this);
        loginTvQx.setOnClickListener(this);
        loginTvQq.setOnClickListener(this);
        loginTvWb.setOnClickListener(this);
        loginTvWx.setOnClickListener(this);
        loginBtnLogin.setOnClickListener(this);
        loginCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    loginEdtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                } else {
                    loginEdtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

    }


    @Subscribe
    public void onEvent(RegisterMessage message) {
        loginEdtUser.setText(message.phone);
        loginEdtPassword.setText(message.password);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.login_tv_register:
                startActivity(RegisterActivity.class);
                break;
            case R.id.login_tv_forget:
                startActivity(ForgetPasswordActivity.class);
                break;
            case R.id.login_tv_other:
                loginRlOther.setVisibility(View.VISIBLE);
                loginJiubei.setVisibility(View.INVISIBLE);
                loginLlRegister.setVisibility(View.INVISIBLE);
                break;
            case R.id.login_tv_qx:
                loginRlOther.setVisibility(View.INVISIBLE);
                loginJiubei.setVisibility(View.VISIBLE);
                loginLlRegister.setVisibility(View.VISIBLE);
                break;
            case R.id.login_tv_wb:
                if (isWeiboInstalled(getApplicationContext())){
                    presenter.loginWb(this);
                }else {
                    ToastUtils.show("微博未安装");
                }
                break;
            case R.id.login_tv_wx:
                if (!UMShareAPI.get(this).isInstall(this, SHARE_MEDIA.WEIXIN))
                    showTestToast("请先安装微信~");
                else
                    presenter.loginWx(this);
                break;
            case R.id.login_tv_qq:
                if (!UMShareAPI.get(this).isInstall(this, SHARE_MEDIA.QQ))
                    showTestToast("请先安装QQ~");
                else
                    presenter.loginQQ(this);
                break;
            case R.id.login_btn_login:
                presenter.login(loginEdtUser.getText().toString(),
                        loginEdtPassword.getText().toString());
                break;
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {

    }

    @Override
    public void error(final  String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToast(msg);
            }
        });


    }

    @Override
    public void success() {
        finish();
        startActivity(MainActivity.class);
    }

    @Override
    public void showDialog() {
        promptDialog.showLoading("正在登录", false);

    }

    @Override
    public void closeDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                promptDialog.dismissImmediately();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }


    @Override
    public void onBackPressed() {
        closeApp();
        super.onBackPressed();

    }
    public static boolean isWeiboInstalled(@NonNull Context context) {
        PackageManager pm;
        if ((pm = context.getApplicationContext().getPackageManager()) == null) {
            return false;
        }
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        for (PackageInfo info : packages) {
            String name = info.packageName.toLowerCase(Locale.ENGLISH);
            if ("com.sina.weibo".equals(name)) {
                return true;
            }
        }
        return false;
    }
}

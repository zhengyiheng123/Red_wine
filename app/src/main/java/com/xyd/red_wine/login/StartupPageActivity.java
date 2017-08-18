package com.xyd.red_wine.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;


import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;
import com.xyd.red_wine.R;
import com.xyd.red_wine.api.MineApi;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.PublicStaticData;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.main.MainActivity;
import com.xyd.red_wine.personinformation.InfromationModel;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/2
 * @time: 17:13
 * @description:
 */

public class StartupPageActivity extends Activity {
    @Bind(R.id.start_vp)
    ViewPager startVp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);
        initView();
    }


    protected void initView() {
        BaseApi.getRetrofit()
                .create(MineApi.class)
                .information()
                .compose(RxSchedulers.<BaseModel<InfromationModel>>compose())
                .subscribe(new BaseObserver<InfromationModel>() {
                    @Override
                    protected void onHandleSuccess(InfromationModel infromationModel, String msg, int code) {
                        PublicStaticData.sharedPreferences.edit().putInt("id", infromationModel.getUserid()).commit();
                      //  login("qiaozhijinhan" + infromationModel.getUserid(), "123456");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(StartupPageActivity.this, MainActivity.class));
                                finish();

                            }
                        }, 2000);
                    }

                    @Override
                    protected void onHandleError(String msg) {

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(StartupPageActivity.this, LoginActivity.class));
                                finish();

                            }
                        }, 2000);
                    }
                });

    }


    private void login(final String uname, final String upwd) {


        // login huanxin server
        ChatClient.getInstance().login(uname, upwd, new Callback() {
            @Override
            public void onSuccess() {
                Log.e("StartupPage", "demo login success!");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(StartupPageActivity.this, MainActivity.class));
                                finish();

                            }
                        }, 2000);
                    }
                });

            }

            @Override
            public void onError(int code, String error) {
                Log.e("", "login fail,code:" + code + ",error:" + error);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(StartupPageActivity.this, LoginActivity.class));
                                finish();

                            }
                        }, 2000);
                    }
                });

            }

            @Override
            public void onProgress(int progress, String status) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(StartupPageActivity.this, LoginActivity.class));
                                finish();

                            }
                        }, 2000);
                    }
                });

            }
        });
    }

}

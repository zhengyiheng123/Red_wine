package com.xyd.red_wine.login;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.Error;
import com.hyphenate.helpdesk.callback.Callback;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xyd.red_wine.api.LoginApi;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.PublicStaticData;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.utils.LogUtil;
import com.xyd.red_wine.utils.RegexUtils;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/19
 * @time: 16:18
 * @description:
 */

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View loginView;

    public LoginPresenter(LoginContract.View loginView) {
        this.loginView = loginView;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void login(String phone, String password) {
        if (!RegexUtils.isMobilePhoneNumber(phone))
            loginView.error("请输入正确的手机号");
        else if (!RegexUtils.isPWD(password))
            loginView.error("密码必须是6~12位且同时包含数字与字母");
        else
            login_(phone, password);

    }

    @Override
    public void loginQQ(Activity context) {
        UMShareAPI.get(context).doOauthVerify(context, SHARE_MEDIA.QQ, umAuthListener);
    }

    @Override
    public void loginWx(Activity context) {
        UMShareAPI.get(context).doOauthVerify(context, SHARE_MEDIA.WEIXIN, umAuthListener);
    }

    @Override
    public void loginWb(Activity context) {
        UMShareAPI.get(context).doOauthVerify(context, SHARE_MEDIA.SINA, umAuthListener);
    }

    public void loginTest(String phone, String password){

    }
    private void login_(String phone, String password) {
        loginView.showDialog();
        BaseApi.getRetrofit()
                .create(LoginApi.class)
                .login(phone, password)
                .compose(RxSchedulers.<BaseModel<LoginModel>>compose())
                .subscribe(new BaseObserver<LoginModel>() {
                    @Override
                    protected void onHandleSuccess(LoginModel loginModel, String msg, int code) {
                       // createRandomAccountThenLoginChatServer("qiaozhijinhan" + loginModel.getUserid(),"123456");
                       // loginHx("qiaozhijinhan" + loginModel.getUserid(), "123456");
                        PublicStaticData.sharedPreferences.edit().putString("head", loginModel.getHead_img()).commit();
                        PublicStaticData.sharedPreferences.edit().putString("signature", loginModel.getSignature()).commit();
                        PublicStaticData.sharedPreferences.edit().putString("nickname", loginModel.getNickname()).commit();
                        PublicStaticData.sharedPreferences.edit().putInt("id", loginModel.getUserid()).commit();
                        loginView.closeDialog();
                        loginView.success();
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        loginView.closeDialog();
                        loginView.error(msg);
                    }
                });

    }

    //三方登陆回调
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            LogUtil.e("开始");
        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            LogUtil.e("onComplete");
            String uid = "";//用户ID
            String name = "";//昵称
            String gender = "";//性别
            String iconurl = "";//头像

            if (SHARE_MEDIA.WEIXIN == share_media) {
                uid = map.get("unionid");//用户ID
                name = map.get("screen_name");//昵称
                gender = map.get("gender");//性别
                iconurl = map.get("profile_image_url");//头像
            } else {
                uid = map.get("uid");//用户ID
                name = map.get("name");//昵称
                gender = map.get("gender");//性别
                iconurl = map.get("iconurl");//头像
            }
            Log.e("", uid + "\n" + name + "\n"
                    + iconurl);
            uid = TextUtils.isEmpty(uid) ? "" : uid;
            name = TextUtils.isEmpty(name) ? "" : name;
            gender = TextUtils.isEmpty(gender) ? "" : gender;
            iconurl = TextUtils.isEmpty(iconurl) ? "" : iconurl;

            LogUtil.e(uid + "\n" + name + "\n" + gender + "\n" + iconurl);
            if (gender.equals("男"))
                loginThree(uid, name, "1", iconurl);
            else
                loginThree(uid, name, "0", iconurl);
        }

        @Override
        public void onError(SHARE_MEDIA platform, int i, Throwable throwable) {
            LogUtil.e("onError");
            String str_platform = "";
            if (SHARE_MEDIA.QQ == platform) {
                str_platform = "QQ";
            } else if (SHARE_MEDIA.WEIXIN == platform) {
                str_platform = "微信";
            }
            loginView.error(str_platform + "登录失败：" + throwable.toString());
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int i) {
            LogUtil.e("onCancel");
            String str_platform = "";
            if (SHARE_MEDIA.QQ == platform) {
                str_platform = "QQ";
            } else if (SHARE_MEDIA.WEIXIN == platform) {
                str_platform = "微信";
            }
            loginView.error(str_platform + "登录取消");
        }
    };


    private void loginThree(String uid, String name, String gender, String iconurl) {
        loginView.showDialog();
        BaseApi.getRetrofit()
                .create(LoginApi.class)
                .tripartite_login(name, gender, uid)
                .compose(RxSchedulers.<BaseModel<LoginModel>>compose())
                .subscribe(new BaseObserver<LoginModel>() {
                    @Override
                    protected void onHandleSuccess(LoginModel loginModel, String msg, int code) {
                       // createRandomAccountThenLoginChatServer("qiaozhijinhan" + loginModel.getUserid(),"123456");
                       // loginHx("qiaozhijinhan" + loginModel.getUserid(), "123456");
                        PublicStaticData.sharedPreferences.edit().putString("head", loginModel.getHead_img()).commit();
                        PublicStaticData.sharedPreferences.edit().putString("signature", loginModel.getSignature()).commit();
                        PublicStaticData.sharedPreferences.edit().putString("nickname", loginModel.getNickname()).commit();
                        PublicStaticData.sharedPreferences.edit().putInt("id", loginModel.getUserid()).commit();
                        loginView.closeDialog();
                        loginView.success();

                    }

                    @Override
                    protected void onHandleError(String msg) {
                        loginView.closeDialog();
                        loginView.error(msg);
                    }
                });
    }

    private void loginHx(final String uname, final String upwd) {


        // login huanxin server
        ChatClient.getInstance().login(uname, upwd, new Callback() {
            @Override
            public void onSuccess() {
                loginView.closeDialog();
                loginView.success();
            }

            @Override
            public void onError(int code, String error) {
                loginView.closeDialog();
                Log.e("hx",error);
                loginView.error("登录失败请重试"+error);

            }

            @Override
            public void onProgress(int progress, String status) {
                loginView.closeDialog();
                loginView.error("登录失败请重试");
            }
        });
    }
    private void createRandomAccountThenLoginChatServer(final String user,final String password) {
        // 自动生成账号,此处每次都随机生成一个账号,为了演示.正式应从自己服务器获取账号


        // createAccount to huanxin server
        // if you have a account, this step will ignore
        ChatClient.getInstance().createAccount(user, password, new Callback() {
            @Override
            public void onSuccess() {
                loginHx(user,password);
            }

            @Override
            public void onError(final int errorCode, String error) {
                loginView.closeDialog();
                loginView.error("登录失败请重试");
            }

            @Override
            public void onProgress(int progress, String status) {
                loginView.closeDialog();
                loginView.error("登录失败请重试");
            }
        });
    }
}

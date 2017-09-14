package com.xyd.red_wine.personinformation;

import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xyd.red_wine.R;
import com.xyd.red_wine.api.LoginApi;
import com.xyd.red_wine.api.MineApi;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.EmptyModel;
import com.xyd.red_wine.base.PublicStaticData;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.utils.ToastUtils;

import butterknife.Bind;

/**
 * Created by Zheng on 2017/8/22.
 */

public class BindActivity extends BaseActivity {
    @Bind(R.id.register_edt_user)
    EditText register_edt_user;
    @Bind(R.id.register_edt_password)
    EditText register_edt_password;
    @Bind(R.id.register_edt_password_ok)
    EditText register_edt_password_ok;
    @Bind(R.id.register_tv_code)
    TextView register_tv_code;
    @Bind(R.id.register_edt_code)
    EditText register_edt_code;
    @Bind(R.id.register_btn)
    TextView register_btn;
    private int downTime = 60;
    private int userId;
    @Bind(R.id.base_title_title)
    TextView mBaseTitle;
    @Bind(R.id.base_title_back)
    TextView mBaseback;
    @Bind(R.id.base_title_menu)
    ImageView mImageMenu;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_password;
    }

    @Override
    protected void initView() {
        mImageMenu.setVisibility(View.INVISIBLE);
        mBaseTitle.setText("绑定手机号");
        userId = PublicStaticData.sharedPreferences.getInt("id",0);
    }

    @Override
    protected void initEvent() {
        register_btn.setOnClickListener(this);
        register_tv_code.setOnClickListener(this);
        mBaseback.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.base_title_back:
                finish();
                break;
            case R.id.register_btn:
                if (TextUtils.isEmpty(register_edt_user.getText().toString())){
                    ToastUtils.show("请输入手机号");
                    return;
                }if (TextUtils.isEmpty(register_edt_code.getText().toString())){
                ToastUtils.show("请输入验证码");
                return;
            }
                bind(register_edt_user.getText().toString(),register_edt_code.getText().toString(),register_edt_password.getText().toString(),register_edt_password_ok.getText().toString());
                break;
            case R.id.register_tv_code:
                if (TextUtils.isEmpty(register_edt_user.getText().toString())){
                    ToastUtils.show("请输入手机号");
                    return;
                }
                if (downTime == 60){
                    code(register_edt_user.getText().toString());
                }
                break;
        }
    }
    private void code(String phone) {
        BaseApi.getRetrofit()
                .create(LoginApi.class)
                .sendCode(phone, 3)
                .compose(RxSchedulers.<BaseModel<EmptyModel>>compose())
                .subscribe(new BaseObserver<EmptyModel>() {
                    @Override
                    protected void onHandleSuccess(EmptyModel emptyModel, String msg, int code) {
//                        registerView.downTime();
                        ToastUtils.show(msg);
                        handler.postDelayed(runnable, 1000);
                    }

                    @Override
                    protected void onHandleError(String msg) {
//                        registerView.showError(msg);
                        ToastUtils.show(msg);
                    }
                });

    }
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (downTime > 0) {
                downTime--;
                register_tv_code.setText("还剩" + downTime + "秒");
                handler.postDelayed(this, 1000);
            } else {
                register_tv_code.setText("获取验证码");
                downTime = 60;
            }
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    //绑定手机号
    private void bind(String phone,String code,String password,String repassword){
                        BaseApi.getRetrofit()
                                .create(MineApi.class)
                                .bind(phone,code,password,repassword,userId)
                                .compose(RxSchedulers.<BaseModel>compose())
                                .subscribe(new BaseObserver() {
                                    @Override
                                    protected void onHandleSuccess(Object o, String msg, int code) {
                                        ToastUtils.show(msg);
                                        finish();
                                    }

                                    @Override
                                    protected void onHandleError(String msg) {
                                        ToastUtils.show(msg);
                                    }
                                });
    }
}

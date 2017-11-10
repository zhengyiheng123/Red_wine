package com.xyd.red_wine.personinformation;

import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xyd.red_wine.R;
import com.xyd.red_wine.api.LoginApi;
import com.xyd.red_wine.api.MineApi;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.EmptyModel;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.utils.ToastUtils;

import butterknife.Bind;

/**
 * Created by Zheng on 2017/8/22.
 */

public class ChangePhoneActivity extends BaseActivity {
    @Bind(R.id.register_edt_user)
    EditText register_edt_user;
    @Bind(R.id.register_edt_code)
    EditText register_edt_code;
    @Bind(R.id.register_tv_code)
    TextView register_tv_code;
    @Bind(R.id.register_btn)
    TextView register_btn;
    private int downTime = 60;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_phone;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {
        register_tv_code.setOnClickListener(this);
        register_btn.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.register_tv_code:
                if (TextUtils.isEmpty(register_edt_user.getText().toString())){
                    ToastUtils.show("请输入手机号");
                    return;
                }
                if (downTime == 60){
                    code(register_edt_user.getText().toString());
                }
                break;
            case R.id.register_btn:
                if (TextUtils.isEmpty(register_edt_user.getText().toString())){
                    ToastUtils.show("请输入手机号");
                    return;
                }
                if(TextUtils.isEmpty(register_edt_code.getText().toString())){
                    ToastUtils.show("请输入验证码");
                    return;
                }
                bind(register_edt_user.getText().toString(),register_edt_code.getText().toString());
                break;
        }
    }
    //获取验证码
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
                        handler.postDelayed(runnable, 0);
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
    //修改手机号
    private void bind(String phone,String code){
        BaseApi.getRetrofit()
                .create(MineApi.class)
                .edit_phone(phone,code)
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

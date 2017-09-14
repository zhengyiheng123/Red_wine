package com.xyd.red_wine.balance;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
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
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.utils.CashierInputFilter;
import com.xyd.red_wine.utils.RegexUtils;
import com.xyd.red_wine.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/9
 * @time: 11:02
 * @description: 提现
 */

public class TixianActivity extends BaseActivity {
    @Bind(R.id.base_title_back)
    TextView baseTitleBack;
    @Bind(R.id.base_title_title)
    TextView baseTitleTitle;
    @Bind(R.id.base_title_menu)
    ImageView baseTitleMenu;
    @Bind(R.id.tixian_edt)
    EditText tixianEdt;
    @Bind(R.id.tixian_alipay)
    CheckBox tixianAlipay;
    @Bind(R.id.tv_available_money)
    TextView tv_available_money;
    @Bind(R.id.tixian_edt_user)
    EditText tixianEdtUser;
    @Bind(R.id.chongzhi_num)
    TextView chongzhiNum;
    @Bind(R.id.tixian_btn)
    TextView tixianBtn;
    @Bind(R.id.register_tv_code)
    TextView forgetTvCode;

    @Bind(R.id.register_edt_code)
    EditText getCode;

    public static String AVAILAVLE_MONEY="available_money";
    public static String PHONENUM="phonenum";
    private Double available;
    private String phonenum;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tixian;
    }

    @Override
    protected void initView() {
        phonenum = getIntent().getStringExtra(PHONENUM);
        available=getIntent().getDoubleExtra(AVAILAVLE_MONEY,0);
        baseTitleTitle.setText("提现");
        baseTitleMenu.setVisibility(View.INVISIBLE);
        InputFilter[] is = {new CashierInputFilter(1)};
        tixianEdt.setFilters(is);
        tv_available_money.setText("可用余额："+available);

    }

    @Override
    protected void initEvent() {
        forgetTvCode.setOnClickListener(this);
        baseTitleBack.setOnClickListener(this);
        tixianBtn.setOnClickListener(this);
        tixianEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s))
                    chongzhiNum.setText("￥0.0");
                else chongzhiNum.setText("￥" + tixianEdt.getText());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.base_title_back:
                finish();
                break;
            case R.id.register_tv_code:

                if (downTime == 60){
                    if (TextUtils.isEmpty(phonenum)){
                        ToastUtils.show("手机号不能为空");
                    }
                    else{
                        getCode(phonenum);
                    }
                }
                break;
            case R.id.tixian_btn:
                if (TextUtils.isEmpty(tixianEdt.getText().toString())) {
                    showToast("请输入提现金额");

                } else if (TextUtils.isEmpty(tixianEdtUser.getText().toString())) {
                    showToast("请输入提现账号");
                }else if (Double.parseDouble(tixianEdt.getText().toString()) <10.00){
                    showToast("提现金额最少应大于10元");
                }

                else {
                    showTixianDialog();


                }
                break;

        }

    }

    private void showTixianDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TixianActivity.this);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                tixian();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setTitle("温馨提示");
        builder.setMessage("您输入的提现账号:" + tixianEdtUser.getText().toString());
        Dialog dialog = builder.create();
        dialog.show();
    }

    private void tixian() {
        BaseApi.getRetrofit()
                .create(MineApi.class)
                .ali_transfer(tixianEdt.getText().toString(), tixianEdtUser.getText().toString(),getCode.getText().toString())
                .compose(RxSchedulers.<BaseModel<EmptyModel>>compose())
                .subscribe(new BaseObserver<EmptyModel>() {
                    @Override
                    protected void onHandleSuccess(EmptyModel emptyModel, String msg, int code) {
                        showToast(msg);
                        finish();
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        showToast(msg);
                    }
                });
    }
    private int downTime = 60;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (downTime > 0) {
                downTime--;
                forgetTvCode.setText("还剩" + downTime + "秒");
                handler.postDelayed(this, 1000);
            } else {
                forgetTvCode.setText("重新获取验证码");
                downTime = 60;
            }
        }
    };

    /**
     *  获取验证码
     */
    private void getCode(String phone) {
        BaseApi.getRetrofit()
                .create(LoginApi.class)
                .sendCode(phone, 2)
                .compose(RxSchedulers.<BaseModel<EmptyModel>>compose())
                .subscribe(new BaseObserver<EmptyModel>() {
                    @Override
                    protected void onHandleSuccess(EmptyModel emptyModel, String msg, int code) {
                        ToastUtils.show(msg);
                        handler.postDelayed(runnable, 1000);
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        ToastUtils.show(msg);
                    }
                });
    }
}

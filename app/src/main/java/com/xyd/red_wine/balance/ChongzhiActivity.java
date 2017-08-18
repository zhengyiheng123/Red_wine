package com.xyd.red_wine.balance;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xyd.red_wine.R;
import com.xyd.red_wine.alipay.AliPay;
import com.xyd.red_wine.api.MineApi;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.commitorder.AliModel;
import com.xyd.red_wine.commitorder.CommitOrderActivity;
import com.xyd.red_wine.commitorder.FinshMessage;
import com.xyd.red_wine.commitorder.PayFinishActivity;
import com.xyd.red_wine.commitorder.WxModel;
import com.xyd.red_wine.utils.CashierInputFilter;
import com.xyd.red_wine.wxapi.WXPay;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/7
 * @time: 16:21
 * @description:
 */

public class ChongzhiActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    public static final String TYPE = "type";
    @Bind(R.id.base_title_back)
    TextView baseTitleBack;
    @Bind(R.id.base_title_title)
    TextView baseTitleTitle;
    @Bind(R.id.base_title_menu)
    ImageView baseTitleMenu;
    @Bind(R.id.chongzhi_text)
    TextView chongzhiText;
    @Bind(R.id.chongzhi_edt)
    EditText chongzhiEdt;
    @Bind(R.id.chongzhi_wechat)
    CheckBox chongzhiWechat;
    @Bind(R.id.chongzhi_alipay)
    CheckBox chongzhiAlipay;
    @Bind(R.id.chongzhi_btn)
    TextView chongzhiBtn;
    @Bind(R.id.chongzhi_num)
    TextView chongzhiNum;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_chongzhi;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        baseTitleMenu.setVisibility(View.INVISIBLE);
        baseTitleTitle.setText("充值");
        InputFilter[] is = {new CashierInputFilter(2)};
        chongzhiEdt.setFilters(is);
    }
    @Subscribe
    public void onEvent(FinshMessage m) {
        finish();
    }
    @Override
    protected void initEvent() {
        baseTitleBack.setOnClickListener(this);
        chongzhiBtn.setOnClickListener(this);
        chongzhiAlipay.setOnCheckedChangeListener(this);
        chongzhiWechat.setOnCheckedChangeListener(this);
        chongzhiEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s))
                    chongzhiNum.setText("￥0.00");
                else chongzhiNum.setText("￥" + chongzhiEdt.getText());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.chongzhi_btn:
                if (!TextUtils.isEmpty(chongzhiEdt.getText().toString())){

                    if (chongzhiWechat.isChecked())
                        chongzhiWechat();
                    else
                        chongzhiAlipay();
                }else {
                    showToast("请输入充值金额");
                }
                break;
            case R.id.base_title_back:
                finish();
                break;
        }

    }

    private void chongzhiWechat() {
        BaseApi.getRetrofit()
                .create(MineApi.class)
                .wechat(chongzhiEdt.getText().toString())
                .compose(RxSchedulers.<BaseModel<WxModel>>compose())
                .subscribe(new BaseObserver<WxModel>() {
                    @Override
                    protected void onHandleSuccess(WxModel model, String msg, int code) {
                        new WXPay(ChongzhiActivity.this,
                                model.getAppid(),
                                model.getPartnerid(),
                                model.getPrepayid(),
                                model.getS_package(),
                                model.getNoncestr(),
                                model.getTimestamp(),
                                model.getSign());
                    }

                    @Override
                    protected void onHandleError(String msg) {

                    }
                });


    }

    private void chongzhiAlipay() {
        BaseApi.getRetrofit()
                .create(MineApi.class)
                .aliPay(chongzhiEdt.getText().toString())
                .compose(RxSchedulers.<BaseModel<AliModel>>compose())
                .subscribe(new BaseObserver<AliModel>() {
                    @Override
                    protected void onHandleSuccess(AliModel model, String msg, int code) {
                        new AliPay(ChongzhiActivity.this, model.getOrderInfo(),0);
                    }

                    @Override
                    protected void onHandleError(String msg) {

                    }
                });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.chongzhi_wechat:
                if (isChecked)
                    chongzhiAlipay.setChecked(false);
                else
                    chongzhiAlipay.setChecked(true);
                break;
            case R.id.chongzhi_alipay:
                if (isChecked)
                    chongzhiWechat.setChecked(false);
                else
                    chongzhiWechat.setChecked(true);
                break;
        }
    }




}

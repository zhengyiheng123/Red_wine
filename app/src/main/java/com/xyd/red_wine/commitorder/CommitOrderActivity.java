package com.xyd.red_wine.commitorder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xyd.red_wine.R;
import com.xyd.red_wine.address.AddressActivity;
import com.xyd.red_wine.address.AddressModel;
import com.xyd.red_wine.alipay.AliPay;
import com.xyd.red_wine.api.AddressApi;
import com.xyd.red_wine.api.OrderApi;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.EmptyModel;
import com.xyd.red_wine.base.PublicStaticData;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.commissionorder.CommissionOrderModel;
import com.xyd.red_wine.glide.GlideUtil;
import com.xyd.red_wine.promptdialog.PromptDialog;
import com.xyd.red_wine.utils.LogUtil;
import com.xyd.red_wine.utils.ToastUtils;
import com.xyd.red_wine.winedetail.WineDetailActivity;
import com.xyd.red_wine.winedetail.WineModel;
import com.xyd.red_wine.wxapi.WXPay;
import com.xyd.red_wine.wxapi.WXPayEntryActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/17
 * @time: 16:20
 * @description:
 */

public class CommitOrderActivity extends BaseActivity {
    @Bind(R.id.base_title_back)
    TextView baseTitleBack;
    @Bind(R.id.base_title_title)
    TextView baseTitleTitle;
    @Bind(R.id.base_title_menu)
    ImageView baseTitleMenu;
    @Bind(R.id.commit_name)
    TextView commitName;
    @Bind(R.id.commit_phone)
    TextView commitPhone;
    @Bind(R.id.commit_address)
    TextView commitAddress;
    @Bind(R.id.commit_iv)
    ImageView commitIv;
    @Bind(R.id.commit_tv_title)
    TextView commitTvTitle;
    @Bind(R.id.commit_tv_title1)
    TextView commitTvTitle1;
    @Bind(R.id.commit_jian)
    ImageView commitJian;
    @Bind(R.id.commit_edt_num)
    EditText commitEdtNum;
    @Bind(R.id.commit_add)
    ImageView commitAdd;
    @Bind(R.id.commit_type)
    TextView commitType;
    @Bind(R.id.commit_freight)
    TextView commitFreight;
    @Bind(R.id.commit_edt_message)
    EditText commitEdtMessage;
    @Bind(R.id.commit_tv_money)
    TextView commitTvMoney;
    @Bind(R.id.commit_tv_money_bottom)
    TextView commitTvMoneyBottom;
    @Bind(R.id.commit_buy)
    TextView commitBuy;
    @Bind(R.id.commit_ll_address)
    LinearLayout commitLlAddress;
    @Bind(R.id.commit_tv_price)
    TextView commitTvPrice;
    @Bind(R.id.commit_tv_num)
    TextView commitTvNum;
    @Bind(R.id.commit_tv_num1)
    TextView commitTvNum1;
    @Bind(R.id.commit_address_none)
    TextView commitTvAddressNone;
//    @Bind(R.id.commit_cb_wx)
//    CheckBox commitCbWx;
//    @Bind(R.id.commit_cb_alipay)
//    CheckBox commitCbAlipay;
    @Bind(R.id.rl_webchat)
    RelativeLayout rl_webchat;
    @Bind(R.id.rl_alipay)
    RelativeLayout rl_alipay;

    @Bind(R.id.rb_alipay)
    CheckBox rb_alipay;
    @Bind(R.id.cb_webchat)
    CheckBox cb_webchat;
    private WineModel model;
    private int num = 1;
    private int a_id = -1;
    private PromptDialog waitDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_commit_order;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        waitDialog = new PromptDialog(this);
        baseTitleTitle.setText("小酒");
        baseTitleMenu.setVisibility(View.GONE);
        model = (WineModel) getIntent().getSerializableExtra(WineDetailActivity.G_DATA);
        num = getIntent().getIntExtra(WineDetailActivity.G_NUM, 1);
        GlideUtil.getInstance()
                .loadImage(this, commitIv, PublicStaticData.baseUrl + model.getGood().getG_img(), true);
        commitTvTitle.setText(model.getGood().getG_name());
        commitTvTitle1.setText(model.getGood().getG_sname());
        commitTvPrice.setText("￥" + model.getGood().getG_price() + "");
        if (model.getGood().getG_freight().equals("0.00"))
            commitFreight.setText("免运费");
        else
            commitFreight.setText("￥" + model.getGood().getG_freight());
        commitTvNum.setText("x" + num);
        commitTvNum1.setText("共" + num + "件商品\u3000小计");
        commitEdtNum.setText(num + "");
        commitTvMoneyBottom.setText("合计：￥" + (num * model.getGood().getG_price() + Double.valueOf(model.getGood().getG_freight())));
        commitTvMoney.setText("￥" + (num * model.getGood().getG_price() + Double.valueOf(model.getGood().getG_freight())));

    }

    @Subscribe
    public void onEvent(FinshMessage m) {

        Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, PayFinishActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        defalutAddress();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void defalutAddress() {

        BaseApi.getRetrofit()
                .create(AddressApi.class)
                .address("1")
                .compose(RxSchedulers.<BaseModel<AddressModel>>compose())
                .subscribe(new BaseObserver<AddressModel>() {
                    @Override
                    protected void onHandleSuccess(AddressModel addressModel, String msg, int code) {
                        if (addressModel.getMy_address().size() > 0) {
                            commitTvAddressNone.setVisibility(View.GONE);
                            commitLlAddress.setVisibility(View.VISIBLE);
                            commitAddress.setVisibility(View.VISIBLE);
                            commitName.setText(addressModel.getMy_address().get(0).getA_name());
                            commitPhone.setText(addressModel.getMy_address().get(0).getLink_phone());
                            commitAddress.setText(addressModel.getMy_address().get(0).getA_address());
                            a_id = addressModel.getMy_address().get(0).getA_id();

                        } else {
                            commitTvAddressNone.setVisibility(View.VISIBLE);
                            commitLlAddress.setVisibility(View.GONE);
                            commitAddress.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    protected void onHandleError(String msg) {
                        showToast(msg);
                    }
                });


    }

    @Override
    protected void initEvent() {
        baseTitleBack.setOnClickListener(this);
        commitAdd.setOnClickListener(this);
        commitJian.setOnClickListener(this);
        commitBuy.setOnClickListener(this);
        commitType.setOnClickListener(this);
        commitFreight.setOnClickListener(this);
        commitLlAddress.setOnClickListener(this);
        commitTvAddressNone.setOnClickListener(this);
//        commitCbWx.setOnCheckedChangeListener(this);
//        commitCbAlipay.setOnCheckedChangeListener(this);
        rl_alipay.setOnClickListener(this);
        rl_webchat.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.base_title_back:
                finish();
                break;
            case R.id.commit_add:
                if (num < 9) {
                    num++;
                    commitTvNum.setText("x" + num);
                    commitTvNum1.setText("共" + num + "件商品\u3000小计");
                    commitEdtNum.setText(num + "");
                    commitTvMoneyBottom.setText("合计：￥" + (num * model.getGood().getG_price() + Double.valueOf(model.getGood().getG_freight())));
                    commitTvMoney.setText("￥" + (num * model.getGood().getG_price() + Double.valueOf(model.getGood().getG_freight())));
                }
                break;
            case R.id.commit_jian:
                if (num > 1) {
                    num--;
                    commitTvNum.setText("x" + num);
                    commitTvNum1.setText("共" + num + "件商品\u3000小计");
                    commitEdtNum.setText(num + "");
                    commitTvMoneyBottom.setText("合计：￥" + (num * model.getGood().getG_price() + Double.valueOf(model.getGood().getG_freight())));
                    commitTvMoney.setText("￥" + (num * model.getGood().getG_price() + Double.valueOf(model.getGood().getG_freight())));
                }
                break;
            case R.id.commit_buy:
                if (a_id == -1)
                    showToast("请选择您的收货地址");
                else if (cb_webchat.isChecked()){
                    if (!UMShareAPI.get(this).isInstall(this, SHARE_MEDIA.WEIXIN)){
                        ToastUtils.show("微信未安装");
                        return;
                    }
                    commitWxBuy();
                }
                else if (rb_alipay.isChecked()){
                    if (!UMShareAPI.get(this).isInstall(this, SHARE_MEDIA.ALIPAY)){
                        ToastUtils.show("支付宝未安装");
                        return;
                    }
                    commitAlipay();
                }
                break;
            case R.id.commit_type:
                showTestToast("配送方式");
                break;
            case R.id.commit_freight:
                showTestToast("运费");
                break;
            case R.id.commit_ll_address:
                startActivity(AddressActivity.class);
                break;
            case R.id.commit_address_none:
                startActivity(AddressActivity.class);
                break;
            case R.id.rl_alipay:
                rb_alipay.setChecked(true);
                cb_webchat.setChecked(false);
                break;
            case R.id.rl_webchat:
                rb_alipay.setChecked(false);
                cb_webchat.setChecked(true);
                break;
        }
    }

    private void commitAlipay() {
        BaseApi.getRetrofit()
                .create(OrderApi.class)
                .buyAliPay(a_id + "", model.getGood().getG_id() + "", num + "", num * model.getGood().getG_price() + Double.valueOf(model.getGood().getG_freight()) + "", commitEdtMessage.getText().toString())
                .compose(RxSchedulers.<BaseModel<AliModel>>compose())
                .subscribe(new BaseObserver<AliModel>() {
                    @Override
                    protected void onHandleSuccess(AliModel model, String msg, int code) {
                        LogUtil.e(model.getOrderInfo());
                        new AliPay(CommitOrderActivity.this, model.getOrderInfo(),1);
                    }

                    @Override
                    protected void onHandleError(String msg) {

                    }
                });
    }

    private void commitWxBuy() {
        BaseApi.getRetrofit()
                .create(OrderApi.class)
                .buy(a_id + "", model.getGood().getG_id() + "", num + "", num * model.getGood().getG_price() + Double.valueOf(model.getGood().getG_freight()) + "", commitEdtMessage.getText().toString())
                .compose(RxSchedulers.<BaseModel<WxModel>>compose())
                .subscribe(new BaseObserver<WxModel>() {
                    @Override
                    protected void onHandleSuccess(WxModel model, String msg, int code) {
                        new WXPay(CommitOrderActivity.this,
                                model.getAppid(),
                                model.getPartnerid(),
                                model.getPrepayid(),
                                model.getS_package(),
                                model.getNoncestr(),
                                model.getTimestamp(),
                                model.getSign());
                        // finish();
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        showToast(msg);
                    }
                });


    }


//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        switch (buttonView.getId()) {
//            case R.id.commit_cb_wx:
//                if (isChecked)
//                    commitCbAlipay.setChecked(false);
//                else
//                    commitCbAlipay.setChecked(true);
//                break;
//            case R.id.commit_cb_alipay:
//                if (isChecked)
//                    commitCbWx.setChecked(false);
//                else
//                    commitCbWx.setChecked(true);
//                break;
//        }
//
//    }
}

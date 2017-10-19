package com.xyd.red_wine.orderdetail;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xyd.red_wine.R;
import com.xyd.red_wine.api.OrderApi;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.EmptyModel;
import com.xyd.red_wine.base.PublicStaticData;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.evaluate.EvaluateActivity;
import com.xyd.red_wine.glide.GlideUtil;
import com.xyd.red_wine.logistics.LogisticsActivity;
import com.xyd.red_wine.order.OrderModel;
import com.xyd.red_wine.promptdialog.PromptDialog;
import com.xyd.red_wine.utils.TimeUtils;
import com.xyd.red_wine.winedetail.WineDetailActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @date: 2017/8/29
 * @time: 17:07
 * @description: 订单详情
 */

public class OrderDetailActivity extends BaseActivity {
    public static final String ORDER_NUM = "order_num";

    @Bind(R.id.base_title_back)
    TextView baseTitleBack;
    @Bind(R.id.base_title_title)
    TextView baseTitleTitle;
    @Bind(R.id.base_title_menu)
    ImageView baseTitleMenu;
    @Bind(R.id.base_title_right)
    TextView baseTitleRight;
    @Bind(R.id.order_detail_order_type)
    TextView orderDetailOrderType;
    @Bind(R.id.order_detail_tv_wuliu)
    TextView orderDetailTvWuliu;
    @Bind(R.id.order_detail_tv_wuliu_time)
    TextView orderDetailTvWuliuTime;
    @Bind(R.id.order_detail_ll_wuliu)
    LinearLayout orderDetailLlWuliu;
    @Bind(R.id.order_detail_tv_address_name)
    TextView orderDetailTvAddressName;
    @Bind(R.id.order_detail_tv_address_phone)
    TextView orderDetailTvAddressPhone;
    @Bind(R.id.order_detail_ll_address)
    LinearLayout orderDetailLlAddress;
    @Bind(R.id.order_detail_tv_address)
    TextView orderDetailTvAddress;
    @Bind(R.id.order_detail_tv_liuyan)
    TextView orderDetailTvLiuyan;
    @Bind(R.id.order_detail_iv)
    ImageView orderDetailIv;
    @Bind(R.id.order_detail_tv_title)
    TextView orderDetailTvTitle;
    @Bind(R.id.order_detail_tv_title1)
    TextView orderDetailTvTitle1;
    @Bind(R.id.order_detail_tv_price)
    TextView orderDetailTvPrice;
    @Bind(R.id.order_detail_tv_num)
    TextView orderDetailTvNum;
    @Bind(R.id.order_detail_tv_left)
    TextView orderDetailTvLeft;
    @Bind(R.id.order_detail_tv_right)
    TextView orderDetailTvRight;
    @Bind(R.id.order_detail_tv_yunfei)
    TextView orderDetailTvYunfei;
    @Bind(R.id.order_detail_tv_shifu)
    TextView orderDetailTvShifu;
    @Bind(R.id.order_detail_tv_ordernum)
    TextView orderDetailTvOrdernum;
    @Bind(R.id.order_detail_tv_express)
    TextView orderDetailTvExpress;
    @Bind(R.id.order_detail_tv_type)
    TextView orderDetailTvType;
    @Bind(R.id.order_detail_tv_create_time)
    TextView orderDetailTvCreateTime;
    @Bind(R.id.order_detail_tv_fukuan_time)
    TextView orderDetailTvFukuanTime;
    @Bind(R.id.order_detail_tv_fahuo_time)
    TextView orderDetailTvFahuoTime;
    @Bind(R.id.order_detail_wuliu)
    TextView orderWuliu;
    private PromptDialog waitDialog;
    private OrderDetailModel model;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initView() {
        baseTitleMenu.setVisibility(View.INVISIBLE);
        baseTitleTitle.setText("订单详情");
        waitDialog = new PromptDialog(this);
        waitDialog.showLoading("");

    }

    private void getData() {
        BaseApi.getRetrofit()
                .create(OrderApi.class)
                .orderDetail(getIntent().getStringExtra(ORDER_NUM))
                .compose(RxSchedulers.<BaseModel<OrderDetailModel>>compose())
                .subscribe(new BaseObserver<OrderDetailModel>() {
                    @Override
                    protected void onHandleSuccess(OrderDetailModel orderDetailModel, String msg, int code) {
                        waitDialog.dismissImmediately();
                        model = orderDetailModel;

                        setData();


                    }

                    @Override
                    protected void onHandleError(String msg) {
                        waitDialog.dismissImmediately();
                        showToast(msg);
                    }
                });
    }

    private void setData() {
        switch (model.getOrder_status()) {
            // 1待付款2待发货3待收货4待评价5申请退款6已退款7已完成
            case 1:
                orderDetailTvRight.setVisibility(View.GONE);
                orderDetailTvLeft.setVisibility(View.GONE);
                orderDetailOrderType.setText("订单状态：待付款");
                orderDetailLlWuliu.setVisibility(View.GONE);
                orderDetailTvExpress.setVisibility(View.GONE);
                orderDetailTvFahuoTime.setVisibility(View.GONE);
                break;
            case 2:
                orderDetailLlWuliu.setVisibility(View.GONE);
                orderDetailTvRight.setVisibility(View.VISIBLE);
                orderDetailTvRight.setText("提醒发货");
                orderDetailTvLeft.setVisibility(View.GONE);
                orderDetailOrderType.setText("订单状态：待发货");
                orderDetailTvExpress.setVisibility(View.GONE);
                orderDetailTvFahuoTime.setVisibility(View.GONE);
                break;
            case 3:
                orderDetailTvRight.setVisibility(View.VISIBLE);
                orderDetailTvRight.setText("确认收货");
                orderDetailTvLeft.setVisibility(View.VISIBLE);
                orderDetailTvLeft.setText("查看物流");
                orderDetailOrderType.setText("订单状态：待收货");
                break;
            case 4:
                orderDetailTvRight.setVisibility(View.VISIBLE);
                orderDetailTvRight.setText("再次购买");
                orderDetailTvLeft.setVisibility(View.VISIBLE);
                orderDetailTvLeft.setText("评价");
                orderDetailOrderType.setText("订单状态：待评价");
                break;
            case 5:
                orderDetailTvRight.setVisibility(View.GONE);
                orderDetailTvLeft.setVisibility(View.GONE);
                orderDetailOrderType.setText("订单状态：申请退款中");
                break;
            case 6:
                orderDetailTvRight.setVisibility(View.GONE);
                orderDetailTvLeft.setVisibility(View.GONE);
                orderDetailOrderType.setText("订单状态：已退款");
                break;
            case 7:
                orderWuliu.setVisibility(View.VISIBLE);
                orderDetailTvRight.setVisibility(View.VISIBLE);
                orderDetailTvRight.setText("再次购买");
                orderDetailTvLeft.setVisibility(View.GONE);
                orderDetailOrderType.setText("订单状态：交易完成");
                break;
        }
        orderDetailTvAddress.setText("收获地址："+model.getA_address());
        orderDetailTvAddressName.setText("收货人："+model.getA_name());
        orderDetailTvAddressPhone.setText(model.getLink_phone());
        if (TextUtils.isEmpty(model.getContent())) {
            orderDetailTvLiuyan.setText("您没有任何留言哦");
        } else {
            orderDetailTvLiuyan.setText(model.getContent());
        }
        orderDetailTvYunfei.setText("￥" + model.getG_freight());
        orderDetailTvShifu.setText("￥" + model.getPrice());
        GlideUtil.getInstance().loadImage(this, orderDetailIv, PublicStaticData.baseUrl+model.getG_img(), true);
        orderDetailTvTitle.setText(model.getG_name());
        orderDetailTvTitle1.setText(model.getG_sname());
        orderDetailTvPrice.setText("￥" + model.getG_price());
        orderDetailTvNum.setText("x" + model.getNum());
        orderDetailTvOrdernum.setText("订单号：" + model.getOrder_num());
        orderDetailTvExpress.setText("快递单号：" + model.getExpress());


        orderDetailTvCreateTime.setText("创建时间：" + TimeUtils.millis2String(model.getCreate_time()*1000,"yyyy-MM-dd HH:mm:ss") );
        orderDetailTvFukuanTime.setText("付款时间：" + TimeUtils.millis2String(model.getUpdate_time()*1000,"yyyy-MM-dd HH:mm:ss"));
        if (model.getPay_type() == 1) {
            orderDetailTvType.setText("支付方式：支付宝");
        } else {
            orderDetailTvType.setText("支付方式：微信");
        }
        orderDetailTvFahuoTime.setText("发货时间：" + TimeUtils.millis2String(model.getGive_time()*1000,"yyyy-MM-dd HH:mm:ss"));
        orderDetailTvWuliu.setText(model.getLogistics_information().getContext());
        orderDetailTvWuliuTime.setText(model.getLogistics_information().getTime());


    }

    @Override
    protected void initEvent() {
        baseTitleBack.setOnClickListener(this);
        orderDetailLlWuliu.setOnClickListener(this);
        orderDetailTvRight.setOnClickListener(this);
        orderDetailTvLeft.setOnClickListener(this);
        orderWuliu.setOnClickListener(this);

    }

    @Override
    public void widgetClick(View v) {
        Bundle bundle;
        switch (v.getId()) {
            case R.id.order_detail_wuliu:
                bundle = new Bundle();
                bundle.putString(LogisticsActivity.ORDER_NUM, model.getOrder_num());
                bundle.putString(LogisticsActivity.ORDER_URL, model.getG_img());
                bundle.putInt(LogisticsActivity.ORDER_STATUS, model.getOrder_status());
                startActivity(LogisticsActivity.class, bundle);
                break;
            case R.id.base_title_back:
                finish();
                break;
            case R.id.order_detail_ll_wuliu:
                bundle = new Bundle();
                bundle.putString(LogisticsActivity.ORDER_NUM, model.getOrder_num());
                bundle.putString(LogisticsActivity.ORDER_URL, model.getG_img());
                bundle.putInt(LogisticsActivity.ORDER_STATUS, model.getOrder_status());
                startActivity(LogisticsActivity.class, bundle);
                break;
            case R.id.order_detail_tv_right:
                if (model.getOrder_status() == 2) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailActivity.this);
                    builder.setTitle("温馨提醒");
                    builder.setMessage("确定提醒商家发货！！！");
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            // commitOrder(adapter.getData().get(position).getOrder_num());
                        }


                    });
                    Dialog dialog = builder.create();
                    dialog.show();
                } else if (model.getOrder_status() == 3) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailActivity.this);
                    builder.setTitle("温馨提醒");
                    builder.setMessage("确定收货！！！");
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            commitOrder(model.getOrder_num());
                        }


                    });
                    Dialog dialog = builder.create();
                    dialog.show();
                } else if (model.getOrder_status() == 4 || model.getOrder_status() == 7) {
                    bundle = new Bundle();
                    bundle.putString(WineDetailActivity.G_ID, model.getG_id() + "");
                    startActivity(WineDetailActivity.class, bundle);
                }
                break;
            case R.id.order_detail_tv_left:
                if (model.getOrder_status() == 4) {
                    bundle = new Bundle();
                    OrderModel.MyOrderBean bean = new OrderModel.MyOrderBean();
                    bean.setG_id(model.getG_id());
                    bean.setG_img(model.getG_img());
                    bean.setG_name(model.getG_name());
                    bean.setG_sname(model.getG_sname());
                    bean.setNum(model.getNum());
                    bean.setOrder_num(model.getOrder_num());
                    bean.setPrice(model.getG_price());
                    bundle.putSerializable(EvaluateActivity.EVALUATE, bean);
                    startActivity(EvaluateActivity.class, bundle);
                } else if (model.getOrder_status() == 2 || model.getOrder_status() == 3) {
                    bundle = new Bundle();
                    bundle.putString(LogisticsActivity.ORDER_NUM, model.getOrder_num());
                    bundle.putString(LogisticsActivity.ORDER_URL, model.getG_img());
                    bundle.putInt(LogisticsActivity.ORDER_STATUS, model.getOrder_status());
                    startActivity(LogisticsActivity.class, bundle);
                }
                break;


        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void commitOrder(String num) {

        BaseApi.getRetrofit()
                .create(OrderApi.class)
                .edit_status(num)
                .compose(RxSchedulers.<BaseModel<EmptyModel>>compose())
                .subscribe(new BaseObserver<EmptyModel>() {
                    @Override
                    protected void onHandleSuccess(EmptyModel emptyModel, String msg, int code) {
                        showToast(msg);
                        getData();
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        showToast(msg);
                    }
                });

    }

}

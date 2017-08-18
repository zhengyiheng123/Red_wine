package com.xyd.red_wine.alipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.xyd.red_wine.commitorder.PayFinishActivity;
import com.xyd.red_wine.utils.LogUtil;


import java.util.Map;

/**
 * 调支付宝支付
 */
public class AliPay {

    private Activity activity;
    private String orderInfo;
    private int type;

    public AliPay(Activity activity, String orderInfo, int type) {
        this.activity = activity;
        this.orderInfo = orderInfo;
        LogUtil.e(orderInfo);
        this.type = type;
        payV2();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {

            @SuppressWarnings("unchecked")
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            /**
             对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
             */
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                if (type == 1) {
                    Intent intent = new Intent(activity, PayFinishActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                } else {
                    activity.finish();
                }


                //Toast.makeText(activity, "支付成功", Toast.LENGTH_SHORT).show();
            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                if (TextUtils.equals(resultStatus, "8000")) {
                    Toast.makeText(activity, "支付结果确认中", Toast.LENGTH_SHORT).show();

                } else {
                    // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                    Toast.makeText(activity, "支付失败", Toast.LENGTH_SHORT).show();
                }
            }
        }

        ;
    };

    /**
     * 支付宝支付业务
     */
    public void payV2() {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());
                Message msg = new Message();
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

}

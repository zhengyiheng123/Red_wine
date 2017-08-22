package com.xyd.red_wine.commitorder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xyd.red_wine.R;
import com.xyd.red_wine.api.OrderApi;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.main.MainActivity;
import com.xyd.red_wine.main.mine.MineFragment;
import com.xyd.red_wine.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/1
 * @time: 12:43
 * @description:  支付完成
 */

public class PayFinishActivity extends BaseActivity {
    @Bind(R.id.base_title_back)
    TextView baseTitleBack;
    @Bind(R.id.base_title_title)
    TextView baseTitleTitle;
    @Bind(R.id.base_title_menu)
    ImageView baseTitleMenu;
    @Bind(R.id.activity_payfinish_back)
    TextView activityPayfinishBack;
    @Bind(R.id.activity_payfinish_again)
    TextView activityPayfinishAgain;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_finish;
    }

    @Override
    protected void initView() {
        baseTitleTitle.setText("支付完成");
        baseTitleMenu.setVisibility(View.INVISIBLE);
        getMessage();
    }

    @Override
    protected void initEvent() {
        baseTitleBack.setOnClickListener(this);
        activityPayfinishAgain.setOnClickListener(this);
        activityPayfinishBack.setOnClickListener(this);


    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.base_title_back:
                finish();
                break;
            case R.id.activity_payfinish_again:
                finish();
                break;
            case R.id.activity_payfinish_back:
                startActivity(MainActivity.class);
                finish();
                break;

        }

    }
    private void getMessage(){
        BaseApi.getRetrofit().create(OrderApi.class)
                .payMsg()
                .compose(RxSchedulers.<BaseModel<PayMsg>>compose())
                .subscribe(new BaseObserver<PayMsg>() {
                    @Override
                    protected void onHandleSuccess(PayMsg payMsg, String msg, int code) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(PayFinishActivity.this);
                        builder.setTitle("温馨提示");
                        builder.setMessage(msg);
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        ToastUtils.show(msg);
                    }
                });
    }
//请求数据
public class PayMsg{
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

}

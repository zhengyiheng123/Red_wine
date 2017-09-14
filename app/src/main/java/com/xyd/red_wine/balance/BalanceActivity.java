package com.xyd.red_wine.balance;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xyd.red_wine.R;
import com.xyd.red_wine.api.MineApi;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.PublicStaticData;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.glide.GlideUtil;
import com.xyd.red_wine.login.LoginActivity;
import com.xyd.red_wine.login.StartupPageActivity;
import com.xyd.red_wine.main.MainActivity;
import com.xyd.red_wine.personinformation.BindActivity;
import com.xyd.red_wine.personinformation.InfromationModel;
import com.xyd.red_wine.promptdialog.PromptDialog;
import com.xyd.red_wine.view.DrawImageView;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/13
 * @time: 16:05
 * @description: 账户余额
 */

public class BalanceActivity extends BaseActivity {
    @Bind(R.id.base_title_back)
    TextView baseTitleBack;
    @Bind(R.id.base_title_right)
    TextView baseTitleRight;
    @Bind(R.id.base_title_title)
    TextView baseTitleTitle;
    @Bind(R.id.base_title_menu)
    ImageView baseTitleMenu;
    @Bind(R.id.balance_tv_name)
    TextView balanceTvName;
    @Bind(R.id.balance_tv_money)
    TextView balanceTvMoney;
    @Bind(R.id.balance_tv_all)
    TextView balanceTvAll;
    @Bind(R.id.balance_tv_generalize)
    TextView balanceTvGeneralize;
    @Bind(R.id.balance_tv_withdraw)
    TextView balanceTvWithdraw;
    @Bind(R.id.balance_tv_balance)
    TextView balanceTvBalance;
    @Bind(R.id.balance_tv_top_up)
    TextView balanceTvTopUp;
    @Bind(R.id.balance_rl)
    RelativeLayout balanceRl;
    @Bind(R.id.balance_iv_head)
    ImageView balanceIvHead;
    @Bind(R.id.balance_iv_money)
    DrawImageView balanceIvMoney;
    @Bind(R.id.base_title_headline)
    ImageView mHeadLine;

    private BalanceModel model;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_balance;
    }

    @Override
    protected void initView() {
        mHeadLine.setVisibility(View.GONE);
        baseTitleTitle.setText("账户余额");
        baseTitleMenu.setVisibility(View.GONE);
         baseTitleRight.setVisibility(View.VISIBLE);
        baseTitleRight.setText("记录");
        getData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        BaseApi.getRetrofit()
                .create(MineApi.class)
                .balance()
                .compose(RxSchedulers.<BaseModel<BalanceModel>>compose())
                .subscribe(new BaseObserver<BalanceModel>() {
                    @Override
                    protected void onHandleSuccess(BalanceModel balanceModel, String msg, int code) {
                        model = balanceModel;
                        balanceTvName.setText(balanceModel.getNickname());
                        GlideUtil.getInstance().loadCircleImage(BalanceActivity.this, balanceIvHead, PublicStaticData.baseUrl + balanceModel.getHead_img());
                        balanceTvMoney.setText(balanceModel.getTotal() + "");
                        balanceTvBalance.setText(balanceModel.getAccount_balance() + "");
                        balanceTvGeneralize.setText(balanceModel.getRevenue_balance() + "");
                        balanceIvMoney.setAngel(balanceModel.getTotal() / 20);
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
        baseTitleMenu.setOnClickListener(this);
        balanceTvWithdraw.setOnClickListener(this);
        balanceTvTopUp.setOnClickListener(this);
        baseTitleRight.setOnClickListener(this);


    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.base_title_back:
                finish();
                break;
            case R.id.base_title_menu:
//                showTestToast("菜单");
//                Random random = new Random();
//                balanceIvMoney.setAngel(random.nextInt(360));
                break;
            case R.id.balance_tv_withdraw:
                getUserInfo();
                break;
            case R.id.balance_tv_top_up:

                startActivity(ChongzhiActivity.class);
                break;
            case R.id.base_title_right:
                startActivity(RecordActivity.class);
                break;
        }

    }
    //获取用户信息
private void getUserInfo(){
    final PromptDialog dialog=new PromptDialog(BalanceActivity.this);
    dialog.showLoading("请稍后",false);
    BaseApi.getRetrofit()
            .create(MineApi.class)
            .information()
            .compose(RxSchedulers.<BaseModel<InfromationModel>>compose())
            .subscribe(new BaseObserver<InfromationModel>() {
                @Override
                protected void onHandleSuccess(InfromationModel infromationModel, String msg, int code) {
                    dialog.dismissImmediately();
                    //  login("qiaozhijinhan" + infromationModel.getUserid(), "123456");
                    if (!TextUtils.isEmpty(infromationModel.getPhone())){
                        Bundle bundle=new Bundle();
                        bundle.putDouble(TixianActivity.AVAILAVLE_MONEY,model.getAccount_balance());
                        bundle.putString(TixianActivity.PHONENUM,infromationModel.getPhone());
                        startActivity(TixianActivity.class,bundle);
                    }else {
                        AlertDialog.Builder builder=new AlertDialog.Builder(BalanceActivity.this);
                        builder.setTitle("提示");
                        builder.setMessage("请先绑定手机号码，才能进行提现。");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(BindActivity.class);
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();
                    }
                }

                @Override
                protected void onHandleError(String msg) {
                    showTestToast(msg);
                    dialog.dismissImmediately();
                }
            });
}

}

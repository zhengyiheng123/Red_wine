package com.xyd.red_wine.balance;

import android.os.Bundle;
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_balance;
    }

    @Override
    protected void initView() {
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
                startActivity(TixianActivity.class);

                break;
            case R.id.balance_tv_top_up:

                startActivity(ChongzhiActivity.class);
                break;
            case R.id.base_title_right:
                startActivity(RecordActivity.class);
                break;
        }

    }


}

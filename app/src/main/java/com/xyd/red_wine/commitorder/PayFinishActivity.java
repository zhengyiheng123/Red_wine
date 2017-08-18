package com.xyd.red_wine.commitorder;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xyd.red_wine.R;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.main.MainActivity;

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


}

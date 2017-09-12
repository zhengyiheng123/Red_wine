package com.xyd.red_wine.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.xyd.red_wine.R;
import com.xyd.red_wine.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/13
 * @time: 11:46
 * @description: 活动
 */

public class ActivityActivity extends BaseActivity {
    @Bind(R.id.base_title_back)
    TextView baseTitleBack;
    @Bind(R.id.base_title_title)
    TextView baseTitleTitle;
    @Bind(R.id.base_title_menu)
    ImageView baseTitleMenu;
    @Bind(R.id.activity_benefit)
    TextView activityBenefit;
    @Bind(R.id.activity_benefit_line)
    TextView activityBenefitLine;
    @Bind(R.id.activity_business)
    TextView activityBusiness;
    @Bind(R.id.activity_business_line)
    TextView activityBusinessLine;
    @Bind(R.id.activity_fl)
    FrameLayout activityFl;
    private Fragment[] fragments;
    private int index = 0, currentTabIndex = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_activity;
    }

    @Override
    protected void initView() {
        baseTitleTitle.setText("活动");
        baseTitleMenu.setVisibility(View.INVISIBLE);
        BusinessFragment businessFragment = new BusinessFragment();
        BenefitFragment benefitFragment = new BenefitFragment();
        fragments = new Fragment[]{businessFragment,benefitFragment};
        onTextClicked();
    }

    @Override
    protected void initEvent() {
        baseTitleBack.setOnClickListener(this);
        activityBenefit.setOnClickListener(this);
        activityBusiness.setOnClickListener(this);

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.base_title_back:
                finish();
                break;
            case R.id.activity_benefit:
                activityBenefit.setTextColor(getResources().getColor(R.color.material_textWhite_black));
                activityBenefitLine.setBackgroundResource(R.color.material_white);
                activityBusiness.setTextColor(getResources().getColor(R.color.material_textWhite_text));
                activityBusinessLine.setBackgroundResource(R.color.touming);
                index = 1;
                onTextClicked();
                break;
            case R.id.activity_business:
                activityBenefit.setTextColor(getResources().getColor(R.color.material_textWhite_text));
                activityBenefitLine.setBackgroundResource(R.color.touming);
                activityBusiness.setTextColor(getResources().getColor(R.color.material_textWhite_black));
                activityBusinessLine.setBackgroundResource(R.color.material_white);
                index = 0;
                onTextClicked();
                break;
        }

    }

    private void onTextClicked() {
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        if (currentTabIndex != index) {
            if (currentTabIndex != -1) {
                trx.hide(fragments[currentTabIndex]);
            }

            if (!fragments[index].isAdded()) {
                trx.add(R.id.activity_fl, fragments[index]);
            }
            trx.show(fragments[index]).commit();
            currentTabIndex = index;
        }

    }


}

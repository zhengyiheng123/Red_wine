package com.xyd.red_wine.rank;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xyd.red_wine.R;
import com.xyd.red_wine.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/13
 * @time: 14:04
 * @description: 排行榜
 */

public class RankActivity extends BaseActivity {


    @Bind(R.id.base_title_back)
    TextView baseTitleBack;
    @Bind(R.id.base_title_title)
    TextView baseTitleTitle;
    @Bind(R.id.base_title_menu)
    ImageView baseTitleMenu;
    @Bind(R.id.rank_iv1)
    ImageView rankIv1;
    @Bind(R.id.rank_benefit)
    TextView rankBenefit;
    @Bind(R.id.rank_benefit_line)
    TextView rankBenefitLine;
    @Bind(R.id.rank_business)
    TextView rankBusiness;
    @Bind(R.id.rank_business_line)
    TextView rankBusinessLine;
    @Bind(R.id.rank_ll)
    LinearLayout rankLl;
    @Bind(R.id.rank_fl)
    FrameLayout rankFl;
    private Fragment[] fragments;
    private int index = 0, currentTabIndex = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rank;
    }

    @Override
    protected void initView() {
        baseTitleTitle.setText("排行榜");
        baseTitleMenu.setVisibility(View.GONE);
        EarningFragment earningFragment = new EarningFragment();
        BenefitFragment benefitFragment = new BenefitFragment();
        fragments = new Fragment[]{benefitFragment, earningFragment};
        onTextClicked();
    }

    @Override
    protected void initEvent() {
        baseTitleBack.setOnClickListener(this);
        rankBenefit.setOnClickListener(this);
        rankBusiness.setOnClickListener(this);

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.base_title_back:
                finish();
                break;
            case R.id.rank_benefit:
                rankBenefit.setTextColor(getResources().getColor(R.color.material_textWhite_black));
                rankBenefitLine.setBackgroundResource(R.color.material_white);
                rankBusiness.setTextColor(getResources().getColor(R.color.material_textWhite_text));
                rankBusinessLine.setBackgroundResource(R.color.touming);
                index = 0;
                onTextClicked();
                break;
            case R.id.rank_business:
                rankBenefit.setTextColor(getResources().getColor(R.color.material_textWhite_text));
                rankBenefitLine.setBackgroundResource(R.color.touming);
                rankBusiness.setTextColor(getResources().getColor(R.color.material_textWhite_black));
                rankBusinessLine.setBackgroundResource(R.color.material_white);
                index = 1;
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
                trx.add(R.id.rank_fl, fragments[index]);
            }
            trx.show(fragments[index]).commit();
            currentTabIndex = index;
        }

    }


}

package com.xyd.red_wine.main;

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
import com.xyd.red_wine.main.home.HomeFragment;
import com.xyd.red_wine.main.mine.MineFragment;
import com.xyd.red_wine.main.service.ServiceFragment;
import com.xyd.red_wine.utils.FileUtils;
import com.xyd.red_wine.utils.LogUtil;
import com.xyd.red_wine.utils.StatusBarUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: zhaoxiaolei
 * @date: 2017/6/12
 * @time: 14:12
 * @description: 主界面
 */

public class MainActivity extends BaseActivity {


    @Bind(R.id.main_fl)
    FrameLayout mainFl;
    @Bind(R.id.main_iv_service)
    ImageView mainIvService;
    @Bind(R.id.main_tv_service)
    TextView mainTvService;
    @Bind(R.id.main_ll_service)
    LinearLayout mainLlService;
    @Bind(R.id.main_iv_home)
    ImageView mainIvHome;
    @Bind(R.id.main_tv_home)
    TextView mainTvHome;
    @Bind(R.id.main_ll_home)
    LinearLayout mainLlHome;
    @Bind(R.id.main_iv_wode)
    ImageView mainIvWode;
    @Bind(R.id.main_tv_wode)
    TextView mainTvWode;
    @Bind(R.id.main_ll_wode)
    LinearLayout mainLlWode;

    private Fragment[] fragments;
    private int index = 0, currentTabIndex = -1;
    private HomeFragment home;
    private ServiceFragment service;
    private MineFragment mine;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
    }

    @Override
    protected void initView() {
        home = new HomeFragment();
        service = new ServiceFragment();
        mine = new MineFragment();

        fragments = new Fragment[]{home, service, mine};
        //默认 选中订单
        onTextClicked();
    }

    @Override
    protected void initEvent() {
        mainLlWode.setOnClickListener(this);
        mainLlHome.setOnClickListener(this);
        mainLlService.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.main_ll_home:
                index = 0;
                mainTvHome.setTextColor(getResources().getColor(R.color.material_amber_A700));
                mainIvHome.setImageResource(R.mipmap.home);
                if (currentTabIndex != index)
                    changeColor();
                break;
            case R.id.main_ll_service:
                index = 1;
                mainTvService.setTextColor(getResources().getColor(R.color.material_amber_A700));
                mainIvService.setImageResource(R.mipmap.service);
                if (currentTabIndex != index)
                    changeColor();
                break;
            case R.id.main_ll_wode:
                index = 2;
                mainTvWode.setTextColor(getResources().getColor(R.color.material_amber_A700));
                mainIvWode.setImageResource(R.mipmap.mine);
                if (currentTabIndex != index)
                    changeColor();

                break;

        }
        onTextClicked();

    }

    private void onTextClicked() {
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        if (currentTabIndex != index) {
            if (currentTabIndex != -1) {
                trx.hide(fragments[currentTabIndex]);
            }

            if (!fragments[index].isAdded()) {
                trx.add(R.id.main_fl, fragments[index]);
            }
            trx.show(fragments[index]).commit();
            currentTabIndex = index;
        }

    }

    private void changeColor() {
        //setStatusBar();
        switch (currentTabIndex) {
            case 0:
                mainIvHome.setImageResource(R.mipmap.home_hui);
                mainTvHome.setTextColor(getResources().getColor(R.color.material_textBlack_secondaryText));
                break;
            case 1:
                mainIvService.setImageResource(R.mipmap.service_hui);
                mainTvService.setTextColor(getResources().getColor(R.color.material_textBlack_secondaryText));
                break;
            case 2:
                mainIvWode.setImageResource(R.mipmap.mine_hui);
                mainTvWode.setTextColor(getResources().getColor(R.color.material_textBlack_secondaryText));
                break;

        }
    }



}

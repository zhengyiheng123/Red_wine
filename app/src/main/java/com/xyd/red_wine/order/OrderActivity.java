package com.xyd.red_wine.order;

import android.graphics.Color;
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
 * @time: 15:29
 * @description: 订单
 */

public class OrderActivity extends BaseActivity {
    @Bind(R.id.base_title_back)
    TextView baseTitleBack;
    @Bind(R.id.base_title_title)
    TextView baseTitleTitle;
    @Bind(R.id.base_title_menu)
    ImageView baseTitleMenu;
    @Bind(R.id.order_all)
    TextView orderAll;
    @Bind(R.id.order_wait)
    TextView orderWait;
    @Bind(R.id.order_finish)
    TextView orderFinish;
    @Bind(R.id.order_fl)
    FrameLayout orderFl;

    private Fragment[] fragments;
    private int index = 0, currentTabIndex = -1;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_order;
    }

    @Override
    protected void initView() {
        baseTitleTitle.setText("订单");
        baseTitleMenu.setVisibility(View.GONE);
        OrderAllFragment orderAllFragment = new OrderAllFragment();
        OrderWaitFragment orderWaitFragment = new OrderWaitFragment();
        OrderFinishFragment orderFinishFragment = new OrderFinishFragment();
        fragments = new Fragment[]{orderAllFragment, orderWaitFragment, orderFinishFragment};
        onTextClicked();

    }

    @Override
    protected void initEvent() {
        baseTitleBack.setOnClickListener(this);
        orderAll.setOnClickListener(this);
        orderFinish.setOnClickListener(this);
        orderWait.setOnClickListener(this);

    }

    @Override
    public void widgetClick(View v) {

        switch (v.getId()) {
            case R.id.base_title_back:
                finish();
                break;
            case R.id.order_all:
                index = 0;
                orderAll.setBackgroundResource(R.drawable.bg_btn_quit);
                orderAll.setTextColor(Color.WHITE);
                if (currentTabIndex != index)
                    changeColor();
                onTextClicked();
                break;
            case R.id.order_wait:
                index = 1;
                orderWait.setBackgroundResource(R.drawable.bg_btn_quit);
                orderWait.setTextColor(Color.WHITE);
                if (currentTabIndex != index)
                    changeColor();
                onTextClicked();
                break;
            case R.id.order_finish:
                index = 2;
                orderFinish.setBackgroundResource(R.drawable.bg_btn_quit);
                orderFinish.setTextColor(Color.WHITE);
                if (currentTabIndex != index)
                    changeColor();
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
                trx.add(R.id.order_fl, fragments[index]);
            }
            trx.show(fragments[index]).commit();
            currentTabIndex = index;
        }

    }

    private void changeColor() {
        switch (currentTabIndex) {
            case 0:
                orderAll.setBackgroundResource(R.drawable.bg_login);
                orderAll.setTextColor(Color.BLACK);
                break;
            case 1:
                orderWait.setBackgroundResource(R.drawable.bg_login);
                orderWait.setTextColor(Color.BLACK);
                break;
            case 2:
                orderFinish.setBackgroundResource(R.drawable.bg_login);
                orderFinish.setTextColor(Color.BLACK);
                break;

        }
    }

}

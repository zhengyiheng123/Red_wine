package com.xyd.red_wine.message;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.xyd.red_wine.R;
import com.xyd.red_wine.base.BaseActivity;

import butterknife.Bind;


/**
 * @author: zhaoxiaolei
 * @date: 2017/8/16
 * @time: 9:24
 * @description:
 */

public class MessageActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {
    @Bind(R.id.base_title_back)
    TextView baseTitleBack;
    @Bind(R.id.base_title_title)
    TextView baseTitleTitle;
    @Bind(R.id.base_title_menu)
    ImageView baseTitleMenu;
    @Bind(R.id.base_title_right)
    TextView baseTitleRight;
//    @Bind(R.id.message_weidu)
//    TextView messageWeidu;
//    @Bind(R.id.message_yidu)
//    TextView messageYidu;
    @Bind(R.id.message_fl)
    FrameLayout messageFl;
    @Bind(R.id.message_tl)
    TabLayout mMessageTL;
    @Bind(R.id.base_title_headline)
    ImageView mHeadLine;
    private Fragment[] fragments;
    private int index = 0, currentTabIndex = -1;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initView() {
        mHeadLine.setVisibility(View.GONE);
        baseTitleBack.setVisibility(View.VISIBLE);
        baseTitleTitle.setVisibility(View.VISIBLE);
        baseTitleMenu.setVisibility(View.INVISIBLE);
        baseTitleTitle.setText("我的消息");
        YiduFragment yiduFragment = new YiduFragment();
        WeiduFragment weiduFragment = new WeiduFragment();
        fragments = new Fragment[]{weiduFragment, yiduFragment};
        onTextClicked();

        mMessageTL.addTab(mMessageTL.newTab().setText("未读消息"));
        mMessageTL.addTab(mMessageTL.newTab().setText("已读消息"));
    }

    @Override
    protected void initEvent() {
        baseTitleBack.setOnClickListener(this);
//        messageWeidu.setOnClickListener(this);
//        messageYidu.setOnClickListener(this);
        mMessageTL.addOnTabSelectedListener(this);

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.base_title_back:
                finish();
                break;
//            case R.id.message_weidu:
////                messageWeidu.setBackgroundResource(R.color.material_white);
////                messageWeidu.setTextColor(Color.BLACK);
////                messageYidu.setBackgroundResource(R.drawable.bg_test);
////                messageYidu.setTextColor(Color.WHITE);
//
//                break;
//            case R.id.message_yidu:
////                messageWeidu.setBackgroundResource(R.drawable.bg_test);
////                messageWeidu.setTextColor(Color.WHITE);
////                messageYidu.setBackgroundResource(R.color.material_white);
////                messageYidu.setTextColor(Color.BLACK);
//
//                break;
        }

    }

    private void onTextClicked() {
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        if (currentTabIndex != index) {
            if (currentTabIndex != -1) {
                trx.hide(fragments[currentTabIndex]);
            }

            if (!fragments[index].isAdded()) {
                trx.add(R.id.message_fl, fragments[index]);
            }
            trx.show(fragments[index]).commit();
            currentTabIndex = index;
        }

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                index = 0;
                onTextClicked();
                break;
            case 1:
                index = 1;
                onTextClicked();
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}

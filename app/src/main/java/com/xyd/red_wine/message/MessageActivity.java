package com.xyd.red_wine.message;

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


/**
 * @author: zhaoxiaolei
 * @date: 2017/8/16
 * @time: 9:24
 * @description:
 */

public class MessageActivity extends BaseActivity {
    @Bind(R.id.base_title_back)
    TextView baseTitleBack;
    @Bind(R.id.base_title_title)
    TextView baseTitleTitle;
    @Bind(R.id.base_title_menu)
    ImageView baseTitleMenu;
    @Bind(R.id.base_title_right)
    TextView baseTitleRight;
    @Bind(R.id.message_weidu)
    TextView messageWeidu;
    @Bind(R.id.message_yidu)
    TextView messageYidu;
    @Bind(R.id.message_fl)
    FrameLayout messageFl;
    private Fragment[] fragments;
    private int index = 0, currentTabIndex = -1;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initView() {
        baseTitleBack.setVisibility(View.VISIBLE);
        baseTitleTitle.setVisibility(View.VISIBLE);
        baseTitleMenu.setVisibility(View.INVISIBLE);
        baseTitleTitle.setText("我的消息");
        YiduFragment yiduFragment = new YiduFragment();
        WeiduFragment weiduFragment = new WeiduFragment();
        fragments = new Fragment[]{weiduFragment, yiduFragment};
        onTextClicked();

    }

    @Override
    protected void initEvent() {
        baseTitleBack.setOnClickListener(this);
        messageWeidu.setOnClickListener(this);
        messageYidu.setOnClickListener(this);

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.base_title_back:
                finish();
                break;
            case R.id.message_weidu:
               messageWeidu.setBackgroundResource(R.drawable.bg_test);
                messageWeidu.setTextColor(Color.WHITE);
                messageYidu.setBackgroundResource(R.color.material_white);
               messageYidu.setTextColor(Color.BLACK);
                index = 0;
                onTextClicked();

                break;
            case R.id.message_yidu:
                messageWeidu.setBackgroundResource(R.color.material_white);
                messageWeidu.setTextColor(Color.BLACK);
                messageYidu.setBackgroundResource(R.drawable.bg_test);
                messageYidu.setTextColor(Color.WHITE);
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
                trx.add(R.id.message_fl, fragments[index]);
            }
            trx.show(fragments[index]).commit();
            currentTabIndex = index;
        }

    }

}

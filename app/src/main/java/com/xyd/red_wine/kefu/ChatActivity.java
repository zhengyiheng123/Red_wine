package com.xyd.red_wine.kefu;



import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;



import com.hyphenate.helpdesk.easeui.ui.ChatFragment;
import com.hyphenate.helpdesk.easeui.util.Config;
import com.hyphenate.helpdesk.model.AgentIdentityInfo;
import com.hyphenate.helpdesk.model.ContentFactory;
import com.hyphenate.helpdesk.model.VisitorInfo;
import com.xyd.red_wine.R;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.utils.StatusBarUtil;

import java.io.Serializable;

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/4
 * @time: 13:33
 * @description:
 */

public class ChatActivity extends FragmentActivity {
    public static ChatActivity instance = null;

    private ChatFragment chatFragment;

    String toChatUsername;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
      //  StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        initView();
    }



    protected void initView() {
        //IM服务号
        toChatUsername = "kefuchannelimid_033725";
        Bundle bundle=new Bundle();
        bundle.putString(Config.EXTRA_SERVICE_IM_NUMBER, toChatUsername);
        bundle.putString(Config.EXTRA_TITLE_NAME, "客服");
        bundle.putBoolean(Config.EXTRA_SHOW_NICK, false);
      //  bundle.putParcelable(Config.EXTRA_VISITOR_INFO, (Parcelable) createVisitorInfo());
       // bundle.putParcelable(Config.EXTRA_AGENT_INFO, (Parcelable) createAgentIdentityInfo());
        //可以直接new ChatFragment使用
        chatFragment = new CustomChatFragment();
        //传入参数
        chatFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.chat_fl, chatFragment).commit();
    }


    public  VisitorInfo createVisitorInfo() {
        VisitorInfo info = ContentFactory.createVisitorInfo(null);
        info.nickName("乔治金瀚")
                .name("乔治金瀚");

        return info;
    }
    public AgentIdentityInfo createAgentIdentityInfo(){

        AgentIdentityInfo info =ContentFactory.createAgentIdentityInfo(null);
        info.agentName("乔治金瀚");
        return info;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}

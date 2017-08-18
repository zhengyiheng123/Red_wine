package com.xyd.red_wine.message;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xyd.red_wine.R;
import com.xyd.red_wine.api.MessageApi;
import com.xyd.red_wine.api.MineApi;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.EmptyModel;
import com.xyd.red_wine.base.RxSchedulers;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/16
 * @time: 10:26
 * @description:
 */

public class MessageDetailActivity extends BaseActivity {
    public static String CONTENT = "content";
    public static String TIME = "time";
    public static String ID = "id";
    @Bind(R.id.base_title_back)
    TextView baseTitleBack;
    @Bind(R.id.base_title_title)
    TextView baseTitleTitle;
    @Bind(R.id.base_title_menu)
    ImageView baseTitleMenu;
    @Bind(R.id.message_detail_time)
    TextView messageDetailTime;
    @Bind(R.id.message_detail_content)
    TextView messageDetailContent;
    private int id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_detail;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        baseTitleTitle.setText("我的消息");
        baseTitleMenu.setVisibility(View.INVISIBLE);
        messageDetailTime.setText(getIntent().getStringExtra(TIME));
        messageDetailContent.setText(getIntent().getStringExtra(CONTENT));
        id = getIntent().getIntExtra(ID, -1);
        if (id != -1)
            edit();


    }

    private void edit() {
        BaseApi.getRetrofit()
                .create(MessageApi.class)
                .do_read(id)
                .compose(RxSchedulers.<BaseModel<EmptyModel>>compose())
                .subscribe(new BaseObserver<EmptyModel>() {
                    @Override
                    protected void onHandleSuccess(EmptyModel messageModel, String msg, int code) {
                        EventBus.getDefault().post(new MessageEvent());
                    }

                    @Override
                    protected void onHandleError(String msg) {

                    }
                });

    }

    @Override
    protected void initEvent() {
        baseTitleBack.setOnClickListener(this);

    }

    @Override
    public void widgetClick(View v) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe
    public void onEvent(MessageEvent event){

    }
}

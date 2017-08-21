package com.xyd.red_wine.message;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.xyd.red_wine.member.EarningActivity;
import com.xyd.red_wine.member.InputDialog;
import com.xyd.red_wine.utils.ToastUtils;

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
    //内容
    public static String CONTENT = "content";
    //时间
    public static String TIME = "time";
    //消息id
    public static String ID = "id";
    //用户id
    public static String USER_ID="user_id";
    //消息类型
    public static String R_TYPE="r_type";
    //是否回复过
    public static String IS_REPLY="is_reply";

    //来自
    public static String FROM="from";
    @Bind(R.id.base_title_back)
    TextView baseTitleBack;
    @Bind(R.id.base_title_title)
    TextView baseTitleTitle;
    @Bind(R.id.base_title_menu)
    ImageView baseTitleMenu;
    @Bind(R.id.message_detail_time)
    TextView messageDetailTime;
    @Bind(R.id.tv_from)
    TextView tvFrom;
    @Bind(R.id.message_detail_content)
    TextView messageDetailContent;
    @Bind(R.id.tv_reply)
    TextView tvReply;
    private int id;
    private InputDialog.Builder dialogBuilder;

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
        if (getIntent().getIntExtra(IS_REPLY,-1) == 0){
            tvReply.setVisibility(View.VISIBLE);
        }
        if (getIntent().getIntExtra(R_TYPE,-1) == 4){
            tvFrom.setText("来自："+getIntent().getStringExtra(FROM));
        }

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
        tvReply.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
//        finish();
        switch (v.getId()){
            case R.id.tv_reply:
                dialogBuilder = new InputDialog.Builder(MessageDetailActivity.this)
                        .setTitle("留言")
                        .setInputHint("请输入留言")
                        .setPositiveButton("确定", new InputDialog.ButtonActionListener() {
                            @Override
                            public void onClick(CharSequence inputText) {
                                // TODO
//                        ToastUtils.show(inputText);
                                if (TextUtils.isEmpty(inputText.toString())){
                                    ToastUtils.show("请输入内容");
                                    return;
                                }
                                if (getIntent().getIntExtra(USER_ID,-1) == -1) {
                                    ToastUtils.show("用户不存在");
                                    return;
                                }else {
                                    sentMessage(id, inputText.toString());
                                }
                            }
                        })
                        .setNegativeButton("取消", new InputDialog.ButtonActionListener() {
                            @Override
                            public void onClick(CharSequence inputText) {
                                // TODO
                            }
                        });
                dialogBuilder.show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe
    public void onEvent(MessageEvent event){

    }
    //发送站内信
    public void sentMessage(int pid,String r_con){
        BaseApi.getRetrofit()
                .create(MineApi.class)
                .replyMessage(pid,r_con)
                .compose(RxSchedulers.<BaseModel>compose())
                .subscribe(new BaseObserver() {
                    @Override
                    protected void onHandleSuccess(Object o, String msg, int code) {
                        if(code == 1){
                            ToastUtils.show("回复成功！");
                            EventBus.getDefault().post(new MessageEvent());
                            finish();
                        }else {
                            ToastUtils.show(msg);
                        }
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        ToastUtils.show(msg);
                    }
                });
    }
}

package com.xyd.red_wine.register;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.xyd.red_wine.R;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.base.PublicStaticData;
import com.xyd.red_wine.main.MainActivity;
import com.xyd.red_wine.promptdialog.PromptDialog;
import com.xyd.red_wine.utils.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/5
 * @time: 14:51
 * @description: 注册界面
 */

public class
RegisterActivity extends BaseActivity implements RegisterContract.View {
    @Bind(R.id.register_edt_user)
    EditText registerEdtUser;
    @Bind(R.id.register_edt_password)
    EditText registerEdtPassword;
    @Bind(R.id.register_edt_password_ok)
    EditText registerEdtPasswordOk;
    @Bind(R.id.register_edt_recommend)
    EditText registerEdtRecommend;
    @Bind(R.id.register_edt_code)
    EditText registerEdtCode;
    @Bind(R.id.register_tv_code)
    TextView registerTvCode;
    @Bind(R.id.register_cb)
    CheckBox registerCb;
    @Bind(R.id.register_tv_protocol)
    TextView registerTvProtocol;
    @Bind(R.id.register_btn)
    Button registerBtn;
    private RegisterPresenter presenter;
    private PromptDialog promptDialog;
    private int downTime = 60;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        presenter = new RegisterPresenter(this);
        promptDialog = new PromptDialog(this);
        EventBus.getDefault().register(this);
//        Button btn = (Button) findViewById(R.id.test_test);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                StringBuffer sb = new StringBuffer();
//                for (String s : PublicStaticData.logString) {
//                    sb.append(s+"\n");
//
//                }
//                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                shareIntent.setType("text/plain");
//                shareIntent.putExtra(Intent.EXTRA_TEXT, sb.toString());
//                //自定义选择框的标题
//                startActivity(Intent.createChooser(shareIntent, "资讯"));
//
//            }
//        });
    }

    @Override
    protected void initEvent() {
        registerTvProtocol.setOnClickListener(this);
        registerTvCode.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    @Subscribe
    public void onEvent(RegisterMessage message) {

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.register_btn:
                PublicStaticData.logString.add("1.正在注册");
                presenter.register(registerEdtUser.getText().toString(),
                        registerEdtPassword.getText().toString(),
                        registerEdtPasswordOk.getText().toString(),
                        registerEdtRecommend.getText().toString(),
                        registerEdtCode.getText().toString(),
                        registerCb.isChecked()
                );
                break;
            case R.id.register_tv_code:
                if (downTime == 60)
                    presenter.getCode(registerEdtUser.getText().toString());
                break;
            case R.id.register_tv_protocol:
//                showTestToast("协议");
                startActivity(new Intent(getApplicationContext(),ProtocolActivity.class));
                break;
        }

    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
    }


    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {

    }

    @Override
    public void showError(String msg) {
        showToast(msg);
    }

    @Override
    public void success() {
        try {

            EventBus.getDefault().post(new RegisterMessage(registerEdtUser.getText().toString(),
                    registerEdtPassword.getText().toString()));
            finish();
        } catch (Throwable t) {
            showTestToast("EventBus" + t.toString());
            PublicStaticData.logString.add("7.EventBus异常" + t.toString());
        }


    }

    @Override
    public void showDialog() {
        promptDialog.showLoading("正在注册", true);
    }

    @Override
    public void closeDialog() {
        promptDialog.dismissImmediately();
    }

    @Override
    public void downTime() {
        handler.post(runnable);

    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (downTime > 0) {
                downTime--;
                registerTvCode.setText("还剩" + downTime + "秒");
                handler.postDelayed(this, 1000);
            } else {
                registerTvCode.setText("获取验证码");
                downTime = 60;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
        EventBus.getDefault().unregister(this);
    }
}

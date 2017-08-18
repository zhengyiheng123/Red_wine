package com.xyd.red_wine.forgetpassword;

import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xyd.red_wine.R;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.promptdialog.PromptDialog;
import com.xyd.red_wine.utils.RegexUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/7
 * @time: 17:00
 * @description: 忘记密码
 */

public class ForgetPasswordActivity extends BaseActivity implements ForgetPasswordContract.View {
    @Bind(R.id.forget_edt_new)
    EditText forgetEdtNew;
    @Bind(R.id.forget_edt_user)
    EditText forgetEdtUser;
    @Bind(R.id.forget_edt_ok)
    EditText forgetEdtOk;
    @Bind(R.id.forget_cb)
    CheckBox forgetCb;
    @Bind(R.id.forget_btn_next)
    Button forgetBtnNext;
    @Bind(R.id.forget_rl)
    RelativeLayout forgetRl;
    @Bind(R.id.forget_tv_phone)
    TextView forgetTvPhone;
    @Bind(R.id.forget_edt_code)
    EditText forgetEdtCode;
    @Bind(R.id.forget_tv_code)
    TextView forgetTvCode;
    @Bind(R.id.forget_btn_commit)
    Button forgetBtnCommit;
    @Bind(R.id.forget_rl_next)
    LinearLayout forgetRlNext;
    private ForgetPasswordPresenter presenter;
    private PromptDialog promptDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void initView() {
        presenter = new ForgetPasswordPresenter(this);
        promptDialog = new PromptDialog(this);


    }

    @Override
    protected void initEvent() {
        forgetBtnNext.setOnClickListener(this);
        forgetTvCode.setOnClickListener(this);
        forgetBtnCommit.setOnClickListener(this);
        forgetCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    forgetEdtNew.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    forgetEdtOk.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    forgetEdtNew.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    forgetEdtOk.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.forget_btn_commit:
                presenter.editPassword(forgetEdtUser.getText().toString(),
                        forgetEdtNew.getText().toString(),
                        forgetEdtCode.getText().toString());
                break;
            case R.id.forget_tv_code:
                if (downTime == 60)
                    presenter.code(forgetEdtUser.getText().toString());
                break;
            case R.id.forget_btn_next:
                if (!RegexUtils.isMobilePhoneNumber(forgetEdtUser.getText().toString())) {
                    showToast("请输入正确的手机号");
                } else if (!RegexUtils.isPWD(forgetEdtNew.getText().toString())) {
                    showToast("密码必须是6~12位且同时包含数字与字母");
                } else if (!forgetEdtNew.getText().toString().equals(forgetEdtOk.getText().toString())) {
                    showToast("请确认密码");
                } else {
                    forgetRl.setVisibility(View.GONE);
                    forgetRlNext.setVisibility(View.VISIBLE);
                    String phone = forgetEdtUser.getText().toString();
                    forgetTvPhone.setText("*******" + phone.substring(phone.length() - 4, phone.length()));
                }

                break;
        }

    }


    @Override
    public void setPresenter(ForgetPasswordContract.Presenter presenter) {

    }

    @Override
    public void error(String msg) {
        showToast(msg);

    }

    @Override
    public void success() {
        showToast("修改成功");
        finish();

    }

    @Override
    public void showDialog() {
        promptDialog.showLoading("");
    }

    @Override
    public void closeDialog() {
        promptDialog.dismissImmediately();

    }

    @Override
    public void downTime() {
        handler.postDelayed(runnable, 1000);
    }

    private int downTime = 60;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (downTime > 0) {
                downTime--;
                forgetTvCode.setText("还剩" + downTime + "秒");
                handler.postDelayed(this, 1000);
            } else {
                forgetTvCode.setText("重新获取验证码");
                downTime = 60;
            }
        }
    };
}

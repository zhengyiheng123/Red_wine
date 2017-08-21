package com.xyd.red_wine.main.mine;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;
import com.xyd.red_wine.R;
import com.xyd.red_wine.api.MineApi;
import com.xyd.red_wine.balance.BalanceActivity;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseFragment;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.PublicStaticData;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.collect.CollectActivity;
import com.xyd.red_wine.commissionorder.CommissionOrderActivity;
import com.xyd.red_wine.member.EarningActivity;
import com.xyd.red_wine.glide.GlideUtil;
import com.xyd.red_wine.kefu.ChatActivity;
import com.xyd.red_wine.message.MessageActivity;
import com.xyd.red_wine.order.OrderActivity;
import com.xyd.red_wine.payments.PaymentsActivity;
import com.xyd.red_wine.permissions.PermissionsManager;
import com.xyd.red_wine.personinformation.InformationMessage;
import com.xyd.red_wine.personinformation.InfromationActivity;
import com.xyd.red_wine.personinformation.InfromationModel;
import com.xyd.red_wine.rank.RankActivity;
import com.xyd.red_wine.setting.SettingActivity;
import com.xyd.red_wine.utils.LogUtil;
import com.xyd.red_wine.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;

/**
 * @author: zhaoxiaolei
 * @date: 2017/6/12
 * @time: 15:06
 * @description: 我的
 */

public class MineFragment extends BaseFragment {

    @Bind(R.id.menu)
    ImageView menu;
    @Bind(R.id.mine_tv_name)
    TextView mineTvName;
    @Bind(R.id.mine_tv_idiograph)
    TextView mineTvIdiograph;
    @Bind(R.id.mine_tv_order)
    TextView mineTvOrder;
    @Bind(R.id.mine_tv_commission_order)
    TextView mineTvCommissionOrder;
    @Bind(R.id.mine_tv_rank)
    TextView mineTvRank;
    @Bind(R.id.mine_tv_collection)
    TextView mineTvCollection;
    @Bind(R.id.mine_tv_setting)
    TextView mineTvSetting;
    @Bind(R.id.mine_iv_head)
    ImageView mineIvHead;
    @Bind(R.id.mine_iv_information)
    ImageView mineIvInformation;
    @Bind(R.id.mine_iv_service)
    ImageView mineIvService;
    @Bind(R.id.mine_tv_gongyi)
    TextView mineTvGongyi;
    @Bind(R.id.mine_tv_balance)
    TextView mineTvBalance;
    @Bind(R.id.mine_tv_earnings)
    TextView mineTvEarnings;
    @Bind(R.id.view_circle)
    View view_circle;
    @Bind(R.id.mine_tv_message)
    RelativeLayout mineTvMessage;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        menu.setVisibility(View.INVISIBLE);
        GlideUtil.getInstance()
                .loadCircleImage(getActivity(), mineIvHead, PublicStaticData.baseUrl + PublicStaticData.sharedPreferences.getString("head", ""));
        mineTvName.setText(PublicStaticData.sharedPreferences.getString("nickname", ""));
        mineTvIdiograph.setText(PublicStaticData.sharedPreferences.getString("signature", ""));
    }

    @Override
    public void onResume() {
        super.onResume();
        queryMessage();
    }

    @Override
    protected void initEvent() {
        menu.setOnClickListener(this);
        mineTvCollection.setOnClickListener(this);
        mineTvCommissionOrder.setOnClickListener(this);
        mineTvOrder.setOnClickListener(this);
        mineTvRank.setOnClickListener(this);
        mineTvSetting.setOnClickListener(this);
        mineIvInformation.setOnClickListener(this);
        mineIvService.setOnClickListener(this);
        mineTvGongyi.setOnClickListener(this);
        mineTvBalance.setOnClickListener(this);
        mineTvEarnings.setOnClickListener(this);

        mineTvMessage.setOnClickListener(this);

    }

    @Subscribe
    public void onEventBus(InformationMessage message) {
        GlideUtil.getInstance()
                .loadCircleImage(getActivity(), mineIvHead, PublicStaticData.baseUrl + PublicStaticData.sharedPreferences.getString("head", ""));
        mineTvName.setText(PublicStaticData.sharedPreferences.getString("nickname", ""));
        mineTvIdiograph.setText(PublicStaticData.sharedPreferences.getString("signature", ""));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.menu:
                showTestToast("menu");
                break;
            case R.id.mine_tv_collection:
                startActivity(CollectActivity.class);
                break;
            case R.id.mine_tv_commission_order:
                startActivity(CommissionOrderActivity.class);
                break;
            case R.id.mine_tv_order:
                startActivity(OrderActivity.class);
                break;
            case R.id.mine_tv_rank:
                startActivity(RankActivity.class);
                break;
            case R.id.mine_tv_setting:
                startActivity(SettingActivity.class);
                break;
            case R.id.mine_iv_information:
                startActivity(InfromationActivity.class);
                break;
            case R.id.mine_iv_service:
                if (ChatClient.getInstance().isLoggedInBefore())
                    startActivity(ChatActivity.class);
                else
                    loginHx("qiaozhijinhan"+PublicStaticData.sharedPreferences.getInt("id",0),"123456");

                break;
            case R.id.mine_tv_gongyi:
                startActivity(PaymentsActivity.class);
                break;
            case R.id.mine_tv_balance:
                startActivity(BalanceActivity.class);
                break;
            case R.id.mine_tv_earnings:
                startActivity(EarningActivity.class);
                break;
            case R.id.mine_tv_message:
               startActivity(MessageActivity.class);
                break;

        }

    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            queryMessage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    private void loginHx(final String uname, final String upwd) {


        // login huanxin server
        ChatClient.getInstance().login(uname, upwd, new Callback() {
            @Override
            public void onSuccess() {
                startActivity(ChatActivity.class);
            }

            @Override
            public void onError(int code, String error) {
                LogUtil.e(code+error);
               // loginHx("qiaozhijinhan"+PublicStaticData.sharedPreferences.getInt("id",0),"123456");
            }

            @Override
            public void onProgress(int progress, String status) {
                LogUtil.e(progress+status);
              //  loginHx("qiaozhijinhan"+PublicStaticData.sharedPreferences.getInt("id",0),"123456");
            }
        });
    }
    //查询是否有未读消息
    private void queryMessage(){
        BaseApi.getRetrofit().create(MineApi.class)
                .s_index()
                .compose(RxSchedulers.<BaseModel<Is_Read>>compose())
                .subscribe(new BaseObserver<Is_Read>() {
                    @Override
                    protected void onHandleSuccess(Is_Read is_read, String msg, int code) {
                        if (is_read.getIs_read() == 0){
                            view_circle.setVisibility(View.VISIBLE);
                        }else {
                            view_circle.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    protected void onHandleError(String msg) {

                    }
                });
    }
    public class Is_Read{
        private int is_read;

        public int getIs_read() {
            return is_read;
        }

        public void setIs_read(int is_read) {
            this.is_read = is_read;
        }
    }
}

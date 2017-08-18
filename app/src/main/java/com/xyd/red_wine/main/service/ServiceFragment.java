package com.xyd.red_wine.main.service;

import android.view.View;
import android.widget.ImageView;

import com.xyd.red_wine.R;
import com.xyd.red_wine.activity.ActivityActivity;
import com.xyd.red_wine.api.GeneralizeApi;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseFragment;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.PublicStaticData;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.generalize.GeneralizeActivity;
import com.xyd.red_wine.generalize.GeneralizeModel;
import com.xyd.red_wine.glide.GlideUtil;
import com.xyd.red_wine.primeur.PrimeurActivity;
import com.xyd.red_wine.promptdialog.PromptDialog;
import com.xyd.red_wine.store.StoreActivity;
import com.xyd.red_wine.suggest.SuggestActivity;
import com.xyd.red_wine.winesteward.StewardActivity;

import butterknife.Bind;

/**
 * @author: zhaoxiaolei
 * @date: 2017/6/12
 * @time: 15:06
 * @description: 服务
 */

public class ServiceFragment extends BaseFragment {

    @Bind(R.id.menu)
    ImageView menu;
    @Bind(R.id.service_iv_steward)
    ImageView serviceIvSteward;
    @Bind(R.id.service_iv_generalize)
    ImageView serviceIvGeneralize;
    @Bind(R.id.service_iv_market)
    ImageView serviceIvMarket;
    @Bind(R.id.service_iv_service)
    ImageView serviceIvService;
    @Bind(R.id.service_iv_primeur)
    ImageView serviceIvPrimeur;
    @Bind(R.id.service_iv_activity)
    ImageView serviceIvActivity;
    private PromptDialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_service;
    }

    @Override
    protected void initView() {
        menu.setVisibility(View.INVISIBLE);
        dialog=new PromptDialog(getActivity());

    }

    @Override
    protected void initEvent() {
        menu.setOnClickListener(this);
        serviceIvActivity.setOnClickListener(this);
        serviceIvSteward.setOnClickListener(this);
        serviceIvGeneralize.setOnClickListener(this);
        serviceIvMarket.setOnClickListener(this);
        serviceIvService.setOnClickListener(this);
        serviceIvPrimeur.setOnClickListener(this);

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.menu:
                break;
            case R.id.service_iv_steward:
                startActivity(StewardActivity.class);
                break;
            case R.id.service_iv_generalize:
                dialog.showLoading("");
                getData();
                break;
            case R.id.service_iv_market:

              startActivity(StoreActivity.class);
                break;
            case R.id.service_iv_service:
                startActivity(SuggestActivity.class);
                break;
            case R.id.service_iv_primeur:
               startActivity(PrimeurActivity.class);
                break;
            case R.id.service_iv_activity:
                startActivity(ActivityActivity.class);
                break;
        }

    }

    /**
     * 购买商品后才能分享
     */
    private void getData() {
        BaseApi.getRetrofit()
                .create(GeneralizeApi.class)
                .generalize()
                .compose(RxSchedulers.<BaseModel<GeneralizeModel>>compose())
                .subscribe(new BaseObserver<GeneralizeModel>() {
                    @Override
                    protected void onHandleSuccess(GeneralizeModel generalizeModel, String msg, int code) {
                        dialog.dismissImmediately();
                        startActivity(GeneralizeActivity.class);
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        dialog.dismissImmediately();
                        showToast(msg);
                    }
                });
    }
}

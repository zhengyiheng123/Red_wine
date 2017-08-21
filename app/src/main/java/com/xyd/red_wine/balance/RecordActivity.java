package com.xyd.red_wine.balance;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xyd.red_wine.R;
import com.xyd.red_wine.activity.BenefitAdapter;
import com.xyd.red_wine.api.MineApi;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.RxSchedulers;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * @author: zhaoxiaolei
 * @date: 2017/8/14
 * @time: 17:08
 * @description: 充值  提现记录
 */

public class RecordActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{
    @Bind(R.id.base_title_back)
    TextView baseTitleBack;
    @Bind(R.id.base_title_title)
    TextView baseTitleTitle;
    @Bind(R.id.base_title_menu)
    ImageView baseTitleMenu;
    @Bind(R.id.base_title_right)
    TextView baseTitleRight;
    @Bind(R.id.record_Rv)
    RecyclerView recordRv;
    @Bind(R.id.record_srl)
    SwipeRefreshLayout recordSrl;
    private RecordAdapter adapter;
    private List<CashValueModel.CashValueBean> list;
    private int page=1,num=10;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_record;

    }

    @Override
    protected void initView() {
        baseTitleTitle.setText("记录");
        baseTitleMenu.setVisibility(View.INVISIBLE);
        recordSrl.setColorSchemeColors(Color.rgb(241, 173, 74));
        recordRv.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();
        getData();
    }

    private void getData() {
        BaseApi.getRetrofit()
                .create(MineApi.class)
                .cash_value(page,num)
                .compose(RxSchedulers.<BaseModel<CashValueModel>>compose())
                .subscribe(new BaseObserver<CashValueModel>() {
                    @Override
                    protected void onHandleSuccess(CashValueModel cashValueModel, String msg, int code) {
                        adapter.loadMoreComplete();
                        recordSrl.setRefreshing(false);
                        if (page == 1) {
                            adapter.setNewData(cashValueModel.getCash_value());
                        } else if (cashValueModel.getCash_value().size() > 0) {
                            adapter.addData(cashValueModel.getCash_value());

                        } else {
                            adapter.loadMoreEnd();
                        }
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        adapter.loadMoreComplete();
                        recordSrl.setRefreshing(false);
                        showToast(msg);
                    }
                });

    }

    private void initAdapter() {

        list = new ArrayList<>();

        adapter = new RecordAdapter(list, this);
        adapter.setOnLoadMoreListener(this, recordRv);
//        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        recordRv.setAdapter(adapter);

    }

    @Override
    protected void initEvent() {
        baseTitleBack.setOnClickListener(this);
        recordSrl.setOnRefreshListener(this);

    }

    @Override
    public void widgetClick(View v) {
        finish();

    }


    @Override
    public void onRefresh() {
        page=1;
        getData();

    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        getData();

    }
}

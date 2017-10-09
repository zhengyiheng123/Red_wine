package com.xyd.red_wine.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xyd.red_wine.R;
import com.xyd.red_wine.api.ActivityApi;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseFragment;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.main.home.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/13
 * @time: 11:56
 * @description: 公益活动
 */

public class BenefitFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener ,BaseQuickAdapter.OnItemClickListener{

    @Bind(R.id.base_rv)
    RecyclerView baseRv;
    @Bind(R.id.base_srl)
    SwipeRefreshLayout baseSrl;
    private int page = 1;
    private int num = 10;
    private List<BenefitModel.ActiveBean> list;
    private BenefitAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.base_srl_rv;
    }

    @Override
    protected void initView() {

        baseSrl.setColorSchemeColors(Color.rgb(241, 173, 74));
        baseRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        initAdapter();
        getData();

    }

    private void initAdapter() {

        list = new ArrayList<>();

        adapter = new BenefitAdapter(list, getActivity());
        adapter.setOnLoadMoreListener(this, baseRv);
//        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        baseRv.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    protected void initEvent() {
        baseSrl.setOnRefreshListener(this);
    }

    @Override
    public void widgetClick(View v) {

    }

    /**
     * SwipeRefreshLayout
     */
    @Override
    public void onRefresh() {
        page = 1;
        getData();

    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        page++;
        getData();
    }

    public void getData() {
        BaseApi.getRetrofit()
                .create(ActivityApi.class)
                .active(0, page, num)
                .compose(RxSchedulers.<BaseModel<BenefitModel>>compose())
                .subscribe(new BaseObserver<BenefitModel>() {
                    @Override
                    protected void onHandleSuccess(BenefitModel benefitModel, String msg, int code) {
                        adapter.loadMoreComplete();
                        baseSrl.setRefreshing(false);
                        if (page == 1) {
                            adapter.setNewData(benefitModel.getActive());
                        } else if (benefitModel.getActive().size() > 0) {
                            adapter.addData(benefitModel.getActive());

                        } else {
                            adapter.loadMoreEnd();
                        }

                    }

                    @Override
                    protected void onHandleError(String msg) {
                        adapter.loadMoreComplete();
                        baseSrl.setRefreshing(false);
                        showToast(msg);
                    }
                });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter1, View view, int position) {
        Bundle b=new Bundle();
        b.putString(WebViewActivity.TITLE,"活动");
        b.putString(WebViewActivity.URL,adapter.getData().get(position).getA_content());
        startActivity(WebViewActivity.class,b);

    }
}

package com.xyd.red_wine.member;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xyd.red_wine.R;
import com.xyd.red_wine.api.MemberApi;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.EmptyModel;
import com.xyd.red_wine.base.PublicStaticData;
import com.xyd.red_wine.base.RxSchedulers;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/16
 * @time: 11:35
 * @description: 站内信
 */

public class MemberActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener,SwipeRefreshLayout.OnRefreshListener {
    public static final String ID = "id";
    @Bind(R.id.base_title_back)
    TextView baseTitleBack;
    @Bind(R.id.base_title_right)
    TextView baseTitleRight;
    @Bind(R.id.base_title_title)
    TextView baseTitleTitle;
    @Bind(R.id.base_title_menu)
    ImageView baseTitleMenu;
    @Bind(R.id.member_edt)
    EditText memberEdt;
    @Bind(R.id.member_send)
    TextView memberSend;
    @Bind(R.id.member_rv)
    RecyclerView memberRv;
    @Bind(R.id.member_srl)
    SwipeRefreshLayout srl;
    private int u_id;
    private int p_id;
    private int num = 10, page = 1;
    private MemberAdapter adapter;
    private List<MemberModel.ExchangeBean> list;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member;
    }

    @Override
    protected void initView() {
        u_id = PublicStaticData.sharedPreferences.getInt("id", 0);
        p_id = getIntent().getIntExtra(ID, 0);
        baseTitleTitle.setText("信息");
        baseTitleMenu.setVisibility(View.INVISIBLE);
        baseTitleRight.setVisibility(View.VISIBLE);
        baseTitleRight.setText("删除记录");
        srl.setColorSchemeColors(Color.rgb(241, 173, 74));
        getData();
        memberRv.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new MemberAdapter(list);
        adapter.setOnLoadMoreListener(this, memberRv);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        memberRv.setAdapter(adapter);


    }

    @Override
    protected void initEvent() {
        baseTitleBack.setOnClickListener(this);
        memberSend.setOnClickListener(this);
        baseTitleRight.setOnClickListener(this);
        srl.setOnRefreshListener(this);

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.base_title_back:
                finish();
                break;
            case R.id.member_send:
                if (!TextUtils.isEmpty(memberEdt.getText().toString()))
                    send(memberEdt.getText().toString());
                memberEdt.getText().clear();
                break;
            case R.id.base_title_right:
                del();
                break;
        }

    }

    private void getData() {

        Log.e("mmmmmmmmm",u_id+"\n"+p_id+"\n"+page+num);
        BaseApi.getRetrofit()
                .create(MemberApi.class)
                .exchange(u_id, p_id, page, num)
                .compose(RxSchedulers.<BaseModel<MemberModel>>compose())
                .subscribe(new BaseObserver<MemberModel>() {
                    @Override
                    protected void onHandleSuccess(MemberModel messageModel, String msg, int code) {
                        adapter.loadMoreComplete();
srl.setRefreshing(false);
                        if (page == 1) {
                            adapter.setNewData(messageModel.getExchange());
                        } else if (messageModel.getExchange().size() > 0) {
                            adapter.addData(messageModel.getExchange());

                        } else {
                            adapter.loadMoreEnd();
                        }
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        adapter.loadMoreComplete();
                        srl.setRefreshing(false);
                    }
                });

    }

    private void send(String s) {
        BaseApi.getRetrofit()
                .create(MemberApi.class)
                .add(u_id, p_id, s)
                .compose(RxSchedulers.<BaseModel<EmptyModel>>compose())
                .subscribe(new BaseObserver<EmptyModel>() {
                    @Override
                    protected void onHandleSuccess(EmptyModel emptyModel, String msg, int code) {
                        page = 1;
                        getData();
                    }

                    @Override
                    protected void onHandleError(String msg) {
                    }
                });
    }

    private void del() {

        BaseApi.getRetrofit()
                .create(MemberApi.class)
                .del(u_id, p_id)
                .compose(RxSchedulers.<BaseModel<EmptyModel>>compose())
                .subscribe(new BaseObserver<EmptyModel>() {
                    @Override
                    protected void onHandleSuccess(EmptyModel emptyModel, String msg, int code) {
                        adapter.getData().clear();
                        page = 1;
                        getData();
                    }

                    @Override
                    protected void onHandleError(String msg) {
                    }
                });
    }


    @Override
    public void onLoadMoreRequested() {
        page++;
        getData();
    }

    @Override
    public void onRefresh() {
        page=1;
        getData();
    }
}

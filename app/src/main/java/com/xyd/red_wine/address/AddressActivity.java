package com.xyd.red_wine.address;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xyd.red_wine.R;
import com.xyd.red_wine.api.AddressApi;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.EmptyModel;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.promptdialog.PromptDialog;
import com.xyd.red_wine.winesteward.StewardAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/25
 * @time: 14:56
 * @description:
 */

public class AddressActivity extends BaseActivity implements BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener {
    @Bind(R.id.base_title_back)
    TextView baseTitleBack;
    @Bind(R.id.base_title_title)
    TextView baseTitleTitle;
    @Bind(R.id.base_title_menu)
    ImageView baseTitleMenu;
    @Bind(R.id.address_add)
    TextView addressAdd;
    @Bind(R.id.address_rv)
    RecyclerView addressRv;
    private List<AddressModel.MyAddressBean> list;
    private AddressAdapter adapter;
    private PromptDialog dialog;

    @Override
    protected void onResume() {
        super.onResume();
        addressData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address;
    }

    @Override
    protected void initView() {
        dialog = new PromptDialog(this);
        baseTitleMenu.setVisibility(View.INVISIBLE);
        baseTitleTitle.setText("收货地址");
        addressRv.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();


    }

    private void initAdapter() {
        list = new ArrayList<>();
        adapter = new AddressAdapter(list, this);
        //  adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);
        addressRv.setAdapter(adapter);
        adapter.setEnableLoadMore(false);
    }

    private void addressData() {

        BaseApi.getRetrofit()
                .create(AddressApi.class)
                .address("")
                .compose(RxSchedulers.<BaseModel<AddressModel>>compose())
                .subscribe(new BaseObserver<AddressModel>() {
                    @Override
                    protected void onHandleSuccess(AddressModel addressModel, String msg, int code) {
                        adapter.setNewData(addressModel.getMy_address());
                        dialog.dismissImmediately();
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        dialog.dismissImmediately();
                        showToast(msg);
                    }
                });
    }

    @Override
    protected void initEvent() {
        baseTitleBack.setOnClickListener(this);
        addressAdd.setOnClickListener(this);

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.base_title_back:
                finish();
                break;
            case R.id.address_add:
                Bundle b = new Bundle();
                b.putString("a_id", "");
                b.putString("a_name", "");
                b.putString("a_area", "");
                b.putString("a_address", "");
                b.putString("link_phone", "");
                b.putString("is_default", "");
                startActivity(AddressEditActivity.class, b);
                break;
        }

    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter1, View view, int position) {
        switch (view.getId()) {
            case R.id.address_del:
                delAddress(adapter.getData().get(position).getA_id() + "");

                break;
            case R.id.address_edit:
                Bundle b = new Bundle();
                b.putString("a_id", adapter.getData().get(position).getA_id() + "");
                b.putString("a_name", adapter.getData().get(position).getA_name() + "");
                b.putString("a_area", adapter.getData().get(position).getA_area() + "");
                b.putString("a_address", adapter.getData().get(position).getA_address() + "");
                b.putString("link_phone", adapter.getData().get(position).getLink_phone() + "");
                b.putString("is_default", adapter.getData().get(position).getIs_default() + "");
                startActivity(AddressEditActivity.class, b);
                break;
            case R.id.address_moren_ll:
                setAddress(adapter.getData().get(position).getA_id() + "");

                break;
        }

    }

    private void delAddress(String a_id) {
        dialog.showLoading("");
        BaseApi.getRetrofit()
                .create(AddressApi.class)
                .delAddress(a_id)
                .compose(RxSchedulers.<BaseModel<EmptyModel>>compose())
                .subscribe(new BaseObserver<EmptyModel>() {
                    @Override
                    protected void onHandleSuccess(EmptyModel emptyModel, String msg, int code) {
                        addressData();
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        showToast(msg);
                    }
                });
    }

    private void setAddress(String a_id) {
        dialog.showLoading("");
        BaseApi.getRetrofit()
                .create(AddressApi.class)
                .setDefaultAddress(a_id)
                .compose(RxSchedulers.<BaseModel<EmptyModel>>compose())
                .subscribe(new BaseObserver<EmptyModel>() {
                    @Override
                    protected void onHandleSuccess(EmptyModel emptyModel, String msg, int code) {
                        addressData();
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        showToast(msg);
                    }
                });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter1, View view, int position) {

        Bundle b = new Bundle();
        b.putString("a_id", adapter.getData().get(position).getA_id() + "");
        b.putString("a_name", adapter.getData().get(position).getA_name() + "");
        b.putString("a_area", adapter.getData().get(position).getA_area() + "");
        b.putString("a_address", adapter.getData().get(position).getA_address() + "");
        b.putString("link_phone", adapter.getData().get(position).getLink_phone() + "");
        b.putString("is_default", adapter.getData().get(position).getIs_default() + "");
        startActivity(AddressEditActivity.class, b);

    }
}

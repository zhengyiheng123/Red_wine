package com.xyd.red_wine.suggest.chateau;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xyd.red_wine.R;
import com.xyd.red_wine.api.SuggestApi;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseFragment;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.main.home.WebViewActivity;
import com.xyd.red_wine.newsdetail.DetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/14
 * @time: 9:57
 * @description: 资讯——酒庄
 */

public class ChateauFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener, AdapterView.OnItemClickListener,BaseQuickAdapter.OnItemClickListener {

    @Bind(R.id.chateau_edt)
    EditText chateauEdt;
    @Bind(R.id.chateau_gv)
    GridView chateauGv;
    @Bind(R.id.base_rv)
    RecyclerView baseRv;
    @Bind(R.id.base_srl)
    SwipeRefreshLayout baseSrl;
    @Bind(R.id.chateau_iv_arrows)
    ImageView chateauIvArrows;
    private boolean showAll = false;
    private List<ChateauAddressModel.ChateatAddBean> chateauStrings;
    private GvAdapter gvAdapter;
    private int page = 1, num = 10, id = 0;
    private List<ChateauModel.ArticlesBean> list;
    private ChateauAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chateau;
    }

    @Override
    protected void initView() {
        baseSrl.setColorSchemeColors(Color.rgb(241, 173, 74));
        baseRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        initGvAdapter();
        initAdapter();
        getTitle();
        getChateauData();

    }

    private void getChateauData() {
        BaseApi.getRetrofit()
                .create(SuggestApi.class)
                .chateau_msg(page, num, id, chateauEdt.getText().toString().trim())
                .compose(RxSchedulers.<BaseModel<ChateauModel>>compose())
                .subscribe(new BaseObserver<ChateauModel>() {
                    @Override
                    protected void onHandleSuccess(ChateauModel chateauModel, String msg, int code) {
                        baseSrl.setRefreshing(false);
                        adapter.loadMoreComplete();
                        if (page == 1) {
                            adapter.setNewData(chateauModel.getArticles());
                        } else if (chateauModel.getArticles().size() > 0) {
                            adapter.addData(chateauModel.getArticles());

                        } else {
                            adapter.loadMoreEnd();
                        }
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        baseSrl.setRefreshing(false);
                        adapter.loadMoreComplete();
                        showToast(msg);
                    }
                });


    }

    private void getTitle() {
        BaseApi.getRetrofit()
                .create(SuggestApi.class)
                .chateau_add()
                .compose(RxSchedulers.<BaseModel<ChateauAddressModel>>compose())
                .subscribe(new BaseObserver<ChateauAddressModel>() {
                    @Override
                    protected void onHandleSuccess(ChateauAddressModel chateauAddressModel, String msg, int code) {
                        chateauStrings.addAll(chateauAddressModel.getChateat_add());
                        gvAdapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        showToast(msg);
                    }
                });

    }

    /**
     * 酒庄地区
     */
    private void initGvAdapter() {
        chateauStrings = new ArrayList<>();
        ChateauAddressModel.ChateatAddBean model = new ChateauAddressModel.ChateatAddBean();
        model.setT_id(0);
        model.setTitle("全部");
        chateauStrings.add(model);
        gvAdapter = new GvAdapter();
        chateauGv.setAdapter(gvAdapter);

    }

    /**
     * 酒庄
     */
    private void initAdapter() {
        list = new ArrayList<>();
        adapter = new ChateauAdapter(list, getActivity());
        adapter.setOnLoadMoreListener(this, baseRv);
//        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        adapter.setOnItemClickListener(this);
        baseRv.setAdapter(adapter);
        adapter.setEnableLoadMore(true);
    }

    @Override
    protected void initEvent() {
        chateauGv.setOnItemClickListener(this);
        baseSrl.setOnRefreshListener(this);
        chateauIvArrows.setOnClickListener(this);
        chateauEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                page=1;
                getChateauData();
            }
        });
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.chateau_iv_arrows:
                if (chateauStrings.size() > 4) {
                    if (showAll) {
                        showAll = false;
                        chateauIvArrows.setImageResource(R.mipmap.shuangjiantou);
                        gvAdapter.notifyDataSetChanged();
                    } else {
                        showAll = true;
                        chateauIvArrows.setImageResource(R.mipmap.shuangjiantou_top);
                        gvAdapter.notifyDataSetChanged();
                    }
                }
                break;
        }

    }


    @Override
    public void onRefresh() {
        page = 1;
        getChateauData();

    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        getChateauData();

    }

    /**
     *
     * @param parent
     * @param view
     * @param position
     * @param id1
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id1) {
        gvAdapter.setCheckPosition(position);
        id = chateauStrings.get(position).getT_id();
        page = 1;
        getChateauData();

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter1, View view, int position) {
        Bundle b=new Bundle();
        b.putString(WebViewActivity.TITLE,"酒庄介绍");
        b.putString(WebViewActivity.URL,adapter.getData().get(position).getA_content());
        startActivity(WebViewActivity.class,b);
    }


    class GvAdapter extends BaseAdapter {
        public int getCheckPosition() {
            return checkPosition;
        }

        public void setCheckPosition(int checkPosition) {
            this.checkPosition = checkPosition;
            notifyDataSetChanged();
        }

        private int checkPosition = 0;

        @Override
        public int getCount() {
            if (chateauStrings.size() > 4) {
                if (showAll)
                    return chateauStrings.size();
                else
                    return chateauStrings.size();
            } else {
                return chateauStrings.size();
            }
        }

        @Override
        public Object getItem(int position) {
            return chateauStrings.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_gv_chateau, null, false);
                holder = new ViewHolder();
                holder.cb = (CheckBox) convertView.findViewById(R.id.item_chateau_cb);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.cb.setText(chateauStrings.get(position).getTitle());
            if (checkPosition == position) {
                holder.cb.setChecked(true);
            } else {
                holder.cb.setChecked(false);
            }

            return convertView;
        }

        class ViewHolder {
            private CheckBox cb;
        }
    }
}

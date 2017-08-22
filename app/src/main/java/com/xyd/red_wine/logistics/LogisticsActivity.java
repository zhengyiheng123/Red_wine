package com.xyd.red_wine.logistics;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xyd.red_wine.R;
import com.xyd.red_wine.api.OrderApi;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.PublicStaticData;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.glide.GlideUtil;
import com.xyd.red_wine.order.OrderActivity;
import com.xyd.red_wine.order.OrderModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/19
 * @time: 11:07
 * @description: 物流
 */

public class LogisticsActivity extends BaseActivity {
    public static final String ORDER_DATA = "order_data";
    @Bind(R.id.base_title_back)
    TextView baseTitleBack;
    @Bind(R.id.base_title_title)
    TextView baseTitleTitle;
    @Bind(R.id.base_title_menu)
    ImageView baseTitleMenu;
    @Bind(R.id.logistics_iv)
    ImageView logisticsIv;
    @Bind(R.id.logistics_tv)
    TextView logisticsTv;
    @Bind(R.id.logistics_tv_state)
    TextView logisticsTvState;
    @Bind(R.id.logistics_tv_source)
    TextView logisticsTvSource;
    @Bind(R.id.logistics_tv_nums)
    TextView logisticsTvNums;
    @Bind(R.id.logistics_head)
    ImageView logisticsHead;
    @Bind(R.id.logistics_tv1)
    TextView logisticsTv1;
    @Bind(R.id.logistics_tv_name)
    TextView logisticsTvName;
    @Bind(R.id.logistics_phone)
    ImageView logisticsPhone;
    @Bind(R.id.logistics_rv)
    RecyclerView logisticsRv;
    @Bind(R.id.logistics_rl)
    RelativeLayout logisticsRl;

    @Bind(R.id.tv_empty)
    TextView tv_empty;
    private List<LogisticsModel.CheckBean> logisticsLists;
    private OrderModel.MyOrderBean orderBean;
    private LogisticsAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_logistics;
    }

    @Override
    protected void initView() {
        orderBean = (OrderModel.MyOrderBean) getIntent().getSerializableExtra(ORDER_DATA);
        GlideUtil.getInstance()
                .loadImage(this, logisticsIv, PublicStaticData.baseUrl + orderBean.getG_img(), true);
        baseTitleTitle.setText("查看物流");
        baseTitleMenu.setVisibility(View.INVISIBLE);
        logisticsLists = new ArrayList<>();
        logisticsRv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LogisticsAdapter();
        logisticsRv.setAdapter(adapter);
        if (orderBean.getOrder_status() == 2)
            logisticsTvState.setText("商家未发货");
        else
            getLogisticsData();

    }

    private void getLogisticsData() {
        BaseApi.getRetrofit()
                .create(OrderApi.class)
                .logistics(orderBean.getOrder_num())
                .compose(RxSchedulers.<BaseModel<LogisticsModel>>compose())
                .subscribe(new BaseObserver<LogisticsModel>() {
                    @Override
                    protected void onHandleSuccess(LogisticsModel logisticsModel, String msg, int code) {
                        logisticsTvState.setText(logisticsModel.getState());
                        logisticsTvSource.setText(logisticsModel.getOrders().getPostcom());
                        logisticsTvNums.setText(logisticsModel.getOrders().getExpress());
                        if (logisticsModel.getCheck().size()==0){
                            tv_empty.setVisibility(View.VISIBLE);
                        }else {
                            tv_empty.setVisibility(View.GONE);
                            logisticsLists.addAll(logisticsModel.getCheck());
                        }

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        showToast(msg);
                    }
                });
    }

    @Override
    protected void initEvent() {
        baseTitleBack.setOnClickListener(this);

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.base_title_back:
                finish();
        }

    }

    class LogisticsAdapter extends RecyclerView.Adapter<LogisticsAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(LogisticsActivity.this).inflate(R.layout.item_logistics, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (logisticsLists.size() == 1) {
                holder.point.setImageResource(R.mipmap.quanquan_liang);
                holder.lineTop.setVisibility(View.INVISIBLE);
                holder.lineBouttom.setVisibility(View.INVISIBLE);
                holder.content.setTextColor(Color.BLACK);
                holder.time.setTextColor(Color.BLACK);

            } else {
                if (position == 0) {

                    holder.point.setImageResource(R.mipmap.quanquan_liang);
                    holder.lineTop.setVisibility(View.INVISIBLE);
                    holder.lineBouttom.setVisibility(View.VISIBLE);
                    holder.content.setTextColor(Color.BLACK);
                    holder.time.setTextColor(Color.BLACK);

                } else if (position == logisticsLists.size() - 1) {
                    holder.point.setImageResource(R.mipmap.quanquan_hui);
                    holder.lineTop.setVisibility(View.VISIBLE);
                    holder.lineBouttom.setVisibility(View.INVISIBLE);
                    holder.content.setTextColor(getResources().getColor(R.color.material_textBlack_secondaryText));
                    holder.time.setTextColor(getResources().getColor(R.color.material_textBlack_secondaryText));

                } else {
                    holder.point.setImageResource(R.mipmap.quanquan_hui);
                    holder.lineTop.setVisibility(View.VISIBLE);
                    holder.lineBouttom.setVisibility(View.VISIBLE);
                    holder.content.setTextColor(getResources().getColor(R.color.material_textBlack_secondaryText));
                    holder.time.setTextColor(getResources().getColor(R.color.material_textBlack_secondaryText));
                }
            }
            holder.content.setText(logisticsLists.get(position).getContext());
            holder.time.setText(logisticsLists.get(position).getTime());

        }

        @Override
        public int getItemCount() {
            return logisticsLists.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {


            private final TextView content;
            private final TextView time;
            private final ImageView point;
            private final View lineTop;
            private final View lineBouttom;

            public ViewHolder(View itemView) {
                super(itemView);
                content = (TextView) itemView.findViewById(R.id.item_logistics_content);
                time = (TextView) itemView.findViewById(R.id.item_logistics_time);
                point = (ImageView) itemView.findViewById(R.id.item_logistics_point);
                lineTop = (View) itemView.findViewById(R.id.item_logistics_line1);
                lineBouttom = (View) itemView.findViewById(R.id.item_logistics_line2);


            }
        }
    }
}

package com.xyd.red_wine.order;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyd.red_wine.R;
import com.xyd.red_wine.base.PublicStaticData;
import com.xyd.red_wine.glide.GlideUtil;

import java.util.List;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/13
 * @time: 15:38
 * @description:
 */

public class OrderAdapter extends BaseQuickAdapter<OrderModel.MyOrderBean, BaseViewHolder> {
    private Context context;

    public OrderAdapter(@Nullable List<OrderModel.MyOrderBean> data, Context context) {
        super(R.layout.item_order, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderModel.MyOrderBean item) {
        GlideUtil.getInstance()
                .loadImage(context, (ImageView) helper.getView(R.id.order_iv), PublicStaticData.baseUrl + item.getG_img(), true);

        helper.setText(R.id.order_tv_title, item.getG_name());
        helper.setText(R.id.order_tv_title1, item.getG_sname());
        helper.setText(R.id.order_nums, "x" + item.getNum());
        helper.setText(R.id.order_item_tv_price, "￥" + item.getPrice());

        switch (item.getOrder_status()) {
            case 1:
                helper.setText(R.id.order_item_tv_state, "待付款");
                break;
            case 2:
                helper.setText(R.id.order_item_tv_state, "待发货");
                helper.setVisible(R.id.order_tv_left,false);
                helper.setVisible(R.id.order_tv_right,true);


                helper.setText(R.id.order_tv_right, "提醒发货");
                break;
            case 3:
                helper.setText(R.id.order_item_tv_state, "待收货");
                helper.setVisible(R.id.order_tv_left,true);
                helper.setVisible(R.id.order_tv_right,true);

                helper.setText(R.id.order_tv_left, "查看物流");
                helper.setText(R.id.order_tv_right, "确认收货");
                break;
            case 4:
                helper.setText(R.id.order_item_tv_state, "待评价");
                helper.setVisible(R.id.order_tv_left,true);
                helper.setVisible(R.id.order_tv_right,true);
                helper.setText(R.id.order_tv_left, "评价");
                helper.setText(R.id.order_tv_right, "再次购买");
                break;
            case 5:
                helper.setText(R.id.order_item_tv_state, "申请退款");
                break;
            case 6:
                helper.setText(R.id.order_item_tv_state, "已退款");
                break;
            case 7:
                helper.setText(R.id.order_item_tv_state, "交易成功");
                helper.setVisible(R.id.order_tv_left,false);
                helper.setVisible(R.id.order_tv_right,true);
                helper.setText(R.id.order_tv_right, "再次购买");
                break;

        }

        helper.addOnClickListener(R.id.order_tv_right);
        helper.addOnClickListener(R.id.order_tv_left);
    }
}

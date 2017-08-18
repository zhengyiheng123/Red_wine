package com.xyd.red_wine.commissionorder;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyd.red_wine.R;
import com.xyd.red_wine.base.PublicStaticData;
import com.xyd.red_wine.glide.GlideUtil;
import com.xyd.red_wine.utils.TimeUtils;

import java.util.List;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/13
 * @time: 15:07
 * @description:
 */

public class CommissionOrderAdapter extends BaseQuickAdapter<CommissionOrderModel.DeductBean, BaseViewHolder> {
    private Context context;

    public CommissionOrderAdapter(@Nullable List<CommissionOrderModel.DeductBean> data, Context context) {
        super(R.layout.item_commission_order, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CommissionOrderModel.DeductBean item) {
        GlideUtil.getInstance()
                .loadCircleImage(context, (ImageView) helper.getView(R.id.commission_iv_head), PublicStaticData.baseUrl + item.getHead_img());
        GlideUtil.getInstance()
                .loadImage(context, (ImageView) helper.getView(R.id.commission_iv), PublicStaticData.baseUrl + item.getG_img(), true);
        helper.setText(R.id.commission_tv_name, item.getNickname());
        if (item.getLev() == 1)

            helper.setText(R.id.commission_tv_level, "一级");
        else if (item.getLev() == 2)
            helper.setText(R.id.commission_tv_level, "二级");
        helper.setText(R.id.commission_tv_money, item.getGive_money());
        helper.setText(R.id.commission_tv_title, item.getG_name());
        helper.setText(R.id.commission_tv_title1, item.getG_sname());
        helper.setText(R.id.commission_item_tv_price, item.getG_price() + "x" + item.getG_num());
        helper.setText(R.id.commission_tv_time, TimeUtils.millis2String(item.getAddtime() * 1000, "yyyy-MM-dd"));


    }
}

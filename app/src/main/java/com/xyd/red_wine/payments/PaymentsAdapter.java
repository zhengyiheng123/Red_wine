package com.xyd.red_wine.payments;

import android.content.Context;
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
 * @date: 2017/7/11
 * @time: 10:30
 * @description:
 */

public class PaymentsAdapter extends BaseQuickAdapter<PaymentsModel.WelfareListBean, BaseViewHolder> {
    private Context context;
    public PaymentsAdapter(@Nullable List<PaymentsModel.WelfareListBean> data, Context context) {
        super(R.layout.item_payments, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, PaymentsModel.WelfareListBean item) {
        GlideUtil.getInstance().loadCircleImage(context, (ImageView) helper.getView(R.id.payments_iv),
                PublicStaticData.baseUrl+item.getG_img());
        helper.setText(R.id.payments_item_tv_price,"￥"+item.getG_price()+"\u3000\u3000"+"x"+item.getG_num());
        helper.setText(R.id.payments_tv_title,item.getG_name());
        helper.setText(R.id.payments_tv_title1,item.getG_sname());
        helper.setText(R.id.payments_tv_add,"+"+item.getDonate_money());
        long  time= Long.parseLong(item.getCreate_time());
        helper.setText(R.id.payments_tv_time, TimeUtils.millis2String(time*1000,"yyyy-MM-dd"));
    }
}

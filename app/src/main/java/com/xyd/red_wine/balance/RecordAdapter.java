package com.xyd.red_wine.balance;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyd.red_wine.R;
import com.xyd.red_wine.utils.TimeUtils;

import java.util.List;

/**
 * @author: zhaoxiaolei
 * @date: 2017/8/15
 * @time: 16:49
 * @description:
 */

public class RecordAdapter extends BaseQuickAdapter<CashValueModel.CashValueBean, BaseViewHolder> {

    private Context context;

    public RecordAdapter(@Nullable List<CashValueModel.CashValueBean> data, Context context) {
        super(R.layout.item_record, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CashValueModel.CashValueBean item) {
        if (item.getWe_price().contains("-")) {
            helper.setVisible(R.id.item_record_user, true);
            helper.setText(R.id.item_record_num, "订单号：" + item.getRecharge_num());
            helper.setText(R.id.item_record_money, "提现金额：" + item.getWe_price().replace("-", ""));
            helper.setText(R.id.item_record_time, "时间：" + TimeUtils.millis2String(item.getCreate_time() * 1000, "yyyy-MM-dd HH:mm"));
            helper.setText(R.id.item_record_user, "提现账号：" + item.getPay_account());
            String s = item.getRl_state() == 1 ? "待支付" : "已支付";
            helper.setText(R.id.item_record_type, "状态：" + s);
        } else {
            helper.setVisible(R.id.item_record_user, false);
            helper.setText(R.id.item_record_num, "订单号：" + item.getRecharge_num());
            helper.setText(R.id.item_record_money, "充值金额：" + item.getWe_price().replace("-", ""));
            helper.setText(R.id.item_record_time, "时间：" + TimeUtils.millis2String(item.getCreate_time() * 1000, "yyyy-MM-dd HH:mm"));
            if (item.getRl_state() == 1)

                helper.setText(R.id.item_record_type, "状态：支付取消");
            else
                helper.setText(R.id.item_record_type, "状态：已支付");

        }

    }
}

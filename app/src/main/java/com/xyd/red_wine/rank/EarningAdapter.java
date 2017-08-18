package com.xyd.red_wine.rank;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
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
 * @time: 12:00
 * @description:
 */

public class EarningAdapter extends BaseQuickAdapter<RankModel.RevenueBean, BaseViewHolder> {
    private Context context;

    public EarningAdapter(@Nullable List<RankModel.RevenueBean> data, Context context) {
        super(R.layout.item_rank, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, RankModel.RevenueBean item) {
        GlideUtil.getInstance().loadCircleImage(context, (ImageView) helper.getView(R.id.rank_iv), PublicStaticData.baseUrl + item.getHead_img());
        helper.setText(R.id.rank_tv_pay, "总收益" + item.getTotal_revenue() + "元");
        Log.e("rank_tv_ranking", getParentPosition(item) +
                "");
        helper.setText(R.id.rank_tv_name, item.getNickname());
        helper.setText(R.id.rank_tv_ranking, item.getLev()+"");



    }
}

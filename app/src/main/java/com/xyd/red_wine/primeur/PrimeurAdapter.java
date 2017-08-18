package com.xyd.red_wine.primeur;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyd.red_wine.R;
import com.xyd.red_wine.base.PublicStaticData;
import com.xyd.red_wine.glide.GlideUtil;
import com.xyd.red_wine.winesteward.NewsModel;

import java.util.List;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/13
 * @time: 10:20
 * @description: 红酒管家
 */

public class PrimeurAdapter extends BaseQuickAdapter<PrimeurModel.ArticlesBean, BaseViewHolder> {
    private Context context;

    public PrimeurAdapter(@Nullable List<PrimeurModel.ArticlesBean> data, Context context) {
        super(R.layout.item_news, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, PrimeurModel.ArticlesBean item) {
        if (item.getA_img().size() > 2) {
            helper.setVisible(R.id.news_type2, true);
            helper.setVisible(R.id.news_rl_type1, false);
            helper.setText(R.id.news_type12_title, item.getA_title());
            helper.setText(R.id.news_type2_time, item.getCreate_time());
            helper.addOnClickListener(R.id.news_type2_delete);
            GlideUtil.getInstance()
                    .loadImage(context, (ImageView) helper.getView(R.id.news_type2_iv1), PublicStaticData.baseUrl + item.getA_img().get(0), true);
            GlideUtil.getInstance()
                    .loadImage(context, (ImageView) helper.getView(R.id.news_type2_iv2), PublicStaticData.baseUrl + item.getA_img().get(1), true);
            GlideUtil.getInstance()
                    .loadImage(context, (ImageView) helper.getView(R.id.news_type2_iv3), PublicStaticData.baseUrl + item.getA_img().get(2), true);


        } else {
            helper.setVisible(R.id.news_type2, false);
            helper.setVisible(R.id.news_rl_type1, true);

            helper.setText(R.id.news_type1_title, item.getA_title());
            helper.setText(R.id.news_type1_time, item.getCreate_time());
            helper.addOnClickListener(R.id.news_type1_delete);
            if (item.getA_img().size() > 0)
                GlideUtil.getInstance()
                        .loadImage(context, (ImageView) helper.getView(R.id.news_type1_iv), PublicStaticData.baseUrl + item.getA_img().get(0), true);
        }

    }
}

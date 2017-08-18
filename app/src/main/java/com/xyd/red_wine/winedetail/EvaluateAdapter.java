package com.xyd.red_wine.winedetail;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import android.text.TextUtils;
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
 * @date: 2017/7/14
 * @time: 16:08
 * @description:
 */

public class EvaluateAdapter extends BaseQuickAdapter<EvaluateModel.CommentsBean ,BaseViewHolder> {

   private Context context;
    public EvaluateAdapter(@Nullable List<EvaluateModel.CommentsBean> data, Context context) {
        super(R.layout.item_wine_evaluate, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, EvaluateModel.CommentsBean item) {
        GlideUtil.getInstance()
                .loadCircleImage(context, (ImageView) helper.getView(R.id.evaluate_iv),
                        PublicStaticData.baseUrl+item.getHead_img()
                        );

        if (TextUtils.isEmpty(item.getImg_1())){
            helper.setVisible(R.id.evaluate_ll,false);
        }else {
            helper.setVisible(R.id.evaluate_ll,true);
            GlideUtil.getInstance()
                    .loadImage(context, (ImageView) helper.getView(R.id.evaluate_ll_iv1),
                            PublicStaticData.baseUrl+item.getImg_1(),
                            true
                    );
        }


        if (TextUtils.isEmpty(item.getImg_2())){
            helper.setVisible(R.id.evaluate_ll_iv2,false);
        }else {
            helper.setVisible(R.id.evaluate_ll_iv2,true);
            GlideUtil.getInstance()
                    .loadImage(context, (ImageView) helper.getView(R.id.evaluate_ll_iv2),
                            PublicStaticData.baseUrl+item.getImg_2(),
                            true
                    );
        }


        helper.setText(R.id.evaluate_content,item.getContent());
        helper.setText(R.id.evaluate_name,item.getNickname());
         long  time= Long.parseLong(item.getPubtime());
        helper.setText(R.id.evaluate_time, TimeUtils.millis2String(time*1000,"yyyy-MM-dd")+"\u3000\u3000"+item.getG_name()+"*"+item.getNum());

        //    酒名+数量


    }
}

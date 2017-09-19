package com.xyd.red_wine.view_img;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyd.red_wine.R;
import com.xyd.red_wine.base.PublicStaticData;
import com.xyd.red_wine.glide.GlideUtil;
import com.xyd.red_wine.view.SmartImageveiw;

import java.util.List;

/**
 * Created by Zheng on 2017/9/19.
 */

public class ImageViewAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    private Context context;
    public ImageViewAdapter(@Nullable List<String> data, Context context) {
        super(R.layout.item_img, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        GlideUtil.getInstance().loadImage(context,(ImageView) helper.getView(R.id.iv_item), item,true);
    }
}

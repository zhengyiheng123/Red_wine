package com.xyd.red_wine.suggest.video;

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
 * @date: 2017/7/14
 * @time: 10:42
 * @description:
 */

public class VideoAdapter extends BaseQuickAdapter<VideoModel.ArticlesBean,BaseViewHolder> {
    private Context context;

    public VideoAdapter(@Nullable List<VideoModel.ArticlesBean> data, Context context) {
        super(R.layout.item_video, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoModel.ArticlesBean item) {
        if (item.getA_img().size() > 0)
            GlideUtil.getInstance()
                    .loadImage(context, (ImageView) helper.getView(R.id.image_iv), PublicStaticData.baseUrl + item.getA_img().get(0), true);
        helper.setText(R.id.image_tv, item.getA_title());

    }
}

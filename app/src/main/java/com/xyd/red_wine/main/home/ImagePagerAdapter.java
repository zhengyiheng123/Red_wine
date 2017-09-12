package com.xyd.red_wine.main.home;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xyd.red_wine.R;
import com.xyd.red_wine.base.PublicStaticData;
import com.xyd.red_wine.glide.GlideUtil;
import com.xyd.red_wine.utils.ConvertUtils;
import com.xyd.red_wine.view.RoundImageView;

import java.util.List;

/**
 * Created by twiceYuan on 9/13/16.
 * Email: i@twiceyuan.com
 * Site: http://twiceyuan.com
 */
public class ImagePagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<HomeModel.CarouselBean> url;
    public ImagePagerAdapter(Context context,List<HomeModel.CarouselBean> url) {
        this.mContext=context;
        this.url=url;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        RoundImageView view= new RoundImageView(mContext);
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        view.setType(RoundImageView.TYPE_ROUND);
        GlideUtil.getInstance().loadImage(mContext,view, PublicStaticData.baseUrl +url.get(position).getAdv_imgs(),false);
        view.setLayoutParams(new LinearLayout.LayoutParams(ConvertUtils.dp2px(200), ConvertUtils.dp2px(100)));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return url.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }
}

package com.xyd.red_wine.view_img;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.recker.flybanner.FlyBanner;
import com.xyd.red_wine.R;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.base.PublicStaticData;
import com.xyd.red_wine.glide.GlideUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Zheng on 2017/9/19.
 */

public class ReviewImageActivity extends BaseActivity {
    @Bind(R.id.vp_img)
    ViewPager vp_img;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    private List<String> imgList=new ArrayList<>();
    private List<ImageView> vieList=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_img;
    }

    @Override
    protected void initView() {
        imgList.clear();
        vieList.clear();
        imgList = getIntent().getStringArrayListExtra("imgList");
        for (int i=0;i<imgList.size();i++){
            ImageView imageView=new ImageView(getApplicationContext());
            RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            GlideUtil.getInstance().loadImage(getApplicationContext(),imageView,imgList.get(i),true);
            params.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
            imageView.setLayoutParams(params);
            vieList.add(imageView);
        }
        vp_img.setAdapter(new MyAdapter());
    }

    @Override
    protected void initEvent() {
        iv_back.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }
    public class MyAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return vieList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(vieList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(vieList.get(position));
            return vieList.get(position);
        }
    }
}

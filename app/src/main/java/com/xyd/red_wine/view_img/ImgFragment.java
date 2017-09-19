package com.xyd.red_wine.view_img;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xyd.red_wine.R;
import com.xyd.red_wine.base.BaseFragment;

import butterknife.Bind;

/**
 * Created by Zheng on 2017/9/19.
 */

@SuppressLint("ValidFragment")
public class ImgFragment extends BaseFragment {
    String img;
    public ImgFragment(String img) {
        this.img=img;
    }

    @Bind(R.id.iv_item)
    ImageView iv_item;
    @Override
    protected int getLayoutId() {
        return R.layout.item_img;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void widgetClick(View v) {

    }
}

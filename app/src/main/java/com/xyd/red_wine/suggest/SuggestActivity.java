package com.xyd.red_wine.suggest;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xyd.red_wine.R;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.suggest.chateau.ChateauFragment;
import com.xyd.red_wine.suggest.image.ImageFragment;
import com.xyd.red_wine.suggest.introduction.IntroductionFragment;
import com.xyd.red_wine.suggest.video.VideoFragment;

import butterknife.Bind;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/14
 * @time: 9:51
 * @description: 咨询界面
 */

public class SuggestActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {

    @Bind(R.id.base_title_back)
    TextView baseTitleBack;
    @Bind(R.id.base_title_title)
    TextView baseTitleTitle;
    @Bind(R.id.base_title_menu)
    ImageView baseTitleMenu;
    @Bind(R.id.suggest_rg_introduction)
    RadioButton suggestRgIntroduction;
    @Bind(R.id.suggest_rg_chateau)
    RadioButton suggestRgChateau;
    @Bind(R.id.suggest_rg_image)
    RadioButton suggestRgImage;
    @Bind(R.id.suggest_rg_video)
    RadioButton suggestRgVideo;
    @Bind(R.id.suggest_rg)
    RadioGroup suggestRg;
    @Bind(R.id.suggest_vp)
    ViewPager suggestVp;
    private Fragment[] fragments;
    private IntroductionFragment introductionFragment;
    private ChateauFragment chateauFragment;
    private ImageFragment imageFragment;
    private VideoFragment videoFragment;
    private int index=0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_suggest;
    }

    @Override
    protected void initView() {
        baseTitleTitle.setText("资讯");
        baseTitleMenu.setVisibility(View.INVISIBLE);
        introductionFragment = new IntroductionFragment();
        chateauFragment = new ChateauFragment();
        imageFragment = new ImageFragment();
        videoFragment = new VideoFragment();
        fragments = new Fragment[]{introductionFragment, chateauFragment, imageFragment, videoFragment};
        suggestRg.check(R.id.suggest_rg_introduction);

        MyViewPagerAdapter adapter=new MyViewPagerAdapter(getSupportFragmentManager());
        suggestVp.setOffscreenPageLimit(3);
        suggestVp.setAdapter(adapter);
        suggestVp.setCurrentItem(index,true);


    }

    @Override
    protected void initEvent() {
        baseTitleBack.setOnClickListener(this);
        suggestRg.setOnCheckedChangeListener(this);
        suggestVp.addOnPageChangeListener(this);

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.base_title_back:
                finish();
                break;
        }

    }

    /**
     * ViewPager
     *
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * ViewPager
     *
     * @param position
     */
    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                suggestRg.check(R.id.suggest_rg_introduction);
                break;
            case 1:
                suggestRg.check(R.id.suggest_rg_chateau);
                break;
            case 2:
                suggestRg.check(R.id.suggest_rg_image);
                break;
            case 3:
                suggestRg.check(R.id.suggest_rg_video);
                break;
        }

    }

    /**
     * ViewPager
     *
     * @param state
     */
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * RadioGroup
     *
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.suggest_rg_introduction:
                index=0;
                break;
            case R.id.suggest_rg_chateau:
                index=1;
                break;
            case R.id.suggest_rg_image:
                index=2;
                break;
            case R.id.suggest_rg_video:
                index=3;
                break;
        }
        suggestVp.setCurrentItem(index,true);
    }


     class  MyViewPagerAdapter extends FragmentStatePagerAdapter{

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }
    }
}

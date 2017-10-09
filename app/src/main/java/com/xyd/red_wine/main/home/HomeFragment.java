package com.xyd.red_wine.main.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;
import com.recker.flybanner.FlyBanner;
import com.xyd.red_wine.R;
import com.xyd.red_wine.api.HomeApi;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseFragment;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.PublicStaticData;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.glide.GlideUtil;
import com.xyd.red_wine.kefu.ChatActivity;
import com.xyd.red_wine.main.home.infiniteViewPager.InfinitePagerAdapter;
import com.xyd.red_wine.main.home.infiniteViewPager.InfiniteViewPager;
import com.xyd.red_wine.newsdetail.DetailActivity;
import com.xyd.red_wine.permissions.PermissionUtils;
import com.xyd.red_wine.permissions.PermissionsManager;
import com.xyd.red_wine.promptdialog.PromptDialog;
import com.xyd.red_wine.utils.LogUtil;
import com.xyd.red_wine.view.DragFloatActionButton;
import com.xyd.red_wine.view.FloatTouchListener;
import com.xyd.red_wine.view.MenuPopWindow;
import com.xyd.red_wine.view.MyListView;
import com.xyd.red_wine.view.NoticeView;
import com.xyd.red_wine.view.banner.Banner;
import com.xyd.red_wine.view.banner.OnBannerListener;
import com.xyd.red_wine.winedetail.WineDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: zhaoxiaolei
 * @date: 2017/6/12
 * @time: 15:06
 * @description: 首页
 */

public class HomeFragment extends BaseFragment {

    @Bind(R.id.menu)
    ImageView menu;
    @Bind(R.id.banner)
    FlyBanner ivBanner;
    @Bind(R.id.home_iv_introduce)
    ImageView homeIvIntroduce;
    @Bind(R.id.home_iv_recommend)
    ImageView homeIvRecommend;
    @Bind(R.id.home_iv_manage)
    ImageView homeIvManage;
    @Bind(R.id.home_news_iv)
    ImageView homeNewsIv;
    @Bind(R.id.home_news_tv)
    TextView homeNewsTv;
    @Bind(R.id.home_ll_news)
    LinearLayout homeNewsLl;
    @Bind(R.id.home_banner)
    Banner homeBanner;
    @Bind(R.id.home_news_notice)
    NoticeView homeNewsNotice;
    @Bind(R.id.home_srl)
    SwipeRefreshLayout homeSrl;
    @Bind(R.id.iv_service)
    DragFloatActionButton iv_service;
    @Bind(R.id.home_frame)
    FrameLayout homeFrame;
    @Bind(R.id.home_rv)
    RecyclerView mHomeLV;
    @Bind(R.id.scroll_view)
    ScrollView mScrollview;
    @Bind(R.id.ll_service_mobile)
    LinearLayout ll_service_mobile;
    @Bind(R.id.iv_service_tel)
    ImageView mIvservicetel;
//    @Bind(R.id.id_viewpager)
//    InfiniteViewPager minfinatePager;

    private HomeMyAdapter homeMyAdapter;

    private List<HomeModel.CarouselBean> banner;
    private HomeModel model;
    private List<HomeModel.PhotoMsgBean> newsList;
    private PromptDialog dialog;
    private ArrayList<HomeModel.GoodsBean> goodsBeen;
    //轮播图
    List<String> bannerList=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
//        addFloatBtn();
//        setTouchListener();
        dialog=new PromptDialog(getActivity());
        dialog.showLoading("");
        getData();
        banner = new ArrayList<>();
        homeBanner.isAutoPlay(false);
        homeBanner.setImages(banner);
        newsList = new ArrayList<>();
        homeBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Bundle b = new Bundle();
                b.putString(WebViewActivity.TITLE, "活动");
                 b.putString(WebViewActivity.URL, model.getCarousel().get(position).getAdv_url());
                startActivity(WebViewActivity.class, b);
            }
        });
        initAdapter();
    }

    private void initAdapter() {
        goodsBeen = new ArrayList<>();
        FullyLinearLayoutManager fullyManager=new FullyLinearLayoutManager(getActivity());
        fullyManager.setScrollEnabled(false);
        mHomeLV.setLayoutManager(fullyManager);
        mHomeLV.setNestedScrollingEnabled(true);
        homeMyAdapter=new HomeMyAdapter(goodsBeen,getContext());
        mHomeLV.setAdapter(homeMyAdapter);
    }
    /**
     * 网络获取首页数据
     */
    private void getData() {
        BaseApi.getRetrofit()
                .create(HomeApi.class)
                .home()
                .compose(RxSchedulers.<BaseModel<HomeModel>>compose())
                .subscribe(new BaseObserver<HomeModel>() {
                    @Override
                    protected void onHandleSuccess(HomeModel homeModel, String msg, int code) {

                        model = homeModel;
                        banner.clear();
                        banner.addAll(homeModel.getCarousel());
                        homeBanner.update(homeModel.getCarousel());
//                        GlideUtil.getInstance()
//                                .loadImage(getActivity(), ivBanner, PublicStaticData.baseUrl + homeModel.getBanner().getAdv_imgs(), true);
                        newsList.clear();
                        newsList.addAll(homeModel.getPhoto_msg());
                        List<String> strings = new ArrayList<String>();
                        for (HomeModel.PhotoMsgBean bean : newsList) {
                            strings.add(bean.getA_title());
                        }
                        if (strings.size() == 0)
                            strings.add("暂无最新图文消息");
                        homeNewsNotice.setNoticeList(strings);
                        dialog.dismissImmediately();
                        homeSrl.setRefreshing(false);
                        homeMyAdapter.setNewData(homeModel.getGoods());
//                        setInificatepager();
                        initBanner();
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        homeSrl.setRefreshing(false);
                        dialog.dismissImmediately();
                        showToast(msg);
                    }
                });
    }
    //  初始化首页轮播图
    private void initBanner() {
        bannerList.clear();
        if (!(model.getCarousel()==null || model.getCarousel().size() == 0)){
            for (int i =0 ;i<model.getCarousel().size();i++){
                bannerList.add(PublicStaticData.baseUrl+model.getCarousel().get(i).getAdv_imgs());
            }
            ivBanner.setImagesUrl(bannerList);
            ivBanner.startAutoPlay();
        }
    }

    @Override
    protected void initEvent() {
        homeIvIntroduce.setOnClickListener(this);
        homeIvManage.setOnClickListener(this);
        homeIvRecommend.setOnClickListener(this);
        ll_service_mobile.setOnClickListener(this);
        mIvservicetel.setOnClickListener(this);
//        ivBanner.setOnClickListener(this);
        homeNewsLl.setOnClickListener(this);
        iv_service.setOnClickListener(this);

        homeFrame.setOnClickListener(this);
        homeNewsNotice.setOnItemClickListener(new NoticeView.OnItemClickListener() {
            @Override
            public void onItemClick(TextView view, int position) {
                if (newsList.size() > 0) {
                    // showToast(newsList.get(position).getA_title());
                    Bundle b = new Bundle();
                    b.putInt(DetailActivity.NEWS_ID, newsList.get(position).getA_id());
                    b.putInt(DetailActivity.COLLECT, newsList.get(position).getCollect());
                    b.putString(DetailActivity.NEWS_URL, newsList.get(position).getA_content());
                    startActivity(DetailActivity.class, b);
                }
            }
        });
        homeSrl.setColorSchemeColors(Color.rgb(241, 173, 74));
        homeSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        homeMyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle=new Bundle();
                bundle.putString(WineDetailActivity.G_ID,homeMyAdapter.getData().get(position).getG_id()+"");
                Log.e("position",position+"");
//                Log.e("g_id",adapter.getData().get(position).getG_id()+"");
                startActivity(WineDetailActivity.class,bundle);
            }
        });
        //轮播图点击事件
        ivBanner.setOnItemClickListener(new FlyBanner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle b = new Bundle();
                b.putString(WebViewActivity.TITLE, "活动");
                b.putString(WebViewActivity.URL, model.getCarousel().get(position).getAdv_url());
                startActivity(WebViewActivity.class, b);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    @Override
    public void widgetClick(View v) {
        Bundle b;
        switch (v.getId()) {
            case R.id.iv_service_tel:
                if (Build.VERSION.SDK_INT>=23){
                    PermissionUtils.phone(getActivity(), new PermissionUtils.OnPermissionResult() {
                        @Override
                        public void onGranted() {
                            Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:40015181111" ));
                            startActivity(intent);
                        }
                    });
                }else {
                    Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:40015181111" ));
                    startActivity(intent);
                }

                break;
            case R.id.home_iv_introduce:
                b = new Bundle();
                b.putString(WebViewActivity.TITLE, "小酒详情");
                 b.putString(WebViewActivity.URL, model.getList().getWine_introduce());
                //b.putString(WebViewActivity.URL, "http://m.wine-world.com/winery/chateau-lafite-rothschild/ad679838-5304-4973-be95-0162fa5d2d7c");

                startActivity(WebViewActivity.class, b);
                break;
            case R.id.home_iv_recommend:
                b = new Bundle();
                b.putString(WebViewActivity.TITLE, "推广规则");
                 b.putString(WebViewActivity.URL, model.getList().getRecommend_rule());
              //  b.putString(WebViewActivity.URL, "http://m.wine-world.com/winery/chateau-lafite-rothschild/ad679838-5304-4973-be95-0162fa5d2d7c");
                startActivity(WebViewActivity.class, b);
                break;
            case R.id.home_iv_manage:
                b = new Bundle();
                b.putString(WebViewActivity.TITLE, "企业介绍");
                 b.putString(WebViewActivity.URL, model.getList().getManage_rule());
              //  b.putString(WebViewActivity.URL, "http://m.wine-world.com/winery/chateau-lafite-rothschild/ad679838-5304-4973-be95-0162fa5d2d7c");
                startActivity(WebViewActivity.class, b);
                break;
            case R.id.home_frame:
                MenuPopWindow popWindow = new MenuPopWindow(getActivity());
                popWindow.showAsDropDown(menu, 0, 10);
                break;

            case R.id.home_ll_news:

                break;
            case R.id.banner:
                b = new Bundle();
                b.putString(WebViewActivity.TITLE, "活动");
                  b.putString(WebViewActivity.URL, model.getBanner().getAdv_url());
               // b.putString(WebViewActivity.URL, "http://m.wine-world.com/culture/pj/20170728114754742");
                startActivity(WebViewActivity.class, b);
                break;
            case R.id.iv_service:
                if (ChatClient.getInstance().isLoggedInBefore())
                    startActivity(ChatActivity.class);
                else
                    loginHx("qiaozhijinhan"+PublicStaticData.sharedPreferences.getInt("id",0),"123456");

                break;

        }

    }
    //登录环信
    private void loginHx(final String uname, final String upwd) {


        // login huanxin server
        ChatClient.getInstance().login(uname, upwd, new Callback() {
            @Override
            public void onSuccess() {
                startActivity(ChatActivity.class);
            }

            @Override
            public void onError(int code, String error) {
                LogUtil.e(code+error);
                // loginHx("qiaozhijinhan"+PublicStaticData.sharedPreferences.getInt("id",0),"123456");
            }

            @Override
            public void onProgress(int progress, String status) {
                LogUtil.e(progress+status);
                //  loginHx("qiaozhijinhan"+PublicStaticData.sharedPreferences.getInt("id",0),"123456");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        homeNewsNotice.start();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
        }else {
            if (mHomeLV!=null){
                mScrollview.scrollTo(0,0);
                mHomeLV.setFocusable(false);
                mHomeLV.setFocusableInTouchMode(false);
            }
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (homeNewsNotice != null & homeNewsNotice.isRunning())
            homeNewsNotice.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
    public int px2dip(float pxValue){
        final float scale = getActivity().getResources().getDisplayMetrics().density;
        return (int) (pxValue/scale+0.5f);
    }


}

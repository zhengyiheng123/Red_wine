package com.xyd.red_wine.newsdetail;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.editorpage.ShareActivity;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.xyd.red_wine.R;
import com.xyd.red_wine.api.CollectApi;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.EmptyModel;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.generalize.GeneralizeActivity;
import com.xyd.red_wine.utils.ToastUtils;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.R.attr.progress;
import static android.R.attr.thumb;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/14
 * @time: 14:14
 * @description:
 */

public class DetailActivity extends BaseActivity {
    public static final String NEWS_ID = "news_id";
    public static final String NEWS_URL = "news_url";
    public static final String COLLECT = "collect";
    public static final String TITLE = "title";
    @Bind(R.id.detail_back)
    TextView detailBack;
    @Bind(R.id.detail_title)
    TextView detailTitle;
    @Bind(R.id.detail_collect)
    ImageView detailCollect;
    @Bind(R.id.detail_share)
    TextView detailShare;
    @Bind(R.id.detail_wv)
    WebView detailWv;
    @Bind(R.id.progress)
    ProgressBar progress;
    private String content;
    private int id;
    private int isCollect;

    private UMShareListener mShareListener;
    private String title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initView() {
        mShareListener = new CustomShareListener(this);
        id = getIntent().getIntExtra(NEWS_ID, 0);
        Log.e("newsid:" ,id+"");
        content = getIntent().getStringExtra(NEWS_URL);
        title = getIntent().getStringExtra(TITLE);
        //  content="http://m.wine-world.com/winery/chateau-lafite-rothschild/ad679838-5304-4973-be95-0162fa5d2d7c";
        isCollect = getIntent().getIntExtra(COLLECT, 0);
        if (isCollect == 0)
            detailCollect.setImageResource(R.mipmap.pingjia_weixuanzhong);
        else
            detailCollect.setImageResource(R.mipmap.pingjia_xuanzhong);
        initWebView();
    }

    @Override
    protected void initEvent() {
        detailBack.setOnClickListener(this);
        detailCollect.setOnClickListener(this);
        detailShare.setOnClickListener(this);


    }

    private void initWebView() {
        WebSettings ws = detailWv.getSettings();
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);// 隐藏缩放按钮
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);// 排版适应屏幕
        //ws.setUseWideViewPort(true);// 可任意比例缩放
        ws.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        ws.setSavePassword(true);
        ws.setSaveFormData(true);// 保存表单数据
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);

        detailWv.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100){
                    progress.setVisibility(View.GONE);
                }else {
                    progress.setProgress(newProgress);
                }
            }
        });
        detailWv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });
        detailWv.loadUrl(content);
        // detailWv.loadUrl("file:///android_asset/show.html");
        //detailWv.loadUrl("https://m.baidu.com/?from=2001a#iact=wiseindex%2Ftabs%2Fnews%2Factivity%2Fnewsdetail%3D%257B%2522linkData%2522%253A%257B%2522name%2522%253A%2522iframe%252Fmib-iframe%2522%252C%2522id%2522%253A%2522feed%2522%252C%2522index%2522%253A0%252C%2522url%2522%253A%2522https%253A%252F%252Ffeed.baidu.com%252Ffeed%252Fdata%252Fwise%252Flandingpage%253Fs_type%253Dnews%2526nid%253D17203467375904090668%2526n_type%253D0%2526p_from%253D2%2522%252C%2522title%2522%253A%2522%25E8%2585%25BE%25E8%25AE%25AF%25E6%2596%25B0%25E9%2597%25BB%2522%257D%257D");

    }


    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.detail_back:
                finish();
                break;
            case R.id.detail_collect:
                if (isCollect == 0) {
                    addCollect();
                } else {
                    delCollect();
                }


                break;
            case R.id.detail_share:
                new ShareAction(this)
                        .setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.SINA)
                        .setShareboardclickCallback(new ShareBoardlistener() {
                            @Override
                            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                if (share_media == SHARE_MEDIA.QQ || share_media== SHARE_MEDIA.QZONE){
                                    UMWeb web = new UMWeb(content);
                                    web.setTitle(title);
                                    web.setDescription(title);
                                    new ShareAction(DetailActivity.this).withMedia(web)
                                            .setPlatform(share_media)
                                            .setCallback(mShareListener)
                                            .share();
                                }else {
                                    UMWeb web = new UMWeb(content);
                                    web.setTitle(title);
                                    web.setDescription(title);
                                    web.setThumb(new UMImage(DetailActivity.this, R.mipmap.logo000));
                                    new ShareAction(DetailActivity.this).withMedia(web)
                                            .setPlatform(share_media)
                                            .setCallback(mShareListener)
                                            .share();
                                }
                            }
                        }).open();




        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
        detailWv.destroy();

    }

    private void addCollect() {
        BaseApi.getRetrofit()
                .create(CollectApi.class)
                .addCollect(id + "")
                .compose(RxSchedulers.<BaseModel<EmptyModel>>compose())
                .subscribe(new BaseObserver<EmptyModel>() {
                    @Override
                    protected void onHandleSuccess(EmptyModel emptyModel, String msg, int code) {
                        isCollect = 1;
                        detailCollect.setImageResource(R.mipmap.pingjia_xuanzhong);
                        ToastUtils.show(msg);
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        showToast(msg);
                    }
                });

    }


    private void delCollect() {
        BaseApi.getRetrofit()
                .create(CollectApi.class)
                .delCollect(id + "")
                .compose(RxSchedulers.<BaseModel<EmptyModel>>compose())
                .subscribe(new BaseObserver<EmptyModel>() {
                    @Override
                    protected void onHandleSuccess(EmptyModel emptyModel, String msg, int code) {
                        isCollect = 0;
                        detailCollect.setImageResource(R.mipmap.pingjia_weixuanzhong);
                        ToastUtils.show(msg);
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        showToast(msg);
                    }
                });
    }


    private static class CustomShareListener implements UMShareListener {

        private WeakReference<DetailActivity> mActivity;

        private CustomShareListener(DetailActivity activity) {
            mActivity = new WeakReference(activity);
        }

        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {

            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(mActivity.get(), platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                        && platform != SHARE_MEDIA.EMAIL
                        && platform != SHARE_MEDIA.FLICKR
                        && platform != SHARE_MEDIA.FOURSQUARE
                        && platform != SHARE_MEDIA.TUMBLR
                        && platform != SHARE_MEDIA.POCKET
                        && platform != SHARE_MEDIA.PINTEREST

                        && platform != SHARE_MEDIA.INSTAGRAM
                        && platform != SHARE_MEDIA.GOOGLEPLUS
                        && platform != SHARE_MEDIA.YNOTE
                        && platform != SHARE_MEDIA.EVERNOTE) {
                    Toast.makeText(mActivity.get(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
                }

            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                    && platform != SHARE_MEDIA.EMAIL
                    && platform != SHARE_MEDIA.FLICKR
                    && platform != SHARE_MEDIA.FOURSQUARE
                    && platform != SHARE_MEDIA.TUMBLR
                    && platform != SHARE_MEDIA.POCKET
                    && platform != SHARE_MEDIA.PINTEREST

                    && platform != SHARE_MEDIA.INSTAGRAM
                    && platform != SHARE_MEDIA.GOOGLEPLUS
                    && platform != SHARE_MEDIA.YNOTE
                    && platform != SHARE_MEDIA.EVERNOTE) {
                Toast.makeText(mActivity.get(), platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
                if (t != null) {
                    com.umeng.socialize.utils.Log.d("throw", "throw:" + t.getMessage());
                }
            }

        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {

            Toast.makeText(mActivity.get(), platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
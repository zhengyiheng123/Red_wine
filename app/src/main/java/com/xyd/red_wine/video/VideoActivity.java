package com.xyd.red_wine.video;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;
import com.xyd.red_wine.R;
import com.xyd.red_wine.api.CollectApi;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.EmptyModel;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.newsdetail.DetailActivity;
import com.xyd.red_wine.utils.StatusBarUtil;
import com.xyd.red_wine.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/18
 * @time: 14:37
 * @description:
 */

public class VideoActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String VIDEO_ID = "video_id";
    public static final String VIDEO_URL = "video_url";
    public static final String COLLECT = "collect";
    public static final String TITLE="title";
    @Bind(R.id.video_horizontal)
    FrameLayout videoHorizontal;
    @Bind(R.id.base_title_back)
    TextView baseTitleBack;
    @Bind(R.id.base_title_title)
    TextView baseTitleTitle;
    @Bind(R.id.base_title_menu)
    ImageView baseTitleMenu;
    @Bind(R.id.video_wb)
    WebView wv;

    @Bind(R.id.video_ll)
    LinearLayout videoLl;
    @Bind(R.id.detail_collect)
    ImageView detailCollect;
    @Bind(R.id.detail_share)
    ImageView detailShare;
    private View xCustomView;
    private WebChromeClient.CustomViewCallback xCustomViewCallback;
    private MyWebChromeClient xwebchromeclient;
    private String content;
    private int id;
    private int isCollect;
    private String title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        id = getIntent().getIntExtra(VIDEO_ID, 0);
        isCollect = getIntent().getIntExtra(COLLECT, 0);
        content = getIntent().getStringExtra(VIDEO_URL);
        title = getIntent().getStringExtra(TITLE);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        if (isCollect == 0)
            detailCollect.setImageResource(R.mipmap.pingjia_weixuanzhong);
        else
            detailCollect.setImageResource(R.mipmap.pingjia_xuanzhong);

        detailCollect.setOnClickListener(this);
        detailShare.setOnClickListener(this);
        baseTitleBack.setOnClickListener(this);
        baseTitleTitle.setText("播放");
        baseTitleMenu.setVisibility(View.INVISIBLE);

        WebSettings ws = wv.getSettings();
        ws.setBuiltInZoomControls(true);// 隐藏缩放按钮
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);// 排版适应屏幕
        ws.setUseWideViewPort(true);// 可任意比例缩放
        ws.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        ws.setSavePassword(true);
        ws.setSaveFormData(true);// 保存表单数据
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);
        xwebchromeclient = new MyWebChromeClient();
        wv.setWebChromeClient(xwebchromeclient);
        wv.setWebViewClient(new WebViewClient() {
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
       // wv.loadUrl("http://www.iqiyi.com/w_19rsyxp5m5.html");
        wv.loadUrl(content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.base_title_back:
                finish();
                break;
            case R.id.detail_share:
                UMWeb web = new UMWeb(content);
                web.setTitle(title);//标题
//                web.setThumb(thumb);  //缩略图
                web.setDescription(title);//描述
                new ShareAction(this)
                        .withMedia(web)
                        .withText(title)
//                        .withMedia(thumb)
                        .setDisplayList(SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.QZONE,SHARE_MEDIA.SINA)
                        .setCallback(new UMShareListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {

                            }

                            @Override
                            public void onResult(SHARE_MEDIA share_media) {
                                Toast.makeText(VideoActivity.this, "成功了", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                                if (share_media==SHARE_MEDIA.WEIXIN||share_media==SHARE_MEDIA.WEIXIN_CIRCLE){
                                    if (UMShareAPI.get(VideoActivity.this).isInstall(VideoActivity.this,SHARE_MEDIA.WEIXIN))
                                        Toast.makeText(VideoActivity.this, "失败" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                                    else
                                        Toast.makeText(VideoActivity.this, "请安装微信客户端", Toast.LENGTH_LONG).show();

                                }
                                if (share_media==SHARE_MEDIA.QQ){
                                    if (UMShareAPI.get(VideoActivity.this).isInstall(VideoActivity.this,SHARE_MEDIA.QQ))
                                        Toast.makeText(VideoActivity.this, "失败" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                                    else
                                        Toast.makeText(VideoActivity.this, "请安装QQ客户端", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media) {
                                Toast.makeText(VideoActivity.this, "分享取消了", Toast.LENGTH_LONG).show();
                            }
                        })
                        .open();
                break;
            case R.id.detail_collect:
                if (isCollect == 0) {
                    addCollect();
                } else {
                    delCollect();
                }
                break;
        }

    }

    public class MyWebChromeClient extends WebChromeClient {
        private View xprogressvideo;

        // 播放网络视频时全屏会被调用的方法
        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            Log.i("fangfa", "已经进入了。。。。。。。。");
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            videoLl.setVisibility(View.INVISIBLE);
            // 如果一个视图已经存在，那么立刻终止并新建一个
            if (xCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            videoHorizontal.addView(view);
            xCustomView = view;
            xCustomViewCallback = callback;
            videoHorizontal.setVisibility(View.VISIBLE);
        }

        // 视频播放退出全屏会被调用的
        @Override
        public void onHideCustomView() {
            if (xCustomView == null)// 不是全屏播放状态
                return;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            xCustomView.setVisibility(View.GONE);
            videoHorizontal.removeView(xCustomView);
            xCustomView = null;
            videoHorizontal.setVisibility(View.GONE);
            xCustomViewCallback.onCustomViewHidden();
            videoLl.setVisibility(View.VISIBLE);
        }

        // 视频加载时进程loading
        @Override
        public View getVideoLoadingProgressView() {
            if (xprogressvideo == null) {
                LayoutInflater inflater = LayoutInflater
                        .from(VideoActivity.this);
                xprogressvideo = inflater.inflate(
                        R.layout.video_loading_progress, null);
            }
            return xprogressvideo;
        }
    }

    public boolean inCustomView() {
        return (xCustomView != null);
    }

    public void hideCustomView() {
        xwebchromeclient.onHideCustomView();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        wv.onResume();
        wv.resumeTimers();

        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        wv.onPause();
        wv.pauseTimers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoHorizontal.removeAllViews();
        wv.loadUrl("about:blank");
        wv.stopLoading();
        wv.setWebChromeClient(null);
        wv.setWebViewClient(null);
        wv.destroy();
        wv = null;
    }

    /**
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (inCustomView()) {
                // webViewDetails.loadUrl("about:blank");
                hideCustomView();
                return true;
            } else {
                wv.loadUrl("about:blank");
                finish();
            }
        }
        return false;
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
                        ToastUtils.show(msg);
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
                        ToastUtils.show(msg);
                    }
                });
    }
}

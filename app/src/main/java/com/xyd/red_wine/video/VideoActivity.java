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

import com.xyd.red_wine.R;
import com.xyd.red_wine.utils.StatusBarUtil;

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
    private View xCustomView;
    private WebChromeClient.CustomViewCallback xCustomViewCallback;
    private MyWebChromeClient xwebchromeclient;
    private String content;
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        id = getIntent().getIntExtra(VIDEO_ID, 0);
        content = getIntent().getStringExtra(VIDEO_URL);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

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
}

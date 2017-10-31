package com.xyd.red_wine.main.home;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xyd.red_wine.R;
import com.xyd.red_wine.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/27
 * @time: 14:17
 * @description:    加载首页跳转的一些网页
 */

public class WebViewActivity extends BaseActivity {
    public static final String TITLE="title";
    public static final String URL="url";
    @Bind(R.id.base_title_back)
    TextView baseTitleBack;
    @Bind(R.id.base_title_title)
    TextView baseTitleTitle;
    @Bind(R.id.base_title_menu)
    ImageView baseTitleMenu;
    @Bind(R.id.base_webView)
    WebView baseWebView;
    @Bind(R.id.progress)
    ProgressBar progress;
    private String url;
    private String title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initView() {
        title = getIntent().getStringExtra(TITLE);
        url = getIntent().getStringExtra(URL);
        baseTitleBack.setOnClickListener(this);
        baseTitleMenu.setVisibility(View.INVISIBLE);
        baseTitleTitle.setText(title);
        initWebView();

    }

    @Override
    protected void initEvent() {
        baseTitleBack.setOnClickListener(this);

    }

    @Override
    public void widgetClick(View v) {
        finish();
    }

    private void initWebView() {
        WebSettings ws = baseWebView.getSettings();
        ws.setSupportZoom(false);
        ws.setBuiltInZoomControls(true);// 隐藏缩放按钮
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);// 排版适应屏幕
        //ws.setUseWideViewPort(true);// 可任意比例缩放
        ws.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        ws.setSavePassword(true);
        ws.setSaveFormData(true);// 保存表单数据
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);

        baseWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100){
                    progress.setVisibility(View.GONE);
                }else {
                    progress.setProgress(newProgress);
                }
            }
        });
        baseWebView.setWebViewClient(new WebViewClient() {
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
        baseWebView .loadUrl(url);

    }
}

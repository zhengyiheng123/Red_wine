package com.xyd.red_wine.main.home;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
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
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);// 隐藏缩放按钮
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);// 排版适应屏幕
        //ws.setUseWideViewPort(true);// 可任意比例缩放
        ws.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        ws.setSavePassword(true);
        ws.setSaveFormData(true);// 保存表单数据
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);

        baseWebView.setWebChromeClient(new WebChromeClient());
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
        //detailWv.loadUrl(content);
        baseWebView .loadUrl(url);
        //detailWv.loadUrl("https://m.baidu.com/?from=2001a#iact=wiseindex%2Ftabs%2Fnews%2Factivity%2Fnewsdetail%3D%257B%2522linkData%2522%253A%257B%2522name%2522%253A%2522iframe%252Fmib-iframe%2522%252C%2522id%2522%253A%2522feed%2522%252C%2522index%2522%253A0%252C%2522url%2522%253A%2522https%253A%252F%252Ffeed.baidu.com%252Ffeed%252Fdata%252Fwise%252Flandingpage%253Fs_type%253Dnews%2526nid%253D17203467375904090668%2526n_type%253D0%2526p_from%253D2%2522%252C%2522title%2522%253A%2522%25E8%2585%25BE%25E8%25AE%25AF%25E6%2596%25B0%25E9%2597%25BB%2522%257D%257D");

    }
}

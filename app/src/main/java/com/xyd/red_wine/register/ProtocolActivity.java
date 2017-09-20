package com.xyd.red_wine.register;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.xyd.red_wine.R;
import com.xyd.red_wine.api.LoginApi;
import com.xyd.red_wine.api.MessageApi;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.EmptyModel;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.utils.ToastUtils;

import butterknife.Bind;
import io.reactivex.Observable;

/**
 * Created by Zheng on 2017/8/23.
 */

public class ProtocolActivity extends BaseActivity {
    @Bind(R.id.detail_wv)WebView detail_wv;

    @Bind(R.id.base_title_back)TextView base_title_back;

    @Bind(R.id.base_title_title)TextView base_title_title;

    @Bind(R.id.base_title_menu)ImageView base_title_menu;
    private String url;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_protocol;
    }

    @Override
    protected void initView() {
        getProtocol();
        base_title_menu.setVisibility(View.INVISIBLE);
        base_title_title.setText("注册协议");
    }

    @Override
    protected void initEvent() {
        base_title_back.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.base_title_back:
                onBackPressed();
                break;
        }
    }
    private void initWebView() {
        WebSettings ws = detail_wv.getSettings();
        ws.setSupportZoom(false);
        ws.setBuiltInZoomControls(true);// 隐藏缩放按钮
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);// 排版适应屏幕
        //ws.setUseWideViewPort(true);// 可任意比例缩放
        ws.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        ws.setSavePassword(true);
        ws.setSaveFormData(true);// 保存表单数据
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);

        detail_wv.setWebChromeClient(new WebChromeClient());
        detail_wv.setWebViewClient(new WebViewClient() {
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
        detail_wv.loadUrl(url);
        // detailWv.loadUrl("file:///android_asset/show.html");
        //detailWv.loadUrl("https://m.baidu.com/?from=2001a#iact=wiseindex%2Ftabs%2Fnews%2Factivity%2Fnewsdetail%3D%257B%2522linkData%2522%253A%257B%2522name%2522%253A%2522iframe%252Fmib-iframe%2522%252C%2522id%2522%253A%2522feed%2522%252C%2522index%2522%253A0%252C%2522url%2522%253A%2522https%253A%252F%252Ffeed.baidu.com%252Ffeed%252Fdata%252Fwise%252Flandingpage%253Fs_type%253Dnews%2526nid%253D17203467375904090668%2526n_type%253D0%2526p_from%253D2%2522%252C%2522title%2522%253A%2522%25E8%2585%25BE%25E8%25AE%25AF%25E6%2596%25B0%25E9%2597%25BB%2522%257D%257D");

    }
    //获取网络数据
    private void getProtocol(){
        BaseApi.getRetrofit()
                .create(LoginApi.class)
                .xieyi()
                .compose(RxSchedulers.<BaseModel>compose())
                .subscribe(new BaseObserver() {
                    @Override
                    protected void onHandleSuccess(Object o, String msg, int code) {
                        url = (String) o;
                        initWebView();
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        showTestToast(msg);
                    }
                });
    }
}

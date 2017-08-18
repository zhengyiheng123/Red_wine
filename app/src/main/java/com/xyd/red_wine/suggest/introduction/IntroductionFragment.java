package com.xyd.red_wine.suggest.introduction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xyd.red_wine.R;
import com.xyd.red_wine.base.BaseFragment;

import java.io.IOException;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/14
 * @time: 9:56
 * @description: 资讯——简介
 */

public class IntroductionFragment extends BaseFragment {
    @Bind(R.id.introduction_wv)
    WebView introductionWv;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_introduction;
    }

    @Override
    protected void initView() {
        WebSettings ws = introductionWv.getSettings();
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);// 隐藏缩放按钮
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);// 排版适应屏幕
        //ws.setUseWideViewPort(true);// 可任意比例缩放
        ws.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        ws.setSavePassword(true);
        ws.setSaveFormData(true);// 保存表单数据
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);

        introductionWv.setWebChromeClient(new WebChromeClient());
        introductionWv.setWebViewClient(new WebViewClient() {
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


       // introductionWv.loadData("<p>视频名称：一个骚男。</p ><p>发布人：琛哥</p ><p>时间：2017年7月19</p ><p><video class=\"edui-upload-video  vjs-default-skin   video-js\" controls=\"\" preload=\"none\" width=\"420\" height=\"280\" src=\"http://hj.jiangliping.com/ueditor/php/upload/video/20170719/1500447583144353.mp4\" data-setup=\"{}\"><source src=\"http://hj.jiangliping.com/ueditor/php/upload/video/20170719/1500447583144353.mp4\" type=\"video/mp4\"/></video></p ><p>啊哈哈哈哈哈哈哈哈哈哈哈哈哈或或或或或或或或或或或或或或或或或或或或或或或或或或或</p ><p>奥斯卡里是否哈搜乐的发货撒地方</p >", "text/html; charset=UTF-8", null);
        introductionWv .loadUrl("file:///android_asset/index.html");
       // introductionWv.loadUrl("file:///C:/Users/shan/Documents/WeChat%20Files/zxl960750562/Files/%E9%85%92%E7%AA%96/%E9%85%92%E7%AA%96/index.html");
        //introductionWv.loadUrl("https://m.baidu.com/?from=2001a#iact=wiseindex%2Ftabs%2Fnews%2Factivity%2Fnewsdetail%3D%257B%2522linkData%2522%253A%257B%2522name%2522%253A%2522iframe%252Fmib-iframe%2522%252C%2522id%2522%253A%2522feed%2522%252C%2522index%2522%253A0%252C%2522url%2522%253A%2522https%253A%252F%252Ffeed.baidu.com%252Ffeed%252Fdata%252Fwise%252Flandingpage%253Fs_type%253Dnews%2526nid%253D17203467375904090668%2526n_type%253D0%2526p_from%253D2%2522%252C%2522title%2522%253A%2522%25E8%2585%25BE%25E8%25AE%25AF%25E6%2596%25B0%25E9%2597%25BB%2522%257D%257D");

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void widgetClick(View v) {

    }


}

package com.xyd.red_wine.newsdetail;

import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;
import com.xyd.red_wine.R;
import com.xyd.red_wine.api.CollectApi;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.EmptyModel;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.utils.ToastUtils;

import butterknife.Bind;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/14
 * @time: 14:14
 * @description:
 */

public class DetailActivityCopy extends BaseActivity {
    public static final String NEWS_ID = "news_id";
    public static final String NEWS_URL = "news_url";
    public static final String COLLECT = "collect";
    @Bind(R.id.detail_back)
    TextView detailBack;
    @Bind(R.id.detail_title)
    TextView detailTitle;
    @Bind(R.id.detail_collect)
    ImageView detailCollect;
    @Bind(R.id.detail_share)
    ImageView detailShare;
    @Bind(R.id.detail_wv)
    WebView detailWv;
    private String content;
    private int id;
    private int isCollect;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initView() {

        id = getIntent().getIntExtra(NEWS_ID, 0);
        Log.e("newsid:" ,id+"");
        content = getIntent().getStringExtra(NEWS_URL);
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

        detailWv.setWebChromeClient(new WebChromeClient());
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
//                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                shareIntent.setType("text/plain");
//                shareIntent.putExtra(Intent.EXTRA_TEXT, content);
//                //自定义选择框的标题
//                startActivity(Intent.createChooser(shareIntent, "资讯"));
//                UMImage thumb =  new UMImage(this, R.mipmap.logo000);
                UMWeb  web = new UMWeb(content);
                web.setTitle("酒瀚");//标题
//                web.setThumb(thumb);  //缩略图
                web.setDescription("红酒资讯");//描述
                new ShareAction(this)
                        .withMedia(web)
                        .withText("乔治金瀚资讯")
//                        .withMedia(thumb)
                        .setDisplayList(SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.QZONE,SHARE_MEDIA.SINA)
                        .setCallback(new UMShareListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {

                            }

                            @Override
                            public void onResult(SHARE_MEDIA share_media) {
                                Toast.makeText(DetailActivityCopy.this, "成功了", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                                if (share_media==SHARE_MEDIA.WEIXIN||share_media==SHARE_MEDIA.WEIXIN_CIRCLE){
                                    if (UMShareAPI.get(DetailActivityCopy.this).isInstall(DetailActivityCopy.this,SHARE_MEDIA.WEIXIN))
                                        Toast.makeText(DetailActivityCopy.this, "失败" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                                    else
                                        Toast.makeText(DetailActivityCopy.this, "请安装微信客户端", Toast.LENGTH_LONG).show();

                                }
                                if (share_media==SHARE_MEDIA.QQ){
                                    if (UMShareAPI.get(DetailActivityCopy.this).isInstall(DetailActivityCopy.this,SHARE_MEDIA.QQ))
                                        Toast.makeText(DetailActivityCopy.this, "失败" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                                    else
                                        Toast.makeText(DetailActivityCopy.this, "请安装QQ客户端", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media) {
                                Toast.makeText(DetailActivityCopy.this, "分享取消了", Toast.LENGTH_LONG).show();
                            }
                        })
                        .open();
                break;
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
}
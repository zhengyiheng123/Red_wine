package com.xyd.red_wine.generalize;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.xyd.red_wine.R;
import com.xyd.red_wine.api.GeneralizeApi;
import com.xyd.red_wine.base.BaseActivity;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.PublicStaticData;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.glide.GlideUtil;
import com.xyd.red_wine.newsdetail.DetailActivity;
import com.xyd.red_wine.suggest.SuggestActivity;
import com.xyd.red_wine.utils.FileUtils;
import com.xyd.red_wine.utils.ShareUtils;
import com.xyd.red_wine.video.VideoActivity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/13
 * @time: 15:12
 * @description: 推广
 */

public class GeneralizeActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @Bind(R.id.base_title_back)
    TextView baseTitleBack;
    @Bind(R.id.base_title_title)
    TextView baseTitleTitle;
    @Bind(R.id.base_title_menu)
    ImageView baseTitleMenu;
    @Bind(R.id.generalize_qr_code)
    ImageView generalizeQrCode;
    @Bind(R.id.generalize_share)
    TextView generalizeShare;
    @Bind(R.id.generalize_image_more)
    TextView generalizeImageMore;
    @Bind(R.id.generalize_image)
    RelativeLayout generalizeImage;
    @Bind(R.id.generalize_video_more)
    TextView generalizeVideoMore;
    @Bind(R.id.generalize_video)
    RelativeLayout generalizeVideo;
    @Bind(R.id.generalize_image_gv)
    GridView generalizeImageGv;
    @Bind(R.id.generalize_video_gv)
    GridView generalizeVideoGv;

    private List<GeneralizeModel.ArticlesBean.VideoMsgBean> videos;
    private List<GeneralizeModel.ArticlesBean.PhotoMsgBean> images;
    private IvAdapter ivAdapter;
    private VideoAdapter videoAdapter;
    private File myCaptureFile;
    private String qr_code;

    private UMShareListener mShareListener;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_generalize;
    }

    @Override
    protected void initView() {
        mShareListener = new CustomShareListener(GeneralizeActivity.this);
        baseTitleTitle.setText("推广");
        baseTitleMenu.setVisibility(View.INVISIBLE);
        getData();
        videos = new ArrayList<>();
        images = new ArrayList<>();
        ivAdapter = new IvAdapter();
        generalizeImageGv.setAdapter(ivAdapter);
        videoAdapter = new VideoAdapter();
        generalizeVideoGv.setAdapter(videoAdapter);
        generalizeVideoGv.setOnItemClickListener(this);
        generalizeImageGv.setOnItemClickListener(this);


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    myBitmap = Glide.with(GeneralizeActivity.this)
//                            .load(PublicStaticData.baseUrl + PublicStaticData.sharedPreferences.getString("head", ""))
//                            .asBitmap()
//                            .centerCrop()
//                            .into(100, 100)
//                            .get();
//
//                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
//                    myBitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
//                    bos.flush();
//                    bos.close();
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();


    }

    private void getData() {
        BaseApi.getRetrofit()
                .create(GeneralizeApi.class)
                .generalize()
                .compose(RxSchedulers.<BaseModel<GeneralizeModel>>compose())
                .subscribe(new BaseObserver<GeneralizeModel>() {
                    @Override
                    protected void onHandleSuccess(GeneralizeModel generalizeModel, String msg, int code) {
                        GlideUtil.getInstance()
                                .loadCornerImage(GeneralizeActivity.this, generalizeQrCode, PublicStaticData.baseUrl + generalizeModel.getQr_code(), 10);
                        images.addAll(generalizeModel.getArticles().getPhoto_msg());
                        videos.addAll(generalizeModel.getArticles().getVideo_msg());
                        ivAdapter.notifyDataSetChanged();
                        videoAdapter.notifyDataSetChanged();
                        qr_code = PublicStaticData.baseUrl + generalizeModel.getQr_code();
                        //saveQrCode(PublicStaticData.baseUrl + generalizeModel.getQr_code());

                    }

                    @Override
                    protected void onHandleError(String msg) {
                        showToast(msg);
                    }
                });
    }

//    private void saveQrCode(final String s) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    myCaptureFile = FileUtils.createImageFile("generalize.png");
//                    Bitmap myBitmap = Glide.with(GeneralizeActivity.this)
//                            .load(s)
//                            .asBitmap()
//                            .centerCrop()
//                            .into(300, 300)
//                            .get();
//
//                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
//                    myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
//                    bos.flush();
//                    bos.close();
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//
//
//    }


    @Override
    protected void initEvent() {
        baseTitleBack.setOnClickListener(this);
        generalizeShare.setOnClickListener(this);
        generalizeImageMore.setOnClickListener(this);
        generalizeVideoMore.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.base_title_back:
                finish();
                break;
            case R.id.generalize_share:
                new ShareAction(this)
                        .setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.SINA)
                        .setShareboardclickCallback(new ShareBoardlistener() {
                            @Override
                            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                if (share_media == SHARE_MEDIA.QQ || share_media== SHARE_MEDIA.QZONE){
                                    UMImage umImage=new UMImage(getApplicationContext(),qr_code);
                                    umImage.setThumb(new UMImage(GeneralizeActivity.this, R.mipmap.logo000));
//                                    web.setThumb(new UMImage(DetailActivity.this, R.mipmap.logo000));
                                    new ShareAction(GeneralizeActivity.this).withMedia(umImage)
                                            .setPlatform(share_media)
                                            .setCallback(mShareListener)
                                            .share();
                                }else {
                                    UMImage umImage=new UMImage(getApplicationContext(),qr_code);
                                    UMImage thum=new UMImage(getApplicationContext(),R.mipmap.logo000);
                                    umImage.setThumb(thum);
                                    new ShareAction(GeneralizeActivity.this).withMedia(umImage)
                                            .setPlatform(share_media)
                                            .setCallback(mShareListener)
                                            .share();
                                }
                            }
                        }).open();



//                new ShareAction(this)
//                        .withText("酒瀚")
//                        .withMedia(new UMImage(GeneralizeActivity.this,qr_code))
//                        .setDisplayList(SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN,SHARE_MEDIA.QZONE,SHARE_MEDIA.SINA)
//                        .setCallback(new UMShareListener() {
//                            @Override
//                            public void onStart(SHARE_MEDIA share_media) {
//
//                            }
//
//                            @Override
//                            public void onResult(SHARE_MEDIA share_media) {
//                                Toast.makeText(GeneralizeActivity.this, "成功了", Toast.LENGTH_LONG).show();
//                            }
//
//                            @Override
//                            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
//                                if (share_media==SHARE_MEDIA.WEIXIN||share_media==SHARE_MEDIA.WEIXIN_CIRCLE){
//                                    if (UMShareAPI.get(GeneralizeActivity.this).isInstall(GeneralizeActivity.this,SHARE_MEDIA.WEIXIN))
//                                        Toast.makeText(GeneralizeActivity.this, "失败" + throwable.getMessage(), Toast.LENGTH_LONG).show();
//                                    else
//                                        Toast.makeText(GeneralizeActivity.this, "请安装微信客户端", Toast.LENGTH_LONG).show();
//
//                                }
//                                if (share_media==SHARE_MEDIA.QQ){
//                                    if (UMShareAPI.get(GeneralizeActivity.this).isInstall(GeneralizeActivity.this,SHARE_MEDIA.QQ))
//                                        Toast.makeText(GeneralizeActivity.this, "失败" + throwable.getMessage(), Toast.LENGTH_LONG).show();
//                                    else
//                                        Toast.makeText(GeneralizeActivity.this, "请安装QQ客户端", Toast.LENGTH_LONG).show();
//                                }
//                            }
//
//                            @Override
//                            public void onCancel(SHARE_MEDIA share_media) {
//                                Toast.makeText(GeneralizeActivity.this, "取消了", Toast.LENGTH_LONG).show();
//                            }
//                        })
//                        .open();
//                if (myCaptureFile.getTotalSpace() == 0)
//                    return;
//                Uri uri = Uri.fromFile(myCaptureFile);
//                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                if (uri != null) {
//                    shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
//                    shareIntent.setType("image/*");
//                    //当用户选择短信时使用sms_body取得文字
//                    shareIntent.putExtra("sms_body", "乔治金瀚");
//                } else {
//                    shareIntent.setType("text/plain");
//                }
//                shareIntent.putExtra(Intent.EXTRA_TEXT, "乔治金瀚");
//                //自定义选择框的标题
//                startActivity(Intent.createChooser(shareIntent, "乔治金瀚"));

                break;
            case R.id.generalize_image_more:
                Bundle bundle1= new Bundle();
                bundle1.putInt(SuggestActivity.CURRENT_PAGE,0);
                startActivity(SuggestActivity.class,bundle1);
                break;
            case R.id.generalize_video_more:
                Bundle bundle=new Bundle();
                bundle.putInt(SuggestActivity.CURRENT_PAGE,1);
                startActivity(SuggestActivity.class,bundle);
                break;
        }

    }

    private void goImageDetail(int i) {
        Bundle b = new Bundle();
        b.putInt(DetailActivity.NEWS_ID, images.get(i).getA_id());
        b.putString(DetailActivity.NEWS_URL, images.get(i).getA_content());
        b.putInt(DetailActivity.COLLECT, images.get(i).getCollect());
        startActivity(DetailActivity.class, b);
    }

    private void goVideoDetail(int i) {
        Bundle b = new Bundle();
        b.putInt(VideoActivity.VIDEO_ID, videos.get(i).getA_id());
        b.putString(VideoActivity.VIDEO_URL, videos.get(i).getA_content());

        startActivity(VideoActivity.class, b);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.generalize_image_gv:
                goImageDetail(position);
                break;
            case R.id.generalize_video_gv:
                goVideoDetail(position);
                break;
        }

    }


    class IvAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object getItem(int position) {
            return images.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(GeneralizeActivity.this).inflate(R.layout.item_generalize, null, false);
                holder = new ViewHolder();
                holder.iv = (ImageView) convertView.findViewById(R.id.generalize_gv_iv);
                holder.tv = (TextView) convertView.findViewById(R.id.generalize_gv_tv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv.setText(images.get(position).getA_title());
            GlideUtil.getInstance()
                    .loadImage(GeneralizeActivity.this, holder.iv, PublicStaticData.baseUrl + images.get(position).getA_img().get(0), true);

            return convertView;
        }
    }

    class VideoAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return videos.size();
        }

        @Override
        public Object getItem(int position) {
            return videos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(GeneralizeActivity.this).inflate(R.layout.item_generalize, null, false);
                holder = new ViewHolder();
                holder.iv = (ImageView) convertView.findViewById(R.id.generalize_gv_iv);
                holder.tv = (TextView) convertView.findViewById(R.id.generalize_gv_tv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv.setText(videos.get(position).getA_title());
            GlideUtil.getInstance()
                    .loadImage(GeneralizeActivity.this, holder.iv, PublicStaticData.baseUrl + videos.get(position).getA_img().get(0), true);
            return convertView;
        }
    }

    class ViewHolder {
        private ImageView iv;
        private TextView tv;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    private static class CustomShareListener implements UMShareListener {

        private WeakReference<GeneralizeActivity> mActivity;

        private CustomShareListener(GeneralizeActivity activity) {
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

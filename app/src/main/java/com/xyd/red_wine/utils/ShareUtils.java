package com.xyd.red_wine.utils;

import android.app.Activity;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * @author: zhaoxiaolei
 * @date: 2017/7/31
 * @time: 10:58
 * @description:
 */

public class ShareUtils {

    public static void share(final Activity activity, String title, String imageUrl) {

        ShareAction share = new ShareAction(activity);
        share.withText(title);
        share.setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE);
        UMImage image = new UMImage(activity, imageUrl);//网络图片
        image.setThumb(image);
        share.withMedia(image);

        share.setCallback(new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
                Toast.makeText(activity, "成功了", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                Toast.makeText(activity, "失败" + throwable.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                Toast.makeText(activity, "取消了", Toast.LENGTH_LONG).show();
            }
        });
        share.share();
    }


}

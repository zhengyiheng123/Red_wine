package com.xyd.red_wine.version;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.xyd.red_wine.MyApplication;
import com.xyd.red_wine.R;
import com.xyd.red_wine.base.BaseApi;
import com.xyd.red_wine.base.BaseModel;
import com.xyd.red_wine.base.BaseObserver;
import com.xyd.red_wine.base.RxSchedulers;
import com.xyd.red_wine.utils.AppUtils;
import com.xyd.red_wine.utils.LogUtil;
import com.xyd.red_wine.utils.ToastUtils;

import java.io.File;



/**
 * @author: zhaoxiaolei
 * @date: 2017/5/8
 * @time: 19:12
 * @description:
 */

public class VersionUpdateService extends Service {
    private static final String TAG = VersionUpdateService.class.getSimpleName();
    private LocalBinder binder = new LocalBinder();

    private DownLoadListener downLoadListener;
    private boolean downLoading;
    private int progress;

    private NotificationManager mNotificationManager;
    private NotificationUpdaterThread notificationUpdaterThread;
    private Notification.Builder notificationBuilder;
    private final int NOTIFICATION_ID = 100;
    private VersionUpdateModel versionUpdateModel;


    public VersionUpdateService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d(TAG, "onCreate called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG, "onDestroy called");
        setDownLoadListener(null);
        setCheckVersionCallBack(null);
        stopDownLoadForground();
        if (mNotificationManager != null)
            mNotificationManager.cancelAll();
        downLoading = false;
    }

    public interface DownLoadListener {
        void begain();

        void inProgress(float progress);

        void downLoadLatestSuccess(File file);

        void downLoadLatestFailed();
    }

    public interface CheckVersionCallBack {
        void onSuccess();

        void onError();
    }

    private CheckVersionCallBack checkVersionCallBack;

    public void setCheckVersionCallBack(CheckVersionCallBack checkVersionCallBack) {
        this.checkVersionCallBack = checkVersionCallBack;
    }

    private class NotificationUpdaterThread extends Thread {
        @Override
        public void run() {
            while (true) {
                notificationBuilder.setContentTitle("正在下载更新" + progress + "%"); // the label of the entry
                notificationBuilder.setProgress(100, progress, false);

                try {
                    Thread.sleep(1000);
                    mNotificationManager.notify(NOTIFICATION_ID, notificationBuilder.getNotification());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (progress >= 100) {
                    mNotificationManager.cancelAll();
                    break;
                }
            }
        }
    }

    public boolean isDownLoading() {
        return downLoading;
    }

    public void setDownLoading(boolean downLoading) {
        this.downLoading = downLoading;
    }

    /**
     * 让Service保持活跃,避免出现:
     * 如果启动此服务的前台Activity意外终止时Service出现的异常(也将意外终止)
     */
    private void starDownLoadForground() {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = "下载中,请稍后...";
        // The PendingIntent to launch our activity if the user selects this notification
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//                new Intent(this, MainActivity.class), 0);
        notificationBuilder = new Notification.Builder(this);
        notificationBuilder.setSmallIcon(R.mipmap.logoooo11);  // the status icon
        notificationBuilder.setTicker(text);  // the status text
        notificationBuilder.setWhen(System.currentTimeMillis());  // the time stamp
        notificationBuilder.setContentText(text);  // the contents of the entry
//        notificationBuilder.setContentIntent(contentIntent);  // The intent to send when the entry is clicked
        notificationBuilder.setContentTitle("正在下载更新" + 0 + "%"); // the label of the entry
        notificationBuilder.setProgress(100, 0, false);
        notificationBuilder.setOngoing(true);
        notificationBuilder.setAutoCancel(true);
        Notification notification = notificationBuilder.getNotification();
        startForeground(NOTIFICATION_ID, notification);
    }

    private void stopDownLoadForground() {
        stopForeground(true);
    }

    /**
     * 检查是否需要更新
     */
    public void doCheckUpdateTask() {
        final String currentBuild = AppUtils.getAppVersionName(this);


        BaseApi.getRetrofit()
                .create(VersionApi.class)
                .versions(AppUtils.getAppVersionName(this))
                .compose(RxSchedulers.<BaseModel<VersionUpdateModel>>compose())
                .subscribe(new BaseObserver<VersionUpdateModel>() {
                    @Override
                    protected void onHandleSuccess(VersionUpdateModel model, String msg, int code) {
                        try {
                            if (model.getVersion_num() .equals(currentBuild)) {
                                model.setNeedUpgrade(false);
                            }else {
                                model.setNeedUpgrade(true);
                            }
                            model.setMustUpgrade(false);
                            versionUpdateModel = model;
                            //TEST DATA
                            if (checkVersionCallBack != null)
                                checkVersionCallBack.onSuccess();
                        } catch (Exception e) {
                            ToastUtils.show( "获取版本信息失败");
                        }
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        if (checkVersionCallBack != null) {
                            checkVersionCallBack.onError();
                        }
                    }
                });


    }

    public void doDownLoadTask() {
        if (mNotificationManager == null)
            mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        starDownLoadForground();

        notificationUpdaterThread = new NotificationUpdaterThread();
        notificationUpdaterThread.start();

         // final String url = versionUpdateModel.getUpgradeUrl();
         //final String fileName_ = url.substring(url.lastIndexOf("/") + 1)+".apk";
         //final String fileName = StringUtil.string2MD5(fileName_) + ".apk";

        downLoading = true;

        if (downLoadListener != null) {
            downLoadListener.begain();
        }

        DownloadUtil.get().download(versionUpdateModel.getUpload_url(), "download", new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(String path) {
                final File destFile = new File(path);
                if (downLoadListener != null) {
                    downLoadListener.downLoadLatestSuccess(destFile);
                }
                downLoading = false;
                installApk(destFile, VersionUpdateService.this);
            }

            @Override
            public void onDownloading(final int progress_) {

                progress = progress_;
                if (downLoadListener != null) {

                    downLoadListener.inProgress(progress_);
                }
                if (progress_ >= 100) {

                   // mNotificationManager.cancelAll();
                }

            }

            @Override
            public void onDownloadFailed() {
                downLoading = false;
                if (mNotificationManager != null)
                    mNotificationManager.cancelAll();
                if (downLoadListener != null) {
                    downLoadListener.downLoadLatestFailed();
                }

            }
        });

    }

    public VersionUpdateModel getVersionUpdateModel() {
        return versionUpdateModel;
    }

    public void setDownLoadListener(DownLoadListener downLoadListener) {
        this.downLoadListener = downLoadListener;
    }

    //安装apk
    public void installApk(File file, Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(Build.VERSION.SDK_INT>=24) { //判读版本是否在7.0以上
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri =
                    FileProvider.getUriForFile(context, "com.xyd.red_wine", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        }else{
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class LocalBinder extends Binder {
        public VersionUpdateService getService() {
            return VersionUpdateService.this;
        }
    }
}
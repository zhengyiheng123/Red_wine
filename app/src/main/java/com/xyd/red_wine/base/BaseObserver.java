package com.xyd.red_wine.base;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.xyd.red_wine.utils.LogUtil;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.adapter.rxjava2.HttpException;

/**
 * @author: zhaoxiaolei
 * @date: 2017/4/17
 * @time: 17:28
 * @description:
 */

public abstract class BaseObserver<T> implements Observer<BaseModel<T>> {

    private static final String TAG = "BaseObserver";
    private Context mContext;

    protected BaseObserver() {
        //this.mContext = context.getApplicationContext();
    }

    @Override
    public void onNext(BaseModel<T> value) {
        LogUtil.e(value.getCode()+"");
        T t = value.getData();
        Gson gson=new Gson();
        if (value.getCode()==1) {
            LogUtil.e("success",gson.toJson(t));
            onHandleSuccess(t,value.getMessage(),1);
        } else {
            LogUtil.e("error",gson.toJson(t));
            LogUtil.e("error",value.getMessage());
            onHandleError(value.getMessage());
        }
    }

    @Override
    public void onError(Throwable e) {
        LogUtil.e(e.toString());
        if (e instanceof SocketTimeoutException) {
            onHandleError("连接超时");
        } else if (e instanceof ConnectException) {
            onHandleError("连接异常");
        } else if (e instanceof UnknownHostException) {
            onHandleError("找不到主机");
        } else if (e instanceof JsonSyntaxException) {
            onHandleError("数据解析异常");
        } else if (e instanceof JSONException) {
            onHandleError("数据解析异常");
        } else if (e instanceof JsonIOException) {
            onHandleError("数据解析异常");
        } else if (e instanceof JsonParseException) {
            onHandleError("数据解析异常");
        } else if (e instanceof HttpException) {
            onHandleError("网页异常");
        } else if (e instanceof retrofit2.HttpException) {
            onHandleError("网页异常");
        } else {
            onHandleError("未知错误");
        }
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete");
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    protected abstract void onHandleSuccess(T t,String msg,int code);

    protected abstract void onHandleError(String msg) ;
}
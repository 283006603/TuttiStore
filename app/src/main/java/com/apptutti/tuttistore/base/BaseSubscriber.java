package com.apptutti.tuttistore.base;

import android.text.TextUtils;

import com.apptutti.tuttistore.network.exception.ApiException;
import com.apptutti.tuttistore.utils.AppUtils;
import com.apptutti.tuttistore.utils.LogUtils;
import com.apptutti.tuttistore.utils.NetworkUtils;
import com.apptutti.tuttistore.utils.ToastUtils;

import java.net.SocketTimeoutException;

import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;

/**
 * @author zzq  作者 E-mail:   283006603@qq.com.com
 * 描述:统一处理订阅者
 */
//ResourceSubscriber：允许异步取消其订阅相关资源，节省内存而且是线程安全。
    //参考https://www.jianshu.com/p/785d9dfb0a5b
public abstract class BaseSubscriber<T> extends ResourceSubscriber<T>{
    private BaseContract.BaseView mView;
    private String mMsg;

    public BaseSubscriber(BaseContract.BaseView view) {
        this.mView = view;
    }


    public abstract void onSuccess(T t);

    public void onFailure(int code, String message) {

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (!NetworkUtils.isConnected(AppUtils.getAppContext())) {
            // Logger.d("没有网络");
            ToastUtils.showSingleLongToast("请检查网络连接，然后重试");
            LogUtils.w("BaseSubercriber","请检查网络连接，然后重试");
        } else {

        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onNext(T response) {
        if (mView == null) return;
        mView.complete();
        LogUtils.d("BaseSubscriber:"+response);
        onSuccess(response);
    }


    @Override
    public void onError(Throwable e) {
        LogUtils.e("BaseSubscriber:"+e.toString());
        if (mView == null) return;
        mView.complete();//完成操作
        if (mMsg != null && !TextUtils.isEmpty(mMsg)) {
            mView.showError(mMsg);
        } else if (e instanceof ApiException) {
            mView.showError(e.toString());
        } else if (e instanceof SocketTimeoutException) {
            mView.showError("服务器响应超时ヽ(≧Д≦)ノ");
        } else if (e instanceof HttpException) {
            mView.showError("数据加载失败ヽ(≧Д≦)ノ");
        } else if(!NetworkUtils.isConnected(AppUtils.getAppContext())){
            mView.showError("请检查网络连接，然后重试");
        } else {
            mView.showError("未知错误ヽ(≧Д≦)ノ");
        }
    }
}

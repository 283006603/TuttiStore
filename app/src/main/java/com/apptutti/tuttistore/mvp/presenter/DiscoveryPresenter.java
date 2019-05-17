package com.apptutti.tuttistore.mvp.presenter;

import android.util.Log;

import com.apptutti.tuttistore.base.BaseSubscriber;
import com.apptutti.tuttistore.base.RxPresenter;
import com.apptutti.tuttistore.bean.discover.DiscoveryBean;
import com.apptutti.tuttistore.mvp.contract.DiscoveryContract;
import com.apptutti.tuttistore.network.helper.HttpMethods;
import com.apptutti.tuttistore.network.api.DiscoveryService;
import com.apptutti.tuttistore.rx.RxUtils;

import retrofit2.Retrofit;

/**
 * @author zzq  作者 E-mail:   283006603@qq.com.com
 * @date 创建时间：2017/9/17 18:00
 * 描述:活动中心resenter
 */

public class DiscoveryPresenter extends RxPresenter<DiscoveryContract.View> implements DiscoveryContract.Presenter<DiscoveryContract.View>{
    private DiscoveryService discoveryService;


    //---
    public DiscoveryPresenter(){
        Retrofit retrofit= HttpMethods.getInstance().createRetrofit();
        discoveryService= retrofit.create(DiscoveryService.class);
    }
    //---


    @Override
    public void getDiscoveryData(int page, int pageSize){
        BaseSubscriber<DiscoveryBean> subscriber =  HttpMethods.getInstance().getDiscovery(discoveryService,page, pageSize)
                .compose(RxUtils.<DiscoveryBean>rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<DiscoveryBean>(mView){
            @Override
            public void onSuccess(DiscoveryBean rankingBean){
                Log.d("DiscoveryPresenter", "rankingBean:" + rankingBean.getData().size());
                if(rankingBean.getData() != null){
                    mView.showRanking(rankingBean/*, gameRecoBean.total*/);
                }
            }
        });
        addSubscribe(subscriber);
    }


}

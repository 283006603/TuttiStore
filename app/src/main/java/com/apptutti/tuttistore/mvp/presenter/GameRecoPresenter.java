package com.apptutti.tuttistore.mvp.presenter;

import com.apptutti.tuttistore.base.BaseSubscriber;
import com.apptutti.tuttistore.base.RxPresenter;
import com.apptutti.tuttistore.bean.GameRecoBean;
import com.apptutti.tuttistore.mvp.contract.GameRecoContract;
import com.apptutti.tuttistore.network.helper.HttpMethods;
import com.apptutti.tuttistore.network.api.GameRecoService;
import com.apptutti.tuttistore.rx.RxUtils;

import retrofit2.Retrofit;

/**
 * @author zzq  作者 E-mail:   283006603@qq.com.com
 * @date 创建时间：2017/9/17 18:00
 * 描述:活动中心resenter
 */

public class GameRecoPresenter extends RxPresenter<GameRecoContract.View> implements GameRecoContract.Presenter<GameRecoContract.View>{
    private final GameRecoService gameRecoService;

    //---
    public GameRecoPresenter(){
        Retrofit retrofit=HttpMethods.getInstance().createRetrofit();
        gameRecoService= retrofit.create(GameRecoService.class);
    }
    //---

    @Override
    public void getGameRecoData(int page, int pageSize){


        BaseSubscriber<GameRecoBean> subscriber = HttpMethods.getInstance().getGameReco(gameRecoService,page, pageSize)
                .compose(RxUtils.<GameRecoBean>rxSchedulerHelper())
                .subscribeWith(new BaseSubscriber<GameRecoBean>(mView){
            @Override
            public void onSuccess(GameRecoBean gameRecoBean){
                if(gameRecoBean.list != null){
                    mView.showGameReco(gameRecoBean.list, gameRecoBean.total);
                }
            }

        });
        addSubscribe(subscriber);
    }
}

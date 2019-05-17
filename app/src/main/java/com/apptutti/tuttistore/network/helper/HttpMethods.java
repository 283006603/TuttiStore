package com.apptutti.tuttistore.network.helper;

import com.apptutti.tuttistore.bean.GameRecoBean;
import com.apptutti.tuttistore.bean.discover.DiscoveryBean;
import com.apptutti.tuttistore.network.api.DiscoveryService;
import com.apptutti.tuttistore.network.api.GameRecoService;
import com.apptutti.tuttistore.network.support.ApiConstants;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Flowable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author zzq  作者 E-mail:   283006603@qq.com
 * @date 创建时间：2017/9/12 16:41
 * 描述:Api网络模型
 */

public class HttpMethods{


    private static HttpMethods httpMethods;

    public  static HttpMethods getInstance (){
        if(httpMethods==null){
            httpMethods=new HttpMethods();
        }
        return httpMethods;
    }

    public Retrofit createRetrofit() {

        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(ApiConstants.API_BASE_URL)
                .client(OkHttpHelper.getInstance().getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }



    //所有API都在这
    /*******************************游戏推荐Api****************************************/
    public Flowable<GameRecoBean> getGameReco(GameRecoService gameRecoService,int page, int pageSize) {
        return gameRecoService.getGameReco(page, pageSize);
    }
    /******************************排行榜Api***************************************/
    public Flowable<DiscoveryBean> getDiscovery(DiscoveryService discoveryService, int page, int pageSize) {
        return discoveryService.getRanking(page, pageSize);
    }


}

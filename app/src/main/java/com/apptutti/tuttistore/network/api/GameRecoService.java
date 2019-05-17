package com.apptutti.tuttistore.network.api;



import com.apptutti.tuttistore.bean.GameRecoBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author zzq  作者 E-mail:   283006603@qq.com
 * @date 创建时间：2017/9/23 16:25
 * 描述:番剧
 */

public interface GameRecoService{

    /**
     * 活动中心
     */
    @GET("event/getlist?device=phone&mobi_app=iphone")
    Flowable<GameRecoBean> getGameReco(@Query("page") int page, @Query("pagesize") int pageSize);

}

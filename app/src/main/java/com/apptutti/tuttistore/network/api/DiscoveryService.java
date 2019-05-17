package com.apptutti.tuttistore.network.api;

import com.apptutti.tuttistore.bean.discover.DiscoveryBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author zzq  作者:李文强 E-mail:   283006603@qq.com
 * @date 创建时间：2017/9/23 16:25
 * 描述:番剧
 */

public interface DiscoveryService{

    /**
     *
     */
    @GET("http://192.168.1.11/json.php")
    Flowable<DiscoveryBean> getRanking(@Query("page") int page, @Query("pagesize") int pageSize);
}

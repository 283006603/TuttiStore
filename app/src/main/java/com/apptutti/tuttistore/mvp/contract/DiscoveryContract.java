package com.apptutti.tuttistore.mvp.contract;

import com.apptutti.tuttistore.base.BaseContract;
import com.apptutti.tuttistore.bean.discover.DiscoveryBean;

/**
 * @author zzq  作者 E-mail:   283006603@qq.com.com
 * @date 创建时间：2017/9/12 10:09
 * 描述:话题中心Contract
 */

public interface DiscoveryContract{

    interface View extends BaseContract.BaseView {

        void showRanking(DiscoveryBean rankingbean/*,int total*/);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {

        void getDiscoveryData(int page, int pageSize);
    }
}

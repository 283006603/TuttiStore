package com.apptutti.tuttistore.widge;

import com.apptutti.tuttistore.R;
import com.chad.library.adapter.base.loadmore.LoadMoreView;


/**
 * @author zzq  作者 E-mail:   283006603@qq.com
 * @date 创建时间：2017/9/6 9:44
 * 描述:自定义加载更多布局
 */

public class CustomLoadMoreView extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.layout_load_more;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.ll_loading;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.ll_load_fail;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.ll_load_end;
    }
}

package com.apptutti.tuttistore.adapter.discover;

import android.support.annotation.Nullable;

import com.apptutti.tuttistore.R;
import com.apptutti.tuttistore.bean.discover.DiscoveryBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by 006 on 23/10/2017.
 */

public class TextListAdapter extends BaseQuickAdapter<DiscoveryBean.DataBeanX.DataBean,BaseViewHolder>{
    public TextListAdapter(@Nullable List<DiscoveryBean.DataBeanX.DataBean> data){
        super(R.layout.item_layout_text_list, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, DiscoveryBean.DataBeanX.DataBean item){
        holder.setText(R.id.tv_title, item.getLabel());
    }
}

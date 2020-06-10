package com.apptutti.tuttistore.adapter.discover;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.apptutti.tuttistore.R;
import com.apptutti.tuttistore.bean.discover.DiscoveryBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by 006 on 23/10/2017.
 */

public class AppListAdapter extends BaseQuickAdapter<DiscoveryBean.DataBeanX.DataBean,BaseViewHolder>{
    public AppListAdapter( @Nullable List<DiscoveryBean.DataBeanX.DataBean> data){
        super(R.layout.item_layout_app_list, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, DiscoveryBean.DataBeanX.DataBean item){

        //有些bean只有ICON没有banner，或者只有banner没有ICON，防止闪退这里只能这样处理
        String original_url;

        if(null==item.getBanner().getOriginal_url()){
            original_url=item.getBanner().getOriginal_url();

        }else{
            original_url=item.getBanner().getOriginal_url();
        }



        Glide.with(mContext)
                .load(original_url+"?")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.iv_error)
                .dontAnimate()
                .into((ImageView) holder.getView(R.id.iv_icon));
        holder.setText(R.id.tv_title, item.getTitle());
    }
}

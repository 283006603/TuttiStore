package com.apptutti.tuttistore.adapter.discover;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.apptutti.tuttistore.R;
import com.apptutti.tuttistore.bean.discover.DiscoveryBean;
import com.apptutti.tuttistore.utils.circle.GlideRoundTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by 李文强 on 23/10/2017.
 */

public class RecListAdapter extends BaseQuickAdapter<DiscoveryBean.DataBeanX.DataBean,BaseViewHolder>{
    public RecListAdapter(@Nullable List<DiscoveryBean.DataBeanX.DataBean> data){
        super(R.layout.item_layout_rec_list, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, DiscoveryBean.DataBeanX.DataBean item){



        Glide.with(mContext)
                .load(item.getBanner().getOriginal_url()+"?")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.iv_error)
                .dontAnimate()
                //以下注释是圆形图片系统自带，当然可以自定义
                /*.bitmapTransform(new CropCircleTransformation(mContext))*/
                .transform(new CenterCrop(mContext), new GlideRoundTransform(mContext, 15))
                .into((ImageView) holder.getView(R.id.iv_icon));

    }

















}

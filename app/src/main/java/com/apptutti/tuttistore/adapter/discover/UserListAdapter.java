package com.apptutti.tuttistore.adapter.discover;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.apptutti.tuttistore.R;
import com.apptutti.tuttistore.bean.discover.DiscoveryBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by 006 on 23/10/2017.
 */

public class UserListAdapter extends BaseQuickAdapter<DiscoveryBean.DataBeanX.DataBean,BaseViewHolder>{
    public UserListAdapter(@Nullable List<DiscoveryBean.DataBeanX.DataBean> data){
        super(R.layout.item_layout_user_list, data);
    }


    @Override
    protected void convert(BaseViewHolder holder, DiscoveryBean.DataBeanX.DataBean item){
        //这个模块，因为是抓包，所有图片的链接问号后面那些字符串全部要去掉,他们接口属于瞎搞
        String new_url;
        String avatar = item.getAvatar();
        if(avatar.contains("?")){
            int i = avatar.indexOf("?");
            new_url=item.getAvatar().substring(0,i);
        }else{
            new_url=avatar;
        }

        Log.d(TAG, new_url);
        Glide.with(mContext)
                .load(new_url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.iv_error)
                .dontAnimate()
                /*.transform(new CenterCrop(mContext), new GlideCircleTransform(mContext))*/
                .bitmapTransform(new CropCircleTransformation(mContext))
                .into((ImageView) holder.getView(R.id.iv_icon));
        holder.setText(R.id.tv_name, item.getName());
        holder.setText(R.id.tv_reason, item.getVerified().getReason());
        Glide.with(mContext)
                .load(item.getVerified().getUrl())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.iv_error)
                .dontAnimate()
                .into((ImageView) holder.getView(R.id.im_reason));
    }
}

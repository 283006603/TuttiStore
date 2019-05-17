package com.apptutti.tuttistore.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.apptutti.tuttistore.R;
import com.apptutti.tuttistore.bean.GameRecoBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author zzq  作者 E-mail:   283006603@qq.com
 * @date 创建时间：2017/9/5 23:15
 * 描述:活动中心
 */
public class GameRecoAdapter extends BaseQuickAdapter<GameRecoBean.ListBean, BaseViewHolder> {

    //多布局http://blog.csdn.net/cym492224103/article/details/51222414
    public GameRecoAdapter(@Nullable List<GameRecoBean.ListBean> data) {
        super(R.layout.item_game_reco, data);

    }


    @Override
    protected void convert(BaseViewHolder holder, GameRecoBean.ListBean listBean) {
        Glide.with(mContext)
                .load(listBean.cover)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.iv_error)//??
                .dontAnimate()
                .into((ImageView) holder.getView(R.id.iv_preview));
        holder.setText(R.id.tv_title, listBean.title)/*.
                setImageResource(R.id.iv_state, listBean.state == 1 ? R.drawable.iv_error : R.drawable.iv_error)*/;
         /*holder.itemView.setOnClickListener(view-> BrowerActivity.startActivity(mContext,listBean.link,listBean.title,listBean.cover));*/

    }
}

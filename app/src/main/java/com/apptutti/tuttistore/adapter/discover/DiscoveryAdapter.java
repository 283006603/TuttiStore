package com.apptutti.tuttistore.adapter.discover;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.apptutti.tuttistore.R;
import com.apptutti.tuttistore.bean.discover.DiscoveryBean;
import com.apptutti.tuttistore.bean.discover.MulDiscoveryBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 006 on 12/10/2017.
 */

public class DiscoveryAdapter extends BaseMultiItemQuickAdapter<MulDiscoveryBean, BaseViewHolder>{

    public DiscoveryAdapter(@Nullable List<MulDiscoveryBean> data){
        super(data);
        Log.d("DiscoveryAdapter", data.size()+"");
        addItemType(MulDiscoveryBean.REC_LIST_BANNER, R.layout.item_rec_list_banner_test);//..
        addItemType(MulDiscoveryBean.REC_LIST, R.layout.item_rec_list_test);
        addItemType(MulDiscoveryBean.TEXT_LIST, R.layout.item_text_list_test);//..
        addItemType(MulDiscoveryBean.APP_LIST, R.layout.item_app_list_test);
        addItemType(MulDiscoveryBean.USER_LIST, R.layout.item_user_list_test);
    }

    @Override
    protected void convert(BaseViewHolder holder, MulDiscoveryBean mulRankingBean){
        DiscoveryBean.DataBeanX dataBeanX = mulRankingBean.dataBeanX;
        //模块类型
        String type = dataBeanX.getType();
        //标题
        String label = dataBeanX.getLabel();
        //模块类型区分
        int style = dataBeanX.getStyle();
        //各个模块
        List<DiscoveryBean.DataBeanX.DataBean> data = dataBeanX.getData();
        switch(holder.getItemViewType()){
            case MulDiscoveryBean.REC_LIST_BANNER:
                //头布局Banner
                Banner bannar = holder.getView(R.id.banner);
                List<String> urls = new ArrayList<>();
                for(DiscoveryBean.DataBeanX.DataBean banner_url : data){
                    urls.add(banner_url.getBanner().getOriginal_url() + "?");
                }
                bannar.setIndicatorGravity(BannerConfig.CENTER).setImages(urls).setImageLoader(new GlideImageLoader()).start();
                break;
            case MulDiscoveryBean.REC_LIST:
                //横向RecycleView
                RecyclerView recycle_rec=holder.getView(R.id.recycler);
                recycle_rec.setHasFixedSize(false);
                recycle_rec.setNestedScrollingEnabled(false);
                LinearLayoutManager layoutManager_rec = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
                recycle_rec.setLayoutManager(layoutManager_rec);
                recycle_rec.setAdapter(new RecListAdapter(data));
                break;
            case MulDiscoveryBean.TEXT_LIST:
                //横向RecycleView
                RecyclerView recycle_text=holder.getView(R.id.recycler);
                recycle_text.setHasFixedSize(false);
                recycle_text.setNestedScrollingEnabled(false);
                LinearLayoutManager layoutManager_text = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
                recycle_text.setLayoutManager(layoutManager_text);
                recycle_text.setAdapter(new TextListAdapter(data));
                break;
            case MulDiscoveryBean.APP_LIST:
                //标题
                holder.setText(R.id.lable,label);
                //横向RecycleView
                RecyclerView recycle_applist=holder.getView(R.id.recycler);
                recycle_applist.setHasFixedSize(false);
                recycle_applist.setNestedScrollingEnabled(false);
                LinearLayoutManager layoutManager_applist = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
                recycle_applist.setLayoutManager(layoutManager_applist);
                recycle_applist.setAdapter(new AppListAdapter(data));
                break;
            case MulDiscoveryBean.USER_LIST:
                //标题
                holder.setText(R.id.lable,label);
                //横向RecycleView
                RecyclerView recycle_userlist=holder.getView(R.id.recycler);
                recycle_userlist.setHasFixedSize(false);
                recycle_userlist.setNestedScrollingEnabled(false);
                LinearLayoutManager layoutManager_userlist = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
                recycle_userlist.setLayoutManager(layoutManager_userlist);
                recycle_userlist.setAdapter(new UserListAdapter(data));
                break;
        }
    }

    private static class GlideImageLoader extends ImageLoader{
        @Override
        public void displayImage(Context context, Object path, ImageView imageView){
            Glide.with(context).load((String) path).diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate().into(imageView);
        }
    }
}

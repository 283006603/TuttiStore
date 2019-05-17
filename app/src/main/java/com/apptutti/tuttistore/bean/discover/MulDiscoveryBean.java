package com.apptutti.tuttistore.bean.discover;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by 006 on 23/10/2017.
 */

public class MulDiscoveryBean implements MultiItemEntity{


    public static final int REC_LIST_BANNER = 1;
    public static final int REC_LIST = 2;
    public static final int TEXT_LIST = 3;
    public static final int APP_LIST = 4;
    public static final int USER_LIST = 5;
    public int mItemType;
    public List<DiscoveryBean.DataBeanX> listdataBeanX;
    public DiscoveryBean.DataBeanX dataBeanX;

    public MulDiscoveryBean(int mItemType, DiscoveryBean.DataBeanX dataBeanX){
        this.mItemType=mItemType;
        this.dataBeanX = dataBeanX;
    }

    public MulDiscoveryBean(int mItemType, List<DiscoveryBean.DataBeanX> listdataBeanX){
        this.mItemType = mItemType;
        this.listdataBeanX = listdataBeanX;
    }

    @Override
    public int getItemType(){
        return mItemType;
    }

    @Override
    public String toString(){
        return "MulDiscoveryBean{" + "mItemType=" + mItemType + ", listdataBeanX=" + listdataBeanX + ", dataBeanX=" + dataBeanX + '}';
    }
}

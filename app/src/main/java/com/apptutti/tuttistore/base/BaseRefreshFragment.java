package com.apptutti.tuttistore.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.apptutti.tuttistore.R;
import com.apptutti.tuttistore.utils.AppUtils;
import com.apptutti.tuttistore.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * @author zzq  作者 E-mail:   283006603@qq.com.com
 * @date 创建时间：2017/9/15 11:13
 * 描述:基础刷新的Fragment
 */

public abstract class BaseRefreshFragment<T extends BaseContract.BasePresenter, K> extends BaseFragment<T> implements SwipeRefreshLayout.OnRefreshListener{
    protected RecyclerView mRecycler;
    protected SwipeRefreshLayout mRefresh;
    protected boolean mIsRefreshing = false;
    protected List<K> mList = new ArrayList<>();
    protected int ScrollState;

    @Override
    protected void initRefreshLayout(){
        if(mRefresh != null){
            mRefresh.setColorSchemeResources(R.color.colorPrimary);
            mRecycler.post(new Runnable(){
                @Override
                public void run(){
                    mRefresh.setRefreshing(true);
                    lazyLoadData();
                }
            });
            mRefresh.setOnRefreshListener(this);
        }
        //自己做处理，点击无网络图片的error重新加载
        mError.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mRefresh.setRefreshing(true);
                onRefresh();
            }
        });
    }

    @Override
    public void onRefresh(){
        clearData();
        lazyLoadData();
    }

    @Override
    protected void clearData(){
        mIsRefreshing = true;
    }

    @Override
    public void finishCreateView(Bundle state){
        mRefresh = ButterKnife.findById(mRootView, R.id.refresh);
        mRecycler = ButterKnife.findById(mRootView, R.id.recycler);
        isPrepared = true;
        initRecycleViewScoll();
        lazyLoad();
    }

    //要想隐藏主界面Activity，必须给RecycleView上下触摸滑动的监听
    protected void initRecycleViewScoll(){
        String currentActivityName = getActivity().getClass().getSimpleName();
        if(currentActivityName.equals("MainActivity") && mRecycler != null){
            mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener(){
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState){
                    ScrollState = newState;
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                    if(ScrollState == 1 && dy < 0){
                        ScrollState = 0;
                        Log.d("BaseRefreshFragment", "向下滑动啦(显示)");
                        getActivity().findViewById(R.id.main_RadioGroup).setVisibility(View.VISIBLE);
                        // visible(R.id.main_RadioGroup);传ID有BUG，传控件没有
                    }else if(ScrollState == 1 && dy > 0){
                        ScrollState = 0;
                        Log.d("BaseRefreshFragment", "向上滑动啦(隐藏)");
                        getActivity().findViewById(R.id.main_RadioGroup).setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    @Override
    protected void lazyLoad(){
        if(!isPrepared || !isVisible){
            return;
        }
        initRefreshLayout();
        initRecyclerView();
        if(mRefresh == null)
            lazyLoadData();
        isPrepared = false;
    }

    @Override
    public void complete(){
        super.complete();
        new AppUtils().runOnUIDelayed(new Runnable(){
            @Override
            public void run(){
                if(mRefresh != null)
                    mRefresh.setRefreshing(false);
            }
        }, 650);
        if(mIsRefreshing){
            if(mList != null)
                mList.clear();
            clear();
            ToastUtils.showSingleLongToast("刷新成功");
        }
        mIsRefreshing = false;
    }

    protected void clear(){
    }

    @Override
    public void initWidget(){
    }
}

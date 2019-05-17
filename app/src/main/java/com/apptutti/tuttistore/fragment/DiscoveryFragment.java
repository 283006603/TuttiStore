package com.apptutti.tuttistore.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.apptutti.tuttistore.R;
import com.apptutti.tuttistore.adapter.discover.DiscoveryAdapter;
import com.apptutti.tuttistore.base.BaseRefreshFragment;
import com.apptutti.tuttistore.bean.discover.DiscoveryBean;
import com.apptutti.tuttistore.bean.discover.MulDiscoveryBean;
import com.apptutti.tuttistore.mvp.contract.DiscoveryContract;
import com.apptutti.tuttistore.mvp.presenter.DiscoveryPresenter;
import com.apptutti.tuttistore.utils.AppUtils;
import com.apptutti.tuttistore.utils.NetworkUtils;
import com.apptutti.tuttistore.widge.CustomLoadMoreView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoveryFragment extends BaseRefreshFragment<DiscoveryPresenter, MulDiscoveryBean> implements DiscoveryContract.View, BaseMultiItemQuickAdapter.RequestLoadMoreListener{

    //-------------注意
    //-------------多类型自己要新建一个类去整合解析的类
    private DiscoveryAdapter mAdapter;
    private int mPage = 1;
    private static final int PS = 20;
    private int mTotal = 0;
    private boolean mIsError = false;
    private boolean mIsLoadMore = false;
    //后期与杨静配合要增加mToal，要增加分页，有需要要用到H5

    public static DiscoveryFragment newInstance(/*String uid*/){
        DiscoveryFragment fragment = new DiscoveryFragment();
        /*Bundle args = new Bundle();
        args.putString("uid", uid);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public int getLayoutId(){
        return R.layout.fragment_discovery;
    }

    //binder问题
    @Override
    protected void initPresenter(){
        mPresenter = new DiscoveryPresenter();
        super.initPresenter();
    }

    @Override
    public void initVariables(){
    }

    @Override
    public void initWidget(){
        //有网是true，没网是false
        NetworkUtils.setOnChangeInternetListener(new NetworkUtils.OnChangeInternetListener(){
            @Override
            public void changeInternet(boolean flag){
                mIsError = !flag;
                Log.d("DiscoveryFragment", "changeInternet flag:=" + flag);
                if(!mIsError){
                    Log.d("DiscoveryFragment", "mAdapter:" + mAdapter);
                    if(mAdapter!=null)
                    mAdapter.setEnableLoadMore(true);

                }
            }
        });
    }

    //懒加载
    @Override
    protected void lazyLoadData(){
        Log.d("DiscoveryFragment", "lazyLoadData mPage:=" + mPage);
        mPresenter.getDiscoveryData(mPage, PS);

    }

    //解决BUG:mList.clear(); mAdapter.notifyDataSetChanged();mList变化一定要随时调用
    @Override
    protected void clearData(){
        super.clearData();
        mList.clear();
        mAdapter.notifyDataSetChanged();
        mPage = 1;
        mIsLoadMore = false;
        mIsError = false;
        //刷新时候关闭上拉加载
        mAdapter.setEnableLoadMore(false);
    }

    @Override
    protected void initRecyclerView(){
        mAdapter = new DiscoveryAdapter(mList);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mAdapter.isFirstOnly(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getSupportActivity()).build());
        mRecycler.setAdapter(mAdapter);
        mAdapter.setLoadMoreView(new CustomLoadMoreView());
        //设置加载更多监听
        mAdapter.setOnLoadMoreListener(this, mRecycler);

    }

    @Override
    protected void finishTask(){
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showRanking(DiscoveryBean rankingBean){
        //----------------------------------------------------------------------
        if(!mIsLoadMore){
            for(DiscoveryBean.DataBeanX hello : rankingBean.getData()){
                Log.d("DiscoveryFragment", "hello:" + hello);
                switch(hello.getType()){
                    case "rec_list":
                        if(hello.getStyle()==0){
                            mList.add(new MulDiscoveryBean(MulDiscoveryBean.REC_LIST_BANNER, hello));
                        }else{
                            mList.add(new MulDiscoveryBean(MulDiscoveryBean.REC_LIST, hello));
                        }
                        break;
                    case "text_list":
                        mList.add(new MulDiscoveryBean(MulDiscoveryBean.TEXT_LIST, hello));
                        break;
                    case "app_list":
                        mList.add(new MulDiscoveryBean(MulDiscoveryBean.APP_LIST, hello));
                        break;
                    case "user_list":
                        mList.add(new MulDiscoveryBean(MulDiscoveryBean.USER_LIST, hello));
                        break;
                    //----------------------------------------------------------------------
                }
            }


           /* mList.addAll();*/
           /* mTotal = total;//总数*/
            finishTask();
        }else{
            //加载更多
            /*mAdapter.addData(rankingBean);*/
            mAdapter.loadMoreComplete();//加载完成
            Log.d("DiscoveryFragment", "bbb");
        }
    }

    @Override
    public void onLoadMoreRequested(){
        Log.d("DiscoveryFragment", "mTotal=" + mTotal);
        Log.d("DiscoveryFragment", "onLoadMoreRequested mIsError:" + mIsError);
        Log.d("DiscoveryFragment", "mAdapter.getItemCount():" + mAdapter.getItemCount());
        AppUtils.runOnUIDelayed(new Runnable(){
            @Override
            public void run(){
                //加载更多
                if(mAdapter.getItemCount() > mTotal){//以前有等于号，去掉就正确，不要>=
                    mAdapter.loadMoreEnd();//结束加载
                    Log.d("DiscoveryFragment", "结束加载");
                }else{
                    if(!mIsError){
                        mPage++;
                        Log.d("DiscoveryFragment", "!mIsError mPage: =" + mPage);
                        lazyLoadData();
                        Log.d("DiscoveryFragment", "正常加载");
                    }else{
                        mIsError = true;
                        mAdapter.loadMoreFail();//加载失败
                        Log.d("DiscoveryFragment", "加载失败");
                    }
                }
            }
        }, 650);
    }

    //-----------------------------------------------------
    @Override
    public void showError(String msg){
        super.showError(msg);
        mIsError = true;
        //---
        Log.d("DiscoveryFragment", "showError mList.size():" + mList.size());
        if(mList.size() != 0){
            mAdapter.loadMoreFail();
            gone(mError);
        }else{
            visible(mError);
        }
        //--
        Log.d("DiscoveryFragment", "showError" + msg);
    }

    @Override
    public void complete(){
        super.complete();
        //需要重新开启监听
        mAdapter.setEnableLoadMore(true);
    }

    //----binder问题
    @Override
    public void onDestroy(){
        Log.d("DiscoveryFragment", "mPresenter=" + mPresenter);
        super.onDestroy();
        Log.d("DiscoveryFragment", "mPresenter 222222=" + mPresenter);
    }
}

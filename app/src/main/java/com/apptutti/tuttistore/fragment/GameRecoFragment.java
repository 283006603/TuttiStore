package com.apptutti.tuttistore.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.apptutti.tuttistore.R;
import com.apptutti.tuttistore.adapter.GameRecoAdapter;
import com.apptutti.tuttistore.base.BaseRefreshFragment;
import com.apptutti.tuttistore.bean.GameRecoBean;
import com.apptutti.tuttistore.mvp.contract.GameRecoContract;
import com.apptutti.tuttistore.mvp.presenter.GameRecoPresenter;
import com.apptutti.tuttistore.utils.AppUtils;
import com.apptutti.tuttistore.utils.NetworkUtils;
import com.apptutti.tuttistore.widge.CustomLoadMoreView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameRecoFragment extends BaseRefreshFragment<GameRecoPresenter, GameRecoBean.ListBean> implements GameRecoContract.View, BaseQuickAdapter.RequestLoadMoreListener{

    private GameRecoAdapter mAdapter;
    private int mPage = 1;
    private static final int PS = 20;
    private int mTotal = 0;
    private boolean mIsError = false;
    private boolean mIsLoadMore = false;

    public static GameRecoFragment newInstance(/*String uid*/){
        GameRecoFragment fragment = new GameRecoFragment();
        /*Bundle args = new Bundle();
        args.putString("uid", uid);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public int getLayoutId(){
        return R.layout.fragment_game_reco;
    }

    //binder问题
    @Override
    protected void initPresenter(){
        mPresenter = new GameRecoPresenter();
        super.initPresenter();
    }

    @Override
    public void initVariables(){
       /* Bundle bundle = getArguments();
        if (bundle != null) {
            mType = bundle.getString(Constants.EXTRA_TYPE);
        }*/
    }

    @Override
    public void initWidget(){
        //有网是true，没网是false
        NetworkUtils.setOnChangeInternetListener(new NetworkUtils.OnChangeInternetListener(){
            @Override
            public void changeInternet(boolean flag){
                mIsError = !flag;
                Log.d("GameRecoFragment", "changeInternet flag:=" + flag);
                if(!mIsError){
                    Log.d("GameRecoFragment", "mAdapter:" + mAdapter);
                    if(mAdapter!=null)
                    mAdapter.setEnableLoadMore(true);
                }
            }
        });
    }

    //懒加载
    @Override
    protected void lazyLoadData(){
        Log.d("GameRecoFragment", "lazyLoadData mPage:=" + mPage);
        mPresenter.getGameRecoData(mPage, PS);
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
        mAdapter = new GameRecoAdapter(mList);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mAdapter.isFirstOnly(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setAdapter(mAdapter);
        mAdapter.setLoadMoreView(new CustomLoadMoreView());
        //设置加载更多监听
        mAdapter.setOnLoadMoreListener(this, mRecycler);
    }

    @Override
    protected void finishTask(){
        mAdapter.setNewData(mList);
        /*mAdapter.notifyDataSetChanged();*/
    }

    @Override
    public void showGameReco(List<GameRecoBean.ListBean> listBeanList, int total){
        if(!mIsLoadMore){
            mList.addAll(listBeanList);
            mTotal = total;//总数
            finishTask();
        }else{
            //加载更多
            mAdapter.addData(listBeanList);
            mAdapter.loadMoreComplete();//加载完成
            Log.d("GameRecoFragment", "bbb");
        }
    }

    @Override
    public void onLoadMoreRequested(){
        Log.d("GameRecoFragment", "mTotal=" + mTotal);
        Log.d("GameRecoFragment", "onLoadMoreRequested mIsError:" + mIsError);
        Log.d("GameRecoFragment", "mAdapter.getItemCount():" + mAdapter.getItemCount());
        AppUtils.runOnUIDelayed(new Runnable(){
            @Override
            public void run(){
                //加载更多
                if(mAdapter.getItemCount() >= mTotal){//要等号，不然会一致加载，具体可以掩饰一遍
                    mAdapter.loadMoreEnd();//结束加载
                    Log.d("GameRecoFragment", "结束加载");
                }else{
                    if(!mIsError){
                        mPage++;
                        Log.d("GameRecoFragment", "!mIsError mPage: =" + mPage);
                        lazyLoadData();
                        Log.d("GameRecoFragment", "正常加载");
                    }else{
                        mIsError = true;
                        mAdapter.loadMoreFail();//加载失败
                        Log.d("GameRecoFragment", "加载失败");
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
        Log.d("GameRecoFragment", "showError mList.size():" + mList.size());
        if(mList.size() != 0){
            mAdapter.loadMoreFail();
            gone(mError);
        }else{
            visible(mError);
        }
        //--
        Log.d("GameRecoFragment", "showError" + msg);
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
        Log.d("GameRecoFragment", "mPresenter=" + mPresenter);
        super.onDestroy();
        Log.d("GameRecoFragment", "mPresenter 222222=" + mPresenter);
    }
}

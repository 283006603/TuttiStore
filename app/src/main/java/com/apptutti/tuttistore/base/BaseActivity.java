package com.apptutti.tuttistore.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.apptutti.tuttistore.R;
import com.apptutti.tuttistore.utils.AppUtils;
import com.apptutti.tuttistore.widge.statusbar.StatusBarUtil;

import butterknife.ButterKnife;

public abstract class BaseActivity<T extends BaseContract.BasePresenter> extends AppCompatActivity implements BaseContract.BaseView{

    protected T mPresenter;
    protected Toolbar mToolbar;//Toolbar
    protected Context mContext;//上下文环境
    protected boolean mBack = true;
    private ConstraintLayout mError;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mContext = this;
        ButterKnife.bind(this);
        mToolbar = ButterKnife.findById(this, R.id.toolbar);
        mError = ButterKnife.findById(this, R.id.cl_error);
        initStatusBar();//初始化StatusBar  时间  电池量那一行
        initPresenter();//初始化Presenter
        initVariables();//初始化变量
        MyApplication.getInstance().addActivity(this);
        if(mToolbar != null){
            //初始化Toolbar
            initToolbar();
            //让组件支持Toolbar
            setSupportActionBar(mToolbar);
            if(mBack)
                mToolbar.setNavigationOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        finish();
                    }
                });
        }
        initWidget();
        initDatas();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //如果用以下这种做法则不保存状态，再次进来的话会显示默认tab
        //总是执行这句代码来调用父类去保存视图层的状态
        //super.onSaveInstanceState(outState);
    }

    /**
     * 初始化StatusBar  时间  电池量那一行
     */
    protected void initStatusBar(){
        StatusBarUtil.setColorNoTranslucent((Activity) mContext, AppUtils.getColor(R.color.colorPrimary));
    }


    /**
     * 布局文件
     *
     * @return 布局文件
     */
    protected abstract int getLayoutId();



    /**
     * 初始化Toolbar
     */
    protected void initToolbar(){
        if(mBack)
            mToolbar.setNavigationIcon(R.drawable.ic_clip_back_white);
    }

    /**
     * 初始化控件
     */
    protected void initWidget(){
    }

    /**
     * 加载数据
     */
    protected void loadData(){
    }

    /**
     * 初始化数据
     */
    protected void initDatas(){
        loadData();
    }

    /**
     * 初始化变量
     */
    protected void initVariables(){
    }


    //BaseRefresh调用
    protected void initRecyclerView() {

    }


    //真正的当前Activity调用
    /**
     * 完成请求
     */
    protected void finishTask() {
    }

    /**
     * 初始化Presenter
     */
    private void initPresenter(){
        if(mPresenter != null)
            mPresenter.attachView(this);
    }

    /**
     * 隐藏View
     *
     * @param views 视图
     */
    protected void gone(final View... views){
        if(views != null && views.length > 0){
            for(View view : views){
                if(view != null){
                    view.setVisibility(View.GONE);
                }
            }
        }
    }

    /**
     * 显示View
     *
     * @param views 视图
     */
    protected void visible(final View... views){
        if(views != null && views.length > 0){
            for(View view : views){
                if(view != null){
                    view.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /**
     * 隐藏View
     *
     * @param id
     */
    protected void gone(final @IdRes int... id){
        if(id != null && id.length > 0){
            for(int resId : id){
                View view = $(resId);
                if(view != null)
                    gone(view);
            }
        }
    }

    /**
     * 显示View
     *
     * @param id
     */
    protected void visible(final @IdRes int... id){
        if(id != null && id.length > 0){
            for(int resId : id){
                View view = $(resId);
                if(view != null)
                    visible(view);
            }
        }
    }

    private View $(@IdRes int id){
        View view;
        if(this != null){
            view = this.findViewById(id);
            return view;
        }
        return null;
    }

    @Override
    public void showError(String msg){
        if(mError != null){
            visible(mError);
        }
    }

    @Override
    public void complete(){
        if(mError != null){
            gone(mError);
        }
    }

    //------------------------------------------------------------------------------------生命周期
    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        if(mPresenter != null)
            mPresenter.detachView();
        MyApplication.getInstance().removeActivity(this);
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
    }
}

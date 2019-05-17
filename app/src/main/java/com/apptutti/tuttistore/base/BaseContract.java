package com.apptutti.tuttistore.base;

/**
 * 基础契约类 用来管理 presenter 与 view
 */

public interface BaseContract{

    interface BaseView {

        /**
         * 请求出错
         */
        void showError(String msg);

        /**
         * 请求完成
         */
        void complete();
    }

    interface BasePresenter<T>  {

        /**
         * 绑定
         *
         * @param view view
         */
        void attachView(T view);

        /**
         * 解绑
         */
        void detachView();
    }

}

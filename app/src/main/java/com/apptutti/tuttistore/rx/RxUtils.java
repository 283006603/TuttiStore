package com.apptutti.tuttistore.rx;

import com.apptutti.tuttistore.network.exception.ApiException;
import com.apptutti.tuttistore.network.response.HttpResponse;

import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author zzq  作者 E-mail:   soleilyoyiyi@gmail.com
 * @date 创建时间：2017/9/7 15:37
 * 描述:RxUtils
 */
public class RxUtils {
    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程 统一处理线程
        return new FlowableTransformer<T, T>() {
            @Override
            public Flowable<T> apply(Flowable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 生成Flowable
     *
     * @param <T>
     * @return
     */
    public static <T> Flowable<T> createData(final T t) {
       return Flowable.create(new FlowableOnSubscribe<T>() {
           @Override
           public void subscribe(FlowableEmitter<T> emitter) throws Exception {
               try {
                   emitter.onNext(t);
                   emitter.onComplete();
               } catch (Exception e) {
                   emitter.onError(e);
               }
           }
       }, BackpressureStrategy.BUFFER);
    }

    /**
     * 生成Flowable
     *
     * @param <T>
     * @return
     */
    public static <T> Flowable<List<T>> createData(final List<T> t) {
        return Flowable.create(new FlowableOnSubscribe<List<T>>(){
            @Override
            public void subscribe(@NonNull FlowableEmitter<List<T>> emitter) throws Exception{
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }

    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<HttpResponse<T>, T> handleResult() {


        return new FlowableTransformer<HttpResponse<T>, T>() {
            @Override
            public Publisher<T> apply(Flowable<HttpResponse<T>> upstream) {
                return upstream.flatMap(new Function<HttpResponse<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(HttpResponse<T> httpResponse) throws Exception {
                        if (httpResponse.code == 0) {
                            if (httpResponse.data != null)
                                return createData(httpResponse.data);
                            if (httpResponse.result != null)
                                return createData(httpResponse.result);
                            return Flowable.error(new ApiException("服务器返回error"));
                        } else {
                            return Flowable.error(new ApiException("服务器返回error"));
                        }
                    }
                });
            }
        };




    }


    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<HttpResponse<List<T>>, List<T>> handleListResult() {
       return new FlowableTransformer<HttpResponse<List<T>>, List<T>>(){
           @Override
           public Publisher<List<T>> apply(@NonNull Flowable<HttpResponse<List<T>>> httpResponseFlowable){
               return httpResponseFlowable.flatMap(new Function<HttpResponse<List<T>>, Flowable<List<T>>>(){
                   @Override
                   public Flowable<List<T>> apply(@NonNull HttpResponse<List<T>> listHttpResponse) throws Exception{
                       if (listHttpResponse.code == 0) {
                           if (listHttpResponse.data != null)
                               return createData(listHttpResponse.data);
                           if (listHttpResponse.result != null)
                               return createData(listHttpResponse.result);
                           return Flowable.error(new ApiException("服务器返回error"));
                       } else {
                           return Flowable.error(new ApiException("服务器返回error"));
                       }
                   }
               });
           }
       };
    }
}

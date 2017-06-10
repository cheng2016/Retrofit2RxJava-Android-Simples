package com.mitnick.rxjava.http;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by chengzj on 2017/6/10.
 */

public class RxJavaManager {
    private static RxJavaManager rxInstance;

    private Stack<RxJavaMethod> list = new Stack<RxJavaMethod>();

    private RxJavaManager() {

    }

    public static RxJavaManager getRxInstance(){
        if(rxInstance == null){
            synchronized (RxJavaManager.class){
                if(rxInstance == null){
                    rxInstance = new RxJavaManager();
                }
            }
        }
        return rxInstance;
    }


    public void regist(Object obj){
        Class<?> subscriberClass = obj.getClass();
        RxJavaMethod rxJavaMethod = new RxJavaMethod(subscriberClass,new CompositeSubscription());
        list.push(rxJavaMethod);
    }

    public void unregist(Object obj){
        for (RxJavaMethod rx:list) {
            if(rx.activity.getClass() == obj.getClass()){
                if(rx.compositeSubscription.hasSubscriptions()){
                    rx.compositeSubscription.unsubscribe();
                }
                list.remove(rx);
                break;
            }
        }
    }

    public CompositeSubscription getCompositeSubscription(){
        CompositeSubscription c = list.firstElement().compositeSubscription;
        return c;
    }

}

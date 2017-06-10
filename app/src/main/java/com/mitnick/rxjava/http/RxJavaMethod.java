package com.mitnick.rxjava.http;

import android.app.Activity;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by chengzj on 2017/6/10.
 */

public class RxJavaMethod {
    public Class<?> activity;
    public CompositeSubscription compositeSubscription;

    public RxJavaMethod(Class<?> activity, CompositeSubscription compositeSubscription) {
        this.activity = activity;
        this.compositeSubscription = compositeSubscription;
    }
}

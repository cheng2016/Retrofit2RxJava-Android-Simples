package com.mitnick.rxjava.http;


import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by chengzj on 2017/6/10.
 */

public class RxJavaManager {
    private static RxJavaManager rxInstance;

    private Map<Object,CompositeSubscription> map = new LinkedHashMap<>();

    public Map<Object, CompositeSubscription> getMap() {
        return map;
    }

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


    public void regist(Object subscriber){
        map.put(subscriber,new CompositeSubscription());
    }

    public void unregist(Object subscriber){
        CompositeSubscription compositeSubscription = map.get(subscriber);
        compositeSubscription.clear();
        map.remove(subscriber);
    }

    public CompositeSubscription getCompositeSubscription(){
        CompositeSubscription c = getTail().getValue();
        if(c.hasSubscriptions()){
            c.clear();
        }
        return c;
    }

    public Entry<Object, CompositeSubscription> getTail (){
        Iterator<Entry<Object, CompositeSubscription>> iterator = map.entrySet().iterator();
        Entry<Object, CompositeSubscription> tail = null;
        while (iterator.hasNext()) {
            tail = iterator.next();
        }
        return tail;
    }
}

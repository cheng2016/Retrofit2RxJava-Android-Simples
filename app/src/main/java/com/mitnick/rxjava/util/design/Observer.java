package com.mitnick.rxjava.util.design;

/**
 * Created by Michael Smith on 2016/7/24.
 * 观察者(观众)
 */

public interface Observer {
    public void register(Subject subject);

    public void unregister(Subject subject);

    public void notifyData();
}

package com.mitnick.rxjava.lnterface;

/**
 * Created by Michael Smith on 2016/7/24.
 * 观察者
 */

public interface Observer {
    public void register(Observable observable);

    public void unregister(Observable observable);

    public void notifyData();
}

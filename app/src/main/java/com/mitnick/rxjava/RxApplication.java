package com.mitnick.rxjava;

import android.app.Application;
import com.mitnick.rxjava.util.AppUtils;
import com.mitnick.rxjava.util.L;


/**
 * Created by mitnick.cheng on 2016/7/21.
 */

public class RxApplication extends Application {

    private static RxApplication sInstance;

    public static String DEBUG_MODE = "debugMode";

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        L.isDebug = AppUtils.getBooleanMetaData(this , DEBUG_MODE);
    }

    public  synchronized static  RxApplication getInstance(){
        return sInstance;
    }
}

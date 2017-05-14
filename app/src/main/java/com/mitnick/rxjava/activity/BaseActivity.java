package com.mitnick.rxjava.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

/**
 * Created by mitnick.cheng on 2016/7/24.
 */

public abstract class BaseActivity extends AppCompatActivity{
    protected final static String TAG = "BaseActivity";
    private ProgressDialog mProgressDialog;

    protected Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getRootViewId());
        context = this;
        initUI();
    }

    //模板模式，充当钩子
    protected abstract void initUI();

    protected abstract int getRootViewId();

    @Subscribe
    public  void onEventMainThread(Object event){
    };

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void showProgressDialog(String message) {
        if(mProgressDialog == null){
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage(message);
                mProgressDialog.setCanceledOnTouchOutside(false);
        }
        mProgressDialog.show();
    }

    public void hideProgressDialog(){
        if(mProgressDialog!=null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }
}

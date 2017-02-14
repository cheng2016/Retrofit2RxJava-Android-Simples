package com.mitnick.rxjava.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mitnick.rxjava.R;
import com.mitnick.rxjava.RxApplication;
import com.mitnick.rxjava.bean.Profile;
import com.mitnick.rxjava.bean.Token;
import com.mitnick.rxjava.net.FailedEvent;
import com.mitnick.rxjava.net.HttpImpl;
import com.mitnick.rxjava.net.MessageType;
import com.mitnick.util.PreferenceConstants;
import com.mitnick.util.PreferenceUtils;

/**
 * Created by mitnick.cheng on 2016/7/28.
 */

public class LoginActivity extends BaseActivity {
    private String mAccessToken = "";

    private TextView mTextView;

    @Override
    public void initView() {
        setContentView(R.layout.activity_login);
        mTextView = (TextView) findViewById(R.id.textView);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog("login...");
                HttpImpl.getInstance().login("Basic dG1qMDAxOjEyMzQ1Ng==");
            }
        });

        findViewById(R.id.loginAndGetProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText("init");
                showProgressDialog("login...");
                HttpImpl.getInstance().loginAndGetProfile("Basic dG1qMDAxOjEyMzQ1Ng==");
            }
        });
    }

    //activity_login.xml 中onClick方法
    void login(View view){
        showProgressDialog("login...");
        HttpImpl.getInstance().login("Basic dG1qMDAxOjEyMzQ1Ng==");
    }


    @Override
    public void onEventMainThread(Object event) {
        super.onEventMainThread(event);
        hideProgressDialog();
        if(event instanceof Token){
            Token token = (Token) event;
            mAccessToken = token.getAccess_token();
            PreferenceUtils.setPrefString(RxApplication.getInstance(), PreferenceConstants.REFRESH_TOKEN,token.getRefresh_token());
            Toast.makeText(LoginActivity.this,"登录成功！",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent().setClass(LoginActivity.this,MainActivity.class);
            intent.putExtra("accessToken",mAccessToken);
            startActivity(intent);
        }
        if(event instanceof Profile){
            Profile profile = (Profile) event;
            mTextView.setText("name :" + profile.getUsername()  +"\t email :"
                    + profile.getEmail() + "\t birthday :" + profile.getBirthday() + "\t height: "+ profile.getHeight() + "\t weight :"+ profile.getWeight());
        }
        if(event instanceof FailedEvent){
            int type = ((FailedEvent) event).getType();
            String message = ((FailedEvent) event).getObject()!=null ?
                    (((Throwable) ((FailedEvent) event).getObject()).getMessage().indexOf("504")!=-1 ? "请检查网络设置...":((Throwable) ((FailedEvent) event).getObject()).getMessage() )
                    : "";
            switch (type){
                case MessageType.LOGIN:
                    Toast.makeText(this,"登录失败！" + message,Toast.LENGTH_SHORT).show();
                    break;
                case MessageType.PROFILE:
                    Toast.makeText(this,"获取用户信息失败！" + message,Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(this,"应用程序异常！" + message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}

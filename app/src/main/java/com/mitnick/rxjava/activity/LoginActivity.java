package com.mitnick.rxjava.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mitnick.cheng on 2016/7/28.
 */

public class LoginActivity extends BaseActivity {
    @Bind(R.id.login)
    Button login;
    @Bind(R.id.loginAndGetProfile)
    Button loginAndGetProfile;
    @Bind(R.id.textView)
    TextView textView;

    private String mAccessToken = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    public void init() {
    }

    //activity_login.xml 中onClick方法
    void login(View view) {
        showProgressDialog("login...");
        HttpImpl.getInstance().login("Basic dG1qMDAxOjEyMzQ1Ng==");
    }

    @Override
    public void onEventMainThread(Object event) {
        super.onEventMainThread(event);
        hideProgressDialog();
        if (event instanceof Token) {
            Token token = (Token) event;
            mAccessToken = token.getAccess_token();
            PreferenceUtils.setPrefString(RxApplication.getInstance(), PreferenceConstants.REFRESH_TOKEN, token.getRefresh_token());
            Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent().setClass(LoginActivity.this, MainActivity.class);
            intent.putExtra("accessToken", mAccessToken);
            startActivity(intent);
        }
        if (event instanceof Profile) {
            Profile profile = (Profile) event;
            textView.setText("name :" + profile.getUsername() + "\t email :"
                    + profile.getEmail() + "\t birthday :" + profile.getBirthday() + "\t height: " + profile.getHeight() + "\t weight :" + profile.getWeight());
        }
        if (event instanceof FailedEvent) {
            int type = ((FailedEvent) event).getType();
            switch (type) {
                case MessageType.LOGIN:
                    Toast.makeText(this, "登录失败！", Toast.LENGTH_SHORT).show();
                    break;
                case MessageType.PROFILE:
                    Toast.makeText(this, "获取用户信息失败！", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(this, "应用程序异常！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick({R.id.login, R.id.loginAndGetProfile})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                showProgressDialog("login...");
                HttpImpl.getInstance().login("Basic dG1qMDAxOjEyMzQ1Ng==");
                break;
            case R.id.loginAndGetProfile:
                textView.setText("init");
                showProgressDialog("login...");
                HttpImpl.getInstance().loginAndGetProfile("Basic dG1qMDAxOjEyMzQ1Ng==");
                break;
        }
    }
}

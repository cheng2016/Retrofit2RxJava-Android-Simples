package com.mitnick.rxjava.activity;

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

public class MainActivity extends BaseActivity {

    @Bind(R.id.retrofitButton)
    Button retrofitButton;
    @Bind(R.id.rxjavaButton)
    Button rxjavaButton;
    @Bind(R.id.textView)
    TextView textView;

    //    private String mAuth = "Basic dG1qMDAxOjEyMzQ1Ng==";
    private String mAccessToken = "";

    @Override
    public void init() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (getIntent().getExtras() != null) {
            mAccessToken = getIntent().getExtras().getString("accessToken", "");
            textView.setText("access_token：" + mAccessToken);
        } else {
//            mTextView.setText("获取token失败，请重新登录！");
        }
    }


    @Override
    public void onEventMainThread(Object event) {
        super.onEventMainThread(event);
        hideProgressDialog();
        if (event instanceof Token) {
            Token token = (Token) event;
            textView.setText("access_token：" + token.getAccess_token());
            mAccessToken = token.getAccess_token();
        }
        if (event instanceof Profile) {
            Profile profile = (Profile) event;
            textView.setText("name：" + profile.getUsername());
//            startActivity(new Intent().setClass(MainActivity.this,MainActivity.class));
        }

        if (event instanceof FailedEvent) {
            int type = ((FailedEvent) event).getType();
            String message = ((FailedEvent) event).getObject() != null ?
                    (((Throwable) ((FailedEvent) event).getObject()).getMessage().indexOf("504") != -1 ? "请检查网络设置..." : ((Throwable) ((FailedEvent) event).getObject()).getMessage())
                    : "";
            switch (type) {
                case MessageType.PROFILE:
                    Toast.makeText(this, "获取用户信息失败！" + message, Toast.LENGTH_SHORT).show();
                    break;
                case MessageType.REFRESH:
                    Toast.makeText(this, "刷新数据失败！" + message, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(this, "应用程序异常！" + message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick({R.id.retrofitButton, R.id.rxjavaButton, R.id.textView})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.retrofitButton:
                textView.setText("init");
                showProgressDialog("wait...");
                HttpImpl.getInstance().getProfiles(mAccessToken);
                break;
            case R.id.rxjavaButton:
                textView.setText("init");
                showProgressDialog("wait...");
                HttpImpl.getInstance().getProfile(mAccessToken);
                break;
            case R.id.textView:
                textView.setText("init");
                showProgressDialog("wait...");
                String refreshToken = PreferenceUtils.getPrefString(RxApplication.getInstance(), PreferenceConstants.REFRESH_TOKEN, "");
                HttpImpl.getInstance().refresh(refreshToken);
                break;
        }
    }
}
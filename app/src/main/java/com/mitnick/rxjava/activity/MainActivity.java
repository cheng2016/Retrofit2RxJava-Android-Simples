package com.mitnick.rxjava.activity;

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

public class MainActivity extends BaseActivity {

//    private String mAuth = "Basic dG1qMDAxOjEyMzQ1Ng==";
    private String mAccessToken = "";

    private TextView mTextView;
    private Button mRetrofitButton,mRxjavaButton;

    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.textView);
        mRetrofitButton = (Button) findViewById(R.id.retrofitButton);
        mRxjavaButton = (Button) findViewById(R.id.rxjavaButton);
    }

    @Override
    public void initData(){
        if(getIntent().getExtras()!=null){
            mAccessToken = getIntent().getExtras().getString("accessToken","");
            mTextView.setText("access_token：" + mAccessToken);
        }else{
//            mTextView.setText("获取token失败，请重新登录！");
        }
    }

    @Override
    public void initEvent() {
        mRxjavaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText("init");
                showProgressDialog("wait...");
                HttpImpl.getInstance().getProfile(mAccessToken);
            }
        });

        mRetrofitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText("init");
                showProgressDialog("wait...");
                HttpImpl.getInstance().getProfiles(mAccessToken);
            }
        });

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText("init");
                showProgressDialog("wait...");
                String refreshToken = PreferenceUtils.getPrefString(RxApplication.getInstance(), PreferenceConstants.REFRESH_TOKEN,"");
                HttpImpl.getInstance().refresh(refreshToken);
            }
        });
    }

    @Override
    public void onEventMainThread(Object event) {
        super.onEventMainThread(event);
        hideProgressDialog();
        if(event instanceof Token){
            Token token = (Token) event;
            mTextView.setText("access_token：" + token.getAccess_token() );
            mAccessToken = token.getAccess_token();
        }
        if(event instanceof Profile){
            Profile profile = (Profile) event;
            mTextView.setText("name：" + profile.getUsername());
//            startActivity(new Intent().setClass(MainActivity.this,MainActivity.class));
        }

        if(event instanceof FailedEvent){
            int type = ((FailedEvent) event).getType();
            String message = ((FailedEvent) event).getObject()!=null ?
                    (((Throwable) ((FailedEvent) event).getObject()).getMessage().indexOf("504")!=-1 ? "请检查网络设置...":((Throwable) ((FailedEvent) event).getObject()).getMessage() )
                    : "";
            switch (type){
                case MessageType.PROFILE:
                    Toast.makeText(this, "获取用户信息失败！" + message, Toast.LENGTH_SHORT).show();
                    break;
                case MessageType.REFRESH:
                    Toast.makeText(this, "刷新数据失败！" + message, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(this,"应用程序异常！" + message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
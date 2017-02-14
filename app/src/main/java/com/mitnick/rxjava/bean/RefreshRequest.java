package com.mitnick.rxjava.bean;

/**
 * Created by WH1604025 on 2016/6/3.
 */
public class RefreshRequest {
    private String refresh_token;

    public RefreshRequest(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}

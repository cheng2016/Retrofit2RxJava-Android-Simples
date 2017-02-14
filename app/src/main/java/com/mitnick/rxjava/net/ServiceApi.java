package com.mitnick.rxjava.net;

import com.mitnick.rxjava.bean.Profile;
import com.mitnick.rxjava.bean.RefreshRequest;
import com.mitnick.rxjava.bean.Token;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Michael Smith on 2016/7/21.
 */

public interface ServiceApi {
    public static final String baseurl = "https://witmj.azurewebsites.net/api/";

    @POST("login")
    Observable<Token> login(@Header("Authorization") String auth);

    @POST("refresh/")
    Call<Token> refresh(@Body RefreshRequest refreshRequest);

    @GET("users/profile/")
    Observable<Profile> getProfile(@Header("X-ZUMO-AUTH") String accessToken);

    @GET("users/profile/")
    Call<Profile> getProfiles(@Header("X-ZUMO-AUTH") String accessToken);

    
}

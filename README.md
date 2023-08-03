## Retrofit2RxJava-Android-Simples
Retrofit2 + Rxjava +Cache 机制+EventBus，新增Token失效处理方案，mobile network 情况下缓存一分钟过期重新请求，wifi 情况下不使用缓存，none network 情况下直接读取缓存并离线缓存4周。

![](screenshot/2016-08-09-14-24-52.png)   ![](screenshot/2016-08-09-14-26-52.png)


### RxJava生命周期管理

```
@Override
protected void onResume() {
    super.onResume();
    RxJavaManager.getRxInstance().regist(this);
}

@Override
protected void onPause() {
    super.onPause();
    RxJavaManager.getRxInstance().unregist(this);
}
```



### 网络缓存机制

      private final static Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            //获取网络状态
            int netWorkState = NetUtils.getNetworkState(RxApplication.getInstance());
            //无网络，请求强制使用缓存
            if (netWorkState == NetUtils.NETWORN_NONE) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
    
            Response originalResponse = chain.proceed(request);
    
            switch (netWorkState) {
                case NetUtils.NETWORN_MOBILE://mobile network 情况下缓存一分钟
                    int maxAge = 60;
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
    
                case NetUtils.NETWORN_WIFI://wifi network 情况下不使用缓存
                    maxAge = 0;
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
    
                case NetUtils.NETWORN_NONE://none network 情况下离线缓存4周
                    int maxStale = 60 * 60 * 24 * 4 * 7;
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .build();
                default:
                    throw new IllegalStateException("network state  is Erro!");
            }
        }
    };

### Token过期处理机制

    public class TokenAuthenticator implements Authenticator {
      @Override
      public Request authenticate(Route route, Response response) throws IOException {
          //取出本地的refreshToken
          String refreshToken = PreferenceUtils.getPrefString(RxApplication.getInstance(),"refreshToken","");
          RefreshRequest refreshRequest = new RefreshRequest(refreshToken);
          // 通过一个特定的接口获取新的token，此处要用到同步的retrofit请求
          ServiceApi service = ServiceFactory.createRetrofit2(ServiceApi.class);
          Call<Token> call = service.refresh(refreshRequest);
    
         //要用retrofit的同步方式
          Token token = call.execute().body();
          PreferenceUtils.setPrefString(RxApplication.getInstance(),"refreshToken",token.getRefresh_token());
    
          return response.request().newBuilder()
                  .header("X-ZUMO-AUTH", token.getAccess_token())
                  .build();
       }
    }



### Android Studio中ButterKnife插件的安装与使用


http://blog.csdn.net/cxc19890214/article/details/47430547


## 推荐书籍

Think in java

Effective java

Clean code


## 参考

https://github.com/cheng2016/RxJava-Android-Samples

https://github.com/cheng2016/RxJava

[RxJava2的相关操作符，多个请求FlatMap案例](https://blog.csdn.net/dongxianfei/article/details/78541698)

    api.register(new RegisterRequest())//发起注册请求
                    .subscribeOn(Schedulers.io())//在IO线程进行网络请求
                    .observeOn(AndroidSchedulers.mainThread())//回到主线程去处理请求注册结果
                    .doOnNext(new Consumer<RegisterResponse>() {
                        @Override
                        public void accept(RegisterResponse registerResponse) throws Exception {
                            //先根据注册的响应结果去做一些操作
                        }
                    })
                    .observeOn(Schedulers.io())//回到IO线程去发起登录请求
                    .flatMap(new Function<RegisterResponse, ObservableSource<LoginResponse>>() {
                        @Override
                        public ObservableSource<LoginResponse> apply(RegisterResponse registerResponse) throws Exception {
                            return api.login(new LoginRequest());
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())//回到主线程去处理请求登录的结果
                    .subscribe(new Consumer<LoginResponse>() {
                        @Override
                        public void accept(LoginResponse loginResponse) throws Exception {
                            Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                        }
                    });
 

## Contact Me

- Github: github.com/cheng2016
- Email: mitnick.cheng@outlook.com
- QQ: 1102743539
- [CSDN: souls0808](https://blog.csdn.net/chengzhenjia?type=blog)


# License

    Copyright 2016 cheng2016,Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


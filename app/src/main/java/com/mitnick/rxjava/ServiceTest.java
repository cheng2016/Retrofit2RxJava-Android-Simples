package com.mitnick.rxjava;


import com.mitnick.rxjava.http.Http;
import com.mitnick.rxjava.http.HttpFactory;
import com.mitnick.rxjava.http.RxJavaManager;
import com.mitnick.rxjava.http.bean.Profile;
import com.mitnick.rxjava.http.bean.RefreshRequest;
import com.mitnick.rxjava.http.bean.Token;
import java.util.Stack;
import rx.Observer;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Michael Smith on 2016/7/21.
 */

public class ServiceTest {
    public static void main(String[] args) throws Exception {
        Stack<String> stack = new Stack<>();
        stack.push("1");
        stack.push("2");
        stack.push("3");
        System.out.println(stack);

        System.out.println(stack.firstElement());
        System.out.println(stack.lastElement());
        System.out.println(stack.pop());


        Profile profile = new Profile();
        Token token = new Token();

        RxJavaManager.getRxInstance().regist(profile);

        RxJavaManager.getRxInstance().regist(new Profile());

        RxJavaManager.getRxInstance().unregist(profile);

        RxJavaManager.getRxInstance().regist(token);

        RxJavaManager.getRxInstance().unregist(token);

        RxJavaManager.getRxInstance().regist(new RefreshRequest());

        RxJavaManager.getRxInstance().regist(new Token());


        System.out.println(RxJavaManager.getRxInstance().getMap());
        System.out.println(RxJavaManager.getRxInstance().getCompositeSubscription());


        String auth = "Basic dG1qMDAxOjEyMzQ1Ng==";

        CompositeSubscription mSubscriptions = new CompositeSubscription();

        Http mApiClient = HttpFactory.createSimpleRetrofit2Service(Http.class);
        mSubscriptions.add(mApiClient.login(auth)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(Schedulers.io())
                .subscribe(new Observer<Token>() {
                               @Override
                               public void onCompleted() {
                                   System.out.print("onCompleted");
                               }

                               @Override
                               public void onError(Throwable e) {
                                   System.out.print("onError");
                               }

                               @Override
                               public void onNext(Token token) {
                                   System.out.print("onNext");
                               }
                           })
        );

    }
}


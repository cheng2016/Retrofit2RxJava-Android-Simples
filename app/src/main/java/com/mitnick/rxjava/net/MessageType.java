package com.mitnick.rxjava.net;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitnick.cheng on 2016/7/29.
 */

public final class MessageType{
    public final static int LOGIN = 0;
    public final static int REFRESH = 1;
    public final static int PROFILE = 2;

    //组合模式处理请求网络错误，然而很多个请求的情况下存在效率问题，思考！
    public static void handlerNetWorkException(Context context, Object event){
        if(event instanceof FailedEvent){
            int type = ((FailedEvent) event).getType();
            String message = ((FailedEvent) event).getObject()!=null ?
                    (((Throwable) ((FailedEvent) event).getObject()).getMessage().indexOf("504")!=-1 ? "请检查网络设置...":((Throwable) ((FailedEvent) event).getObject()).getMessage() )
                    : "";
            switch (type){
                case MessageType.LOGIN:
                    Toast.makeText(context,"登录失败！" + message,Toast.LENGTH_SHORT).show();
                    break;
                case MessageType.PROFILE:
                    Toast.makeText(context, "获取用户信息失败！" + message, Toast.LENGTH_SHORT).show();
                    break;
                case MessageType.REFRESH:
                    Toast.makeText(context, "刷新数据失败！" + message, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(context,"应用程序异常！" + message,Toast.LENGTH_SHORT).show();
            }
        }
    }
}

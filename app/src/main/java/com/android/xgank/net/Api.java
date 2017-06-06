package com.android.xgank.net;

import com.android.mvp.net.XApi;

/**
 * Created by yury on 2017/6/5.
 */

public class Api {

    public static String API_BASE_URL = "http://gank.io/api/";

    public static GankService service;

    public static GankService getGankService(){
        if (service == null) {
            synchronized (Api.class) {
                if (service == null) {
                    service = XApi.getInstance().getRetrofit(API_BASE_URL,true).create(GankService.class);
                }
            }
        }
        return service;
    }
}

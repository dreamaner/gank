package com.android.xgank.ui;

import android.app.Application;
import android.content.Context;

import com.android.mvp.APP;
import com.android.mvp.net.NetError;
import com.android.mvp.net.NetProvider;
import com.android.mvp.net.RequestHandler;
import com.android.mvp.net.XApi;
import com.android.xgank.config.ConfigManage;
import com.android.xgank.R;
import com.android.xgank.config.ThemeManage;

import org.litepal.LitePal;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;


/**
 * Created by yury on 2017/6/5.
 */

public class App extends Application {

    private static Context context;

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initXApi();
        APP.parmConfig(true,true,"x-mvp","","---");
        // 初始化主题色
        ThemeManage.INSTANCE.initColorPrimary(getResources().getColor(R.color.colorPrimary));
        ConfigManage.INSTANCE.initConfig(this);
        LitePal.initialize(this);
    }

    public static Context getContext(){
        return context;
    }

    public static App getInstance(){

        if (instance == null){

            instance = new App();

        }

        return instance;
    }

    public void initXApi(){

        XApi.registerProvider(new NetProvider() {

            @Override
            public Interceptor[] configInterceptors() {
                return new Interceptor[0];
            }

            @Override
            public void configHttps(OkHttpClient.Builder builder) {
//                builder.addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        Request request = chain.request().newBuilder()
//                                .addHeader("productkey","1442374334440558399")
//                                .build();
//                        return chain.proceed(request);
//                    }
//                });
            }

            @Override
            public CookieJar configCookie() {
                return null;
            }

            @Override
            public RequestHandler configHandler() {
                return null;
            }

            @Override
            public long configConnectTimeoutMills() {
                return 0;
            }

            @Override
            public long configReadTimeoutMills() {
                return 0;
            }

            @Override
            public boolean configLogEnable() {
                return true;
            }

            @Override
            public boolean handleError(NetError error) {
                return false;
            }
        });
    }
}

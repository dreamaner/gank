package com.android.xgank.ui;

import android.app.Application;
import android.content.Context;


import com.android.kit.utils.system.AppUtils;
import com.android.mvp.AppInit;
import com.android.mvp.net.NetError;
import com.android.mvp.net.NetProvider;
import com.android.mvp.net.RequestHandler;
import com.android.mvp.net.XApi;
import com.android.xgank.config.ConfigManage;
import com.android.xgank.R;
import com.android.xgank.config.ThemeManage;
import com.facebook.stetho.Stetho;


import org.litepal.LitePal;
import org.litepal.LitePalApplication;

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
        AppUtils.init(context);
        AppInit.parmConfig(true,true,"x-mvp","","---");
        // 初始化主题色
        ThemeManage.INSTANCE.initColorPrimary(getResources().getColor(R.color.colorPrimary));
        ConfigManage.INSTANCE.initConfig(this);
        LitePal.initialize(this);
        Stetho.initializeWithDefaults(this);
        //DebugDB.getAddressLog();
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
//                builder.addNetworkInterceptor(new StethoInterceptor());
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

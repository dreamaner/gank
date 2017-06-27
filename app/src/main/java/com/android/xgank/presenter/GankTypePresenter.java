package com.android.xgank.presenter;

import com.android.mvp.mvp.XPresent;
import com.android.mvp.net.ApiSubscriber;
import com.android.mvp.net.NetError;
import com.android.mvp.net.XApi;
import com.android.xgank.bean.GankResults;
import com.android.xgank.net.Api;
import com.android.xgank.ui.activitys.GankTypeListActivity;

/**
 * Created by yury on 2017/6/8.
 */

public class GankTypePresenter extends XPresent<GankTypeListActivity> {
    private static final int PAGE_SIZE =10 ;

    public void loadData(String type, final int page) {
            Api.getGankService().getGankData(type, PAGE_SIZE, page)
                    .compose(XApi.<GankResults>getApiTransformer())
                    .compose(XApi.<GankResults>getScheduler())
                    .compose(getV().<GankResults>bindToLifecycle())
                    .subscribe(new ApiSubscriber<GankResults>() {
                        @Override
                        protected void onFail(NetError error) {
                             getV().showError(error);
                        }

                        @Override
                        public void onNext(GankResults gankResults) {
                            getV().showView(page, gankResults);
                        }
                    });
        }

}

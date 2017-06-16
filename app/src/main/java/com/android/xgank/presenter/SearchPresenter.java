package com.android.xgank.presenter;

import com.android.kit.utils.system.DateUtils;
import com.android.kit.utils.system.KeyguardLockUtils;
import com.android.mvp.mvp.XPresent;
import com.android.mvp.net.ApiSubscriber;
import com.android.mvp.net.NetError;
import com.android.mvp.net.XApi;
import com.android.xgank.bean.History;
import com.android.xgank.bean.SearchResult;
import com.android.xgank.net.Api;
import com.android.xgank.ui.activitys.SearchActivity;

import org.litepal.crud.DataSupport;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.internal.schedulers.ScheduledRunnable;

/**
 * Created by 王小铭Style on 2017/6/13.
 */

public class SearchPresenter extends XPresent<SearchActivity> {
    protected static final int PAGE_SIZE = 10;
    public boolean isSaveHistory;
    public History history;

    public void loadData(String key,int page){

        getV().setContentLayoutVisible();
        getV().setLlSearchHistoryInVisible();
        Api.getGankService().getSearchResult(key,PAGE_SIZE,page)
                .compose(XApi.<SearchResult>getApiTransformer())
                .compose(XApi.<SearchResult>getScheduler())
                .compose(getV().<SearchResult>bindToLifecycle())
                .subscribe(new ApiSubscriber<SearchResult>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(SearchResult searchResult) {
                        getV().showSearchData(searchResult.getResults(),page);
                    }
                });
    }


    public void saveHistory(String content){
        // 不知道 LitePal 支不支持去重
        // 先这样写吧，新增之前查询是否有相同数据，有就更新 CreateAt ，没有就直接新增
        List<History> historyList = DataSupport.where("content = ?", content).find(History.class);
        if (historyList == null || historyList.size() < 1) { // 不存在
            history = new History();
            history.setCreateAt(DateUtils.getYmdhms(System.currentTimeMillis()));
            history.setContent(content);
            history.save();
        } else {
            // 更新
            History updateNews = new History();
            history.setCreateAt(DateUtils.getYmdhms(System.currentTimeMillis()));
            updateNews.updateAll("content = ?", content);
        }

    }
    public void queryHistory(){

         getV().setContentLayoutInVisible();
         getV().setLlSearchHistoryVisible();
         getV().setIvEditClearInVisible();
         List<History> list =  DataSupport.findAll(History.class);
         getV().showHistoryData(list);
    }
    public void deleteHistory(){
        DataSupport.deleteAll(History.class);
        queryHistory();
    }
}

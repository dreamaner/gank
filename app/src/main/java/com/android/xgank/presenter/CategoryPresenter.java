package com.android.xgank.presenter;

import android.widget.LinearLayout;

import com.android.mvp.log.XLog;
import com.android.mvp.mvp.XPresent;
import com.android.mvp.net.ApiSubscriber;
import com.android.mvp.net.NetError;
import com.android.mvp.net.XApi;
import com.android.xgank.bean.Constant;
import com.android.xgank.bean.Favorite;
import com.android.xgank.bean.GankResults;
import com.android.xgank.net.Api;
import com.android.xgank.ui.activitys.FavActivity;
import com.android.xgank.ui.fragments.CategoryFragment;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by yury on 2017/6/15.
 */

public class CategoryPresenter extends XPresent<CategoryFragment> {
    private List<Favorite> list;
    public void loadData(String type,int page) {

        if (page == 1){

             list = DataSupport.where(" type = ?", type).limit(page*5).find(Favorite.class);
        }else{
             list = DataSupport.where(" type = ?", type).limit(5).offset((page-1)*5).find(Favorite.class);
        }
           getV().showData(list,page);
    }
}

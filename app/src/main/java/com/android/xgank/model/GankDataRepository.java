package com.android.xgank.model;

import android.content.ContentValues;
import android.content.Context;

import com.android.mvp.cache.PrefsUtils;
import com.android.xgank.R;
import com.android.xgank.bean.Constant;
import com.android.xgank.bean.MoreEntity;
import com.android.xgank.config.ConfigManage;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yury on 2017/6/7.
 */

public class GankDataRepository {

    public static GankDataRepository INSTANCE;

    public static synchronized GankDataRepository getInstance() {
        if (INSTANCE == null){
            INSTANCE = new GankDataRepository();
        }
        return INSTANCE;
    }
    public void initMoreData(){

        if (getMoreData().size() == 0){
            (new MoreEntity(Constant.ANDROID, R.drawable.android)).save();
            (new MoreEntity(Constant.IOS,R.drawable.ios)).save();
            (new MoreEntity(Constant.WEB,R.drawable.web)).save();
            (new MoreEntity(Constant.PHOTO,R.drawable.phot)).save();
            (new MoreEntity(Constant.EXPANDRES,R.drawable.expended)).save();
            (new MoreEntity(Constant.RECOMMEND,R.drawable.recommend)).save();
            (new MoreEntity(Constant.APP,R.drawable.app)).save();
            (new MoreEntity(Constant.VIDEO,R.drawable.video)).save();
        }
    }

    public void upMoreData(List<MoreEntity> list){

        DataSupport.deleteAll(MoreEntity.class);

        for (MoreEntity moreEntity : list) {
            moreEntity.save();
        }
    }

    public List<MoreEntity> getMoreData() {

        List<MoreEntity> moreData = DataSupport.limit(8).find(MoreEntity.class);

        return moreData;
    }

    public List addData(){
        List<MoreEntity> list=new ArrayList<>();
        list.add(new MoreEntity(Constant.ANDROID,R.drawable.android));
        list.add(new MoreEntity(Constant.IOS,R.drawable.ios));
        list.add(new MoreEntity(Constant.WEB,R.drawable.web));
        list.add(new MoreEntity(Constant.PHOTO,R.drawable.phot));
        list.add(new MoreEntity(Constant.EXPANDRES,R.drawable.expended));
        list.add(new MoreEntity(Constant.RECOMMEND,R.drawable.recommend));
        list.add(new MoreEntity(Constant.APP,R.drawable.app));
        list.add(new MoreEntity(Constant.VIDEO,R.drawable.video));
        return list;
    }
}

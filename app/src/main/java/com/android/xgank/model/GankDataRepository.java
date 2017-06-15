package com.android.xgank.model;

import android.content.ContentValues;
import android.content.Context;

import com.android.kit.utils.operate.RandomUtils;
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

            (new MoreEntity(Constant.ANDROID, String.valueOf(R.drawable.android_1))).save();
            (new MoreEntity(Constant.IOS,String.valueOf(R.drawable.ios))).save();
            (new MoreEntity(Constant.WEB,String.valueOf(R.drawable.web))).save();
            (new MoreEntity(Constant.PHOTO,String.valueOf(R.drawable.phot))).save();
            (new MoreEntity(Constant.EXPANDRES,String.valueOf(R.drawable.expended))).save();
            (new MoreEntity(Constant.RECOMMEND,String.valueOf(R.drawable.recommend))).save();
            (new MoreEntity(Constant.APP,String.valueOf(R.drawable.app))).save();
            (new MoreEntity(Constant.VIDEO,String.valueOf(R.drawable.video))).save();
        }
    }

    public void upMoreData(List<MoreEntity> list){

        DataSupport.deleteAll(MoreEntity.class);

        for (MoreEntity moreEntity : list) {
            moreEntity.clearSavedState();
            moreEntity.save();
        }
    }

    public List<MoreEntity> getMoreData() {

        List<MoreEntity> moreData = DataSupport.findAll(MoreEntity.class);

        return moreData;
    }

}

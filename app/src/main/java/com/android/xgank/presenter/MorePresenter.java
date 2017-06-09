package com.android.xgank.presenter;

import android.content.Context;

import com.android.mvp.mvp.XPresent;
import com.android.xgank.bean.MoreEntity;
import com.android.xgank.model.GankDataRepository;
import com.android.xgank.ui.fragments.MoreFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yury on 2017/6/6.
 */

public class MorePresenter extends XPresent<MoreFragment> {

    public void updateMoreData( List<MoreEntity> moreEntity){
        GankDataRepository.getInstance().upMoreData(moreEntity);
    }

    public void getMoreData(){
        GankDataRepository.getInstance().initMoreData();
        List<MoreEntity> list = GankDataRepository.getInstance().getMoreData();
        getV().setUpMoreData(list);
    }
}

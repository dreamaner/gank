package com.android.xgank.presenter;

import com.android.mvp.log.XLog;
import com.android.mvp.mvp.XPresent;
import com.android.xgank.bean.Favorite;
import com.android.xgank.ui.activitys.PhotoActivity;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by 王小铭Style on 2017/6/17.
 */

public class PhotoPresenter extends XPresent<PhotoActivity>{
    private boolean mIsFavorite;
    private Favorite mFavoriteData;

    public void init(){

        mFavoriteData = getV().getFavorite();
        XLog.i("---gank_id---",mFavoriteData.getGank_id());
    }

    public void favoriteGank() {
        if (mIsFavorite) { // 已经收藏
            unFavorite();
        } else { // 未收藏
            favorite();
        }
    }
    public void unFavorite(){
        int result = DataSupport.deleteAll(Favorite.class, "gank_id = ?", mFavoriteData.getGank_id());
        mFavoriteData.clearSavedState();
        mIsFavorite = result < 1;
        if (!mIsFavorite)
            getV().showTips("取消收藏");
        else
            getV().showTips("取消收藏失败");
        getV().setFavoriteState(mIsFavorite);

    }

    public void favorite(){

        mIsFavorite =  mFavoriteData.save();

        if (mIsFavorite)
            getV().showTips("收藏成功");
        else
            getV().showTips("收藏失败");
        getV().setFavoriteState(mIsFavorite);

    }

    public void showFavState(){
        if (mFavoriteData == null) {
            // 隐藏收藏 fab
            getV().hideFavoriteFab();
            return;
        }
        List<Favorite> favorites = DataSupport.where("gank_id = ?", mFavoriteData.getGank_id()).find(Favorite.class);
        mIsFavorite = favorites.size() > 0;
        getV().setFavoriteState(mIsFavorite);
        XLog.i("---gank_id---",mIsFavorite+"");


    }
}

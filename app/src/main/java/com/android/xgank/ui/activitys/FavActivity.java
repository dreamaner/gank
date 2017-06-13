package com.android.xgank.ui.activitys;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;

import com.android.kit.view.widget.MyToolbar;
import com.android.mvp.mvp.XActivity;
import com.android.mvp.recycleview.XRecyclerContentLayout;
import com.android.xgank.R;
import com.android.xgank.presenter.FavPresenter;

import butterknife.BindView;


public class FavActivity extends XActivity<FavPresenter> {

    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.recyclerview)
    XRecyclerContentLayout xrecyclerview;



    @Override
    public void initData(Bundle savedInstanceState) {
        setUpToolBar(true,toolbar,"我的收藏");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_fav;
    }

    @Override
    public FavPresenter newP() {
        return new FavPresenter();
    }

    @Override
    public boolean canBack() {
        return true;
    }


}

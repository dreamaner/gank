package com.android.xgank.ui.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.mvp.mvp.XActivity;
import com.android.xgank.R;
import com.android.xgank.presenter.SearchPresenter;

public class SearchActivity extends XActivity<SearchPresenter> {

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public SearchPresenter newP() {
        return new SearchPresenter();
    }

    @Override
    public boolean canBack() {
        return true;
    }
}

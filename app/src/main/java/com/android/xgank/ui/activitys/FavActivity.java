package com.android.xgank.ui.activitys;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;

import com.android.kit.view.tab.XTabLayout;
import com.android.kit.view.widget.MyToolbar;
import com.android.mvp.mvp.XActivity;
import com.android.xgank.R;
import com.android.xgank.bean.Constant;
import com.android.xgank.presenter.FavPresenter;
import com.android.xgank.ui.adapters.CategoryViewPagerAdapter;
import com.android.xgank.ui.fragments.CategoryFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FavActivity extends XActivity<FavPresenter> {

    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.tab)
    XTabLayout tab;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    public CategoryViewPagerAdapter pagerAdapter;
    public String[] titles = {
            Constant.ANDROID,
            Constant.IOS,
            Constant.WEB,
            Constant.EXPANDRES,
            Constant.PHOTO,
            Constant.VIDEO,
            Constant.APP,
            Constant.RECOMMEND
    };
    CategoryFragment androidFragment;
    CategoryFragment iosFragment;
    CategoryFragment webFragment;
    CategoryFragment expandresFragment;
    CategoryFragment photoFragment;
    CategoryFragment videoFragment;
    CategoryFragment appFragment;
    CategoryFragment recommendFragment;

    public void initAdapter(){
        pagerAdapter = new CategoryViewPagerAdapter(getSupportFragmentManager(),titles);

        androidFragment = CategoryFragment.newInstance(titles[0]);
        iosFragment = CategoryFragment.newInstance(titles[1]);
        webFragment = CategoryFragment.newInstance(titles[2]);
        expandresFragment = CategoryFragment.newInstance(titles[3]);
        photoFragment = CategoryFragment.newInstance(titles[4]);
        videoFragment = CategoryFragment.newInstance(titles[5]);
        appFragment = CategoryFragment.newInstance(titles[6]);
        recommendFragment = CategoryFragment.newInstance(titles[7]);

        pagerAdapter.addFragment(androidFragment);
        pagerAdapter.addFragment(iosFragment);
        pagerAdapter.addFragment(webFragment);
        pagerAdapter.addFragment(expandresFragment);
        pagerAdapter.addFragment(photoFragment);
        pagerAdapter.addFragment(videoFragment);
        pagerAdapter.addFragment(appFragment);
        pagerAdapter.addFragment(recommendFragment);

        viewPager.setAdapter(pagerAdapter);
        tab.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);

    }
    @Override
    public void initData(Bundle savedInstanceState) {
        setUpToolBar(true, toolbar, "我的收藏");
        initAdapter();
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

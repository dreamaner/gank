package com.android.xgank.ui.activitys;
import butterknife.BindView;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;

import com.android.kit.view.tab.XTabLayout;
import com.android.kit.view.widget.MyToolbar;
import com.android.mvp.mvp.XActivity;
import com.android.xgank.R;
import com.android.xgank.bean.Constant;
import com.android.xgank.config.ConfigManage;
import com.android.xgank.presenter.FavPresenter;
import com.android.xgank.ui.adapters.CategoryViewPagerAdapter;
import com.android.xgank.ui.fragments.CategoryFragment;

public class FavActivity extends XActivity<FavPresenter> {

    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    @BindView(R.id.tab)
    XTabLayout tab;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    public CategoryViewPagerAdapter pagerAdapter;
    public String[] titles = {
            Constant.ANDROID,
            Constant.IOS,
            Constant.PHOTO,
            Constant.VIDEO,
            Constant.WEB,
            Constant.EXPANDRES,
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
        webFragment = CategoryFragment.newInstance(titles[4]);
        expandresFragment = CategoryFragment.newInstance(titles[5]);
        photoFragment = CategoryFragment.newInstance(titles[2]);
        videoFragment = CategoryFragment.newInstance(titles[3]);
        appFragment = CategoryFragment.newInstance(titles[6]);
        recommendFragment = CategoryFragment.newInstance(titles[7]);

        pagerAdapter.addFragment(androidFragment);
        pagerAdapter.addFragment(iosFragment);
        pagerAdapter.addFragment(photoFragment);
        pagerAdapter.addFragment(videoFragment);
        pagerAdapter.addFragment(webFragment);
        pagerAdapter.addFragment(expandresFragment);
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

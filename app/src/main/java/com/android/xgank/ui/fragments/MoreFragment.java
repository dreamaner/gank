package com.android.xgank.ui.fragments;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.kit.view.immersion.ImmersionBar;
import com.android.kit.view.widget.MyToolbar;
import com.android.mvp.mvp.XLazyFragment;
import com.android.mvp.recycleview.XRecyclerView;
import com.android.xgank.R;
import com.android.xgank.presenter.MorePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MoreFragment extends XLazyFragment<MorePresenter> {


    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.more)
    XRecyclerView more;
    Unbinder unbinder;

    public static MoreFragment getInstance() {
        MoreFragment moreFragment = new MoreFragment();

        return moreFragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ImmersionBar.with(getActivity())
                .statusBarDarkFont(false)
                .navigationBarColor(R.color.colorPrimary)
                .init();
        setUpToolBar(true,toolbar,"更多");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_more;
    }

    @Override
    public MorePresenter newP() {
        return new MorePresenter();
    }

    @Override
    public boolean canBack() {
        return false;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

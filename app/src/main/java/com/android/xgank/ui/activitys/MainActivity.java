package com.android.xgank.ui.activitys;

import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.android.kit.view.widget.MyBottomNavigationView;
import com.android.mvp.mvp.XActivity;
import com.android.xgank.R;
import com.android.xgank.bean.Constant;
import com.android.xgank.kit.FragmentUtils;
import com.android.xgank.listener.HomeFrgListener;

import butterknife.BindView;


public class MainActivity extends XActivity implements BottomNavigationView.OnNavigationItemSelectedListener,HomeFrgListener {

    @BindView(R.id.navigation)
    MyBottomNavigationView navigation;

    @Override
    public boolean canBack() {
        return false;
    }


    @Override
    public void initData(Bundle savedInstanceState) {
         navigation.setOnNavigationItemSelectedListener(this);
         FragmentUtils.getInstance(MainActivity.this).initFragment(Constant.HOME);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public Object newP() {
        return null;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navigation_home:
                FragmentUtils.getInstance(MainActivity.this).initFragment(Constant.HOME);
                return true;
            case R.id.navigation_more:
                FragmentUtils.getInstance(MainActivity.this).initFragment(Constant.MORE);
                return true;
            case R.id.navigation_user:
                FragmentUtils.getInstance(MainActivity.this).initFragment(Constant.ME);
                return true;
        }
        return false;
    }

    @Override
    public void hideToolbar() {

    }

    @Override
    public void showToolbar() {

    }

    @Override
    public void hideBottomBar() {
        if (!navigation.isHidden()){
            navigation.hide();
        }

    }

    @Override
    public void showBottomBar() {
        if (navigation.isHidden()){
            navigation.show();
        }
    }
}

package com.android.xgank.ui.activitys;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import android.widget.Toast;
import com.android.kit.utils.toast.ToastUtils;
import com.android.kit.view.dialog.color.CircleView;
import com.android.kit.view.dialog.color.ColorChooserDialog;
import com.android.kit.view.dialog.internal.ThemeSingleton;
import com.android.kit.view.dialog.util.DialogUtils;
import com.android.kit.view.widget.MyBottomNavigationView;
import com.android.mvp.event.BusProvider;
import com.android.mvp.event.IBus;
import com.android.mvp.event.RxBusImpl;
import com.android.mvp.mvp.XActivity;
import com.android.xgank.R;
import com.android.xgank.bean.Constant;
import com.android.xgank.config.ConfigManage;
import com.android.xgank.config.ThemeManage;
import com.android.xgank.kit.FragmentUtils;
import com.android.xgank.listener.HomeFrgListener;

import butterknife.BindView;
import com.android.xgank.ui.fragments.MoreFragment;
import com.android.xgank.ui.fragments.OwnFragment;

public class MainActivity extends XActivity implements BottomNavigationView.OnNavigationItemSelectedListener,HomeFrgListener,ColorChooserDialog.ColorCallback{

    @BindView(R.id.navigation)
    MyBottomNavigationView navigation;
    private FragmentUtils fragmentUtil;
    @Override
    public boolean canBack() {
        return false;
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
         fragmentUtil = new FragmentUtils(this);
         navigation.setOnNavigationItemSelectedListener(this);
         fragmentUtil.initFragment(Constant.HOME);
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
                fragmentUtil.initFragment(Constant.HOME);
                return true;
            case R.id.navigation_more:
                fragmentUtil.initFragment(Constant.MORE);
                return true;
            case R.id.navigation_user:
                fragmentUtil.initFragment(Constant.ME);
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

    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, @ColorInt int selectedColor) {
        ConfigManage.INSTANCE.setThemeColor(selectedColor);
        OwnFragment.getInstance().initBar();
        MoreFragment.getInstance().initBar();
    }

    @Override
    public void onColorChooserDismissed(@NonNull ColorChooserDialog dialog) {

    }
}

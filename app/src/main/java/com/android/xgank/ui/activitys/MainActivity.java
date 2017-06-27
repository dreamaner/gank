package com.android.xgank.ui.activitys;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.android.kit.utils.toast.SnackbarUtils;
import com.android.kit.utils.toast.ToastUtils;
import com.android.kit.view.dialog.color.ColorChooserDialog;
import com.android.kit.view.widget.MyBottomNavigationView;
import com.android.mvp.mvp.XActivity;
import com.android.xgank.R;
import com.android.xgank.bean.Constant;
import com.android.xgank.config.ThemeManage;
import com.android.xgank.kit.FragmentUtils;
import com.android.xgank.listener.HomeFrgListener;

public class MainActivity extends XActivity
    implements BottomNavigationView.OnNavigationItemSelectedListener, HomeFrgListener,
    ColorChooserDialog.ColorCallback {

    @BindView(R.id.navigation)
    MyBottomNavigationView navigation;
    @BindView(R.id.fragment_content)
    FrameLayout fragmentContent;
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

    private long firstTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {                                         //如果两次按键时间间隔大于2秒，则不退出
                    ToastUtils.showLongToast(Constant.EXIT_SYSTEM);
                    firstTime = secondTime;//更新firstTime
                    return true;
                } else {                                                    //两次按键小于2秒时，退出应用
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
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
        if (!navigation.isHidden()) {
            navigation.hide();
        }
    }

    @Override
    public void showBottomBar() {
        if (navigation.isHidden()) {
            navigation.show();
        }
    }

    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, @ColorInt int selectedColor) {
        ThemeManage.INSTANCE.setColorPrimary(selectedColor);
    }

    @Override
    public void onColorChooserDismissed(@NonNull ColorChooserDialog dialog) {

    }

}

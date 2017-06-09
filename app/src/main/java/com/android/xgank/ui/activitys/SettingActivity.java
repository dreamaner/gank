package com.android.xgank.ui.activitys;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.kit.view.widget.MyToolbar;
import com.android.mvp.mvp.XActivity;
import com.android.xgank.R;
import com.android.xgank.presenter.SettingPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends XActivity<SettingPresenter> {


    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.appCompatTextView)
    AppCompatTextView appCompatTextView;
    @BindView(R.id.switch_setting)
    SwitchCompat switchSetting;
    @BindView(R.id.ll_is_show_list_img)
    LinearLayout llIsShowListImg;
    @BindView(R.id.tv_setting_image_quality_title)
    AppCompatTextView tvSettingImageQualityTitle;
    @BindView(R.id.tv_setting_image_quality_tip)
    AppCompatTextView tvSettingImageQualityTip;
    @BindView(R.id.tv_setting_image_quality_content)
    AppCompatTextView tvSettingImageQualityContent;
    @BindView(R.id.ll_setting_image_quality)
    LinearLayout llSettingImageQuality;
    @BindView(R.id.tv_is_show_launcher_img_content)
    AppCompatTextView tvIsShowLauncherImgContent;
    @BindView(R.id.switch_setting_show_launcher_img)
    SwitchCompat switchSettingShowLauncherImg;
    @BindView(R.id.ll_is_show_launcher_img)
    LinearLayout llIsShowLauncherImg;
    @BindView(R.id.tv_is_always_show_launcher_img_title)
    AppCompatTextView tvIsAlwaysShowLauncherImgTitle;
    @BindView(R.id.tv_is_always_show_launcher_img_content)
    AppCompatTextView tvIsAlwaysShowLauncherImgContent;
    @BindView(R.id.switch_setting_always_show_launcher_img)
    SwitchCompat switchSettingAlwaysShowLauncherImg;
    @BindView(R.id.ll_is_always_show_launcher_img)
    LinearLayout llIsAlwaysShowLauncherImg;
    @BindView(R.id.setting_cacheSize_ll)
    TextView settingCacheSizeLl;
    @BindView(R.id.setting_clearCache_ll)
    LinearLayout settingClearCacheLl;
    @BindView(R.id.setting_version_ll)
    TextView settingVersionLl;
    @BindView(R.id.setting_checkUpdate_ll)
    LinearLayout settingCheckUpdateLl;
    @BindView(R.id.setting_about_ll)
    LinearLayout settingAboutLl;
    @BindView(R.id.setting_logout_ll)
    LinearLayout settingLogoutLl;
    @BindView(R.id.header_view)
    View headerView;

    @Override
    public void initData(Bundle savedInstanceState) {
           setUpToolBar(true,toolbar,"设置");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public SettingPresenter newP() {
        return null;
    }

    @Override
    public boolean canBack() {
        return true;
    }


    @OnClick({R.id.switch_setting, R.id.ll_is_show_list_img, R.id.tv_setting_image_quality_title, R.id.tv_setting_image_quality_tip, R.id.tv_setting_image_quality_content, R.id.ll_setting_image_quality, R.id.tv_is_show_launcher_img_content, R.id.switch_setting_show_launcher_img, R.id.ll_is_show_launcher_img, R.id.tv_is_always_show_launcher_img_title, R.id.tv_is_always_show_launcher_img_content, R.id.switch_setting_always_show_launcher_img, R.id.ll_is_always_show_launcher_img, R.id.setting_cacheSize_ll, R.id.setting_clearCache_ll, R.id.setting_version_ll, R.id.setting_checkUpdate_ll, R.id.setting_about_ll, R.id.setting_logout_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.switch_setting:
                break;
            case R.id.ll_is_show_list_img:
                break;
            case R.id.tv_setting_image_quality_title:
                break;
            case R.id.tv_setting_image_quality_tip:
                break;
            case R.id.tv_setting_image_quality_content:
                break;
            case R.id.ll_setting_image_quality:
                break;
            case R.id.tv_is_show_launcher_img_content:
                break;
            case R.id.switch_setting_show_launcher_img:
                break;
            case R.id.ll_is_show_launcher_img:
                break;
            case R.id.tv_is_always_show_launcher_img_title:
                break;
            case R.id.tv_is_always_show_launcher_img_content:
                break;
            case R.id.switch_setting_always_show_launcher_img:
                break;
            case R.id.ll_is_always_show_launcher_img:
                break;
            case R.id.setting_cacheSize_ll:
                break;
            case R.id.setting_clearCache_ll:
                break;
            case R.id.setting_version_ll:
                break;
            case R.id.setting_checkUpdate_ll:
                break;
            case R.id.setting_about_ll:
                break;
            case R.id.setting_logout_ll:
                break;
        }
    }
}

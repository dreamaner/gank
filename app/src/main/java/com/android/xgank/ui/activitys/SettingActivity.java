package com.android.xgank.ui.activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.android.kit.utils.toast.Toasty;
import com.android.kit.view.dialog.MaterialDialog;
import com.android.kit.view.widget.MyToolbar;
import com.android.mvp.mvp.XActivity;
import com.android.xgank.R;
import com.android.xgank.config.ConfigManage;
import com.android.xgank.kit.AlipayZeroSdk;
import com.android.xgank.kit.MDTintUtil;
import com.android.xgank.presenter.SettingPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SettingActivity extends XActivity<SettingPresenter> implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
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
    @BindView(R.id.ll_setting_about)
    LinearLayout llSettingAbout;
    @BindView(R.id.tv_setting_version_name)
    AppCompatTextView tvSettingVersionName;
    @BindView(R.id.ll_setting_update)
    LinearLayout llSettingUpdate;
    @BindView(R.id.tv_setting_clean_cache)
    AppCompatTextView tvSettingCleanCache;
    @BindView(R.id.ll_setting_clean_cache)
    LinearLayout llSettingCleanCache;
    @BindView(R.id.ll_setting_pay)
    LinearLayout llSettingPay;
    @BindView(R.id.ll_setting_issues)
    LinearLayout llSettingIssues;
    @BindView(R.id.setting_logout_ll)
    LinearLayout settingLogoutLl;
    @BindView(R.id.header_view)
    View headerView;

    @Override
    public void initData(Bundle savedInstanceState) {
        setUpToolBar(true, toolbar, "设置");
        switchSetting.setOnCheckedChangeListener(this);
        switchSettingShowLauncherImg.setOnCheckedChangeListener(this);
        switchSettingAlwaysShowLauncherImg.setOnCheckedChangeListener(this);

        try {
            getP().init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //toolbar.setBackgroundColor(ConfigManage.INSTANCE.getThemeColor());
    }

    @Override
    public void onBackPressed() {
        if (getP().isThumbnailSettingChanged()) { // 显示缩略图设置项改变
            setResult(RESULT_OK);
        }
        super.onBackPressed();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public SettingPresenter newP() {
        return new SettingPresenter();
    }

    @Override
    public boolean canBack() {
        return true;
    }


    @OnClick({
            R.id.ll_is_show_list_img,
            R.id.ll_is_show_launcher_img,
            R.id.ll_is_always_show_launcher_img,})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ll_is_show_list_img:
                switchSetting.setChecked(!switchSetting.isChecked());
                break;

            case R.id.ll_is_show_launcher_img:
                switchSettingShowLauncherImg.setChecked(!switchSettingShowLauncherImg.isChecked());
                break;


            case R.id.ll_is_always_show_launcher_img:
                switchSettingAlwaysShowLauncherImg.setChecked(!switchSettingAlwaysShowLauncherImg.isChecked());

                break;
        }
    }


    public void changeSwitchState(boolean isChecked) {
        switchSetting.setChecked(isChecked);
    }


    public void changeIsShowLauncherImgSwitchState(boolean isChecked) {
        switchSettingShowLauncherImg.setChecked(isChecked);
    }


    public void changeIsAlwaysShowLauncherImgSwitchState(boolean isChecked) {
        switchSettingAlwaysShowLauncherImg.setChecked(isChecked);
    }

    public void setSwitchCompatsColor(int color) {
        MDTintUtil.setTint(switchSetting, color);
        MDTintUtil.setTint(switchSettingShowLauncherImg, color);
        MDTintUtil.setTint(switchSettingAlwaysShowLauncherImg, color);
    }


    public void setAppVersionNameInTv(String versionName) {
        tvSettingVersionName.setText("版本: " + versionName);
    }


    public void setImageQualityChooseUnEnable() {
        switchSetting.setClickable(false);
        tvSettingImageQualityTip.setTextColor(getResources().getColor(R.color.colorTextUnEnable));
        tvSettingImageQualityTitle.setTextColor(getResources().getColor(R.color.colorTextUnEnable));
        tvSettingImageQualityContent.setTextColor(getResources().getColor(R.color.colorTextUnEnable));
    }

    public void setImageQualityChooseEnable() {
        switchSetting.setClickable(true);
        tvSettingImageQualityTip.setTextColor(getResources().getColor(R.color.colorTextEnable));
        tvSettingImageQualityTitle.setTextColor(getResources().getColor(R.color.colorTextEnableGary));
        tvSettingImageQualityContent.setTextColor(getResources().getColor(R.color.colorTextEnableGary));
    }


    public void setLauncherImgProbabilityUnEnable() {
        switchSettingShowLauncherImg.setClickable(false);
        switchSettingAlwaysShowLauncherImg.setClickable(false);
        tvIsAlwaysShowLauncherImgTitle.setTextColor(getResources().getColor(R.color.colorTextUnEnable));
        tvIsAlwaysShowLauncherImgContent.setTextColor(getResources().getColor(R.color.colorTextUnEnable));
    }

    public void setLauncherImgProbabilityEnable() {
        switchSettingShowLauncherImg.setClickable(true);
        switchSettingAlwaysShowLauncherImg.setClickable(true);
        tvIsAlwaysShowLauncherImgTitle.setTextColor(getResources().getColor(R.color.colorTextEnable));
        tvIsAlwaysShowLauncherImgContent.setTextColor(getResources().getColor(R.color.colorTextEnableGary));
    }

    public void setThumbnailQualityInfo(int quality) {
        String thumbnailQuality = "";
        switch (quality) {
            case 0:
                thumbnailQuality = "原图";
                break;
            case 1:
                thumbnailQuality = "默认";
                break;
            case 2:
                thumbnailQuality = "省流";
                break;
        }
        tvSettingImageQualityContent.setText(thumbnailQuality);
    }

    public void showCacheSize(String cache) {
        tvSettingCleanCache.setText(cache);
    }


    public void showSuccessTip(String msg) {
        Toasty.success(this, msg).show();
    }


    public void showFailTip(String msg) {
        Toasty.error(this, msg).show();
    }


    public void setShowLauncherTip(String tip) {
        tvIsAlwaysShowLauncherImgContent.setText(tip);
    }


    public void setAlwaysShowLauncherTip(String tip) {
        tvIsAlwaysShowLauncherImgContent.setText(tip);
    }

    @OnClick(R.id.ll_setting_image_quality)
    public void chooseThumbnailQuality() {
        new MaterialDialog.Builder(this)
                .title("缩略图质量")
                .items("原图", "默认", "省流")
                .widgetColor(getP().getColorPrimary())
                .alwaysCallSingleChoiceCallback()
                .itemsCallbackSingleChoice(getP().getThumbnailQuality(), new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        getP().setThumbnailQuality(which);
                        dialog.dismiss();
                        return true;
                    }
                })
                .positiveText("取消")
                .positiveColor(getP().getColorPrimary())
                .show();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.switch_setting:
                getP().saveIsListShowImg(isChecked);
                break;
            case R.id.switch_setting_show_launcher_img:
                getP().saveIsLauncherShowImg(isChecked);
                break;
            case R.id.switch_setting_always_show_launcher_img:
                getP().saveIsLauncherAlwaysShowImg(isChecked);
                break;
        }
    }
    @OnClick(R.id.ll_setting_about)
    public void about() {
//        new AboutDialog(this, getP().getColorPrimary()).show();
    }
    @OnClick(R.id.ll_setting_issues)
    public void issues(){
        Uri uri = Uri.parse("https://github.com/dreamaner/gank/issues");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    @OnClick(R.id.ll_setting_pay)
    public void pay(){
        // https://fama.alipay.com/qrcode/qrcodelist.htm?qrCodeType=P  二维码地址
        // http://cli.im/deqr/ 解析二维码
        // a6x05967si8cicrldsu2c7f
        if (AlipayZeroSdk.hasInstalledAlipayClient(this)) {
            AlipayZeroSdk.startAlipayClient(this, "a6x05967si8cicrldsu2c7f");
        } else {
            Toasty.info(this, "谢谢，您没有安装支付宝客户端").show();
        }

    }
    @OnClick(R.id.setting_logout_ll)
    public void logout(){

    }
    @OnClick(R.id.ll_setting_update)
    public void update(){

    }
    @OnClick(R.id.ll_setting_clean_cache)
    public void cleanCache() {
        getP().cleanCache(this);
    }

}

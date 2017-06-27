package com.android.xgank.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.android.kit.view.dialog.color.ColorChooserDialog;
import com.android.kit.view.dialog.util.DialogUtils;
import com.android.kit.view.image.CircleImageView;
import com.android.kit.view.widget.MyToolbar;
import com.android.mvp.mvp.XFragment;
import com.android.mvp.router.Router;
import com.android.xgank.R;
import com.android.xgank.bean.Constant;
import com.android.xgank.ui.activitys.FavActivity;
import com.android.xgank.ui.activitys.MainActivity;
import com.android.xgank.ui.activitys.SettingActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class OwnFragment extends XFragment {

    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    @BindView(R.id.header_view)
    View headerView;
    @BindView(R.id.me_iv)
    CircleImageView meIv;
    @BindView(R.id.me_tv)
    TextView meTv;
    @BindView(R.id.me_rl)
    RelativeLayout meRl;
    @BindView(R.id.login_bt)
    Button loginBt;
    @BindView(R.id.login_rl)
    RelativeLayout loginRl;
    @BindView(R.id.me_fav_ll)
    LinearLayout meFavLl;

    @BindView(R.id.me_nightTh_ll)
    LinearLayout meNightThLl;
    @BindView(R.id.me_setting_ll)
    LinearLayout meSettingLl;
    @BindView(R.id.fav)
    ImageView fav;
    @BindView(R.id.theme)
    ImageView theme;
    @BindView(R.id.theme_text)
    TextView themeText;
    @BindView(R.id.setting)
    ImageView setting;
    private int primaryPreselect;

    public OwnFragment() {
        // Required empty public constructor
    }

    @Override
    public boolean canBack() {
        return false;
    }

    public static OwnFragment getInstance() {
        OwnFragment meFragment = new OwnFragment();

        return meFragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setUpToolBar(true, toolbar, Constant.TOOLBAR_OWN_FRAGMENT);
        primaryPreselect = DialogUtils.resolveColor(getActivity(), R.attr.colorPrimary);

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_own;
    }

    @Override
    public Object newP() {
        return  null;
    }

    @OnClick({
        R.id.me_iv, R.id.login_bt, R.id.login_rl, R.id.me_fav_ll, R.id.me_nightTh_ll,
        R.id.me_setting_ll
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.me_iv:
                break;
            case R.id.login_bt:
                break;
            case R.id.login_rl:
                break;
            case R.id.me_fav_ll:
                goFav();
                break;

            case R.id.me_nightTh_ll:
                showThemeDialog();
                break;
            case R.id.me_setting_ll:
                goSetting();
                break;
        }
    }

    public void goSetting() {
        Router.newIntent(getActivity()).to(SettingActivity.class).launch();
    }

    public void goFav() {
        Router.newIntent(getActivity()).to(FavActivity.class).launch();
    }

    public void showThemeDialog() {
        new ColorChooserDialog.Builder((MainActivity) getActivity(), R.string.theme_color).titleSub(
            R.string.theme_color)
            .preselect(primaryPreselect)
            .backButton(R.string.theme_back)
            .doneButton(R.string.theme_done)
            .customButton(R.string.theme_custom)
            .cancelButton(R.string.theme_cancle)
            .presetsButton(R.string.theme_presets)
            .show();
    }

}

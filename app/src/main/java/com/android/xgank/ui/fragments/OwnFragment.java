package com.android.xgank.ui.fragments;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.android.kit.view.image.CircleImageView;
import com.android.kit.view.widget.MyToolbar;
import com.android.mvp.mvp.XFragment;
import com.android.mvp.router.Router;
import com.android.xgank.R;
import com.android.xgank.presenter.OwnPresenter;
import com.android.xgank.ui.activitys.FavActivity;
import com.android.xgank.ui.activitys.SettingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class OwnFragment extends XFragment<OwnPresenter> {


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
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.me_fav_ll)
    LinearLayout meFavLl;
    @BindView(R.id.me_nightTh_sw)
    Switch meNightThSw;
    @BindView(R.id.me_nightTh_ll)
    LinearLayout meNightThLl;
    @BindView(R.id.me_setting_ll)
    LinearLayout meSettingLl;
    Unbinder unbinder;

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
        setUpToolBar(true,toolbar,"我");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_own;
    }

    @Override
    public OwnPresenter newP() {
        return new OwnPresenter();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.me_iv, R.id.login_bt, R.id.login_rl, R.id.me_fav_ll, R.id.me_nightTh_sw, R.id.me_nightTh_ll, R.id.me_setting_ll})
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
            case R.id.me_nightTh_sw:
                break;
            case R.id.me_nightTh_ll:
                break;
            case R.id.me_setting_ll:
                goSetting();
                break;
        }
    }

    public void goSetting(){
        Router.newIntent(getActivity())
                .to(SettingActivity.class)
                .launch();
    }

    public void goFav(){
        Router.newIntent(getActivity())
                .to(FavActivity.class)
                .launch();
    }
}

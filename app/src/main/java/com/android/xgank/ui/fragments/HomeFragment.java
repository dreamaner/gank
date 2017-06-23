package com.android.xgank.ui.fragments;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.graphics.Palette;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.kit.utils.toast.ToastUtils;

import com.android.mvp.event.BusProvider;
import com.android.mvp.mvp.XFragment;

import com.android.mvp.recycleview.RecyclerItemCallback;
import com.android.mvp.recycleview.XRecyclerContentLayout;
import com.android.mvp.recycleview.XRecyclerView;
import com.android.mvp.router.Router;
import com.android.xgank.R;
import com.android.xgank.bean.Constant;
import com.android.xgank.bean.ThemeEvent;
import com.android.xgank.kit.DisplayUtils;
import com.android.xgank.kit.MDTintUtil;
import com.android.xgank.bean.GankResults;
import com.android.xgank.listener.HomeFrgListener;
import com.android.xgank.listener.RecyclerScrollListener;
import com.android.xgank.presenter.HomePresenter;

import com.android.xgank.ui.activitys.PhotoActivity;
import com.android.xgank.ui.activitys.SearchActivity;
import com.android.xgank.ui.activitys.WebActivity;


import com.android.xgank.ui.adapters.HomeAdapter;
import com.github.florent37.picassopalette.PicassoPalette;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import java.util.ArrayList;

public class HomeFragment extends XFragment<HomePresenter> {
    @BindView(R.id.text)
    TextView textView;
    @BindView(R.id.vp_home_category)
    XRecyclerContentLayout contentLayout;
    @BindView(R.id.fab_home_random)
    FloatingActionButton mFloatingActionButton;
    @BindView(R.id.iv_home_banner)
    ImageView ivHomeBanner;
    @BindView(R.id.tl_home_toolbar)
    Toolbar tlHomeToolbar;
    @BindView(R.id.ll_home_search)
    LinearLayout llHomeSearch;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.appBar)
    AppBarLayout mAppBarLayout;
    //照片路径的集合
    private ArrayList<String> urls;
    //照片ID的集合
    private ArrayList<String> ids;
    private HomeFrgListener homeFrgListener;
    private boolean isBannerBig; // banner 是否是大图
    private boolean isBannerAniming; // banner 放大缩小的动画是否正在执行
    private HomeAdapter adapter;
    private ObjectAnimator mAnimator;
    protected static final int MAX_PAGE = 10;
    private CollapsingToolbarLayoutState state; // CollapsingToolbarLayout 折叠状态

    @Override
    public boolean canBack() {
        return false;
    }

    private enum CollapsingToolbarLayoutState {
        EXPANDED, // 完全展开
        COLLAPSED, // 折叠
        INTERNEDIATE // 中间状态
    }

    public static HomeFragment getInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initImmersionBar();
        getP().loadData(getType(), 1);
        initAdapter();
        getP().getBanner(false);
        getP().cacheRandomImg();
        setToolbarHeight();
        setFabDynamicState();
        if (urls == null)
            urls = new ArrayList<>();
        if (ids == null)
            ids = new ArrayList<>();
    }

    public void setToolbarHeight() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 4.4 以上版本
            // 设置 Toolbar 高度为 80dp，适配状态栏
            ViewGroup.LayoutParams layoutParams = tlHomeToolbar.getLayoutParams();
            layoutParams.height = DisplayUtils.dp2px(80, getActivity());
            tlHomeToolbar.setLayoutParams(layoutParams);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public HomePresenter newP() {
        return new HomePresenter();
    }

    public void setLayoutManager(XRecyclerView recyclerView) {
        recyclerView.verticalLayoutManager(context);
    }

    private void initAdapter() {
        if (getActivity() instanceof HomeFrgListener) {
            homeFrgListener = (HomeFrgListener) getActivity();
        }
        setLayoutManager(contentLayout.getRecyclerView());
        contentLayout.getRecyclerView()
                .setAdapter(getAdapter());
        contentLayout.getRecyclerView().addOnScrollListener(recyclerScrollListener);
        contentLayout.getRecyclerView()
                .setOnRefreshAndLoadMoreListener(new XRecyclerView.OnRefreshAndLoadMoreListener() {
                    @Override
                    public void onRefresh() {
                        getP().loadData(getType(), 1);
                    }

                    @Override
                    public void onLoadMore(int page) {
                        getP().loadData(getType(), page);
                    }
                });

        contentLayout.getRecyclerView().useDefLoadMoreView();
    }

    /**
     * 根据 CollapsingToolbarLayout 的折叠状态，设置 FloatingActionButton 的隐藏和显示
     */
    private void setFabDynamicState() {

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset == 0) {
                    if (state != CollapsingToolbarLayoutState.EXPANDED) {
                        state = CollapsingToolbarLayoutState.EXPANDED; // 修改状态标记为展开
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                        mFloatingActionButton.hide();

                        state = CollapsingToolbarLayoutState.COLLAPSED; // 修改状态标记为折叠
                        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
                        layoutParams.height = DisplayUtils.dp2px(240, getActivity());
                        mAppBarLayout.setLayoutParams(layoutParams);
                        isBannerBig = false;
                    }
                } else {
                    if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                        if (state == CollapsingToolbarLayoutState.COLLAPSED) {
                            mFloatingActionButton.show();

                        }
                        state = CollapsingToolbarLayoutState.INTERNEDIATE; // 修改状态标记为中间
                    }
                }
                showText(state);
            }
        });

    }

    public void showText(CollapsingToolbarLayoutState state) {
        switch (state) {
            case COLLAPSED:
                textView.setText(R.string.collapsed_next_tip);
                break;
            case EXPANDED:
                textView.setText(R.string.expanded_next_tip);
                break;
        }
    }

    public void showData(int page, GankResults model) {
        if (page > 1) {
            getAdapter().addData(model.getResults());
        } else {
            getAdapter().setData(model.getResults());
        }

        contentLayout.getRecyclerView().setPage(page, MAX_PAGE);

        if (getAdapter().getItemCount() < 1) {
            contentLayout.showEmpty();
            return;
        }
    }

    public String getType() {
        return String.valueOf(R.string.gank_all_type);
    }

    public HomeAdapter getAdapter() {
        if (adapter == null) {
            adapter = new HomeAdapter(context);
            adapter.setRecItemClick(new RecyclerItemCallback<GankResults.Item, HomeAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, GankResults.Item model, int tag, HomeAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);

                    switch (tag) {
                        case HomeAdapter.TAG_VIEW:
                            if (model.getType().equals(Constant.PHOTO))
                                goPhoto(model,position);
                            else
                                launch(context, model);
                            break;
                    }
                }
            });
        }
        return adapter;
    }

    public void launch(Activity activity, GankResults.Item item) {
        Router.newIntent(activity)
                .to(WebActivity.class)
                .putSerializable(String.valueOf(R.string.intent_to_web_put_item), item)
                .putInt(String.valueOf(R.string.intent_to_web_put_flag), 1)
                .launch();
    }

    @OnClick({R.id.iv_home_banner, R.id.fab_home_random, R.id.ll_home_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_home_random:
                getP().getBanner(true);
                break;
            case R.id.ll_home_search:
                goSearch();
                break;
            case R.id.iv_home_banner:
                if (isBannerAniming) {
                    return;
                }
                startBannerAnim();
                break;
        }
    }

    public void goSearch() {
        Router.newIntent(getActivity())
                .to(SearchActivity.class)
                .launch();
    }


    public void goPhoto(GankResults.Item item,int position){
        urls.clear();
        ids.clear();
        ids.add(item.get_id());
        urls.add(item.getUrl());
        Router.newIntent(getActivity())
                .to(PhotoActivity.class)
                .putStringArrayList(String.valueOf(R.string.intent_to_photo_put_urls_string_array),urls)
                .putInt(String.valueOf(R.string.intent_to_photo_position),0)
                .putInt(String.valueOf(R.string.intent_to_photo_put_flag), 1)
                .putStringArrayList(String.valueOf(R.string.intent_to_photo_put_ids_string_array),ids)
                .putSerializable(String.valueOf(R.string.intent_to_photo_put_item), item)
                .launch();
    }

    private void startBannerAnim() {
        final CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
        ValueAnimator animator;
        if (isBannerBig) {
            animator = ValueAnimator.ofInt(DisplayUtils.getScreenHeight(getActivity()), DisplayUtils.dp2px(240, getActivity()));
        } else {
            animator = ValueAnimator.ofInt(DisplayUtils.dp2px(240, getActivity()), DisplayUtils.getScreenHeight(getActivity()));
        }
        animator.setDuration(1000);
        animator.addUpdateListener(valueAnimator -> {
            layoutParams.height = (int) valueAnimator.getAnimatedValue();
            mAppBarLayout.setLayoutParams(layoutParams);
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isBannerBig = !isBannerBig;
                isBannerAniming = false;
            }
        });
        animator.start();
        isBannerAniming = true;
    }

    public void setBanner(String imgUrl) {
        Picasso.with(getActivity()).load(imgUrl)
                .into(ivHomeBanner,
                        PicassoPalette.with(imgUrl, ivHomeBanner)
                                .intoCallBack(palette -> {
                                    getP().setThemeColor(palette);
                                    BusProvider.getBus().post(new ThemeEvent());
                                }));
    }

    public void cacheImg(final String imgUrl) {
        // 预加载 提前缓存好的欢迎图
        Picasso.with(getActivity()).load(imgUrl).fetch(new Callback() {
            @Override
            public void onSuccess() {
                getP().saveCacheImgUrl(imgUrl);
            }

            @Override
            public void onError() {

            }
        });
    }

    public void showBannerFail(String failMessage) {
        ToastUtils.showLongToast(failMessage);
    }

    public void setAppBarLayoutColor(int appBarLayoutColor) {
        mCollapsingToolbar.setContentScrimColor(appBarLayoutColor);
        mAppBarLayout.setBackgroundColor(appBarLayoutColor);
    }

    public void setFabButtonColor(int color) {

        MDTintUtil.setTint(mFloatingActionButton, color);
    }

    public void startBannerLoadingAnim() {
        mFloatingActionButton.setImageResource(R.drawable.ic_loading);
        mAnimator = ObjectAnimator.ofFloat(mFloatingActionButton,
            String.valueOf(R.string.animation_ratotion), 0, 360);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setDuration(800);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.start();
    }

    public void stopBannerLoadingAnim() {
        mFloatingActionButton.setImageResource(R.drawable.ic_beauty);
        mAnimator.cancel();
        mFloatingActionButton.setRotation(0);
    }

    public void enableFabButton() {
        mFloatingActionButton.setEnabled(true);
    }

    public void disEnableFabButton() {
        mFloatingActionButton.setEnabled(false);
    }

    private RecyclerScrollListener recyclerScrollListener = new RecyclerScrollListener() {
        @Override
        public void hideToolBar() {
            if (homeFrgListener != null)
                homeFrgListener.hideToolbar();
        }

        @Override
        public void showToolBar() {
            if (homeFrgListener != null)
                homeFrgListener.showToolbar();
        }

        @Override
        public void hideBottomBar() {
            if (homeFrgListener != null)
                homeFrgListener.hideBottomBar();

        }

        @Override
        public void showBottomBar() {
            if (homeFrgListener != null)
                homeFrgListener.showBottomBar();

        }

        @Override
        public void firstVisibleItemPosition(int position) {

        }
    };
}

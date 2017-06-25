package com.android.xgank.ui.activitys;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.android.kit.utils.toast.Toasty;
import com.android.mvp.kit.Kits;
import com.android.mvp.mvp.XActivity;
import com.android.mvp.recycleview.XStateController;
import com.android.xgank.R;
import com.android.xgank.bean.Constant;
import com.android.xgank.bean.Favorite;
import com.android.xgank.bean.GankResults;
import com.android.xgank.bean.SearchResult;
import com.android.xgank.config.ConfigManage;
import com.android.xgank.config.ThemeManage;
import com.android.xgank.kit.MDTintUtil;
import com.android.xgank.presenter.WebPresenter;
import com.android.xgank.ui.widget.ObservableWebView;

/**
 * Created by Dreamaner on 2017/5/15.
 */
public class WebActivity extends XActivity<WebPresenter> {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webView)
    ObservableWebView webView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.contentLayout)
    XStateController contentLayout;
    @BindView(R.id.fab_web_favorite)
    FloatingActionButton mFloatingActionButton;
    @BindView(R.id.appbar)
    AppBarLayout appbar;

    public String url;
    public String desc;

    public GankResults.Item item;
    public Favorite fav;
    public SearchResult.Item search;


    private int flag;
    public boolean isForResult;// 是否回传结果

    @Override
    public void initData(Bundle savedInstanceState) {
        compareTo();
        getP().init();
        setUpToolBar(true, toolbar, desc);
        initRefreshLayout();
        initWebView();
        getP().showFavState();
        MDTintUtil.setTint(mFloatingActionButton, ThemeManage.INSTANCE.getColorPrimary());
    }

    public void compareTo() {
        flag = getIntent().getIntExtra(Constant.INTENT_FLAG, 0);
        if (flag == 1) {
            item = (GankResults.Item) getIntent().getSerializableExtra(Constant.INTENT_FLAG_ITEM);
            url = item.getUrl();
            desc = item.getDesc();
        } else if (flag == 2) {
            fav = (Favorite) getIntent().getSerializableExtra(Constant.INTENT_FLAG_ITEM);
            url = fav.getUrl();
            desc = fav.getDesc();
        } else if (flag == 3) {
            search = (SearchResult.Item) getIntent().getSerializableExtra(Constant.INTENT_FLAG_ITEM);
            url = search.getUrl();
            desc = search.getDesc();
        }
    }

    private void initRefreshLayout() {
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
            android.R.color.holo_red_light, android.R.color.holo_orange_light,
            android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            webView.loadUrl(url);
        });
    }

    public Favorite getFavorite() {
        if (flag == 1) {
            fav = new Favorite();

            fav.setGank_id(item.get_id());
            fav.setImages(item.getImages());
            fav.setCreatedAt(item.getCreatedAt());
            fav.setDesc(item.getDesc());
            fav.setPublishedAt(item.getPublishedAt());
            fav.setSource(item.getSource());
            fav.setType(item.getType());
            fav.setUrl(item.getUrl());
            fav.setUsed(item.getUsed());
            fav.setWho(item.getWho());
        } else if (flag == 3) {
            fav = new Favorite();

            fav.setGank_id(search.getGanhuo_id());
            fav.setWho(search.getWho());
            fav.setUrl(search.getUrl());
            fav.setType(search.getType());
            fav.setDesc(search.getDesc());
            fav.setCreatedAt(search.getPublishedAt());
            fav.setPublishedAt(search.getPublishedAt());
        }

        return fav;
    }

    @OnClick(R.id.fab_web_favorite)
    public void favorite() {
        getP().favoriteGank();
    }

    public void setFavoriteState(boolean isFavorite) {
        if (isFavorite) {
            mFloatingActionButton.setImageResource(R.drawable.ic_favorite);
        } else {
            mFloatingActionButton.setImageResource(R.drawable.ic_unfavorite);
        }
        isForResult = !isFavorite;
    }

    public void showTips(String msg) {

        switch (msg) {
            case Constant.FAV_SUCCESS:
                Toasty.success(this, msg).show();
                break;
            case Constant.FAV_DEFEAT:
                Toasty.error(this, msg).show();
                break;
            case Constant.UNFAV_SUCCESS:
                Toasty.info(this, msg).show();
                break;
            case Constant.UNFAV_DEFEAT:
                Toasty.error(this, msg).show();
                break;
        }
    }

    public void hideFavoriteFab() {
        mFloatingActionButton.setVisibility(View.GONE);
        webView.setOnScrollChangedCallback(null);
    }

    private void initWebView() {
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    swipeRefreshLayout.setRefreshing(false);
                    if (contentLayout != null) contentLayout.showContent();
                    if (webView != null) url = webView.getUrl();
                } else {
                    if (contentLayout != null) contentLayout.showLoading();
                }
            }
        });
        WebSettings webSettings = webView.getSettings();
        webView.setWebViewClient(new WebViewClient());
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setBuiltInZoomControls(true); // 支持手势缩放
        webSettings.setDisplayZoomControls(false); // 不显示缩放按钮

        webSettings.setDatabaseEnabled(true);
        webSettings.setSaveFormData(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setUseWideViewPort(true); // 将图片调整到适合WebView的大小
        webSettings.setLoadWithOverviewMode(true); // 自适应屏幕

        webView.loadUrl(url);
        webView.setOnScrollChangedCallback((dx, dy) -> {
            if (dy > 0) {
                mFloatingActionButton.hide();
            } else {
                mFloatingActionButton.show();
            }
        });
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_share:
                Kits.shareText(this, webView.getTitle() + " " + webView.getUrl() + " 来自[X-MVP]");
                break;
            case R.id.action_refresh:
                webView.reload();
                break;
            case R.id.action_copy:
                Kits.copyToClipBoard(this, webView.getUrl());
                break;
            case R.id.action_open_in_browser:
                Kits.openInBrowser(this, webView.getUrl());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null) webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null) webView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            ViewGroup parent = (ViewGroup) webView.getParent();
            if (parent != null) {
                parent.removeView(webView);
            }
            webView.removeAllViews();
            webView.destroy();
        }
    }

    @Override
    public int getOptionsMenuId() {
        return R.menu.menu_web;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public WebPresenter newP() {
        return new WebPresenter();
    }


}

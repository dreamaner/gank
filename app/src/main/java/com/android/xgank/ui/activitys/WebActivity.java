package com.android.xgank.ui.activitys;

import android.app.Activity;
import android.os.Bundle;
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

import com.android.kit.view.likebutton.LikeButton;
import com.android.kit.view.likebutton.OnLikeListener;
import com.android.mvp.kit.Kits;
import com.android.mvp.recycleview.XStateController;

import com.android.mvp.mvp.XActivity;
import com.android.mvp.router.Router;
import com.android.xgank.R;
import com.android.xgank.bean.Favorite;
import com.android.xgank.bean.GankResults;



import butterknife.BindView;


/**
 * Created by Dreamaner on 2017/5/15.
 */
public class WebActivity extends XActivity implements OnLikeListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.contentLayout)
    XStateController contentLayout;
    @BindView(R.id.fab_web_favorite)
    LikeButton likeButton;
    public String url;
    public String desc;
    public GankResults.Item item;
    public Favorite fav;
    @Override
    public void initData(Bundle savedInstanceState) {

        item = (GankResults.Item) getIntent().getSerializableExtra("item");
        url = item.getUrl();
        desc = item.getDesc();
        

        setUpToolBar(true,toolbar,desc);
        initContentLayout();
        initRefreshLayout();
        initWebView();
        likeButton.setOnLikeListener(this);
    }

    private void initContentLayout() {
        contentLayout.loadingView(View.inflate(context, R.layout.loading, null));
    }

    private void initRefreshLayout() {
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.loadUrl(url);
            }
        });

    }

    private void initWebView() {
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    swipeRefreshLayout.setRefreshing(false);
                    if (contentLayout != null)
                        contentLayout.showContent();
                    if (webView != null)
                        url = webView.getUrl();
                } else {
                    if (contentLayout != null)
                        contentLayout.showLoading();
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
//        webView.setScrollListener(new WebViewScrollListener() {
//            @Override
//            public void hideToolbar() {
//                new Handler().postDelayed(() -> {
//                    toolbar.hide();
////                    hideSystemUI();
//                }, 500);
//
//
//            }
//
//            @Override
//            public void showToolbar() {
//                new Handler().postDelayed(() -> {
////                    showSystemUI();
//                    toolbar.show();
//                }, 500);
//            }
//        });
    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    private void showSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_share:
                Kits.shareText(this, webView.getTitle() + " " + webView.getUrl() + " 来自「XDroid」");
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
    public Object newP() {
        return null;
    }

    @Override
    public void liked(LikeButton likeButton) {

    }

    @Override
    public void unLiked(LikeButton likeButton) {

    }
}

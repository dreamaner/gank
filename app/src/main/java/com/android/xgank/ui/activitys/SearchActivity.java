package com.android.xgank.ui.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.kit.utils.check.CheckUtils;
import com.android.kit.utils.system.KeyboardUtils;
import com.android.kit.utils.toast.Toasty;
import com.android.mvp.mvp.XActivity;
import com.android.mvp.recycleview.RecyclerItemCallback;
import com.android.mvp.recycleview.XRecyclerContentLayout;
import com.android.mvp.recycleview.XRecyclerView;
import com.android.mvp.router.Router;
import com.android.xgank.R;
import com.android.xgank.bean.Constant;
import com.android.xgank.bean.GankResults;
import com.android.xgank.bean.History;
import com.android.xgank.bean.SearchResult;
import com.android.xgank.config.ConfigManage;
import com.android.xgank.presenter.SearchPresenter;
import com.android.xgank.ui.adapters.HistoryAdapter;
import com.android.xgank.ui.adapters.SearchAdapter;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends XActivity<SearchPresenter> implements TextWatcher, TextView.OnEditorActionListener{

    private static final int MAX_PAGE = 10;
    @BindView(R.id.ed_search)
    AppCompatEditText edSearch;
    @BindView(R.id.iv_edit_clear)
    AppCompatImageView ivEditClear;
    @BindView(R.id.iv_search)
    AppCompatImageView ivSearch;
    @BindView(R.id.toolbar_search)
    Toolbar toolbarSearch;
    @BindView(R.id.appbar_search)
    AppBarLayout appbarSearch;
    @BindView(R.id.tv_search_clean)
    TextView tvSearchClean;
    @BindView(R.id.recycler_search_history)
    RecyclerView recyclerSearchHistory;
    @BindView(R.id.ll_search_history)
    LinearLayout llSearchHistory;
    @BindView(R.id.contentLayout)
    XRecyclerContentLayout contentLayout;

    public HistoryAdapter historyAdapter;
    public SearchAdapter searchAdapter;
    public ArrayList<String> urls;
    public ArrayList<String> ids;
    @Override
    public void initData(Bundle savedInstanceState) {
        setUpToolBar(true, toolbarSearch, "");

        getP().queryHistory();

        edSearch.addTextChangedListener(this);
        edSearch.setOnEditorActionListener(this);
        initSearchAdapter();
        initHistoryAdapter();
        //toolbarSearch.setBackgroundColor(ConfigManage.INSTANCE.getThemeColor());
        if (urls == null)
            urls = new ArrayList<>();
        if (ids == null)
            ids = new ArrayList<>();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public SearchPresenter newP() {
        return new SearchPresenter();
    }

    @Override
    public boolean canBack() {
        return true;
    }

    public void setLayoutManager(XRecyclerView recyclerView) {
        recyclerView.verticalLayoutManager(context);
    }

    public void initHistoryAdapter(){
        FlexboxLayoutManager manager = new FlexboxLayoutManager(this);
        //设置主轴排列方式
        manager.setFlexDirection(FlexDirection.ROW);
        //设置是否换行
        manager.setFlexWrap(FlexWrap.WRAP);
        manager.setAlignItems(AlignItems.STRETCH);
        recyclerSearchHistory.setLayoutManager(manager);
        recyclerSearchHistory.setAdapter(getHistoryAdapter());
    }

    public void initSearchAdapter(){
        setLayoutManager(contentLayout.getRecyclerView());
        contentLayout.getRecyclerView()
                .setAdapter(getSearchAdapter());
        contentLayout.getRecyclerView()
                .setOnRefreshAndLoadMoreListener(new XRecyclerView.OnRefreshAndLoadMoreListener() {
                    @Override
                    public void onRefresh() {
                        getP().loadData(getKey(), 1);
                    }

                    @Override
                    public void onLoadMore(int page) {
                        getP().loadData(getKey(), page);
                    }
                });

        contentLayout.getRecyclerView().useDefLoadMoreView();


    }

    public void showHistoryData(List<History> list) {
        getHistoryAdapter().setData(list);
    }

    public void showSearchData(List<SearchResult.Item> list, int page) {

        if (page > 1) {
            getSearchAdapter().addData(list);
        } else {
            getSearchAdapter().setData(list);
        }

        contentLayout.getRecyclerView().setPage(page, MAX_PAGE);

        if (getSearchAdapter().getItemCount() < 1) {
            contentLayout.showEmpty();
            return;
        }

    }

    public HistoryAdapter getHistoryAdapter() {
        if (historyAdapter == null) {
            historyAdapter = new HistoryAdapter(context);
            historyAdapter.setRecItemClick(new RecyclerItemCallback<History, HistoryAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, History model, int tag, HistoryAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);

                    switch (tag) {
                        case HistoryAdapter.TAG_VIEW:
                            edSearch.setText(model.getContent());
                            getP().loadData(getKey(), 1);
                            break;
                    }
                }
            });
        }
        return historyAdapter;
    }

    public String getKey() {
        return edSearch.getText().toString().trim();
    }

    public SearchAdapter getSearchAdapter() {
        if (searchAdapter == null) {
            searchAdapter = new SearchAdapter(context);
            searchAdapter.setRecItemClick(new RecyclerItemCallback<SearchResult.Item, SearchAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, SearchResult.Item model, int tag, SearchAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);

                    switch (tag) {
                        case SearchAdapter.TAG_VIEW:
                            if (model.getType().equals(Constant.PHOTO))
                                goPhoto(searchAdapter.getDataSource(),position);
                            else
                                launch(context, model);
                            break;
                    }
                }
            });
        }
        return searchAdapter;
    }

    public void goPhoto(List<SearchResult.Item> items,int position){
        urls.clear();
        for (SearchResult.Item item:items){
            urls.add(item.getUrl());
        }
        ids.clear();
        for (SearchResult.Item item:items){
            ids.add(item.getGanhuo_id());
        }
        Router.newIntent(this)
            .to(PhotoActivity.class)
            .putStringArrayList(Constant.INTENT_FLAG_URLS,urls)
            .putStringArrayList(Constant.INTENT_FLAG_IDS,ids)
            .putInt(Constant.INTENT_FLAG_POSITION,position)
            .putInt(Constant.INTENT_FLAG, 3)
            .putSerializable(Constant.INTENT_FLAG_SEARCH,items.get(position))
            .launch();
    }

    public void setContentLayoutInVisible() {
        contentLayout.setVisibility(View.GONE);
    }

    public void setContentLayoutVisible() {
        contentLayout.setVisibility(View.VISIBLE);
    }

    public void setLlSearchHistoryVisible() {
        llSearchHistory.setVisibility(View.VISIBLE);
    }

    public void setLlSearchHistoryInVisible() {
        llSearchHistory.setVisibility(View.GONE);
    }

    public void setIvEditClearVisible(){
        ivEditClear.setVisibility(View.VISIBLE);
    }

    public void setIvEditClearInVisible(){
        ivEditClear.setVisibility(View.GONE);
    }

    public void launch(Activity activity, SearchResult.Item item) {
        Router.newIntent(activity)
                .to(WebActivity.class)
                .putSerializable("item", item)
                .putInt("flag", 3)
                .launch();
    }

    @OnClick(R.id.iv_edit_clear)
    public void onIvEditClearClicked() {
        edSearch.setText("");
        ivEditClear.setVisibility(View.GONE);
        getP().queryHistory();
    }
    @OnClick(R.id.iv_search)
    public void onIvSearchClicked() {
        if (!CheckUtils.isEmpty(getKey())){
            KeyboardUtils.hideSoftInput(this);
            getP().loadData(getKey(), 1);
            getP().saveHistory(getKey());
        } else{
            Toasty.warning(this,"不要输入空数据哟").show();
        }
    }

    @OnClick(R.id.tv_search_clean)
    public void onTvSearchCleanClicked() {
        getP().deleteHistory();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length() > 0) {
            setIvEditClearVisible();
        } else {
            setIvEditClearInVisible();
            getP().queryHistory();
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if ( i == EditorInfo.IME_ACTION_SEARCH) {
            getP().loadData(getKey(),1);
        }
        return false;
    }
}

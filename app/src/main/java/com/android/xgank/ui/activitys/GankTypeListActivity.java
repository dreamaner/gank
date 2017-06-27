package com.android.xgank.ui.activitys;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;

import com.android.kit.view.widget.MyToolbar;
import com.android.mvp.base.SimpleRecAdapter;
import com.android.mvp.mvp.XActivity;
import com.android.mvp.net.NetError;
import com.android.mvp.recycleview.RecyclerItemCallback;
import com.android.mvp.recycleview.XRecyclerContentLayout;
import com.android.mvp.recycleview.XRecyclerView;
import com.android.mvp.router.Router;
import com.android.xgank.R;
import com.android.xgank.bean.Constant;
import com.android.xgank.bean.GankResults;
import com.android.xgank.presenter.GankTypePresenter;
import com.android.xgank.ui.adapters.HomeAdapter;

import org.litepal.util.Const;

import butterknife.BindView;
import java.util.ArrayList;
import java.util.List;

public class GankTypeListActivity extends XActivity<GankTypePresenter> {


    private static final int MAX_PAGE = 10 ;
    @BindView(R.id.toolbar)
    MyToolbar mToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.recyclerContent)
    XRecyclerContentLayout contentLayout;

    private ArrayList<String> urls;
    private Intent it;
    private String type;
    private HomeAdapter adapter;
    public ArrayList<String> ids;
    @Override
    public void initData(Bundle savedInstanceState) {
         showData();
//         initImmersionBar();
         setUpToolBar(true,mToolbar,type);

         initAdapter();

         getP().loadData(type,1);

        //mToolbar.setBackgroundColor(ConfigManage.INSTANCE.getThemeColor());
    }
    public void showData(){
        it = getIntent();
        type = it.getStringExtra(Constant.INTENT_FLAG_TYPE);

        if (urls == null){
            urls = new ArrayList<>();
        }

        if (ids == null)
            ids = new ArrayList<>();
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_gank_type_list;
    }
    private void initAdapter() {

        setLayoutManager(contentLayout.getRecyclerView());

        contentLayout.getRecyclerView()
                .setAdapter(getAdapter());
        //contentLayout.getRecyclerView().addOnScrollListener(listener);
        contentLayout.getRecyclerView()
                .setOnRefreshAndLoadMoreListener(new XRecyclerView.OnRefreshAndLoadMoreListener() {
                    @Override
                    public void onRefresh() {
                        getP().loadData(type, 1);
                    }

                    @Override
                    public void onLoadMore(int page) {
                        getP().loadData(type, page);
                    }
                });

        contentLayout.getRecyclerView().useDefLoadMoreView();
    }
    public void setLayoutManager(XRecyclerView recyclerView) {
        if (type.equals(Constant.PHOTO))
            recyclerView.verticalStaggeredLayoutManager(2);
            //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        else
            recyclerView.verticalLayoutManager(context);
    }
    @Override
    public GankTypePresenter newP() {
        return new GankTypePresenter();
    }

    public void goPhoto(List<GankResults.Item> items,int position){
        urls.clear();
        for (GankResults.Item item:items){
             urls.add(item.getUrl());
        }
        ids.clear();
        for (GankResults.Item item:items){
            ids.add(item.get_id());
        }
        Router.newIntent(this)
            .to(PhotoActivity.class)
            .putStringArrayList(Constant.INTENT_FLAG_URLS,urls)
            .putStringArrayList(Constant.INTENT_FLAG_IDS,ids)
            .putInt(Constant.INTENT_FLAG_POSITION,position)
            .putInt(Constant.INTENT_FLAG, 1)
            .putSerializable(Constant.INTENT_FLAG_ITEM, items.get(position))
            .launch();
    }
    @Override
    public boolean canBack() {
        return true;
    }
    public void showView(int page, GankResults model) {
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

    public SimpleRecAdapter getAdapter() {
        if (adapter == null) {
            adapter = new HomeAdapter(context);
            adapter.setRecItemClick(new RecyclerItemCallback<GankResults.Item, HomeAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, GankResults.Item model, int tag, HomeAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);
                    switch (tag) {
                        case HomeAdapter.TAG_VIEW:
                            if (type.equals(Constant.PHOTO))
                                goPhoto(adapter.getDataSource(),position);
                            else
                                launch(context, model);
                            break;
                    }
                }
            });
        }
        return adapter;
    }
    public void showError(NetError error){
        showError(contentLayout,error);
    }
    public static void launch(Activity activity, GankResults.Item item) {
        Router.newIntent(activity)
                .to(WebActivity.class)
                .putSerializable(Constant.INTENT_FLAG_ITEM,item)
                .putInt(Constant.INTENT_FLAG,1)
                .launch();
    }
}

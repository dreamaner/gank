package com.android.xgank.ui.activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.android.kit.view.widget.MyToolbar;
import com.android.mvp.base.SimpleRecAdapter;
import com.android.mvp.mvp.XActivity;
import com.android.mvp.recycleview.RecyclerItemCallback;
import com.android.mvp.recycleview.XRecyclerContentLayout;
import com.android.mvp.recycleview.XRecyclerView;
import com.android.mvp.router.Router;
import com.android.xgank.R;
import com.android.xgank.bean.Constant;
import com.android.xgank.bean.GankResults;
import com.android.xgank.presenter.GankTypePresenter;
import com.android.xgank.ui.adapters.HomeAdapter;

import butterknife.BindView;



public class GankTypeListActivity extends XActivity<GankTypePresenter> {


    private static final int MAX_PAGE = 10 ;
    @BindView(R.id.toolbar)
    MyToolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.recyclerContent)
    XRecyclerContentLayout contentLayout;

    private Intent it;
    private String type;
    private HomeAdapter adapter;
    @Override
    public void initData(Bundle savedInstanceState) {
         showData();
//         initImmersionBar();
         setUpToolBar(true,toolbar,type);

         initAdapter();

         getP().loadData(type,1);
    }
    public void showData(){
        it = getIntent();
        type = it.getStringExtra("type");
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_gank_type_list;
    }
    private void initAdapter() {

        setLayoutManager(contentLayout.getRecyclerView());

        contentLayout.getRecyclerView()
                .setAdapter(getAdapter());
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
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        else
            recyclerView.verticalLayoutManager(context);
    }
    @Override
    public GankTypePresenter newP() {
        return new GankTypePresenter();
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
                            launch(context, model);
                            break;
                    }
                }
            });
        }
        return adapter;
    }

    public static void launch(Activity activity, GankResults.Item item) {
        Router.newIntent(activity)
                .to(WebActivity.class)
                .putSerializable("item",item)
                .launch();
    }
}

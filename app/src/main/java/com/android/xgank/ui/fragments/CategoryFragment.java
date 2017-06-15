package com.android.xgank.ui.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.mvp.mvp.XLazyFragment;
import com.android.mvp.recycleview.RecyclerItemCallback;
import com.android.mvp.recycleview.XRecyclerContentLayout;
import com.android.mvp.recycleview.XRecyclerView;
import com.android.mvp.router.Router;
import com.android.xgank.R;
import com.android.xgank.bean.Favorite;

import com.android.xgank.presenter.CategoryPresenter;
import com.android.xgank.ui.activitys.WebActivity;
import com.android.xgank.ui.adapters.CatrgoryListAdapter;
import java.util.List;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends XLazyFragment<CategoryPresenter> {


    private static final String CATEGORY_NAME = "com.android.xgank.ui.fragments.CategoryFragment";
    private static final int MAX_PAGE = 5;
    @BindView(R.id.content)
    XRecyclerContentLayout content;

    public String mCategoryName;
    private CatrgoryListAdapter adapter;

    public CategoryFragment() {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance(String mCategoryName) {
        CategoryFragment categoryFragment = new CategoryFragment();

        Bundle bundle = new Bundle();
        bundle.putString(CATEGORY_NAME, mCategoryName);

        categoryFragment.setArguments(bundle);
        return categoryFragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        mCategoryName = bundle.getString(CATEGORY_NAME);
        getP().loadData(getType(),1);
        initAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    public CategoryPresenter newP() {
        return new CategoryPresenter();
    }

    @Override
    public boolean canBack() {
        return false;
    }


    public void setLayoutManager(XRecyclerView recyclerView) {
        recyclerView.verticalLayoutManager(context);
    }

    private void initAdapter() {

        setLayoutManager(content.getRecyclerView());
        content.getRecyclerView().setAdapter(getAdapter());
//        contentLayout.getRecyclerView().addOnScrollListener(recyclerScrollListener);
        content.getRecyclerView().setOnRefreshAndLoadMoreListener(new XRecyclerView.OnRefreshAndLoadMoreListener() {
                    @Override
                    public void onRefresh() {
                        getP().loadData(getType(),1);
                    }

                    @Override
                    public void onLoadMore(int page) {
                        getP().loadData(getType(),page);
                    }
                });

        content.getRecyclerView().useDefLoadMoreView();
    }
    public void showData(List<Favorite> list,int page) {

        if (page > 1) {
            getAdapter().addData(list);
        } else {
            getAdapter().setData(list);
        }
        if (list.size() < 5)
            content.getRecyclerView().setPage(MAX_PAGE, MAX_PAGE);
        else
            content.getRecyclerView().setPage(page, MAX_PAGE);

        if (getAdapter().getItemCount() < 1) {
            content.showEmpty();
            return;
        }
    }

    public String getType(){
        return mCategoryName;
    }

    public CatrgoryListAdapter getAdapter() {
        if (adapter == null) {
            adapter = new CatrgoryListAdapter(context);
            adapter.setRecItemClick(new RecyclerItemCallback<Favorite, CatrgoryListAdapter.ViewHolder>() {
                @Override
                public void onItemClick(int position, Favorite model, int tag, CatrgoryListAdapter.ViewHolder holder) {
                    super.onItemClick(position, model, tag, holder);

                    switch (tag) {
                        case CatrgoryListAdapter.TAG_VIEW:
                            launch(context, model);
                            break;
                    }
                }
            });
        }
        return adapter;
    }

    public  void launch(Activity activity, Favorite item) {
        Router.newIntent(activity)
                .to(WebActivity.class)
                .putSerializable("item",item)
                .putInt("flag",2)
                .launch();
    }

}

package com.android.xgank.ui.fragments;


import android.os.Bundle;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.kit.view.immersion.ImmersionBar;
import com.android.kit.view.recycleview.BaseQuickAdapter;
import com.android.kit.view.recycleview.callback.ItemDragAndSwipeCallback;
import com.android.kit.view.recycleview.listener.OnItemDragListener;
import com.android.kit.view.widget.MyToolbar;

import com.android.mvp.event.BusProvider;
import com.android.mvp.log.XLog;
import com.android.mvp.mvp.XFragment;

import com.android.mvp.router.Router;
import com.android.xgank.R;
import com.android.xgank.bean.Constant;
import com.android.xgank.bean.MoreEntity;
import com.android.xgank.bean.ShowImgEvent;
import com.android.xgank.listener.HomeFrgListener;
import com.android.xgank.listener.RecyclerScrollListener;

import com.android.xgank.presenter.MorePresenter;
import com.android.xgank.ui.activitys.GankTypeListActivity;
import com.android.xgank.ui.adapters.MoreAdapter;

import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;



public class MoreFragment extends XFragment<MorePresenter> {


    @BindView(R.id.toolbar)
    MyToolbar toolbar;

    @BindView(R.id.more)
    RecyclerView more;

    private HomeFrgListener homeFrgListener;
    private List<MoreEntity> moreEntities  = new ArrayList<>();;
    private MoreAdapter moreAdapter;
    private ItemTouchHelper itemTouchHelper;
    private ItemDragAndSwipeCallback dragAndSwipeCallback;
    public static MoreFragment getInstance() {
        MoreFragment moreFragment = new MoreFragment();

        return moreFragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ImmersionBar.with(getActivity())
                .statusBarDarkFont(false)
                .navigationBarColor(R.color.colorPrimary)
                .init();
        setUpToolBar(true,toolbar, Constant.TOOLBAR_MORE_FRAGMENT);
        getP().getMoreData();

    }

    private OnItemDragListener dragListener=new OnItemDragListener() {


        @Override
        public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
        }

        @Override
        public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {

        }

        @Override
        public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
            getP().updateMoreData(moreEntities);
            moreAdapter.notifyDataSetChanged();
        }
    };
    @Override
    public int getLayoutId() {
        return R.layout.fragment_more;
    }

    @Override
    public MorePresenter newP() {
        return new MorePresenter();
    }

    @Override
    public boolean canBack() {
        return false;
    }


    public void setUpMoreData(List<MoreEntity> list) {

        if (getActivity() instanceof HomeFrgListener){
            homeFrgListener = (HomeFrgListener) getActivity();
        }
        moreEntities.addAll(list);

        more.setLayoutManager(new GridLayoutManager(getContext(), 2));
        moreAdapter = new MoreAdapter(R.layout.gank_more_item_example, moreEntities, getContext());
//        moreAdapter.addHeaderView(LayoutInflater.from(getContext()).inflate(R.layout.recycler_header,
//                (ViewGroup) more.getParent(), false));
        moreAdapter.addFooterView(LayoutInflater.from(getContext()).inflate(R.layout.recycler_footer,
                (ViewGroup) more.getParent(), false));

        moreAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        moreAdapter.isFirstOnly(true);

        dragAndSwipeCallback=new ItemDragAndSwipeCallback(moreAdapter);
        itemTouchHelper=new ItemTouchHelper(dragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(more);
        dragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);

        moreAdapter.enableDragItem(itemTouchHelper);
        moreAdapter.setOnItemDragListener(dragListener);
        more.setAdapter(moreAdapter);
        moreAdapter.setOnItemClickListener((new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        String mType = moreEntities.get(position).getType();
                        gotoTypeListActivity(mType);
                    }
                }));
        more.addOnScrollListener(recyclerScrollListener);

    }
    public void gotoTypeListActivity(String type){
        Router.newIntent(getActivity())
                .to(GankTypeListActivity.class)
                .putString(Constant.INTENT_FLAG_TYPE,type)
                .launch();
    }
    private RecyclerScrollListener recyclerScrollListener = new RecyclerScrollListener() {
        @Override
        public void hideToolBar() {

        }

        @Override
        public void showToolBar() {


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

package com.android.xgank.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.mvp.mvp.LazyFragment;
import com.android.mvp.mvp.XLazyFragment;
import com.android.xgank.R;
import com.android.xgank.presenter.CategoryPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends XLazyFragment<CategoryPresenter> {


    private static final String CATEGORY_NAME = "com.android.xgank.ui.fragments.CategoryFragment" ;

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

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    public CategoryPresenter newP() {
        return null;
    }

    @Override
    public boolean canBack() {
        return false;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

}

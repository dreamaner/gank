package com.android.xgank.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.mvp.mvp.XLazyFragment;
import com.android.xgank.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OwnFragment extends XLazyFragment {


    public OwnFragment() {
        // Required empty public constructor
    }

    @Override
    public boolean canBack() {
        return false;
    }

    public static OwnFragment getInstance(){
        OwnFragment meFragment=new OwnFragment();

        return meFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_own, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public Object newP() {
        return null;
    }
}

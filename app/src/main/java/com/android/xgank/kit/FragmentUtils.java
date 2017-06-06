package com.android.xgank.kit;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.android.xgank.R;
import com.android.xgank.bean.Constant;
import com.android.xgank.ui.fragments.HomeFragment;
import com.android.xgank.ui.fragments.MoreFragment;
import com.android.xgank.ui.fragments.OwnFragment;


/**
 * Created by dreamaner on 2017/5/17.
 */

public class FragmentUtils {
    private HomeFragment homeFragment;
    private MoreFragment moreFragment;
    private OwnFragment meFragment;
    private FragmentManager mManager;
    private ActionBar actionBar;
    private FragmentTransaction mTransaction;
    private Context context;
    public static FragmentUtils instance;
    public FragmentUtils(AppCompatActivity activity){
        this.context=activity;
        mManager=activity.getSupportFragmentManager();
        actionBar=activity.getSupportActionBar();
    }

    public void initFragment(String name){
        mTransaction=mManager.beginTransaction();

        hideFragments(mTransaction);
        switch (name){
            case Constant.HOME:
//                actionBar.setTitle(HomeFragment.currentTitle);
                if (homeFragment==null){
                    homeFragment=HomeFragment.getInstance();
                    mTransaction.add(R.id.fragment_content,homeFragment);
                }else
                    mTransaction.show(homeFragment);
                break;
            case Constant.MORE:
//                actionBar.setTitle(R.string.title_more);
                if (moreFragment==null){
                    moreFragment=MoreFragment.getInstance();
                    mTransaction.add(R.id.fragment_content,moreFragment);
                }else
                    mTransaction.show(moreFragment);
                break;
            case Constant.ME:
//                actionBar.setTitle(R.string.title_me);
                if (meFragment==null){
                    meFragment=OwnFragment.getInstance();
                    mTransaction.add(R.id.fragment_content,meFragment);
                }else
                    mTransaction.show(meFragment);
                break;

            default:
                return;
        }
        mTransaction.commit();
    }
    public static FragmentUtils getInstance(AppCompatActivity activity){
          if (instance == null){
              instance = new FragmentUtils(activity) ;
          }
          return instance;
    }
    private void hideFragments(FragmentTransaction mTransaction) {
        if (homeFragment!=null) mTransaction.hide(homeFragment);
        if (moreFragment!=null) mTransaction.hide(moreFragment);
        if (meFragment!=null) mTransaction.hide(meFragment);

    }

}

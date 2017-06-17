package com.android.xgank.ui.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.kit.view.photoview.PhotoView;
import com.android.xgank.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Dreamaner on 2017/5/15.
 */

public class PhotoAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<String> mDatas;


    public PhotoAdapter(Context context, ArrayList<String> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view= LayoutInflater.from(context).inflate(R.layout.photo_item_layout,container,false);
        PhotoView photoView= (PhotoView) view.findViewById(R.id.photo_pv);
        Glide.with(context).load(mDatas.get(position)).into(photoView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }


}

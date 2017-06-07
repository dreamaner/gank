package com.android.xgank.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.android.kit.utils.screen.ScreenUtils;
import com.android.kit.view.recycleview.BaseItemDraggableAdapter;
import com.android.kit.view.recycleview.BaseViewHolder;
import com.android.mvp.base.SimpleRecAdapter;
import com.android.mvp.recycleview.XRecyclerAdapter;
import com.android.xgank.R;
import com.android.xgank.bean.MoreEntity;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by yury on 2017/6/7.
 */

public class MoreAdapter extends BaseItemDraggableAdapter<MoreEntity,BaseViewHolder> {
    private Context context;
    public MoreAdapter(int layoutResId, List<MoreEntity> data, Context context) {
        super(layoutResId, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MoreEntity item) {
        helper.setText(R.id.gankitemmore_type_tv, item.getType());
        ImageView iv = helper.getView(R.id.gankitemmore_photo_iv);
        float width = ScreenUtils.getScreenWidth(context);
        int halfW = (int) (width / 2);
        Glide.with(context).load(item.getResId()).override(halfW, halfW).into(iv);
    }
}

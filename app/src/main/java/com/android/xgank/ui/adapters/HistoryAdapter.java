package com.android.xgank.ui.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.mvp.base.SimpleRecAdapter;
import com.android.xgank.R;
import com.android.xgank.bean.History;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yury on 2017/6/16.
 */

public class HistoryAdapter extends SimpleRecAdapter<History,HistoryAdapter.ViewHolder> {
    public static final int TAG_VIEW = 0;

    public HistoryAdapter(Context context) {
        super(context);
    }

    @Override
    public HistoryAdapter.ViewHolder newViewHolder(View itemView) {
        return new HistoryAdapter.ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_history;
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.ViewHolder holder, int position) {
           History history = data.get(position);
           holder.tvItemContentHistory.setText(history.getContent());

           holder.itemView.setOnClickListener(v -> {
            if (getRecItemClick() != null) {
                getRecItemClick().onItemClick(position, history, TAG_VIEW, holder);
            }
        });
    }

   public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_content_history)
        AppCompatTextView tvItemContentHistory;

       public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

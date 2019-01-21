package com.cy.view.popupwindow;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.cy.utilandroid.R;
import com.pacific.adapter.BaseRecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;

import java.util.List;

public class PopUpAdapter extends BaseRecyclerAdapter<PopupItem> {
    private OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(int postion);
    }
    public PopUpAdapter(RecyclerView recyclerView, @Nullable List<PopupItem> data) {
        super(recyclerView, data);
    }

    public PopUpAdapter(RecyclerView recyclerView, @Nullable List<PopupItem> data, OnItemClickListener onItemClickListener) {
        super(recyclerView, data);
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    protected void convert(RecyclerAdapterHelper helper, PopupItem item, final int pos, int viewTypeAndLayoutRes, RecyclerView.ViewHolder holder) {
        helper.setText(R.id.mtvPopText,item.itemName);
        if (!TextUtils.isEmpty(item.itemIcon)) {
            helper.setImageUrl(R.id.mivPopIcon,item.itemIcon);
        }
        if (mOnItemClickListener != null) {
            helper.getItemView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(pos);
                }
            });
        }
    }

    @Override
    protected int getLayoutResAsItemViewType(int position) {
        return R.layout.item_popupwindow;
    }
}

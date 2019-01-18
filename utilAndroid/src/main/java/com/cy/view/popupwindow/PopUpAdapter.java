package com.cy.view.popupwindow;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.cy.utilandroid.R;
import com.pacific.adapter.BaseRecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;

import java.util.List;

public class PopUpAdapter extends BaseRecyclerAdapter<PopupItem> {

    public PopUpAdapter(RecyclerView recyclerView, @Nullable List<PopupItem> data) {
        super(recyclerView, data);
    }

    @Override
    protected void convert(RecyclerAdapterHelper helper, PopupItem item, int pos, int viewTypeAndLayoutRes) {
        helper.setText(R.id.mtvPopText,item.itemName);
        if (!TextUtils.isEmpty(item.itemIcon)) {
            helper.setImageUrl(R.id.mivPopIcon,item.itemIcon);
        }
    }

    @Override
    protected int getLayoutResAsItemViewType(int position) {
        return R.layout.item_popupwindow;
    }
}

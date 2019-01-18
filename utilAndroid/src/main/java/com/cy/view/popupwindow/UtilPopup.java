package com.cy.view.popupwindow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.cy.app.UtilContext;
import com.cy.data.UtilCollection;
import com.cy.utilandroid.R;
import com.pacific.adapter.util.HorizontalItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**usage:
 EasyPopup mQQPop = EasyPopup.create()
 .setContentView(R.layout.layout_right_pop)
 .setAnimationStyle(R.style.RightTop2PopAnim)
 .setOnViewListener(new EasyPopup.OnViewListener() {
@Override
public void initViews(View view, EasyPopup basePopup) {
View arrowView = view.findViewById(R.id.v_arrow);
arrowView.setBackground(new TriangleDrawable(TriangleDrawable.TOP, Color.parseColor("#88FF88")));
}
})
 .setFocusAndOutsideEnable(true)
 //                .setBackgroundDimEnable(true)
 //                .setDimValue(0.5f)
 //                .setDimColor(Color.RED)
 //                .setDimView(mTitleBar)
 .apply();

 }

 private void showQQPop(View view) {
 int offsetX = SizeUtils.dp2px(20) - view.getWidth() / 2;
 int offsetY = (mTitleBar.getHeight() - view.getHeight()) / 2;
 mQQPop.showAtAnchorView(view, YGravity.BELOW, XGravity.ALIGN_RIGHT, offsetX, offsetY);
 }
 */
public class UtilPopup extends EasyPopup {

    private List<PopupItem> mPopupItems=new ArrayList<>();

    private UtilPopup() {
    }

    private UtilPopup(Context context) {
        super(context);
    }

    public UtilPopup setPopupItems(List<PopupItem> popupItems) {
        if (UtilCollection.notEmpty(popupItems)) {
            mPopupItems.addAll(popupItems);
        }
        return this;
    }

    public UtilPopup addItem(PopupItem popupItem){
        mPopupItems.add(popupItem);
        return this;
    }
    private List<PopupItem> getPopupItems() {
        return mPopupItems;
    }

    public static UtilPopup create() {
        UtilPopup easyPopup= new UtilPopup(UtilContext.getContext());
        View view= LayoutInflater.from(UtilContext.getContext()).inflate(R.layout.popupwindow,null);
        RecyclerView recyclerView=view.findViewById(R.id.mRecyclerView);
        recyclerView.addItemDecoration(new HorizontalItemDecoration.Builder(UtilContext.getContext()).build());
        new PopUpAdapter(recyclerView,easyPopup.getPopupItems());
        easyPopup.setContentView(view);
        return easyPopup;
    }

}

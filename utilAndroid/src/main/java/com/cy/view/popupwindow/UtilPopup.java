package com.cy.view.popupwindow;

import com.cy.app.UtilContext;

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

    public static EasyPopup create() {
        return new EasyPopup(UtilContext.getContext());
    }

}

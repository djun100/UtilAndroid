package com.cy.view.popupwindow;

import android.content.Context;
import android.view.View;

public abstract class EasyPopup<T extends EasyPopup> extends BasePopup<T> {

    private OnViewListener mOnViewListener;

    public EasyPopup() {
    }

    public EasyPopup(Context context) {
        setContext(context);
    }

    @Override
    protected void initAttributes() {

    }

    @Override
    protected void initViews(View view, EasyPopup popup) {
        if (mOnViewListener != null) {
            mOnViewListener.initViews(view, popup);
        }
    }

    public T setOnViewListener(OnViewListener listener) {
        this.mOnViewListener = listener;
        return (T) this;
    }

    public interface OnViewListener {

        void initViews(View view, EasyPopup popup);
    }
}

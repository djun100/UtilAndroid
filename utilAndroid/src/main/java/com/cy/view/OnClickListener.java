package com.cy.view;

import android.view.View;

public abstract class OnClickListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        if (!UtilClick.isFastClick()){
            return;
        }
        onClick_(v);
    }

    public abstract void onClick_(View v);
}

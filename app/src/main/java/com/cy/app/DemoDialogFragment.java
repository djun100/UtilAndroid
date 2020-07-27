package com.cy.app;


import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.cy.container.BaseDialogFragment;
import com.cy.view.UtilDialog;

public class DemoDialogFragment extends BaseDialogFragment {

    @Override
    protected Dialog onCreateDialog(int defaultStyle) {
//    return UtilDialog.newAlertDialogBuilder(getActivity())
//            .setContentRes(R.string.app_name)
//            .setCancelRes(android.R.string.cancel)
//            .setConfirmRes(android.R.string.ok)
//            .setCanceledOnTouchOutside(false)
//            .build();

        View view=LayoutInflater.from(getActivity()).inflate(R.layout.dialogfragment_demo,null);
        return UtilDialog.newDialogBuilder(getActivity())
                .setLayout(R.layout.dialogfragment_demo)
                .setCustomView(view)
//                .setCanceledOnTouchOutside(false)
                .setMatchHorizontalParent()
                .setGravity(Gravity.BOTTOM)
                .build();
    }

    public static DemoDialogFragment newInstance(){
        return new DemoDialogFragment();
    }
}

package com.cy.host;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.cy.utilandroid.R;

public class DemoDialogFragment extends BaseDialogFragment {

    @Override
    protected Dialog onCreateDialog(int defaultStyle) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View view = inflater.inflate(R.layout.fragment_upgrade_dialog, null);
        Dialog dialog = new Dialog(getActivity(), defaultStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    public static DemoDialogFragment newInstance(){
        return new DemoDialogFragment();
    }
}

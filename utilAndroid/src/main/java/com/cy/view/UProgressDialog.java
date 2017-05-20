package com.cy.view;

import android.app.ProgressDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.cy.app.UContext;

/**
 * Created by Administrator on 2016/12/15.
 */

public class UProgressDialog {
    static ProgressDialog pd = null; // 进度条对话框

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public static void showDownloadProgressDialog(String title, int current, int total){

        if (pd==null) {
            pd = new ProgressDialog(UContext.getContext());
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setMessage(title);
            pd.setCanceledOnTouchOutside(false);
            pd.setCancelable(false);
            pd.show();
        }

        pd.setMax(total);
        pd.setProgress(current);
        pd.setProgressNumberFormat(String.format("%.2fM/%.2fM"
                ,(float)current/1024/1024
                ,(float)total/1024/1024));
        pd.dismiss();
    }
}

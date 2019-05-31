package com.cy.file;

import android.app.Activity;

public class UtilSelectFile {

    public interface OnSelectListener {
        void onSelected(String filePath);
    }

    public static void chooseFile(Activity activity,OnSelectListener onSelectListener){
        SelectFileActivity.startActivity(activity,onSelectListener);
    }

    public static void chooseFile(Activity activity,int type,OnSelectListener onSelectListener){
        SelectFileActivity.startActivity(activity,type,onSelectListener);
    }
}

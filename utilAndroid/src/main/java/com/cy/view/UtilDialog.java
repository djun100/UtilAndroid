package com.cy.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.WindowManager;

public class UtilDialog {

    public static class Builder{
        private int title;
        private int content;
        private int confirm;
        private int cancel;
        private int iconResId;
        private boolean isOutOfActivity;

        public static Builder newInstance(){
            return new Builder();
        }

        public int getTitle() {
            return title;
        }

        public Builder setTitle(int title) {
            this.title = title;
            return this;
        }

        public int getContent() {
            return content;
        }

        public Builder setContent(int content) {
            this.content = content;
            return this;
        }

        public int getConfirm() {
            return confirm;
        }

        public Builder setConfirm(int confirm) {
            this.confirm = confirm;
            return this;
        }

        public int getCancel() {
            return cancel;
        }

        public Builder setCancel(int cancel) {
            this.cancel = cancel;
            return this;
        }

        public int getIconResId() {
            return iconResId;
        }

        public Builder setIconResId(int iconResId) {
            this.iconResId = iconResId;
            return this;
        }

        public boolean getIsOutOfActivity() {
            return isOutOfActivity;
        }

        public Builder setIsOutOfActivity(boolean outOfActivity) {
            isOutOfActivity = outOfActivity;
            return this;
        }
    }
    /**系统疑问对话框 右边确定
     * @param listener
     */
    public static void showDialogAsk(Context context, Builder builder, DialogInterface.OnClickListener listener) {

        AlertDialog.Builder customBuilder = new AlertDialog.Builder(context);
        if (builder.getTitle()>0) {
            customBuilder.setTitle(builder.getTitle());
        }
        if (builder.getIconResId()>0) {
            customBuilder.setIcon(builder.getIconResId());
        }
        if (builder.getContent()>0) {
            customBuilder.setMessage(builder.getContent());
        }
        if (builder.getConfirm()>0) {
            customBuilder.setPositiveButton(builder.getConfirm(), listener);
        }
        if (builder.getCancel()>0) {
            customBuilder.setNegativeButton(builder.getCancel(), null);
        }
        AlertDialog dialog = customBuilder.create();
        if (builder.getIsOutOfActivity()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //8.0悬浮窗权限适配
                dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
//            mType = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//            mType = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
            }

        }
        dialog.show();
    }
}

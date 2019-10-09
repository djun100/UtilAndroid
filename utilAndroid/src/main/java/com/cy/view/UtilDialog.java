package com.cy.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.cy.utilandroid.R;

/**
 * AlertDialog：Dialog的子类，宽度无法填充，自带取消、确定按钮，内容列表项，标题，标题图标，内容
 * Dialog：宽度可以填充，无取消确定按钮
 * https://www.jianshu.com/p/b48da813acf5 自定义view样式竖线分隔按钮
 */
public class UtilDialog {

    /**
     * @param context 创建dialog的上下文，如果只需要在activity内显示，则上下文需要设置为activity，如果需要可以脱离activity显示，则可以传application级别上下文
     * @return
     */
    public static AlertDialogBuilder newAlertDialogBuilder(Context context){
        AlertDialogBuilder alertDialogBuilder =new AlertDialogBuilder();
        alertDialogBuilder.context=context;
        return alertDialogBuilder;
    }

    /**
     * @param context 创建dialog的上下文，如果只需要在activity内显示，则上下文需要设置为activity，如果需要可以脱离activity显示，则可以传application级别上下文
     * @return
     */
    public static DialogBuilder newDialogBuilder(Context context){
        DialogBuilder dialogBuilder=new DialogBuilder();
        dialogBuilder.context=context;
        return dialogBuilder;
    }

    /**
     * 系统疑问对话框 右边确定
     */
    public static class AlertDialogBuilder {
        private Context context;
        private int titleRes;
        private int contentRes;
        private int confirmRes;
        private int cancelRes;
        private int iconRes;
        /**
         *需要申请悬浮窗权限
         */
        private boolean isOutOfActivity;
        private View view;
        private DialogInterface.OnClickListener positiveListener;
        private DialogInterface.OnClickListener negativeListener;
        private Boolean canceledOnTouchOutside;

        public AlertDialogBuilder setTitleRes(int titleRes) {
            this.titleRes = titleRes;
            return this;
        }

        public AlertDialogBuilder setContentRes(int contentRes) {
            this.contentRes = contentRes;
            return this;
        }

        public AlertDialogBuilder setConfirmRes(int confirmRes) {
            this.confirmRes = confirmRes;
            return this;
        }


        public AlertDialogBuilder setCancelRes(int cancelRes) {
            this.cancelRes = cancelRes;
            return this;
        }


        public AlertDialogBuilder setIconRes(int iconRes) {
            this.iconRes = iconRes;
            return this;
        }


        /**需要申请悬浮窗权限
         * @param outOfActivity
         * @return
         */
        public AlertDialogBuilder setIsOutOfActivity(boolean outOfActivity) {
            isOutOfActivity = outOfActivity;
            return this;
        }

        public AlertDialogBuilder setPositiveListener(DialogInterface.OnClickListener positiveListener) {
            this.positiveListener = positiveListener;
            return this;
        }

        public AlertDialogBuilder setNegativeListener(DialogInterface.OnClickListener negativeListener) {
            this.negativeListener = negativeListener;
            return this;
        }

        public AlertDialogBuilder setCanceledOnTouchOutside(Boolean canceledOnTouchOutside) {
            this.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public AlertDialogBuilder setView(View view) {
            this.view = view;
            return this;
        }

        public AlertDialog build(){
            AlertDialog.Builder customBuilder = new AlertDialog.Builder(context);
            if (titleRes>0) {
                customBuilder.setTitle(titleRes);
            }
            if (iconRes>0) {
                customBuilder.setIcon(iconRes);
            }
            if (contentRes>0) {
                customBuilder.setMessage(contentRes);
            }
            if (confirmRes>0) {
                customBuilder.setPositiveButton(confirmRes, positiveListener);
            }
            if (cancelRes>0) {
                customBuilder.setNegativeButton(cancelRes, negativeListener);
            }
            if (view!=null){
                customBuilder.setView(view);
            }

            AlertDialog dialog = customBuilder.create();

            if (canceledOnTouchOutside!=null) {
                dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
            }

            if (isOutOfActivity){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    //8.0悬浮窗权限适配
                    dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
//            mType = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                } else {
                    dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR);
//            mType = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
                }

            }

            return dialog;
        }
    }

    public static class DialogBuilder{
        private Context context;
        private boolean isOutOfActivity;
        private boolean matchParent;
        private Integer gravity;
        private Integer layout;
        private View customView;
        private Integer style= R.style.base_dialog;
//        private DialogInterface.OnClickListener positiveListener; //no define
//        private DialogInterface.OnClickListener negativeListener; //no define
        private Boolean canceledOnTouchOutside;

        public DialogBuilder setOutOfActivity(boolean outOfActivity) {
            isOutOfActivity = outOfActivity;
            return this;
        }

        public DialogBuilder setMatchParent() {
            this.matchParent = true;
            return this;
        }

        public DialogBuilder setGravity(Integer gravity) {
            this.gravity = gravity;
            return this;
        }

        public DialogBuilder setLayout(Integer layout) {
            this.layout = layout;
            return this;
        }

        public DialogBuilder setCanceledOnTouchOutside(Boolean canceledOnTouchOutside) {
            this.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public DialogBuilder setStyle(Integer style) {
            this.style = style;
            return this;
        }

        public DialogBuilder setCustomView(View customView) {
            this.customView = customView;
            return this;
        }

        public Dialog build(){
            Dialog dialog = new Dialog(context, style);
            //style中设置动画的话，这样写才生效
            dialog.getWindow().setWindowAnimations(style);
            if (layout!=null){
                dialog.setContentView(layout);
            }else if (customView!=null){
                dialog.setContentView(customView);
            }
            if (canceledOnTouchOutside!=null) {
                dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
            }
            if (matchParent){
                setWidthFull(dialog);
            }
            if (gravity!=null){
                setLocation(dialog,gravity);
            }
            if (isOutOfActivity){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    //8.0悬浮窗权限适配
                    dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
//            mType = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                } else {
                    dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR);
//            mType = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
                }
            }
            return dialog;
        }

        /**
         * 设置宽度填充屏幕
         * 适合在子类的onViewCreated()中调用
         */
        private void setWidthFull(Dialog dialog){
            Window win = dialog.getWindow();
            win.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            win.setAttributes(lp);
        }

        /**设置dialog位置<br>
         * 适合在子类的onViewCreated()中调用
         * eg:Gravity.LEFT | Gravity.TOP Gravity.BOTTOM
         * @param gravity
         */
        private void setLocation(Dialog dialog,int gravity){
            dialog.getWindow().setGravity(gravity);
        }
    }


}

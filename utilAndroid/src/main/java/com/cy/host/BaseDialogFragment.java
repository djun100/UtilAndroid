package com.cy.host;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.cy.utilandroid.R;

/**<p/><b>Tips:</b></p>
 * <li><b>子dialog布局必须包含RelativeLayout或直接以之为根布局，然后内部内容match_parent才能撑开dialog宽度</b></li>
 * <li><b>baseFind dialog的子视图，使用dialog.findViewById()</b></li>
 * <p/><b>Usage：</b></p>
 * <pre>
 * SampleDialogFragment fragment = new SampleDialogFragment();
   fragment.show(getSupportFragmentManager(), "blur_sample");//fragment中        flagFrom= getTag();可以用作参数传递
 </pre>  
   可用资源：<li>host_basedialog_background_shape.xml 圆角背景shape </li>         <br>
                
 * 对话框有两种类型的可供使用，一种是Dialog，另一种则是Android 3.0 引入的基于Fragment的DialogFragment。
 * 
 * 从代码的编写角度看，Dialog使用起来要更为简单，但是Google则是推荐尽量使用DialogFragment（对于Android
 * 3.0以下的版本，可以结合使用support包中提供的DialogFragment以及FragmentActivity
 * ）。今天试着用这两种方式来创建对话框，
 * 发现DialogFragment果然有一个非常好的特性（在手机配置变化，导致Activity需要重新创建时，例如旋屏，
 * 基于DialogFragment的对话框将会由FragmentManager自动重建，然而基于Dialog实现的对话框则没有这样的能力）。
 * DialogFragment还拥有fragment的优点，即可以在一个Activity内部实现回退（因为FragmentManager会管理一个回退栈） 
 * activity 关闭时，dialog不关闭也不会报错，activity自动管理<br>
 *
 *建议使用UtilDialog创建dialog
 @author 承影
 */
public abstract class BaseDialogFragment extends DialogFragment {
    protected Dialog dialog;

    @NonNull
    @Override
    final public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = onCreateDialog(R.style.host_dialog);
        return dialog;
    }

    protected abstract Dialog onCreateDialog(int defaultStyle);

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag).addToBackStack(null);
            ft.commitAllowingStateLoss();
        } catch (IllegalStateException e) {
        }
    }

    /**禁止子类重写，因为使用了onCreateDialog的方式，该方法就不会被自动调用
     * @param view
     * @param savedInstanceState
     */
    @Override
     final public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

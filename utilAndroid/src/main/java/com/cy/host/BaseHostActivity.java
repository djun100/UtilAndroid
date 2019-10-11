package com.cy.host;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.cy.host.onActivityResult2.OnActivityResultManager;
import com.cy.io.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

/**使用帮助
 * 本类重写了所有setContentView方法，内部后续会自动调用baseInit1Data();baseInitView();这两个抽象方法
 * according to init 1、2、3 to do the workflow
 * feature:both Activity and FragmentActivity are compatible;
 * both using layout file or custom view in java to set content view are supported;
 * show class name in Logi when onCreate.
 *
 * baseInit3PullData() 有时候需要在onResume中才出发，所以去掉该方法
 */
public abstract class BaseHostActivity extends FragmentActivity{
	public FragmentActivity mActivity;
	private boolean isEnableExitAppByDobbleClick;

	// TODO: 2016/11/22 glide 使用封装
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//默认只允许竖屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		mActivity = this;
//		UtilStatusBar.setStatusBarFontDark(mActivity);
		Log.w(getClass().getName());//log显示页面记录
	}

	@Override
	protected void onResume() {
		super.onResume();
//		initSystemBar();
	}

	/**<pre>沉浸式状态栏(不适用于近似白色的沉浸，因为默认系统状态栏文字颜色为白色)
	 * 小米、魅族、Android6.0支持： https://www.zhihu.com/question/31994153
	 * 必须在每个跟布局加 android:fitsSystemWindows="true  android:clipToPadding="false"" 属性，不能在theme中统一设置
	 *
	 */
	public void baseInitSystemBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			baseSetTranslucentStatus(true);
		}
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		// 使用颜色资源
		tintManager.setStatusBarTintResource(android.R.color.white);
	}

	@TargetApi(19)
	private void baseSetTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}


	private boolean mIsEventBusEnable;

	/**will auto unregister in onDestroy()*/
	protected void baseEnableEventbus(){
		mIsEventBusEnable = true;
		try {
			EventBus.getDefault().register(this);
		} catch (Exception e) {
		}
	}

	@Override
	protected void onDestroy() {
		mActivity=null;
		super.onDestroy();
		if (mIsEventBusEnable){
			try {
				EventBus.getDefault().unregister(this);
			} catch (Exception e) {
			}
		}
	}

	/**页面执行流程1：页面所需数据初始化*/
	protected void baseInitData(){}
	/**页面执行流程2：view后序处理*/
	protected void baseInitView(){}

	@Override
	public void onBackPressed() {
		if (isEnableExitAppByDobbleClick){
			baseExitAppByDobleClick();
		}else {
			super.onBackPressed();
		}
	}

	/**开关双击退出程序功能
	 * @param b
	 */
	public void baseEnableExitAppByDoubleBack(boolean b){
		isEnableExitAppByDobbleClick = b;
	}
	private static Boolean isQuit = false;
	private static Timer timer;


	private void baseExitAppByDobleClick() {
		if (isQuit == false) {
			isQuit = true;
			Toast.makeText(this, "再次点击确定退出软件", Toast.LENGTH_SHORT).show();
			TimerTask task = null;
			task = new TimerTask() {
				@Override
				public void run() {
					isQuit = false;
				}
			};
			if (timer == null) {
				timer = new Timer();
			}
			timer.schedule(task, 2000);
		} else {
			System.exit(0);
		}
	}
	public void baseStartActivity(Class clazz){
		startActivity(new Intent(this,clazz));
	}

	private boolean hasCallFinish;
	@Override
	public void finish() {
		if (hasCallFinish){
			super.finish();
		}else {
			hasCallFinish=true;
		}
	}

	//block to use AppManager end
	protected void baseAddOnGlobalLayoutListener(final OnGlobalLayoutListener onGlobalLayoutListener){
		final View v=getWindow().getDecorView();
		ViewTreeObserver vto =v.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				v.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				onGlobalLayoutListener.onGlobalLayout();
			}
		});
	}


	protected interface OnGlobalLayoutListener{
		void onGlobalLayout();
	}

	private OnActivityResultManager mOnActivityResultManager;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		mOnActivityResultManager.onActivtyResult(requestCode, resultCode, data);
	}

	public void baseStartActivityForResult(Intent intent, int requestCode
			, OnActivityResultManager.OnActivityResultCallBack callBack) {
		if (mOnActivityResultManager==null){
			mOnActivityResultManager=new OnActivityResultManager(this);
		}
		mOnActivityResultManager.startActivityForResult(intent,requestCode,callBack);
	}
}

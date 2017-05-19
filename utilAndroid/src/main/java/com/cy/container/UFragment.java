package com.cy.container;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class UFragment {
	/**往容器内填充fragment
	 * @param container R.id.content_container
	 * @param fragment
	 * @param Activity
	 */
	public static void replaceFragment(int container,Fragment fragment,String tag,Activity activity){
		FragmentManager mFM =activity.getFragmentManager();
		FragmentTransaction ft = mFM.beginTransaction();
		ft.replace(container, fragment,tag);
		ft.commit();
	}
	public static void replaceFragment(int container,Fragment fragment,Activity activity){
		replaceFragment(container,fragment,"",activity);
	}
}

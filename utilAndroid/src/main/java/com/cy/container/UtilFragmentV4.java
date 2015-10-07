package com.cy.container;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class UtilFragmentV4 {
	/**往容器内填充fragment
	 * @param container R.id.content_container
	 * @param fragment
	 * @param fragmentActivity
	 */
	public static void replaceFragment(int container,Fragment fragment,String tag,FragmentActivity fragmentActivity){
		FragmentManager mFM =fragmentActivity.getSupportFragmentManager();
		FragmentTransaction ft = mFM.beginTransaction();
		ft.replace(container, fragment,tag);
		ft.commit();
	}
	public static void replaceFragment(int container,Fragment fragment,FragmentActivity fragmentActivity){
		replaceFragment(container,fragment,"",fragmentActivity);
	}
}

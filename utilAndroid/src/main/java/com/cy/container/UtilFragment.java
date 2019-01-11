package com.cy.container;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class UtilFragment {
	/**往容器内填充fragment
	 * @param container R.id.content_container
	 * @param fragment
	 * @param fragmentActivity
	 */
	public static void replaceFragment(int container,Fragment fragment,String tag,FragmentActivity fragmentActivity){
		fragmentActivity.getSupportFragmentManager().beginTransaction().replace(container, fragment,tag).commit();
	}
	public static void replaceFragment(int container,Fragment fragment,FragmentActivity fragmentActivity){
		replaceFragment(container,fragment,"",fragmentActivity);
	}
}

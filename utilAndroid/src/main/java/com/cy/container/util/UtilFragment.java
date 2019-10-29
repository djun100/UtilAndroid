package com.cy.container.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class UtilFragment {
	/**往容器内填充fragment
	 * @param container R.id.content_container
	 * @param fragment
	 * @param fragmentActivity
	 */
	private static void replaceFragment(int container, Fragment fragment, String tag, FragmentActivity fragmentActivity){
		fragmentActivity.getSupportFragmentManager().beginTransaction().replace(container, fragment,tag).commit();
	}

	private static void replaceFragment(int container, Fragment fragment, FragmentActivity fragmentActivity){
		replaceFragment(container,fragment,"",fragmentActivity);
	}

	public static void hideFragment(Fragment fragment, FragmentActivity fragmentActivity){
		fragmentActivity.getSupportFragmentManager().beginTransaction().hide(fragment).commit();
	}

	public static void showFragment(int container, Fragment fragment, FragmentActivity fragmentActivity){
		if (fragment.isAdded()){
			fragmentActivity.getSupportFragmentManager().beginTransaction().show(fragment).commit();
		}else {
			replaceFragment(container, fragment, fragmentActivity);
		}
	}
}

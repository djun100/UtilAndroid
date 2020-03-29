package com.cy.container.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class UtilFragment {
	/**往容器内填充fragment，不建议使用该方法，会使下面的工具方法按tag:container+""找fragment失效
	 * @param container R.id.content_container
	 * @param fragment
	 * @param fragmentActivity
	 */
	@Deprecated
	private static void replaceFragment(int container, Fragment fragment, String tag, FragmentActivity fragmentActivity){
		fragmentActivity.getSupportFragmentManager().beginTransaction().replace(container, fragment,tag).commit();
	}

	private static void replaceFragment(int container, Fragment fragment, FragmentActivity fragmentActivity){
		replaceFragment(container,fragment,container+"",fragmentActivity);
	}

	public static void hideFragment(Fragment fragment, FragmentActivity fragmentActivity){
		fragmentActivity.getSupportFragmentManager().beginTransaction().hide(fragment).commit();
	}

	public static void showFragment(int container, Fragment fragment, FragmentActivity fragmentActivity){
		if (fragment.isAdded()){
			fragmentActivity.getSupportFragmentManager().beginTransaction().show(fragment).commit();
		}else {
			Fragment fragmentOri = fragmentActivity.getSupportFragmentManager().findFragmentByTag(container+"");
			if (fragmentOri==null){
				//该container中没有其他fragment，不需要replace
				fragmentActivity.getSupportFragmentManager().beginTransaction().add(container,fragment,container+"").commit();
			}else {
				//该container中有其他fragment，需要replace
				replaceFragment(container, fragment, fragmentActivity);
			}
		}
	}
}

package com.cy.container.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.cy.app.BaseConstants;
import com.cy.io.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * activity fragment都可以作为fragment的容器，都有自己的FragmentManager，所以传参要传FragmentManager
 */
public class UtilFragment {
	private static WeakHashMap<Fragment,Integer> fras = new WeakHashMap<>();

	/**往容器内填充fragment，不建议使用该方法，会使下面的工具方法按tag:container+""找fragment失效
	 * @param container R.id.content_container
	 * @param fragment
	 */
	@Deprecated
	private static void replaceFragment(int container, Fragment fragment, String tag, FragmentManager fragmentManager){
		fragmentManager.beginTransaction().replace(container, fragment,tag).commit();
	}

	private static void replaceFragment(int container, Fragment fragment, FragmentManager fragmentManager){
		replaceFragment(container,fragment,container+"",fragmentManager);
	}

	public static void hideFragment(Fragment fragment, FragmentManager fragmentManager){
		fragmentManager.beginTransaction().hide(fragment).commit();
	}

	public static void showFragment(int container, Fragment fragment, FragmentManager fm){
		Fragment topFra=getTopShow(container);
		FragmentTransaction transaction = fm.beginTransaction();
		if (topFra!=null){
			transaction.hide(topFra);
			Log.i("隐藏当前topFra:"+topFra);
		}

		if (fragment.isAdded()){
			Log.i("之前add过，show fra "+fragment);
			transaction.show(fragment);

		} else {
			Log.i(BaseConstants.TAG_LIFECYCLE,"add进fra " + fragment);
			transaction.add(container, fragment, null);
			fras.put(fragment,container);
		}
		transaction.commit();
	}

	private static Fragment getTopShow(int container) {
		List<Fragment> fragments = getFragments(container);
		for (int i = fragments.size() - 1; i >= 0; --i) {
			Fragment fragment = fragments.get(i);
			if (fragment != null
					&& fragment.isResumed()
					&& fragment.isVisible()
					&& fragment.getUserVisibleHint()) {

					return fragment;
			}
		}
		return null;
	}


	public static List<Fragment> getFragments(int container) {
		List<Fragment> fragments = new ArrayList<>();
		for (Map.Entry<Fragment, Integer> entry : fras.entrySet()) {
			if (entry.getValue() == container) {
				fragments.add(entry.getKey());
			}
		}
		return fragments;
	}

}

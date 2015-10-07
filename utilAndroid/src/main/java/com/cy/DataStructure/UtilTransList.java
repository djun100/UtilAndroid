package com.cy.DataStructure;

import java.util.ArrayList;

public class UtilTransList {
	/** 泛型数组T[] 转 ArrayList<T>
	 * @param sa
	 * @return
	 */
	public static <T> ArrayList<T> transArrayToArrayList(T[] sa) {
		ArrayList<T> als = new ArrayList<T>(0);
		for (int i = 0; i < sa.length; i++) {
			als.add(sa[i]);
		}
		return als;
	}
}
 
package com.cy.DataStructure;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 *  
 */  
public class UtilList {

	public  static <E> boolean notEmpty(List<E> list){
		return list!=null && list.size()>0;
	}

	public static <E> List<E> convert_arrayToList(E[] array){

		List<E> userList = new ArrayList<E>();

		if (array==null||array.length==0){
			return userList;
		}

		Collections.addAll(userList, array);
		return userList;
	}

	public static <E> E[] convert_listToArray(List<E> list){
		if (list==null || list.size()==0){
			return null;
		}
		E[] array = (E[]) list.toArray();
		return array;
	}

	/**
	 * 自定义排序，专门针对列表（List）中的数据进行排序；可按指定方法进行。
	 * 目前实现对字符串（String）、日期（Date）、整型（Integer）等三种对象进行排序。
	 * 对列表中的数据按指定字段进行排序。要求类必须有相关的方法返回字符串、整型、日期等值以进行比较。
	 * @param list
	 * @param method eg:getUsername
	 * @param bigToLittle
	 */
	public  static <E> void sortByMethod(List<E> list, final String method, final boolean bigToLittle) {
		Collections.sort(list, new Comparator<Object>() {
			@SuppressWarnings("unchecked")
			public int compare(Object arg1, Object arg2) {
				int result = 0;
				try {
					Method m1 = ((E) arg1).getClass().getMethod(method, null);
					Method m2 = ((E) arg2).getClass().getMethod(method, null);
					Object obj1 = m1.invoke(((E)arg1), null);
					Object obj2 = m2.invoke(((E)arg2), null);
					if(obj1 instanceof String) {
						// 字符串
						result = obj1.toString().compareTo(obj2.toString());
					}else if(obj1 instanceof Date) {
						// 日期
						long l = ((Date)obj1).getTime() - ((Date)obj2).getTime();
						if(l > 0) {
							result = 1;
						}else if(l < 0) {
							result = -1;
						}else {
							result = 0;
						}
					}else if(obj1 instanceof Integer) {
						// 整型（Method的返回参数可以是int的，因为JDK1.5之后，Integer与int可以自动转换了）
						result = (Integer)obj1 - (Integer)obj2;
					}else {
						// 目前尚不支持的对象，直接转换为String，然后比较，后果未知
						result = obj1.toString().compareTo(obj2.toString());

						System.err.println("UtilSortList.sortByMethod方法接受到不可识别的对象类型，转换为字符串后比较返回...");
					}

					if (bigToLittle) {
						// 倒序
						result = -result;
					}
				} catch (NoSuchMethodException nsme) {
					nsme.printStackTrace();
				} catch (IllegalAccessException iae) {
					iae.printStackTrace();
				} catch (InvocationTargetException ite) {
					ite.printStackTrace();
				}

				return result;
			}
		});
	}
}  
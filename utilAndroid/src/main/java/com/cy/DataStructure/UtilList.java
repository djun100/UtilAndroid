package com.cy.DataStructure;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class UtilList {

    public static <E> boolean notEmpty(List<E> list) {
        return list != null && list.size() > 0;
    }

    public static <E> boolean isEmpty(List<E> list) {
        return list == null || list.size() == 0;
    }

    public static <E> List<E> convert_arrayToList(E[] array) {

        List<E> userList = new ArrayList<E>();

        if (array == null || array.length == 0) {
            return userList;
        }

        Collections.addAll(userList, array);
        return userList;
    }

    /**会出错
     * @param list
     * @param <E>
     * @return
     */
    public static <E> E[] convert_listToArray(List<E> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        E[] array = (E[]) list.toArray();
        return array;
    }

    /**
     * @param list  really arraylist
     * @param contents  eg:  new String[receivers.size()]
     * @param <E>
     * @return
     */
    public static <E> E[] listToArray(List<E> list,E[] contents){
        if (list==null || list.size()==0){
            return null;
        }
        ArrayList<E> list1= (ArrayList<E>) list;
        return list1.toArray(contents);
    }

    /**list去重
     * @param list
     * @param <E>
     * @return
     */
    public static <E> List<E> singleFilter(List<E> list){
        ArrayList<E> result = new ArrayList<E>();

        for(E e: list){
            if(Collections.frequency(result, e) < 1) result.add(e);
        }
        return result;
    }

    /**
     * based on http://www.cnblogs.com/nayitian/p/3322267.html
     * <p>
     * 要求 对列表（List）中的自定义对象，要求能够按照对象的属性（字段）进行排序（正序、倒序）。
     * 如：用户对象（Member）有用户名（username）、级别（level）、出生日期（birthday）等字段，要求可以分别对它的三个字段进行排序。
     * <p>
     * 实现思路
     * 对于自定义对象，可以在自定义对象中实现Comparable接口，然后再调用Collections.sort的方法实现排序，只能是针对一个属性（字段），维持一个顺序；要实多字段任意选择一个排序，同样需要通过调用Collections.sort(List list, Comparator<? super T> c)方法，传进一个Comparator来实现。
     * <p>
     * 为避免上述步骤中复杂且重复的代码，可以写一个通用的排序类，能够对自定义对象，针对不同的属性（字段），实现排序（正序、倒序）。
     * <p>
     * <p>
     * 自定义排序，专门针对列表（List）中的数据进行排序；可按指定方法进行。
     * 目前实现对字符串（String）、日期（Date）、整型（Integer）等三种对象进行排序。
     * 对列表中的数据按指定字段进行排序。要求类必须有相关的方法返回字符串、整型、日期等值以进行比较。
     *
     * @param list
     * @param method      eg:getUsername
     * @param bigToLittle
     */
    public static <E> void sortByMethod(List<E> list, final String method, final boolean bigToLittle) {
        Collections.sort(list, new Comparator<Object>() {
            @SuppressWarnings("unchecked")
            public int compare(Object arg1, Object arg2) {
                int result = 0;
                try {
                    Method m1 = ((E) arg1).getClass().getMethod(method, new Class[0]);
                    Method m2 = ((E) arg2).getClass().getMethod(method, new Class[0]);
                    Object obj1 = m1.invoke(((E) arg1), new  Object[]{});
                    Object obj2 = m2.invoke(((E) arg2), new  Object[]{});
                    if (obj1!=null&&obj2!=null) {
                        if (obj1 instanceof String) {
                            obj1= UtilHanziToPinyin.getPinYin((String) obj1);
                            obj2= UtilHanziToPinyin.getPinYin((String) obj2);
                            // 字符串
                            result = obj1.toString().compareTo(obj2.toString());
                            //                        Log.w("obj1:"+obj1+" obj2:"+obj2+" result:"+result);
                        } else if (obj1 instanceof Date) {
                            // 日期
                            long l = ((Date) obj1).getTime() - ((Date) obj2).getTime();
                            if (l > 0) {
                                result = 1;
                            } else if (l < 0) {
                                result = -1;
                            } else {
                                result = 0;
                            }
                        } else if (obj1 instanceof Integer) {
                            // 整型（Method的返回参数可以是int的，因为JDK1.5之后，Integer与int可以自动转换了）
                            result = (Integer) obj1 - (Integer) obj2;
                        } else {
                            // 目前尚不支持的对象，直接转换为String，然后比较，后果未知
                            result = obj1.toString().compareTo(obj2.toString());
                            System.err.println("UtilSortList.sortByMethod方法接受到不可识别的对象类型，转换为字符串后比较返回...");

                        }
                    } else {
                        if (obj1==null&&obj2!=null){
                            result=-1;
                        }else if (obj1==null&&obj2==null){
                            result=0;
                        }else {// obj1 != null && obj2 == null
                            result=1;
                        }
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
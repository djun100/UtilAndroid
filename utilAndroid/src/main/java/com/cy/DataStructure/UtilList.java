package com.cy.DataStructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/7/27.
 */
public class UtilList {
    public  static <E> boolean notEmpty(List<E> list){
        return list!=null && list.size()>0;
    }

    public static <E> List<E> arrayToList(E[] array){
        List<E> userList = new ArrayList<E>();
        Collections.addAll(userList, array);
        return userList;
    }

    public static <E> E[] listToArray(List<E> list){
        E[] array = (E[]) list.toArray();
        return array;
    }
}

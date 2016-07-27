package com.cy.DataStructure;

import java.util.List;

/**
 * Created by Administrator on 2016/7/27.
 */
public class UtilList {
    public  static <E> boolean notEmpty(List<E> list){
        return list!=null && list.size()>0;
    }
}

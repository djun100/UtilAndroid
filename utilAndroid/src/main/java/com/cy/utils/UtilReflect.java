package com.cy.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by cy on 2017/4/20.
 */

public class UtilReflect {
    public static Class getGenericClass(Field field){
        Class fieldArgClass = null;
        Type genericFieldType = field.getGenericType();

        if(genericFieldType instanceof ParameterizedType) {
            ParameterizedType aType = (ParameterizedType) genericFieldType;
            Type[] fieldArgTypes = aType.getActualTypeArguments();
            for (Type fieldArgType : fieldArgTypes) {
                fieldArgClass = (Class) fieldArgType;
                System.out.println("fieldArgClass = " + fieldArgClass);
            }
        }
            return fieldArgClass;
    }
}

package com.cy.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by cy on 2017/4/20.
 */

public class UtilReflect extends _BaseUtilReflect {
    protected UtilReflect(Class<?> type) {
        super(type);
    }

    protected UtilReflect(Class<?> type, Object object) {
        super(type, object);
    }

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

    public static <T> T makeInstance(Class<T> c, Map<String,Object> kvs) {
        T t = null;
        try {
            Constructor<?>[] con = c.getDeclaredConstructors();
            t = (T) con[0].newInstance();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Field[] fields = c.getDeclaredFields();
        if (fields!=null) {
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                String fieldName=fields[i].getName();

                if (kvs!=null) {
                    for (Map.Entry<String, Object> entry : kvs.entrySet()) {
                        if (fieldName.equals(entry.getKey())){
                            try {
                                fields[i].set(t,entry.getValue());
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        return t;
    }
}

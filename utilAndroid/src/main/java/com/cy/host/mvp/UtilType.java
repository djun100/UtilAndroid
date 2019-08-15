package com.cy.host.mvp;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class UtilType {

    // class CircleZoneActivity extends BaseActivity<CircleZonePresenter, ZoneModel>
    public static <T> T getTypeInstance(Object o, int i) {
        try {
            ParameterizedType parameterizedType = (ParameterizedType) o.getClass().getGenericSuperclass();
            Type type=parameterizedType.getActualTypeArguments()[i];
            Class<T> tClass = (Class<T>)type;
            return tClass.newInstance();
        } catch (Exception e) {
        }
        return null;
    }
}

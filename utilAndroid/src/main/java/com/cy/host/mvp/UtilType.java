package com.cy.host.mvp;

import java.lang.reflect.ParameterizedType;

public class UtilType {

    // class CircleZoneActivity extends BaseActivity<CircleZonePresenter, ZoneModel>
    public static <T> T getTypeInstance(Object o, int i) {
        try {
            return
                    (
                            (Class<T>)
                                    ((ParameterizedType) o.getClass().getGenericSuperclass())
                                            .getActualTypeArguments()[i]
                    ).newInstance();
        } catch (Exception e) {
        }
        return null;
    }
}

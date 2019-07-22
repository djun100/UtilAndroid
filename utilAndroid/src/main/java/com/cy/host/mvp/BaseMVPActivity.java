package com.cy.host.mvp;

import android.os.Bundle;

import com.cy.host.BaseHostActivity;


/**
 * view
 * mPresenter
 * Created by LiLei on 2017/7/3.Go.
 */
public abstract class BaseMVPActivity<T extends BasePresenter> extends BaseHostActivity {
    private T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            mPresenter = UtilType.getTypeInstance(this,0);
        } catch (Exception e) {
            //未提供presenter泛型的activity会报如下错误
            // java.lang.ClassCastException: java.lang.Class cannot be cast to java.lang.reflect.ParameterizedType
//            e.printStackTrace();
        }
        if (mPresenter !=null) {
            mPresenter.attach(BaseMVPActivity.this, this);
        }

    }


    @Override
    protected void onDestroy() {
        if (mPresenter !=null) {
            mPresenter.detach();
        }
        super.onDestroy();
    }

    protected T baseGetPresenter(){
        return mPresenter;
    }
}

package com.cy.host.mvp;

import android.os.Bundle;

import com.cy.host.BaseHostActivity;


/**
 * view
 * mPresenter
 * Created by LiLei on 2017/7/3.Go.
 */
public abstract class BaseMVPActivity<T extends BasePresenter> extends BaseHostActivity {
    public T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = UtilType.getTypeInstance(this,1);
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

}

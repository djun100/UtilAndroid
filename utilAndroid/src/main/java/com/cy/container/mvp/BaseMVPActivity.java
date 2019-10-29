package com.cy.container.mvp;

import android.os.Bundle;

import com.cy.container.BaseHostActivity;


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

        mPresenter = UtilType.getTypeInstance(this,0);
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

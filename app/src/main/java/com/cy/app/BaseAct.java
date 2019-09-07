package com.cy.app;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cy.host.mvp.BaseMVPActivity;
import com.cy.host.mvp.BasePresenter;
import com.cy.io.Log;

/**
 * Created by cy on 2018/1/9.
 */

public abstract class BaseAct<T extends BasePresenter> extends BaseMVPActivity<T> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("mytag","大发发\n大姐夫");
//        Log.d(0,"大发发\n大姐夫");
    }
}

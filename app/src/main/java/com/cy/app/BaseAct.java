package com.cy.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.cy.io.Log;

/**
 * Created by cy on 2018/1/9.
 */

public class BaseAct extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("大发发\n大姐夫");
        Log.d(0,"大发发\n大姐夫");
    }
}

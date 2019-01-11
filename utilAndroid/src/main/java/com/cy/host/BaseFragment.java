package com.cy.host;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.cy.io.Log;


/**
 * Tips:<><br/>
 * must call super.onCreateView(inflater,container,savedInstanceState);
 */
public abstract class BaseFragment extends Fragment {
    public void baseStartActivity(Class activity) {
        startActivity(new Intent(getActivity(), activity));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w(getClass().getName());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.w(getClass().getName() + " visable:" + isVisibleToUser);
    }

    /**
     * 回避接口返回数据慢时fragment却已不存在的bug
     *
     * @param runnable
     */
    public void baseRunOnUiThread(Runnable runnable) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(runnable);
        }
    }

}

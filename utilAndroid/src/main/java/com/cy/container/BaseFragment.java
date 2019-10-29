package com.cy.container;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.cy.data.UtilDummyData;
import com.cy.io.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;


/**
 * Tips:<><br/>
 * must call super.onCreateView(inflater,container,savedInstanceState);
 */
public abstract class BaseFragment extends Fragment {

    private boolean enableBusEvent;

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

    protected void baseAutoRegisterAndUndoEventBus(){
        enableBusEvent=true;
        EventBus.getDefault().register(this);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (enableBusEvent) {
            EventBus.getDefault().unregister(this);
        }
    }

    public static <T> T baseNewInstance(Class<T> t, Map<String,Object> params){
        return UtilDummyData.makeInstance(t,params);
    }
}

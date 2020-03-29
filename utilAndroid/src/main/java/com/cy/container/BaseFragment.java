package com.cy.container;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ViewTreeObserver;

import com.cy.io.Log;

import org.greenrobot.eventbus.EventBus;


/**
 * Tips:<><br/>
 * must call super.onCreateView(inflater,container,savedInstanceState);
 */
public abstract class BaseFragment extends Fragment {

    private boolean enableBusEvent;

    public void baseStartActivity(Class activity) {
        startActivity(new Intent(getActivity(), activity));
    }

    //避免使用有参构造函数，防止没有无参构造函数的时候restore找不到报错
//    public static XXFragment newInstance(String id) {
//        Bundle args = new Bundle();
//        args.putString("id", id);
//        XXFragment f = new XXFragment();
//        f.setArguments(args);
//        return f;
//    }


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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.w(getClass().getName() + " visable:" + isVisibleToUser);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(getClass().getName());
        if (savedInstanceState!=null){
            Log.i("savedInstanceState:"+Log.bundle2String(savedInstanceState));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.i();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i();
        if (enableBusEvent) {
            EventBus.getDefault().unregister(this);
        }
    }

    protected void baseAddOnGlobalLayoutListener(final OnGlobalLayoutListener onGlobalLayoutListener){
        ViewTreeObserver vto =getView().getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                getView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
                onGlobalLayoutListener.onGlobalLayout();
            }
        });
    }


    protected interface OnGlobalLayoutListener{
        void onGlobalLayout();
    }

}

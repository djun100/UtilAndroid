package com.cy.container.onActivityResult2;

import android.app.Activity;
import android.content.Intent;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * 参考：https://www.jianshu.com/p/2fd7fff09945
 */
public class OnActivityResultManager {

    private WeakReference<Activity> mActivity;
    private HashMap<Integer, OnActivityResultCallBack> mCallBackMap = new HashMap<>();

    public OnActivityResultManager(Activity activity) {
        mActivity = new WeakReference<>(activity);
    }


    private Activity getActivity() {
        return mActivity.get();
    }


    public void onActivtyResult(int requestCode, int resultCode, Intent data) {
        OnActivityResultCallBack callBack = mCallBackMap.remove(requestCode);
        callBack.onResultCallBack(requestCode, resultCode, data);

    }


    public void startActivityForResult(Intent intent, int requestCode, OnActivityResultCallBack callBack) {
        mCallBackMap.put(requestCode, callBack);
        getActivity().startActivityForResult(intent, requestCode);
    }


    public interface OnActivityResultCallBack {
        void onResultCallBack(int requestCode, int resultCode, Intent data);
    }

}
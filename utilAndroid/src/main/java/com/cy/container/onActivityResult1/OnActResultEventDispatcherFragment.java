package com.cy.container.onActivityResult1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;

public class OnActResultEventDispatcherFragment extends Fragment {

    public static final String TAG = "on_act_result_event_dispatcher";

    private SparseArray<ActResultRequest.OnCallback> mCallbacks = new SparseArray<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void startForResult(Intent intent, ActResultRequest.OnCallback onCallback) {
        mCallbacks.put(onCallback.hashCode(), onCallback);
        startActivityForResult(intent, onCallback.hashCode());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ActResultRequest.OnCallback onCallback = mCallbacks.get(requestCode);
        mCallbacks.remove(requestCode);

        if (onCallback != null) {
            onCallback.onActivityResult(resultCode, data);
        }
    }

}
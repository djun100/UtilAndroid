package com.cy.host.onActivityResult1;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class ActResultRequest {

    private OnActResultEventDispatcherFragment fragment;

    public ActResultRequest(FragmentActivity activity) {
        fragment = getEventDispatchFragment(activity);
    }

    public static ActResultRequest newInstance(FragmentActivity activity){
        return new ActResultRequest(activity);
    }

    private OnActResultEventDispatcherFragment getEventDispatchFragment(FragmentActivity activity) {
        final FragmentManager fragmentManager = activity.getSupportFragmentManager();

        OnActResultEventDispatcherFragment fragment = findEventDispatchFragment(fragmentManager);
        if (fragment == null) {
            fragment = new OnActResultEventDispatcherFragment();
            fragmentManager
                    .beginTransaction()
                    .add(fragment, OnActResultEventDispatcherFragment.TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return fragment;
    }

    private OnActResultEventDispatcherFragment findEventDispatchFragment(FragmentManager manager) {
        return (OnActResultEventDispatcherFragment) manager.findFragmentByTag(OnActResultEventDispatcherFragment.TAG);
    }

    public void startForResult(Intent intent, OnCallback onCallback) {
        fragment.startForResult(intent, onCallback);
    }

    public interface OnCallback {

        void onActivityResult(int resultCode, Intent data);
    }
}

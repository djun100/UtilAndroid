package com.cy.utils;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**可以避免内存泄漏的Observer
 * @param <T>
 */
public abstract class RxObserver<T> implements Observer<T> {
    private static final String TAG = "RxObserver";
    private Disposable disposable;
    public abstract void onResult(T t);
    public abstract void onFail(Throwable e);

    /**
     * FragmentActivity 或 Fragment
     */
    private Context mActOrFraOrNull;

    public RxObserver(Context actOrFraOrNull) {
        mActOrFraOrNull = actOrFraOrNull;
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
        Log.e(TAG, "onSubscribe: " + d.toString());
    }

    @Override
    public void onNext(T t) {
        Log.e(TAG, "result: " + t);

        onResult(t);
    }

    @Override
    public void onError(Throwable e) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        Log.e(TAG, "onError: ", e);
        onFail(e);
    }

    @Override
    public void onComplete() {
        Log.e(TAG, "onComplete");
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }

        disposeOnDestroy(mActOrFraOrNull,disposable);
    }

    /**
     * addObserver后不需要调用removeObserver
     * https://stackoverflow.com/questions/47656728/is-it-mandatory-to-remove-yourself-as-an-observer-from-android-lifecycle
     * @param context
     * @param disposable
     */
    public static void disposeOnDestroy(Context context, final Disposable disposable) {
        LifecycleOwner lifecycleOwner;
        if (context instanceof LifecycleOwner) {
            lifecycleOwner = (LifecycleOwner) context;
            lifecycleOwner.getLifecycle().addObserver(new LifecycleObserver() {
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                void onDestroy() {
                    if (!disposable.isDisposed()) {
                        disposable.dispose();
                    }
                }
            });
        }
    }
}

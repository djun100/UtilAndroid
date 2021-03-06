package com.cy.container.mvp;

import android.app.Activity;
import android.content.Context;

import com.cy.io.Log;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

/**
 * V传的必须是View实现的接口类，如果传act或fragment本身则通过动态代理调用act/fra.xxx()会发生强制转换失败
 * presenter类基类用于解决activity的view解绑绑定
 * 解决当Activity销毁，presenter里面持有的view就为null，如果这时候耗时任务刚好执行完准备调用view中的某个方法
 * 就会导致的空指针问题
 * <p/>
 * Mvp Presenter 抽象类. 通过 弱引用持有 Context 和 View对象, 避免产生内存泄露。
 * 注意, 如果Presenter有多个泛型类,那么 MvpView类型的泛型类要放在第一位.
 * 当 Context (通常是指Activity)被销毁时如果客户端程序再调用Context,
 * 那么直接返回 Application 的Context. 因此如果用户需要调用与Activity相关的UI操作(例如弹出Dialog)时,
 * 应该先调用 {@link #baseIsActivityAlive()} 来判断Activity是否还存活.
 * 当 View 对象销毁时如果用户再调用 View对象, 那么则会
 * 通过动态代理创建一个View对象 {@link #mNullViewProxy}, 这样保证 view对象不会为空.
 * 使用getMvpView方法来获取View
 * 因为使用了弱引用，所以不需要detach将引用置空
 * </p>
 */


public abstract class BasePresenter<V/*接口*/,M> {
    /**
     * Null Mvp View InvocationHandler
     */
    private static final InvocationHandler NULL_VIEW = new MvpViewInvocationHandler();
    /**
     * context weak reference
     */
    private WeakReference<Context> mContextRef;
    /**
     * mvp view weak reference
     */
    private WeakReference<V> mViewRef;
    /**
     * mvp view class
     */
    private Class mMvpViewClass = null;
    /**
     * Mvp View created by dynamic Proxy
     */
    private V mNullViewProxy;
    private M mModel;

    public BasePresenter() {
    }


    public BasePresenter(Context context, V view) {
        attach(context, view);
    }

    /**
     * attach context & mvp view
     *
     * @param context
     * @param view
     */
    public void attach(Context context, V view) {
        mContextRef = new WeakReference<>(context);
        mViewRef = new WeakReference<>(view);
        mModel = newModel();
    }

    protected abstract M newModel();

    /**
     * release resource
     */
    public void detach() {
        if (mContextRef != null) {
            mContextRef.clear();
        }
        mContextRef = null;
        if (mViewRef != null) {
            mViewRef.clear();
        }
        mViewRef = null;
    }

    /**
     * UI展示相关的操作需要判断一下 Activity 是否已经 finish.
     * <p/>
     * todo : 只有当 isActivityAlive 返回true时才可以执行与Activity相关的操作,
     * 比如 弹出Dialog、Window、跳转Activity等操作.
     *
     * @return
     */
    protected boolean baseIsActivityAlive() {
        return !isActivityFinishing() && mViewRef.get() != null;
    }


    /**
     * activity 是否是finishing状态
     *
     * @return
     */
    private boolean isActivityFinishing() {
        if (mContextRef == null) {
            return true;
        }
        Context context = mContextRef.get();
        if (context instanceof Activity) {
            Activity hostActivity = (Activity) context;
            return hostActivity.isFinishing();
        }
        return true;
    }

    /**
     * 返回 Mvp View对象. 如果真实的 View对象已经被销毁, 那么会通过动态代理构建一个View,
     * 确保调用 View对象执行操作时不会crash.
     *
     * @return Mvp View
     */
    protected V baseGetMvpView() {
        V view = mViewRef != null ? mViewRef.get() : null;
        if (view == null) {
            // create null mvp view
            if (mNullViewProxy == null) {
                mNullViewProxy = createView(getMvpViewClass());
            }
            view = mNullViewProxy;
        }
        return view;
    }

    protected M baseGetModule(){
        return mModel;
    }

    /**
     * 创建 mvp view
     *
     * @param viewClz
     * @param <T>
     * @return
     */
    private static <T> T createView(Class<T> viewClz) {
        //V传的必须是View实现的接口类，如果传act或fragment本身则通过动态代理创建的类.类内的方法会发生强制转换失败
        Class<?>[] interfaces = new Class[]{viewClz};
//        Class<?>[] interfaces = viewClz.getInterfaces();
        return (T) Proxy.newProxyInstance(viewClz.getClassLoader(), interfaces, NULL_VIEW);
    }


    private Class<V> getMvpViewClass() {
        if (mMvpViewClass == null) {
            Type genType = getClass().getGenericSuperclass();
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            mMvpViewClass = (Class<V>) params[0];
        }
        return mMvpViewClass;
    }


    /**
     * 动态代理 InvocationHandler
     */
    private static class MvpViewInvocationHandler implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.e("### MvpView InvocationHandler do nothing -> " + method.getName());
            return null;
        }
    }
}
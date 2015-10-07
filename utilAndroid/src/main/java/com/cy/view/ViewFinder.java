package com.cy.view;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;

/**
 * view finder, 方便查找View。用户需要在使用时调用initContentView，
 * 将Context和布局id传进来，然后使用findViewById来获取需要的view
 * ,findViewById为泛型方法,返回的view则直接是你接收的类型,而不需要进行强制类型转换.比如,
 * 以前我们在Activity中找一个TextView一般是这样 : 
 * TextView textView = (TextView)findViewById(viewId); 
 * 如果页面中的控件比较多，就会有很多的类型转换,而使用ViewFinder则免去了类型转换,
 * 示例如下 : 
 * TextView textView = ViewFinder.findViewById(viewId);
 * 
 * @author mrsimple
 * tips:使用前先初始化initContentView();
 */
public final class ViewFinder {

    /**
     * LayoutInflater
     */
    static LayoutInflater mInflater;

    /**
     * 每项的View的sub view Map
     */
    private static SparseArray<View> mViewMap = new SparseArray<View>();

    /**
     * Content View
     */
    static View mContentView;

    /**
     * 初始化ViewFinder, 实际上是获取到该页面的ContentView.
     * 
     * @param context
     * @param layoutId
     */
    public static void initContentView(Context context, int layoutId) {
        mInflater = LayoutInflater.from(context);
        mContentView = mInflater.inflate(layoutId, null, false);
        if (mInflater == null || mContentView == null) {
            throw new RuntimeException(
                    "ViewFinder init failed, mInflater == null || mContentView == null.");
        }
    }

    /**
     * @return
     */
    public static View getContentView() {
        return mContentView;
    }

    /**
     * @param viewId
     * @return
     */
//    @SuppressWarnings("unchecked")
    public static <T extends View> T findViewById(int viewId) {
        // 先从view map中查找,如果有的缓存的话直接使用,否则再从mContentView中找
        View tagetView = mViewMap.get(viewId);
        if (tagetView == null) {
            tagetView = mContentView.findViewById(viewId);
            mViewMap.put(viewId, tagetView);
        }
        return tagetView == null ? null : (T) mContentView.findViewById(viewId);
    }
}
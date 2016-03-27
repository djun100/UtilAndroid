package com.cy.app;

import android.app.Application;
import android.content.Context;

/**
 * VDContextHolder
 * AndroidContextHolder <com.vilyever.vdcontextholder>
 * Created by vilyever on 2015/9/15.
 * Feature:
 */
public class UtilContext {
    private final UtilContext self = this;

    static Context mContext;

    /* Public Methods */
    public static void initial(Context context) {
        mContext = context;
    }

    public static Context getContext() {
        if (mContext == null) {
            try {
                Application application = (Application) Class
                        .forName("android.app.ActivityThread")
                        .getMethod("currentApplication")
                        .invoke(null, (Object[]) null);
                if (application != null) {
//                    Log.e(UtilContext.class.getName(), "context gotten by ActivityThread#currentApplication");
                    mContext = application;
                    return application;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Application application = (Application) Class
                        .forName("android.app.AppGlobals")
                        .getMethod("getInitialApplication")
                        .invoke(null, (Object[]) null);
                if (application != null) {
//                    Log.e(UtilContext.class.getName(), "context gotten by AppGlobals#getInitialApplication.");
                    mContext = application;
                    return application;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            throw new IllegalStateException("context not initialed and cannot be reflected.");
        }
        return mContext;
    }
}
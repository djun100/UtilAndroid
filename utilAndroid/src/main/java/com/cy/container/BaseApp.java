package com.cy.container;

import android.app.Application;

import com.cy.app.CrashHandler;

public class BaseApp extends Application {
	public static BaseApp mInstance;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		CrashHandler.initCrashHandler();
	}
}

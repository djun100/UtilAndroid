package com.cy.container;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public abstract class UtilActivity extends Activity {
	Context context;
	LayoutInflater inflater = getLayoutInflater();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context=this;
		initData();
		initCalculate();
		initView();
	}
	private void initData() {
		// TODO Auto-generated method stub
		
	}
	private void initCalculate() {
		// TODO Auto-generated method stub
		
	}
	private void initView() {
		// TODO Auto-generated method stub
		
	}
	public View getInflateView(int layout){
		View v = inflater.inflate(layout,null);
		return v;
	}
}

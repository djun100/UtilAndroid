package com.cy.app.main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;

import com.cy.app.BaseAct;
import com.cy.app.DemoDialogFragment;
import com.cy.app.R;
import com.cy.app.StyledViewActivity;
import com.cy.app.TestLog;
import com.cy.app.log.LogActivity;
import com.cy.file.UtilFile;
import com.cy.io.Log;
import com.cy.view.UtilScreen;
import com.cy.view.UtilToast;
import com.cy.view.UtilViewStyle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends BaseAct<MainPresenter> implements IMainView, View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * Hello World!
     */
    private Button mTv;
    /**
     * Button
     */
    private Button mBtn;
    private Button mBtnShow;
    /**
     * testMVP
     */
    private Button mBtnTestMVP;
    /**
     * StyledView
     */
    private Button mBtnStyledView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        TestLog.showLog("呵呵");
        String ANDROID_ID = Settings.System.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        Log.i("ANDROID_ID :"+ANDROID_ID );
        UtilToast.showLong(ANDROID_ID);

        baseGetPresenter().testThread();
        baseGetPresenter().testMMKV();
    }


    private void initView() {
        mTv = findViewById(R.id.tv);
        mTv.setOnClickListener(this);
        mBtn = findViewById(R.id.btn);
        mBtn.setOnClickListener(this);
        mBtnShow = findViewById(R.id.btnShow);
        mBtnShow.setOnClickListener(this);
        mBtnTestMVP = findViewById(R.id.btnTestMVP);
        mBtnTestMVP.setOnClickListener(this);
        mBtnStyledView = findViewById(R.id.btnStyledView);
        mBtnStyledView.setOnClickListener(this);

        UtilViewStyle.view(mBtn)
//                .setRippleEnable(true)
                .setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright))
                .setBackgroundDisableColor(getResources().getColor(android.R.color.darker_gray))
                .setTextDisableColor(getResources().getColor(android.R.color.black))
                .setStrokeColorRes(android.R.color.holo_purple)
//                .setStrokePressColorRes(android.R.color.holo_red_light)
                .setStrokeWidth(5)
                .setCornerRadius(UtilScreen.dp(5))
                .setOrientation(GradientDrawable.Orientation.TOP_BOTTOM)
                .setStartColor(0xffff0000)
                .setEndColor(0xff0000ff)
                .apply();
    }

    public static void writeFile1() {
        File file = new File(UtilFile.getSdPath() + "/A.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFile2() {
        File a = new File(UtilFile.getSdPath() + "/A.txt");
        if (a.exists()) {

        } else {
            try {
                a.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        UtilFile.writeUtf8FileContent(a, "aaaa");
    }

    public boolean hasPermissions(Activity mActivity) {
        boolean allGarented = true;
        String permissions[] = {
                //Manifest.permission.RECORD_AUDIO,
                //Manifest.permission.ACCESS_NETWORK_STATE,
                // Manifest.permission.INTERNET,
                // Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        ArrayList<String> toApplyList = new ArrayList<String>();
        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(mActivity, perm)) {
                toApplyList.add(perm);
                //进入到这里代表没有权限.
                Log.e(TAG, "没有权限：" + perm);
            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            allGarented = false;
            Log.e(TAG, "请求权限数量" + toApplyList.size());
            ActivityCompat.requestPermissions(mActivity, toApplyList.toArray(tmpList), 123);
        }

        return allGarented;

    }
    DemoDialogFragment mDemoDialogFragment;
    private void showDialogFragment() {
        if (mDemoDialogFragment==null){
            mDemoDialogFragment = DemoDialogFragment.newInstance();
        }
        mDemoDialogFragment.show(getSupportFragmentManager(), "");
        Log.i(Log.getStackTraceStr());
    }

    public void showToast(String data) {
        UtilToast.showShort(data);
    }

    public void showDialog() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv:
                mBtn.setEnabled(!mBtn.isEnabled());

//                    writeFile2();
                TestLog.showLog("呵呵");
//                    com.cy.io.Log.w("呵呵");
                Intent intent=new Intent(this, LogActivity.class);
                startActivity(intent);
                break;
            case R.id.btn:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mBtnShow.setBackgroundTintList(ColorStateList.valueOf(0xffff0000));
                }

                break;
            case R.id.btnShow:
                showDialogFragment();
                break;
            case R.id.btnTestMVP:
                baseGetPresenter().readData();
                break;
            case R.id.btnStyledView:
                baseStartActivity(StyledViewActivity.class);
                break;
        }
    }
}

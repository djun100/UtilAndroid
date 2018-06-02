package com.cy.app;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cy.File.UtilFile;
import com.cy.File.UtilSharedPreferences;
import com.cy.view.UtilViewStyle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends BaseAct {
    private static final String TAG=MainActivity.class.getSimpleName();
    private Button mbtnResult;
    private View.OnClickListener mOnClickListener;
    private Button mbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        UtilSharedPreferences.setParam("key1","a");
        String value = (String) UtilSharedPreferences.getParam("key1","");
//        com.cy.io.Log.init(this).setBorderSwitch(false).setLogHeadSwitch(false);
//        com.cy.io.Log.w("呵呵");
//        TestLog.showLogUseLogUtil();
//        TestLog.showLog("呵呵");
    }


    private void initView() {
        mbtnResult = (Button) findViewById(R.id.mtv);
        mbtn = (Button) findViewById(R.id.mbtn);
        new UtilViewStyle(mbtnResult)
//                .setRippleEnable(true)
                .setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright))
                .setBackgroundDisableColor(getResources().getColor(android.R.color.darker_gray))
                .setTextDisableColor(getResources().getColor(android.R.color.black))
                .setStrokeColorRes(android.R.color.holo_purple)
//                .setStrokePressColorRes(android.R.color.holo_red_light)
                .setStrokeWidth(5)
//                .setCornerRadius(15)
                .apply();

        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == mbtn) {
//                    mbtnResult.setEnabled(!mbtnResult.isEnabled());

//                    writeFile2();
//                    TestLog.showLog("呵呵");
//                    com.cy.io.Log.w("呵呵");
                }
            }
        };
        mbtn.setOnClickListener(mOnClickListener);

    }

    public static void writeFile1(){
        File file=new File(UtilFile.getSdPath()+"/A.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFile2(){
        File a = new File(UtilFile.getSdPath() + "/A.txt");
        if (a.exists()) {

        } else {
            try {
                a.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        UtilFile.write_UTF8_FileContent(a, "aaaa");
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
}

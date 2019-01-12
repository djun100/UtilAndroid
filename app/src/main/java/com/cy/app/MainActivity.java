package com.cy.app;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cy.File.UtilFile;
import com.cy.view.UtilScreen;
import com.cy.view.UtilViewStyle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends BaseAct {
    private static final String TAG=MainActivity.class.getSimpleName();
    private Button mbtnResult;
    private View.OnClickListener mOnClickListener;
    private Button mbtn;
    private Button mbtnShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }


    private void initView() {
        mbtnShow=findViewById(R.id.mbtnShow);
        mbtnResult = (Button) findViewById(R.id.mtv);
        mbtn = (Button) findViewById(R.id.mbtn);
        UtilViewStyle.view(mbtnResult)
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

        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == mbtn) {
                    mbtnResult.setEnabled(!mbtnResult.isEnabled());

//                    writeFile2();
//                    TestLog.showLog("呵呵");
//                    com.cy.io.Log.w("呵呵");
                }else if (v==mbtnResult){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mbtnResult.setBackgroundTintList(ColorStateList.valueOf(0xffff0000));
                    }
                }else if (v==mbtnShow){
                    showDialogFragment();
                }
            }
        };
        mbtn.setOnClickListener(mOnClickListener);
        mbtnResult.setOnClickListener(mOnClickListener);
        mbtnShow.setOnClickListener(mOnClickListener);
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

    private void showDialogFragment(){
        DemoDialogFragment.newInstance().show(getSupportFragmentManager(),"");
    }
}

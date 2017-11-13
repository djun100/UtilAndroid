package com.cy.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.cy.view.UViewStyle;

public class MainActivity extends FragmentActivity {
    private Button mbtnResult;
    private View.OnClickListener mOnClickListener;
    private Button mbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    private void initView() {
        mbtnResult = (Button) findViewById(R.id.mtv);
        mbtn = (Button) findViewById(R.id.mbtn);
        new UViewStyle(mbtnResult)
//                .setRippleEnable(true)
                .setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright))
                .setBackgroundDisableColor(getResources().getColor(android.R.color.darker_gray))
                .setTextDisableColor(getResources().getColor(android.R.color.black))
//                .setCornerRadius(15)
                .apply();

        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == mbtn) {
                    mbtnResult.setEnabled(!mbtnResult.isEnabled());

                }
            }
        };
        mbtn.setOnClickListener(mOnClickListener);

    }
}

package com.cy.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cy.view.UViewStyle;

public class MainActivity extends FragmentActivity {
    private TextView mtv;
    private View.OnClickListener mOnClickListener;
    private Button mbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    private void initView() {
        mtv = (TextView) findViewById(R.id.mtv);
        mbtn = (Button) findViewById(R.id.mbtn);
        new UViewStyle(mtv)
                .setBackgroundDisableColor(getResources().getColor(android.R.color.darker_gray))
                .setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright))
                .setTextDisableColor(getResources().getColor(android.R.color.black))
                .setCornerRadius(15)
                .apply();

        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == mbtn) {
                    mtv.setEnabled(!mtv.isEnabled());

                }
            }
        };
        mbtn.setOnClickListener(mOnClickListener);

    }
}

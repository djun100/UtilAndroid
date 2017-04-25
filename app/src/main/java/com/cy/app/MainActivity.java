package com.cy.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;

import com.cy.view.UtilView;

public class MainActivity extends FragmentActivity {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UtilView.stateLizeBtn(button,);
    }
}

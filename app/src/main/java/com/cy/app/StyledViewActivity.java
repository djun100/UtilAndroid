package com.cy.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.cy.view.styled.RoundFrameLayout;
import com.cy.view.styled.RoundLinearLayout;
import com.cy.view.styled.RoundTextView;
import com.cy.view.styled.RoundViewDelegate;

public class StyledViewActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * TextView Default
     */
    private RoundTextView mRtv1;
    /**
     * Radius Half Height
     */
    private RoundTextView mRtv2;
    /**
     * TextView Radius 10dpTextView Radius 10dp
     */
    private RoundTextView mRtv3;
    /**
     * Radius TopRight
     */
    private RoundTextView mTvRt;
    private RoundFrameLayout mFl;
    private RoundLinearLayout mLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_styled_view);
        initView();
    }

    private void initView() {
        mRtv1 = findViewById(R.id.rtv_1);
        mRtv1.setOnClickListener(this);
        mRtv2 = findViewById(R.id.rtv_2);
        mRtv2.setOnClickListener(this);
        mRtv3 = findViewById(R.id.rtv_3);
        mRtv3.setOnClickListener(this);
        mTvRt = findViewById(R.id.tvRt);
        mTvRt.setOnClickListener(this);
        mFl = findViewById(R.id.fl);
        mFl.setOnClickListener(this);
        mLl = findViewById(R.id.ll);
        mLl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.rtv_1:
                Toast.makeText(this, "Click--->RoundTextView_1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rtv_2:
                //                rtv_2.setEnabled(false);
                Toast.makeText(this, "LongClick--->RoundTextView_2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rtv_3:
                RoundViewDelegate delegate = mRtv3.getDelegate();
                delegate.setBackgroundColor(
                        delegate.getBackgroundColor() == Color.parseColor("#ffffff")
                                ? Color.parseColor("#F6CE59") : Color.parseColor("#ffffff"));

                break;
            case R.id.tvRt:
                break;
            case R.id.fl:
                break;
            case R.id.ll:
                break;
        }
    }
}

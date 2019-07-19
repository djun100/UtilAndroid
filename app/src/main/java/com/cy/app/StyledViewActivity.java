package com.cy.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.cy.view.styled.StyledFrameLayout;
import com.cy.view.styled.StyledLinearLayout;
import com.cy.view.styled.StyledTextView;
import com.cy.view.styled.StyledViewDelegate;

public class StyledViewActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * TextView Default
     */
    private StyledTextView mRtv1;
    /**
     * Radius Half Height
     */
    private StyledTextView mRtv2;
    /**
     * TextView Radius 10dpTextView Radius 10dp
     */
    private StyledTextView mRtv3;
    /**
     * Radius TopRight
     */
    private StyledTextView mTvRt;
    private StyledFrameLayout mFl;
    private StyledLinearLayout mLl;
    private ImageView mIvGlide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_styled_view);
        initView();


        String url="http://lorempixel.com/400/200/sports/1/";
        RequestOptions options = new RequestOptions()
                .bitmapTransform(new GlideRoundBorderTransform(30)
                        .setBorder(3, 0xffff0000))
                .diskCacheStrategy(DiskCacheStrategy.DATA);
        Glide.with(this)
                .load(url)
                .apply(options)
                .placeholder(R.mipmap.ic_launcher)
                .fallback(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                //默认淡入淡出动画
                .transition(DrawableTransitionOptions.withCrossFade())
                //缓存策略,跳过内存缓存【此处应该设置为false，否则列表刷新时会闪一下】
                .skipMemoryCache(false)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //设置图片加载的优先级
//                .priority(Priority.HIGH)
                .into(mIvGlide);


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
        mIvGlide = findViewById(R.id.ivGlide);
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
                Toast.makeText(this, "LongClick--->RoundTextView_2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rtv_3:
                mRtv2.setEnabled(!mRtv2.isEnabled());
                StyledViewDelegate delegate = mRtv3.getDelegate();
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

package com.cy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.cy.utilandroid.R;


/**
 <declare-styleable name="TypefaceTextViewAttr">
 <attr name="text_typeface" format="string" />
 </declare-styleable>

 <com.sogou.upd.x1.widget.TypefaceTextView
     app:text_typeface="字体名称不带后缀" />
 */
public class TypefaceTextView extends android.support.v7.widget.AppCompatTextView {
    String type;
    public TypefaceTextView(Context context) {
        super(context);
    }

    public TypefaceTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TypefaceTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!TextUtils.isEmpty(getText()) && "Barlow-MediumCondensedOblique.woff".equals(type)) {
            setMeasuredDimension(getMeasuredWidth()+ UtilScreen.dp(3), getMeasuredHeight());
        }
    }

    private void setTypeface(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TypefaceTextViewAttr);

        type = typedArray.getString(R.styleable.TypefaceTextViewAttr_text_typeface);
        if(null==type){
            return;
        }
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),  type+".ttf");
        setTypeface(typeface);
        if (typedArray != null) {
            typedArray.recycle();
        }
    }
}

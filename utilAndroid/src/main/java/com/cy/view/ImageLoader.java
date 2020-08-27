package com.cy.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.cy.app.UtilContext;
import com.cy.utilandroid.R;

public class ImageLoader {

    private final Builder builder;
    ImageLoader(Builder builder) {
        this.builder = builder;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public void load(){
        RequestManager requestManager;
        if (builder.activity != null) {
            requestManager = Glide.with(builder.activity);
        } else if (builder.fragment != null) {
            requestManager = Glide.with(builder.fragment);
        } else if (builder.view != null) {
            requestManager = Glide.with(builder.view);
        } else if (builder.context != null) {
            requestManager = Glide.with(builder.context);
        } else {
            requestManager = Glide.with(UtilContext.getContext());
        }

        RequestOptions requestOptions = null;
        if (builder.circleCrop || builder.scaleType == ScaleType.CircleCrop) {
            if (builder.hasBorder) {
                requestOptions = RequestOptions.bitmapTransform(new CircleWithBorderTransform(builder.borderWidth, builder.borderColor));
            } else {
                requestOptions = RequestOptions.bitmapTransform(new CircleCrop());
            }
        } else if (builder.roundedCorner > 0) {
            requestOptions = RequestOptions.bitmapTransform(new RoundedCorners(builder.roundedCorner));
        }

        RequestBuilder<Drawable> requestBuilder =  requestManager.load(builder.uri);

        //解决 placeHolder 不能圆角或圆形变化的问题
        RequestBuilder<Drawable> requestBuilder2 = null;
        if (builder.placeHolderResId > 0 || builder.failHolderResId > 0) {
            int resId = builder.placeHolderResId > 0 ? builder.placeHolderResId : builder.failHolderResId;
            requestBuilder2 = requestManager.load(resId);
            if (requestOptions!=null) {
                requestBuilder2 = requestBuilder2.apply(requestOptions);
            }
            requestBuilder = requestBuilder.thumbnail(requestBuilder2);
        }

        if (requestOptions != null) {
            requestBuilder = requestBuilder.apply(requestOptions);
        }

//        if (builder.scaleType == ScaleType.FitCenter) {
//            requestBuilder = requestBuilder.fitCenter();
//        } else if (builder.scaleType != ScaleType.CircleCrop){
//            requestBuilder = requestBuilder.centerCrop();
//        }

        if (builder.requestListener!=null) {
            requestBuilder = requestBuilder.listener(builder.requestListener);
        }

        requestBuilder.into(builder.imageView);
    }


    public static void loadCircleWithBolder(Activity activity, ImageView imageView, String url, int borderWidthDp) {
        new ImageLoader.Builder().with(activity).imageUrl(url).targetImageView(imageView)
                .circleCrop(true).border(true).borderWidth(UtilScreen.dp(borderWidthDp))
//                .placeHolder(R.drawable.avatar)
                .build().load();
    }

    public static void loadCircler(Activity activity, ImageView imageView, String url, @DrawableRes int placeHolder) {
        new ImageLoader.Builder().with(activity).imageUrl(url).targetImageView(imageView)
                .circleCrop(true)
                .placeHolder(placeHolder)
                .build().load();
    }

    public static void showBlur(Activity activity, ImageView imageView, String url) {
        Glide.with(activity).load(url).transform(new BlurTransformation(activity, 5)).into(imageView);
    }
    public static void showBlur(Activity activity, ImageView imageView, @DrawableRes int resId) {
        Glide.with(activity).load(resId).transform(new BlurTransformation(activity, 5)).into(imageView);
    }

    public static void showImage(Activity activity, ImageView imageView, Uri uri) {
        new Builder().with(activity).uri(uri).targetImageView(imageView).build().load();
    }

    public static void showImage(Activity activity, ImageView imageView, String url) {
        new Builder().with(activity).imageUrl(url).targetImageView(imageView).build().load();
    }

    public static void showImage(Activity activity, ImageView imageView, String url,int placeHolder) {
        new Builder().with(activity).imageUrl(url).targetImageView(imageView).placeHolder(placeHolder).build().load();
    }

    public static void showRounderImage(Activity activity, ImageView imageView, String url, int placeHolderId, int corner) {
        new Builder().with(activity).imageUrl(url).targetImageView(imageView).placeHolder(placeHolderId).roundedCorner(corner).build().load();
    }

    public static void showRounderImage(Activity activity, ImageView imageView, Uri uri, int placeHolderId, int corner) {
        new Builder().with(activity).uri(uri).targetImageView(imageView).placeHolder(placeHolderId).roundedCorner(corner).build().load();
    }

    public static final class Builder {
        Uri uri;
        ImageView imageView;
        Context context;
        Activity activity;
        Fragment fragment;
        View view;
        boolean circleCrop;
        int roundedCorner;
        boolean leftTopRoundedCorner;
        boolean rightTopRoundedCorner;
        boolean rightBottomRoundedCorner;
        boolean leftBottomRoundedCorner;
        int placeHolderResId;
        int failHolderResId;
        boolean hasBorder;
        int borderWidth = UtilScreen.dp(1);
        int borderColor = Color.WHITE;
        ScaleType scaleType ;
        SimpleRequestListener requestListener;

        public Builder imageUrl(String url) {
            if (!TextUtils.isEmpty(url)){
                this.uri = Uri.parse(url);
            }
            return this;
        }

        public Builder uri(Uri uri) {
            this.uri = uri;
            return this;
        }

        public Builder targetImageView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public Builder with(Context context) {
            this.context = context;
            return this;
        }

        public Builder with(Activity activity) {
            this.activity = activity;
            return this;
        }

        public Builder with(Fragment fragment) {
            this.fragment = fragment;
            return this;
        }

        public Builder with(View view) {
            this.view = view;
            return this;
        }

        public Builder circleCrop(boolean flag) {
            this.circleCrop = flag;
            this.scaleType = ScaleType.CircleCrop;
            return this;
        }

        public Builder roundedCorner(int roundedCorner) {
            this.roundedCorner = roundedCorner;
            return this;
        }

        public Builder placeHolder(int placeHolderResId) {
            this.placeHolderResId = placeHolderResId;
            return this;
        }

        public Builder failHolder(int failHolderResId) {
            this.failHolderResId = failHolderResId;
            return this;
        }

        public Builder needCorner(boolean leftTop, boolean rightTop, boolean rightBottom, boolean leftBottom) {
            this.leftTopRoundedCorner = leftTop;
            this.rightTopRoundedCorner = rightTop;
            this.rightBottomRoundedCorner = rightBottom;
            this.leftBottomRoundedCorner = leftBottom;
            return  this;
        }

        public Builder border(boolean border) {
            this.hasBorder = border;
            return this;
        }

        public Builder borderWidth(int width) {
            this.borderWidth = width;
            return this;
        }

        public Builder borderColor(int color) {
            this.borderColor = color;
            return this;
        }

        public Builder requestListener(SimpleRequestListener listener) {
            this.requestListener = listener;
            return this;
        }

        public Builder scaleType(ScaleType scaleType) {
            this.scaleType = scaleType;
            return this;
        }


        public ImageLoader build() {
            return new ImageLoader(this);
        }
    }

    public static class SimpleRequestListener implements RequestListener<Drawable> {

        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            return false;
        }
    }

    public static enum ScaleType {
        CenterCrop,
        FitCenter,
        CircleCrop;
    }
}

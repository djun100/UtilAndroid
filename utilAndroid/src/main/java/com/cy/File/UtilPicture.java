
package com.cy.File;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.cy.app.Log;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;

/**{@link #getDecodeSampledBitmapFromResource(String, int, int)} 高效加载大图<br>
 * {@link #calculateInSampleSize(Options, int, int)} 计算2次幂的缩小比例
   @author cy <a href="https://github.com/djun100">https://github.com/djun100</a>
 */
public class UtilPicture {
	static final String tag = "UtilPicture";

	/**
	 * 本地文件转bitmap
	 * 
	 * @param sPathname
	 *            fullpath
	 * @param key
	 *            cache-key
	 * @return Exception null
	 */
	public static Bitmap picToBitmap(String sPathname, String key) {
		try {
			Bitmap tempBitmap;
			tempBitmap = BitmapFactory.decodeFile(sPathname);
			/*
			 * //加入内存缓存 CacheBitmap.getInstance().addBitmapToMemoryCache(key,
			 * tempBitmap);
			 */
			return tempBitmap;
		} catch (Exception e) {
			System.err.println(tag + " picToBitmap(String sPathname,String key)本地文件不存在");
			return null;
		}
	}

	/**
	 * 等比例缩放Bitmap
	 * 
	 * @param bitmap
	 * @param scale
	 *            matrix.postScale(1.5f,1.5f); //长和宽放大缩小的比例
	 * @return
	 */
	public static Bitmap getScaledBitmap(Bitmap bitmap, float scale) {
		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);
		Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizeBmp;

	}

	/**
	 * 更改图片尺寸
	 * 
	 * @param bitmap
	 * @param weith
	 * @param height
	 * @return
	 */
	public static Bitmap getResizedBitmap(Bitmap bitmap, int weith, int height) {

		Bitmap resizeBmp = Bitmap.createScaledBitmap(bitmap, weith, weith, true);

		return resizeBmp;
	}

	/**
	 * read native pic and convert it to drawable
	 * 
	 * @param sFilePath
	 *            fullPathname
	 * @return
	 */
	public static Drawable picToDrawable(String sFilePath) {
		try {
			Bitmap bitmapTemp;
			bitmapTemp = BitmapFactory.decodeFile(sFilePath);

			return (Drawable) new BitmapDrawable(bitmapTemp);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * bitmap转drawable
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Drawable bitmapToDrawable(Bitmap bitmap) {
		return (Drawable) new BitmapDrawable(bitmap);
	}

	/**
	 * 指定手机SDcard图片文件夹全路径，得到Drawable[]
	 * 
	 * @param path
	 *            fullPath root/a root/a/
	 * @return
	 */
	public static Drawable[] pathToDrawable(String path) {
		path = UtilFile.addEndPathSeparator(path);
		String[] tempPicList = new File(path).list();
		String[] picList = new String[tempPicList.length];
		Drawable[] drawable = new Drawable[picList.length];
		for (int i = 0; i < tempPicList.length; i++) {
			picList[i] = path + tempPicList[i];
			drawable[i] = UtilPicture.picToDrawable(picList[i]);
		}
		return drawable;
	}

	public static Bitmap getResBitmap(Context context, int r) {
		return BitmapFactory.decodeResource(context.getResources(), r);
	}

	/**
	 * bgimg0 = getImageFromAssetsFile("Cat_Blink/cat_blink0000.png");<br>
	 * 从Assets中读取图片
	 */
	public static Bitmap getBitmapFromAssets(Context context, String fileName) {
		Bitmap image = null;
		AssetManager am = context.getResources().getAssets();
		try {
			InputStream is = am.open(fileName);
			image = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return image;

	}

	/**
	 * 将bitmap转换为带倒影的bitmap
	 * 
	 * @param originalImage
	 * @return
	 */
	public static Bitmap getBitmapWithReflaction(Bitmap originalImage) {
		final int reflectionGap = 4;
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();

		// This will not scale but will flip on the Y axis
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		// Create a Bitmap with the flip matrix applied to it.
		// We only want the bottom half of the image
		Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height / 2, width, height / 2, matrix, false);

		// Create a new bitmap with same width but taller to fit
		// reflection
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 2), Config.ARGB_8888);

		// Create a new Canvas with the bitmap that's big enough for
		// the image plus gap plus reflection
		Canvas canvas = new Canvas(bitmapWithReflection);
		// Draw in the original image
		canvas.drawBitmap(originalImage, 0, 0, null);
		// Draw in the gap
		Paint deafaultPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, deafaultPaint);
		// Draw in the reflection
		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

		// Create a shader that is a linear gradient that covers the
		// reflection
		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0, bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff,
				TileMode.CLAMP);
		// Set the paint to use this shader (linear gradient)
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);

		originalImage = bitmapWithReflection;
		// 回收
		reflectionImage.recycle();
		return originalImage;
	}

	public static Drawable getDrawableFromInt(Context context, int drawable) {
		return context.getResources().getDrawable(drawable);
	}

	public static void get1BitmapFromMedia_ForResult(Activity activity, int requestCode) {

		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		// Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setType("image/*");
		activity.startActivityForResult(intent, requestCode);

	}
	/**
	 * @param activity
	 * @param path with no "/"
	 * @param requestCode
	 * @return 图片绝对路径加名称
	 */
	public static String getCameraPic_ForResult(Activity activity, String path,int requestCode) {
		
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		String imgName = System.currentTimeMillis() + ".jpg";
		intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(path, imgName)));
		activity.startActivityForResult(intent, requestCode);
		return path+"/"+imgName;
	}

	public static String onActivityResult_GetImgPath_FromMedia(Activity activity, Intent data) {
		Cursor cursor = activity.getContentResolver().query(data.getData(), null, null, null, null);
		cursor.moveToFirst();
		String photopathString = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
		return photopathString;
	}

	/**
	 * 限制最大宽高到二者皆满足的范围
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return 需要缩小的比率
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		int inSampleSizeHeight = Math.round((float) height / (float) reqHeight);
		int inSampleSizeWidth = Math.round((float) width / (float) reqWidth);
		inSampleSize = Math.max(inSampleSizeHeight, inSampleSizeWidth);
		/*
		 * if (height > reqHeight || width > reqWidth) { if (width > height) {
		 * inSampleSize = Math.round((float)height / (float)reqHeight); } else {
		 * inSampleSize = Math.round((float)width / (float)reqWidth); } }
		 */
		return inSampleSize;
	}
	/**
	 * 限制最大宽高到二者皆满足的范围
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return 需要缩小的比率
	 */
	public static void calculateReSizeRate(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		float scaleHeight = (float) reqHeight/(float) height  ;
		float scaleWidth =(float) reqWidth/(float) width  ;
		float scale = Math.min(scaleHeight, scaleWidth);
		options.inSampleSize= (int) (1/scale);
		options.outHeight=(int) (height*scaleHeight);
		options.outWidth=(int) (width*scaleWidth);
		options.inDither=false;    /*不进行图片抖动处理*/
		options.inPreferredConfig=null;  /*设置让解码器以最佳方式解码*/
		/* 下面两个字段需要组合使用 */

		options.inPurgeable = true;

		options.inInputShareable = true;
		/*
		 * if (height > reqHeight || width > reqWidth) { if (width > height) {
		 * inSampleSize = Math.round((float)height / (float)reqHeight); } else {
		 * inSampleSize = Math.round((float)width / (float)reqWidth); } }
		 */
	}
	/**高效加载大图
	 * @param pathPic
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static Bitmap getDecodeSampledBitmapFromResource(String pathPic,int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(pathPic, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		Log.i("options.outHeight:"+options.outHeight);
		Log.i("options.outWidth:"+options.outWidth);
		Log.i("options.inSampleSize:"+options.inSampleSize);
	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    Bitmap bitmap=BitmapFactory.decodeFile(pathPic, options);
		Log.i("bitmap.getHeight():"+bitmap.getHeight());
		Log.i("bitmap.getWidth():"+bitmap.getWidth());
	    return bitmap;
	}

	/**限制最大宽高按比例缩放图片,若未超出目标宽高，则不缩放
	 * @param bm
	 * @param newWidth
	 * @param newHeight
	 * @return
	 */
	public static Bitmap resizeBitmapInLimit(Bitmap bm, int newWidth ,int newHeight){
	   // 获得图片的宽高
	   int width = bm.getWidth();
	   int height = bm.getHeight();
	   if(newHeight>=height && newWidth>=width){
		   return bm;
	   }
	   float scale;
	   // 计算缩放比例
	   float scaleWidth = ((float) newWidth) / width;
	   float scaleHeight = ((float) newHeight) / height;
	   if(scaleHeight>scaleWidth){
		   scale=scaleWidth;
	   }else{
		   scale=scaleHeight;
	   }
	   // 取得想要缩放的matrix参数
	   Matrix matrix = new Matrix();
//	   matrix.postScale(scaleWidth, scaleHeight);
	   matrix.postScale(scale, scale);
	   // 得到新的图片
	   Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
	    return newbm;
	} 
}

package com.cy.File;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.cy.System.UtilSysInfo;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

public class UtilAssets {
	/**
	  * 
	  * @param myContext
	  * @param ASSETS_NAME 要复制的文件名
	  * @param savePath 要保存的路径 
	  * @param saveName 复制后的文件名
	  *  @see #testCopy(Context context)是一个测试例子。
	  */
	public static boolean copyFileFromAssets(Context myContext, String ASSETS_NAME, String savePath, String saveName) {
		new File(savePath).mkdirs();
		String filename = savePath +File.separator+ saveName;
		com.cy.app.Log.w("filename copy to:" + filename);
		try {
			// 如果toFile不存在
			if (!(new File(filename)).exists()) {
				InputStream is = myContext.getResources().getAssets().open(ASSETS_NAME);
				FileOutputStream fos = new FileOutputStream(filename);
				byte[] buffer = new byte[7168];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	 
	 /**
	 * @param context
	 * @param path   a   a/b
	 * @return
	 */
	public static String[] getAssetsFiles(Context context,String path){
		 String[] string=null;
		 try {
			 string= context.getResources().getAssets().list("pics/channelsPic");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return string;
	 }

		/**复制assets内指定文件夹到指定sdcard内文件夹
		 * @param context
		 * @param folderAssets assets文件夹内 a/b
		 * @param folderDestination sdcard内 /a/b
		 * @return
		 */
		public static boolean copyAssetsToLocal(Context context,String folderAssets,String folderDestination) {
//			UtilAssets.copyFileFromAssets(context, "channelsLocal", pathAppFile, "channelsLocal");
			 String[]  arrayFile=  UtilAssets.getAssetsFiles(context, folderAssets);
			 for (int i = 0; i < arrayFile.length; i++) {
				if(!UtilAssets.copyFileFromAssets(context, folderAssets+"/"+arrayFile[i], folderDestination, arrayFile[i]))
					return false;
			}
			 return true;
		}

	    /** 读取assets里的文本文件
	     * @param context
	     * @param fileUrl
	     * @return
	     */
	    public static String getAssetsFileContent(Context context, String fileUrl) {
	        String result = null;
	        AssetManager am = context.getAssets();
	        InputStream is = null;
	        ByteArrayOutputStream baos = null;
	        try {
	            is = am.open(fileUrl);
	            byte[] buffer = new byte[256];
	            int length = 0;
	            baos = new ByteArrayOutputStream();
	            while ((length = is.read(buffer)) > 0) {
	                baos.write(buffer, 0, length);
	            }
	            byte[] data = baos.toByteArray();
	            result = new String(data, "UTF-8");
	            result = result.replaceAll("\r", "");

	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                baos.close();
	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	            try {
	                is.close();
	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	        }
	        return result;
	    }
	    
	    public void testCopy(Context context) {
	        String path=context.getFilesDir().getAbsolutePath();
	           String name="test.txt";
	           copyFileFromAssets(context, name, path, name);
	       }
}

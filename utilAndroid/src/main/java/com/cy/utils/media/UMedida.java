package com.cy.utils.media;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.support.annotation.RequiresApi;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by wangxuechao on 2017/4/11.
 */
@RequiresApi(api = Build.VERSION_CODES.GINGERBREAD_MR1)
public class UMedida {
    private static final String POST_FIX="_snap.jpg";
    // 对于视频，取第一帧作为缩略图，也就是怎样从filePath得到一个Bitmap对象。

    public static File createVideoThumbnail(String filePath) {
        File destFile=null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();

        try {
            retriever.setDataSource(filePath);
            Bitmap bitmap = retriever.getFrameAtTime();
            destFile=new File(getSnapPath(filePath));
            bitmap2File(bitmap,destFile);
        } catch(Exception ex) {
//            Log.e(ex.getMessage());
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        return destFile;
    }

    /**bitmap 保存为文件
     * @param bitmap
     * @param pathNameFile  带文件名的全路径
     */
    public static void bitmap2File(Bitmap bitmap, File pathNameFile){
        if (pathNameFile.exists()){
            pathNameFile.delete();
        }
        pathNameFile.getParentFile().mkdirs();
        try {
            pathNameFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(pathNameFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getSnapPath(String videoPath){
        return videoPath.replace(".mp4","")+POST_FIX;
    }
}

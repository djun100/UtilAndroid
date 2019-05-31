package com.cy.file;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.Toast;

public class SelectFileActivity extends Activity {
    /**
     * 常量
     */
    public final static int FILE_SELECT_CODE = 0x100;
    public final static String ACTIVITY_TYPE = "ACTIVITY_TYPE";

    /**
     * 变量
     */
    private int type = -1;//0-音乐 1-视频
    /**
     * 接口
     */
    private static UtilSelectFile.OnSelectListener sOnSelectListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectFile();
    }

    public static void startActivity(Activity activity, UtilSelectFile.OnSelectListener listener) {
        sOnSelectListener = listener;
        activity.startActivity(new Intent(activity, SelectFileActivity.class));
    }

    public static void startActivity(Activity activity, int type, UtilSelectFile.OnSelectListener listener) {
        sOnSelectListener = listener;
        Intent intent = new Intent(activity, SelectFileActivity.class);
        intent.putExtra(ACTIVITY_TYPE, type);
        activity.startActivity(intent);
    }

    public void finish() {
        sOnSelectListener = null;
        super.finish();
    }

    /**
     * 调用文件选择软件来选择文件
     **/
    public void selectFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        if (type == 0) {
            intent.setType("audio/*");
        } else if (type == 1){
            intent.setType("video/*");
        }else {
            intent.setType("*/*");
        }
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "来，选一个"), FILE_SELECT_CODE
            );
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "未安装文件管理器", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 根据返回选择的文件，来进行上传操作
     **/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            String path;
            Uri uri = data.getData();
            if ("file".equalsIgnoreCase(uri.getScheme())) {//使用第三方应用打开
                path = uri.getPath();
                callback(path);
                return;
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                path = getPath(this, uri);
                callback(path);
            } else {//4.4以下下系统调用方法
                path = getRealPathFromURI(uri);
                callback(path);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void callback(String path) {
        if (sOnSelectListener != null) {
            sOnSelectListener.onSelected(path);
        }
        finish();
    }

    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(null!=cursor&&cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }
        return res;
    }
    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }
    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

}
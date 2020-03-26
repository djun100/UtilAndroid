package com.cy.io;

import android.support.annotation.Nullable;
import android.util.Log;

import com.cy.file.UtilFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by wangxuechao on 2020/3/8.
 */
public class LogWriteMgr {
    private static LogWriterMmap mLogWriterMmap;
    private static File mLogFile;

    /**
     * @param logToSD
     * @param folderPath 如appname/common,最终会生成在appname/common/log下
     */
    public static void init(boolean logToSD, @Nullable String folderPath) {
        _LogFileMgr.init(logToSD, folderPath);
    }

    public static void writeLog(String content) {
        // TODO_cy: 2020/3/15 webview 与在主进程使用mmap会导致webview崩溃？
//        if (UtilApp.isMainProcess()){
//            if (true) return;
//            Log.i("tag","log trace --> 非主线程写日志");
//        }
        if (_LogFileMgr.sDirFileLog == null) init(true, null);

        File file = _LogFileMgr.getLogFile();
        if (mLogFile == null || (!file.getAbsolutePath().equals(mLogFile.getAbsolutePath()))) {
            Log.i("tag","log trace --> 日志文件不存在或需要用新文件");
            mLogFile = file;
            if (mLogWriterMmap != null) {
                Log.i("tag","log trace --> to close mLogWriterMmap");
                mLogWriterMmap.close();
            }
            try {
                mLogWriterMmap = LogWriterMmap.newInstance(file);
                Log.i("tag","log trace --> 创建mLogWriterMmap");
            } catch (Exception e) {
                Log.e("tag","log trace --> 创建mLogWriterMmap 失败");
                e.printStackTrace();
            }
        }
        if (mLogWriterMmap==null){
            Log.i("tag","log trace --> mLogWriterMmap null");
            try {
                mLogWriterMmap = LogWriterMmap.newInstance(file);
                Log.i("tag","log trace --> 再次创建mLogWriterMmap");
            } catch (Exception e) {
                Log.e("tag","log trace --> 再次创建mLogWriterMmap 失败");
                e.printStackTrace();
            }
        }
        if (mLogWriterMmap==null){
            Log.i("tag","log trace --> 怎么还是 mLogWriterMmap null");
        }
        mLogWriterMmap.write(content);
    }

    public static void writeCrash(String content){
        if (_LogFileMgr.sDirFileCrash == null) init(true, null);
        File file = _LogFileMgr.getCrashFile();
        UtilFile.writeUtf8FileContent(file,content+"\n\n");
    }

    public static final SimpleDateFormat CONTENT_FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS ");
    private static final TimeZone mGmt8TimeZone = TimeZone.getTimeZone("GMT+8");

    /**
     * @param msg
     * @param type
     * @return      eg:     01-01 23:11:59.123 V
     */
    public static String getFormatContent(String msg, String type){
        if (CONTENT_FORMAT.getTimeZone().getID() != mGmt8TimeZone.getID()) {
            CONTENT_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        }
        return CONTENT_FORMAT.format(new Date())+type+" "+msg+"\n";
    }

}

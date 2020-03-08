package com.cy.io;

import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
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
        if (_LogFileMgr.sDirFileLog == null) init(true, null);

        File file = _LogFileMgr.getLogFile();
        if (mLogFile == null || (!file.getAbsolutePath().equals(mLogFile.getAbsolutePath()))) {
            mLogFile = file;
            if (mLogWriterMmap != null) {
                mLogWriterMmap.close();
            }
            try {
                mLogWriterMmap = LogWriterMmap.newInstance(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        mLogWriterMmap.write(content);
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

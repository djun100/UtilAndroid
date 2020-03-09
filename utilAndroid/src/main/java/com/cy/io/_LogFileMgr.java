package com.cy.io;

import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.cy.app.UtilApp;
import com.cy.app.UtilContext;
import com.cy.data.UtilArray;
import com.cy.data.UtilCollection;
import com.cy.data.UtilDate;
import com.cy.file.UtilFile;
import com.cy.utils.UtilPermission;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxuechao on 2020/3/7.
 */
public class _LogFileMgr {
    private static final long LOG_FILE_MAX_SIZE = 5 * 1024 * 1024;//5MB;
    private static final int LOG_FILE_MAX_EXIST_DAYS = 7;//7天;
    //创建日志文件的时候才去判断，而非每次写日志都判断，提高性能
    private static final long MIN_FREE_SPACE = LOG_FILE_MAX_SIZE + 2 * 1024 * 1024;//2MB;
    public static File sDirFileLog;
    private static File sCurrLogFile;
    private static boolean sLogToSD;

    /**
     * @param logToSD
     * @param folderPath 如appname/common,最终会生成在appname/common/log下
     */
    public static void init(boolean logToSD, @Nullable String folderPath) {
        sLogToSD = logToSD;
        String tempFolderPath = folderPath;
        if (TextUtils.isEmpty(tempFolderPath)) {
            tempFolderPath = UtilApp.getLastPkgName();
        }
        if (!tempFolderPath.startsWith("/")) tempFolderPath = "/" + tempFolderPath;
        tempFolderPath += "/log";

        if (logToSD) {
            sDirFileLog = new File(Environment.getExternalStorageDirectory() + tempFolderPath);
        } else {
            sDirFileLog = new File(UtilContext.getContext().getFilesDir() + tempFolderPath);
        }
        sDirFileLog.mkdirs();
        if (!sDirFileLog.exists()) {
            if (logToSD) {
                if (UtilPermission.isGranted(UtilPermission.READ_EXTERNAL_STORAGE) &&
                        UtilPermission.isGranted(UtilPermission.READ_EXTERNAL_STORAGE)) {
                    //有权限却创建文件夹失败，不考虑
                } else {
                    // : 2020/3/8 无权限，改为存放到APP私有存储
                    init(false, folderPath);
                    return;
                }
            } else {
                //私有存储下创建文件夹失败，不考虑
            }
        } else {
            //: 2020/3/8 文件夹创建成功
        }

    }

    public static File getLogFile() {
        String name = null;
        if (sCurrLogFile == null || sCurrLogFile.length() >= LOG_FILE_MAX_SIZE) {
            name = findExistLatestLogFileName();

            if (name == null) {
                name = UtilApp.getLastPkgName() + "-" +
                        UtilDate.getDateStrNow(UtilDate.FORMAT_YYYYMMDD_HH_MM_SS) + ".log";
            }
            long curAvailableSize;
            if (sLogToSD) {
                curAvailableSize = UtilFile.getExternalFreeSpace();
            } else {
                curAvailableSize = UtilFile.getInternalFreeSpace();
            }

            if (curAvailableSize < MIN_FREE_SPACE) {
                // TODO: 2020/3/8 剩余空间不足

            }
            File logFile = new File(sDirFileLog + "/" + name);
            if (!logFile.exists()) {
                try {
                    logFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            sCurrLogFile = logFile;
        }

        return sCurrLogFile;
    }

    private static String findExistLatestLogFileName() {
        String[] filenames = sDirFileLog.list();
        if (UtilArray.isEmpty(filenames)) return null;

        List<Long> timemillises = new ArrayList<>();
        for (int i = 0; i < filenames.length; i++) {
            long timemillis = UtilDate.getLong(
                    filenames[i].substring(filenames[i].indexOf("-") + 1, filenames[i].length() - 4),
                    UtilDate.FORMAT_YYYYMMDD_HH_MM_SS);
            if (timemillis > 0 && UtilDate.getTimeDiff(timemillis, System.currentTimeMillis()).days > LOG_FILE_MAX_EXIST_DAYS) {
                new File(filenames[i]).delete();
            } else {
                timemillises.add(timemillis);
            }
        }
        if (UtilCollection.isEmpty(timemillises)) return null;

        UtilCollection.sort(timemillises, true);
        //取时间戳最大的，即最新的文件的名称的时间，从而找到该文件的名字
        long timemillis = timemillises.get(0);
        String name = UtilApp.getLastPkgName() + "-" +
                UtilDate.getDateStr(timemillis, UtilDate.FORMAT_YYYYMMDD_HH_MM_SS) + ".log";

        if (name != null && new File(sDirFileLog + "/" + name).length() >= LOG_FILE_MAX_SIZE) {
            //日志文件大小超限，不使用该文件
            name = null;
        }

        return name;
    }
}

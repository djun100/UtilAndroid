package com.cy.io;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.cy.app.UtilContext;
import com.cy.data.UtilDate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * log使用mappedbyteBuffer异步写文件，待使用时整理
 * 需要关注未手动调用force()时，系统自动写入文件的触发时机
 */
public class LogWriter {

    public static abstract class Level {
        public static final String D = "D"; // 普通debug
        public static final String W = "W"; // 警告
        public static final String E = "E"; // 错误
    }

    private static String defTag = "App";

    private static boolean showLogcat = true;
    private static boolean writeFile = true;

    // 注意申请SD卡读写权限
    private static String logFileDir = Environment.getExternalStorageDirectory().getAbsolutePath() +
            File.separator + "rust" + File.separator + "logs";

    private static String fileName;

    private static List<LogListener> listenerList = new ArrayList<>();

    private static HandlerThread handlerThread;
    private static Handler writerHandler;

    private static final int LOG_FILE_GROW_SIZE = 1024 * 10; // log文件每次增长的大小
    private static long gCurrentLogPos = 0;                  // log文件当前写到的位置 - 注意要单线程处理

    /**
     * 使用前必须调用此方法进行准备
     *
     * @param context 建议传入applicationContext
     * @param fileDir 存放log文件的目录
     */
    public static void prepare(Context context, @NonNull String fileDir, String logFilePrefix) {
        gCurrentLogPos = 0;
        if (TextUtils.isEmpty(fileDir)) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                logFileDir = Environment.getExternalStorageDirectory().getAbsolutePath() +
                        File.separator + "rust" + File.separator + "logs";
            } else {
                logFileDir = context.getFilesDir().getAbsolutePath() +
                        File.separator + "rust" + File.separator + "logs";
            }
        } else {
            logFileDir = fileDir;
        }
        if (null == handlerThread) {
            handlerThread = new HandlerThread("LogWriter");
            handlerThread.start();
        }
        writerHandler = new Handler(handlerThread.getLooper());
        fileName = logFilePrefix + "_"
                + UtilDate.getDateStr(System.currentTimeMillis(),UtilDate.FORMAT_YYYY_MM_DD)
                + ".txt";
        Log.d(defTag, "[prepare] file: " + fileName);
    }

    public static String getLogFileDir() {
        return logFileDir;
    }

    public static String getFileName() {
        return fileName;
    }

    // 退出
    public static void quit() {
        if (writerHandler != null) {
            writerHandler.removeCallbacksAndMessages(null);
        }
        if (handlerThread != null) {
            handlerThread.quit();
        }
    }

    public static void setDefTag(String t) {
        LogWriter.defTag = t;
    }

    public static void setWriteFile(boolean w) {
        LogWriter.writeFile = w;
    }

    public static void d(String content) {
        d(defTag, content, writeFile);
    }

    public static void d(String tag, String content) {
        d(tag, content, writeFile);
    }

    public static void dn(String content) {
        d(defTag, content, false);
    }

    // 不写到文件中
    public static void dn(String tag, String content) {
        d(tag, content, false);
    }

    public static void d(String tag, String content, boolean write) {
        if (showLogcat) {
            Log.d(tag, content);
        }
        tellLog(Level.D, tag, content);
        if (write) {
            if (writerHandler != null) {
                writerHandler.post(new WriteRunnable(tag, content));
            }
        }
    }

    // log级别 WARN - w
    public static void w(String content) {
        w(defTag, content, writeFile);
    }

    public static void w(String tag, String content) {
        w(tag, content, writeFile);
    }

    // 不写到文件中
    public static void wn(String content) {
        w(defTag, content, false);
    }

    // 不写到文件中
    public static void wn(String tag, String content) {
        w(tag, content, false);
    }

    public static void w(String tag, String content, boolean write) {
        if (showLogcat) {
            Log.w(tag, content);
        }
        tellLog(Level.W, tag, content);
        if (write) {
            if (writerHandler != null) {
                writerHandler.post(new WriteRunnable(tag, content));
            }
        }
    }

    public static void e(String content) {
        e(defTag, content);
    }

    public static void e(String tag, String content) {
        e(tag, content, writeFile);
    }

    public static void e(String tag, Exception e) {
        e(tag, e.getMessage(), writeFile);
    }

    // 只打log  不写文件
    public static void en(String tag, String content) {
        e(tag, content, false);
    }

    public static void e(String tag, String content, boolean write) {
        if (showLogcat) {
            Log.e(tag, content);
        }
        tellLog(Level.E, tag, content);
        if (write) {
            if (writerHandler != null) {
                writerHandler.post(new WriteRunnable(tag, content));
            }
        }
    }

    private static void tellLog(String level, String tag, String content) {
        if (null != listenerList) {
            for (LogListener l : listenerList) {
                l.onLog(level, tag, content);
            }
        }
    }

    public static void addListener(LogListener l) {
        if (null == listenerList) {
            listenerList = new ArrayList<>();
        }
        listenerList.add(l);
    }

    public static void removeListener(LogListener l) {
        if (null != listenerList) {
            listenerList.remove(l);
        }
    }

    static class WriteRunnable implements Runnable {
        String mmTag;
        String mmContent;

        WriteRunnable(String tag, String content) {
            this.mmTag = tag;
            this.mmContent = content;
        }

        @Override
        public void run() {
            SimpleDateFormat logTimeFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.CHINA);
            String logContent = logTimeFormat.format(new Date()) + " [" + mmTag + "] " + mmContent + "\r\n";
            try {
                File dir = new File(logFileDir);
                if (!dir.exists()) {
                    boolean mk = dir.mkdirs();
                    Log.d(defTag, "make dir " + mk);
                }
                File eFile = new File(logFileDir + File.separator + fileName);
                byte[] strBytes = logContent.getBytes();
                try {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(eFile, "rw");
                    MappedByteBuffer mappedByteBuffer;
                    final int inputLen = strBytes.length;
                    if (!eFile.exists()) {
                        boolean nf = eFile.createNewFile();
                        Log.d(defTag, "new log file " + nf);
                        mappedByteBuffer = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_WRITE, gCurrentLogPos, LOG_FILE_GROW_SIZE);
                    } else {
                        mappedByteBuffer = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_WRITE, gCurrentLogPos, inputLen);
                    }
                    if (mappedByteBuffer.remaining() < inputLen) {
                        mappedByteBuffer = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_WRITE, gCurrentLogPos, LOG_FILE_GROW_SIZE + inputLen);
//                        Log.d(defTag, "run: grow size ");
                    }
//                    Log.d(defTag, "run: gCurrentLogPos: " + gCurrentLogPos + ", pos: " + mappedByteBuffer.position() + ", remaining: " + mappedByteBuffer.remaining());
                    mappedByteBuffer.put(strBytes);
                    gCurrentLogPos += inputLen;
                } catch (Exception e) {
                    Log.e(defTag, "WriteRunnable run: ", e);
                    if (!eFile.exists()) {
                        boolean nf = eFile.createNewFile();
                        Log.d(defTag, "new log file " + nf);
                    }
                    FileOutputStream os = new FileOutputStream(eFile, true);
                    os.write(logContent.getBytes());
                    os.flush();
                    os.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(defTag, "写log文件出错: ", e);
            }
        }
    }
}

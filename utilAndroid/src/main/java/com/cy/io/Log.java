package com.cy.io;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import com.cy.app.UtilContext;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Log extends KLog {
    public static boolean allowLog = true;

    private Log() {
        super();
    }

    /**
     * traversal bundle with string values.
     *
     * @param bundle
     * @return
     */
    public static String bundle2String(Bundle bundle) {
        if (bundle == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder("");
        Set<String> keys = bundle.keySet();
        for (String key : keys) {
            sb.append(key).append(":").append(bundle.get(key)).append(";");
        }
        return sb.toString();
    }

    public static <K, T> String getMapStr(Map<K, T> map) {
        if (map == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder("");
        for (Map.Entry<K, T> entry : map.entrySet()) {
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append(";");
        }
        return sb.toString();
    }

    public static String getArrayStr(Object[] array) {
        if (array == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < array.length; i++) {
            sb.append("[" + i + "]").append(array[i]).append("\n");
        }
        return sb.toString();
    }

    public static String getListStr(List lists) {
        if (lists == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder("");
        for (Object object : lists) {
            sb.append(object.toString()).append(";");
        }
        return sb.toString();
    }

    public static String intentToString(Intent intent) {
        if (intent == null) return null;
        if (intent.getExtras() != null) {
            return intent.toString() + " EXTRA: " + bundle2String(intent.getExtras());
        } else {
            return intent.toString();
        }
    }


    /**android 的log中：
     * String stackTrace = Log.getStackTraceString(exception);
     * 也是基于此方法，和java通用
     * reference:https://stackoverflow.com/questions/1149703/how-can-i-convert-a-stack-trace-to-a-string
     * @param e
     * @return
     */
    public static String getStackTraceStr(Throwable e){
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    public static String getStackTraceStr() {
        StringBuffer err =new StringBuffer();
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        for (int i = 0; i < stacks.length; i++) {
            String stackContent = stacks[i].toString();
            if (stackContent.startsWith(UtilContext.getContext().getPackageName())){
                err.append("\tat ");
                err.append(stackContent);
                err.append("\n");
            }
        }
        return err.toString();
    }
    //////////////////////////override parent class start///////////////////////////////////

    //////////////////////////override parent class end///////////////////////////////////

    private static final String FILE_SEP       = System.getProperty("file.separator");
    protected static final Format FORMAT         =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS ", Locale.getDefault());
    protected static final String[] T = new String[]{"V", "D", "I", "W", "E", "A","JSON","XML"};
    protected static final String LINE_SEP       = System.getProperty("line.separator");
    private static Builder mBuilder;

    public static void init(Builder builder){
        mBuilder=builder;
        Context context = UtilContext.getContext();
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                && context.getExternalCacheDir() != null)
            builder.dir = context.getExternalCacheDir() + FILE_SEP + "log" + FILE_SEP;
        else {
            builder.dir = context.getCacheDir() + FILE_SEP + "log" + FILE_SEP;
        }
    }

    private static Builder getBuilder(){
        if (mBuilder==null) mBuilder = Builder.getInstance();
        return mBuilder;
    }

    public static class Builder{
        private String dir;
        private String filePrefix;
        private int saveDays;
        private boolean logFileEnable;

        public static Builder getInstance(){
            return new Builder();
        }
        public String getDir() {
            return dir;
        }

        public Builder setDir(String dir) {
            this.dir = dir;
            return this;
        }

        public String getFilePrefix() {
            return filePrefix;
        }

        public Builder setFilePrefix(String filePrefix) {
            this.filePrefix = filePrefix;
            return this;
        }

        public int getSaveDays() {
            return saveDays;
        }

        public Builder setSaveDays(int saveDays) {
            this.saveDays = saveDays;
            return this;
        }

        public boolean getLogFileEnable() {
            return logFileEnable;
        }

        public Builder setLogFileEnable(boolean logFileEnable) {
            this.logFileEnable = logFileEnable;
            return this;
        }
    }

    /**改动处：
     * @param type
     * @param tag
     * @param msg
     */
    protected static void print2File(final int type, final String tag, final String msg) {
        if (!getBuilder().getLogFileEnable()) return;
        Date now = new Date(System.currentTimeMillis());
        String format = FORMAT.format(now);
        String date = format.substring(0, 13).replaceAll(" ","_");
        String time = format.substring(11);
        final String fullPath = getBuilder().getDir() + getBuilder().getFilePrefix() + "-" + date + "_" + T[type - V] + ".txt";
        if (!createOrExistsFile(fullPath)) {
            e("create " + fullPath + " failed!");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(time)
                .append(T[type - V])
                .append("/")
                .append(tag)
                .append(msg)
                .append(LINE_SEP);
        final String content = sb.toString();
        input2File(content, fullPath);
    }

    protected static boolean createOrExistsFile(final String filePath) {
        File file = new File(filePath);
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            deleteDueLogs(filePath);
            boolean isCreate = file.createNewFile();
            if (isCreate) {
                printDeviceInfo(filePath);
            }
            return isCreate;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    private static void deleteDueLogs(String filePath) {
        File file = new File(filePath);
        File parentFile = file.getParentFile();
        File[] files = parentFile.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.matches("^" + getBuilder().getFilePrefix() + "-[0-9]{4}-[0-9]{2}-[0-9]{2}.txt$");
            }
        });
        if (files.length <= 0) return;
        final int length = filePath.length();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            String curDay = filePath.substring(length - 14, length - 4);
            long dueMillis = sdf.parse(curDay).getTime() - getBuilder().getSaveDays() * 86400000L;
            for (final File aFile : files) {
                String name = aFile.getName();
                int l = name.length();
                String logDay = name.substring(l - 14, l - 4);
                if (sdf.parse(logDay).getTime() <= dueMillis) {
                    EXECUTOR.execute(new Runnable() {
                        @Override
                        public void run() {
                            boolean delete = aFile.delete();
                            if (!delete) {
                                e( "delete " + aFile + " failed!");
                            }
                        }
                    });
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void printDeviceInfo(final String filePath) {
        String versionName = "";
        int versionCode = 0;
        Context context = UtilContext.getContext();
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (pi != null) {
                versionName = pi.versionName;
                versionCode = pi.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String time = filePath.substring(filePath.length() - 14, filePath.length() - 4);
        final String head = "************* Log Head ****************" +
                "\nDate of Log        : " + time +
                "\nDevice Manufacturer: " + Build.MANUFACTURER +
                "\nDevice Model       : " + Build.MODEL +
                "\nAndroid Version    : " + Build.VERSION.RELEASE +
                "\nAndroid SDK        : " + Build.VERSION.SDK_INT +
                "\nApp VersionName    : " + versionName +
                "\nApp VersionCode    : " + versionCode +
                "\n************* Log Head ****************\n\n";
        input2File(head, filePath);
    }

    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();

    protected static void input2File(final String input, final String filePath) {
        EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                BufferedWriter bw = null;
                try {
                    bw = new BufferedWriter(new FileWriter(filePath, true));
                    bw.write(input);
                } catch (IOException e) {
                    e.printStackTrace();
                    e( "log to " + filePath + " failed!");
                } finally {
                    try {
                        if (bw != null) {
                            bw.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}

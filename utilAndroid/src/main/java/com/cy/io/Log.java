package com.cy.io;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.cy.app.UtilContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/04/06
 *     desc  : 一个精简、全面、方便的 Android Log 库
 *     compile 'com.blankj:alog:1.8.0'
 * </pre>
 */
public class Log {

    public static final int V = android.util.Log.VERBOSE;
    public static final int D = android.util.Log.DEBUG;
    public static final int I = android.util.Log.INFO;
    public static final int W = android.util.Log.WARN;
    public static final int E = android.util.Log.ERROR;
    public static final int A = android.util.Log.ASSERT;

    @IntDef({V, D, I, W, E, A})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TYPE {
    }

    private static final char[] T = new char[]{'V', 'D', 'I', 'W', 'E', 'A'};

    private static final int FILE = 0x10;
    private static final int JSON = 0x20;
    private static final int XML  = 0x30;

    private static final String FILE_SEP       = System.getProperty("file.separator");
    private static final String LINE_SEP       = System.getProperty("line.separator");
    private static final String TOP_CORNER     = "┌";
    private static final String MIDDLE_CORNER  = "├";
    private static final String LEFT_BORDER    = "│ ";
    private static final String BOTTOM_CORNER  = "└";
    private static final String SIDE_DIVIDER   = "────────────────────────────────────────────────────────";
    private static final String MIDDLE_DIVIDER = "┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄";
    private static final String TOP_BORDER     = TOP_CORNER + SIDE_DIVIDER + SIDE_DIVIDER;
    private static final String MIDDLE_BORDER  = MIDDLE_CORNER + MIDDLE_DIVIDER + MIDDLE_DIVIDER;
    private static final String BOTTOM_BORDER  = BOTTOM_CORNER + SIDE_DIVIDER + SIDE_DIVIDER;
    private static final int    MAX_LEN        = 3000;
    @SuppressLint("SimpleDateFormat")
    private static final Format FORMAT         = new SimpleDateFormat("MM-dd HH:mm:ss.SSS ");
    private static final String NOTHING        = "log nothing";
    private static final String NULL           = "null";
    private static final String ARGS           = "args";
    private static final String PLACEHOLDER    = " ";
    private static Context         sAppContext;
    private static Config          sConfig;
    private static ExecutorService sExecutor;

    protected Log() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static Config init(@NonNull final Context context) {
        sAppContext = context;
        if (sConfig == null) {
            sConfig = new Config();
        }
        return sConfig;
    }

    private static void checkInit(){
        synchronized (Log.class) {
            if (sAppContext==null){
                init(UtilContext.getContext());
            }
        }
    }

    public static Config getConfig() {
        if (sConfig == null) throw new NullPointerException("U should init first.");
        return sConfig;
    }

    public static void v(final Object content) {
        checkInit();
        log(V, sConfig.mGlobalTag,0, content);
    }

    public static void v(int callStackOffset,final Object content) {
        checkInit();
        log(V, sConfig.mGlobalTag,callStackOffset, content);
    }

    public static void v(final String tag, final Object content) {
        checkInit();
        log(V, tag,0, content);
    }

    public static void v(final String tag,int callStackOffset, final Object content) {
        checkInit();
        log(V, tag,callStackOffset, content);
    }

    public static void d(final Object content) {
        checkInit();
        log(D, sConfig.mGlobalTag,0, content);
    }

    public static void d(int callStackOffset,final Object content) {
        checkInit();
        log(D, sConfig.mGlobalTag,callStackOffset, content);
    }

    public static void d(final String tag, final Object content) {
        checkInit();
        log(D, tag,0, content);
    }

    public static void d(final String tag,int callStackOffset, final Object content) {
        checkInit();
        log(D, tag,callStackOffset, content);
    }

    public static void i(final Object content) {
        checkInit();
        log(I, sConfig.mGlobalTag,0, content);
    }

    public static void i(int callStackOffset,final Object content) {
        checkInit();
        log(I, sConfig.mGlobalTag,callStackOffset, content);
    }

    public static void i(final String tag, final Object content) {
        checkInit();
        log(I, tag,0, content);
    }

    public static void i(final String tag,int callStackOffset, final Object content) {
        checkInit();
        log(I, tag,callStackOffset, content);
    }

    public static void w(final Object content) {
        checkInit();
        log(W, sConfig.mGlobalTag,0, content);
    }

    public static void w(int callStackOffset,final Object content) {
        checkInit();
        log(W, sConfig.mGlobalTag, callStackOffset, content);
    }

    public static void w(final String tag, final Object content) {
        checkInit();
        log(W, tag,0, content);
    }

    public static void w(final String tag,int callStackOffset, final Object content) {
        checkInit();
        log(W, tag,callStackOffset, content);
    }

    public static void e(final Object content) {
        checkInit();
        log(E, sConfig.mGlobalTag,0, content);
    }

    public static void e(int callStackOffset,final Object content) {
        checkInit();
        log(E, sConfig.mGlobalTag,callStackOffset, content);
    }

    public static void e(final String tag, final Object content) {
        checkInit();
        log(E, tag,0, content);
    }

    public static void e(final String tag,int callStackOffset, final Object content) {
        checkInit();
        log(E, tag,callStackOffset, content);
    }

    public static void a(final Object content) {
        checkInit();
        log(A, sConfig.mGlobalTag,0, content);
    }

    public static void a(int callStackOffset,final Object content) {
        checkInit();
        log(A, sConfig.mGlobalTag,callStackOffset, content);
    }

    public static void a(final String tag, final Object content) {
        checkInit();
        log(A, tag,0, content);
    }

    public static void a(final String tag,int callStackOffset, final Object content) {
        checkInit();
        log(A, tag,callStackOffset, content);
    }

    public static void file(final Object content) {
        checkInit();
        log(FILE | D, sConfig.mGlobalTag,0, content);
    }

    public static void file(int callStackOffset,final Object content) {
        checkInit();
        log(FILE | D, sConfig.mGlobalTag,callStackOffset, content);
    }

    public static void fileWithType(@TYPE final int type, final Object content) {
        checkInit();
        log(FILE | type, sConfig.mGlobalTag,0, content);
    }

    public static void fileWithType(@TYPE final int type,int callStackOffset, final Object content) {
        checkInit();
        log(FILE | type, sConfig.mGlobalTag,callStackOffset, content);
    }

    public static void file(final String tag, final Object content) {
        checkInit();
        log(FILE | D, tag,0, content);
    }

    public static void file(final String tag,int callStackOffset, final Object content) {
        checkInit();
        log(FILE | D, tag,callStackOffset, content);
    }

    public static void file(@TYPE final int type, final String tag, final Object content) {
        checkInit();
        log(FILE | type, tag,0, content);
    }

    public static void file(@TYPE final int type, final String tag,int callStackOffset, final Object content) {
        checkInit();
        log(FILE | type, tag,callStackOffset, content);
    }

    public static void json(final String content) {
        checkInit();
        log(JSON | D, sConfig.mGlobalTag,0, content);
    }

    public static void json(int callStackOffset,final String content) {
        checkInit();
        log(JSON | D, sConfig.mGlobalTag,callStackOffset, content);
    }

    public static void jsonWithType(@TYPE final int type, final String content) {
        checkInit();
        log(JSON | type, sConfig.mGlobalTag,0, content);
    }

    public static void jsonWithType(@TYPE final int type,int callStackOffset, final String content) {
        checkInit();
        log(JSON | type, sConfig.mGlobalTag,callStackOffset, content);
    }

    public static void json(final String tag, final String content) {
        checkInit();
        log(JSON | D, tag,0, content);
    }

    public static void json(final String tag,int callStackOffset, final String content) {
        checkInit();
        log(JSON | D, tag,callStackOffset, content);
    }

    public static void json(@TYPE final int type, final String tag, final String content) {
        checkInit();
        log(JSON | type, tag,0, content);
    }

    public static void json(@TYPE final int type, final String tag,int callStackOffset, final String content) {
        checkInit();
        log(JSON | type, tag,callStackOffset, content);
    }

    public static void xml(final String content) {
        checkInit();
        log(XML | D, sConfig.mGlobalTag,0, content);
    }

    public static void xml(@TYPE final int type, final String content) {
        checkInit();
        log(XML | type, sConfig.mGlobalTag,0, content);
    }

    public static void xml(final String tag, final String content) {
        checkInit();
        log(XML | D, tag,0, content);
    }

    public static void xml(@TYPE final int type, final String tag, final String content) {
        checkInit();
        log(XML | type, tag,0, content);
    }

    public static void log(final int type, final String tag,int stackOffset, final Object content) {
        if (!sConfig.mLogSwitch || (!sConfig.mLog2ConsoleSwitch && !sConfig.mLog2FileSwitch))
            return;
        int type_low = type & 0x0f, type_high = type & 0xf0;
        if (type_low < sConfig.mConsoleFilter && type_low < sConfig.mFileFilter) return;
        final TagHead tagHead = processTagAndHead(tag,stackOffset);
        String body = processBody(type_high, content);
        if (sConfig.mLog2ConsoleSwitch && type_low >= sConfig.mConsoleFilter && type_high != FILE) {
            print2Console(type_low, tagHead.tag, tagHead.consoleHead, body);
        }
        if ((sConfig.mLog2FileSwitch || type_high == FILE) && type_low >= sConfig.mFileFilter) {
            print2File(type_low, tagHead.tag, tagHead.fileHead + body);
        }
    }

    private static TagHead processTagAndHead(String tag,int stackOffset) {
        if (!sConfig.mTagIsSpace && !sConfig.mLogHeadSwitch) {
            tag = sConfig.mGlobalTag;
        } else {
            final StackTraceElement[] stackTrace = new Throwable().getStackTrace();
//            final int stackIndex = 3 + sConfig.mStackOffset;
            final int stackIndex = 3 + stackOffset;
            if (stackIndex >= stackTrace.length) {
                StackTraceElement targetElement = stackTrace[3];
                final String fileName = getFileName(targetElement);
                if (sConfig.mTagIsSpace && isSpace(tag)) {
                    int index = fileName.indexOf('.');// Use proguard may not find '.'.
                    tag = index == -1 ? fileName : fileName.substring(0, index);
                }
                return new TagHead(tag, null, ": ");
            }
            StackTraceElement targetElement = stackTrace[stackIndex];
            final String fileName = getFileName(targetElement);
            if (sConfig.mTagIsSpace && isSpace(tag)) {
                int index = fileName.indexOf('.');// Use proguard may not find '.'.
                tag = index == -1 ? fileName : fileName.substring(0, index);
            }
            if (sConfig.mLogHeadSwitch) {
                String tName = Thread.currentThread().getName();
                final String head = new Formatter()
                        .format("%s, %s.%s(%s:%d)",
                                tName,
                                targetElement.getClassName(),
                                targetElement.getMethodName(),
                                fileName,
                                targetElement.getLineNumber())
                        .toString();
                final String fileHead = " [" + head + "]: ";
                if (sConfig.mStackDeep <= 1) {
                    return new TagHead(tag, new String[]{head}, fileHead);
                } else {
                    final String[] consoleHead =
                            new String[Math.min(
                                    sConfig.mStackDeep,
                                    stackTrace.length - stackIndex
                            )];
                    consoleHead[0] = head;
                    int spaceLen = tName.length() + 2;
                    String space = new Formatter().format("%" + spaceLen + "s", "").toString();
                    for (int i = 1, len = consoleHead.length; i < len; ++i) {
                        targetElement = stackTrace[i + stackIndex];
                        consoleHead[i] = new Formatter()
                                .format("%s%s.%s(%s:%d)",
                                        space,
                                        targetElement.getClassName(),
                                        targetElement.getMethodName(),
                                        getFileName(targetElement),
                                        targetElement.getLineNumber())
                                .toString();
                    }
                    return new TagHead(tag, consoleHead, fileHead);
                }
            }
        }
        return new TagHead(tag, null, ": ");
    }

    private static String getFileName(final StackTraceElement targetElement) {
        String fileName = targetElement.getFileName();
        if (fileName != null) return fileName;
        // If name of file is null, should add
        // "-keepattributes SourceFile,LineNumberTable" in proguard file.
        String className = targetElement.getClassName();
        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0) {
            className = classNameInfo[classNameInfo.length - 1];
        }
        int index = className.indexOf('$');
        if (index != -1) {
            className = className.substring(0, index);
        }
        return className + ".java";
    }

    private static String processBody(final int type, final Object object) {
        String body = NULL;
        if (object != null) {
                body = object.toString();
                if (type == JSON) {
                    body = formatJson(body);
                } else if (type == XML) {
                    body = formatXml(body);
                }

        }
        return body.length() == 0 ? NOTHING : body;
    }

    private static String formatJson(String json) {
        try {
            if (json.startsWith("{")) {
                json = new JSONObject(json).toString(4);
            } else if (json.startsWith("[")) {
                json = new JSONArray(json).toString(4);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    private static String formatXml(String xml) {
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(xmlInput, xmlOutput);
            xml = xmlOutput.getWriter().toString().replaceFirst(">", ">" + LINE_SEP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xml;
    }

    private static void print2Console(final int type,
                                      final String tag,
                                      final String[] head,
                                      final String msg) {
        if (sConfig.mSingleTagSwitch) {
            StringBuilder sb = new StringBuilder();
            sb.append(PLACEHOLDER).append(LINE_SEP);
            if (sConfig.mLogBorderSwitch) {
                sb.append(TOP_BORDER).append(LINE_SEP);
                if (head != null) {
                    for (String aHead : head) {
                        sb.append(LEFT_BORDER).append(aHead).append(LINE_SEP);
                    }
                    sb.append(MIDDLE_BORDER).append(LINE_SEP);
                }
                for (String line : msg.split(LINE_SEP)) {
                    sb.append(LEFT_BORDER).append(line).append(LINE_SEP);
                }
                sb.append(BOTTOM_BORDER);
            } else {
                if (head != null) {
                    for (String aHead : head) {
                        sb.append(aHead).append(LINE_SEP);
                    }
                }
                sb.append(msg);
            }
            printMsgSingleTag(type, tag, sb.toString());
        } else {
            printBorder(type, tag, true);
            printHead(type, tag, head);
            printMsg(type, tag, msg);
            printBorder(type, tag, false);
        }
    }

    private static void printBorder(final int type, final String tag, boolean isTop) {
        if (sConfig.mLogBorderSwitch) {
            android.util.Log.println(type, tag, isTop ? TOP_BORDER : BOTTOM_BORDER);
        }
    }

    private static void printHead(final int type, final String tag, final String[] head) {
        if (head != null) {
            for (String aHead : head) {
                android.util.Log.println(type, tag, sConfig.mLogBorderSwitch ? LEFT_BORDER + aHead : aHead);
            }
            if (sConfig.mLogBorderSwitch) android.util.Log.println(type, tag, MIDDLE_BORDER);
        }
    }

    private static void printMsg(final int type, final String tag, final String msg) {
        int len = msg.length();
        int countOfSub = len / MAX_LEN;
        if (countOfSub > 0) {
            int index = 0;
            for (int i = 0; i < countOfSub; i++) {
                printSubMsg(type, tag, msg.substring(index, index + MAX_LEN));
                index += MAX_LEN;
            }
            if (index != len) {
                printSubMsg(type, tag, msg.substring(index, len));
            }
        } else {
            printSubMsg(type, tag, msg);
        }
    }

    private static void printMsgSingleTag(final int type, final String tag, final String msg) {
        int len = msg.length();
        int countOfSub = len / MAX_LEN;
        if (countOfSub > 0) {
            if (sConfig.mLogBorderSwitch) {
                android.util.Log.println(type, tag, msg.substring(0, MAX_LEN) + LINE_SEP + BOTTOM_BORDER);
                int index = MAX_LEN;
                for (int i = 1; i < countOfSub; i++) {
                    android.util.Log.println(type, tag, PLACEHOLDER + LINE_SEP + TOP_BORDER + LINE_SEP
                            + LEFT_BORDER + msg.substring(index, index + MAX_LEN)
                            + LINE_SEP + BOTTOM_BORDER);
                    index += MAX_LEN;
                }
                if (index != len) {
                    android.util.Log.println(type, tag, PLACEHOLDER + LINE_SEP + TOP_BORDER + LINE_SEP
                            + LEFT_BORDER + msg.substring(index, len));
                }
            } else {
                int index = 0;
                for (int i = 0; i < countOfSub; i++) {
                    android.util.Log.println(type, tag, msg.substring(index, index + MAX_LEN));
                    index += MAX_LEN;
                }
                if (index != len) {
                    android.util.Log.println(type, tag, msg.substring(index, len));
                }
            }
        } else {
            android.util.Log.println(type, tag, msg);
        }
    }

    private static void printSubMsg(final int type, final String tag, final String msg) {
        if (!sConfig.mLogBorderSwitch) {
            android.util.Log.println(type, tag, msg);
            return;
        }
        StringBuilder sb = new StringBuilder();
        String[] lines = msg.split(LINE_SEP);
        for (String line : lines) {
            android.util.Log.println(type, tag, LEFT_BORDER + line);
        }
    }

    private static void printSubMsg1(final int type, final String tag, final String msg) {
        if (!sConfig.mLogBorderSwitch) {

            return;
        }
        StringBuilder sb = new StringBuilder();
        String[] lines = msg.split(LINE_SEP);
        for (String line : lines) {
            android.util.Log.println(type, tag, LEFT_BORDER + line);
        }
    }

    private static void print2File(final int type, final String tag, final String msg) {
        Date now = new Date(System.currentTimeMillis());
        String format = FORMAT.format(now);
        String date = format.substring(0, 5);
        String time = format.substring(6);
        final String fullPath =
                (sConfig.mDir == null ? sConfig.mDefaultDir : sConfig.mDir)
                        + sConfig.mFilePrefix + "-" + date + ".txt";
        if (!createOrExistsFile(fullPath)) {
            android.util.Log.e("LogUtils", "create " + fullPath + " failed!");
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

    private static boolean createOrExistsFile(final String filePath) {
        File file = new File(filePath);
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            boolean isCreate = file.createNewFile();
            if (isCreate) printDeviceInfo(filePath);
            return isCreate;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void printDeviceInfo(final String filePath) {
        String versionName = "";
        int versionCode = 0;
        try {
            PackageInfo pi = sAppContext
                    .getPackageManager()
                    .getPackageInfo(sAppContext.getPackageName(), 0);
            if (pi != null) {
                versionName = pi.versionName;
                versionCode = pi.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String time = filePath.substring(filePath.length() - 9, filePath.length() - 4);
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

    private static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static void input2File(final String input, final String filePath) {
        if (sExecutor == null) {
            sExecutor = Executors.newSingleThreadExecutor();
        }
        Future<Boolean> submit = sExecutor.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                BufferedWriter bw = null;
                try {
                    bw = new BufferedWriter(new FileWriter(filePath, true));
                    bw.write(input);
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
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
        try {
            if (submit.get()) return;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        android.util.Log.e("LogUtils", "log to " + filePath + " failed!");
    }

    public static class Config {
        private String mDefaultDir;// The default storage directory of android.util.Log.
        private String mDir;       // The storage directory of android.util.Log.
        private String  mFilePrefix        = "util";// The file prefix of android.util.Log.
        private boolean mLogSwitch         = true;  // The switch of android.util.Log.
        private boolean mLog2ConsoleSwitch = true;  // The logcat's switch of android.util.Log.
        private String  mGlobalTag         = null;  // The global tag of android.util.Log.
        private boolean mTagIsSpace        = true;  // The global tag is space.
        private boolean mLogHeadSwitch     = true;  // The head's switch of android.util.Log.
        private boolean mLog2FileSwitch    = false; // The file's switch of android.util.Log.
        private boolean mLogBorderSwitch   = true;  // The border's switch of android.util.Log.
        private boolean mSingleTagSwitch   = true;  // The single tag of android.util.Log.
        private int     mConsoleFilter     = V;     // The console's filter of android.util.Log.
        private int     mFileFilter        = V;     // The file's filter of android.util.Log.
        private int     mStackDeep         = 1;     // The stack's deep of android.util.Log.
        private int     mStackOffset       = 0;     // The stack's offset of android.util.Log.

        private Config() {
            if (mDefaultDir != null) return;
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    && sAppContext.getExternalCacheDir() != null)
                mDefaultDir = sAppContext.getExternalCacheDir() + FILE_SEP + "log" + FILE_SEP;
            else {
                mDefaultDir = sAppContext.getCacheDir() + FILE_SEP + "log" + FILE_SEP;
            }
        }

        public Config setLogSwitch(final boolean logSwitch) {
            mLogSwitch = logSwitch;
            return this;
        }

        public Config setConsoleSwitch(final boolean consoleSwitch) {
            mLog2ConsoleSwitch = consoleSwitch;
            return this;
        }

        public Config setGlobalTag(final String tag) {
            if (isSpace(tag)) {
                mGlobalTag = "";
                mTagIsSpace = true;
            } else {
                mGlobalTag = tag;
                mTagIsSpace = false;
            }
            return this;
        }

        public Config setLogHeadSwitch(final boolean logHeadSwitch) {
            mLogHeadSwitch = logHeadSwitch;
            return this;
        }

        public Config setLog2FileSwitch(final boolean log2FileSwitch) {
            mLog2FileSwitch = log2FileSwitch;
            return this;
        }

        public Config setDir(final String dir) {
            if (isSpace(dir)) {
                mDir = null;
            } else {
                mDir = dir.endsWith(FILE_SEP) ? dir : dir + FILE_SEP;
            }
            return this;
        }

        public Config setDir(final File dir) {
            mDir = dir == null ? null : dir.getAbsolutePath() + FILE_SEP;
            return this;
        }

        public Config setFilePrefix(final String filePrefix) {
            if (isSpace(filePrefix)) {
                mFilePrefix = "util";
            } else {
                mFilePrefix = filePrefix;
            }
            return this;
        }

        public Config setBorderSwitch(final boolean borderSwitch) {
            mLogBorderSwitch = borderSwitch;
            return this;
        }

        public Config setSingleTagSwitch(final boolean singleTagSwitch) {
            mSingleTagSwitch = singleTagSwitch;
            return this;
        }

        public Config setConsoleFilter(@TYPE final int consoleFilter) {
            mConsoleFilter = consoleFilter;
            return this;
        }

        public Config setFileFilter(@TYPE final int fileFilter) {
            mFileFilter = fileFilter;
            return this;
        }

        public Config setStackDeep(@IntRange(from = 1) final int stackDeep) {
            mStackDeep = stackDeep;
            return this;
        }

        public Config setStackOffset(@IntRange(from = 0) final int stackOffset) {
            mStackOffset = stackOffset;
            return this;
        }

        @Override
        public String toString() {
            return "switch: " + mLogSwitch
                    + LINE_SEP + "console: " + mLog2ConsoleSwitch
                    + LINE_SEP + "tag: " + (mTagIsSpace ? "null" : mGlobalTag)
                    + LINE_SEP + "head: " + mLogHeadSwitch
                    + LINE_SEP + "file: " + mLog2FileSwitch
                    + LINE_SEP + "dir: " + (mDir == null ? mDefaultDir : mDir)
                    + LINE_SEP + "filePrefix: " + mFilePrefix
                    + LINE_SEP + "border: " + mLogBorderSwitch
                    + LINE_SEP + "singleTag: " + mSingleTagSwitch
                    + LINE_SEP + "consoleFilter: " + T[mConsoleFilter - V]
                    + LINE_SEP + "fileFilter: " + T[mFileFilter - V]
                    + LINE_SEP + "stackDeep: " + mStackDeep
                    + LINE_SEP + "mStackOffset: " + mStackOffset;
        }
    }

    private static class TagHead {
        String   tag;
        String[] consoleHead;
        String   fileHead;

        TagHead(String tag, String[] consoleHead, String fileHead) {
            this.tag = tag;
            this.consoleHead = consoleHead;
            this.fileHead = fileHead;
        }
    }
}
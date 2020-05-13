package com.cy.io;


import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.cy.io.klog.BaseLog;
import com.cy.io.klog.FileLog;
import com.cy.io.klog.JsonLog;
import com.cy.io.klog.XmlLog;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * This is a Log tool，with this you can the following
 * <ol>
 * <li>use KLog.d(),you could print whether the method execute,and the default tag is current class's name</li>
 * <li>use KLog.d(msg),you could print log as before,and you could location the method with a click in Android Studio Logcat</li>
 * <li>use KLog.json(),you could print json string with well format automatic</li>
 * </ol>
 *
 * @author zhaokaiqiang
 *         github https://github.com/ZhaoKaiQiang/KLog
 *         15/11/17 扩展功能，添加对文件的支持
 *         15/11/18 扩展功能，增加对XML的支持，修复BUG
 *         15/12/8  扩展功能，添加对任意参数的支持
 *         15/12/11 扩展功能，增加对无限长字符串支持
 *         16/6/13  扩展功能，添加对自定义全局Tag的支持,修复内部类不能点击跳转的BUG
 *         16/6/15  扩展功能，添加不能关闭的KLog.debug(),用于发布版本的Log打印,优化部分代码
 *         16/6/20  扩展功能，添加堆栈跟踪功能KLog.trace()
 */
public class KLog {

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String NULL_TIPS = "Log with null object";

    private static final String DEFAULT_MESSAGE = "execute";
    private static final String PARAM = "Param";
    private static final String NULL = "null";
    private static final String TAG_DEFAULT = "→";
    private static final String SUFFIX = ".java";

    public static final int JSON_INDENT = 4;

    public static final int V = 0x1;
    public static final int D = 0x2;
    public static final int I = 0x3;
    public static final int W = 0x4;
    public static final int E = 0x5;
    public static final int A = 0x6;

    private static final int JSON = 0x7;
    private static final int XML = 0x8;

    private static final int STACK_TRACE_INDEX_5 = 5;
    private static final int STACK_TRACE_INDEX_4 = 4;

    private static String mGlobalTag = TAG_DEFAULT;
    private static boolean mIsGlobalTagEmpty = true;
    private static boolean IS_SHOW_LOG = true;
    private static Object[] sObjects = new Object[1];
    public static void init(boolean isShowLog) {
        IS_SHOW_LOG = isShowLog;
    }

    public static void init(boolean isShowLog, @Nullable String tag) {
        IS_SHOW_LOG = isShowLog;
        mGlobalTag = tag;
        mIsGlobalTagEmpty = TextUtils.isEmpty(mGlobalTag);
    }

    public static void v() {
        printLog(0, V, null, DEFAULT_MESSAGE);
    }
    public static void v(boolean enable) {
        if (!enable) return;
        printLog(0, V, null, DEFAULT_MESSAGE);
    }

    public static void v(Object msg) {
        printLog(0, V, null, msg);
    }
    public static void v(boolean enable,Object msg) {
        if (!enable) return;
        printLog(0, V, null, msg);
    }

    public static void v(String tag, Object... objects) {
        printLog(0, V, tag, objects);
    }
    public static void v(boolean enable,String tag, Object... objects) {
        if (!enable) return;
        printLog(0, V, tag, objects);
    }

    public static void d() {
        printLog(0, D, null, DEFAULT_MESSAGE);
    }
    public static void d(boolean enable) {
        if (!enable) return;
        printLog(0, D, null, DEFAULT_MESSAGE);
    }

    public static void d(boolean enable,Object msg) {
        if (!enable) return;
        printLog(0, D, null, msg);
    }

    public static void d(String tag, Object... objects) {
        printLog(0, D, tag, objects);
    }
    public static void d(boolean enable,String tag, Object... objects) {
        if (!enable) return;
        printLog(0, D, tag, objects);
    }

    public static void i() {
        printLog(0, I, null, DEFAULT_MESSAGE);
    }
    public static void i(boolean enable) {
        if (!enable) return;
        printLog(0, I, null, DEFAULT_MESSAGE);
    }

    public static void i(int stackTraceOffset) {
        printLog(stackTraceOffset, I, null, DEFAULT_MESSAGE);
    }
    public static void i(boolean enable,int stackTraceOffset) {
        if (!enable) return;
        printLog(stackTraceOffset, I, null, DEFAULT_MESSAGE);
    }

    public static void i(Object msg) {
        printLog(0, I, null, msg);
    }
    public static void i(boolean enable,Object msg) {
        if (!enable) return;
        printLog(0, I, null, msg);
    }

    public static void i(String tag, Object... objects) {
        printLog(0, I, tag, objects);
    }
    public static void i(boolean enable,String tag, Object... objects) {
        if (!enable) return;
        printLog(0, I, tag, objects);
    }

    public static void w() {
        printLog(0, W, null, DEFAULT_MESSAGE);
    }
    public static void w(boolean enable) {
        if (!enable) return;
        printLog(0, W, null, DEFAULT_MESSAGE);
    }

    public static void w(Object msg) {
        printLog(0, W, null, msg);
    }
    public static void w(boolean enable,Object msg) {
        if (!enable) return;
        printLog(0, W, null, msg);
    }

    public static void w(String tag, Object... objects) {
        printLog(0, W, tag, objects);
    }
    public static void w(boolean enable,String tag, Object... objects) {
        if (!enable) return;
        printLog(0, W, tag, objects);
    }

    public static void e() {
        printLog(0, E, null, DEFAULT_MESSAGE);
    }
    public static void e(boolean enable) {
        if (!enable) return;
        printLog(0, E, null, DEFAULT_MESSAGE);
    }

    public static void e(Object msg) {
        printLog(0, E, null, msg);
    }
    public static void e(boolean enable,Object msg) {
        if (!enable) return;
        printLog(0, E, null, msg);
    }

    public static void e(String tag, Object... objects) {
        printLog(0, E, tag, objects);
    }
    public static void e(boolean enable,String tag, Object... objects) {
        if (!enable) return;
        printLog(0, E, tag, objects);
    }

    public static void a() {
        printLog(0, A, null, DEFAULT_MESSAGE);
    }
    public static void a(boolean enable) {
        if (!enable) return;
        printLog(0, A, null, DEFAULT_MESSAGE);
    }

    public static void a(Object msg) {
        printLog(0, A, null, msg);
    }
    public static void a(boolean enable,Object msg) {
        if (!enable) return;
        printLog(0, A, null, msg);
    }

    public static void a(String tag, Object... objects) {
        printLog(0, A, tag, objects);
    }
    public static void a(boolean enable,String tag, Object... objects) {
        if (!enable) return;
        printLog(0, A, tag, objects);
    }

    public static void json(String jsonFormat) {
        printLog(0, JSON, null, jsonFormat);
    }
    public static void json(boolean enable,String jsonFormat) {
        if (!enable) return;
        printLog(0, JSON, null, jsonFormat);
    }

    public static void json(String tag, String jsonFormat) {
        printLog(0, JSON, tag, jsonFormat);
    }
    public static void json(boolean enable,String tag, String jsonFormat) {
        if (!enable) return;
        printLog(0, JSON, tag, jsonFormat);
    }

    public static void xml(String xml) {
        printLog(0, XML, null, xml);
    }

    public static void xml(boolean enable,String xml) {
        if (!enable) return;
        printLog(0, XML, null, xml);
    }

    public static void xml(String tag, String xml) {
        printLog(0, XML, tag, xml);
    }
    public static void xml(boolean enable,String tag, String xml) {
        if (!enable) return;
        printLog(0, XML, tag, xml);
    }

    public static void file(File targetDirectory, Object msg) {
        printFile(null, targetDirectory, null, msg);
    }

    public static void file(String tag, File targetDirectory, Object msg) {
        printFile(tag, targetDirectory, null, msg);
    }

    public static void file(String tag, File targetDirectory, String fileName, Object msg) {
        printFile(tag, targetDirectory, fileName, msg);
    }

    public static void debug() {
        printDebug(null, DEFAULT_MESSAGE);
    }

    public static void debug(Object msg) {
        printDebug(null, msg);
    }

    public static void debug(String tag, Object... objects) {
        printDebug(tag, objects);
    }

    public static void trace() {
        printStackTrace();
    }

    private static void printStackTrace() {

        if (!IS_SHOW_LOG) {
            return;
        }

        Throwable tr = new Throwable();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        String message = sw.toString();

        String traceString[] = message.split("\\n\\t");
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (String trace : traceString) {
            if (trace.contains("at com.socks.library.KLog")) {
                continue;
            }
            sb.append(trace).append("\n");
        }
        String[] contents = wrapperContent(STACK_TRACE_INDEX_4, null, sb.toString());
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];
        BaseLog.printDefault(D, tag, headString + msg);
    }

    protected static void printLog(int stackTraceOffset, int type, String tagStr, Object... objects) {

        if (!IS_SHOW_LOG) {
            return;
        }

        String[] contents = wrapperContent(STACK_TRACE_INDEX_5 + stackTraceOffset, tagStr, objects);
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];

        switch (type) {
            case V:
            case D:
            case I:
            case W:
            case E:
            case A:
                BaseLog.printDefault(type, tag, headString + msg);
                LogWriteMgr.writeLog(LogWriteMgr.getFormatContent(headString + msg,getType(type)));
                break;
            case JSON:
                JsonLog.printJson(tag, msg, headString);
                break;
            case XML:
                XmlLog.printXml(tag, msg, headString);
                break;
        }
        // TODO_cy: 2019/9/7  add
//        Log.print2File(type,tag,msg);
    }

    private static void printDebug(String tagStr, Object... objects) {
        String[] contents = wrapperContent(STACK_TRACE_INDEX_5, tagStr, objects);
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];
        BaseLog.printDefault(D, tag, headString + msg);
    }


    private static void printFile(String tagStr, File targetDirectory, String fileName, Object objectMsg) {

        if (!IS_SHOW_LOG) {
            return;
        }

        String[] contents = wrapperContent(STACK_TRACE_INDEX_5, tagStr, objectMsg);
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];

        FileLog.printFile(tag, targetDirectory, fileName, headString, msg);
    }

    private static String[] wrapperContent(int stackTraceIndex, String tagStr, Object... objects) {
        if (objects.length==0){
            sObjects[0]=tagStr;
            objects=sObjects;
            tagStr=TAG_DEFAULT;
        }
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        StackTraceElement targetElement = stackTrace[stackTraceIndex];
        String className = targetElement.getClassName();
        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0) {
            className = classNameInfo[classNameInfo.length - 1] + SUFFIX;
        }

        if (className.contains("$")) {
            className = className.split("\\$")[0] + SUFFIX;
        }

        String methodName = targetElement.getMethodName();
        int lineNumber = targetElement.getLineNumber();

        if (lineNumber < 0) {
            lineNumber = 0;
        }

        String tag = (tagStr == null ? TAG_DEFAULT : tagStr);

        if (mIsGlobalTagEmpty && TextUtils.isEmpty(tag)) {
            tag = TAG_DEFAULT;
        } else if (!mIsGlobalTagEmpty) {
            tag = mGlobalTag;
        }

        String msg = (objects == null) ? NULL_TIPS : getObjectsString(objects);
        String headString = "[ (" + targetElement.getFileName() + ":" + lineNumber + ")#" + methodName + " ] ";

        return new String[]{tag, msg, headString};
    }

    private static String getObjectsString(Object... objects) {

        if (objects.length > 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\n");
            for (int i = 0; i < objects.length; i++) {
                Object object = objects[i];
                if (object == null) {
                    stringBuilder.append(PARAM).append("[").append(i).append("]").append(" = ").append(NULL).append("\n");
                } else {
                    stringBuilder.append(PARAM).append("[").append(i).append("]").append(" = ").append(object.toString()).append("\n");
                }
            }
            return stringBuilder.toString();
        } else if (objects.length == 1){
            Object object = objects[0];
            return object == null ? NULL : object.toString();
        } else {
            return NULL;
        }
    }

    private static String getType(int type){
        switch (type){
            case V:
                return "V";
            case D:
                return "D";
            case I:
                return "I";
            case W:
                return "W";
            case E:
                return "E";
            case A:
                return "A";
            default:
                return "";
        }
    }
}

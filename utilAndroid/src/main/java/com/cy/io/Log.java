package com.cy.io;

import android.content.Intent;
import android.os.Bundle;


import com.cy.app.UtilContext;

import java.util.Formatter;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Log extends ALog {
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

    public static void printBundle(Bundle b) {
        w(1, bundle2String(b));
    }

    public static void printIntent(Intent intent) {
        w(1, intentToString(intent));
    }

    public static <K, T> void printMap(Map<K, T> map) {
        if (!allowLog) return;
        if (map == null) {
            return;
        }
        StringBuilder sb = new StringBuilder("");
        for (Map.Entry<K, T> entry : map.entrySet()) {
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append(";");
        }
        w(1, sb.toString());
    }

    public static void printArray(Object[] array) {
        if (!allowLog) return;
        if (array == null) {
            return;
        }
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < array.length; i++) {
            sb.append("[" + i + "]").append(array[i]).append("\n");
        }
        w(1, sb.toString());
    }

    public static void printList(List lists) {
        if (!allowLog) return;
        if (lists == null) {
            return;
        }
        StringBuilder sb = new StringBuilder("");
        for (Object object : lists) {
            sb.append(object.toString()).append(";");
        }
        w(1, sb.toString());
    }

    public static String intentToString(Intent intent) {
        if (intent == null) return null;
        if (intent.getExtras() != null) {
            return intent.toString() + " EXTRA: " + bundle2String(intent.getExtras());
        } else {
            return intent.toString();
        }
    }

    private static void checkInit() {
        synchronized (ALog.class) {
            if (sAppContext == null) {
                init(UtilContext.getContext());
            }
        }
    }

    //////////////////////////override parent class start///////////////////////////////////
    public static void v(final Object content) {
        checkInit();
        log(V, sConfig.mGlobalTag, 1, content);
    }

    public static void v(int callStackOffset, final Object content) {
        checkInit();
        log(V, sConfig.mGlobalTag, callStackOffset + 1, content);
    }

    public static void v(final String tag, final Object content) {
        checkInit();
        log(V, tag, 1, content);
    }

    public static void v(final String tag, int callStackOffset, final Object content) {
        checkInit();
        log(V, tag, callStackOffset + 1, content);
    }

    public static void d(final Object content) {
        checkInit();
        log(D, sConfig.mGlobalTag, 1, content);
    }

    public static void d(int callStackOffset, final Object content) {
        checkInit();
        log(D, sConfig.mGlobalTag, callStackOffset + 1, content);
    }

    public static void d(final String tag, final Object content) {
        checkInit();
        log(D, tag, 1, content);
    }

    public static void d(final String tag, int callStackOffset, final Object content) {
        checkInit();
        log(D, tag, callStackOffset + 1, content);
    }

    public static void i(final Object content) {
        checkInit();
        log(I, sConfig.mGlobalTag, 1, content);
    }

    public static void i(int callStackOffset, final Object content) {
        checkInit();
        log(I, sConfig.mGlobalTag, callStackOffset + 1, content);
    }

    public static void i(final String tag, final Object content) {
        checkInit();
        log(I, tag, 1, content);
    }

    public static void i(final String tag, int callStackOffset, final Object content) {
        checkInit();
        log(I, tag, callStackOffset + 1, content);
    }

    public static void w(final Object content) {
        checkInit();
        log(W, sConfig.mGlobalTag, 1, content);
    }

    public static void w(int callStackOffset, final Object content) {
        checkInit();
        log(W, sConfig.mGlobalTag, callStackOffset + 1, content);
    }

    public static void w(final String tag, final Object content) {
        checkInit();
        log(W, tag, 1, content);
    }

    public static void w(final String tag, int callStackOffset, final Object content) {
        checkInit();
        log(W, tag, callStackOffset + 1, content);
    }

    public static void e(final Object content) {
        checkInit();
        log(E, sConfig.mGlobalTag, 1, content);
    }

    public static void e(int callStackOffset, final Object content) {
        checkInit();
        log(E, sConfig.mGlobalTag, callStackOffset + 1, content);
    }

    public static void e(final String tag, final Object content) {
        checkInit();
        log(E, tag, 1, content);
    }

    public static void e(final String tag, int callStackOffset, final Object content) {
        checkInit();
        log(E, tag, callStackOffset + 1, content);
    }

    public static void a(final Object content) {
        checkInit();
        log(A, sConfig.mGlobalTag, 1, content);
    }

    public static void a(int callStackOffset, final Object content) {
        checkInit();
        log(A, sConfig.mGlobalTag, callStackOffset + 1, content);
    }

    public static void a(final String tag, final Object content) {
        checkInit();
        log(A, tag, 1, content);
    }

    public static void a(final String tag, int callStackOffset, final Object content) {
        checkInit();
        log(A, tag, callStackOffset + 1, content);
    }

    public static void file(final Object content) {
        checkInit();
        log(FILE | D, sConfig.mGlobalTag, 1, content);
    }

    public static void file(int callStackOffset, final Object content) {
        checkInit();
        log(FILE | D, sConfig.mGlobalTag, callStackOffset + 1, content);
    }

    public static void fileWithType(@TYPE final int type, final Object content) {
        checkInit();
        log(FILE | type, sConfig.mGlobalTag, 1, content);
    }

    public static void fileWithType(@TYPE final int type, int callStackOffset, final Object content) {
        checkInit();
        log(FILE | type, sConfig.mGlobalTag, callStackOffset + 1, content);
    }

    public static void file(final String tag, final Object content) {
        checkInit();
        log(FILE | D, tag, 1, content);
    }

    public static void file(final String tag, int callStackOffset, final Object content) {
        checkInit();
        log(FILE | D, tag, callStackOffset + 1, content);
    }

    public static void file(@TYPE final int type, final String tag, final Object content) {
        checkInit();
        log(FILE | type, tag, 1, content);
    }

    public static void file(@TYPE final int type, final String tag, int callStackOffset, final Object content) {
        checkInit();
        log(FILE | type, tag, callStackOffset + 1, content);
    }

    public static void json(final String content) {
        checkInit();
        log(JSON | D, sConfig.mGlobalTag, 1, content);
    }

    public static void json(int callStackOffset, final String content) {
        checkInit();
        log(JSON | D, sConfig.mGlobalTag, callStackOffset + 1, content);
    }

    public static void jsonWithType(@TYPE final int type, final String content) {
        checkInit();
        log(JSON | type, sConfig.mGlobalTag, 1, content);
    }

    public static void jsonWithType(@TYPE final int type, int callStackOffset, final String content) {
        checkInit();
        log(JSON | type, sConfig.mGlobalTag, callStackOffset + 1, content);
    }

    public static void json(final String tag, final String content) {
        checkInit();
        log(JSON | D, tag, 1, content);
    }

    public static void json(final String tag, int callStackOffset, final String content) {
        checkInit();
        log(JSON | D, tag, callStackOffset + 1, content);
    }

    public static void json(@TYPE final int type, final String tag, final String content) {
        checkInit();
        log(JSON | type, tag, 1, content);
    }

    public static void json(@TYPE final int type, final String tag, int callStackOffset, final String content) {
        checkInit();
        log(JSON | type, tag, callStackOffset + 1, content);
    }

    public static void log(final int type, final String tag, int stackOffset, final Object... contents) {
        if (!sConfig.mLogSwitch || (!sConfig.mLog2ConsoleSwitch && !sConfig.mLog2FileSwitch))
            return;
        int type_low = type & 0x0f, type_high = type & 0xf0;
        if (type_low < sConfig.mConsoleFilter && type_low < sConfig.mFileFilter) return;
        final TagHead tagHead = processTagAndHead(tag, stackOffset);
        String body = processBody(type_high, contents);
        if (sConfig.mLog2ConsoleSwitch && type_low >= sConfig.mConsoleFilter && type_high != FILE) {
            print2Console(type_low, tagHead.tag, tagHead.consoleHead, body);
        }
        if ((sConfig.mLog2FileSwitch || type_high == FILE) && type_low >= sConfig.mFileFilter) {
            print2File(type_low, tagHead.tag, tagHead.fileHead + body);
        }
    }

    protected static TagHead processTagAndHead(String tag, int stackOffset) {
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

    //////////////////////////override parent class end///////////////////////////////////
}

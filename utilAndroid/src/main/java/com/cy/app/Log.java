package com.cy.app;

import android.os.Bundle;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**1、类名为Log方便原有项目的log全部由此类替代<br>
 * 2、allowLog为总开关，allowX为logLevel对应开关<br>
 * 3、tag自动产生，格式: customTagPrefix:className.methodName(L:lineNumber),<br>
 * 4、customTagPrefix为空时只输出：className.methodName(L:lineNumber)。
 * <p/>
 */
public class Log {

    public static String customTagPrefix = "";
    public static boolean isUseInternalTag=true;
    private Log() {
    }
	public static LogWriter mLogWriter = null;  
	
    public static boolean allowLog = true;
    
    public static boolean allowD = true;
    public static boolean allowE = true;
    public static boolean allowI = true;
    public static boolean allowV = true;
    public static boolean allowW = true;
    public static boolean allowWtf = true;
    private static String generateTag(StackTraceElement caller) {
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
        return tag;
    }

    public static CustomLogger customLogger;

    public interface CustomLogger {
        void d(String tag, String content);

        void d(String tag, String content, Throwable tr);

        void e(String tag, String content);

        void e(String tag, String content, Throwable tr);

        void i(String tag, String content);

        void i(String tag, String content, Throwable tr);

        void v(String tag, String content);

        void v(String tag, String content, Throwable tr);

        void w(String tag, String content);

        void w(String tag, String content, Throwable tr);

        void w(String tag, Throwable tr);

        void wtf(String tag, String content);

        void wtf(String tag, String content, Throwable tr);

        void wtf(String tag, Throwable tr);
    }
    /**eg:			   String path=context.getFilesDir().getAbsolutePath()";
     * @param pathFile
     */
    public static void initWriter(String pathFile){
		new File(pathFile).mkdirs();
        try {
		        mLogWriter = LogWriter.open(pathFile);
		    } catch (IOException e) {  
		        Log.e(e.getMessage());  
		    } 
    }
    /**data/data/files/log.txt
     */
    public static void initWriter(){
    	initWriter(UContext.getContext().getFilesDir().getAbsolutePath());
    }
    /**Eclipse 经常显示不出来Log.d，不推荐使用
     * @param content
     */
    public static void d(String content) {
        if (!allowLog) return;
        if (!allowD) return;
        content = validateContent(content);
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.d(tag, content);
        } else {
            android.util.Log.d(tag, content);
        }
    }

    public static void d(boolean show,String content) {
        if (show){
            if (!allowLog) return;
            if (!allowD) return;
            content = validateContent(content);
            StackTraceElement caller = getCallerStackTraceElement();
            String tag = generateTag(caller);

            if (customLogger != null) {
                customLogger.d(tag, content);
            } else {
                android.util.Log.d(tag, content);
            }
        }
    }

    private static String validateContent(String content) {
        if (TextUtils.isEmpty(content)){
            content="avoid exception,content is empty";
        }
        return content;
    }

    /**Eclipse 经常显示不出来Log.d，不推荐使用
     * @param content
     */
    public static void writeD(String content) {
        if (!allowLog) return;
        if (!allowD) return;
        content = validateContent(content);
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.d(tag, content);
        } else {
            android.util.Log.d(tag, content);
        }
        initWriterIfNeed();
        mLogWriter.print(tag + " " + content);
    }
    public static void writeD(boolean show,String content) {
        if (show){
            if (!allowLog) return;
            if (!allowD) return;
            content = validateContent(content);
            StackTraceElement caller = getCallerStackTraceElement();
            String tag = generateTag(caller);

            if (customLogger != null) {
                customLogger.d(tag, content);
            } else {
                android.util.Log.d(tag, content);
            }
            initWriterIfNeed();
            mLogWriter.print(tag + " " + content);
        }
    }
    private static void initWriterIfNeed() {
        if (mLogWriter == null) {
            initWriter();
            //当前LogWritter使用的时间已经不是最新的了，换新文件来写log
        }else if (!LogWriter.getFormatCurrentDate().equals(mLogWriter.getmCurrentUseDate())){
            try {
                mLogWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            initWriter();
        }
    }

    /**Eclipse 经常显示不出来Log.d，不推荐使用
     * @param content
     */
    public static void d(String tag,String content) {
        if (!allowLog) return;
        if (!allowD) return;
        content = validateContent(content);
        if(isUseInternalTag) tag=generateTag(getCallerStackTraceElement());
        if (customLogger != null) {
            customLogger.d(tag, content);
        } else {
            android.util.Log.d(tag, content);
        }
    }
    /**Eclipse 经常显示不出来Log.d，不推荐使用
     * @param content
     */
    public static void d(String content, Throwable tr) {
        if (!allowLog) return;
        if (!allowD) return;
        content = validateContent(content);
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.d(tag, content, tr);
        } else {
            android.util.Log.d(tag, content, tr);
        }
    }

    public static void e(String content) {
        if (!allowLog) return;
        if (!allowE) return;
        content = validateContent(content);
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.e(tag, content);
        } else {
            android.util.Log.e(tag, content);
//            Toast.makeText(getApplicationContext(), "程序异常，请退出重试", Toast.LENGTH_SHORT).show();
        }
    }
    public static void e(boolean show,String content) {
        if (show) {
            if (!allowLog) return;
            if (!allowE) return;
            content = validateContent(content);
            StackTraceElement caller = getCallerStackTraceElement();
            String tag = generateTag(caller);

            if (customLogger != null) {
                customLogger.e(tag, content);
            } else {
                android.util.Log.e(tag, content);
//            Toast.makeText(getApplicationContext(), "程序异常，请退出重试", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public static void writeE(String content) {
        if (!allowLog) return;
        if (!allowE) return;
        content = validateContent(content);
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.e(tag, content);
        } else {
            android.util.Log.e(tag, content);
//            Toast.makeText(getApplicationContext(), "程序异常，请退出重试", Toast.LENGTH_SHORT).show();
        }

        initWriterIfNeed();
        mLogWriter.print(tag + " " + content);
    }
    public static void writeE(boolean show,String content) {
        if (show){
            if (!allowLog) return;
            if (!allowE) return;
            content = validateContent(content);
            StackTraceElement caller = getCallerStackTraceElement();
            String tag = generateTag(caller);

            if (customLogger != null) {
                customLogger.e(tag, content);
            } else {
                android.util.Log.e(tag, content);
//            Toast.makeText(getApplicationContext(), "程序异常，请退出重试", Toast.LENGTH_SHORT).show();
            }

            initWriterIfNeed();
            mLogWriter.print(tag + " " + content);
        }
    }
    public static void e(String tag,String content) {
        if (!allowLog) return;
        if (!allowE) return;
        content = validateContent(content);
        if(isUseInternalTag) tag=generateTag(getCallerStackTraceElement());
        if (customLogger != null) {
            customLogger.e(tag, content);
        } else {
            android.util.Log.e(tag, content);
//            Toast.makeText(getApplicationContext(), "程序异常，请退出重试", Toast.LENGTH_SHORT).show();
        }
    }

    public static void e(String content, Throwable tr) {
        if (!allowLog) return;
        if (!allowE) return;
        content = validateContent(content);
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.e(tag, content, tr);
        } else {
            android.util.Log.e(tag, content, tr);
        }
    }

    public static void i(String content) {
        if (!allowLog) return;
        if (!allowI) return;
        content = validateContent(content);
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.i(tag, content);
        } else {
            android.util.Log.i(tag, content);
        }
    }
    public static void i(boolean show,String content) {
        if (show) {
            if (!allowLog) return;
            if (!allowI) return;
            content = validateContent(content);
            StackTraceElement caller = getCallerStackTraceElement();
            String tag = generateTag(caller);

            if (customLogger != null) {
                customLogger.i(tag, content);
            } else {
                android.util.Log.i(tag, content);
            }
        }
    }
    public static void writeI(String content) {
        if (!allowLog) return;
        if (!allowI) return;
        content = validateContent(content);
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.i(tag, content);
        } else {
            android.util.Log.i(tag, content);
        }

        initWriterIfNeed();
        mLogWriter.print(tag + " " + content);
    }
    public static void writeI(boolean show,String content) {
        if (show) {
            if (!allowLog) return;
            if (!allowI) return;
            content = validateContent(content);
            StackTraceElement caller = getCallerStackTraceElement();
            String tag = generateTag(caller);

            if (customLogger != null) {
                customLogger.i(tag, content);
            } else {
                android.util.Log.i(tag, content);
            }

            initWriterIfNeed();
            mLogWriter.print(tag + " " + content);
        }
    }
    public static void i(String tag,String content) {
        if (!allowLog) return;
        if (!allowI) return;
        content = validateContent(content);
        if(isUseInternalTag) tag=generateTag(getCallerStackTraceElement());
        if (customLogger != null) {
            customLogger.i(tag, content);
        } else {
            android.util.Log.i(tag, content);
        }
    }

    public static void i(String content, Throwable tr) {
        if (!allowLog) return;
        if (!allowI) return;
        content = validateContent(content);
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.i(tag, content, tr);
        } else {
            android.util.Log.i(tag, content, tr);
        }
    }

    public static void v(String content) {
        if (!allowLog) return;
        if (!allowV) return;
        content = validateContent(content);
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.v(tag, content);
        } else {
            android.util.Log.v(tag, content);
        }
    }
    public static void v(boolean show,String content) {
        if (show) {
            if (!allowLog) return;
            if (!allowV) return;
            content = validateContent(content);
            StackTraceElement caller = getCallerStackTraceElement();
            String tag = generateTag(caller);

            if (customLogger != null) {
                customLogger.v(tag, content);
            } else {
                android.util.Log.v(tag, content);
            }
        }
    }
    public static void writeV(String content) {
        if (!allowLog) return;
        if (!allowV) return;
        content = validateContent(content);
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.v(tag, content);
        } else {
            android.util.Log.v(tag, content);
        }

        initWriterIfNeed();
        mLogWriter.print(tag + " " + content);
    }
    public static void writeV(boolean show,String content) {
        if (show) {
            if (!allowLog) return;
            if (!allowV) return;
            content = validateContent(content);
            StackTraceElement caller = getCallerStackTraceElement();
            String tag = generateTag(caller);

            if (customLogger != null) {
                customLogger.v(tag, content);
            } else {
                android.util.Log.v(tag, content);
            }

            initWriterIfNeed();
            mLogWriter.print(tag + " " + content);
        }
    }
    public static void v(String tag,String content) {
        if (!allowLog) return;
        if (!allowV) return;
        content = validateContent(content);
        if(isUseInternalTag) tag=generateTag(getCallerStackTraceElement());
        if (customLogger != null) {
            customLogger.v(tag, content);
        } else {
            android.util.Log.v(tag, content);
        }
    }

    public static void v(String content, Throwable tr) {
        if (!allowLog) return;
        if (!allowV) return;
        content = validateContent(content);
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.v(tag, content, tr);
        } else {
            android.util.Log.v(tag, content, tr);
        }
    }

    public static void w(String content) {
        if (!allowLog) return;
        if (!allowW) return;
        content = validateContent(content);
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.w(tag, content);
        } else {
            android.util.Log.w(tag, content);
        }

    }
    public static void w(boolean show,String content) {
        if (show) {
            if (!allowLog) return;
            if (!allowW) return;
            content = validateContent(content);
            StackTraceElement caller = getCallerStackTraceElement();
            String tag = generateTag(caller);

            if (customLogger != null) {
                customLogger.w(tag, content);
            } else {
                android.util.Log.w(tag, content);
            }
        }
    }
    public static void w(Object object) {
        w(object.getClass().getName());
    }

    public static void writeW(String content) {
        if (!allowLog) return;
        if (!allowW) return;
        content = validateContent(content);
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.w(tag, content);
        } else {
            android.util.Log.w(tag, content);
        }
        initWriterIfNeed();
        mLogWriter.print(tag + " " + content);
    }
    public static void writeW(boolean show,String content) {
        if (show) {
            if (!allowLog) return;
            if (!allowW) return;
            content = validateContent(content);
            StackTraceElement caller = getCallerStackTraceElement();
            String tag = generateTag(caller);

            if (customLogger != null) {
                customLogger.w(tag, content);
            } else {
                android.util.Log.w(tag, content);
            }
            initWriterIfNeed();
            mLogWriter.print(tag + " " + content);
        }
    }
    public static void w(String tag,String content) {
        if (!allowLog) return;
        if (!allowW) return;
        content = validateContent(content);
        if(isUseInternalTag) tag=generateTag(getCallerStackTraceElement());
        if (customLogger != null) {
            customLogger.w(tag, content);
        } else {
            android.util.Log.w(tag, content);
        }
    }

    public static void w(String content, Throwable tr) {
        if (!allowLog) return;
        if (!allowW) return;
        content = validateContent(content);
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.w(tag, content, tr);
        } else {
            android.util.Log.w(tag, content, tr);
        }
    }

    public static void w(Throwable tr) {
        if (!allowLog) return;
        if (!allowW) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.w(tag, tr);
        } else {
            android.util.Log.w(tag, tr);
        }
    }


    public static void wtf(String content) {
        if (!allowLog) return;
        if (!allowWtf) return;
        content = validateContent(content);
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.wtf(tag, content);
        } else {
            android.util.Log.wtf(tag, content);
        }
    }

    public static void wtf(String content, Throwable tr) {
        if (!allowLog) return;
        if (!allowWtf) return;
        content = validateContent(content);
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.wtf(tag, content, tr);
        } else {
            android.util.Log.wtf(tag, content, tr);
        }
    }

    public static void wtf(Throwable tr) {
        if (!allowLog) return;
        if (!allowWtf) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.wtf(tag, tr);
        } else {
            android.util.Log.wtf(tag, tr);
        }
    }
    public static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }
    /**
     * traversal bundle with string values.
     * 
     * @param bundle
     * @return
     */
    public static String bundle2String(Bundle bundle) {
    	if (bundle==null) {
			return "";
		}
        StringBuilder sb = new StringBuilder("");
        Set<String> keys = bundle.keySet();
        for (String key : keys) {
            sb.append(key).append(":").append(bundle.get(key)).append(";");
        }
        return sb.toString();
    }
    public static void printBundle(Bundle b){
    	e(bundle2String(b));
    }
    public static <K,T> void printMap(Map<K,T> map){
        if (!allowLog) return;
        if (map==null) {
			return;
		}
    	StringBuilder sb = new StringBuilder("");
    	for (Entry<K, T> entry : map.entrySet()) {
    	    sb.append(entry.getKey()).append(":").append(entry.getValue()).append(";");
    	}
    	e(sb.toString());
    }
    public static void printArray(Object[] array){
        if (!allowLog) return;
        if (array==null) {
			return;
		}
    	StringBuilder sb = new StringBuilder("");
    	for (int i=0;i<array.length;i++) {
    		sb.append("["+i+"]").append(array[i]).append("\n");
    	}
    	e(sb.toString());
    }
    public static void printList(List lists){
        if (!allowLog) return;
        if (lists==null) {
			return;
		}
    	StringBuilder sb = new StringBuilder("");
    	for(Object object:lists){
    		sb.append(object.toString()).append(";");
    	}
    	e(sb.toString());
    }

}

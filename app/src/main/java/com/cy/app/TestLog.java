package com.cy.app;

import android.util.Log;

import java.util.Formatter;

/**
 * Created by cy on 2017/12/31.
 */

public class TestLog {

    public static void showLogUseLogUtil(){
        com.cy.io.Log.w("呵呵");
        com.cy.io.Log.w(1,"parent stack 呵呵");
    }

    public static void showLog(String msg){
        StackTraceElement[] stackTraceElement = Thread.currentThread()
                .getStackTrace();
        int currentIndex = -1;
        for (int i = 0; i < stackTraceElement.length; i++) {
            if (stackTraceElement[i].getMethodName().compareTo("showLog") == 0)
            {
                currentIndex = i + 1;
                break;
            }
        }

        String fullClassName = stackTraceElement[currentIndex].getClassName();
        String className = fullClassName.substring(fullClassName
                .lastIndexOf(".") + 1);
        String methodName = stackTraceElement[currentIndex].getMethodName();
        String lineNumber = String
                .valueOf(stackTraceElement[currentIndex].getLineNumber());

        String head = new Formatter()
                .format("%s.%s(%s:%s)", fullClassName, methodName, className+".java", lineNumber).toString();
        Log.w(head,msg);
        Log.w(":",head+":"+msg);
        Log.w("(" + fullClassName + ".java:" + lineNumber + ")", msg);
        Log.w("(" + fullClassName + ".java:" + lineNumber + ")"+methodName+"()", msg);
        Log.w("(" + fullClassName + ".java:" + lineNumber + ")", "(" + fullClassName + ".java:" + lineNumber + ")"
                +"(" + fullClassName + ".java:" + lineNumber + ")");

    }
}

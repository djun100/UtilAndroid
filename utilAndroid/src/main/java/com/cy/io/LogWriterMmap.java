package com.cy.io;

import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * log使用mappedbyteBuffer异步写文件，待使用时整理
 * 需要关注未手动调用force()时，系统自动写入文件的触发时机
 */
public class LogWriterMmap {

    private File mFile;
    private RandomAccessFile mRandomAccessFile;
    private MappedByteBuffer mMappedByteBuffer;
    private FileChannel mFileChannel;
    private static final int LOG_FILE_GROW_SIZE = 1024 * 10; // log文件每次增长的大小
    private static long gCurrentLogPos = 0;                  // log文件当前写到的位置 - 注意要单线程处理
    private static byte[] sBlankData = null;

    private LogWriterMmap(File file) throws FileNotFoundException {
        if (file == null) throw new FileNotFoundException();
        mFile = file;
        mRandomAccessFile = new RandomAccessFile(mFile, "rw");
        mFileChannel = mRandomAccessFile.getChannel();
        if (sBlankData == null) {
            sBlankData = new byte[LOG_FILE_GROW_SIZE];
            for (int i = 0; i < LOG_FILE_GROW_SIZE; i++) {
                //填充空格
                sBlankData[i] = 32;
            }
        }
        try {
            gCurrentLogPos = mFileChannel.size();
            mMappedByteBuffer = mFileChannel.map(FileChannel.MapMode.READ_WRITE, gCurrentLogPos, LOG_FILE_GROW_SIZE);
            fillSpace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LogWriterMmap newInstance(File file) throws FileNotFoundException {
        LogWriterMmap mLogWriterMmap = new LogWriterMmap(file);
        return mLogWriterMmap;
    }

    public void write(String content) {
        if (content == null) content = "null";
        try {
            byte[] strBytes = content.getBytes();
            try {
                final int inputLen = strBytes.length;
                if (mMappedByteBuffer.remaining() < inputLen) {
                    unmap(mMappedByteBuffer);
                    mMappedByteBuffer = mFileChannel.map(FileChannel.MapMode.READ_WRITE, gCurrentLogPos,
                                    LOG_FILE_GROW_SIZE + inputLen);
                    fillSpace();
                }
                mMappedByteBuffer.put(strBytes);
                gCurrentLogPos += inputLen;
            } catch (Exception e) {
                Log.e("LogWriterMmap", "map失败：\n"+ com.cy.io.Log.getStackTraceStr(e));
                FileOutputStream os = new FileOutputStream(mFile, true);
                os.write(content.getBytes());
                os.write(e.getMessage().getBytes());
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void unmap(MappedByteBuffer mbbi) {
        if (mbbi == null)return;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            try {
                Method m = mbbi.getClass().getDeclaredMethod("free");
                m.setAccessible(true);m.invoke(mbbi);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }else {
            try {
                Class<?> clazz = Class.forName("sun.nio.ch.FileChannelImpl");
                Method m = clazz.getDeclaredMethod("unmap", MappedByteBuffer.class);
                m.setAccessible(true);m.invoke(null, mbbi);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 该文件不再用于继续写入，需要关闭
     */
    public void close(){
        unmap(mMappedByteBuffer);
        UtilIO.closeQuietly(mFileChannel);
        UtilIO.closeQuietly(mRandomAccessFile);
        mMappedByteBuffer = null;
        mFileChannel = null;
        mRandomAccessFile = null;
    }

    private void fillSpace(){
        if (sBlankData != null) {
            mMappedByteBuffer.put(sBlankData);
            mMappedByteBuffer.rewind();
        }
    }
}

package com.cy.File;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;

import com.cy.DataStructure.UtilString;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;

/**
 * @author djun100
 */
public class UtilFile {


    public static String read_UTF8_FileContent(File file) {
        String str = null;
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
            StringBuffer sbread = new StringBuffer();
            while (isr.ready()) {
                sbread.append((char) isr.read());
            }
            isr.close();
            // 从构造器中生成字符串，并替换搜索文本
            str = sbread.toString();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str;
    }

    public static void write_UTF8_FileContent(File file, String content) {
        try {
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            out.write(content.toCharArray());
            out.flush();
            out.close();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 根据路径删除指定的目录或文件，无论存在与否
     *
     * @param sPath 要删除的目录或文件
     * @return 删除成功返回 true，否则返回 false。
     */
    public static boolean deleteFolder(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) { // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) { // 为文件时调用删除文件方法
                return deleteFile(sPath);
            } else { // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }

    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     *
     * @param sPath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
        boolean flag;
        // 如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        // 删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            } // 删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag)
            return false;
        // 删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 覆盖方式复制一个目录及其子目录、文件到另外一个目录
     * @param src
     * @param dest
     * @throws IOException
     * PS： apache commons-io包，FileUtils有相关的方法，IOUtils一般是拷贝文件。

    删除目录结构                    FileUtils.deleteDirectory(dest);
    递归复制目录及文件        FileUtils.copyDirectory(src, dest);
     */
    public static void copyFolder(File src, File dest) throws IOException {

        if (src.isDirectory()) {
            if (!dest.exists()) {
                dest.mkdir();
            }
            String files[] = src.list();
            for (String file : files) {
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                // 递归复制
                copyFolder(srcFile, destFile);
            }
        } else {
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest);

            byte[] buffer = new byte[1024];

            int length;

            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            in.close();
            out.close();
        }
    }
    /**
     * 智能添加路径开头分隔符
     *
     * @param path
     */
    public static String addStartPathSeparator(String path) {
        String pathNew = path;
        if (!path.startsWith("/")) {
            // path="/"+path;不能在字符串自身进行修改操作，String是不可变的
            pathNew = "/" + path;
        }
        return pathNew;
    }

    /**
     * 智能添加路径末尾分隔符
     *
     * @param path
     */
    public static String addEndPathSeparator(String path) {
        String pathNew = path;
        if (!path.endsWith("/")) {
            pathNew = path + "/";
        }
        return pathNew;
    }

    /**
     * 写在/mnt/sdcard/目录下面的文件
     *
     * @param fileName
     * @param content
     */
    public void writeFileSdcard(String fileName, String content) {
        try {
            // FileOutputStream fout = openFileOutput(fileName, MODE_PRIVATE);
            FileOutputStream fout = new FileOutputStream(fileName);
            byte[] bytes = content.getBytes();
            fout.write(bytes);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读在/mnt/sdcard/目录下面的文件
     *
     * @param fileName
     * @return
     */
    public static String readFileSdcard(String fileName) {

        String res = "";

        try {

            FileInputStream fin = new FileInputStream(fileName);

            int length = fin.available();

            byte[] buffer = new byte[length];

            fin.read(buffer);

            res = new String(buffer, "UTF-8");

            fin.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return res;

    }

    /**
     * 根据地址url获取文件名<br>
     * String url="http://192.168.1.15:8080/tvportal/images/c11.jpg";<br>
     * File file=new File(url);<br>
     * System.out.println(file.getName());<br>
     *
     * @param url
     * @return
     */
    public static String getFileName(String url) {
        File file = new File(url);
        return file.getName();
    }

    /**
     * 判断文件的编码格式
     *
     * @param fileName :file
     * @return 文件编码格式
     * @throws Exception
     */
    public static String getFileCode(String fileName) throws Exception {
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(fileName));
        int p = (bin.read() << 8) + bin.read();
        String code = null;

        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            default:
                code = "GBK";
        }

        bin.close();

        Log.d("Parseutil", "find text code ===>" + code);

        return code;
    }

    /**
     * 获取文件后缀
     */
    public static String getFileExtension(File f) {
        if (f != null) {
            String filename = f.getName();
            int i = filename.lastIndexOf('.');
            if (i > 0 && i < filename.length() - 1) {
                return filename.substring(i + 1).toLowerCase();
            }
        }
        return null;
    }

    public static String getUrlFileName(String url) {
        int slashIndex = url.lastIndexOf('/');
        int dotIndex = url.lastIndexOf('.');
        String filenameWithoutExtension;
        if (dotIndex == -1) {
            filenameWithoutExtension = url.substring(slashIndex + 1);
        } else {
            filenameWithoutExtension = url.substring(slashIndex + 1, dotIndex);
        }
        return filenameWithoutExtension;
    }

    public static String getUrlExtension(String url) {
        if (!UtilString.isEmpty(url)) {
            int i = url.lastIndexOf('.');
            if (i > 0 && i < url.length() - 1) {
                return url.substring(i + 1).toLowerCase();
            }
        }
        return "";
    }

    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }
    /**@param format %.20f 小数点后20位*/
    public static String showFileSize(long size,String format) {
        if (TextUtils.isEmpty(format)) format="%.1f";
        final double KB = 1024.0;
        final double MB = KB * KB;
        final double GB = KB * KB * KB;
        String fileSize;
        if (size < KB)
            fileSize = size + "B";
        else if (size < MB)
            fileSize = String.format(format, size / KB) + "K";
        else if (size < GB)
            fileSize = String.format(format, size / MB) + "M";
        else
            fileSize = String.format(format, size / GB) + "G";

        return fileSize;
    }

    /**
     * 显示SD卡剩余空间
     */
    public static String showFileAvailable() {
        String result = "";
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            StatFs sf = new StatFs(Environment.getExternalStorageDirectory().getPath());
            long blockSize = sf.getBlockSize();
            long blockCount = sf.getBlockCount();
            long availCount = sf.getAvailableBlocks();
            return showFileSize(availCount * blockSize,null) + " / " + showFileSize(blockSize * blockCount,"");
        }
        return result;
    }

    /**
     * 多个SD卡时 取外置SD卡
     */
    public static String getExternalStorageDirectory() {
        // 参考文章
        // http://blog.csdn.net/bbmiku/article/details/7937745
        Map<String, String> map = System.getenv();
        String[] values = new String[map.values().size()];
        map.values().toArray(values);
        String path = values[values.length - 1];
        Log.e("nmbb", "FileUtils.getExternalStorageDirectory : " + path);
        if (path.startsWith("/mnt/") && !Environment.getExternalStorageDirectory().getAbsolutePath().equals(path))
            return path;
        else
            return null;
    }

    /**
     * 判断文件是否存在，并且存在的不是一个目录
     *
     * @param path
     * @return
     */
    public static boolean ifExist(String path) {

        return (new File(path).exists()) && !(new File(path).isDirectory());
    }

    /**
     * @param context
     * @return /data/data/cn.corporation.package/files
     */
    public static String getInternalFilePath(Context context) {
        return context.getFilesDir().getAbsolutePath().toString();
    }

    /**
     * 判断是否存在内置SD卡
     *
     * @return
     */
    public static boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 使用Java.nio ByteBuffer字节将一个文件输出至另一文件
     * */
    public static void copyFileByByteBuffer(String sourcePath, String desPath, int bufferSize) {
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            // 获取源文件和目标文件的输入输出流
            in = new FileInputStream(sourcePath);
            out = new FileOutputStream(desPath);
            // 获取输入输出通道
            FileChannel fcIn = in.getChannel();
            FileChannel fcOut = out.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
            while (true) {
                // clear方法重设缓冲区，使它可以接受读入的数据
                buffer.clear();
                // 从输入通道中将数据读到缓冲区
                int r = fcIn.read(buffer);
                if (r == -1) {
                    break;
                }
                // flip方法让缓冲区可以将新读入的数据写入另一个通道
                buffer.flip();
                fcOut.write(buffer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null && out != null) {
                try {
                    in.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 请确保目标文件夹存在，不存在请先新建
     * 使用Java.nio ByteBuffer字节将一个文件输出至另一文件<br>
     * bufferSize=1024;<br>
     * public static void main(String args[]) {<br>
     * long time1 = getTime() ;<br>
     * // readFileByByte(FILE_PATH); //2338,2286<br>
     * // readFileByCharacter(FILE_PATH);//160,162,158<br>
     * // readFileByLine(FILE_PATH); //46,51,57<br>
     * // readFileByBybeBuffer(FILE_PATH);//19,18,17<br>
     * // readFileByBybeBuffer(FILE_PATH);//2048: 11,13<br>
     * // readFileByBybeBuffer(FILE_PATH);//1024*100 100k,711k: 6,6<br>
     * // readFileByBybeBuffer(FILE_PATH);//1024*100 100k,1422k: 7<br>
     * // readFileByBybeBuffer(FILE_PATH);//1024*100 100k,9951k: 49,48<br>
     * // readFileByBybeBuffer(FILE_PATH);//1024*1000 1M,711k: 7,7<br>
     * // readFileByBybeBuffer(FILE_PATH);//1024*1000 1M,1422k: 7,8<br>
     * // readFileByBybeBuffer(FILE_PATH);//1024*1000 1M,9951k: 48,49<br>
     * // readFileByBybeBuffer(FILE_PATH);//1024*10000 10M,711k: 21,13,17<br>
     * // readFileByBybeBuffer(FILE_PATH);//1024*10000 10M,1422k: 16,17,14,15<br>
     * // readFileByBybeBuffer(FILE_PATH);//1024*10000 10M,9951k:64,60<br>
     * <p/>
     * long time2 = getTime() ;<br>
     * System.out.println(time2-time1); }
     */
    public static void copyFileByByteBuffer(String sourcePath, String desPath) {
        FileInputStream in = null;
        FileOutputStream out = null;
        int bufferSize = 1024;
        // TODO: 2015/11/27 如a/b/file file是文件，b文件夹不存在，先新建b.makedirs()
        try {
            // 获取源文件和目标文件的输入输出流
            in = new FileInputStream(sourcePath);
            out = new FileOutputStream(desPath);
            // 获取输入输出通道
            FileChannel fcIn = in.getChannel();
            FileChannel fcOut = out.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
            while (true) {
                // clear方法重设缓冲区，使它可以接受读入的数据
                buffer.clear();
                // 从输入通道中将数据读到缓冲区
                int r = fcIn.read(buffer);
                if (r == -1) {
                    break;
                }
                // flip方法让缓冲区可以将新读入的数据写入另一个通道
                buffer.flip();
                fcOut.write(buffer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null && out != null) {
                try {
                    in.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * sdCard下创建目录,非exsdCard
     *
     * @param _path eg:   /Download  _path代表地址前面需要加“/”
     */
    public static boolean makeDirsInExternalStorageDirectory(String _path) {
        String temp = Environment.getExternalStorageDirectory().getAbsolutePath() + _path;
        return new File(temp).mkdirs();

    }

    public static String getSdPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }
    /**移动文件*/
    public static boolean move(File srcFile, String destPath)
    {
        // Destination directory
        File dir = new File(destPath);

        // Move file to new directory
        boolean success = srcFile.renameTo(new File(dir, srcFile.getName()));

        return success;
    }
    /**移动文件*/
    public static boolean move(String srcFile, String destPath)
    {
        // File (or directory) to be moved
        File file = new File(srcFile);

        // Destination directory
        File dir = new File(destPath);

        // Move file to new directory
        boolean success = file.renameTo(new File(dir, file.getName()));

        return success;
    }

    public static void copy(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("error  ");
            e.printStackTrace();
        }
    }

    public static void copy(File oldfile, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            //File     oldfile     =     new     File(oldPath);
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldfile);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("error  ");
            e.printStackTrace();
        }
    }

    /**
     * 获取文件夹大小
     * @param file File实例
     * @return long
     */
    public static long getFolderSize(java.io.File file) {

        long size = 0;
        try {
            java.io.File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);

                } else {
                    size = size + fileList[i].length();

                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //return size/1048576;
        return size;
    }
}

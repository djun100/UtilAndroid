package com.cy.app;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogWriter {
	
	private static LogWriter mLogWriter;

	private static String mPath;
	
	private static Writer mWriter;
	
	private static SimpleDateFormat df;

	public static String getmCurrentUseDate() {
		return mCurrentUseDate;
	}

	/**
	 * 格式：yyyy-MM-dd_HH
	 */
	private static String mCurrentUseDate;
	private LogWriter(String file_path) {
		this.mPath = file_path;
		this.mWriter = null;
	}
	/**生成文件名规则：packageName.yyyy-MM-dd_HH.log<br/>
	 * 按1小时分割*/
	public static LogWriter open(String file_path) throws IOException {
		if (mLogWriter == null) {
			mCurrentUseDate=getFormatCurrentDate();
			String fileName=UtilContext.getContext().getPackageName()+"."
					+mCurrentUseDate +".log";
				mLogWriter = new LogWriter(file_path+"/"+fileName);

		}
		mWriter = new BufferedWriter(new FileWriter(mPath,true), 2048);
		df = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]: ");
		
		return mLogWriter;
	}
	
	public void close() throws IOException {
		mWriter.close();
		mLogWriter=null;
	}
	
	public void print(String log) {
		try {
			mWriter.write(df.format(new Date()));
			mWriter.write(log);
			mWriter.write("\n");
			mWriter.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(e.getMessage());
		}
	}

	public static String getFormatCurrentDate(){
		return new SimpleDateFormat("yyyy-MM-dd_HH").format(new Date());
	}
}

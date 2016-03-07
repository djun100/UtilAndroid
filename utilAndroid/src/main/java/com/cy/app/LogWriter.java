package com.cy.app;

import java.io.BufferedWriter;
import java.io.File;
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
	
	private LogWriter(String file_path) {
		this.mPath = file_path;
		this.mWriter = null;
	}
	
	public static LogWriter open(String file_path) throws IOException {
		if (mLogWriter == null) {
			File file=new File(file_path);
			File[] files= file.listFiles();
			String fileName=UtilContext.getContext().getPackageName()+"."
					+new SimpleDateFormat("yyyy-MM-dd_HH").format(new Date())
					+".log";
//			for (int i = 0; i < files.length; i++) {
//				if (files[i].getName().equals(fileName)){
//					mLogWriter = new LogWriter(files[i].getName());
//				}
//			}
//			if (mLogWriter==null){
				mLogWriter = new LogWriter(fileName);
//			}

		}
		File mFile = new File(mPath);
		mWriter = new BufferedWriter(new FileWriter(mPath,true), 2048);
		df = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]: ");
		
		return mLogWriter;
	}
	
	public void close() throws IOException {
		mWriter.close();
	}
	
	public void print(String log) {
		try {
			mWriter.write(df.format(new Date()));
			mWriter.write(log);
			mWriter.write("\n");
			mWriter.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(e.getMessage());
		}
	}
	
}

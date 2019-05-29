package com.cy.file;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.util.Log;

import com.cy.system.UtilEnv;
import com.cy.io.UtilIO;

/**1、Java 序列化和反序列化的两个端，被序列化对象的类所处的包名必须一致<br>
 * 	2、private static final long serialVersionUID = 1L;
 * @author djun100
 *
 */
public class UtilSerialization {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
/*		ChannelInfo[] channels= UtilChannelsIO.initChannelData();
		UtilSerialization.serialize(channels, "channels");
		ChannelInfo[] channelLocal= (ChannelInfo[])UtilSerialization.unSerialize( "channels");
		System.out.println(channelLocal[0].getChannelTitle());
		UtilSerialization.serialize(channels, "/iflyor/FlyTv/channelsLocal");
		*/
	}
	/**
	 * @param object
	 * @param filePathName    /a/a.*  a/a.*
	 */
	public static void serialize(Object object, String filePathName) {
		 if(!filePathName.startsWith("/")){
			 filePathName="/"+filePathName;
		 }

		try {
			FileOutputStream fs = new FileOutputStream(UtilEnv.pathRoot+ filePathName);
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(object);
			
			UtilIO.closeQuietly(fs);
			UtilIO.closeQuietly(os);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @param object
	 * @param filePathName    /a/a.*  a/a.*
	 */
	public static void serialize(Object object, String filePathName,String filename) {
		 if(!filePathName.startsWith("/")){
			 filePathName="/"+filePathName;
		 }
		 if (!filePathName.endsWith("/")) {
			 filePathName=filePathName+"/";			
		}
//		 String path= UtilFile.getFilePath(SysInfo.pathRoot+ filePathName);
		 String path= UtilEnv.pathRoot+ filePathName;
		 Log.v("", "将要创建的路径："+path);
		 try {
//			UtilFile.createMultiLevelFolder(path);
			 File file=new File(path);
			 file.mkdirs();
			FileOutputStream fs = new FileOutputStream(path+filename);
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(object);
			os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @param filePathName   a/b.*  /a/b.* *
	 * @return
	 */
	public static Object unSerialize( String filePathName){
		Object tempObject = null;
		 filePathName= UtilFile.addStartPathSeparator(filePathName);
		try {
			FileInputStream fin=new FileInputStream(UtilEnv.pathRoot+filePathName);
			  ObjectInputStream in=new ObjectInputStream(fin);
			  tempObject=(Object) in.readObject();
			  
			  UtilIO.closeQuietly(fin);
			  UtilIO.closeQuietly(in);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tempObject;
	}

}

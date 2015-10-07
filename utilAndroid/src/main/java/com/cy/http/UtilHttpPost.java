package com.cy.http;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;


public class UtilHttpPost {
	String url = "";
	HttpPost httpRequest;
	List<NameValuePair> params;
	String result;//服务器返回字符串
	public UtilHttpPost(String url, Map<String, String> map) {

		// 请求数据
//		 httpRequest = new HttpPost(Variable.accessaddress+"dbutil");
		 httpRequest = new HttpPost(url);
		// 创建参数
		 params = new ArrayList<NameValuePair>();
		//遍历传进来的map参数
		Set set = map.keySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			String value = map.get(key);

			params.add(new BasicNameValuePair(key, value));
		}
/*		不要在构造函数里调用执行方法，因为构造函数只能返回类对象实例
		通过这样来调用commodityId = new Util_HttpPost(Variable.accessaddress
		+ "sendcommodity", getParamsMap()).post();
		 post();  
*/	
	}

	public String post() {
		try {
			 //对提交数据进行编码
			httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			HttpResponse httpResponse=new DefaultHttpClient().execute(httpRequest);
			//获取响应服务器的数据
			if (httpResponse.getStatusLine().getStatusCode()==200) {
				//利用字节数组流和包装的绑定数据
				byte[] data =new byte[2048];
				//先把从服务端来的数据转化成字节数组
				data =EntityUtils. toByteArray((HttpEntity)httpResponse.getEntity());  
				//再创建字节数组输入流对象   
				ByteArrayInputStream bais = new ByteArrayInputStream(data);  
				 //绑定字节流和数据包装流   
				DataInputStream dis = new DataInputStream(bais);  
				  //将字节数组中的数据还原成原来的各种数据类型，代码如下：  
				result=new String(dis.readUTF());
				   System.out.println("Util_HttpPost————服务器返回信息:"+result);
			}
		} catch(ClientProtocolException e){  
            e.printStackTrace();  
        }catch(UnsupportedEncodingException e){  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
		return result;
	}
	public String postIso() {
		try {
			//对提交数据进行编码
			httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.ISO_8859_1));
			HttpResponse httpResponse=new DefaultHttpClient().execute(httpRequest);
			//获取响应服务器的数据
			if (httpResponse.getStatusLine().getStatusCode()==200) {
				//利用字节数组流和包装的绑定数据
				byte[] data =new byte[2048];
				//先把从服务端来的数据转化成字节数组
				data =EntityUtils. toByteArray((HttpEntity)httpResponse.getEntity());  
				//再创建字节数组输入流对象   
				ByteArrayInputStream bais = new ByteArrayInputStream(data);  
				//绑定字节流和数据包装流   
				DataInputStream dis = new DataInputStream(bais);  
				//将字节数组中的数据还原成原来的各种数据类型，代码如下：  
				result=new String(dis.readUTF());
				System.out.println("Util_HttpPost————服务器返回信息:"+result);
			}
		} catch(ClientProtocolException e){  
			e.printStackTrace();  
		}catch(UnsupportedEncodingException e){  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}
		return result;
	}
}

package com.cy.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.Toast;

import com.cy.System.UtilSysInfo;

/**命名的一些感悟：<br>
 * 以UtilLog为例，1、所有工具类以Util开头肯定是记得住的，我忘记了这个类的详细名字，我就可以先敲出util来提示<br>
 * 2、如果我记得住这个类的名字，那就可以直接敲出UtilLog.，避免命名为Log并和它的方法一同列出来，造成方法赛选列表内容混杂过多
 */
public class App {
	public static void filterNetType(Context context) {
		String netWorkType = UtilSysInfo.getNetWorkType(context);
		if (netWorkType.equals("NETWORKTYPE_INVALID")) {
			showErrorAndExit(context,"没有发现网络，缤纷TV将退出！", true);
		} else {
			// new TaskInit().execute();
			if(!netWorkType.equals("NETWORKTYPE_WIFI"))
			{
				Toast.makeText(context, "发现您使用移动网络，缤纷TV会产生较大流量，请注意！", Toast.LENGTH_LONG).show();
			}
		}
	}
	public static void showErrorAndExit( final Context context, String errMessage, boolean exit) {
		final boolean exitActivity = exit;
		AlertDialog.Builder builder = new Builder(context);
		builder.setMessage(errMessage);
		builder.setTitle("出错了");
		builder.setPositiveButton("确认", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if(exitActivity)
					((Activity)context).finish();
			}
		});
		builder.create().show();
	}
}

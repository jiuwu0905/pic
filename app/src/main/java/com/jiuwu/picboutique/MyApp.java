package com.jiuwu.picboutique;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.jiuwu.picboutique.tools.L;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.androidannotations.annotations.EApplication;
import org.androidannotations.annotations.UiThread;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

@EApplication
public class MyApp extends Application {
	// MyApp_

//	public EventBus BUS;

	public RequestQueue mRequestQueue;

	private int Flag_Notify_Num = 0;

	@Override
	public void onCreate() {
		super.onCreate();

//		BUS = new EventBus();
		mRequestQueue = Volley.newRequestQueue(this);

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this).build();
		ImageLoader.getInstance().init(config);

		// JPushInterface.setDebugMode(true);
//		JPushInterface.init(this);

		L.isDebug = true;

		// L.v("进入了ONCREATE");

	}

	@Override
	public void onTerminate() {
		super.onTerminate();
//		ShareSDK.stopSDK(this);
	}

	public int getPushMsgFlag() {
		Flag_Notify_Num++;
		return Flag_Notify_Num;
	}

	// ----------------------------------------------------------------
	// toast 显示
	// ----------------------------------------------------------------
	/**
	 * 显示Toast信息
	 * 
	 * @param msg
	 *            要显示的内容
	 */
	@UiThread
	public void showToast(String msg) {
		toast(msg);
	}

	@UiThread
	public void showToast(int res) {
		toast(getString(res));
	}

	private Toast toast;

	// private Handler handler = new Handler();

	private void toast(String msg) {
		if (toast == null) {
			toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
			// toast.getView().setBackgroundResource(android.R.drawable.toast_frame);
		} else {
			toast.setText(msg);
		}
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	// private class ToastHandler implements Runnable {
	//
	// private String msg;
	//
	// private ToastHandler(String msg) {
	// this.msg = msg;
	// }
	//
	// public void run() {
	//
	// }
	// }

	// /**
	// * 显示Toast信息
	// *
	// * @param msg
	// * 要显示的内容
	// */
	// public void showToast(String msg) {
	// handler.post(new ToastHandler(msg));
	// }
	//
	// public void showToast(int res) {
	// handler.post(new ToastHandler(getString(res)));
	// }

	// ----------------------------------------------------------------
	// 获取手机imei
	// ----------------------------------------------------------------
	public String getDeviceId() {
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String deviceId = tm.getDeviceId();
		// 平板没IMEI 。。为平板生成一个。
		if (deviceId == null || deviceId.trim().length() == 0) {

			deviceId = "35" + Build.BOARD.length() % 10 + Build.BRAND.length()
					% 10 + Build.CPU_ABI.length() % 10 + Build.DEVICE.length()
					% 10 + Build.DISPLAY.length() % 10 + Build.HOST.length()
					% 10 + Build.ID.length() % 10 + Build.MANUFACTURER.length()
					% 10 + Build.MODEL.length() % 10 + Build.PRODUCT.length()
					% 10 + Build.TAGS.length() % 10 + Build.TYPE.length() % 10
					+ Build.USER.length() % 10;
		}
		return deviceId;
	}

	// ----------------------------------------------------------------
	// 获取app 本地版本号
	// ----------------------------------------------------------------

	public int getVersion() {
		try {
			PackageManager manager = this.getPackageManager();
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			int version = info.versionCode;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	// ----------------------------------------------------------------
	// 获取app 本地版本名
	// ----------------------------------------------------------------
	public String getVersionName() {
		PackageManager manager = this.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "1.0";
		}
	}



	// ----------------------------------------------------------------
	// 检测网络连接状态
	// ----------------------------------------------------------------
	/** 检测网络连接状态 */
	public boolean checkNetwork() {
		ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED)
						return true;
				}
		}
		return false;
	}

	public String getFilePath() {
		return Environment.getExternalStorageDirectory() + File.separator
				+ "jumper" + File.separator;
	}

	public String getImageFilePath() {
		return Environment.getExternalStorageDirectory() + File.separator
				+ "京柏天使之音" + File.separator;
	}

	
	private List<Activity> activitys = new LinkedList<Activity>();
	// 添加Activity到容器中
	public void addActivity(Activity activity) {
		if (activitys != null && activitys.size() > 0) {
			if (!activitys.contains(activity)) {
				activitys.add(activity);
			}
		} else {
			activitys.add(activity);
		}
	}

	// 遍历所有Activity并finish
	public void exit() {
		if (activitys != null && activitys.size() > 0) {
			for (Activity activity : activitys) {
				activity.finish();
			}
		}
		// System.exit(0);
	}
}

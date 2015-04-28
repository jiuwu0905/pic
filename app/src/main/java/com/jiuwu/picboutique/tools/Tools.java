package com.jiuwu.picboutique.tools;

import android.content.Context;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {
	
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dp2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	
	public static int spTopx(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
	
	
	
	/**
	 * 手机号码验证
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		// Pattern p =
		// Pattern.compile("^((13［0-9］)|(15［^4，\\D］)|(18［0，5-9］))\\d{8}$");
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	
	/**
	 * 此处将 时间： 改为  - 是因为。有些手机不支持命名中包含：
	 * @return
	 */
	public static String getDataString(){
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		return sdf.format(c.getTime());
	}

	
	public static String getDataString_(){
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(c.getTime());
	}
	
	public static String getRecordeString(String str){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM月dd日 HH:mm");
		try {
			return sdf2.format(sdf.parse(str));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sdf2.format(new Date());
	}
	
	public static String getRecordeString_(String str){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM月dd日 HH:mm");
		try {
			return sdf2.format(sdf.parse(str));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sdf2.format(new Date());
	}
	
	public static String formatNumber(int num) {
		return num >= 10 ? (num + "") : ("0" + num);
	}
	
	
	public static String getDeviceInfo(Context context) {
	    try{
	      org.json.JSONObject json = new org.json.JSONObject();
	      android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
	          .getSystemService(Context.TELEPHONY_SERVICE);
	  
	      String device_id = tm.getDeviceId();
	      
	      android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);
	          
	      String mac = wifi.getConnectionInfo().getMacAddress();
	      json.put("mac", mac);
	      
	      if( TextUtils.isEmpty(device_id) ){
	        device_id = mac;
	      }
	      
	      if( TextUtils.isEmpty(device_id) ){
	        device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
	      }
	      
	      json.put("device_id", device_id);
	      
	      return json.toString();
	    }catch(Exception e){
	      e.printStackTrace();
	    }
	  return null;
	}
	
}

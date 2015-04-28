package com.jiuwu.picboutique.unit;

import java.net.URLEncoder;

import org.json.JSONObject;

import com.jiuwu.picboutique.tools.L;

public class UrlAdr {
	private static final String Tag = "Terry";

	// public static final String SERVER_HEAD =
	// "http://10.0.2.124:8080/mobile/api/handler.do";
	// public static final String SERVER_HEAD =
	// "http://120.24.66.123/mobile/api/handler.do";
	public static final String SERVER_HEAD = "http://mobile.jumper-health.com/mobile/api/handler.do";

	public static final String THRIFT_ADDR = "10.0.2.124";

	public static final int THRIFT_PORT = 7008;

	public static final String METHOD_KEY = "*JUMPER*";

	public static final String SIGNED_KEYS = "JUMPER2014API";

	public static String GetJson(String... strs) throws Exception {
		String json = "";
		JSONObject obj = new JSONObject();
		for (int i = 0; i < strs.length; i++) {
			obj.accumulate(strs[i], strs[i + 1]);
			i++;
		}
		json = obj.toString();
		return json;
	}

	public String getJson(String userName) {
		// return userNmae;
		return "{\"userName\":\"" + userName + "\"}";
	}

	/**
	 * 获取JSON
	 * 
	 * @param params
	 *            "[键名]", "[键值]", ...
	 * @return 经过3DES加密的JSON
	 * @throws Exception
	 */
	private static String GetParams(Object... params) throws Exception {
		JSONObject obj = new JSONObject();
		for (int i = 0; i < params.length; i++) {
			obj.accumulate(params[i].toString(), params[i + 1]);
			i++;
		}
		String json = obj.toString();
		L.e(Tag, "====================== Params ======================");
		L.d(Tag, json);
		return DES.Encrypt(URLEncoder.encode(json, "utf-8"), METHOD_KEY);
	}

	/**
	 * //获取设订单 "jumper.shop.getorder": "order_num=订单号\n"
	 */
	public static String jumper_shop_getorder(String order_num) {
		String Method = "jumper.shop.getorder";
		String Params;
		try {
			Params = GetParams("order_num", order_num);
			return GetUrl(Method, Params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取拼接完成的网址
	 * 
	 * @param Method
	 *            方法名
	 * @param Params
	 *            经过3DES加密的参数
	 * @return 网址
	 * @throws Exception
	 */
	private static String GetUrl(String Method, String Params) throws Exception {

		String Sign;
		String url;

		if (Params == null) {
			Sign = MD5.getMd5Value(SIGNED_KEYS + "method" + Method + SIGNED_KEYS);
			url = SERVER_HEAD + "?" + "method=" + Method + "&sign=" + Sign;
		} else {
			Sign = MD5.getMd5Value(SIGNED_KEYS + "method" + Method + "params" + Params + SIGNED_KEYS);
			url = SERVER_HEAD + "?" + "method=" + Method + "&params=" + Params + "&sign=" + Sign;
		}
		L.e(Tag, "====================== url ======================");
		L.d(Tag, url);
		return url;
	}

}

/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.volley.toolbox;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;

import com.android.volley.JsonError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jiuwu.picboutique.tools.L;
import com.jiuwu.picboutique.unit.DES;
import com.jiuwu.picboutique.unit.UrlAdr;

/**
 * A request for retrieving a T type response body at a given URL that also
 * optionally sends along a JSON body in the request specified.
 * 
 * @param <T>
 *            JSON type of response expected
 */
public class GsonListRequest<T> extends Request<T> {

	private final Listener<T> mListener;

	private Type type;

	private boolean decryption;

	public GsonListRequest(String url, Type type, Listener<T> listener,
			ErrorListener errorListener) {
		this(Method.DEPRECATED_GET_OR_POST, url, type, listener, errorListener);
	}
	
	
	public GsonListRequest(String url, Type type, Listener<T> listener,
			ErrorListener errorListener,boolean decryption) {
		this(Method.DEPRECATED_GET_OR_POST, url, type, listener, errorListener,decryption);
	}

	public GsonListRequest(int method, String url, Type type,
			Listener<T> listener, ErrorListener errorListener) {
		this(method, url, type, listener, errorListener, false);
	}

	public GsonListRequest(int method, String url, Type type,
			Listener<T> listener, ErrorListener errorListener,
			boolean decryption) {
		super(method, url, errorListener);
		mListener = listener;
		this.type = type;
		this.decryption = decryption;
	}

	@Override
	protected void deliverResponse(T response) {
		mListener.onResponse(response);
	}

	// @Override
	// abstract protected Response<T> parseNetworkResponse(NetworkResponse
	// response);

	@SuppressWarnings("unchecked")
	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		// TODO Auto-generated method stub
		try {
			String jsonString = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			if (decryption) {
				jsonString = URLDecoder.decode(DES.Decrypt(jsonString, UrlAdr.METHOD_KEY), "utf-8");
			}
			L.e( "======================= Result ===================");
			L.d( jsonString);
			return (Response<T>) Response.success(
					new Gson().fromJson(jsonString, type),HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException je) {
			return Response.error(new JsonError(je));
		} catch (Exception e) {
			return Response.error(new ParseError(e));
		}
	}

}

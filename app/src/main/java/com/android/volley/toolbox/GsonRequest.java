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

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * A request for retrieving a T type response body at a given URL that also
 * optionally sends along a JSON body in the request specified.
 *
 * @param <T> JSON type of response expected
 */
public  class GsonRequest<T> extends Request<T> {

    private final Listener<T> mListener;

    private  Class<T> clazz;
    
    /**
     * Deprecated constructor for a JsonRequest which defaults to GET unless {@link #getPostBody()}
     * or {@link #getPostParams()} is overridden (which defaults to POST).
     *
     * @deprecated Use {@link #JsonRequest(int, String, String, Listener, ErrorListener)}.
     */
    public GsonRequest(String url,  Class<T> clazz, Listener<T> listener,
            ErrorListener errorListener) {
        this(Method.DEPRECATED_GET_OR_POST, url, clazz, listener, errorListener);
    }

    public GsonRequest(int method, String url,  Class<T> clazz, Listener<T> listener,
            ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
        this.clazz = clazz;
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

//    @Override
//    abstract protected Response<T> parseNetworkResponse(NetworkResponse response);



	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		// TODO Auto-generated method stub
		 try {
	            String jsonString =
	                new String(response.data, HttpHeaderParser.parseCharset(response.headers));
	            return Response.success(new Gson().fromJson(jsonString, clazz),
	                    HttpHeaderParser.parseCacheHeaders(response));
	        } catch (UnsupportedEncodingException e) {
	            return Response.error(new ParseError(e));
	        } catch (JsonSyntaxException e) {
	            return Response.error(new ParseError(e));
	        }
	}


}

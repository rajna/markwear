package com.newmark.tools;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.conn.ConnectTimeoutException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * http工具
 * 
 * @author ilvel_000
 *
 */
public class HttpUtil {
	private static AsyncHttpClient client = new AsyncHttpClient(); // 实例话对象

	static {
		client.setTimeout(10000); // 设置链接超时，如果不设置，默认为10s
	}

	public static void get(String urlString, AsyncHttpResponseHandler res) {// 用一个完整url获取一个string对象
		client.get(urlString, res);
	}

	public static void get(String urlString, RequestParams params,
			AsyncHttpResponseHandler res) {// url里面带参数
		client.get(urlString, params, res);
	}

	public static void get(String urlString, JsonHttpResponseHandler res) {// 不带参数，获取json对象或者数组
		client.get(urlString, res);
	}

	public static void get(String urlString, RequestParams params,
			JsonHttpResponseHandler res) {// 带参数，获取json对象或者数组
		client.get(urlString, params, res);
	}

	public static void get(String uString, BinaryHttpResponseHandler bHandler) {// 下载数据使用，会返回byte数据
		client.get(uString, bHandler);
	}

	public static void get(String uString, FileAsyncHttpResponseHandler fHandler) {// 下载数据使用，会返回byte数据
		client.get(uString, fHandler);
	}

	public static void get(String uString, TextHttpResponseHandler tHandler) {// 下载数据使用，会返回byte数据
		client.get(uString, tHandler);
	}

	/**
	 * 发送post请求
	 * 
	 * @param uString
	 * @param params
	 *            如何是上传参数文件则params.put("filename",file);
	 * @param tHandler
	 */
	public static void post(String uString, RequestParams params,
			TextHttpResponseHandler tHandler) {
		if (params == null) {
			client.post(uString, tHandler);
		} else {
			client.post(uString, params, tHandler);
		}
	}

	public static AsyncHttpClient getClient() {
		return client;
	}

	@SuppressWarnings("static-access")
	public static void allowRetry() {
		client.allowRetryExceptionClass(ConnectException.class);
		client.allowRetryExceptionClass(ConnectTimeoutException.class);
		client.allowRetryExceptionClass(SocketTimeoutException.class);
	}

	@SuppressWarnings("static-access")
	public static void blockRetry() {
		client.blockRetryExceptionClass(ConnectException.class);
		client.blockRetryExceptionClass(ConnectTimeoutException.class);
		client.blockRetryExceptionClass(SocketTimeoutException.class);
	}

}

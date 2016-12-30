package com.ofweek.live.core.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewHttpClient {

	private static final HttpParams params;

	private static final Logger logger = LoggerFactory.getLogger(NewHttpClient.class);

	static {
		params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 2000); // 设置连接超时
		HttpConnectionParams.setSoTimeout(params, 3000); // 设置请求超时
	}

	/**
	 * post请求地址
	 * 
	 * @param url
	 */
	public static String post(String url) {
		return post(url, null, false);
	}

	/**
	 * post请求地址
	 * 
	 * @param url
	 * @param acceptJson
	 *            是否接受json数据
	 */
	public static String post(String url, boolean acceptJson) {
		return post(url, null, acceptJson);
	}

	/**
	 * post请求地址
	 * 
	 * @param url
	 * @param params
	 *            请求参数
	 */
	public static String post(String url, Map<String, Object> params) {
		return post(url, params, false);
	}

	/**
	 * post请求地址
	 * 
	 * @param url
	 * @param params
	 *            请求参数
	 * @param acceptJson
	 *            是否接受json数据
	 */
	public static String post(String url, Map<String, Object> params, boolean acceptJson) {
		logger.info("create httpPost:" + url);
		HttpClient httpclient = new DefaultHttpClient();
		String body = null;
		try {
			HttpPost post = postForm(url, params);
			body = invoke(httpclient, post, acceptJson);
		} catch (UnknownHostException e) {
			logger.error("发送post请求到地址={}失败,域名解析出错", url);
		} catch (SocketTimeoutException e) {
			logger.error("发送post请求到地址={}失败,读取数据超时", url);
		} catch (Exception e) {
			logger.error("发送post请求到地址={}失败", url);
			logger.error("详细信息：", e);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return body;
	}

	/**
	 * get请求地址
	 * 
	 * @param url
	 */
	public static String get(String url) {
		return get(url, true);
	}

	public static <T> T get(String url, Class<T> objClass) {
		String jsonStr = get(url);
		T result = null;
		try {
			result = FastJsonUtils.parseObject(jsonStr.trim(), objClass);
		} catch (Exception e) {
			logger.error("json解析为对象失败, url={}", url);
			logger.error("responseText={}", jsonStr);
		}
		return result;
	}

	/**
	 * get请求地址
	 * 
	 * @param url
	 * @param acceptJson
	 *            是否接受json数据
	 */
	public static String get(String url, boolean acceptJson) {
		logger.info("create httpGet:" + url);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String body = null;
		try {
			HttpGet get = new HttpGet(url);
			body = invoke(httpClient, get, acceptJson);
		} catch (UnknownHostException e) {
			logger.error("发送get请求到地址={}失败,域名解析出错", url);
		} catch (SocketTimeoutException e) {
			logger.error("发送get请求到地址={}失败,读取数据超时", url);
		} catch (Exception e) {
			logger.error("发送get请求到地址={}失败", url);
			logger.error("详细信息：", e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return body;
	}
	
	/**
	 * get请求地址,获取全部响应信息
	 * 
	 * @param url
	 * @param acceptJson
	 *            是否接受json数据
	 */
	public static FullResponse getAll(String url, boolean acceptJson) {
		logger.info("create httpGet:" + url);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		FullResponse response = null;
		try {
			HttpGet get = new HttpGet(url);
			String body = invoke(httpClient, get, acceptJson);
			CookieStore cookieStore = httpClient.getCookieStore();
			List<Cookie> cookies = cookieStore.getCookies();
			response = new FullResponse(body, cookies);
		} catch (Exception e) {
			logger.error("发送get请求到地址={}失败", url);
			logger.error("详细信息：", e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return response;
	}

    /**
	 * post请求地址,获取全部响应信息
	 *
	 * @param url
	 * @param params
	 * @param acceptJson
	 *            是否接受json数据
	 */
	public static FullResponse postAll(String url, Map<String, Object> params, boolean acceptJson) {
		logger.info("create httpPost:" + url);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		FullResponse response = null;
		try {
			String body = invoke(httpClient, postForm(url, params), acceptJson);
			CookieStore cookieStore = httpClient.getCookieStore();
			List<Cookie> cookies = cookieStore.getCookies();
			response = new FullResponse(body, cookies);
		} catch (Exception e) {
			logger.error("发送post请求到地址={}失败", url);
			logger.error("详细信息：", e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return response;
	}

	private static String invoke(HttpClient httpClient, HttpUriRequest request, boolean acceptJson)
			throws ClientProtocolException, IOException {
		request.setParams(params);
		setCommonHeader(request);
		if (acceptJson) {
			request.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
			request.setHeader("X-Requested-With", "XMLHttpRequest");
		} else {
			request.setHeader("Accept", "*/*");
		}

		HttpResponse response = httpClient.execute(request);
		Header[] headers = response.getAllHeaders();
		boolean isGzip = false;
		for (int i = 0; i < headers.length; i++) {
			if ("Content-Encoding".equals(headers[i].getName()) && "gzip".equals(headers[i].getValue())) {
				isGzip = true;
				break;
			}
		}

		InputStream is = null;
		String body = null;
		try {
			is = response.getEntity().getContent();
			if (isGzip) {
				body = zipInputStream(is);
			} else {
				body = readInputStream(is);
			}
		} catch (IOException e) {
			logger.error("读取请求内容失败", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
		logger.debug(body);
		return body;
	}

	private static HttpPost postForm(String url, Map<String, Object> params) {
		HttpPost httpost = new HttpPost(url);

		if (params != null) {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			Set<String> keySet = params.keySet();
			for (String key : keySet) {
				nvps.add(new BasicNameValuePair(key, toString(params.get(key))));
			}
			try {
				if (url.contains("www.smartlifein.com")) {
					httpost.setEntity(new UrlEncodedFormEntity(nvps, "GBK"));
				} else {
					httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		return httpost;
	}
	
	private static String toString(Object obj){
		return (obj == null) ? "" : obj.toString();	
	}

	private static void setCommonHeader(HttpUriRequest request) {
		request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:34.0) Gecko/20100101 Firefox/34.0");
		request.setHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		request.setHeader("Accept-Encoding", "gzip, deflate");
		request.setHeader("Connection", "keep-alive");
		request.setHeader("Cache-Control", "no-cache");
	}

	/**
	 * 处理返回文件流
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	private static String readInputStream(InputStream is) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		StringBuffer buffer = new StringBuffer();
		String line;
		while ((line = in.readLine()) != null)
			buffer.append(line + "\n");
		return buffer.toString();
	}

	/**
	 * 处理gzip,deflate返回流
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	private static String zipInputStream(InputStream is) throws IOException {
		GZIPInputStream gzip = new GZIPInputStream(is);
		BufferedReader in = new BufferedReader(new InputStreamReader(gzip, "UTF-8"));
		StringBuffer buffer = new StringBuffer();
		String line;
		while ((line = in.readLine()) != null)
			buffer.append(line + "\n");
		return buffer.toString();
	}
	
	public static class FullResponse {
		
		private String body;
		
		private List<Cookie> cookies;

		public FullResponse(String body, List<Cookie> cookies) {
			super();
			this.body = body;
			this.cookies = cookies;
		}

		public String getBody() {
			return body;
		}

		public List<Cookie> getCookies() {
			return cookies;
		}
	}

	public static void main(String[] args) {
		// get("http://webinar.ofweek.com/index.action?user.id=2");
		post("http://expo.ofweek.com/api/exhibition/get.xhtml");
		post("http://expo.ofweek.com/api/exhibition/get.xhtml", true);
		get("http://expo.ofweek.com/api/exhibition/get.xhtml");
		get("http://expo.ofweek.com/api/exhibition/get.xhtml", true);
		// post("http://www.qq.com", null);
	}
}

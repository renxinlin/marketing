package com.jgw.supercodeplatform.marketing.common.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Http请求工具类
 */
public class HttpUtil {
	private HttpUtil() {
		
	}
	/**
	 * 发送post请求,根据Content-Type分别返回不同的返回值
	 * 
	 * @param url
	 * @param header
	 * @param body
	 * @return
	 */
	public static Map<String, Object> doMultiPost(String url, Map<String, String> header, String body) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		PrintWriter out = null;
		BufferedReader in =null;
		try {
			// 设置 url
			URL realUrl = new URL(url);
			URLConnection connection = realUrl.openConnection();
			// 设置 header
			for (String key : header.keySet()) {
				connection.setRequestProperty(key, header.get(key));
			}
			// 设置请求 body
			connection.setDoOutput(true);
			connection.setDoInput(true);
			out = new PrintWriter(connection.getOutputStream());
			// 保存body
			out.print(body);
			// 发送body
			out.flush();
			// 获取响应header
			String responseContentType = connection.getHeaderField("Content-Type");
			if ("audio/mpeg".equals(responseContentType)){
				// 获取响应body
				byte[] bytes = toByteArray(connection.getInputStream());
				resultMap.put("Content-Type", "audio/mpeg");
				resultMap.put("sid", connection.getHeaderField("sid"));
				resultMap.put("body", bytes);
				return resultMap;
			} else {
				// 设置请求 body
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				String result = "";
				while ((line = in.readLine()) != null) {
					result += line;
				}
				resultMap.put("Content-Type", "text/plain");
				resultMap.put("body", result);
				return resultMap;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			try {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

public void getParams(String url,Map<String, String> params,Map<String, String> headers) {
 
    // 获取连接客户端工具
    CloseableHttpClient httpClient = HttpClients.createDefault();
 
    String entityStr = null;
    CloseableHttpResponse response = null;
    try {
        /*
         * 由于GET请求的参数都是拼装在URL地址后方，所以我们要构建一个URL，带参数
         */
        URIBuilder uriBuilder = new URIBuilder(url);
        if (null!=params && !params.isEmpty()) {
			for (Entry<String, String> entry : params.entrySet()) {
			    uriBuilder.addParameter(entry.getKey(), entry.getValue());
			}
		}
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        if (null!=headers && !headers.isEmpty()) {
			for (Entry<String, String> entry : headers.entrySet()) {
			    httpGet.addHeader(entry.getKey(), entry.getValue());
			}
		}
        // 执行请求
        response = httpClient.execute(httpGet);
        // 获得响应的实体对象
        HttpEntity entity = response.getEntity();
        // 使用Apache提供的工具类进行转换成字符串
        entityStr = EntityUtils.toString(entity, "UTF-8");
    } catch (ClientProtocolException e) {
        System.err.println("Http协议出现问题");
        e.printStackTrace();
    } catch (URISyntaxException e) {
        System.err.println("URI解析异常");
        e.printStackTrace();
    } catch (IOException e) {
        System.err.println("IO异常");
        e.printStackTrace();
    } finally {
        // 释放连接
        if (null != response) {
            try {
                response.close();
                httpClient.close();
            } catch (IOException e) {
                System.err.println("释放连接出错");
                e.printStackTrace();
            }
        }
    }
 
    // 打印响应内容
    System.out.println(entityStr);
 
}
	private static byte[] toByteArray(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			byte[] buffer = new byte[1024 * 4];
			int n = 0;
			while ((n = in.read(buffer)) != -1) {
				out.write(buffer, 0, n);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			out.close();
			in.close();
		}
		return out.toByteArray();
	}
}

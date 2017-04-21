package fhr.com.smackdemo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
/**
 * http请求简单辅助类
 * @author fhr
 * @date 2017/04/08
 */
public class HttpSimleRequest {
	 /**
     * 向指定URL发送GET方法的请求
     * @param url   发送请求的URL
     * @param params 请求参数map
     * @return URL 所代表远程资源的响应结果
     */
    public  String sendGet(String url,Map<String, Object> params) {
		String result = "";
		BufferedReader in=null;
		url = getRealUrlByParams(url, params);
		try {
			URLConnection connection = openConnection(url);
			Map<String, List<String>> map = connection.getHeaderFields();
			 in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			result = readBody(result, in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			closeReader(in);
		}
		return result;
    }
	
    /**
     * 向指定 URL 发送POST方法的请求
     * @param url   发送请求的 URL
     * @param params 请求参数
     * @return 所代表远程资源的响应结果
     */
    public  String sendPost(String url, Map<String, Object> params) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URLConnection conn = getConnection(url);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            if(params!=null){
//            	out.print(params.entrySet().stream()
//    					.map(param -> String.format("%s=%s", param.getKey().toString(), param.getValue()))
//    					.collect(Collectors.joining("=")));
            	out.flush();
            }
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            result = readBody(result, in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
			closeReader(in);
			closeWriter(out);
        }
        return result;
    }
    /**
     * 获取连接
     * @param url
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
	private  URLConnection getConnection(String url) throws MalformedURLException, IOException {
		URL realUrl = new URL(url);
		// 打开和URL之间的连接
		URLConnection conn = realUrl.openConnection();
		// 设置通用的请求属性
		conn.setRequestProperty("accept", "*/*");
		conn.setRequestProperty("connection", "Keep-Alive");
		conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		return conn;
	}
    /**
     * 打开连接
     * @param url
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
	private  URLConnection openConnection(String url) throws MalformedURLException, IOException {
		URLConnection connection = getConnection(url);
		// 建立实际的连接
		connection.connect();
		return connection;
	}
	/**
	 * 原地址拼接参数构成正确地址
	 * @param url
	 * @param params
	 * @return
	 */
	private  String getRealUrlByParams(String url, Map<String, Object> params) {
		if (params != null) {
			if (!params.isEmpty()) {
				url += "?";
			}
//			url += params.entrySet().stream()
//					.map(param -> String.format("%s=%s", param.getKey().toString(), param.getValue()))
//					.collect(Collectors.joining("&"));
		}
		return url;
	}
	/**
	 * 读取响应body
	 * @param result
	 * @param in
	 * @return
	 * @throws IOException
	 */
	private  String readBody(String result, BufferedReader in) throws IOException {
		String line=null;
		while ((line = in.readLine()) != null) {
			result += line;
		}
		return result;
	}
	/**
	 * 关闭阅读器
	 * @param in
	 */
	private static void closeReader(BufferedReader in) {
		try {
			if (in != null) {
				in.close();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	/**
	 * 关闭书写器
	 * @param out
	 */
	private static void closeWriter(PrintWriter out) {
		try {
			if (out != null) {
				out.close();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
}

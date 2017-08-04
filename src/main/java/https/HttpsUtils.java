package https;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @desc Https工具类
 * @author:lihj
 * 
 */
public class HttpsUtils {

	private static Log logger = LogFactory.getLog(HttpsUtils.class);

	private static String SESSIONKEY = "JSESSIONID=";

	private static class TrustAnyTrustManager implements X509TrustManager {

		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}

	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

	String _url = "";
	String sessionId = "";
	String param;

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public HttpsUtils(String url, String sessionId) {
		this._url = url;
		this.sessionId = sessionId;
	}

	public HttpsURLConnection getConnection() throws Exception {
		HttpsURLConnection connection = null;
		try {
			String urlStr = this._url;
			logger.info("Post请求的URL为：" + urlStr);

			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
			URL realUrl = new URL(urlStr);
			// 打开和URL之间的连接
			connection = (HttpsURLConnection) realUrl.openConnection();

			// 设置https相关属性
			connection.setSSLSocketFactory(sc.getSocketFactory());
			connection.setHostnameVerifier(new TrustAnyHostnameVerifier());
			connection.setDoOutput(true);

			connection.setRequestMethod("POST");

			connection.setRequestProperty("Content-Length",
					String.valueOf(StringUtils.trimToEmpty(param).getBytes().length));

			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");

			// 设置会话sessionId
			if (StringUtils.isNotBlank(sessionId)) {
				connection.setRequestProperty("Cookie", SESSIONKEY + sessionId);
			}

			// 设置通讯超时时间
			connection.setConnectTimeout(60000);
			connection.setReadTimeout(60000);

			// 建立实际的连接
			connection.connect();
			connection.getOutputStream().write(StringUtils.trimToEmpty(param).getBytes("GBK"));

			return connection;
		} catch (Exception e) {
			logger.error("https连接失败", e);
			throw e;
		}
	}

	public String getResponse() throws Exception {
		String result = "";
		BufferedReader in = null;
		StringBuilder buffer = new StringBuilder();
		try {
			InputStream is = responseStream();
			if (null != is) {
				in = new BufferedReader(new InputStreamReader(is, "GBK"));
				String line = null;
				while ((line = in.readLine()) != null) {
					buffer.append(line);
				}
				result = URLDecoder.decode(buffer.toString(),System.getProperty("file.encoding"));
				logger.info("Post请求返回值：" + result);
			}
		} catch (Throwable e) {
			logger.error("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				throw e2;
			}
		}
		return result;
	}

	/**
	 * 获取返回的输入流
	 * 
	 * @return
	 * @throws Exception
	 */
	public InputStream responseStream() throws Exception {
		try {
			HttpsURLConnection connection = getConnection();
			if (null != connection) {
				return connection.getInputStream();
			}
		} catch (Throwable e) {
			logger.error("发送GET请求出现异常！" + e);

		}
		return null;
	}

	/**
	 * 获取会话session 返回格式 JSESSIONID=593E2A1AB154E50FCE9D2ED053F096FC
	 * 
	 * @return
	 * @throws Exception
	 */
	public String responseSession() throws Exception {
		String sId = "";
		try {
			HttpsURLConnection connection = getConnection();
			if (null != connection) {
				String sessionStr = connection.getHeaderField("Set-Cookie");
				if (StringUtils.isNotBlank(sessionStr)) {
					sId = sessionStr.substring(0, sessionStr.indexOf(";"));

					sId = sId.substring(SESSIONKEY.length());
				}
				logger.info("获取的结果为：" + sId);
			}
			return sId;
		} catch (Throwable e) {
			logger.error("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		return null;
	}
}

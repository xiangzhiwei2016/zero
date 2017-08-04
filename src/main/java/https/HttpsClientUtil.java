package https;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonObject;
import constants.Constants;

/* 
 * 利用HttpsClient进行post请求的工具类 
 */
public class HttpsClientUtil {

	private static final Log logger = LogFactory.getLog(HttpsClientUtil.class);

	// 设置请求编码方式
	private static String CHARSET = "utf-8";

	/**
	 * 接口请求
	 * 
	 * @param url
	 * @param map
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String request(String url, Map<String, Object> map, HttpClientContext context) {
		HttpResponse response = null;
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {

			httpClient = new SSLClient().getHttpClient();
			httpPost = getHttpPost(url, map);// 获取HttpPost请求
			String cookie = "";
			if (null != context && !"".equals(context.getCookieStore().getCookies().get(1).getValue())) {
				cookie = "token=" + context.getCookieStore().getCookies().get(1).getValue()
						+ ";expire_time=20160412204206";
			}
			logger.info("cookie:" + cookie);
			httpPost.setHeader(new BasicHeader("Cookie", cookie));
			// 发送https请求
			response = httpClient.execute(httpPost);
			logger.info("状态码:" + response.getHeaders("error_code")[0].getValue());
			int errorCode = Integer.valueOf(response.getHeaders("error_code")[0].getValue());
			if (errorCode != 200) {
				return handleError(errorCode);
			}
			HttpEntity entity = response.getEntity();
			logger.info(entity.getContent().toString());
			result = EntityUtils.toString(entity, CHARSET);
		} catch (Exception ex) {
			logger.error("https服务连接失败！",ex);
//			ex.printStackTrace();
			// 连接断开,返回码
			result = handleError(Constants.BREAK_CODE);
		}
		return result;
	}

	/**
	 * 文件上传接口请求
	 * 
	 * @param url
	 * @param map
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String uploadRequest(String url, String params, String fileName, File file,
			HttpClientContext context) {
		HttpResponse response = null;
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {

			FileBody bin = new FileBody(file);

			StringBody commentParams = new StringBody(params,ContentType.DEFAULT_TEXT);
			StringBody commentfileName = new StringBody(fileName,ContentType.DEFAULT_TEXT);
			MultipartEntityBuilder build= MultipartEntityBuilder.create();
			
			build.addPart("file", bin);// file1为请求后台的File upload;属性
			build.addPart("filename", commentfileName);// filename1为请求后台的普通参数;属性
			build.addPart("params", commentParams);
			
			HttpEntity HttpEntity = build.build();
			
			httpClient = new SSLClient().getHttpClient();
			httpPost = new HttpPost(url);

			httpPost.setEntity(HttpEntity);
			String cookie = "";
			if (null != context && !"".equals(context.getCookieStore().getCookies().get(1).getValue())) {
				cookie = "token=" + context.getCookieStore().getCookies().get(1).getValue()
						+ ";expire_time=20160412204206";
			}
			httpPost.setHeader(new BasicHeader("Cookie", cookie));
			// 发送https请求
			response = httpClient.execute(httpPost);

			logger.info("状态码：" + response.getHeaders("error_code")[0].getValue());
			int errorCode = Integer.valueOf(response.getHeaders("error_code")[0].getValue());
			if (errorCode != 200) {
				return handleError(errorCode);
			}
			HttpEntity entity = response.getEntity();
			logger.info(entity.getContent());
			result = EntityUtils.toString(entity, CHARSET);
		} catch (Exception ex) {
			logger.error("https服务连接失败！");
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * http get请求
	 * 
	 * @param url
	 * @param context
	 * @return
	 */
	public static InputStream requestGet(String url, HttpClientContext context) {
		HttpResponse response = null;
		HttpClient httpClient = null;
		HttpGet httpGet = null;
		InputStream is = null;
		try {
			httpClient = new SSLClient().getHttpClient();
			httpGet = new HttpGet(url);
			String cookie = "";
			if (null != context && !"".equals(context.getCookieStore().getCookies().get(1).getValue())) {
				cookie = "token=" + context.getCookieStore().getCookies().get(1).getValue()
						+ ";expire_time=20160412204206";
			}
			httpGet.setHeader(new BasicHeader("Cookie", cookie));
			// 发送https请求
			response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			// System.out.println("返回结果值：" + EntityUtils.toString(entity,
			// CHARSET));
			is = entity.getContent();
		} catch (Exception ex) {
			logger.error("https服务连接失败！");
			ex.printStackTrace();
		}
		return is;
	}

	/**
	 * 登录请求获取cookie
	 * 
	 * @param url
	 * @param map
	 * @return
	 * @throws IOException 
	 */
	public static Map<String, Object> loginRequest(String url, Map<String, Object> map) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		HttpResponse response = null;
		CloseableHttpClient httpClient = null;
		HttpPost httpPost = null;
		HttpClientContext context = new HttpClientContext();
		try {
			httpClient = new SSLClient().getHttpClient();
			//httpClient = new SSLClient();
			httpPost = getHttpPost(url, map);// 获取HttpPost请求
			CookieStore cookieStore = new BasicCookieStore();
			context.setCookieStore(cookieStore);
			// https请求
			response = httpClient.execute(httpPost, context);
			returnMap.put(Constants.KEY_RESULT, EntityUtils.toString(response.getEntity(), CHARSET));
			returnMap.put(Constants.KEY_CONTEXT, context);
		} catch (Exception ex) {
			logger.error("https服务连接失败！",ex);
//			ex.printStackTrace();
		} finally {
			// 关闭连接 ,释放资源
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return returnMap;
	}

	/**
	 * 获取HttpClient连接
	 * 
	 * @param connectUrl
	 *            连接url
	 * @param paraMap
	 *            调用接口所需的参数信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static HttpPost getHttpPost(String connectUrl, Map<String, Object> paraMap) {
		HttpPost httpPost = null;
		try {
			httpPost = new HttpPost(connectUrl);
			// 设置参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			if (null != paraMap) {
				Iterator<?> iterator = paraMap.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, String> elem = (Entry<String, String>) iterator.next();
					list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
				}
				if (list.size() > 0) {
					// 参数传递设置编码格式
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, CHARSET);
					httpPost.setEntity(entity);
				}
			}
		} catch (Exception e) {
			logger.error("封装参数信息异常！");
		}
		return httpPost;
	}

	/**
	 * 错误码对应提示
	 * 
	 * @param errorCode
	 * @return
	 */
	public static String handleError(int errorCode) {
		JsonObject json = new JsonObject();
		json.addProperty(Constants.VERSION, "1.0");
		switch (errorCode) {
		case 100:
			json.addProperty(Constants.ERROR_CODE, 100);
			json.addProperty(Constants.ERROR_MSG, "未登录！");
			break;
		case 110:
			json.addProperty(Constants.ERROR_CODE, 110);
			json.addProperty(Constants.ERROR_MSG, "后台业务处理未知错误！");
			break;

		case 101:
			json.addProperty(Constants.ERROR_CODE, 101);
			json.addProperty(Constants.ERROR_MSG, "无权限访问！");
			break;
		case 102:
			json.addProperty(Constants.ERROR_CODE, 102);
			json.addProperty(Constants.ERROR_MSG, "访问版本不支持！");
			break;
		case 103:
			json.addProperty(Constants.ERROR_CODE, 103);
			json.addProperty(Constants.ERROR_MSG, "会话失效！");
			break;
		case 104:
			json.addProperty(Constants.ERROR_CODE, 104);
			json.addProperty(Constants.ERROR_MSG, "请求无对应服务！");
			break;
		case 105:
			json.addProperty(Constants.ERROR_CODE, 105);
			json.addProperty(Constants.ERROR_MSG, "请求接口错误！");
			break;
		case 106:
			json.addProperty(Constants.ERROR_CODE, 106);
			json.addProperty(Constants.ERROR_MSG, "返回对象无法做json对象转换！");
			break;
		case 107:
			json.addProperty(Constants.ERROR_CODE, 107);
			json.addProperty(Constants.ERROR_MSG, "请求参数个数错误！");
			break;
		case 108:
			json.addProperty(Constants.ERROR_CODE, 108);
			json.addProperty(Constants.ERROR_MSG, "请求参数类型错误！");
			break;
		case 109:
			json.addProperty(Constants.ERROR_CODE, 109);
			json.addProperty(Constants.ERROR_MSG, "请求参数无法转换java对象！");
			break;
		case 111:
			json.addProperty(Constants.ERROR_CODE, 111);
			json.addProperty(Constants.ERROR_MSG, "entity定义错误，没有主键ID！");
			break;
		case 112:
			json.addProperty(Constants.ERROR_CODE, 112);
			json.addProperty(Constants.ERROR_MSG, "无法获得数据库连接！");
			break;
		case 113:
			json.addProperty(Constants.ERROR_CODE, 113);
			json.addProperty(Constants.ERROR_MSG, "参数验证错误！");
			break;
		case 114:
			json.addProperty(Constants.ERROR_CODE, 114);
			json.addProperty(Constants.ERROR_MSG, "当前服务不可用！");
			break;
		case Constants.BREAK_CODE:
			json.addProperty(Constants.ERROR_CODE, Constants.BREAK_CODE);
			json.addProperty(Constants.ERROR_MSG, "https服务连接失败！");
			break;
		default:
			break;
		}
		json.addProperty(Constants.DATA, "");
		return json.toString();
	}
}
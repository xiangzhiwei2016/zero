package utils;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.protocol.HttpClientContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import constants.Constants;
import https.HttpsClientUtil;
import single.HttpContextSingle;

public class CommonUtils {
	private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

	// 单例初始化
	private static HttpContextSingle single = HttpContextSingle.getInstance();

	// 登录
	public static Map<String, Object> loginRequest(String login_url, Map<String, Object> paramMap) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if (null == PsUtil.convertToNull(login_url) || null == paramMap) {
			returnMap.put(Constants.ERROR_CODE, "-1");
			returnMap.put(Constants.ERROR_MSG, "参数不能为空");
			return returnMap;
		}
		logger.info("登录，url【"+login_url+"】"+",param【"+paramMap.toString()+"】");
		Map<String, Object> loginMap = HttpsClientUtil.loginRequest(login_url, paramMap);
		if(null == loginMap || loginMap.isEmpty()){
			returnMap.put(Constants.ERROR_CODE, "-1");
			returnMap.put(Constants.ERROR_MSG, "系统出现异常,请稍后再试");
		}else{
			returnMap = analysis((String)loginMap.get(Constants.KEY_RESULT));
			if(Double.valueOf((double) returnMap.get(Constants.ERROR_CODE)) == 0){
				single.httpClientContext = (HttpClientContext) loginMap.get(Constants.KEY_CONTEXT);
			}else{
				single.httpClientContext = null;
			}
		}
		return returnMap;
	}

	// 通用下载
	public static InputStream commonDownload(String url) {
		return HttpsClientUtil.requestGet(url, single.httpClientContext);
	}
	// 通用上传文件模块
	public static Map<String, Object> commonUpload(String url, String params, String fileName, File file) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if (null == PsUtil.convertToNull(url) || null == PsUtil.convertToNull(params)
				|| null == PsUtil.convertToNull(fileName) || null == file) {
			returnMap.put(Constants.ERROR_CODE, "-1");
			returnMap.put(Constants.ERROR_MSG, "参数不能为空");
			return returnMap;
		}
		logger.info("文件上传，url:【"+url+"】"+"params:【"+params.toString()+"】"+"file【"+file.getAbsolutePath().concat(fileName)+"】");
		// 调用
		String message = HttpsClientUtil.uploadRequest(url, params, fileName, file, single.httpClientContext);
		
		return analysis(message);
	}

	// 统一用post，且参数是个对象的
	public static <T> Map<String, Object> commonCall(String url, T request) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if (null == url || null == request) {
			returnMap.put(Constants.ERROR_CODE, "-1");
			returnMap.put(Constants.ERROR_MSG, "参数不能为空");
			return returnMap;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap = Bean2MapUtils.beanToMap(request);
		return commonRequest(url, paramMap);
	}
	
	// 通用的请求
	public static Map<String, Object> commonRequest(String url, Map<String, Object> paramMap) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if (null == url) {
			returnMap.put(Constants.ERROR_CODE, "-1");
			returnMap.put(Constants.ERROR_MSG, "请求地址url参数不能为空");
			return returnMap;
		}
		// 调用
		String message = HttpsClientUtil.request(url, paramMap, single.httpClientContext);
		
		return analysis(message);
	}
	
	// 获取消息
	public static String msgRecieve(String url, Map<String, Object> paramMap) {
		if (null == url || null == paramMap ) {
			logger.info("获取消息接口，请求参数不能为空");
			return null;
		}
		// 调用
		return HttpsClientUtil.request(url, paramMap, single.httpClientContext);
	}
	
	/**
	 * 返回结果解析
	 * 
	 * @param message
	 * @return
	 */
	public static Map<String, Object> analysis(String message) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if (null == PsUtil.convertToNull(message)) {
			returnMap.put(Constants.ERROR_CODE, "-1");
			returnMap.put(Constants.ERROR_MSG, "系统出现异常,请稍后再试");
			return returnMap;
		}
		logger.info("message:" + message.toString());
		returnMap = new Gson().fromJson(message, new TypeToken<Map<String, Object>>() {
		}.getType());
		return returnMap;
	}
}

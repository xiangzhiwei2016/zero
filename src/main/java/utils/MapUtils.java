package utils;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import constants.Constants;

public class MapUtils {
	
//	String data = "{\"version\":\"1.0\",\"errorCode\":0,\"errorMsg\":null,\"data\":""{\"errorCode\":0,\"errorMsg\":null,\"funcFlag\":\"1009\",\"data\":{\"exchangeId\":\"00001\",\"bankId\":\"607290056123\"}}""}";
	// 解析map
	public static Map<String, Object> analysis(Map<String, Object> map) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		// 首先判断上一层的错误码
		String out_code = PsUtil.convertToString(map.get(Constants.ERROR_CODE));
		if (Constants.OUT_SUCC.equals(out_code)) {
			returnMap = new Gson().fromJson(PsUtil.convertToString(map.get(Constants.DATA)),
					new TypeToken<Map<String, Object>>() {
					}.getType());
		} else {
			returnMap = map;
		}
		return returnMap;
	}
	

	/*
	 * 返回失败码
	 */
	public static String analysisMap(Map<String,Object> map){
		// {"version":"1.0","errorCode":0.0,"errorMsg":null,"data":"{\"errorCode\":\"0\",\"errorMsg\":\"\"}"}
		if(!Constants.OUT_SUCC.equals(map.get(Constants.ERROR_CODE).toString())){
			return Constants.FAIL_CODE;
		}
		String data = map.get(Constants.DATA).toString();
		Map<String, Object> resp = new Gson().fromJson(data, new TypeToken<Map<String, Object>>() {
		}.getType());
		if(!Constants.IN_SUCC.equals(resp.get(Constants.ERROR_CODE).toString())){
			return Constants.FAIL_CODE;
		}
		return Constants.SUCC_CODE; 
	}

}

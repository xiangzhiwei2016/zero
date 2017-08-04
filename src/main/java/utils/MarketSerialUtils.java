package utils;

public class MarketSerialUtils {
	// 生成 20位的市场流水号
	// 流水号生成规则funcFlag+当前时间的毫秒数
	public static String genMarketSerial(String funcFlag) {
		long mills = DateUtils.millis();
		String value = funcFlag.concat(String.valueOf(mills));
		if (value.length() < 20) {
			// 若长度少于20位,右补0
			while (value.length() != 20) {
				value += "0";
			}
		} else if (value.length() > 20) {
			// 截取20位
			value = value.substring(0, 20);
		}
		return value;
	}
}

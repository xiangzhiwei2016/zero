package constants;

public class Constants {
	// 用户名
	public static final String USER_NAME = "username";
	
	// 密码
	public static final String PASS_WORD = "password";

	// 链接地址
	public static final String CONNECT_URL = "connect.url";
	
	// 是否初始化登录清算中心
	public static final String INIT_START = "INIT_START";
	
	// 是否实时上报数据开关
	public static final String REPORT_START = "REPORT_START";
	
	// 是
	public static final String START_VALUE = "1";

	// 间隔秒数
	public static final String INTERVAL_SECONDS = "INTERVAL_SECONDS";
	
	// 默认秒数
	public static final String DEFAULT_SECONDS = "1";
	
	// 失败后隔多久重新连接
	public static final String RECONNECT_SECONDS = "RECONNECT_SECONDS";
	
	// 默认秒数
	public static final String DEFAULT_RECONNECT_SECONDS = "600";
		
	// httpclient返回map的key
	public static final String KEY_RESULT = "result";
	
	public static final String KEY_CONTEXT = "context";
	
	// 清算查询接口的参数
	public static final String KEY_PARAM = "key";
		
	// 链接url
	public static final String BASE_URL = "connect_url";
	
	// 市场流水
	public static final String MARKET_SERIAL = "marketSerial";
	
	// 银行流水
	public static final String BANK_SERIAL = "bankSerial";
	
	// 功能标志
	public static final String FUNC_FLAG = "funcFlag";
	
	// 成功标志
	public static final String SUCC_CODE = "0";
	
	// 失败标志
	public static final String FAIL_CODE = "1";
	
	// 返回的version
	public static final String VERSION = "version";
		
	// 返回的错误码
	public static final String ERROR_CODE = "errorCode";
	
	// 返回的错误信息
	public static final String ERROR_MSG = "errorMsg";
		
	// 返回的数据
	public static final String DATA = "data";
		
	// 外部返回的成功标志
	public static final String OUT_SUCC = "0.0";
	
	// 内返回的成功标志
	public static final String IN_SUCC = "0";
	
	// 需要审核
	public static final String IN_AUDIT = "2";
	
	// 审核标志
	public static final String AUDIT_CODE = "2";
	
	// 是否登录
	public static final String WHETHER_LOGIN = "whether_login";
	
	// 失败次数
	public static final String FAIL_TIMES = "FAIL_TIMES";
	
	// 配置文件
	public static final String MAX_FAIL_TIMES_CONFIG = "MAX_FAIL_TIMES";
	
	// 默认失败次数
	public static final String DEFAULT_MAX_FAIL_TIMES = "5";
	
	// 已登录
	public static final String IS_LOGGED = "1";
	
	// 未登录
	public static final String NOT_LOGGED = "0";
	
	// 是否签到签退
	public static final String WHETHER_SIGN = "whether_sign";
	
	// 已签到
	public static final String IS_SIGNED = "1";
	
	// 已签退
	public static final String IS_SIGNEDOFF = "0";
	
	// 参数为空的错误码
	public static final int PARM_EMPTY = 3001;
	
	// 不能进行操作的错误码
	public static final int RORBID_OPERATE = 3002;
	
	// https服务连接失败code！
	public static final int BREAK_CODE = 115;
	// 未登录
	public static final int NOT_LOGON_CODE = 110;
	// 交互码
	public static final String FUND_IN = "1001";// 入金
	public static final String FUND_OUT = "1002";// 出金
	public static final String SIGN_IN = "1003";// 银行端签约
	public static final String SIGN_OUT = "1004";// 银行端解约
	public static final String GET_TURNOVER_REQUEST = "1007";//获取银行对账流水文件
	public static final String QUERY_GET_TURNOVER_SPEED = "1008";//查询获取银行对账流水文件进度
	public static final String SIGN = "1009";//签到
	public static final String SIGN_OFF = "1010";//签退
	public static final String OUTFUND_OF_BANK = "1011";// 银行端出金确认
	public static final String OUTFUND_OF_BANK_RESULT = "1012";// 银行端出金结果通知
	public static final String INFUND_OF_BANK_RESULT = "1013";// 银行端入金结果通知
	public static final String BANKFILE_GET = "1015";// 通知会员下载银行对账文件
	public static final String BALANCE_ACCOUNT_FINISH = "1047";// 对账完成通知
	// 上报
	public static final String TRADER_REPORT = "2001";// 交易商信息上报（需要审核）
	public static final String PRODUCT_REPORT = "2002";// 品种信息上报（需要审核）
	public static final String INSTRUMENT_REPORT = "2003";// 商品信息上报
	public static final String INSTRUMENT_SPECFEE_REPORT = "2004";// 商品特殊手续费上报
	public static final String TRADE_SEGMENT_REPORT = "2005";// 交易节上报
	public static final String INSTRUMENT_TRADE_SEGMENT_REPORT = "2006";// 商品交易节设置上报
	public static final String UN_TRADE_DAYS_REPORT = "2007";// 非交易日上报
	
	// 实时上报
	public static final String ACTUAL_TRADE_REPORT = "2008";// 实时成交数据上报
	public static final String ACTUAL_ORDER_REPORT = "2009";// 实时委托数据上报
	public static final String ACTUAL_QUOTATION_REPORT = "2010";// 实时行情数据上报
	public static final String ACTUAL_WAREHOUSE_REPORT = "2011";// 实时仓单数据上报
	public static final String ACTUAL_DELIVERY_REPORT = "2012";// 实时交割单数据上报

	// 文件上报
	public static final String AFTER_TRADE_REPORT = "2013";// 盘后成交数据上报
	public static final String AFTER_ORDER_REPORT = "2014";// 盘后委托数据上报
	public static final String AFTER_WAREHOUSE_REPORT = "2015";// 盘后仓单数据上报
	public static final String AFTER_DELIVERY_REPORT = "2016";// 盘后交割单数据上报
	
	// 上传行情信息
	public static final String MARKET_DATA_REPORT = "2017";
	
	// 上传清算文件
	public static final String CLEARING_FILE_REPORT = "2018";

}

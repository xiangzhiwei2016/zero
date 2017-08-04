package constants;

public class TargetUrlConstants {
	// 清算中心连接地址 http://localhost:8081/clearingCenter
	public static String BASE_URL = "";
	
	// 轮询获取清算中心结果地址
	public static String MESSAGE_QUEUE_URL = "/restfulservice/messageQueueService/popQueue";
	
	// 入金
	public static String FUND_IN_URL = "/restfulservice/iClientManageService/sendInFund";

	// 出金
	public static String FUND_OUT_URL = "/restfulservice/iClientManageService/sendOutFund";

	// 审核银行出金申请
	public static String BANK_FUND_OUT_URL = "/restfulservice/iClientManageService/sendInFundOfBank";
	
	// 签到
	public static String SIGN_URL = "/restfulservice/iClientManageService/sendClientSign";

	// 签退
	public static String SIGN_OFF_URL = "/restfulservice/iClientManageService/sendClientSignOff";

	// 开户
	public static String OPEN_ACCOUNT_URL = "/restfulservice/traderService/addTrader";

	// 申请获取对账流水文件
	public static String TRUNOVER_FILE_URL = "/restfulservice/iClientManageService/getTurnoverRequest";

	// 穿获取对账流水文件进度
	public static String TRUNOVER_SPEED_URL = "/restfulservice/iClientManageService/queryGetTurnoverSpeed";

	// 下载对账流水文件
	public static String TRUNOVER_DOWNLOAD_URL = "/download/docSubmmitService/clientDownloadTrunoverFile";

	// 清算文件上传
	public static String SETTLEMENT_FILE_URL = "/restfulservice/memberSettlementResultFileService/uploadFile";

	// 确认清算文件上传完毕
	public static String SETTLEMENT_FILESTATUS_URL = "/restfulservice/memberSettlementResultFileService/uploadFile";

	// 上传清算文件数据
	public static String CLEARING_FILE_URL = "/upload/clearingFileUploadService/uploadClearingFile";
	
	// 品种信息上报
	public static String PRODUCT_REPORT_URL = "/restfulservice/productClientService/saveProduct";
		
	// 商品信息上报
	public static String INSTRUMENT_REPORT_URL = "/restfulservice/instrumentClientService/saveInstrumentWithProduct";

	// 商品特殊手续费设置
	public static String INSTRUMENT_SPECFEE_REPORT_URL = "/restfulservice/instrumentClientService/saveInstrumentSpec";
	
	// 交易节信息上报
	public static String TRADE_SEGMENT_REPORT_URL = "/restfulservice/tradeSegmentClientService/saveTradeSegment";
	
	// 商品交易节信息上报
	public static String INSTRUMENT_TRADE_SEGMENT_REPORT_URL = "/restfulservice/tradeSegmentClientService/saveSegmentInstrument";
		
	// 非交易日信息上报
	public static String UNTRADE_DAYS_REPORT_URL = "/restfulservice/unTradeDayClientService/saveUnTradeDays";
	
	/*
	 * 实时数据上报
	 */
	// 成交数据
	public static String TRADE_DATA_URL = "/restfulservice/settlementActualService/TradeData";
	
	// 委托数据
	public static String ORDER_DATA_URL = "/restfulservice/settlementActualService/OrderData";
		
	// 行情数据
	public static String QUOTATION_DATA_URL = "/restfulservice/settlementActualService/QuotationData";
		
	// 仓单信息
	public static String WAREHOUSE_DATA_URL = "/restfulservice/settlementActualService/WarehouseData";
	
	// 交割单信息
	public static String DELIVERY_DATA_URL = "/restfulservice/settlementActualService/DeliveryData";
	
	/*
	 * 盘后文件上报
	 */
	// 盘后成交文件上报
	public static String DEAL_DOC_URL = "/upload/docSubmmitService/uploadTradeDoc";
	
	// 盘后委托文件上报
	public static String ORDER_DOC_URL = "/upload/docSubmmitService/uploadOrderDoc";
	
	// 盘后仓单文件上报
	public static String WAREHOUSE_DOC_URL = "/upload/docSubmmitService/uploadWarehouseDoc";
	
	// 盘后交割单文件上报
	public static String DELIVERY_DOC_URL = "/upload/docSubmmitService/uploadDeliveryDoc";
	
	// 行情信息上传接口
	public static String MARKET_DATA_URL = "/upload/marketInformationUploadService/makertDataFileUplod";

	// 登录
	public static String LOGIN_URL = "/logon";

	// 退出登录
	public static String LOGOUT_URL = "/logout";
}

/**
 * copy right 2012 sctiyi all rights reserved
 * create time:涓嬪崍03:08:21
 * author:ftd
 */
package com.jxlt.stage.common.utils;


/**
 * @author ftd
 *
 */
public class Constants {

	/**
	 * 绯荤粺榛樿绠＄悊鍛樿处鎴�	 */
	public static final String ADMINISTRATOR_ACCOUNT="admin";
	
	public static final String USER_SESSION_NAME = "userInfo";
	public static final String USER_SESSION_FUNCTION = "userFunctions";
	public static final String CURRENT_MENU_ID = "__currentMenuId";
	
	public static final String THE_REALM_NAME="userRealm";
	public final static  String USER_INFO = "USER_INFO";
	
	public static final int IMAGE_RESIZE_WIDTH = 150;
	public static final int IMAGE_RESIZE_HEIGHT = 150;

	public static final int DEFAULT_PAGE_SIZE = 10;
	public static final int DEFAULT_QUEUE_SIZE = 10;
	/**
	 * api鎺ュ彛杩斿洖鐘讹拷?鍏叡鐮佽〃 start
	 */
	public static final Integer API_RESULT_SUCCESS = 0;
	public static final Integer API_RESULT_FAILURE = 1;
	public static final Integer API_RESULT_TIMEOUT = 95;
	public static final Integer API_RESULT_SUBMIT_DUPLICATE = 96;
	public static final Integer API_RESULT_PARAMTER_ERROR = 97;
	public static final Integer API_RESULT_TOKEN_ERROR = 98;
	public static final Integer API_RESULT_ORTHER_ERROR = 99;
	/**
	 * api鎺ュ彛杩斿洖鐘讹拷?鍏叡鐮佽〃 end
	 */
	
	/**
	 * 鏁版嵁鐘讹拷?锛屽惎鐢拷?鍋滅敤
	 */
	public static final Integer STATUS_ENABLE = 0;
	public static final Integer STATUS_DISABLE = 1;

	public static final Integer CHECK_RESULT_NORMAL = 3;
	public static final Integer CHECK_RESULT_EXCEPTION = 1;
	public static final Integer CHECK_RESULT_FAIL = 4;
	public static final Integer CHECK_RESULT_WARN = 2;
	public static final Integer LATEST_TIME =10;
	/**
	 * 鏈�柊姹囨姤鏃堕棿娈�	 */
	
	/**
	 * 微信支付
	 */
	//公众账号ID
	public static final String APPID = "wxcb2aa0556554b51c";
	// 第三方用户唯一凭证密钥
	public static final String APPSECRET = "6e2dcfce9bd45a7c07da009d8aac2e7d";
	//商户号
	public static final String PARNER = "1377614502";
	//商户号密码
	public static final String  PARTNERKEY = "430141";
	//商户号
	public static final String  MCD_ID = "1377614502";
	//签名
	public static final String  SIGN = "";
	//随机字符串
	public static final String  NONCE_STR = "";
	//设备号
	public static final String  DEVICE_INFO = "";
	//微信订单号
	public static final String  TRANSACTION_ID = "";
	//商户订单号
	public static final String  OUT_TRADE_NO = "";
	//商户退款单号
	public static final String  OUT_REFUND_NO = "";
	//订单金额
	public static final int  TOTAL_FEE = 0;
	//退款金额
	public static final int  REFUND_FEE = 0;
	//操作员
	public static final String  OP_USER_ID = "1377614502";
	

	
	
	
	
	
	
	/** openfire鍙厤缃�*/
	public final static String BLOWFISHCODE = PropertiesUtil.getInstance().getParamsProperty("BLOWFISHCODE"); 
	public final static String OPENFIRE_IP = PropertiesUtil.getInstance().getParamsProperty("OPENFIRE_IP");  
	public final static String OPENFIRE_DOMAIN = PropertiesUtil.getInstance().getParamsProperty("OPENFIRE_DOMAIN"); 
	public final static String APK_VERSION = PropertiesUtil.getInstance().getParamsProperty("APK_VERSION"); 
	public final static String INIT_PASSWORD = "111111";
}

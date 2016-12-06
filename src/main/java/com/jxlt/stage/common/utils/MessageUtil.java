package com.jxlt.stage.common.utils;
import java.util.HashMap;
import java.util.Set;

import com.cloopen.rest.sdk.CCPRestSmsSDK;



public class MessageUtil {
	/**
	 * 短信推�?
	 * @param mobile
	 * @param modelType
	 * @param answer
	 */
	public static boolean sendMobileMessage(String mobile,String templateId,String[] message){
		HashMap<String, Object> result = null;
		boolean res = false;
		//初始化SDK
		CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
		
		//******************************注释*********************************************
		//*初始化服务器地址和端�?                                                      *
		//*沙盒环境（用于应用开发调试）：restAPI.init("sandboxapp.cloopen.com", "8883");*
		//*生产环境（用户应用上线使用）：restAPI.init("app.cloopen.com", "8883");       *
		//*******************************************************************************
		restAPI.init("app.cloopen.com", "8883");
		
		
		//******************************注释*********************************************
		//*初始化主帐号和主帐号令牌,对应官网�?��者主账号下的ACCOUNT SID和AUTH TOKEN     *
		//*ACOUNT SID和AUTH TOKEN在登陆官网后，在“应�?管理控制台�?中查看开发�?主账号获�?
		//*参数顺序：第�?��参数是ACOUNT SID，第二个参数是AUTH TOKEN�?                  *
		//*******************************************************************************
		restAPI.setAccount("8a216da858867fd701588a1f15270185", "c7893a8ace024ee884e8623f2a146453");
		
		
		//******************************注释*********************************************
		//*初始化应用ID                                                                 *
		//*测试�?��可使用�?测试Demo”的APP ID，正式上线需要使用自己创建的应用的App ID     *
		//*应用ID的获取：登陆官网，在“应�?应用列表”，点击应用名称，看应用详情获取APP ID*
		//*******************************************************************************
		restAPI.setAppId("8a216da858867fd701588a1f15bb018a");
		
		
		//******************************注释****************************************************************
		//*调用发�?模板短信的接口发送短�?                                                                 *
		//*参数顺序说明�?                                                                                 *
		//*第一个参�?是要发�?的手机号码，可以用�?号分隔，�?���?��支持100个手机号                          *
		//*第二个参�?是模板ID，在平台上创建的短信模板的ID值；测试的时候可以使用系统的默认模板，id�?�?   *
		//*系统默认模板的内容为“�?云�?讯�?您使用的是云通讯短信模板，您的验证码是{1}，请于{2}分钟内正确输入�?*
		//*第三个参数是要替换的内容数组�?																													       *
		//**************************************************************************************************
		
		//**************************************举例说明***********************************************************************
		//*假设您用测试Demo的APP ID，则�?��用默认模板ID 1，发送手机号�?3800000000，传入参数为6532�?，则调用方式�?          *
		//*result = restAPI.sendTemplateSMS("13800000000","1" ,new String[]{"6532","5"});																		  *
		//*�?3800000000手机号收到的短信内容是：【云通讯】您使用的是云�?讯短信模板，您的验证码是6532，请�?分钟内正确输�?    *
		//*********************************************************************************************************************
		result = restAPI.sendTemplateSMS(mobile,templateId,message);
		//System.out.println("SDKTestGetSubAccounts result=" + result);
		if("000000".equals(result.get("statusCode"))){
			//正常返回输出data包体信息（map�?
			@SuppressWarnings("unchecked")
			HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
			Set<String> keySet = data.keySet();
			for(String key:keySet){
				Object object = data.get(key);
				System.out.println(key +" = "+object);
			}
			res = true;
		}else{
			//异常返回输出错误码和错误信息
			System.out.println("错误�?" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
		}
		return res;
	}
	
	
}

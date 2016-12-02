package com.jxlt.stage.wechat.utils;

public class SendCodeUtil {

    static  String[] seed={"0","1","2","3","4","5","6","7","8","9"};
	
	static int sendCodeLength=4;
	
	public static String createSendCode(){
		
		String result="";
		
		for(int i=0;i<sendCodeLength;i++){
			result +=Math.round(Math.random() * 9);
		}
		return result;
	}
}

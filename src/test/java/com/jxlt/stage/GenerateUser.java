package com.jxlt.stage;

import org.junit.Test;

import com.jxlt.stage.common.utils.MD5Util;

public class GenerateUser {
	
	@Test
	public void testUser(){
		String password = MD5Util.getMD5("111111");
		System.out.println(password);
	}
}

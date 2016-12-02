package com.jxlt.stage;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;

import com.jxlt.stage.common.JsonResult;
import com.jxlt.stage.dao.AutoMapper;
import com.jxlt.stage.model.Auto;
import com.jxlt.stage.service.AutoService;
import com.jxlt.stage.service.serviceImpl.TimerServiceImpl;

public class TestAutoDao extends BaseTest {

	@Resource
	private AutoMapper autoMapper;
	
	@Resource(name = "autoService")
	private AutoService autoService; 
	
	//取列表
	@Test
	public void getAutoList(){
		Auto auto = new Auto();
		List<Auto> list = autoMapper.getAutoList(auto);
		String keyword = list.get(0).getKeyword();
		Assert.assertEquals("测试",keyword);
	}
	
	//取总数
	@SuppressWarnings("deprecation")
	@Test
	public void getAutoListCount(){
		Auto auto = new Auto();
		List<Auto> list = autoMapper.getAutoList(auto);
		int count = list.size();
		Assert.assertEquals(1,count);
	}
	
	//保存
	@Test
	public void save(){
		Auto auto = new Auto();
		auto.setId(1);
		auto.setKeyword("测试保存");
		auto.setAnswer("测试编辑保存");
		JsonResult<Auto> js = autoService.saveOrUpdateAuto(auto);
		int code = js.getCode();
		Assert.assertEquals(0,code);
	}
	
	//过期自动应答获取
	@Test
	public void getDeleteAutoList(){
		Auto auto = new Auto();
		List<Auto> list = autoMapper.getDeleteAutoList();
		String keyword = list.get(0).getKeyword();
		Assert.assertEquals("测试",keyword);
	}
	
	
	//过期自动应答清理
	@Test
	public void DeleteAutoList(){
		 List<Auto> alist = new ArrayList<Auto>();	            
    	alist = autoMapper.getDeleteAutoList();
    	int count = 0;
		for(Auto a:alist){
			if(a.getFlag() == 0){
				autoMapper.deleteByPrimaryKey(a.getId());
				count++;
			}
		} 
		Assert.assertEquals(1, count);
	}

}

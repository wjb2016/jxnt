package com.jxlt.stage;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import com.jxlt.stage.dao.ProjectMapper;
import com.jxlt.stage.model.Project;

public class TestProjectDao extends BaseTest{

	@Resource
	private ProjectMapper projectMapper;
	
	@Test
	public void TestGetUserByMobile(){
		Project pro = new Project();
		pro.setName("工程一");
		pro.setOrderId(3);
		pro.setId(5);
		List<Project> list =  projectMapper.getExistProjectByName(pro);
		Assert.assertEquals(0, list.size());
	}
	
	@Test
	public void TestGetProjectListByUserId(){
		List<String> list =  projectMapper.getProjectListByUserId(26,1);
		for(String s:list){
			System.out.println(s);
		}
	}
}

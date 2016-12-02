package com.jxlt.stage.service.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jxlt.stage.common.JsonResult;
import com.jxlt.stage.dao.AutoMapper;
import com.jxlt.stage.model.Auto;
import com.jxlt.stage.service.AutoService;
@Service("autoService")
public class AutoServiceImpl implements AutoService {

	@Resource
	private AutoMapper autoMapper;

	@Override
	public List<Auto> getAutoList(Auto auto) {
		// TODO Auto-generated method stub
		return autoMapper.getAutoList(auto);
	}

	@Override
	public int getAutoCount(Auto auto) {
		// TODO Auto-generated method stub
		return autoMapper.getAutoCount(auto);
	}

	@Override
	public Auto getAutoById(Integer id) {
		// TODO Auto-generated method stub
		return autoMapper.selectByPrimaryKey(id);
	}

	@Override
	public void changeAutoById(Integer id) {
		// TODO Auto-generated method stub
		Auto auto = autoMapper.selectByPrimaryKey(id);
		if(auto.getFlag() == 1){
			auto.setFlag(0);
		}else if(auto.getFlag() == 0){
			auto.setFlag(1);
		}
		autoMapper.updateByPrimaryKey(auto);
	}

	@Override
	public JsonResult<Auto> saveOrUpdateAuto(Auto auto) {
		JsonResult<Auto> result = new JsonResult<Auto>();
		result.setCode(1);
		List<Auto> autoFind = new ArrayList<Auto>();
		auto.setSearchName(auto.getKeyword());
		auto.setCreateTime(new Date());
		//auto.setFlag(1);
		autoFind = autoMapper.getAutoList(auto);
		//关键字重复判断
		for(Auto a:autoFind){
			if(a.getKeyword().equals(auto.getKeyword()) && a.getId() != auto.getId()){
				result.setMessage("该自动应答关键字已存在！");
				return result;
			}
		}
		if(auto.getId() == 0){
			autoMapper.insertSelective(auto);
			result.setCode(0);
			result.setMessage("添加自动应答成功!");
		}else{
			autoMapper.updateByPrimaryKeySelective(auto);
			result.setCode(0);
			result.setMessage("修改自动应答成功!");
		}
		return result;
	}

}

package com.jxlt.stage.service.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jxlt.stage.common.JsonResult;
import com.jxlt.stage.dao.GradeMapper;
import com.jxlt.stage.dao.UserMapper;
import com.jxlt.stage.model.Grade;
import com.jxlt.stage.model.User;
import com.jxlt.stage.service.GradeService;

@Service("gradeService")
public class GradeServiceImpl implements GradeService {

	@Resource
	private GradeMapper gradeMapper;
	
	@Resource
	private UserMapper userMapper;

	@Override
	public List<Grade> getGradeList(Grade grade) {
		// TODO Auto-generated method stub
		return gradeMapper.getGradeList(grade);
	}

	@Override
	public int getTotalCount(Grade grade) {
		// TODO Auto-generated method stub
		return gradeMapper.getGradeCount(grade);
	}

	@Override
	public JsonResult<Grade> convertGrade(Integer id,Integer userId) {
		// TODO Auto-generated method stub
		JsonResult<Grade> js = new JsonResult<Grade>();
		js.setCode(1);
		js.setMessage("确认积分兑换失败！");
		try{
			Grade grade = gradeMapper.selectByPrimaryKey(id);
			grade.setOperId(userId);
			gradeMapper.updateByPrimaryKeySelective(grade);
			User user = userMapper.selectByPrimaryKey(grade.getUserId());
			int num = 0 - grade.getGrade();
			js.setObj(user.getName()+"兑换积分"+num+"分");
			js.setCode(0);
			js.setMessage(user.getName()+"兑换"+num+"积分成功!");
		}catch(Exception e){
			e.printStackTrace();
		}
		return js;
	}

	@Override
	public JsonResult<Grade> disconvertGrade(Integer id,Integer userId) {
		// TODO Auto-generated method stub
		JsonResult<Grade> js = new JsonResult<Grade>();
		js.setCode(1);
		js.setMessage("取消积分兑换失败！");
		try{
			Grade grade = gradeMapper.selectByPrimaryKey(id);
			User user = userMapper.selectByPrimaryKey(grade.getUserId());
			int num = 0 - grade.getGrade();
			gradeMapper.deleteByPrimaryKey(id);
			user.setGrade(user.getGrade()+num);
			userMapper.updateByPrimaryKeySelective(user);
			js.setObj("取消"+user.getName()+"的积分兑换"+num+"分");
			js.setCode(0);
			js.setMessage(user.getName()+"取消兑换积分成功!");
		}catch(Exception e){
			e.printStackTrace();
		}
		return js;
	}

	@Override
	public Grade getExsitGrade(Grade grade) {
		// TODO Auto-generated method stub
		return gradeMapper.getExsitGrade(grade).get(0);
	}

	@Override
	public void writeGrade(Integer grades, Integer userId, Integer operId,String description) {
		// TODO Auto-generated method stub
		Grade grade = new Grade();
		grade.setId(0);
		grade.setGrade(grades);
		grade.setUserId(userId);
		grade.setCreateTime(new Date());
		grade.setOperId(operId);
		grade.setDescription(description);
		gradeMapper.insertSelective(grade);
	}

	@Override
	public Grade getExsitGrade(int grades, int userId,
			String mobile) {
		// TODO Auto-generated method stub
		Grade grade = new Grade();
		grade.setId(0);
		grade.setGrade(grades);
		grade.setUserId(userId);
		grade.setMobile(mobile);
		List<Grade> list = new ArrayList<Grade>();
		list = gradeMapper.getExsitGrade(grade);
		if(list.size() > 0)
			grade = list.get(0);
		return grade;
	}

	@Override
	public void writeGrade(Integer grades, Integer userId, Integer operId,String description,String mobile) {
		// TODO Auto-generated method stub
		Grade grade = new Grade();
		grade.setId(0);
		grade.setGrade(grades);
		grade.setUserId(userId);
		grade.setCreateTime(new Date());
		grade.setOperId(operId);
		grade.setDescription(description);
		grade.setMobile(mobile);
		gradeMapper.insertSelective(grade);
	}

	@Override
	public List<Grade> getExsitGrade(int grades, int userId) {
		// TODO Auto-generated method stub
		Grade grade = new Grade();
		grade.setId(0);
		grade.setGrade(grades);
		grade.setUserId(userId);
		grade.setCreateTime(new Date());
		return gradeMapper.getExsitGrade(grade);
	}

}

package com.jxlt.stage.dao;

import java.util.List;

import com.jxlt.stage.model.Grade;
@MyBatisRepository
public interface GradeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Grade record);

    int insertSelective(Grade record);

    Grade selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Grade record);

    int updateByPrimaryKey(Grade record);
    //获取积分列表
	List<Grade> getGradeList(Grade grade);
    //获取积分记录总数
	int getGradeCount(Grade grade);
	//积分添加重复查找
	List<Grade> getExsitGrade(Grade grade);

	// 通过userid获取积分信息
	List<Grade> getGradeByUserId(Integer id);
}
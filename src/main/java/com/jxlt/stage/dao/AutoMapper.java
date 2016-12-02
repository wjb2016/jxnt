package com.jxlt.stage.dao;

import java.util.List;

import com.jxlt.stage.model.Auto;
@MyBatisRepository
public interface AutoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Auto record);

    int insertSelective(Auto record);

    Auto selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Auto record);

    int updateByPrimaryKey(Auto record);
    //自动应答列表
	List<Auto> getAutoList(Auto auto);
    //自动应答列表总数
	int getAutoCount(Auto auto);
	//获取长期不使用的自动应答
	List<Auto> getDeleteAutoList();

	// 获取自动应答所有信息
	List<Auto> getAutoKeyWord();
    // 通过关键字查询答案
	List<Auto> getAnswerByQuestion(Auto auto);
}
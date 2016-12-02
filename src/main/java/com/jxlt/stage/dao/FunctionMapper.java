package com.jxlt.stage.dao;

import java.util.List;

import com.jxlt.stage.model.Function;
@MyBatisRepository
public interface FunctionMapper {
	int deleteByPrimaryKey(Integer id);

    int insert(Function record);

    int insertSelective(Function record);

    Function selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Function record);

    int updateByPrimaryKey(Function record);
    //所有function
	List<Function> getFunctionList();
	//子集列表
	List<Function> getFunctionByParentId(Integer id);
    //一级菜单列表
	List<Function> getFunctionMainList();
    //子集列表
	List<Function> getFunctionChildList(Integer id);
}
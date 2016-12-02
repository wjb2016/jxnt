package com.jxlt.stage.dao;

import java.util.List;

import com.jxlt.stage.model.Log;
@MyBatisRepository
public interface LogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Log record);

    int insertSelective(Log record);

    Log selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Log record);

    int updateByPrimaryKey(Log record);
    //获取日志列表
	List<Log> getLogList(Log log);
    //获取日志记录总数
	int getLogCount(Log log);
    //删除过期日志
	int deleteExpiredLog();
}
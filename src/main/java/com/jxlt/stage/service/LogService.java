package com.jxlt.stage.service;

import java.util.List;

import com.jxlt.stage.model.Log;

public interface LogService {

	void writeLog(String string);

	void writeLog(String oper, int userId);

	List<Log> getLogList(Log log);

	int getTotalCount(Log log);

}

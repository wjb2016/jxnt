package com.jxlt.stage.service.serviceImpl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jxlt.stage.controller.BaseController;
import com.jxlt.stage.dao.LogMapper;
import com.jxlt.stage.model.Log;
import com.jxlt.stage.model.User;
import com.jxlt.stage.service.LogService;

@Service("logService")
public class LogServiceImpl extends BaseController implements LogService  {

	@Resource
	private LogMapper logMapper;

	@Override
	public void writeLog(String oper) {
		// TODO Auto-generated method stub
		User user = this.getLoginUser();
		Log log = new Log();
		log.setId(0);
		log.setOper(oper);
		log.setOperTime(new Date());
		log.setUserId(user.getId());
		logMapper.insert(log);
	}
	
	@Override
	public void writeLog(String oper,int userId) {
		// TODO Auto-generated method stub
		Log log = new Log();
		log.setId(0);
		log.setOper(oper);
		log.setOperTime(new Date());
		log.setUserId(userId);
		logMapper.insert(log);
	}

	@Override
	public List<Log> getLogList(Log log) {
		// TODO Auto-generated method stub
		return logMapper.getLogList(log);
	}

	@Override
	public int getTotalCount(Log log) {
		// TODO Auto-generated method stub
		return logMapper.getLogCount(log);
	}

}

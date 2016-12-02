package com.jxlt.stage.model;

import java.util.Date;

import com.jxlt.stage.common.utils.Page;

public class Log extends Page{
    private Integer id;

    private Integer userId;
    //操作人
    private String userName;

    private Date operTime;

    private String oper;
    //操作时间依赖
    private String operTimes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    public String getOper() {
        return oper;
    }

    public void setOper(String oper) {
        this.oper = oper;
    }

	public String getOperTimes() {
		return operTimes;
	}

	public void setOperTimes(String operTimes) {
		this.operTimes = operTimes;
	}

	public String getUserName() {
		if(userId == 0)
			userName = "系统操作";
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
package com.jxlt.stage.model;

import java.util.Date;

import com.jxlt.stage.common.utils.Page;

public class Project  extends Page{
    private Integer id;

    private String name;

    private Integer groupId;

    private Integer orderId;
    
    private Integer orderTypeId;

    private Date startTime;

    private Date endTime;

    private Integer status;

    private Date finishTime;

    private Date beganTime;

    private Integer assessValue;

    private String assessMessage;

    private Integer assessUserId;

    private Integer permission;
    //工程描述
    private String description;
    
    //品牌名称
    private String typeName;
    
    //团队名称
    private String groupName;
    
    //评价人姓名
    private String assessUserName;
    
    //开工时间
    private String beganTimes;
    
    //完工时间
    private String finishTimes;

    //合同号
    private String contractNumber;
    
    //队长手机号
    private String leaderPhone;
    
    public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getBeganTime() {
        return beganTime;
    }

    public void setBeganTime(Date beganTime) {
        this.beganTime = beganTime;
    }

    public Integer getAssessValue() {
        return assessValue;
    }

    public void setAssessValue(Integer assessValue) {
        this.assessValue = assessValue;
    }

    public String getAssessMessage() {
        return assessMessage;
    }

    public void setAssessMessage(String assessMessage) {
        this.assessMessage = assessMessage;
    }

    public Integer getAssessUserId() {
        return assessUserId;
    }

    public void setAssessUserId(Integer assessUserId) {
        this.assessUserId = assessUserId;
    }

    public Integer getPermission() {
        return permission;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }

	public Integer getOrderTypeId() {
		return orderTypeId;
	}

	public void setOrderTypeId(Integer orderTypeId) {
		this.orderTypeId = orderTypeId;
	}

	public String getAssessUserName() {
		return assessUserName;
	}

	public void setAssessUserName(String assessUserName) {
		this.assessUserName = assessUserName;
	}

	public String getFinishTimes() {
		return finishTimes;
	}

	public void setFinishTimes(String finishTimes) {
		this.finishTimes = finishTimes;
	}

	public String getBeganTimes() {
		return beganTimes;
	}

	public void setBeganTimes(String beganTimes) {
		this.beganTimes = beganTimes;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getLeaderPhone() {
		return leaderPhone;
	}

	public void setLeaderPhone(String leaderPhone) {
		this.leaderPhone = leaderPhone;
	}
}
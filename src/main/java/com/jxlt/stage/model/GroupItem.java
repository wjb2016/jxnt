package com.jxlt.stage.model;

import com.jxlt.stage.common.utils.Page;

public class GroupItem  extends Page{
    private Integer id;

    private Integer groupId;

    private Integer userId;

    private Integer isLeader;
    //姓名
    private String name;   
    //职务
    private String duty;
    //手机号
    private String mobile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIsLeader() {
        return isLeader;
    }

    public void setIsLeader(Integer isLeader) {
        this.isLeader = isLeader;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDuty() {
		if(isLeader == 0){
			duty = "队员";
		}else if(isLeader == 1){
			duty = "副队长";
		}else if(isLeader == 2){
			duty = "队长";
		}
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
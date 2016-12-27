package com.jxlt.stage.model;

import com.jxlt.stage.common.utils.Page;

public class ProjectImage  extends Page{
    private Integer id;

    private Integer projectId;

    private String imagePath;

    private String description;

    private Integer operId;

    private String message;

    private Integer permission;
    //用户手机号
    private String userMobile;
    
    private Integer orderImageId;

    // 加载的工程名
    private String projectName;

    // 工程描述
    private String projectDescription;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOperId() {
        return operId;
    }

    public void setOperId(Integer operId) {
        this.operId = operId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getPermission() {
        return permission;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public Integer getOrderImageId() {
		return orderImageId;
	}

	public void setOrderImageId(Integer orderImageId) {
		this.orderImageId = orderImageId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

}

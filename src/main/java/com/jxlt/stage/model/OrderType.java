package com.jxlt.stage.model;

import java.util.List;

import com.jxlt.stage.common.utils.Page;

public class OrderType extends Page{
    private Integer id;

    private Integer parentId;

    private String name;

    private String description;

    private String imagePath;
    
    private Integer flag;
    
    //父节点名称
    private String parentName;
    
    //节点名称
    private String text;
    
    //节点是否展开
    private String state;
    
    //子节点数量
    private Integer childrenCount;
    
    //子节点集合
    private List<OrderType> children;

    public Integer getChildrenCount() {
		return childrenCount;
	}

	public void setChildrenCount(Integer childrenCount) {
		this.childrenCount = childrenCount;
	}

	public List<OrderType> getChildren() {
		return children;
	}

	public void setChildren(List<OrderType> children) {
		this.children = children;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
}
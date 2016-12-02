package com.jxlt.stage.model;

import java.util.Date;

import com.jxlt.stage.common.utils.Page;

public class Group extends Page{
    private Integer id;

    private String name;

    private String description;

    private Date createTime;
    //创建时间
    private String createTimes;
    //领队
    private String leader;
    //是否删除
    private Integer flag;
    //队员总数
    private Integer count;
    //副队长数
    private Integer count1;
    //队长数
    private Integer count2;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public String getCreateTimes() {
		return createTimes;
	}

	public void setCreateTimes(String createTimes) {
		this.createTimes = createTimes;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}


	public Integer getCount1() {
		return count1;
	}

	public void setCount1(Integer count1) {
		this.count1 = count1;
	}

	public Integer getCount2() {
		return count2;
	}

	public void setCount2(Integer count2) {
		this.count2 = count2;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
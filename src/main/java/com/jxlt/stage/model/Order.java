package com.jxlt.stage.model;

import java.util.Date;

import com.jxlt.stage.common.utils.DateUtil;
import com.jxlt.stage.common.utils.Page;
import com.jxlt.stage.common.utils.StringUtil;

public class Order  extends Page{
    private Integer id;

    private String name;

    private Date appointment;

    private Date createTime;

    private Integer orderTypeId;

    private Double area;

    private String homeAddress;

    private String message;

    private Byte depostFlag;

    private Byte newFlag;

    private Byte measureFlag;

    private Integer status;

    private Date finishTime;

    private Date serviceStart;

    private Date serviceEnd;

    private Integer operId;

    private Double amount;

    private String operMessage;

    private String mobile;

    private String refMobile;
    
    //合同号
    private String contractNumber;
    
    //预约时间，前台展示
    private String appointTime;
    
    //创建时间
    private String createDate;
    
    // 户型
    private String houseType;

    //接单人姓名
    private String operName;
    //维修起始时间展示
    private String serviceStarts;
    //维修截止时间展示
    private String serviceEnds;
    
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

    public Date getAppointment() {
        return appointment;
    }

    public void setAppointment(Date appointment) {
        this.appointment = appointment;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Integer orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Byte getDepostFlag() {
        return depostFlag;
    }

    public void setDepostFlag(Byte depostFlag) {
        this.depostFlag = depostFlag;
    }

    public Byte getNewFlag() {
        return newFlag;
    }

    public void setNewFlag(Byte newFlag) {
        this.newFlag = newFlag;
    }

    public Byte getMeasureFlag() {
        return measureFlag;
    }

    public void setMeasureFlag(Byte measureFlag) {
        this.measureFlag = measureFlag;
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

    public Date getServiceStart() {
        return serviceStart;
    }

    public void setServiceStart(Date serviceStart) {
        this.serviceStart = serviceStart;
    }

    public Date getServiceEnd() {
        return serviceEnd;
    }

    public void setServiceEnd(Date serviceEnd) {
        this.serviceEnd = serviceEnd;
    }

    public Integer getOperId() {
        return operId;
    }

    public void setOperId(Integer operId) {
        this.operId = operId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getOperMessage() {
        return operMessage;
    }

    public void setOperMessage(String operMessage) {
        this.operMessage = operMessage;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRefMobile() {
        return refMobile;
    }

    public void setRefMobile(String refMobile) {
        this.refMobile = refMobile;
    }

	public String getAppointTime() {
		return appointTime;
	}

	public void setAppointTime(String appointTime) {
		this.appointTime = appointTime;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}
	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public String getServiceStarts() {		
		return serviceStarts;
	}

	public void setServiceStarts(String serviceStarts) {
		this.serviceStarts = serviceStarts;
	}

	public String getServiceEnds() {
		return serviceEnds;
	}

	public void setServiceEnds(String serviceEnds) {
		this.serviceEnds = serviceEnds;
	}
}

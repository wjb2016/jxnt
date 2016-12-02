package com.jxlt.stage.model;

import java.util.Date;

public class Contract {
    private Integer id;

    private String mobile;

    private String contractNumber;

    private Integer contractType;

    private Integer orderTypeId;

    private Integer orderTypeParentid;

    private Integer keepTime;

    private Date startTime;

    private Date endTime;

    private Integer status;

    private Integer usedGrade;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Integer getContractType() {
        return contractType;
    }

    public void setContractType(Integer contractType) {
        this.contractType = contractType;
    }

    public Integer getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Integer orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public Integer getOrderTypeParentid() {
        return orderTypeParentid;
    }

    public void setOrderTypeParentid(Integer orderTypeParentid) {
        this.orderTypeParentid = orderTypeParentid;
    }

    public Integer getKeepTime() {
        return keepTime;
    }

    public void setKeepTime(Integer keepTime) {
        this.keepTime = keepTime;
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

    public Integer getUsedGrade() {
        return usedGrade;
    }

    public void setUsedGrade(Integer usedGrade) {
        this.usedGrade = usedGrade;
    }
}
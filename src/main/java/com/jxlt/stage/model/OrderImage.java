package com.jxlt.stage.model;

import com.jxlt.stage.common.utils.Page;

public class OrderImage extends Page{
    private Integer id;

    private Integer orderId;

    private String houseImagePath;

    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getHouseImagePath() {
        return houseImagePath;
    }

    public void setHouseImagePath(String houseImagePath) {
        this.houseImagePath = houseImagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
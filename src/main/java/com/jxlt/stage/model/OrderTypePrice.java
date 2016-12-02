package com.jxlt.stage.model;

public class OrderTypePrice {
    private Integer id;

    private Integer orderTypeId;

    private Double keep1Price;

    private Double keep2Price;

    private Double keep3Price;

    private Double keepSysPrice;

    private Double keepDevicePrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Integer orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public Double getKeep1Price() {
        return keep1Price;
    }

    public void setKeep1Price(Double keep1Price) {
        this.keep1Price = keep1Price;
    }

    public Double getKeep2Price() {
        return keep2Price;
    }

    public void setKeep2Price(Double keep2Price) {
        this.keep2Price = keep2Price;
    }

    public Double getKeep3Price() {
        return keep3Price;
    }

    public void setKeep3Price(Double keep3Price) {
        this.keep3Price = keep3Price;
    }

    public Double getKeepSysPrice() {
        return keepSysPrice;
    }

    public void setKeepSysPrice(Double keepSysPrice) {
        this.keepSysPrice = keepSysPrice;
    }

    public Double getKeepDevicePrice() {
        return keepDevicePrice;
    }

    public void setKeepDevicePrice(Double keepDevicePrice) {
        this.keepDevicePrice = keepDevicePrice;
    }
}
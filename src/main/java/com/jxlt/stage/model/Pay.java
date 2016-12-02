package com.jxlt.stage.model;

import java.util.Date;

import com.jxlt.stage.common.utils.Page;
import com.jxlt.stage.common.utils.StringUtil;

public class Pay extends Page{
    private Integer id;

    private Integer orderId;
    //订单合同号依赖
    private String orderContract;

    private Integer contractId;
    //维修保养合同号依赖
    private String cContract;

    private Double payPrice;

    private Date payTime;
    //支付时间依赖
    private String payTimes;

    private Double payBackPrice;

    private Date payBackTime;   
    //退款时间依赖
    private String payBackTimes;

    private Integer payType;
    //支付类型依赖
    private String payAccount;
    //合同号
    private String contract;
    //订单类型
    private Integer orderType;
    //销售统计是否导出：导出1，不导出0
    private Integer flag;
    //导出文件路径
    private String filePath;

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

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public Double getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(Double payPrice) {
        this.payPrice = payPrice;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Double getPayBackPrice() {
        return payBackPrice;
    }

    public void setPayBackPrice(Double payBackPrice) {
        this.payBackPrice = payBackPrice;
    }

    public Date getPayBackTime() {
        return payBackTime;
    }

    public void setPayBackTime(Date payBackTime) {
        this.payBackTime = payBackTime;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

	public String getPayTimes() {
		return payTimes;
	}

	public void setPayTimes(String payTimes) {
		this.payTimes = payTimes;
	}

	public String getPayBackTimes() {
		return payBackTimes;
	}

	public void setPayBackTimes(String payBackTimes) {
		this.payBackTimes = payBackTimes;
	}

	public String getPayAccount() {
		if(payType == 1){
			payAccount = "微信支付";
		}else if(payType == 2){
			payAccount = "现金支付";
		}else{
			payAccount = "其他支付";
		}
		return payAccount;
	}

	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}

	public String getOrderContract() {
		return orderContract;
	}

	public void setOrderContract(String orderContract) {
		this.orderContract = orderContract;
	}

	public String getcContract() {
		return cContract;
	}

	public void setcContract(String cContract) {
		this.cContract = cContract;
	}

	public String getContract() {
		contract = "";
		if(!StringUtil.isEmpty(cContract))
			contract = cContract;
		if(!StringUtil.isEmpty(orderContract))
			contract =  orderContract;
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
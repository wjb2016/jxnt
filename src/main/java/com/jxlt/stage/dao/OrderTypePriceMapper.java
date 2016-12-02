package com.jxlt.stage.dao;

import com.jxlt.stage.model.OrderTypePrice;

public interface OrderTypePriceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderTypePrice record);

    int insertSelective(OrderTypePrice record);

    OrderTypePrice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderTypePrice record);

    int updateByPrimaryKey(OrderTypePrice record);
}
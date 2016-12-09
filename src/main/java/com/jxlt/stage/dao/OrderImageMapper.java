package com.jxlt.stage.dao;


import com.jxlt.stage.model.OrderImage;
@MyBatisRepository
public interface OrderImageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderImage record);

    int insertSelective(OrderImage record);

    OrderImage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderImage record);

    int updateByPrimaryKey(OrderImage record);

}
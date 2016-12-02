package com.jxlt.stage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jxlt.stage.model.Project;
@MyBatisRepository
public interface ProjectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Project record);

    int insertSelective(Project record);

    Project selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);

    //获取订单的工程列表
	List<Project> getProjectListById(Integer id);

	//根据名称查找是否已存在同名工程
	List<Project> getExistProjectByName(Project pro);

	//根据id获取工程
	Project getProjectById(Integer id);

	//取消订单工程中断状态
	void updateProByOrderId(Integer orderId);

	/**
	 * 通过项目经理id去得到分配给他的工程集合
	 * @param id
	 * @param integer 
	 * @return
	 */
	List<String> getProjectListByUserId(@Param("userId")Integer id, @Param("userType")Integer integer);

	//根据合同号获取工程列表
	List<Project> getProjectListByConNum(@Param("contractNum")String contractNum,@Param("userType")Integer integer,@Param("userId")Integer userId);

	//根据订单id获取剩余未完成的工程
	List<Project> getRemainProListById(Integer orderId);

}
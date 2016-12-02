package com.jxlt.stage.dao;

import java.util.List;

import com.jxlt.stage.model.OrderImage;
import com.jxlt.stage.model.ProjectImage;
@MyBatisRepository
public interface ProjectImageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectImage record);

    int insertSelective(ProjectImage record);

    ProjectImage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProjectImage record);

    int updateByPrimaryKey(ProjectImage record);

    //获取工程的所有照片
	List<ProjectImage> getImageById(Integer id);

	//获取照片集合
	List<ProjectImage> getImageList(ProjectImage open);

	//获取照片数量
	int getImageCount(ProjectImage pji);

	//公开工程，更新工程图片为公开状态
	void updateProjectImgByProId(Integer id);
    
	// 获取客服公开的工程图信息
	List<ProjectImage> getProjectImageListInfo();

	int getPermissionPhotoCount();
}
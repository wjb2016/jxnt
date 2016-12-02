package com.jxlt.stage.service.serviceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jxlt.stage.dao.ProjectImageMapper;
import com.jxlt.stage.dao.ProjectMapper;
import com.jxlt.stage.service.ProjectService;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {

	@Resource
	private ProjectMapper projectMapper;
	
	@Resource
	private ProjectImageMapper projectImageMapper;
	

}

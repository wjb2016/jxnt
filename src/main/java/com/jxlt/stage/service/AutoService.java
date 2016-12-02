package com.jxlt.stage.service;

import java.util.List;

import com.jxlt.stage.common.JsonResult;
import com.jxlt.stage.model.Auto;

public interface AutoService {

	List<Auto> getAutoList(Auto auto);

	int getAutoCount(Auto auto);

	Auto getAutoById(Integer id);

	void changeAutoById(Integer id);

	JsonResult<Auto> saveOrUpdateAuto(Auto auto);

}

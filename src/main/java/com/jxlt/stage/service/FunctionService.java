package com.jxlt.stage.service;

import java.util.List;

import com.jxlt.stage.model.Function;

public interface FunctionService {

	List<Function> getFunctionList();

	List<Function> getFunctionByParentId(Integer id);

	List<Function> getFunctionMainList();

	List<Function> getFunctionChildList(Integer id);
}

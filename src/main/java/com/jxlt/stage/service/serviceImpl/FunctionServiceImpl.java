package com.jxlt.stage.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jxlt.stage.dao.FunctionMapper;
import com.jxlt.stage.model.Function;
import com.jxlt.stage.service.FunctionService;

@Service("functionService")
public class FunctionServiceImpl implements FunctionService {

	@Resource
	private FunctionMapper functionMapper;

	public List<Function> getFunctionList() {
		List<Function>lf = functionMapper.getFunctionList();
		List<Function> mainlist = new ArrayList<Function>();
		for(Function function:lf){
			if(function.getParentId()==0){
				function.setChildFunctionlist(getChildrenList(lf,function.getId()));
				mainlist.add(function);
			}
		}
		return mainlist;
	}
	
	private List<Function> getChildrenList(List<Function> lf,Integer parentFunctionId){
		List<Function> mainlist = new ArrayList<Function>();
		for(Function uf:lf){
			if(uf.getParentId()==parentFunctionId){
	//			function.setChildFunctionlist(getChildrenList(lf));
				mainlist.add(uf);
			}
		}
		return mainlist;
    }

	public List<Function> getFunctionByParentId(Integer id) {
		// TODO Auto-generated method stub
		return functionMapper.getFunctionByParentId(id);
	}

	public List<Function> getFunctionMainList() {
		// TODO Auto-generated method stub
		return functionMapper.getFunctionMainList();
	}

	public List<Function> getFunctionChildList(Integer id) {
		// TODO Auto-generated method stub
		return functionMapper.getFunctionChildList(id);
	}
	
//	@Override
//	public List<Function> getFunctionList() {
//		// TODO Auto-generated method stub
//		List<Function>lf = functionMapper.getFunctionList();
//		List<Function> mainlist = new ArrayList<Function>();
//		for(Function function:lf){
//			if(function.getParentId()==0){
//				function.setChildFunctionlist(getChildrenList(lf,function.getId()));
//				mainlist.add(function);
//			}
//		}
//		return mainlist;
//		//return functionMapper.getFunctionList();
//	}
//	private List<Function> getChildrenList(List<Function> lf,Integer parentFunctionId){
//		List<Function> mainlist = new ArrayList<Function>();
//		for(Function uf:lf){
//			if(uf.getParentId()==parentFunctionId){
////				function.setChildFunctionlist(getChildrenList(lf));
//				mainlist.add(uf);
//			}
//		}
//		return mainlist;
//	}
//
//	@Override
//	public List<Function> getFunctionByParentId(Integer id) {
//		// TODO Auto-generated method stub
//		return functionMapper.getFunctionByParentId(id);
//	}
//
//	@Override
//	public List<Function> getFunctionMainList() {
//		// TODO Auto-generated method stub
//		return functionMapper.getFunctionMainList();
//	}
//
//	@Override
//	public List<Function> getFunctionChildList(Integer id) {
//		// TODO Auto-generated method stub
//		return functionMapper.getFunctionChildList(id);
//	}


}

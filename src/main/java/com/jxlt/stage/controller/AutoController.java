package com.jxlt.stage.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jxlt.stage.model.Auto;
import com.jxlt.stage.service.AutoService;
import com.jxlt.stage.service.LogService;
import com.jxlt.stage.common.utils.Constants;
import com.jxlt.stage.common.utils.DateUtil;
import com.jxlt.stage.common.utils.StringUtil;
import com.jxlt.stage.common.JsonResult;


@Scope("prototype")
@Controller
@RequestMapping("/Auto")
public class AutoController extends BaseController {
	
	@Resource(name = "autoService")
	private AutoService autoService; 
	
	@Resource(name = "logService")
	private LogService logService;

	/**
	 * 自动应答列表
	 * @param auto
	 * @param request
	 * @param response
	 * @return "web/base/autoList"
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value="/autoList.do")
	public String autoList(Auto auto,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		if (auto.getSearchName() != null	&& auto.getSearchName().length() > 0) 
		{
			String searchName = new String(auto.getSearchName().getBytes("iso8859-1"), "utf-8");
			auto.setSearchName(searchName);		
		}
		if (auto.getPageNo() == null)
			auto.setPageNo(1);
		auto.setPageSize(Constants.DEFAULT_PAGE_SIZE); 
		int totalCount = 0;
		List<Auto> autolist = new ArrayList<Auto>();
		
		try{
			totalCount = autoService.getAutoCount(auto);
			autolist = autoService.getAutoList(auto);
			for(Auto a:autolist){
				String ctime = DateUtil.sortFormat(a.getCreateTime());
				a.setCreateTimes(ctime);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		auto.setTotalCount(totalCount);
		request.setAttribute("Autolist", autolist);
		request.setAttribute("Auto", auto);
		return "web/base/autoList";	     
	}
	
	
	/**
	 * 禁用、启用自动应答
	 * @param id
	 * @param request
	 * @param response
	 * @return JsonResult<Auto>
	 */
	@ResponseBody
	@RequestMapping(value = "/jsonChangeAutoById.do", method = RequestMethod.POST, produces = { "text/html;charset=UTF-8" })
	public JsonResult<Auto> ChangeAuto(
			@RequestParam(value = "id", required = false) Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		JsonResult<Auto> js = new JsonResult<Auto>();
		js.setCode(1);
		js.setMessage("修改自动应答状态失败!");
		int flag = 1;
		try{
			if(id > 0){
				    Auto auto = autoService.getAutoById(id);
				    flag = auto.getFlag();
					autoService.changeAutoById(id);
					js.setCode(0);
					if(flag == 1){
						js.setMessage("禁用自动应答成功！");
						logService.writeLog("禁用自动应答:"+auto.getKeyword());
					}else{
						js.setMessage("启用自动应答成功！");
						logService.writeLog("启用自动应答:"+auto.getKeyword());
					}				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return js;
	} 
	
	
	
	/**
	 * 自动应答详情
	 * @param id
	 * @param request
	 * @param response
	 * @return "web/base/auto"
	 */
	@RequestMapping(value = "/autoInfo.do", method = RequestMethod.GET, produces = { "text/html;charset=UTF-8" })
	public String AutoInfo(
			@RequestParam(value = "id", required = false) Integer id,
			HttpServletRequest request, HttpServletResponse response) {
	    	Auto auto = new Auto();
	    	try{	    		
	    		if(id > 0){
	    			auto = autoService.getAutoById(id);
	    			String ctime = DateUtil.longFormat(auto.getCreateTime());
	    			auto.setCreateTimes(ctime);
	    		}
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	request.setAttribute("Auto", auto);
			return "web/base/autoInfo";
		
	}
	
	/**
	 * 新建、编辑自动应答保存
	 * @param auto
	 * @param request
	 * @param response
	 * @return JsonResult<Auto>
	 */
	@ResponseBody
	@RequestMapping(value = "/jsonSaveOrUpdateAuto.do", method = RequestMethod.POST, produces = { "text/html;charset=UTF-8" })
	public JsonResult<Auto> SaveOrUpdateAuto(
			Auto auto,HttpServletRequest request, HttpServletResponse response) {
		JsonResult<Auto> js = new JsonResult<Auto>();
		js.setCode(1);
		js.setMessage("保存自动应答失败!");
		int flag = 0;
		if(auto.getId() == null || auto.getId() == 0){
	   		auto.setId(flag);
	   		auto.setFlag(1);
		}else{
			flag = auto.getId();
		}
		if(StringUtil.isEmpty(auto.getKeyword()) || StringUtil.isEmpty(auto.getAnswer())){
			js.setMessage("自动应答和关键字不能为空！");
			return js;
		}
		try{
			js = autoService.saveOrUpdateAuto(auto);
			if(flag == 0){
				logService.writeLog("添加自动应答:"+auto.getKeyword());
			}else{
				logService.writeLog("修改自动应答:"+auto.getKeyword());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return js;
	} 
}

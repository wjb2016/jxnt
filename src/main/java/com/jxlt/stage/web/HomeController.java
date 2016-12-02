package com.jxlt.stage.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jxlt.stage.controller.BaseController;



@Controller
public class HomeController extends BaseController{

	/*** 
     * 首页 返回至/page/login.jsp页面 
     * @return 
     */  
	@RequestMapping("index")  
    public ModelAndView index(){  
        //创建模型跟视图，用于渲染页面。并且指定要返回的页面为login页面  
        ModelAndView mav = new ModelAndView("login");  
        return mav;
    }   
}

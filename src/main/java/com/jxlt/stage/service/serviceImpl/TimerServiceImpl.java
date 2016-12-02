package com.jxlt.stage.service.serviceImpl;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.jxlt.stage.common.utils.DateUtil;
import com.jxlt.stage.dao.AutoMapper;
import com.jxlt.stage.dao.GradeMapper;
import com.jxlt.stage.dao.LogMapper;
import com.jxlt.stage.dao.OrderMapper;
import com.jxlt.stage.dao.UserMapper;
import com.jxlt.stage.model.Auto;
import com.jxlt.stage.model.Grade;
import com.jxlt.stage.model.Log;
import com.jxlt.stage.model.Order;
import com.jxlt.stage.model.User;

  
public class TimerServiceImpl {
	
	
	@Resource 
	private AutoMapper autoMapper;
	
	@Resource 
	private LogMapper logMapper;
	
	@Resource 
	private OrderMapper orderMapper;
	
	@Resource 
	private UserMapper userMapper;
	
	@Resource 
	private GradeMapper gradeMapper;
	
	/**
	 * 定时服务
	 */
	public void work(){				
        System.out.println("定时服务启动！");
        //变更相应数据总数
        int code = 0; 
        //实体类工具组
        Log log = new Log();
        User user = new User();
        Grade grade = new Grade();
        Order order = new Order();
        List<User> userlist = new ArrayList<User>();
		List<Auto> autolist = new ArrayList<Auto>();
		List<Order> orderlist = new ArrayList<Order>();
		List<Log> loglist = new ArrayList<Log>();
		
		//积分记录基本信息
		grade.setId(0);
		grade.setOperId(0);
		grade.setCreateTime(new Date());
		grade.setGrade(888);
		grade.setDescription("生日赠送积分");
		
		//日志基本信息
		log.setOperTime(new Date());
		log.setUserId(0);
		log.setId(0);
		//清除过期自动应答
		 try{     
			 //删除停用超过30天自动应答
        	autolist = autoMapper.getDeleteAutoList();
    		for(Auto a:autolist){
    			if(a.getFlag() == 0){
					autoMapper.deleteByPrimaryKey(a.getId());
					code++;
				}
			}  
    		log.setOper("清除过期自动应答"+code+"条。");
    		if(code > 0)
	    	     logMapper.insertSelective(log);
	        } catch (Exception e) {
				e.printStackTrace();
				System.err.println("订时服务之过期自动应答清理出错!");
		   } 	
		 //清除过期订单
		 code = 0;
	     try{
			//Date start = DateUtil.longParse("2016-01-01 00:00:00");
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, -1);
			Date end = c.getTime();
			//order.setStartTime(start);
			order.setEndTime(end);
			order.setStatus(1);
			//一个月未确认订单
			orderlist = orderMapper.getOrderList(order);
			for(Order p:orderlist){
				orderMapper.deleteByPrimaryKey(p.getId());
				code++;
			}
			log.setOper("清除过期订单"+code+"条。");
			if(code > 0)
	    	     logMapper.insertSelective(log);
    	    System.out.println("清除过期订单"+code+"条。");
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("订时服务之过期订单清理出错!");
		}
	     //生日送积分
	     code = 0;
	     try{
	    	 //当天生日
	    	 userlist = userMapper.getBirthList();
	    	 for(User u:userlist){
	    		 //积分添加
	    		 grade.setUserId(u.getId());
	    		 gradeMapper.insert(grade);
	    		 u.setGrade(u.getGrade()+grade.getGrade());
	    		 userMapper.updateByPrimaryKeySelective(u);
	    		 code++;
	    	 }
	    	 log.setOper("当天生日赠送积分人数"+code+"位。");
	    	 if(code > 0)
	    	     logMapper.insertSelective(log);
	    	 System.out.println(log.getOper());
	     }catch(Exception e){
	    	 e.printStackTrace();
	    	 System.err.println("订时服务之生日赠送积分出错!");
	     }
	     //维修到期提醒
	     code = 0;
	     try{
	    	 //保修即将到期（一周）
	    	 orderlist = new ArrayList<Order>();
	    	 orderlist = orderMapper.getExpiredOrder();
	    	 for(Order p:orderlist){
	    		
	    		 Calendar c = Calendar.getInstance();
	    		 c.add(Calendar.DAY_OF_MONTH, 7);
	    		 String ctime = c.getTime().toString().substring(0, 9);
	    		 String mobile = p.getMobile();
	    		 String name = p.getName();
	    		 String message = name+"您好，您的保修服务即将到期，到期时间"+ctime+"，如要办理延保服务请尽快联系我们，超出保修时间将无法办理，精欣暖通祝你生活愉快!";
	    		 /*
	    		  * 调用短信模板通知用户
	    		  */
	    		 System.out.println(mobile+":"+message);
	    		 code++;
	    	 }
	    	 log.setOper("当天推送保修到期短信"+code+"条。");
	    	 if(code > 0)
	    	     logMapper.insertSelective(log);
	    	 System.out.println(log.getOper());
	     }catch(Exception e){
	    	 e.printStackTrace();
	    	 System.err.println("订时服务之保修到期推送出错!");
	     }
	     //日志清理
	     code = 0;
	     try{
	    	 //保修即将到期（一周）
	    	 code = logMapper.deleteExpiredLog();	    	
	    	 if(code > 0)
	    		 log.setOper("清理过期日志"+code+"条。");
	    	 System.out.println(log.getOper());
	     }catch(Exception e){
	    	 e.printStackTrace();
	    	 System.err.println("订时服务之删除过期日志失败!");
	     }
	}
	
	
	
}

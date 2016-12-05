package com.jxlt.stage.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jxlt.stage.common.JsonResult;
import com.jxlt.stage.common.utils.Constants;
import com.jxlt.stage.common.utils.DateUtil;
import com.jxlt.stage.common.utils.StringUtil;
import com.jxlt.stage.model.Grade;
import com.jxlt.stage.model.Log;
import com.jxlt.stage.model.Pay;
import com.jxlt.stage.model.PayVM;
import com.jxlt.stage.model.User;
import com.jxlt.stage.service.GradeService;
import com.jxlt.stage.service.LogService;
import com.jxlt.stage.service.OrderService;
import com.jxlt.stage.service.PayService;

@Scope("prototype")
@Controller
@RequestMapping("/Statistic")
public class StatisticController extends BaseController {

	@Resource(name = "orderService")
	private OrderService orderService; 
	
	@Resource(name = "logService")
	private LogService logService; 
	
	@Resource(name = "gradeService")
	private GradeService gradeService; 
	
	@Resource(name = "payService")
	private PayService payService; 

	/**
	 * 销售统计列表
	 * @param pay
	 * @param request
	 * @param response
	 * @return "web/statistic/saleList"
	 */
	@RequestMapping(value="/saleList.do")
	public String saleList(Pay pay,HttpServletRequest request, HttpServletResponse response){
		List<PayVM> salelist = new ArrayList<PayVM>();
		int flag = 0;
		try{
			pay = CheckSearch(pay);
			salelist = payService.getSaleAmount(pay);
			if(pay.getFlag() != null && pay.getFlag() == 1){
				//导出销售统计:成功2，失败4
				pay = exportSale(pay,salelist);	
				if(pay.getFlag() == 2){
					logService.writeLog("导出销售数据");
				}
			}else{
				pay.setFlag(flag);
			}
						
		}catch(Exception e){
			e.printStackTrace();
			pay.setFlag(flag);
		}		
		request.setAttribute("Pay", pay);
		request.setAttribute("salelist", salelist);
		return "web/statistic/saleList";
	}
		
	/**
	 * 日志列表
	 * @param log
	 * @param request
	 * @param response
	 * @return "web/statistic/logList"
	 * @throws UnsupportedEncodingException 
	 * @throws ParseException 
	 */
	@RequestMapping(value="/logList.do")
	public String logList(Log log,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, ParseException{
		//System.out.println("UserController-test");
		log = CheckSearch(log);
		List<Log> loglist = new ArrayList<Log>();
		int totalCount = 0;
		String searchName = log.getSearchName();
		if("系统".equals(searchName) || "系统操作".equals(searchName)){
			log.setUserId(0);
			log.setSearchName(null);
		}	
		try{
			//管理员可见自己日志以及系统记录
			User user = this.getLoginUser();
			if(user.getUtype() == 10 && log.getUserId() == null)
				log.setUserId(user.getId());
			loglist = logService.getLogList(log);
			totalCount = logService.getTotalCount(log);
			for(Log s:loglist)
				s.setOperTimes(DateUtil.sortFormat(s.getOperTime()));
		}catch(Exception e){
			e.printStackTrace();
		}
		if("系统".equals(searchName) || "系统操作".equals(searchName)){
			log.setSearchName(searchName);
			log.setUserId(null);
		}
		log.setTotalCount(totalCount);
		request.setAttribute("Log", log);
		request.setAttribute("loglist", loglist);
		return "web/statistic/logList";
	     
	}
		
	/**
	 * 积分列表
	 * @param grade
	 * @param request
	 * @param response
	 * @return "web/statistic/gradeList"
	 * @throws UnsupportedEncodingException 
	 * @throws ParseException 
	 */
	@RequestMapping(value="/gradeList.do")
	public String gradeList(Grade grade,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, ParseException{
		grade = CheckSearch(grade);
		List<Grade> gradelist = new ArrayList<Grade>();		
		int totalCount = 0;
		int convertCount = 0;
		try{
			gradelist = gradeService.getGradeList(grade);
			totalCount = gradeService.getTotalCount(grade);
			for(Grade s:gradelist){
				s.setCreateTimes(DateUtil.sortFormat(s.getCreateTime()));
				if(s.getOperId() == 0 && s.getGrade() < 0)
					convertCount++;
			}
			grade.setConvertCount(convertCount);
		}catch(Exception e){
			e.printStackTrace();
		}
		grade.setTotalCount(totalCount);
		request.setAttribute("Grade", grade);
		request.setAttribute("gradelist", gradelist);
		return "web/statistic/gradeList";	     
	}
		
	/**
	 * 支付列表
	 * @param pay
	 * @param request
	 * @param response
	 * @return "web/statistic/payList"
	 * @throws UnsupportedEncodingException 
	 * @throws ParseException 
	 */
	@RequestMapping(value="/payList.do")
	public String payList(Pay pay,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, ParseException{
		pay = CheckSearch(pay);
		List<Pay> paylist = new ArrayList<Pay>();
		int totalCount = 0;
		try{
			paylist = payService.getPayList(pay);
			totalCount = payService.getTotalCount(pay);
			for(Pay s:paylist){
				s.setPayTimes(DateUtil.sortFormat(s.getPayTime()));
				if(s.getPayBackTime() != null)
			    	s.setPayBackTimes(DateUtil.sortFormat(s.getPayBackTime()));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		pay.setTotalCount(totalCount);
		request.setAttribute("Pay", pay);
		request.setAttribute("paylist", paylist);
		return "web/statistic/payList";	     
	}
	
	/**
	 * 查询条件判断
	 * @param pay
	 * @return pay
	 * @throws ParseException
	 * @throws UnsupportedEncodingException
	 */
	private Pay CheckSearch(Pay pay) throws ParseException, UnsupportedEncodingException{
		//查询转码
		if (pay.getSearchName() != null	&& pay.getSearchName().length() > 0) 
		{
			String searchName = new String(pay.getSearchName().getBytes("iso8859-1"), "utf-8");
			pay.setSearchName(searchName);		
		}
		//分页设置
		if (pay.getPageNo() == null)
			pay.setPageNo(1);
		pay.setPageSize(Constants.DEFAULT_PAGE_SIZE); 
		//初始化默认结束时间为开始时间
		String startTimes = pay.getStartTimes();
		String endTimes = startTimes;
	    Date startTime = null;
	    Date endTime = null;
	    //开始时间为空返回null
		if(!StringUtil.isDate(startTimes)){
			pay.setStartTime(null);
			pay.setStartTimes("");
			pay.setEndTime(null);
			pay.setEndTimes("");
			return pay;		
		}
		endTime = DateUtil.sortParse(endTimes);
		startTime = DateUtil.sortParse(startTimes);
		//结束时间大于开始时间赋值结束时间
		if(StringUtil.isDate(pay.getEndTimes())){			
			String endTimes1 = pay.getEndTimes();
			Date endTime1 = DateUtil.sortParse(endTimes1);
			
			if(endTime1.after(endTime)){
				endTimes = endTimes1;
				endTime = endTime1;
			}
		}
		//结束时间往后推一天
		Calendar  calendar  =  new  GregorianCalendar(); 
		calendar.setTime(endTime); 
		calendar.add(Calendar.DAY_OF_MONTH,1);//把日期往后增加一天.整数往后推,负数往前移动 
		endTime=calendar.getTime();
		
		pay.setStartTime(startTime);
		pay.setStartTimes(startTimes);
		pay.setEndTime(endTime);
		pay.setEndTimes(endTimes);
		return pay;		
	}
	
	/**
	 * 查询条件判断
	 * @param grade
	 * @return grade
	 * @throws ParseException
	 * @throws UnsupportedEncodingException
	 */
	private Grade CheckSearch(Grade grade) throws ParseException, UnsupportedEncodingException{
		//查询转码
		if (grade.getSearchName() != null	&& grade.getSearchName().length() > 0) 
		{
			String searchName = new String(grade.getSearchName().getBytes("iso8859-1"), "utf-8");
			grade.setSearchName(searchName);		
		}
		//分页设置
		if (grade.getPageNo() == null)
			grade.setPageNo(1);
		grade.setPageSize(Constants.DEFAULT_PAGE_SIZE); 
		//初始化默认结束时间为开始时间
		String startTimes = grade.getStartTimes();
		String endTimes = startTimes;
	    Date startTime = null;
	    Date endTime = null;
	    //开始时间为空返回null
		if(!StringUtil.isDate(startTimes)){
			grade.setStartTime(null);
			grade.setStartTimes("");
			grade.setEndTime(null);
			grade.setEndTimes("");
			return grade;		
		}
		endTime = DateUtil.sortParse(endTimes);
		startTime = DateUtil.sortParse(startTimes);
		//结束时间大于开始时间赋值结束时间
		if(StringUtil.isDate(grade.getEndTimes())){			
			String endTimes1 = grade.getEndTimes();
			Date endTime1 = DateUtil.sortParse(endTimes1);
			
			if(endTime1.after(endTime)){
				endTimes = endTimes1;
				endTime = endTime1;
			}
		}
		//结束时间往后推一天
		Calendar  calendar  =  new  GregorianCalendar(); 
		calendar.setTime(endTime); 
		calendar.add(Calendar.DAY_OF_MONTH,1);//把日期往后增加一天.整数往后推,负数往前移动 
		endTime=calendar.getTime();
		
		grade.setStartTime(startTime);
		grade.setStartTimes(startTimes);
		grade.setEndTime(endTime);
		grade.setEndTimes(endTimes);
		return grade;		
	}
	
	/**
	 * 查询条件判断
	 * @param log
	 * @return log
	 * @throws ParseException
	 * @throws UnsupportedEncodingException
	 */
	private Log CheckSearch(Log log) throws ParseException, UnsupportedEncodingException{
		//查询转码
		if (log.getSearchName() != null	&& log.getSearchName().length() > 0) 
		{
			String searchName = new String(log.getSearchName().getBytes("iso8859-1"), "utf-8");			
			log.setSearchName(searchName);										
		}
		//分页设置
		if (log.getPageNo() == null)
			log.setPageNo(1);
		log.setPageSize(Constants.DEFAULT_PAGE_SIZE); 
		//初始化默认结束时间为开始时间
		String startTimes = log.getStartTimes();
		String endTimes = startTimes;
	    Date startTime = null;
	    Date endTime = null;
	    //开始时间为空返回null
		if(!StringUtil.isDate(startTimes)){
			log.setStartTime(null);
			log.setStartTimes("");
			log.setEndTime(null);
			log.setEndTimes("");
			return log;		
		}
		endTime = DateUtil.sortParse(endTimes);
		startTime = DateUtil.sortParse(startTimes);
		//结束时间大于开始时间赋值结束时间
		String endTimes1 = log.getEndTimes();
		if(StringUtil.isDate(endTimes1)){						
			Date endTime1 = DateUtil.sortParse(endTimes1);			
			if(endTime1.after(endTime)){
				endTimes = endTimes1;
				endTime = endTime1;
			}
		}
		//结束时间往后推一天
		Calendar  calendar  =  new  GregorianCalendar(); 
		calendar.setTime(endTime); 
		calendar.add(Calendar.DAY_OF_MONTH,1);//把日期往后增加一天.整数往后推,负数往前移动 
		endTime=calendar.getTime();
		
		log.setStartTime(startTime);
		log.setStartTimes(startTimes);
		log.setEndTime(endTime);
		log.setEndTimes(endTimes);
		return log;		
	}
	
	/**
	 * 导出销售统计
	 * @param pay
	 * @param list
	 * @return 成功2，失败4
	 */
	private Pay exportSale(Pay pay,List<PayVM> list){
		int result = 4;
		if(list.size() > 0){
		//	result = 2;
			String path =  createSaleExcel(pay,list);
			if(!StringUtil.isEmpty(path)){
				result = 2;
				pay.setFilePath(path);
			}
		}
		pay.setFlag(result);
		return pay;		
	}
	
	/**
	 * 生成excel
	 * @param pay
	 * @param list
	 * @return
	 */
	private String createSaleExcel(Pay pay,List<PayVM> list){
		String filePath = "";
		
		Workbook workbook = null;
		try {
			workbook = new HSSFWorkbook();
			
			if (workbook != null) {			
				
				Sheet sheet = workbook.createSheet("暖通销售统计");
				sheet.setVerticallyCenter(true);			
				Row row0 = sheet.createRow(0);				
				String title = "销售统计";
					
				Cell cell_0 = row0.createCell(0, Cell.CELL_TYPE_STRING);
				cell_0.setCellValue(title);
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
				
				/**
				 * row1
				 */
				Row row1 = sheet.createRow(1);
				Cell cell_row1_1 = row1.createCell(0, Cell.CELL_TYPE_STRING);
				cell_row1_1.setCellValue("统计类型");
				sheet.autoSizeColumn(0);
	
				Cell cell_row1_2 = row1.createCell(1, Cell.CELL_TYPE_STRING);
				cell_row1_2.setCellValue("有效订单数");
				sheet.autoSizeColumn(1);
				
				Cell cell_row1_3 = row1.createCell(2, Cell.CELL_TYPE_STRING);
				cell_row1_3.setCellValue("成交订单数");
				sheet.autoSizeColumn(2);
				
				Cell cell_row1_4 = row1.createCell(3, Cell.CELL_TYPE_STRING);
				cell_row1_4.setCellValue("成交订单e");
				sheet.autoSizeColumn(3);
	
				Cell cell_row1_5 = row1.createCell(4, Cell.CELL_TYPE_STRING);
				cell_row1_5.setCellValue("付款订单额");
				sheet.autoSizeColumn(4);
				
				Cell cell_row1_6 = row1.createCell(5, Cell.CELL_TYPE_STRING);
				cell_row1_6.setCellValue("付款订单额");
				sheet.autoSizeColumn(5);
				
				Cell cell_row1_7 = row1.createCell(6, Cell.CELL_TYPE_STRING);
				cell_row1_7.setCellValue("评价人数");
				sheet.autoSizeColumn(6);
				
				Cell cell_row1_8 = row1.createCell(7, Cell.CELL_TYPE_STRING);
				cell_row1_8.setCellValue("总体评价");
				sheet.autoSizeColumn(7);
				
				/**
				 * rows统计内容
				 */
				int i= 2;
				for(PayVM p:list){
					Row row = sheet.createRow(i);
					Cell p1 = row.createCell(0, Cell.CELL_TYPE_STRING);
					p1.setCellValue(p.getName());
					sheet.autoSizeColumn(0);
					
					Cell p2 = row.createCell(1, Cell.CELL_TYPE_STRING);
					p2.setCellValue(p.getAllCount());
					sheet.autoSizeColumn(1);
					
					Cell p3 = row.createCell(2, Cell.CELL_TYPE_STRING);
					p3.setCellValue(p.getOrderCount());
					sheet.autoSizeColumn(2);
					
					Cell p4 = row.createCell(3, Cell.CELL_TYPE_STRING);
					p4.setCellValue(p.getOrderAmount());
					sheet.autoSizeColumn(3);
					
					Cell p5 = row.createCell(4, Cell.CELL_TYPE_STRING);
					p5.setCellValue(p.getPayCount());
					sheet.autoSizeColumn(4);
					
					Cell p6 = row.createCell(5, Cell.CELL_TYPE_STRING);
					p6.setCellValue(p.getPayAmount());
					sheet.autoSizeColumn(5);
					
					Cell p7 = row.createCell(6, Cell.CELL_TYPE_STRING);
					p7.setCellValue(p.getAssessCount());
					sheet.autoSizeColumn(6);
					
					Cell p8 = row.createCell(7, Cell.CELL_TYPE_STRING);
					p8.setCellValue(p.getAssess());
					sheet.autoSizeColumn(7);		
					i++;
				}
				
				/**
				 * row起始时间
				 */
				Row row_s = sheet.createRow(i);
				i++;
				Cell st1 = row_s.createCell(0, Cell.CELL_TYPE_STRING);
				st1.setCellValue("起始时间");
				sheet.autoSizeColumn(0);
				
				String startTime = pay.getStartTimes();							
				Cell st2 = row_s.createCell(1, Cell.CELL_TYPE_STRING);
				if(StringUtil.isEmpty(startTime))
					startTime = "null";
				st2.setCellValue(startTime);
				sheet.autoSizeColumn(1);
				
				/**
				 * row结束时间
				 */
				Row row_e = sheet.createRow(i);
				Cell et1 = row_e.createCell(0, Cell.CELL_TYPE_STRING);
				et1.setCellValue("结束时间");
				sheet.autoSizeColumn(0);
				
				String endTime = pay.getEndTimes();
				if(StringUtil.isEmpty(endTime))
					endTime = DateUtil.sortFormat(new Date());
				Cell et2 = row_e.createCell(1, Cell.CELL_TYPE_STRING);
				et2.setCellValue(endTime);
				sheet.autoSizeColumn(1);
				
			
		
			
		    	/**
		    	 * 导出
				 */
				String serverPath = getClass().getResource("/").getFile()
						.toString();
				serverPath = serverPath.substring(0, (serverPath.length() - 16));
				 serverPath += "data";
				File file = new File(serverPath);
				if(!file.exists() && !file.isDirectory()){
					file.mkdirs();
				}
				serverPath += "/tempFile";
				file = new File(serverPath);
				if(!file.exists() && !file.isDirectory()){
					file.mkdirs();
				}
				String createTime = DateUtil.sortFormat(new Date());
				String str = createTime.replace("-", "");
				filePath = "/" + str + "_sale.xls";
				String realPath = serverPath + filePath;
	
				try {
					
					FileOutputStream outputStream = new FileOutputStream(
							realPath);
					workbook.write(outputStream);
					outputStream.flush();
					outputStream.close();
					return filePath;
				}catch (Exception e) {
					e.printStackTrace();
					return "";
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
		return "";
	}

	@ResponseBody
	@RequestMapping(value = "/jsonChangeGradeById.do", method = RequestMethod.POST, produces = { "text/html;charset=UTF-8" })
	public JsonResult<Grade> ConvertGrade(
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "flag", required = false) Integer flag,
			HttpServletRequest request, HttpServletResponse response) {
		JsonResult<Grade> js = new JsonResult<Grade>();
		js.setCode(1);
		js.setMessage("积分兑换确认失败!");
		if(flag == 1)
			js.setMessage("积分兑换取消失败!");
		int temp = -1;
		try{
			User user = this.getLoginUser();
			if(id > 0){
				if(flag == 0){
					//兑换积分
					js = gradeService.convertGrade(id,user.getId());					
					logService.writeLog(""+js.getObj());
				}else if(flag == 1){
					//取消兑换
					js = gradeService.disconvertGrade(id,user.getId());					
					logService.writeLog(""+js.getObj());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return js;
	} 
}
		
	



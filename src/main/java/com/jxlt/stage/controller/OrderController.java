package com.jxlt.stage.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.jxlt.stage.common.JsonResult;
import com.jxlt.stage.common.utils.Constants;
import com.jxlt.stage.common.utils.DateUtil;
import com.jxlt.stage.common.utils.FileUtil;
import com.jxlt.stage.common.utils.MessageUtil;
import com.jxlt.stage.common.utils.StringUtil;
import com.jxlt.stage.model.Grade;
import com.jxlt.stage.model.Group;
import com.jxlt.stage.model.Order;
import com.jxlt.stage.model.OrderType;
import com.jxlt.stage.model.Pay;
import com.jxlt.stage.model.Project;
import com.jxlt.stage.model.ProjectImage;
import com.jxlt.stage.model.User;
import com.jxlt.stage.service.GradeService;
import com.jxlt.stage.service.LogService;
import com.jxlt.stage.service.OrderService;
import com.jxlt.stage.service.UserService;

/**
 * 订单控制器
 * @author abo
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/Order")
public class OrderController extends BaseController {

	@Resource(name = "orderService")
	private OrderService orderService; 
	
	@Resource(name="logService")
	private LogService logService;

	@Resource(name="gradeService")
	private GradeService gradeService;
	
	@Resource(name="userService")
	private UserService userService;
	
	/**
	 * 订单列表
	 * @param request
	 * @param response
	 * @param order
	 * @return
	 */
	@RequestMapping(value="/orderList.do")
	public String heatingList(Order order,
			HttpServletRequest request, HttpServletResponse response){
		//设置起始页码及页面显示数量
		if(order.getPageNo() == null){
			order.setPageNo(1);
		}
		order.setPageSize(Constants.DEFAULT_PAGE_SIZE);
		
		int totalCount = 0;	//订单总数
		List<Order> orderlist = new ArrayList<Order>(); //订单集合
		
		try {
			String endTime = null;
			if(order.getEndTimes() != null && !"".equals(order.getEndTimes())){
				String endTimes = order.getEndTimes();
				endTime = order.getEndTimes();
				Date endDate = DateUtil.parse(endTimes, "yyyy-MM-dd");
				Calendar calendar = new GregorianCalendar(); 
				calendar.setTime(endDate); 
				calendar.add(Calendar.DAY_OF_MONTH,1);//把日期往后增加一天.整数往后推,负数往前移动 
				endDate=calendar.getTime();
				endTimes = DateUtil.format(endDate, "yyyy-MM-dd");
				order.setEndTimes(endTimes);
			}
			//获取订单集合及总数，姓名模糊匹配，手机号全匹配
			totalCount = orderService.getTotalCount(order);
			orderlist = orderService.getOrderList(order);
			
			if(endTime != null){
				order.setEndTimes(endTime);
			}
			//格式化订单创建日期
			if(orderlist.size() > 0){
				for(Order o:orderlist){
					o.setCreateDate(DateUtil.format(o.getCreateTime(), "yyyy-MM-dd"));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		order.setTotalCount(totalCount);
		request.setAttribute("Order", order);
		request.setAttribute("OrderList", orderlist);
		return "web/order/orderList";	     
	}
	
	/**
	 * 导出订单，1、生成订单数据excel；2、下载excel
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/exportExcel.do")
	public JsonResult<Order> exportExcel(HttpServletRequest request,
			HttpServletResponse response) {
		//json初始化
		JsonResult<Order> js = new JsonResult<Order>();
		js.setCode(new Integer(1));
		js.setMessage("导出订单数据失败！");
		
		List<Order> list = new ArrayList<Order>();
		Order order = new Order();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
		try {
			//获取未确认订单列表
			order.setStatus(0);
			list = orderService.getUnsureOrder(order);
			
			//格式化预约时间
			for(Order od :list){
				if(od.getAppointment() != null){
					od.setAppointTime(sdf.format(od.getAppointment()));
				}
				od.setCreateDate(sdf.format(od.getCreateTime()));
			}
			
			if(list.size()>0){
				//创建Excle文件，并将文件路径返回
				String filePath = createOrderExcel(list);
				if(!StringUtil.isEmpty(filePath)){
					//更新订单状态，标记已导出
					for(Order ord:list){
						ord.setStatus(1);
						orderService.saveOrder(ord);
					}
					js.setCode(0);
					js.setGotoUrl(filePath);
					js.setMessage("导出订单数据成功！");
					
					logService.writeLog("导出订单数据");
				}else{
					js.setMessage("创建Excle文件出错！");
				}
			}else{
				js.setMessage("未确认订单数据都已导出！");
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js;
	}

	/**
	 * 创建Excle文件，并将文件路径返回
	 * @param list
	 * @return
	 */
	private String createOrderExcel(List<Order> list) {
		String filePath ="";
        String filename = "";
        Workbook workbook = null;
        try {
            workbook = new HSSFWorkbook();
            if (workbook != null) { 
                Sheet sheet = workbook.createSheet("订单信息");

                Row row0 = sheet.createRow(0);
                Cell cell0_0 = row0.createCell(0, Cell.CELL_TYPE_STRING);
                cell0_0.setCellValue("客户名称");
                Cell cell0_1 = row0.createCell(1, Cell.CELL_TYPE_STRING);
                cell0_1.setCellValue("客户电话");
                Cell cell0_2 = row0.createCell(2, Cell.CELL_TYPE_STRING);
                cell0_2.setCellValue("房屋类型");
                Cell cell0_3 = row0.createCell(3, Cell.CELL_TYPE_STRING);
                cell0_3.setCellValue("下单时间");
                Cell cell0_4 = row0.createCell(4, Cell.CELL_TYPE_STRING);
                cell0_4.setCellValue("房屋地址");
                Cell cell0_5 = row0.createCell(5, Cell.CELL_TYPE_STRING);
                cell0_5.setCellValue("施工类型");
                Cell cell0_6 = row0.createCell(6, Cell.CELL_TYPE_STRING);
                cell0_6.setCellValue("预约时间");
                Cell cell0_7 = row0.createCell(7, Cell.CELL_TYPE_STRING);
                cell0_7.setCellValue("留言信息");
                Cell cell0_8 = row0.createCell(8, Cell.CELL_TYPE_STRING);
                cell0_8.setCellValue("是否交房");
                Cell cell0_9 = row0.createCell(9, Cell.CELL_TYPE_STRING);
                cell0_9.setCellValue("是否新房");
                Cell cell0_10 = row0.createCell(10, Cell.CELL_TYPE_STRING);
                cell0_10.setCellValue("是否上门测量");
                Cell cell0_11 = row0.createCell(11, Cell.CELL_TYPE_STRING);
                cell0_11.setCellValue("合同编号");
                Cell cell0_12 = row0.createCell(12, Cell.CELL_TYPE_STRING);
                cell0_12.setCellValue("是否有效");

                try {
                    for (int i = 0; i < list.size(); i++) {
                        Row row = sheet.createRow(1 + i);
                        Cell cell1 = row.createCell(0, Cell.CELL_TYPE_STRING);
                        cell1.setCellValue(list.get(i).getName());
                        sheet.autoSizeColumn(1,true);
                         
                        Cell cell2 = row.createCell(1, Cell.CELL_TYPE_STRING);
                        cell2.setCellValue(list.get(i).getMobile());
                        sheet.autoSizeColumn(1,true);
                        
                        Cell cell3 = row.createCell(2, Cell.CELL_TYPE_STRING);
                        String homeType = list.get(i).getHouseType();
                        if(homeType != null){
                        	if(homeType.equals("1")){
                        		cell3.setCellValue("平层一套一");
                        	}else if(homeType.equals("2")){
                        		cell3.setCellValue("平层一套二");
                        	}else if(homeType.equals("3")){
                        		cell3.setCellValue("平层一套三");
                        	}else if(homeType.equals("4")){
                        		cell3.setCellValue("平层一套四");
                        	}else if(homeType.equals("5")){
                        		cell3.setCellValue("跃层/别墅");
                        	}else{
                        		cell3.setCellValue("");
                        	}
                        }else{
                        	cell3.setCellValue("");
                        }
                        sheet.autoSizeColumn(1,true);
                                                                                            
                        Cell cell4 = row.createCell(3, Cell.CELL_TYPE_STRING);
                        cell4.setCellValue(list.get(i).getCreateDate() == null?"":list.get(i).getCreateDate());
                        sheet.autoSizeColumn(1,true);
                        
                        Cell cell5 = row.createCell(4, Cell.CELL_TYPE_STRING);
                        cell5.setCellValue(list.get(i).getHomeAddress() == null ? "":list.get(i).getHomeAddress());
                        sheet.autoSizeColumn(1,true);
                        
                        Cell cell6 = row.createCell(5, Cell.CELL_TYPE_STRING);
                        Integer type = list.get(i).getOrderTypeId();
                        if(type != null){
                        	if(type == 1){
                        		cell6.setCellValue("地暖");
                        	}else if(type == 2){
                        		cell6.setCellValue("中央空调");
                        	}else if(type == 3){
                        		cell6.setCellValue("净水系统");
                        	}else{
                        		cell6.setCellValue("");
                        	}
                        }else{
                        	cell6.setCellValue("");
                        }
                        sheet.autoSizeColumn(1,true);
                                               
                        Cell cell7 = row.createCell(6, Cell.CELL_TYPE_STRING);
                        cell7.setCellValue(list.get(i).getAppointTime() == null?"":list.get(i).getAppointTime());
                        sheet.autoSizeColumn(1,true);
                        
                        Cell cell8 = row.createCell(7, Cell.CELL_TYPE_STRING);
                        cell8.setCellValue(list.get(i).getMessage() == null?"":list.get(i).getMessage());
                        sheet.autoSizeColumn(1,true);
                        
//                        Cell cell9 = row.createCell(8, Cell.CELL_TYPE_STRING);
//                        cell9.setCellValue(list.get(i).getDepostFlag() == null?"":list.get(i).getDepostFlag()==0?"×":"√");
//                        sheet.autoSizeColumn(1,true);
//                        
//                        Cell cell10 = row.createCell(9, Cell.CELL_TYPE_STRING);
//                        cell10.setCellValue(list.get(i).getNewFlag() == null?"":list.get(i).getNewFlag()==0?"×":"√");
//                        sheet.autoSizeColumn(1,true);
//                        
//                        Cell cell11 = row.createCell(10, Cell.CELL_TYPE_STRING);
//                        cell11.setCellValue(list.get(i).getMeasureFlag() == null?"":list.get(i).getMeasureFlag()==0?"×":"√");
//                        sheet.autoSizeColumn(1,true);
//                        
//                        Cell cell12 = row.createCell(11, Cell.CELL_TYPE_STRING);
//                        cell12.setCellValue(list.get(i).getContractNumber() == null?"":list.get(i).getContractNumber());
//                        sheet.autoSizeColumn(1,true);
                        
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date date = new Date();
            String str = sdf.format(date);
            filePath = "tempfile";
            String serverPath = getClass().getResource("/").getFile().toString();
            serverPath = serverPath.substring(0, (serverPath.length() - 16));

            filePath = serverPath+filePath;
            File file = new File(filePath);

            if(!file.exists()){
                file.mkdir();
            }
            filePath += "/"+ str + "_订单详情.xls";
            filename = str + "_订单详情.xls";
            File realFile = new File(filePath);
            if(realFile.exists()){
                realFile.delete();
            }
            try {
                FileOutputStream outputStream = new FileOutputStream(filePath);
                workbook.write(outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
            	e.printStackTrace();
                return "";
            }
        }catch (Exception ex){
        	ex.printStackTrace();
        }
        return filename;
	}
	
	/**
	 * 下载未确认订单excel
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "/downOrderExcel.do")
	public void downExceptionfile(HttpServletRequest request,
			HttpServletResponse response) { 
		try {
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
             Date date = new Date();
             String str = sdf.format(date);
             String filename = str +"_订单详情.xls";
             String filePath = request.getSession().getServletContext().getRealPath("tempfile");
             filePath += "/"+filename;
             response.reset();
             response.setContentType("APPLICATION/OCTET-STREAM; charset=UTF-8");
             response.setHeader("Content-disposition", "attachment;filename=\""
                    + new String(filename.getBytes("GB2312"), "ISO-8859-1")
                    + "\"");

             FileInputStream inStream = new FileInputStream(filePath);
             byte[] b = new byte[100];
             int len;
             while ((len = inStream.read(b)) > 0) {
                response.getOutputStream().write(b, 0, len);
             }
             inStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 订单详情界面
	 * @param orderId
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping("/orderInfo.do")
	public String orderInfo(
			@RequestParam(value="id",required=true)Integer orderId,
			HttpServletRequest req,HttpServletResponse resp
			){
		Order order = new Order();
		try {
			if(orderId != 0){
				//根据id查找订单，并格式化日期
				order = orderService.getOrderById(orderId);
				if(order.getServiceStart() != null && order.getServiceEnd() != null){
					order.setServiceStarts(DateUtil.sortFormat(order.getServiceStart()));
					order.setServiceEnds(DateUtil.sortFormat(order.getServiceEnd()));
				}
//				System.out.println(order.getServiceStart()+"|"+order.getServiceEnd());
//				System.out.println(order.getServiceStarts()+"|"+order.getServiceEnds());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				if(order.getAppointment() != null){
					order.setAppointTime(sdf.format(order.getAppointment()));
				}
				order.setCreateDate(sdf.format(order.getCreateTime()));
				
				//如果订单的操作人id不为空(订单确认之后)，则根据id查找对应的操作人
				if(order.getOperId() != null){
					User operUser = orderService.getUserById(order.getOperId());
					if(operUser != null){
						order.setOperName(operUser.getName());
					}
				}
				
				//获取订单品牌类型所属的所有品牌
				if(order.getOrderTypeId() != null){
					List<OrderType> listOrderType = orderService.getTypeByParentId(order.getOrderTypeId());
					req.setAttribute("listOrderType", listOrderType);
				}
				
				//获取订单的工程列表
				List<Project> listProject = orderService.getProjectListById(order.getId());
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				if(listProject != null){
					for(Project project:listProject){
						project.setStartTimes(format.format(project.getStartTime()));
						project.setEndTimes(format.format(project.getEndTime()));
					}
				}
				req.setAttribute("listProject", listProject);
				
				//获取所有团队集合
				List<Group>	listGroup = orderService.getAllGroup();
				List<Group> newGroup = new ArrayList<Group>();
				for(Group g:listGroup){
					if(g.getCount() > 0){
						newGroup.add(g);
					}
				}
				req.setAttribute("listGroup",newGroup);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		req.setAttribute("order", order);
		
		//根据订单状态，跳转到不同界面：0：未确认，1:已导出，2：已确认，4：进行中，6：已完成，10：已作废
		if(order.getStatus() == null || order.getStatus() == 0 || order.getStatus() == 1){
			return "web/order/unsureOrderInfo";
		}else if(order.getStatus() == 2){
			return "web/order/sureOrderInfo"; 
		}else if(order.getStatus() == 4 || order.getStatus() == 6){
			return "web/order/progressOrderInfo"; 
		}else if(order.getStatus() == 8 || order.getStatus() == 10){
			return "web/order/breakOrderInfo"; 
		}else{
			return "";
		}
	}
	
	/**
	 * 中断工程取消
	 * @param orderId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/jsonUpdateBreakPro.do")
	public JsonResult<Order> jsonUpdateBreakPro(
			@RequestParam(value="orderId",required=true)Integer orderId
			){
		JsonResult<Order> js = new JsonResult<Order>();
		js.setCode(1);
		js.setMessage("取消工程中断失败！");
		try {
			Order order = new Order();
			order.setId(orderId);
			order.setStatus(4);
			order.setOperMessage("");
			orderService.saveOrder(order);
			
			//取消订单工程中断状态
			orderService.updateProByOrderId(orderId);
			js.setCode(0);
			js.setMessage("工程中断已取消！");
			
			order = orderService.getOrderById(orderId);
			logService.writeLog("取消用户名"+order.getName()+"的工程中断");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js;
	}
	
	/**
	 * 作废工程
	 * @param orderId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/jsonUpdateCancelOrder.do")
	public JsonResult<Order> jsonUpdateCancelOrder(
			@RequestParam(value="orderId",required=true)Integer orderId,
			@RequestParam(value="amount",required=true)Double amount,
			@RequestParam(value="reason",required=false)String reason
			){
		JsonResult<Order> js = new JsonResult<Order>();
		js.setCode(1);
		js.setMessage("项目作废失败！");
		try {
			//更新订单状态
			Order order = new Order();
			order.setId(orderId);
			order.setStatus(10);
			if(reason != null && !reason.trim().equals("")){
				reason = new String(reason.getBytes("iso-8859-1"),"utf-8");
				order.setOperMessage(reason);
			}
			orderService.saveOrder(order);
			
			//更新支付表记录
			Pay pay = new Pay();
			pay.setOrderId(orderId);
			if(amount == null){
				pay.setPayBackPrice(0.00);
			}else{
				pay.setPayBackPrice(amount);
			}
			pay.setPayBackTime(new Date());
			orderService.updatePayByOrderId(pay);
			js.setCode(0);
			js.setMessage("项目作废成功！");
			
			order = orderService.getOrderById(orderId);
			logService.writeLog("作废用户"+order.getName()+"的项目");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js;
	}
	
	/**
	 * 确认订单保存
	 * @param order
	 * @param req
	 * @param resp
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/jsonSaveOrUpdateOrder.do")
	public JsonResult<Order> jsonSaveOrUpdateOrder(
			Order order,
			HttpServletRequest req,HttpServletResponse resp
			){
		JsonResult<Order> js = new JsonResult<Order>();
		js.setCode(1);
		js.setMessage("确认订单失败！");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");		
		try {
			if(order.getId() == null)
				order.setId(0);
			Order old = new Order();
			old= orderService.getOrderById(order.getId());
			if(old != null && old.getStatus() > 1){
				js.setMessage("订单已更新！");
				js.setObj(order);
				return js;
			}
			if(order.getOrderTypeId() == null || order.getOrderTypeId() == 0){
				js.setMessage("请选择订单类型！");
				return js;
			}
			if(order.getAppointTime() == null || order.getAppointTime() == ""){
				js.setMessage("请设置项目交付时间！");
				return js;
			}else{
				order.setAppointment(sdf.parse(order.getAppointTime()));
			}
			if(order.getHouseType() == null || "0".equals(order.getHouseType())){
				js.setMessage("请选择房屋户型！");
				return js;
			}
			//保修时间验证
			if(StringUtil.isEmpty(order.getServiceStarts()) || StringUtil.isEmpty(order.getServiceEnds())){
				js.setMessage("保修时间不能为空！");
				return js;
			}else{
				Date start = DateUtil.sortParse(order.getServiceStarts());
				Date end = DateUtil.sortParse(order.getServiceEnds());
				if(start.after(end) || end.before(new Date())){
					js.setMessage("请输入正确保修时间！");
					return js;
				}
				order.setServiceEnd(end);
				order.setServiceStart(start);
			}
			
			//合同号唯一验证
			Order contractOrder = new Order();
			contractOrder.setContractNumber(order.getContractNumber());
			List<Order> orderList = orderService.getOrderList(contractOrder);
			if(orderList.size() > 0){
				js.setMessage("合同编号已存在！");
				return js;
			}
			
			User operUser = this.getLoginUser();
			if(operUser != null){
				order.setOperId(operUser.getId());
			}
			order.setStatus(2);
			if(order.getCreateDate() == null){
				order.setCreateTime(new Date());
			}else{
				order.setCreateTime(sdf.parse(order.getCreateDate()));
			}
			orderService.saveOrder(order);
			
			int grade = (int) (order.getAmount()/10);
			User user = new User();
			
			//定义用户及推荐用户集合
			List<User> listUser = new ArrayList<User>();
			List<User> refListUser = new ArrayList<User>();
			listUser = orderService.getUserByMobile(order.getMobile());
			
			User refUser = new User();
			if(StringUtil.isMobileNumber(order.getRefMobile())){
				refListUser = orderService.getUserByMobile(order.getRefMobile());
			}
			//无用户,则插入用户
			if(listUser.size() == 0){
				user.setId(0);
				user.setName(order.getName());
				user.setMobile(order.getMobile());
				user.setGrade(0);
				user.setUtype(1);
				user.setFlag(0);
				orderService.saveUser(user);
				//推荐人积分添加
				if(refListUser.size() > 0){
					refUser = refListUser.get(0);
				}else{
					refUser.setId(0);
				}
				//积分规则：如为新用户，成交则给推荐用户加10000积分
				addGrade(grade,user,10000,refUser);
			}else{
				user = listUser.get(0);
				if(refListUser.size() > 0){
					refUser = refListUser.get(0);
				}else{
					refUser.setId(0);
				}
				addGrade(grade,user,0,refUser);
			}
			
			//插入支付记录到支付表
			Pay pay = new Pay();
			pay.setOrderId(order.getId());
			pay.setPayPrice(order.getAmount());
			pay.setPayTime(new Date());
			pay.setPayType(2);
			orderService.savePay(pay);
			js.setCode(0);
			js.setObj(order);
			js.setMessage("确认订单成功！");
			
			logService.writeLog("确认用户"+order.getName()+"的订单");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js;
	}
	
	/**
	 * 保存项目工程
	 * @param pro
	 * @param req
	 * @param resp
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/jsonSaveOrUpdatePro.do")
	public JsonResult<Project> jsonSaveOrUpdatePro(
			Project pro,
			HttpServletRequest req,HttpServletResponse resp
			){
		JsonResult<Project> js = new JsonResult<Project>();
		js.setCode(1);
		if(pro.getId() == null){
			js.setMessage("添加工程失败！");
		}else{
			js.setMessage("修改工程失败！");
		}
		try {
			Order order = orderService.getOrderById(pro.getOrderId());
			if(order.getStatus() > 2){
				js.setMessage("工程划分已确认完毕！");
				js.setObj(pro);
				js.setCode(2);
				return js;
			}
			if(pro.getStartTimes() != null && !pro.getStartTimes().trim().equals("")){
				Date date = new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				
				String dateToString = format.format(date);
				date = format.parse(dateToString);
				
				Date start = format.parse(pro.getStartTimes()); 
				if(start.before(date)){
					js.setMessage("开始时间不能早于当前时间！");
					return js;
				}
			}
			
			if(pro.getGroupId() == null || pro.getGroupId() == 0){
				js.setMessage("请选择施工团队！");
				return js;
			}
			if(pro.getOrderTypeId() == null || pro.getOrderTypeId() == 0){
				js.setMessage("请选择施工品牌！");
				return js;
			}
			if(pro.getId() == null){
				pro.setId(0);
				pro.setStatus(0);
				js.setMessage("添加工程成功！");
			}else{
				js.setMessage("修改工程成功！");
			}
			
			//根据名称查找是否已存在同名工程
			if(pro.getName() != null){
				List<Project> list = orderService.getExistProjectByName(pro);
				if(list.size() > 0){
					js.setMessage("工程名称已存在！");
					return js;
				}
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(pro.getStartTimes() != null){
				pro.setStartTime(sdf.parse(pro.getStartTimes()));
			}
			if(pro.getEndTimes() != null){
				pro.setEndTime(sdf.parse(pro.getEndTimes()));
			}
			orderService.savePro(pro);
			js.setCode(0);
			js.setObj(pro);
			
			if(pro.getId() == 0){
				logService.writeLog("添加工程"+pro.getName()+"成功");
			}else{
				logService.writeLog("修改工程"+pro.getName()+"成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js;
	}
	
	/**
	 * 工程划分完毕确认
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/jsonSavePro.do")
	public JsonResult<Project> jsonSavePro(
			@RequestParam(value="id",required=true)Integer id
			){
		JsonResult<Project> js = new JsonResult<Project>();
		js.setCode(1);
		js.setMessage("工程划分完毕确认失败！");
		try {
			
			Order order= orderService.getOrderById(id);
			if(order.getStatus() > 2){
				js.setCode(1);
				js.setObj(0);
				js.setMessage("订单已更新！");
			}
			order.setStatus(4);
			order.setId(id);
			orderService.saveOrder(order);
			js.setCode(0);
			js.setMessage("工程划分完毕确认成功！");
			//推送短信
			String mobile = order.getMobile();
			String name = order.getName();
			String type = "";
			if(order.getOrderTypeId() == 1){
				type = "地暖";
			}else if(order.getOrderTypeId() == 2){
				type = "中央空调";
			}else if(order.getOrderTypeId() == 3){
				type = "净水系统";
			}
    		String[] message = {name,type};
    		MessageUtil.sendMobileMessage(mobile,"142667", message);
			//order = orderService.getOrderById(id);
			logService.writeLog("确认用户"+order.getName()+"的项目工程划分完毕");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js;
	}
	
	
	/**
	 * 品牌管理列表
	 * @param request
	 * @param response
	 * @param orderType
	 * @return
	 */
	@RequestMapping(value="/orderTypeList.do")
	public String orderTypeList(OrderType orderType,
			HttpServletRequest request, HttpServletResponse response){
		//设置起始页码及页面显示数量
		if(orderType.getPageNo() == null){
			orderType.setPageNo(1);
		}
		orderType.setPageSize(Constants.DEFAULT_PAGE_SIZE);
		
		//取品牌集合和品牌总数
		List<OrderType> itemList = new ArrayList<OrderType>(); 
		int total = 0;
		try {
			itemList = orderService.getItemList(orderType);
			total = orderService.getTotalCount(orderType);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		orderType.setTotalCount(total);
		request.setAttribute("Type",orderType);
		request.setAttribute("ItemList", itemList);
		return "web/order/orderTypeList";	     
	}
	
	/**
	 * 删除品牌
	 * @param typeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/jsonDeleteType.do")
	public JsonResult<OrderType> jsonDelType(
			@RequestParam(value="typeId",required=true)Integer typeId,
			@RequestParam(value="flag",required=true)Integer flag
			){
		JsonResult<OrderType> js = new JsonResult<OrderType>();
		OrderType ot = new OrderType();
		js.setCode(1);
		if(flag == 0){
			js.setMessage("启用品牌失败！");
			ot.setFlag(1);
		}else{
			js.setMessage("停用品牌失败！");
			ot.setFlag(0);
		}
		try {
			//逻辑删除品牌，设置品牌的flag为0
			ot.setId(typeId);
			orderService.saveType(ot);
			js.setCode(0);
			
			if(flag == 0){
				js.setMessage("启用品牌成功！");
			}else{
				js.setMessage("停用品牌成功！");
			}
			
			OrderType ot1 = orderService.getOrderTypeById(typeId);
			if(flag == 0){
				logService.writeLog("启用" + ot1.getName() + "品牌");
			}else{
				logService.writeLog("停用" + ot1.getName() + "品牌");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js;
	}
	
	/**
	 * 根据id删除工程
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/jsonDeletePro.do")
	public JsonResult<Project> jsonDeletePro(
			@RequestParam(value="id",required=true)Integer id
			){
		JsonResult<Project> js = new JsonResult<Project>();
		js.setCode(1);
		js.setMessage("删除工程失败！");
		try {
			orderService.deleteProById(id);
			js.setCode(0);
			js.setMessage("删除工程成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js;
	}
	
	/**
	 * 中断工程
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/jsonUpdatePro.do")
	public JsonResult<Project> jsonUpdatePro(
			@RequestParam(value="proId",required=true)Integer proId,
			@RequestParam(value="orderId",required=true)Integer orderId,
			@RequestParam(value="reason",required=false)String reason
			){
		JsonResult<Project> js = new JsonResult<Project>();
		js.setCode(1);
		js.setMessage("中断工程失败！");
		try {
			Project project = orderService.getProjectById(proId);
			if(project != null && project.getStatus() == 2){
				js.setMessage("工程已完成，不可中断！");
				return js;
			}
			
			//中断工程
			Project pro = new Project();
			pro.setStatus(3);
			pro.setId(proId);
			orderService.updatePro(pro);
			
			//中断项目
			Order order = new Order();
			order.setId(orderId);
			order.setStatus(8);
			if(reason != null && reason.trim() != ""){
				reason = new String(reason.getBytes("iso-8859-1"),"utf-8");
				order.setOperMessage(reason);
			}
			orderService.saveOrder(order);
			js.setCode(0);
			js.setMessage("中断工程成功！");
			
			order = orderService.getOrderById(orderId);
			logService.writeLog("中断客户"+order.getName()+"的项目");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js;
	}
	
	/**
	 * 工程详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/proInfo.do")
	public String jsonLoadPro(
			@RequestParam(value="id",required=true)Integer id,
			@RequestParam(value="orderTypeId",required=true)Integer orderTypeId,
			@RequestParam(value="orderId",required=true)Integer orderId,
			HttpServletRequest req,HttpServletResponse resp
			){
		try {
			Project pro = new Project();
			pro = orderService.getProjectById(id);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(pro.getStartTime() != null){
				pro.setStartTimes(sdf.format(pro.getStartTime()));
			}
			if(pro.getEndTime() != null){
				pro.setEndTimes(sdf.format(pro.getEndTime()));
			}
			if(pro.getBeganTime() != null){
				pro.setBeganTimes(sdf.format(pro.getBeganTime()));
			}
			if(pro.getFinishTime() != null){
				pro.setFinishTimes(sdf.format(pro.getFinishTime()));
			}
			
			//获取所有团队集合
			List<Group>	listGroup = orderService.getAllGroup();
			List<Group> newGroup = new ArrayList<Group>();
			for(Group g:listGroup){
				if(g.getCount() > 0){
					newGroup.add(g);
				}
			}
			req.setAttribute("listGroup",newGroup);
			
			//获取订单
			Order order = orderService.getOrderById(orderId);
			
			if(order != null){
				//获取订单品牌类型所属的所有品牌
				List<OrderType> listOrderType = orderService.getTypeByParentId(order.getOrderTypeId());
				req.setAttribute("listOrderType", listOrderType);
				req.setAttribute("order", order);
			}
			
			//获取工程的所有照片
			List<ProjectImage> listImage = orderService.getImageById(id);
			
			req.setAttribute("listImage",listImage);
			req.setAttribute("project", pro);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "web/order/projectInfo";
	}
	
	/**
	 * 保存照片
	 * @param file1
	 * @param file2
	 * @param file3
	 * @param file4
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/jsonSaveOrUpdatePhoto.do")
	public JsonResult<ProjectImage> jsonSaveOrUpdatePhoto(
			ProjectImage projectImage,
			@RequestParam(value = "file1", required = false) CommonsMultipartFile file1,
			@RequestParam(value = "file2", required = false) CommonsMultipartFile file2,
			@RequestParam(value = "file3", required = false) CommonsMultipartFile file3,
			@RequestParam(value = "file4", required = false) CommonsMultipartFile file4,
			HttpServletRequest req,HttpServletResponse resp
			){
		JsonResult<ProjectImage> js = new JsonResult<ProjectImage>();
		js.setMessage("保存照片失败！");
		js.setCode(1);
		if(projectImage.getOrderImageId() != null){
			Order order = orderService.getOrderById(projectImage.getOrderImageId());
			if(order.getStatus() == 6){
				js.setMessage("项目已完工，不能再上传照片！");
				return js;
			}
		}
		if(file1.getSize() == 0 && file2.getSize() == 0 && file3.getSize() == 0 && file4.getSize() == 0){
			js.setMessage("请选择照片!");
			return js;
		}
		User loginUser = this.getLoginUser();
		projectImage.setOperId(loginUser.getId());
		int sum = 0;
		try{
		    //照片保存
			if(file1.getSize() > 0){
				projectImage.setId(0);
				savePhoto(file1,projectImage,req);	
				sum++;
			}
			if(file2.getSize() > 0){
				projectImage.setId(0);
				savePhoto(file2,projectImage,req);	
				sum++;
			}
			if(file3.getSize() > 0){
				projectImage.setId(0);	
				savePhoto(file3,projectImage,req);	
				sum++;
			}
			if(file4.getSize() > 0){
				projectImage.setId(0);	
				savePhoto(file4,projectImage,req);	
				sum++;
			}
			js.setMessage("照片保存成功！");
			js.setCode(0);
			
			logService.writeLog("上传工程照片" + sum + "张");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js;
	}
	
	
	/**
	 * 保存照片
	 * @param file
	 * @param photo
	 * @param req
	 */
	private void savePhoto(CommonsMultipartFile file,ProjectImage photo,HttpServletRequest req){	
		try {
			orderService.insertPhoto(photo);			
			String path = req.getSession().getServletContext().getRealPath("uploadsource");
			File targetFile = new File(path);
			String filePath = "";
			String fileName = "";
			if(file.getSize()>0){ 
				String tempName = file.getOriginalFilename();  
				String[] fileTypes = tempName.split("\\.");
				int size = fileTypes.length;
				String fileType = fileTypes[size-1];
				fileName = photo.getId()+"."+fileType;
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				targetFile = new File(path+"/project");
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				targetFile = new File(path+"/project",fileName);  
				if(targetFile.exists()){
					targetFile.delete();
				}
				filePath ="uploadsource/project/"+fileName;
				file.transferTo(targetFile);
				photo.setImagePath(filePath);
				orderService.insertPhoto(photo);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * 删除指定照片
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/jsonDeletePhotoById.do", method = RequestMethod.POST, produces = { "text/html;charset=UTF-8" })
	public JsonResult<ProjectImage> deletePhotoByid(
			@RequestParam(value = "id", required = false) Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		JsonResult<ProjectImage> js = new JsonResult<ProjectImage>();
		js.setCode(1);
		js.setMessage("删除工程照片失败!");
		ProjectImage photo = new ProjectImage();	
		try{
			User user = this.getLoginUser();
			if(user == null){
				js.setMessage("未登录！");
				return js;
			}
			if(id > 0){
				photo = orderService.getProImageById(id);	
				if(photo != null){
					String path = request.getSession().getServletContext().getRealPath("uploadsource");
					path = path.replace("uploadsource", photo.getImagePath());
					boolean result = FileUtil.deleteFile(path);	
					if(result){
						orderService.deleteProImageById(id);						
						js.setCode(0);
						js.setMessage("照片删除成功！");
						
						Project pro = orderService.getProjectById(photo.getProjectId());
						logService.writeLog("删除工程"+pro.getName()+"一张照片");
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return js;
	} 
	
	/**
	 * 加载品牌列表
	 * @param pid
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/jsonLoadItemTreeList.do")
	public List<OrderType> getItemList(
			@RequestParam(value = "pid", required = false) Integer pid,
			HttpServletRequest request, HttpServletResponse response) {
		OrderType item = new OrderType();
		//父节点id
		if (pid != null){
			item.setParentId(pid);
		}else {
			item.setParentId(new Integer(0));
		}
		List<OrderType> list = new ArrayList<OrderType>();
		try {
			//获取品牌集合
			list = orderService.getItemList(item);
			
			//遍历，设置节点名称及根据是否有子节点设置是否有展开按钮
			for(OrderType i:list){
				i.setText(i.getName());
				if(i.getChildrenCount() > 0){ 
					i.setState("closed"); 
				}else{
					i.setChildren(new ArrayList<OrderType>());
					i.setState("open");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	/**
	 * 品牌节点加载
	 * @param pid
	 * @param pageNumber
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/jsonLoadItemListById.do", method = RequestMethod.POST, produces = { "text/html;charset=UTF-8" })
	public JsonResult<OrderType> jsonLoadItemListById(
			@RequestParam(value = "pid", required = false) Integer pid,
			@RequestParam(value = "pageNumber", required = false) String pageNumber,
			HttpServletRequest request, HttpServletResponse response){
		JsonResult<OrderType> js = new JsonResult<OrderType>();
		js.setCode(1);
		js.setMessage("加载节点失败!");
		try {
			OrderType item = new OrderType();
			List<OrderType> list = new ArrayList<OrderType>();
			int total = 0;
			if(pid != null){
				item.setParentId(pid);
			}
			if (pageNumber != null && !pageNumber.endsWith("undefined") && pageNumber != ""){
				item.setPageNo(Integer.valueOf(pageNumber));
			}else{
				item.setPageNo(1);
			}
			item.setPageSize(Constants.DEFAULT_PAGE_SIZE);
			list = orderService.getItemList(item);
			total = orderService.getTotalCount(item);
			item.setTotalCount(total);
			js.setCode(0);
			js.setList(list);
			js.setObj(item);
			js.setMessage("加载列表成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js;
	}
	
	/**
	 * 品牌节点加载
	 * @param pid
	 * @param pageNumber
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/jsonLoadItemListByParentId.do", method = RequestMethod.POST, produces = { "text/html;charset=UTF-8" })
	public JsonResult<OrderType> jsonLoadItemListByParentId(
			@RequestParam(value = "pid", required = false) Integer pid,
			@RequestParam(value = "pageNumber", required = false) String pageNumber,
			HttpServletRequest request, HttpServletResponse response){
		JsonResult<OrderType> js = new JsonResult<OrderType>();
		js.setCode(1);
		js.setMessage("加载节点失败!");
		try {
			OrderType item = new OrderType();
			List<OrderType> list = new ArrayList<OrderType>();
			int total = 0;
			if(pid != null){
				item.setParentId(pid);
			}
			if (pageNumber != null && !pageNumber.endsWith("undefined") && pageNumber != ""){
				item.setPageNo(Integer.valueOf(pageNumber));
			}else{
				item.setPageNo(1);
			}
			item.setPageSize(Constants.DEFAULT_PAGE_SIZE);
			list = orderService.getItemListByParentId(item);
			total = orderService.getTotalCountByParentId(item);
			item.setTotalCount(total);
			js.setCode(0);
			js.setList(list);
			js.setObj(item);
			js.setMessage("加载列表成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js;
	}
	
	/**
	 * 品牌详情界面
	 * @param typeId
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping("/typeInfo.do")
	public String typeInfo(
			@RequestParam(value="typeId",required=true)Integer typeId,
			HttpServletRequest req,HttpServletResponse resp
			){
		OrderType orderType = new OrderType();
		//根据id获取品牌
		if(typeId != 0){
			orderType = orderService.getOrderTypeById(typeId);
		}
		req.setAttribute("orderType",orderType);
		req.setAttribute("typeId",typeId);
		return "web/order/orderTypeInfo";
	}
	
	/**
	 * 保存品牌
	 * @param OrderType
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/jsonSaveOrUpdateType.do", method = RequestMethod.POST)
	public JsonResult <OrderType> SaveOrUpdateItem(
			OrderType OrderType,
			@RequestParam(value = "file", required = false) CommonsMultipartFile file,
			HttpServletRequest request, HttpServletResponse response){
		JsonResult<OrderType> js = new JsonResult<OrderType>();
		js.setCode(1);
		js.setMessage("保存品牌失败！");
		
		if(OrderType.getParentId() == null || OrderType.getParentId() == 4){
			js.setMessage("请选择品牌类型！");
			return js;
		}
		try {
			//判断新增还是编辑品牌
			if(OrderType.getId() == null)
				OrderType.setId(0);
			//未删除
			OrderType.setFlag(1);
			
			//取品牌名称相同的品牌数量
			int typeCount = orderService.getExistType(OrderType);
			if(typeCount > 0){
				js.setMessage("品牌名称已存在，请确认！");
				return js;
			}
			//保存品牌信息
			orderService.saveType(OrderType);
			
			//品牌图片上传	
			String path = request.getSession().getServletContext().getRealPath("uploadsource");
			File targetFile = new File(path);
			String filePath = "";
			String fileName = "";
			if(file != null && file.getSize()>0){ 
				String tempName = file.getOriginalFilename();  
				String fileType = tempName.split("\\.")[1];
				fileName = OrderType.getId()+"."+fileType;
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				targetFile = new File(path+"/ordertype");
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				targetFile = new File(path+"/ordertype",fileName);  
				if(targetFile.exists()){
					targetFile.delete();
				}
				filePath ="uploadsource/ordertype/"+fileName;
				file.transferTo(targetFile);
				OrderType.setImagePath(filePath);
			}
			orderService.saveType(OrderType);
			
			js.setCode(0);
			js.setObj(OrderType);
			js.setMessage("保存品牌成功！");
			
			
			logService.writeLog("保存品牌"+OrderType.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js;
	}
	
	/**
	 * 照片列表
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping("/photoList.do")
	public String getContractList(
			ProjectImage pji,
			HttpServletRequest req,HttpServletResponse resp
			){
		//已公开的照片
		List<ProjectImage> openList = new ArrayList<ProjectImage>();
		//未公开的照片集合(客户允许)
		List<ProjectImage> unOpenList = new ArrayList<ProjectImage>();
		//未公开的照片数量
		int count = 0;
		
		try {
			ProjectImage open = new ProjectImage();
			open.setPermission(2);
			openList = orderService.getImageList(open);
			
			if(pji.getPageNo() == null){
				pji.setPageNo(1);
			}
			pji.setPageSize(Constants.DEFAULT_PAGE_SIZE);
			pji.setPermission(1);
			unOpenList = orderService.getImageList(pji);
			count = orderService.getImageCount(pji);
			pji.setTotalCount(count);
			req.setAttribute("openList", openList);
			req.setAttribute("ProjectImage", pji);
			req.setAttribute("unOpenList", unOpenList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "web/order/photoList";
	}
	
	/**
	 * 更新图片是否公开
	 * @param status
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/jsonUpdateOpenStatus.do")
	public JsonResult<ProjectImage> jsonUpdateOpenStatus(
			@RequestParam(value="status",required=true)Integer status,
			@RequestParam(value="imgId",required=true)Integer id
			){
		JsonResult<ProjectImage> js = new JsonResult<ProjectImage>();
		js.setCode(1);
		try {
			ProjectImage pi = new ProjectImage();
			if(status == 0){
				pi.setPermission(1);
			}else if(status == 1){
				int count = orderService.getPermissionPhotoCount();
				if(count >= 12){
					js.setMessage("公开图片数量12张已达上限！");
				}else{
					pi.setPermission(2);
				}
			}else{
				pi.setPermission(0);
			}
			pi.setId(id);
			orderService.updateProjectImage(pi);
			js.setCode(0);
			
			if(status == 0){
				logService.writeLog("取消公开一张图片");
			}else{
				logService.writeLog("公开一张图片");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js;
	}
	
	/**
	 * 检查是否有新的订单
	 * @param req
	 * @param resp
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkOrder.do")
	public JsonResult<Order> checkOrder(
			HttpServletRequest req,HttpServletResponse resp
			){
		JsonResult<Order> js = new JsonResult<Order>();
		js.setCode(1);
		try {
			int count = orderService.getUnsureOrderCount(0);
			if(count > 0){
				js.setCode(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js;
	}

	/**
	 * 取消订单
	 * @param id
	 * @param flag
	 * @param req
	 * @param resp
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/jsonSaveFalseOrder.do")
	public JsonResult<Order> falseOrder(
			@RequestParam(value="id",required=true)Integer id,
			@RequestParam(value="flag",required=true)Integer flag,
			HttpServletRequest req,HttpServletResponse resp
			){
		JsonResult<Order> js = new JsonResult<Order>();
		js.setCode(1);
		js.setMessage("取消订单失败！");
		Order order = new Order();
		try {
	        order = orderService.getOrderById(id);
	        if(flag == 1){
	        	User user = new User();
	        	User refUser = new User();
				List<User> listUser = new ArrayList<User>();
				List<User> refListUser = new ArrayList<User>();
				listUser = orderService.getUserByMobile(order.getMobile());
				//无用户,则插入用户
				if(listUser.size() == 0){
					user.setId(0);
					user.setName(order.getName());
					user.setMobile(order.getMobile());
					user.setGrade(0);
					user.setUtype(1);
					user.setFlag(0);
					orderService.saveUser(user);
					if(StringUtil.isMobileNumber(order.getRefMobile())){
						refListUser = orderService.getUserByMobile(order.getRefMobile());
					}
					//推荐人积分添加
					if(refListUser.size() > 0){
						refUser = refListUser.get(0);
					}else{
						refUser.setId(0);
					}
					//积分规则：推荐一个有效新客户增加推荐人500积分
					addGrade(0,user,500,refUser);
				}
				order.setOperMessage("订单信息有效");
	        }else{
	        	order.setOperMessage("订单信息无效");
	        }
	        order.setOperId(this.getLoginUser().getId());
	        order.setStatus(10);
	        orderService.saveOrder(order);
	        logService.writeLog("取消用户"+order.getName()+"的未确认订单");
	        
	        js.setCode(0);
	        js.setMessage("取消订单成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js;
		
	}
	
	
    /**
     * 添加积分
     * @param grades 用户添加积分
     * @param userId 用户id
     * @param refGrades 推荐人添加积分
     * @param refUserId 推荐人id
     * @param mobile 被推荐人手机号
     * @return temp 推荐人上限-3，运行错误-2，逻辑错误-1，用户积分成功0，推荐客户有效1，用户首次消费成功2
     */
	private int addGrade(int grades,User user,int refGrades,User refUser){
		int temp = -1;
		try{
			String description = "订单合同签订";
			//签订合同添加用户积分
			if(grades > 0 && user.getId() > 0){
				//生日当月享双倍积分
				if(DateUtil.isBirthMonth(user.getBirth())){
					grades *= 2;
					description = "订单合同签订(生日当月双倍积分)";
				}
				//消费积分添加
				user.setGrade(user.getGrade()+grades);
				//userService.saveOrUpdateUser(user);
				orderService.saveUser(user);
				//记录积分变化
				gradeService.writeGrade(grades,user.getId(),this.getLoginUser().getId(),description);
				temp = 0;
			}	
			//推荐人积分添加
			if(refUser.getId() > 0 && refGrades > 0){
				//推荐人积分添加重复验证
				Grade grade = new Grade();
				grade.setId(0);
				/*
				 * 无必要作此判断，只有为新用户才会添加推荐人积分，故积分记录不可能重复
				//查询是否已添加
				grade = gradeService.getExsitGrade(refGrades,refUser.getId(),user.getMobile());
				//已经增加过积分了
				if(grade.getId() > 0)
					return temp;
				*/
				//推荐人当日次数
				if(temp < 0){
					List<Grade> list = new ArrayList<Grade>();
					//推荐人当日已添加积分次数
					list = gradeService.getExsitGrade(refGrades,refUser.getId());
					if(list.size() >= 5)
						return -3;
				}
				description = "被推荐人"+user.getMobile()+"真实有效。";
				if(temp == 0)
					description = "被推荐人"+user.getMobile()+"订单合同签订。";
				//推荐人积分添加
				refUser.setGrade(refUser.getGrade()+refGrades);
				orderService.saveUser(refUser);
				//记录积分变化
				gradeService.writeGrade(refGrades,refUser.getId(),this.getLoginUser().getId(),description,user.getMobile());
				temp += 2;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			temp = -2;
		}
		return temp;
	}
}

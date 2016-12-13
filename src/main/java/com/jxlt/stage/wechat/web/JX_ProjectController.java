package com.jxlt.stage.wechat.web;

import java.io.BufferedReader;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.jxlt.stage.common.JsonResult;
import com.jxlt.stage.common.utils.FileUtil;
import com.jxlt.stage.model.Grade;
import com.jxlt.stage.model.Order;
import com.jxlt.stage.model.OrderType;
import com.jxlt.stage.model.Project;
import com.jxlt.stage.model.ProjectImage;
import com.jxlt.stage.model.User;
import com.jxlt.stage.wechat.service.JX_ProjectService;

@Controller
@RequestMapping(value="Project")
public class JX_ProjectController {

	@Resource
	private JX_ProjectService jxProjectService;
	
	// 我爱我家
	@RequestMapping(value="jxWelove.do")
	public String JX_Welove(HttpServletRequest request , Model model){
		// 从session缓存里面去登录的用户
		User user = (User)request.getSession().getAttribute("loginUser");
		if(user!= null){
			request.getSession().setAttribute("userName", user.getName());
			request.getSession().setAttribute("ID", user.getId());			
			request.getSession().setAttribute("User", user);
			// 通过项目经理id去得到分配给他的工程
			List<String> projects = new ArrayList<String>();
			//项目经理或者员工
			if(user.getUtype() == 11 || user.getUtype() == 12){
				projects = jxProjectService.getProjectByUserId(user.getId(),user.getUtype());
				request.setAttribute("projects", projects);
				return "wechat/project/JX_welove";
			}else if(user.getUtype() == 1){
				projects = jxProjectService.getProjectByUserId(user.getId(),user.getUtype());
				request.setAttribute("projects", projects);
				return "wechat/project/JX_welove_customer";
			}else{
				return "wechat/person/JX_login_register_forget";
			}
			
		}else{
			request.getSession().setAttribute("userName", "未登录");
			request.getSession().setAttribute("ID", 0);
			return "wechat/project/JX_welove";
		}
	}
	
	/**
	 * 根据合同号加载工程列表
	 * @param req
	 * @param resp
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/jsonLoadProList.do")
	public JsonResult<Project> jsonLoadProList(
			HttpServletRequest req,HttpServletResponse resp
			){
		JsonResult<Project> js = new JsonResult<Project>();
		js.setCode(1);
		List<Project> listPro = new ArrayList<Project>();
		try {
			StringBuilder buffer = new StringBuilder();
	        BufferedReader reader = req.getReader();
	        String line;
	        while ((line = reader.readLine()) != null) {
	            buffer.append(line);
	        }
	        String data = buffer.toString();
	        System.out.println(data);
			String contractNum = new String(data.getBytes("iso-8859-1"),"utf-8");
			System.out.println(contractNum);
			User user = (User)req.getSession().getAttribute("loginUser"); 
			user = jxProjectService.getUserById(user.getId());
			if(user != null){
				System.out.println(user.getUtype());
				System.out.println(user.getId());
				listPro = jxProjectService.getProjectListByConNum(contractNum,user.getUtype(),user.getId());
			}
			
			if(user.getUtype() == 1){
				Order order = jxProjectService.getOrderByConNum(contractNum);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if(order != null){
					if(order.getServiceEnd() != null){
						order.setServiceEnds(sdf.format(order.getServiceEnd()));
					}
					if(order.getServiceStart() != null){
						order.setServiceStarts(sdf.format(order.getServiceStart()));
					}
				}
				js.setObj(order);
			}
			
			if(listPro.size() > 0){
				js.setCode(0);
				js.setList(listPro);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js;
	}
	
	/**
	 * 加载工程
	 * @param req
	 * @param resp
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/jsonLoadPro.do")
	public JsonResult<ProjectImage> jsonLoadPro(
			@RequestParam(value="proId",required=true)Integer proId,
			HttpServletRequest req,HttpServletResponse resp
			){
		JsonResult<ProjectImage> js = new JsonResult<ProjectImage>();
		js.setCode(1);
		js.setMessage("加载工程出错！");
		try {
			Project pro = jxProjectService.getProjectById(proId);
			if(pro != null){
				Order order = jxProjectService.getOrderById(pro.getOrderId());
				if(order.getStatus() == 2){
					js.setMessage("项目正在规划中，请等待！");
					return js;
				}else{
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					//后台规划开始结束时间
					pro.setStartTimes(sdf.format(pro.getStartTime()));
					pro.setEndTimes(sdf.format(pro.getEndTime()));
					
					//前台施工开始结束时间
					if(pro.getBeganTime() != null){
						pro.setBeganTimes(sdf.format(pro.getBeganTime()));
					}
					if(pro.getFinishTime() != null){
						pro.setFinishTimes(sdf.format(pro.getFinishTime()));
					}
					
					OrderType ot = jxProjectService.getOrderTypeById(pro.getOrderTypeId());
					pro.setTypeName(ot.getName());
					
					String leaderPhone = jxProjectService.getGroupLeaderPhone(pro.getGroupId());
					pro.setLeaderPhone(leaderPhone);
					
					if(pro.getStatus() > 0){
						List<ProjectImage> imgList = jxProjectService.getImgByProId(pro.getId());
						js.setList(imgList);
					}
				}
				js.setCode(0);
				js.setObj(pro);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js;
	}
	
	/**
	 * 施工保存
	 * @param proId
	 * @param status
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/jsonSavePro.do")
	public JsonResult<Project> jsonSavePro(
			@RequestParam(value="proId",required=true)Integer proId,
			@RequestParam(value="status",required=true)Integer status
			){
		JsonResult<Project> js = new JsonResult<Project>();
		Project project = new Project();
		int preStatus = 0;
		if(status == 0){
			js.setMessage("开始施工出错，稍后再试！");
			project.setStatus(1);
			project.setBeganTime(new Date());
		}else{
			js.setMessage("完成施工出错，稍后再试！");
			project.setFinishTime(new Date());
			project.setStatus(2);
			preStatus = 1;
		}
		try {
			Project prePro = jxProjectService.getProjectById(proId);
			if(prePro.getStatus() != preStatus){
				js.setMessage("工程状态已被他人更新！");
				return js;
			}
			
			//保存工程
			project.setId(proId);
			jxProjectService.savePro(project);
			
			if(status == 0){
				js.setMessage("开始施工！");
			}else{
				js.setMessage("完成施工！");
				project = jxProjectService.getProjectById(proId);
				List<Project> listPro = new ArrayList<Project>();
				listPro = jxProjectService.getRemainProListById(project.getOrderId());
				//如果工程都完工，则更新项目状态为完工
				if(listPro.size() == 0){
					Order order = new Order();
					order.setId(project.getOrderId());
					order.setStatus(6);
					order.setFinishTime(new Date());
					jxProjectService.saveOrder(order);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js;
	}
	
	/**
	 * 上传照片
	 * @param file
	 * @param id
	 * @param request
	 * @param resp
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/jsonLoadFile.do")
	public JsonResult<Project> jsonLoadFile(
			@RequestParam(value = "file", required = false)CommonsMultipartFile file,
			@RequestParam(value = "id", required = true)Integer id,
			HttpServletRequest request,HttpServletResponse resp
			){
		JsonResult<Project> js = new JsonResult<Project>();
		js.setCode(1);
		js.setMessage("照片保存失败！");
		try {
			if(file.getSize() == 0){
				js.setMessage("请选择照片！");
				return js;
			}
			User user = (User)request.getSession().getAttribute("loginUser");
			ProjectImage projectImage = new ProjectImage();
			projectImage.setOperId(user.getId());
			projectImage.setProjectId(id);
		    //照片保存
			if(file.getSize() > 0){
				projectImage.setId(0);
				jxProjectService.insertPhoto(projectImage);			
				String path = request.getSession().getServletContext().getRealPath("uploadsource");
				File targetFile = new File(path);
				String filePath = "";
				String fileName = "";
				String tempName = file.getOriginalFilename();  
				String[] fileTypes = tempName.split("\\.");
				int size = fileTypes.length;
				String fileType = fileTypes[size-1];
				fileName = projectImage.getId()+"."+fileType;
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
				projectImage.setImagePath(filePath);
				jxProjectService.insertPhoto(projectImage);
			}
			js.setObj(projectImage);
			js.setCode(0);
		}catch(Exception e2){
			e2.printStackTrace();
		}
	    return js;
	}
	
	/**
	 * 删除工程照片
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
			if(id > 0){
				photo = jxProjectService.getImgByPhotoId(id);	
				if(photo != null){
					String path = request.getSession().getServletContext().getRealPath("uploadsource");
					path = path.replace("uploadsource", photo.getImagePath());
					boolean result = FileUtil.deleteFile(path);	
					if(result){
						jxProjectService.deleteProImageById(id);						
						js.setCode(0);
						js.setMessage("照片删除成功！");
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return js;
	}
	
	/**
	 * 加载工程图片
	 * @param id
	 * @param req
	 * @param resp
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/jsonLoadProImg.do")
	public JsonResult<ProjectImage> jsonLoadProImg(
			@RequestParam(value="id",required=true)Integer id,
			HttpServletRequest req,HttpServletResponse resp
			){
		JsonResult<ProjectImage> js = new JsonResult<ProjectImage>();
		js.setCode(1);
		try {
			ProjectImage pi = jxProjectService.getImgByPhotoId(id);
			if(pi != null){
				js.setObj(pi);
				js.setCode(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js;
	}
	
	/**
	 * 保存工程图片评价
	 * @param id
	 * @param message
	 * @param open
	 * @param req
	 * @param resp
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/jsonSaveProImg.do")
	public JsonResult<ProjectImage> jsonSaveProImg(
			@RequestParam(value="id",required=true)Integer id,
			@RequestParam(value="message",required=true)String message,
			@RequestParam(value="open",required=true)Integer open,
			HttpServletRequest req,HttpServletResponse resp
			){
		 JsonResult<ProjectImage> js = new  JsonResult<ProjectImage>();
		 js.setMessage("评价图片失败！");
		 try {
			String mes = new String(message.getBytes("iso-8859-1"),"utf-8");
			ProjectImage pro = new ProjectImage();
			pro.setId(id);
			pro.setMessage(mes);
			pro.setPermission(open);
			//积分规则，公开工程图片则加用户100积分,并将积分详情插入积分表
			if(open == 1){
				User user = (User)req.getSession().getAttribute("loginUser");
				user.setGrade(user.getGrade()+100);
				jxProjectService.saveUser(user);
				Grade grade = new Grade();
				grade.setUserId(user.getId());
				grade.setCreateTime(new Date());
				grade.setGrade(100);
				grade.setDescription("公开工程图片");
				jxProjectService.saveGrade(grade);
			}
			jxProjectService.saveProImg(pro);
			js.setMessage("评价图片成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js;
	}
	
	/**
	 * 保存工程图片
	 * @param id
	 * @param grade
	 * @param message
	 * @param permission
	 * @param req
	 * @param resp
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/jsonSaveProMsg.do")
	public JsonResult<Project> jsonSaveProMsg(
			@RequestParam(value="id",required=true)Integer id,
			@RequestParam(value="grade",required=true)Integer grade,
			@RequestParam(value="message",required=true)String message,
			@RequestParam(value="permission",required=true)Integer permission,
			HttpServletRequest req,HttpServletResponse resp
			){
		JsonResult<Project> js = new  JsonResult<Project>();
		js.setMessage("评价工程失败！");
		try {
			String mes = new String(message.getBytes("iso-8859-1"),"utf-8");
			Project pro = new Project();
			pro.setId(id);
			pro.setAssessValue(grade);
			pro.setAssessMessage(mes);
			pro.setPermission(permission);
			User user = (User)req.getSession().getAttribute("loginUser");
			pro.setAssessUserId(user.getId());
			//积分规则，公开工程则加用户500积分,并将积分详情插入积分表
			if(permission == 1){
				user.setGrade(user.getGrade()+500);
				jxProjectService.saveUser(user);
				
				Grade point = new Grade();
				point.setCreateTime(new Date());
				point.setDescription("公开工程");
				point.setGrade(500);
				point.setUserId(user.getId());
				jxProjectService.saveGrade(point);
				
				//更新工程图片状态为公开
				jxProjectService.updateProjectImgByProId(id);
			}
			jxProjectService.savePro(pro);
			js.setObj(pro);
			js.setMessage("评价工程成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js;
	}
	
	// 客户之家
	@RequestMapping(value="jxCustomerPhoto.do")
	public String JX_customerPhoto(HttpServletRequest request){
		
		List<ProjectImage> imageList = jxProjectService.getOrderImageList();
		request.setAttribute("imageList", imageList);
		return "wechat/project/JX_photoList";
		
	}
}

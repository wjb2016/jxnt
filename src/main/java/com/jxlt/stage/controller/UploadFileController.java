package com.jxlt.stage.controller;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Scope("prototype")
@Controller
@RequestMapping("/fileUpload")
public class UploadFileController extends BaseController {
	
//
//	@ResponseBody
//	@RequestMapping(value = "/downfile.do")
//	public void downFile(HttpServletRequest request,
//			HttpServletResponse response,
//			@RequestParam(value = "filepath", required = true) String filepath) {
//		try {
////			String moduleName = new String(filepath.getBytes("iso8859-1"),
////					"utf-8");
////			String filePath = request.getRealPath("/") + moduleName;
//			@SuppressWarnings("deprecation")
//			String filePath = request.getRealPath("/") + filepath;
//			String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
//			fileName = new String(fileName.getBytes("ISO-8859-1"), "utf-8");
//			/*response.reset();
//			response.setContentType("APPLICATION/OCTET-STREAM; charset=UTF-8");
//			response.setHeader("Content-disposition", "attachment;filename="
//					+ fileName + ".xls");*/
//			response.reset();
//			final String userAgent = request.getHeader("USER-AGENT");
//			if(StringUtils.contains(userAgent, "MSIE")){//IE浏览�? 
//				fileName = URLEncoder.encode(fileName,"UTF8");  
//			}else if(StringUtils.contains(userAgent, "Mozilla")){//google,火狐浏览�? 
//				fileName = new String(fileName.getBytes(), "ISO8859-1");  
//			}else{  
//				fileName = URLEncoder.encode(fileName,"UTF8");//其他浏览�? 
//			}  
//			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");//这里设置�?��让浏览器弹出下载提示框，而不是直接在浏览器中打开  
//			response.setContentType("application/vnd.ms-excel"); 
//			filePath = new String(filePath.getBytes("ISO-8859-1"), "utf-8");
//			FileInputStream inStream = new FileInputStream(filePath);
//			byte[] b = new byte[100];
//			int len;
//			while ((len = inStream.read(b)) > 0) {
//				response.getOutputStream().write(b, 0, len);
//				// this.getRes().getOutputStream().write(b,0,len);
//			}
//			inStream.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	/**
//	 * 导入社会机构
//	 * 
//	 * @param request
//	 * @param response
//	 * @param file
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value = "/jsonLoadAssociateExcel.do", method = RequestMethod.POST, produces = { "text/html;charset=UTF-8" })
//	public JsonResult<Associate> jsonLoadAssociateExcel(HttpServletRequest request,
//			HttpServletResponse response,
//			@RequestParam(value = "file", required = false) MultipartFile file) {
//		JsonResult<Associate> js = new JsonResult<Associate>();
//		js.setCode(1);
//		js.setMessage("导入数据失败�?);		
//		List<Associate> associatelist = new ArrayList<Associate>();
//		List<Associate> list = new ArrayList<Associate>();
//		List<Associate> nulllist = new ArrayList<Associate>();
//		int totalCount = 0;
//		int rightCount = 0;
//		int faultCount = 0;
//		try {
//			// 项目在容器中的实际发布运行的upload路径
//			String path = request.getSession().getServletContext()
//					.getRealPath("upload");
//			// 获取文件�?
//			String fileName = file.getOriginalFilename();
//			File targetFile = new File(path, fileName);
//			if (!targetFile.exists()) {
//				targetFile.mkdirs();
//			}
//			// 保存
//			try {
//				File f = new File(targetFile.getPath());
//				if (f.exists()) {
//					f.delete();
//				}
//				// 把MultipartFile转换成File类型,MultipartFile自带的transferTo
//				file.transferTo(targetFile);
//				InputStream stream = new FileInputStream(targetFile.getPath());
//				Workbook wb = WorkbookFactory.create(stream);
//				// HSSFWorkbook是解析出来excel 2007 以前版本的，后缀名为xls�?
//				// XSSFWorkbook是解析excel 2007 版的，后�?��为xlsx�?
//				try {
//					stream = new FileInputStream(targetFile.getPath());
//					wb = new XSSFWorkbook(stream);
//					associatelist = getAssociateXSSFResult(wb);
//				} catch (Exception ex) {
//					stream = new FileInputStream(targetFile.getPath());
//					wb = new HSSFWorkbook(stream);
//					associatelist = getAssociateHSSFResult(wb);
//				}
//				for (Associate associate : associatelist) {
//					// 把刚获取的列存入list,判断获取的对象是否按照规�?
//					if (associate.getName() == null
//						|| "".equals(associate.getName())
//						|| associate.getTypeid() == null
//						|| "".equals(associate.getTypeid())
//						|| associate.getAddress() == null
//						|| "".equals(associate.getAddress())
//						|| associate.getTelephone() == null
//						|| "".equals(associate.getTelephone())
//					){
//						nulllist.add(associate);
//					} else {
//						list.add(associate);
//					}
//				}
//				// stream.close();
//				IOUtils.closeQuietly(stream);				
//				totalCount = associatelist.size();
//				rightCount = list.size();
//				faultCount = nulllist.size();
//				String message = "导入成功，共�?+totalCount+"�?其中成功"+rightCount+"�?失败"+faultCount+"条�?";
//				if (rightCount > 0) {
//					associateService.importAssociateList(list);
//					js.setCode(0);
//					js.setMessage(message);
//				}				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return js;
//	}
//
//	// 解析excel文件
//		private List<Associate> getAssociateXSSFResult(Workbook wb) {
//			// TODO Auto-generated method stub
//			List<Associate> result = new ArrayList<Associate>();
//			List<AssociateType> associateTypeList = new ArrayList<AssociateType>();
//			associateTypeList = associateService.getAssociateTypeAllList();
//			List<Area> areaList = areaService.getAreaAlList();
//			for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
//				XSSFSheet st = (XSSFSheet) wb.getSheetAt(sheetIndex);
//				// 第一行为标题，不�?
//				for (int rowIndex = 1; rowIndex <= st.getLastRowNum(); rowIndex++) {
//					XSSFRow row = st.getRow(rowIndex);
//					if (row == null) {
//						continue;
//					}
//					Associate associate = new Associate();
//					User u = this.getLoginUser();
//					associate.setCreator(u.getId());
//					associate.setCreatorname(u.getName());
//					associate.setOrganname(u.getOrganName()); 
//					associate.setGuid(u.getGuid());
//					XSSFCell cell0 = row.getCell(0);
//					if (cell0 != null || "".equals(cell0)) {
//						cell0.setCellType(Cell.CELL_TYPE_STRING);
//						associate.setName(cell0.getStringCellValue());
//					}
//					XSSFCell cell1 = row.getCell(1);
//					if (cell1 != null || "".equals(cell1)) {
//						cell1.setCellType(Cell.CELL_TYPE_STRING);
//						//associate.setTypeid(2);
//						for(AssociateType a : associateTypeList){
//							String name = a.getName();
//							if(name.equals(cell1.getStringCellValue())){
//								associate.setTypeid(a.getId());
//								break;
//							}
//						}
//						if(associate.getTypeid()==null){
//							associate.setTypeid(associateTypeList.get(0).getId());
//						}
//						String serialNo = getAssoSerialNo(associate.getTypeid()); 
//						associate.setSerialno(serialNo);
//					}
//					XSSFCell cell2 = row.getCell(2);
//					if (cell2 != null || "".equals(cell2)) {
//						cell2.setCellType(Cell.CELL_TYPE_STRING);
//						associate.setAddress(cell2.getStringCellValue());
//					}
//					XSSFCell cell3 = row.getCell(3);
//					if (cell3 != null || "".equals(cell3)) {
//						cell3.setCellType(Cell.CELL_TYPE_STRING);
//						associate.setTelephone(cell3.getStringCellValue());
//					}
//					XSSFCell cell4 = row.getCell(4);
//					if (cell4 != null || "".equals(cell4)) {
//						cell4.setCellType(Cell.CELL_TYPE_STRING);
//						associate.setAreaId(0);
//						for(Area a : areaList){
//							String name = a.getName();
//							if(name.equals(cell4.getStringCellValue())){
//								associate.setAreaId(a.getId());
//								break;
//							}
//						}
//						if(associate.getAreaId() == 0){
//							associate.setAreaId(areaList.get(0).getId());
//						}
//					}
//					XSSFCell cell5 = row.getCell(5);
//					if (cell5 != null || "".equals(cell5)) {
//						cell5.setCellType(Cell.CELL_TYPE_STRING);
//						associate.setLatitude(cell5.getStringCellValue());
//					}
//					XSSFCell cell6 = row.getCell(6);
//					if (cell6 != null || "".equals(cell6)) {
//						cell6.setCellType(Cell.CELL_TYPE_STRING);
//						associate.setLongitude(cell6.getStringCellValue());
//					}
//					XSSFCell cell7 = row.getCell(7);
//					if (cell7 != null || "".equals(cell7)) {
//						cell7.setCellType(Cell.CELL_TYPE_STRING);
//						associate.setDescription(cell7.getStringCellValue());
//					}
//					result.add(associate);
//				}
//			}
//			return result;
//		}
//
//		private List<Associate> getAssociateHSSFResult(Workbook wb) {
//			// TODO Auto-generated method stub
//			List<Associate> result = new ArrayList<Associate>();
//			List<AssociateType> associateTypeList = new ArrayList<AssociateType>();
//			associateTypeList = associateService.getAssociateTypeAllList();
//			List<Area> areaList = areaService.getAreaAlList();
//			for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
//				HSSFSheet st = (HSSFSheet) wb.getSheetAt(sheetIndex);
//				// 第一行为标题，不�?
//				for (int rowIndex = 1; rowIndex <= st.getLastRowNum(); rowIndex++) {
//					HSSFRow row = st.getRow(rowIndex);
//					if (row == null) {
//						break;
//					}
//					Associate associate = new Associate();
//					User u = this.getLoginUser();
//					associate.setCreator(u.getId());
//					associate.setCreatorname(u.getName());
//					associate.setOrganname(u.getOrganName()); 
//					associate.setGuid(u.getGuid());
//					HSSFCell cell0 = row.getCell(0);
//					if (cell0 != null || "".equals(cell0)) {
//						cell0.setCellType(Cell.CELL_TYPE_STRING);
//						associate.setName(cell0.getStringCellValue());
//					}
//					HSSFCell cell1 = row.getCell(1);
//					if (cell1 != null || "".equals(cell1)) {
//						cell1.setCellType(Cell.CELL_TYPE_STRING);
//						for(AssociateType a : associateTypeList){
//							String name = a.getName();
//							if(name.equals(cell1.getStringCellValue())){
//								associate.setTypeid(a.getId());
//								break ;
//							}
//						}
//						if(associate.getTypeid() == null){
//							associate.setTypeid(associateTypeList.get(0).getId());
//						}
//						String serialNo = getAssoSerialNo(associate.getTypeid()); 
//						associate.setSerialno(serialNo);
//					}
//					HSSFCell cell2 = row.getCell(2);
//					if (cell2 != null || "".equals(cell2)) {
//						cell2.setCellType(Cell.CELL_TYPE_STRING);
//						associate.setAddress(cell2.getStringCellValue());
//					}
//					HSSFCell cell3 = row.getCell(3);
//					if (cell3 != null || "".equals(cell3)) {
//						cell3.setCellType(Cell.CELL_TYPE_STRING);
//						associate.setTelephone(cell3.getStringCellValue());
//					}
//					HSSFCell cell4 = row.getCell(4);
//					if (cell4 != null || "".equals(cell4)) {
//						cell4.setCellType(Cell.CELL_TYPE_STRING);
//						associate.setAreaId(0);
//						for(Area a : areaList){
//							String name = a.getName();
//							if(name.equals(cell4.getStringCellValue())){
//								associate.setAreaId(a.getId());
//								break;
//							}
//						}
//						if(associate.getAreaId() == 0){
//							associate.setAreaId(areaList.get(0).getId());
//						}
//					}
//					HSSFCell cell5 = row.getCell(5);
//					if (cell5 != null || "".equals(cell5)) {
//						cell5.setCellType(Cell.CELL_TYPE_STRING);
//						associate.setLatitude(cell5.getStringCellValue());
//					}
//					HSSFCell cell6 = row.getCell(6);
//					if (cell6 != null || "".equals(cell6)) {
//						cell6.setCellType(Cell.CELL_TYPE_STRING);
//						associate.setLongitude(cell6.getStringCellValue());
//					}
//					HSSFCell cell7 = row.getCell(7);
//					if (cell7 != null || "".equals(cell7)) {
//						cell7.setCellType(Cell.CELL_TYPE_STRING);
//						associate.setDescription(cell7.getStringCellValue());
//					}
//					result.add(associate);
//				}
//			}
//			return result;
//		}
//		private String getAssoSerialNo(Integer typeid) {
//			// TODO Auto-generated method stub
//			String serialNo = "";
//			AssociateType at = new AssociateType();
//			at = associateService.getAssociateTypeById(typeid);
//			serialNo = at.getKeyword() +"000001";
//			List<Associate> assoList = new ArrayList<Associate>();
//			Associate associate = new Associate();
//			associate.setSearchName(at.getKeyword());
//			assoList = associateService.getAssociateList(associate); 
//			int temp =0;
//			if(assoList.size() >0){
//				for(Associate a : assoList){
//					String sNo = a.getSerialno();
//					sNo = sNo.replaceAll(at.getKeyword(), "");
//					sNo = sNo.replaceAll("^(0+)", "");
//					int maxSerialNo = Integer.parseInt(sNo);
//					if(maxSerialNo > temp){
//						temp = maxSerialNo;
//					}
//				}
//				int maxNo  = temp +1;
//				String maxStr = maxNo+"";
//				if(maxStr.length() == 1){
//					serialNo = at.getKeyword() + "00000" + maxStr;
//				}else if(maxStr.length() == 2){
//					serialNo = at.getKeyword() + "0000" + maxStr;
//				}else if(maxStr.length() == 3){
//					serialNo = at.getKeyword() + "000" + maxStr;
//				}else if(maxStr.length() == 4){
//					serialNo = at.getKeyword() + "00" + maxStr;
//				}else if(maxStr.length() == 5){
//					serialNo = at.getKeyword() + "0" + maxStr;
//				}else if(maxStr.length() == 6){
//					serialNo = at.getKeyword() + maxStr;
//				}
//			} 
//			return serialNo;
//		}
//	
//
//	/**
//	 * 导入社会机构数据采集信息 
//	 * 
//	 * @param request
//	 * @param response
//	 * @param file
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value = "/jsonLoadAssociateMemberExcel.do", method = RequestMethod.POST, produces = { "text/html;charset=UTF-8" })
//	public JsonResult<AssociatePerson> jsonLoadAssociateMemberExcel(HttpServletRequest request,
//			HttpServletResponse response,
//			@RequestParam(value = "associateid" , required = false) Integer associateid,
//			@RequestParam(value = "file", required = false) MultipartFile file) {
//		JsonResult<AssociatePerson> js = new JsonResult<AssociatePerson>();
//		js.setCode(1);
//		js.setMessage("导入数据失败�?);		  
//		if(associateid == null || associateid == 0)
//		{
//			js.setMessage("未指定社会机构！");
//			return js;			
//		}
//		List<AssociatePerson> associatePersonlist = new ArrayList<AssociatePerson>();
//		List<AssociatePerson> associatePersonSuccesslist = new ArrayList<AssociatePerson>();
//		List<AssociatePerson> associatePersonFaultlist = new ArrayList<AssociatePerson>();
//		int totalCount = 0;
//		int rightCount = 0;
//		int faultCount = 0;
//
//		try {
//			// 项目在容器中的实际发布运行的upload路径
//			String path = request.getSession().getServletContext()
//					.getRealPath("upload");
//			// 获取文件�?
//			String fileName = file.getOriginalFilename();
//			File targetFile = new File(path, fileName);
//			if (!targetFile.exists()) {
//				targetFile.mkdirs();
//			}
//			// 保存
//			try {
//				File f = new File(targetFile.getPath());
//				if (f.exists()) {
//					f.delete();
//				}
//				// 把MultipartFile转换成File类型,MultipartFile自带的transferTo
//				file.transferTo(targetFile);
//				InputStream stream = new FileInputStream(targetFile.getPath());
//				Workbook wb = WorkbookFactory.create(stream);
//				// HSSFWorkbook是解析出来excel 2007 以前版本的，后缀名为xls�?
//				// XSSFWorkbook是解析excel 2007 版的，后�?��为xlsx�?
//				try {
//					stream = new FileInputStream(targetFile.getPath());
//					wb = new XSSFWorkbook(stream);
//					associatePersonlist = getAssociatePersonXSSFResult(wb);
//					
//				} catch (Exception ex) {
//					stream = new FileInputStream(targetFile.getPath());
//					wb = new HSSFWorkbook(stream);
//					associatePersonlist = getAssociatePersonHSSFResult(wb);
//				}
//				for (AssociatePerson p : associatePersonlist) {
//					//格式转换
//					
//					// 把刚获取的列存入list,判断获取的对象是否按照规�?
//					if (p.getName() != null
//							&& !"".equals(p.getName())
//							&& p.getAddress() != null
//							&& !"".equals(p.getAddress())
//							&& p.getIdcard() != null
//							&& !"".equals(p.getIdcard()) 
//							&& p.getSex() != null
//							&& !"".equals(p.getSex()) 
//							&& p.getBirth() != null
//							&& !"".equals(p.getBirth()) 
//							&& p.getIdcard() != null
//							&& !"".equals(p.getIdcard())
//					    ){			
//						p.setAssociateid(associateid);
//						if(p.getIdcard().length() == 18 || p.getIdcard().length() == 15){							
//							associatePersonSuccesslist.add(p);							
//						}else{
//							associatePersonFaultlist.add(p);
//						}
//					}else{
//						associatePersonFaultlist.add(p);
//					}
//				}
//				// stream.close();
//				IOUtils.closeQuietly(stream);
//				totalCount = associatePersonlist.size();
//				rightCount = associatePersonSuccesslist.size();
//				faultCount = associatePersonFaultlist.size();
//				String message = "导入成功，共�?+totalCount+"�?其中成功"+rightCount+"�?失败"+faultCount+"条�?";
//				if (totalCount > 0) {
//					associateService.importAssociatePersonList(associatePersonSuccesslist);
//					js.setCode(0);
//					js.setMessage(message);										
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//   
//		return js;
//	}
//	
//	/**
//	 * 解析相关人员EXCEL文件.xls
//	 * @param wb
//	 * @return
//	 */
//	private List<AssociatePerson> getAssociatePersonHSSFResult(Workbook wb) {		
//			List<AssociatePerson> result = new ArrayList<AssociatePerson>();
//			for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
//				HSSFSheet st = (HSSFSheet) wb.getSheetAt(sheetIndex);
//				// 第一行为标题，不�?
//				for (int rowIndex = 1; rowIndex <= st.getLastRowNum(); rowIndex++) {
//					HSSFRow row = st.getRow(rowIndex);
//					if (row == null) {
//						continue;
//					}
//					AssociatePerson p = new AssociatePerson();
//					User u = this.getLoginUser();
//					p.setGuid(u.getGuid());
//					p.setOrganname(u.getOrganName());
//					p.setCreator(u.getId());
//					p.setCreatorname(u.getName());
//					
//					HSSFCell cell0 = row.getCell(0);
//					if (cell0 != null || "".equals(cell0)) {
//						cell0.setCellType(Cell.CELL_TYPE_STRING);
//						p.setName(cell0.getStringCellValue());
//					}
//					HSSFCell cell1 = row.getCell(1);					
//					if (cell1 != null || "".equals(cell1)) {
//						cell1.setCellType(Cell.CELL_TYPE_STRING);
//						String sex = cell1.getStringCellValue();
//						if(sex.equals("�?) || sex.equals("1"))
//						    p.setSex(1);
//						if(sex.equals("�?) || sex.equals("0"))
//							p.setSex(0);
//					}					
//					HSSFCell cell2 = row.getCell(2);
//					if (cell2 != null || "".equals(cell2)) {
//						cell2.setCellType(Cell.CELL_TYPE_STRING);
//						p.setBirth(cell2.getStringCellValue());
//					}
//					HSSFCell cell3 = row.getCell(3);
//					if (cell3 != null || "".equals(cell3)) {
//						cell3.setCellType(Cell.CELL_TYPE_STRING);
//						p.setIdcard(cell3.getStringCellValue());
//					}
//					HSSFCell cell4 = row.getCell(4);
//					if (cell4 != null || "".equals(cell4)) {
//						cell4.setCellType(Cell.CELL_TYPE_STRING);
//						p.setAddress(cell4.getStringCellValue());
//					}
//					HSSFCell cell5 = row.getCell(5);
//					if (cell5 != null || "".equals(cell5)) {
//						cell5.setCellType(Cell.CELL_TYPE_STRING);
//						p.setCharacter(cell5.getStringCellValue());
//					}
//					HSSFCell cell6 = row.getCell(6);
//					if (cell6 != null || "".equals(cell6)) {
//						cell6.setCellType(Cell.CELL_TYPE_STRING);
//						p.setDescription(cell6.getStringCellValue());
//					}
//					HSSFCell cell7 = row.getCell(7);
//					if (cell7 != null || "".equals(cell7)) {
//						cell7.setCellType(Cell.CELL_TYPE_STRING);
//						p.setIsleader(0);
//						try {	
//							String isleader = cell7.getStringCellValue();
//							if(isleader.equals("�?) || isleader.equals("1"))
//								p.setIsleader(1);
//						} catch (Exception ex) {
//							ex.printStackTrace();
//						}
//					}
//					result.add(p);
//				}
//			}
//			return result;
//		}
//		
//	
//	/**
//	 * 解析相关人员EXCEL文件.xlsx
//	 * @param wb
//	 * @return
//	 */
//	private List<AssociatePerson> getAssociatePersonXSSFResult(Workbook wb) {
//		// TODO Auto-generated method stub
//		List<AssociatePerson> result = new ArrayList<AssociatePerson>();
//		AssociatePerson p = new AssociatePerson();
//		User u = this.getLoginUser();
//		p.setGuid(u.getGuid());
//		p.setOrganname(u.getOrganName());
//		p.setCreator(u.getId());
//		p.setCreatorname(u.getName());
//		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
//			XSSFSheet st = (XSSFSheet) wb.getSheetAt(sheetIndex);
//			// 第一行为标题，不�?
//			for (int rowIndex = 1; rowIndex <= st.getLastRowNum(); rowIndex++) {
//				XSSFRow row = st.getRow(rowIndex);
//				if (row == null) {
//					continue;
//				}
//				
//				XSSFCell cell0 = row.getCell(0);
//				if (cell0 != null || "".equals(cell0)) {
//					cell0.setCellType(Cell.CELL_TYPE_STRING);
//					p.setName(cell0.getStringCellValue());
//				}
//				XSSFCell cell1 = row.getCell(1);
//				if (cell1 != null || "".equals(cell1)) {
//					cell1.setCellType(Cell.CELL_TYPE_STRING);
//					String sex = cell1.getStringCellValue();
//					if(sex.equals("�?) || sex.equals("1"))
//					    p.setSex(1);
//					if(sex.equals("�?) || sex.equals("0"))
//						p.setSex(0);
//				}
//				XSSFCell cell2 = row.getCell(2);
//				if (cell2 != null || "".equals(cell2)) {
//					cell2.setCellType(Cell.CELL_TYPE_STRING);
//					p.setBirth(cell2.getStringCellValue());
//				}
//				XSSFCell cell3 = row.getCell(3);
//				if (cell3 != null || "".equals(cell3)) {
//					cell3.setCellType(Cell.CELL_TYPE_STRING);
//					p.setIdcard(cell3.getStringCellValue());
//				}
//				XSSFCell cell4 = row.getCell(4);
//				if (cell4 != null || "".equals(cell4)) {
//					cell4.setCellType(Cell.CELL_TYPE_STRING);
//					p.setAddress(cell4.getStringCellValue());
//				}
//				XSSFCell cell5 = row.getCell(5);
//				if (cell5 != null || "".equals(cell5)) {
//					cell5.setCellType(Cell.CELL_TYPE_STRING);
//					p.setCharacter(cell5.getStringCellValue());
//				}
//				XSSFCell cell6 = row.getCell(6);
//				if (cell6 != null || "".equals(cell6)) {
//					cell6.setCellType(Cell.CELL_TYPE_STRING);
//					p.setDescription(cell6.getStringCellValue());
//				}
//				XSSFCell cell7 = row.getCell(7);
//				if (cell7 != null || "".equals(cell7)) {
//					cell7.setCellType(Cell.CELL_TYPE_STRING);
//					p.setIsleader(0);
//					try {	
//						String isleader = cell7.getStringCellValue();
//						if(isleader.equals("�?) || isleader.equals("1"))
//							p.setIsleader(1);
//					} catch (Exception ex) {
//						ex.printStackTrace();
//					}
//				}
//		     result.add(p);
//			}
//		}
//		return result;
//	}
//		

}

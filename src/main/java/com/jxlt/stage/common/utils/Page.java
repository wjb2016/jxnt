/**
 * 
 */
package com.jxlt.stage.common.utils;

import java.util.Date;

/**
 * @author Leo
 *
 */
public class Page {
	/**
	 * 分页起始行数，自动计�?	 */
	private Integer pageStart;
	/**
	 * 每页记录数，�?���?	 */
    private Integer pageSize;
    /**
	 * 当前页数，需设置
	 */
    private Integer pageNo;
    /**
     * 查询条件
     */
    private String searchName;
    /**
     * 时间查询
     */
    private Date startTime;    
    private Date endTime;
    private String startTimes;  
    private String endTimes;
    
    /**
	 * 分页起始行数，自动计�?	 */
    @SuppressWarnings("unused")
	private Integer pageCount;
    
    private Integer totalCount;
    
    private Integer pageEnd;
    
    private Integer roleUserId; 
    
	public Integer getPageStart() {
		if (pageNo != null && pageSize != null) {
			pageStart = pageSize * (pageNo-1);
		}
		return pageStart;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageCount() {
		int pageCount = 0;
		if(totalCount != null){
			 pageCount = totalCount%pageSize==0 ? totalCount/pageSize:totalCount/pageSize+1;
			if(pageCount == 0){
				pageCount = 1;
			}
		}else{
			pageCount = 1;
		}
		return pageCount;
	}
	
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the pageEnd
	 */
	public Integer getPageEnd() {
		if (pageStart == null) {
			return null;
		}
		if (totalCount < pageStart + pageSize) {
			pageEnd = totalCount;
		} else {
		    pageEnd = pageStart + pageSize;
		}
		return pageEnd;
	}

	public Integer getRoleUserId() {
		return roleUserId;
	}

	public void setRoleUserId(Integer roleUserId) {
		this.roleUserId = roleUserId;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	} 

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getStartTimes() {
		return startTimes;
	}

	public void setStartTimes(String startTimes) {
		this.startTimes = startTimes;
	}

	public String getEndTimes() {
		return endTimes;
	}

	public void setEndTimes(String endTimes) {
		this.endTimes = endTimes;
	} 
}


package com.jxlt.stage.common.utils;

/**
 * 分页控制工具�? * @author Leo
 */
public class PageUtil {
	
	/**
	 * 每页显示的记录数，需设置
	 */
    private Integer pageSize;
    /**
	 * 当前�?��页数，需设置
	 */
    private Integer pageNo;
    /**
	 * 总记录数，需设置
	 */
    private Integer totalCount;
    /**
	 * 总页数，自动计算
	 */
    private Integer pageCount;
    /**
	 * 分页起始记录数，自动计算
	 */
	private Integer pageStart;
	/**
	 * 分页结束记录数，自动计算
	 */
	private Integer pageEnd;
    
    
	/**
	 * 获取分页起始记录�?	 * @return
	 */
	public Integer getPageStart() {
		if (pageNo != null && pageSize != null) {
			pageStart = pageSize * (pageNo-1);
		}
		return pageStart;
	}
	
	/**
	 * 获取分页结束记录�?	 * @return
	 */
	public Integer getPageEnd() {
		pageEnd = pageStart + pageSize;
		if (pageEnd > totalCount)
			pageEnd = totalCount;
		return pageEnd;
	}
	/**
	 * 获取每页显示的记录数
	 * @return
	 */
	public Integer getPageSize() {
		return pageSize;
	}
	
	/**
	 * 设置每页显示的记录数
	 * @param pageSize
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * 获取当前�?��的页�?	 * @return
	 */
	public Integer getPageNo() {
		return pageNo;
	}
	/**
	 * 设置当前�?��的页�?	 * @param pageNo
	 */
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	/**
	 * 获取总页�?	 * @return
	 */
	public Integer getPageCount() {
		pageCount = 0;
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
	/**
	 * 获取总记录数
	 * @return
	 */
	public Integer getTotalCount() {
		return totalCount;
	}
	/**
	 * 设置总记录输
	 * @param totalCount
	 */
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	
}

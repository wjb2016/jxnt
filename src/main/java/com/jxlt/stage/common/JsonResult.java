/**
 * copy right 2012 sctiyi all rights reserved
 * create time:下午05:37:06
 * author:ftd
 */
package com.jxlt.stage.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ftd
 * @param <E>
 *
 */
public class JsonResult<E> extends BaseResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3575327713107875249L;

	private Object obj;
	
	private List<E> list =new ArrayList<E>(); 
	
	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public List<E> getList() {
		return list;
	}

	public void setList(List<E> list) {
		this.list = list;
	}
	
	
}

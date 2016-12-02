/**
 * @File: BaseModel.java
 * @author ibm
 * @create date 2013-9-25 上午11:03:09
 * @copyright 四川天翼网络服务有限责任公司
 */
package com.jxlt.stage.common;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

@SuppressWarnings("serial")
public abstract class BaseModel implements Serializable{

	/** (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * 打印Model Object实例对象属�?值为字符串显�?	 */
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
	/**
	 * 将对象转换为格式的字符串
	 * @return
	 */
	public String toJson(){
		ObjectMapper om = new ObjectMapper();
		try {
			return om.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 将对象转换为HashMap
	 * @return
	 */
	public Map<String, Object> toMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] fields = this.getClass().getDeclaredFields();
		for(Field field : fields){
			if(Modifier.isStatic(field.getModifiers())) continue;
			try {
				field.setAccessible(true);
				map.put(field.getName(), field.get(this));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}
}

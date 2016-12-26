package com.jxlt.stage.service;

import java.util.List;

import com.jxlt.stage.common.JsonResult;
import com.jxlt.stage.model.Grade;

public interface GradeService {

	List<Grade> getGradeList(Grade grade);

	int getTotalCount(Grade grade);

	JsonResult<Grade> convertGrade(Integer id,Integer integer);

	JsonResult<Grade> disconvertGrade(Integer id, Integer integer);

	Grade getExsitGrade(Grade grade);

	void writeGrade(Integer grades, Integer userId, Integer operId,String description);

	Grade getExsitGrade(int refGrades, int refUserId, String mobile);

	void writeGrade(Integer refGrades, Integer refUserId, Integer id,
			String description, String mobile);

	List<Grade> getExsitGrade(int refGrades, int refUserId);

	int getFalseOrderCount(String mobile);

	int getSureOrderCount(String mobile);

}

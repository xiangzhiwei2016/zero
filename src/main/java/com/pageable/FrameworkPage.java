package com.pageable;

import java.util.List;

public interface FrameworkPage<T> {
//	static default <T> FrameworkPage<T> getFrameworkPage(FrameworkPageable frameworkPageable, List<T> content) {
//		FrameworkPageImpl result = new FrameworkPageImpl();
//		result.setContent(content);
//		result.setPageNo(frameworkPageable.getPageNo());
//		result.setPageSize(frameworkPageable.getPageSize());
//		result.setTotalPage(frameworkPageable.getTotalPage());
//		result.setTotalRecord(frameworkPageable.getTotalRecord());
//		return result;
//	}

	int getPageNo();

	void setPageNo(int arg0);

	int getPageSize();

	void setPageSize(int arg0);

	long getTotalRecord();

	void setTotalRecord(long arg0);

	int getTotalPage();

	void setTotalPage(int arg0);

	List<T> getContent();

	void setContent(List<T> arg0);
}
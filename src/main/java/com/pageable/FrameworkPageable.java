package com.pageable;

public interface FrameworkPageable {
	int getPageNo();

	void setPageNo(int arg0);

	int getPageSize();

	void setPageSize(int arg0);

	long getTotalRecord();

	void setTotalRecord(long arg0);

	int getTotalPage();

	void setTotalPage(int arg0);
}

package com.pageable;

import java.io.Serializable;
import java.util.List;

public class FrameworkPageImpl<T> implements FrameworkPage<T>, Serializable {
	private static final long serialVersionUID = 1003586149169614169L;
	private int pageNo = 1;
	private int pageSize = 10;
	private long totalRecord = -1L;
	private int totalPage;
	private List<T> content;

	public int getPageNo() {
		return this.pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalRecord() {
		return this.totalRecord;
	}

	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
	}

	public int getTotalPage() {
		return this.totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getContent() {
		return this.content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}
}
package com.pageable;


import java.io.Serializable;

public class FrameworkPageableImpl implements FrameworkPageable, Serializable {
	private static final long serialVersionUID = 7523301097937037108L;
	private int pageNo = 1;
	private int pageSize = 10;
	private long totalRecord = -1L;
	private int totalPage;

	public FrameworkPageableImpl() {
	}

	public FrameworkPageableImpl(int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

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
		int totalPage = (int) (totalRecord % (long) this.pageSize == 0L ? totalRecord / (long) this.pageSize
				: totalRecord / (long) this.pageSize + 1L);
		this.setTotalPage(totalPage);
	}

	public int getTotalPage() {
		return this.totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
}
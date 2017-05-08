package com.pageable;


public class PagingResponseResult extends ResponseResult {
	private static final long serialVersionUID = 1032695410647623136L;
	private long totalCount;
	private int pageCount;

	public PagingResponseResult() {
	}

	public PagingResponseResult(long totalCount, int pageCount) {
		this.totalCount = totalCount;
		this.pageCount = pageCount;
	}

	public long getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageCount() {
		return this.pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
}
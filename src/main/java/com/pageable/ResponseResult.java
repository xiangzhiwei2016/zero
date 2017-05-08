package com.pageable;

import java.io.Serializable;

public class ResponseResult implements Serializable {
	private static final long serialVersionUID = 1L;
	protected final String version = "1.0";
	protected int errorCode = 0;
	protected String errorMsg = null;
	protected String data = "";

	public int getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getVersion() {
		return "1.0";
	}
}
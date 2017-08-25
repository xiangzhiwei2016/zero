package com.exception;

public class ServiceException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int errorCode;
	private String errorMsg;

	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(int errorCode, String errorMsg) {
		super("错误码:" + errorCode + ",错误描述:" + errorMsg);
		if (errorCode <= 0) {
			throw new RuntimeException("error code cannot be less than zero");
		}
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public ServiceException(int errorCode, String errorMsg, Exception e) {
		super("错误码:" + errorCode + ",错误描述:" + errorMsg, e);
		if (errorCode <= 0) {
			throw new RuntimeException("error code cannot be less than zero");
		}
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}

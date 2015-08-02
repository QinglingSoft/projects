package com.qinglingsoft.changshuchannel;

public class JsonResult {
	private boolean success;
	private Object object;
	private String errorMessage;

	protected JsonResult(boolean success, Object object, String errorMessage) {
		this.success = success;
		this.object = object;
		this.errorMessage = errorMessage;
	}

	public static JsonResult success(Object object) {
		return new JsonResult(true, object, null);
	}

	public static JsonResult fail(String errorMessage) {
		if(errorMessage.indexOf(":")>-1){
			errorMessage = errorMessage.substring(errorMessage.lastIndexOf(":")+1,errorMessage.length());
		}
		return new JsonResult(false, null, errorMessage);
	}

	public boolean isSuccess() {
		return success;
	}

	public Object getObject() {
		return object;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}

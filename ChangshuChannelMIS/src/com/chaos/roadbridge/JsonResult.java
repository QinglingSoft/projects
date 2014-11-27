package com.chaos.roadbridge;

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

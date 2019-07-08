package org.jeavio.meetin.backend.dto;

public class ApiResponse {

	private int code;
	private String message;
	
	public ApiResponse(int code,String message) {
		this.code=code;
		this.message=message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"code\":\"");
		builder.append(code);
		builder.append("\", \"message\":\"");
		builder.append(message);
		builder.append("\"}");
		return builder.toString();
	}

	
}

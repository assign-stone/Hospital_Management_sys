package com.cg.hms.adm.exception;

public class ErrorResponse<T> {
	
	private T data;
    private String message;
    private int statusCode;

    public ErrorResponse(T data, String message, int statusCode) {
        this.data = data;
        this.message = message;
        this.statusCode = statusCode;
    }

	public T getData() {
		return data;
	}

	public String getMessage() {
		return message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setData(T data) {
		this.data = data;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	@Override
	public String toString() {
		return "ErrorResponse [data=" + data + ", message=" + message + ", statusCode=" + statusCode + "]";
	}

}

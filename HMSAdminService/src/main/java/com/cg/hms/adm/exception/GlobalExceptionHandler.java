package com.cg.hms.adm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ResponseBody
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value= {BadRequestException.class})
	protected ErrorResponse<String> handleConflict(
			BadRequestException ex,
			HttpServletRequest req)
	{
		String bodyOfResponse = ex.getMessage();
		String uri = req.getRequestURL().toString();
		ErrorResponse<String> ei = new ErrorResponse<>(uri, bodyOfResponse, HttpStatus.NOT_FOUND.value());
		System.out.println("--handleConflict executed"+ei);
		return ei;
	}

}

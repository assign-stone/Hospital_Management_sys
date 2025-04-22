package com.cg.hms.phy.exception;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice // it will handle the exception
public class ErrorAdvise {

	@ResponseBody
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = { BadRequestException.class })
	protected ErrorInfo handleException(BadRequestException ex, HttpServletRequest req) {

		String bodyOfResponse = ex.getMessage();
		String uri = req.getRequestURL().toString();
		ErrorInfo ei = new ErrorInfo(uri, bodyOfResponse); // it will return message and URL as response
		System.out.println("--handleConflict executed--" + ei);
		return ei;
	}

}

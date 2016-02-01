package com.tubager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tubager.exception.*;

@ControllerAdvice
public class GlobalControllerAdvice {
	
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="INVALID_CODE")
	@ExceptionHandler(InvalidSmsCodeException.class)
	public void invalidSmsCode(){
		
	}
	
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="AUTH_FAIL")
	@ExceptionHandler(AuthenticationFailException.class)
	public void authenticationFail(){
		
	}
	
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="USER_EXISTS")
	@ExceptionHandler(UserExistException.class)
	public void userAlreadyExists(){
		
	}
	
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="FILE_NOT_FOUND")
	@ExceptionHandler(FileNotFound.class)
	public void fileNotFound(){
		
	}
}

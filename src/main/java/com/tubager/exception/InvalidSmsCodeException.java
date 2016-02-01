package com.tubager.exception;

public class InvalidSmsCodeException extends RuntimeException{
	
	private static final long serialVersionUID = -7051983416417286607L;

	public InvalidSmsCodeException(String msg){
		super(msg);
	}
}

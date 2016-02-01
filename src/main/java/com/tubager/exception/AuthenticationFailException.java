package com.tubager.exception;

public class AuthenticationFailException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4537678999394536416L;
	
	public AuthenticationFailException(String msg){
		super(msg);
	}
}

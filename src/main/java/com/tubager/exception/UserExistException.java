package com.tubager.exception;

public class UserExistException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3351217694054506796L;
	
	public UserExistException(String msg){
		super(msg);
	}

}

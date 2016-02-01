package com.tubager.exception;

public class FileNotFound extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6561556887449148566L;
	
	public FileNotFound(String msg){
		super(msg);
	}
}

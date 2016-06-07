package com.tubager.domain;

import org.springframework.security.core.authority.AuthorityUtils;

public class CurrentUser extends org.springframework.security.core.userdetails.User{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1046151974169846142L;
	private TUser user;
	
	public CurrentUser(TUser user, TAuth auth){
		super(user.getName(), auth.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
		this.user = user;
	}
	
	public TUser getUser(){
	    return this.user;
	}
	
	public int getId(){
		return this.user.getId();
	}
	
	public RoleEnum getRole(){
		return this.user.getRole();
	}

}

package com.tubager.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tubager.dao.UserDao;
import com.tubager.domain.CurrentUser;
import com.tubager.domain.TAuth;
import com.tubager.domain.TUser;
import com.tubager.exception.UserExistException;

@Service
public class UserService implements UserDetailsService{
    @Autowired
	private UserDao userDao;
    
    private final static Logger logger = LoggerFactory.getLogger(UserService.class);
    
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		TUser user = userDao.getUser(username);
		TAuth auth = userDao.getAuth(username);
		if(user == null){
			throw new UsernameNotFoundException("User with login name ${username} was not found.");
		}else{
			return new CurrentUser(user, auth);
		}
	}
	
	public boolean checkExistence(String name){
		TUser user = userDao.getUser(name);
		if(user != null){
			return true;
		}
		return false;
	}
	
	public boolean checkMailExistence(String email){
		TUser user = userDao.getUserByMail(email);
		if(user != null){
			return true;
		}
		return false;
	}
	
	public TUser getCurrentUser(){
		CurrentUser current = null;
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(obj  instanceof CurrentUser){
			current = (CurrentUser) obj;
			if(current != null){
				return userDao.getUser(current.getUser().getName());
			}
		}
		TUser user = userDao.getUser((String) obj);
		return user;
	}
	
	public void signup(String userName, String password, String mobile, String email) throws RuntimeException{
		TAuth auth = userDao.getAuth(userName);
		System.out.println(userName);
		if(auth != null){
			throw new UserExistException("用户名已存在");
		}
//		String cacheCode = SmsCache.getInstance().get(userName);
//		if(cacheCode == null || !cacheCode.equals(code)){
//			throw new InvalidSmsCodeException("验证码不正确");
//		}
		//new BCryptPasswordEncoder().matches(data.oldPw, persistUser.passwordHash)
		String encoded = new BCryptPasswordEncoder().encode(password);
		auth = new TAuth();
		auth.setUserName(userName);
		auth.setPassword(encoded);
		Date date = new Date();
		auth.setDateCreated(date);
		auth.setLastUpdated(date);
		
		userDao.register(auth, mobile, email);
	}
	
	public void updateUser(TUser user){
		userDao.updateUser(user);
	}
}

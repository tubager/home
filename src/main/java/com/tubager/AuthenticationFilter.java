package com.tubager;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.UrlPathHelper;

import com.tubager.domain.CurrentUser;
import com.tubager.domain.RoleEnum;
import com.tubager.service.UserService;

public class AuthenticationFilter extends GenericFilterBean {
	private final static Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;

	public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpRequest = asHttp(request);
        HttpServletResponse httpResponse = asHttp(response);
		String resourcePath = new UrlPathHelper().getPathWithinApplication(httpRequest);
		try{
			//TUser user = userService.getCurrentUser();
			
			if(resourcePath.startsWith("/account/admin")){
				Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if(obj  instanceof CurrentUser){
					CurrentUser current = (CurrentUser) obj;
					if(current.getRole() != RoleEnum.ADMIN){
						httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
						//chain.doFilter(request, response);
						return;
					}
				}
			}
			//logger.info(user.getName());
		}catch(Exception e){
			//e.printStackTrace();
		}
		chain.doFilter(request, response);
	}

    private HttpServletRequest asHttp(ServletRequest request) {
        return (HttpServletRequest) request;
    }

    private HttpServletResponse asHttp(ServletResponse response) {
        return (HttpServletResponse) response;
    }

}

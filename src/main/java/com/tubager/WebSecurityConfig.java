package com.tubager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.tubager.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserService userService;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
	        .csrf().disable()
	        .authorizeRequests()
            .antMatchers("/admin/**", "/upload/**", "/expert/**").authenticated()
			.anyRequest().permitAll()
        .and()
            .formLogin().loginPage("/login.html").defaultSuccessUrl("/index.html").failureUrl("/login.html?error=1").permitAll()
        .and()
            .logout().logoutUrl("/logout").logoutSuccessUrl("/login.html").permitAll()
		.and().rememberMe().tokenRepository(new InMemoryTokenRepositoryImpl()).tokenValiditySeconds(1209600);
        
        http.addFilterBefore(new AuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
//            .inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER");
    }
}
package com.tubager.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tubager.domain.RoleEnum;
import com.tubager.domain.TAuth;
import com.tubager.domain.TUser;

@Repository
public class UserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void changePassword(String userName, String password){
		String sql = "update auth set password=? where user_name=?";
		jdbcTemplate.update(sql, new Object[]{password,userName});
	}
	
	public void verifyEmail(String userName){
		String sql = "update auth set email_verified='V' where user_name=?";
		jdbcTemplate.update(sql, new Object[]{userName});
	}
	
	public TUser getUser(String userName){
		try{
			TUser user = (TUser)jdbcTemplate.queryForObject("select * from user where name = ?", new Object[] {userName},
					(rs, rowNum) ->{
						TUser b = new TUser();
						b.setName(userName);
						b.setId(rs.getInt("id"));
						b.setTechId(rs.getString("tech_id"));
						b.setNickName(rs.getString("nick_name"));
						b.setGender(rs.getString("gender"));
						b.setImg(rs.getString("img"));
						b.setMobile(rs.getString("mobile"));
						b.setEmail(rs.getString("email"));
						b.setAddress(rs.getString("address"));
						b.setRegion(rs.getString("region"));
						b.setMobileVerified(rs.getString("mobile_verified"));
						b.setEmailVerified(rs.getString("email_verified"));
						b.setLastWord(rs.getString("last_word"));
						String role = rs.getString("role");
						if(role == null || role.equals("")){
							b.setRole(RoleEnum.USER);
						}
						else{
							b.setRole(RoleEnum.valueOf(role));
						}
						return b;
					});
			return user;
		}catch(Exception e){
			return null;
		}
	}
	
	public TUser getUserByMail(String email){
		try{
			TUser user = (TUser)jdbcTemplate.queryForObject("select * from user where email = ?", new Object[] {email},
					(rs, rowNum) ->{
						TUser b = new TUser();
						b.setName(rs.getString("name"));
						b.setId(rs.getInt("id"));
						b.setTechId(rs.getString("tech_id"));
						b.setNickName(rs.getString("nick_name"));
						b.setGender(rs.getString("gender"));
						b.setImg(rs.getString("img"));
						b.setMobile(rs.getString("mobile"));
						b.setEmail(rs.getString("email"));
						b.setAddress(rs.getString("address"));
						b.setRegion(rs.getString("region"));
						b.setMobileVerified(rs.getString("mobile_verified"));
						b.setEmailVerified(rs.getString("email_verified"));
						b.setLastWord(rs.getString("last_word"));
						String role = rs.getString("role");
						if(role == null || role.equals("")){
							b.setRole(RoleEnum.USER);
						}
						else{
							b.setRole(RoleEnum.valueOf(role));
						}
						return b;
					});
			return user;
		}catch(Exception e){
			return null;
		}
	}
	
	public void updateUser(TUser user){
		Date date = new Date();
		java.sql.Timestamp today = new java.sql.Timestamp(date.getTime());
		
		String sql = "update user set nick_name=?, gender=?, img=?, mobile=?, email=?, address=?, region=?, mobile_verified=?, email_verified=?, last_updated=?, last_word=? where name=?";
		jdbcTemplate.update(sql, new Object[]{user.getNickName(),user.getGender(),
				user.getImg(),user.getMobile(), user.getEmail(), user.getAddress(), user.getRegion(),
				user.getMobileVerified(), user.getEmailVerified(), today,user.getLastWord(), user.getName()});
	}
	
	public void register(TAuth auth, String mobile, String email){
		Date date = new Date();
		java.sql.Timestamp today = new java.sql.Timestamp(date.getTime());
		
		String insertSql="insert into auth values(?,?,?,?)";
		jdbcTemplate.update(insertSql, new Object[]{auth.getUserName(),auth.getPassword(),today,today});
		
		insertSql = "INSERT INTO user (name, role, mobile, email, date_created, last_updated, tech_id) values(?,?,?,?,?,?)";
		jdbcTemplate.update(insertSql, new Object[]{auth.getUserName(),"USER",mobile, email,today,today,null});
	}
	
	public TAuth getAuth(String userName){
		List<TAuth> authList = jdbcTemplate.query(
                "SELECT user_name, password FROM auth where user_name='" + userName + "'",
                (rs, rowNum) -> {
	                	TAuth auth = new TAuth();
	                	auth.setUserName(rs.getString("user_name"));
	                	auth.setPassword(rs.getString("password"));
	                	return auth;
                	}
        );
		if(authList.size() > 0){
			return authList.get(0);
		}
		return null;
	}
}

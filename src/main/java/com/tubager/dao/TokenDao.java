package com.tubager.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.tubager.domain.TToken;

@Repository
public class TokenDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public TToken getByToken(String token){
		final List<TToken> result = new ArrayList<TToken>();
		String sql = "SELECT token, username FROM token where token='" + token + "'";
		jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                	TToken ttoken = new TToken();
                	ttoken.setToken(rs.getString("token"));
                	ttoken.setUserId(rs.getString("username"));
                	return ttoken;
                }
        ).forEach(t -> {result.add(t);});
		if(result.size() == 0){
			return null;
		}
		return result.get(0);
	}
	
	public TToken getByUser(String username){
		return null;
	}
}

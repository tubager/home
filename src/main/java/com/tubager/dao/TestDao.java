package com.tubager.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.tubager.domain.TUser;

@Repository
public class TestDao {
	
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public TestDao(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
		System.out.println("JDBC Template is: ");
		System.out.println(jdbcTemplate);
	}
	
//	public void testRun(){
//		
//		jdbcTemplate.query(
//                "SELECT id, name FROM user",
//                (rs, rowNum) -> new TUser(rs.getString("id"), rs.getString("name"))
//        ).forEach(customer -> System.out.println(customer.toString()));
//	}
}

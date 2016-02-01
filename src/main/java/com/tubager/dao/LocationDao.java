package com.tubager.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tubager.domain.TLocation;
import com.tubager.domain.TTrace;
import com.tubager.domain.TTraceSpot;

@Repository
public class LocationDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void addLocation(TTrace trace){
		String techId = trace.getUserId();
		List<TTraceSpot> spots = trace.getSpots();
		String insertSql="insert into trace_spot(`tech_id`,`latitude`,`longitude`,`name`,`date_created`) values(?,?,?,?,?)";
		jdbcTemplate.batchUpdate(insertSql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				TTraceSpot spot = spots.get(i);
				TLocation l = spot.getLocation();
				ps.setString(1, techId);
				ps.setDouble(2, l.getLatitude());
				ps.setDouble(3, l.getLongitude());
				ps.setString(4, null);
				ps.setTimestamp(5, new java.sql.Timestamp(spot.getTime().getTime()));
			}
			
			@Override
			public int getBatchSize() {
				return spots.size();
			}
		});
	}
}

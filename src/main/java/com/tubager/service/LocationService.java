package com.tubager.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tubager.dao.LocationDao;
import com.tubager.domain.TLocation;
import com.tubager.domain.TTrace;
import com.tubager.domain.TTraceSpot;

@Service
public class LocationService {
	@Autowired
	private LocationDao locationDao;
	
	public void saveTrace(String techId, String text){
		TTrace trace = new TTrace();
		trace.setUserId(techId);
		List<TTraceSpot> spots = new ArrayList<TTraceSpot>();
		trace.setSpots(spots);
		String[] texts = text.split(";");
		for(String t : texts){
			if(t.equals("")){
				continue;
			}
			String[] data = t.split(",");
			TLocation l = new TLocation();
			l.setLatitude(Double.parseDouble(data[0]));
			l.setLongitude(Double.parseDouble(data[1]));
			Date d = new Date(Long.parseLong(data[2]));
			System.out.println("-----------------");
			System.out.println(data[2]);
			System.out.println(d.toString());
			System.out.println(new java.sql.Timestamp(d.getTime()).toString());
			TTraceSpot spot = new TTraceSpot();
			spot.setTime(d);
			spot.setLocation(l);
			spots.add(spot);
		}
		locationDao.addLocation(trace);
	}
}

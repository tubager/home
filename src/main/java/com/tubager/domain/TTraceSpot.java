package com.tubager.domain;

import java.util.Date;

public class TTraceSpot {
	private String uuid;
	private Date time;
	private TLocation location;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public TLocation getLocation() {
		return location;
	}
	public void setLocation(TLocation location) {
		this.location = location;
	}
}

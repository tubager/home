package com.tubager.domain;

import java.util.List;

public class TTrace {
	private String uuid;
	private String userId;
	private List<TTraceSpot> spots;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<TTraceSpot> getSpots() {
		return spots;
	}
	public void setSpots(List<TTraceSpot> spots) {
		this.spots = spots;
	}
}
